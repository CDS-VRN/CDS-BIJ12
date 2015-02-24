package nl.ipo.cds.deegree.extension.vrnfilter;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.xml.namespace.QName;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.deegree.persistence.jaxb.VRNFilterSQLFeatureStoreConfig;
import nl.ipo.cds.domain.Bronhouder;
import nl.ipo.cds.domain.Gebruiker;
import nl.ipo.cds.domain.GebruikerThemaAutorisatie;
import nl.ipo.cds.domain.TypeGebruik;

import org.deegree.commons.annotations.LoggingNotes;
import org.deegree.commons.tom.gml.GMLObject;
import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.FeatureStoreException;
import org.deegree.feature.persistence.FeatureStoreTransaction;
import org.deegree.feature.persistence.lock.LockManager;
import org.deegree.feature.persistence.query.Query;
import org.deegree.feature.stream.FeatureInputStream;
import org.deegree.feature.types.AppSchema;
import org.deegree.feature.types.GenericFeatureType;
import org.deegree.filter.Expression;
import org.deegree.filter.Filter;
import org.deegree.filter.FilterEvaluationException;
import org.deegree.filter.OperatorFilter;
import org.deegree.filter.expression.ValueReference;
import org.deegree.filter.logical.And;
import org.deegree.filter.spatial.Intersects;
import org.deegree.filter.spatial.Overlaps;
import org.deegree.geometry.Envelope;
import org.deegree.geometry.Geometry;
import org.deegree.protocol.wfs.getfeature.TypeName;
import org.deegree.workspace.Resource;
import org.deegree.workspace.ResourceMetadata;
import org.deegree.workspace.Workspace;
import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * @author annes
 */

@LoggingNotes(info = "logs problems when connecting to the DB/getting data from the DB", debug = "logs the SQL statements sent to the SQL server", trace = "logs stack traces")
public class VRNFilterSQLFeatureStore implements FeatureStore {
	static final Logger LOG = getLogger(VRNFilterSQLFeatureStore.class);

	private VRNFilterSQLFeatureStoreMetadata metadata = null;

	GenericFeatureType featureType;

	private FeatureStore featureStore;
	private VRNFilterSQLFeatureStoreConfig config;

	private ManagerDao managerDao;

	public VRNFilterSQLFeatureStore(FeatureStore featureStore, VRNFilterSQLFeatureStoreMetadata metadata,
			VRNFilterSQLFeatureStoreConfig config, Workspace workspace, ManagerDao managerDao) {

		this.featureStore = featureStore;
		this.metadata = metadata;
		this.config = config;
		this.managerDao = managerDao;
	}

	public FeatureStoreTransaction acquireTransaction() throws FeatureStoreException {
		return featureStore.acquireTransaction();
	}

	public Envelope calcEnvelope(QName ftName) throws FeatureStoreException {
		return featureStore.calcEnvelope(ftName);
	}

	public void destroy() {
		featureStore.destroy();
	}

	public Envelope getEnvelope(QName ftName) throws FeatureStoreException {
		return featureStore.getEnvelope(ftName);
	}

	public LockManager getLockManager() throws FeatureStoreException {
		return featureStore.getLockManager();
	}

	public ResourceMetadata<? extends Resource> getMetadata() {
		return metadata;
	}

	public GMLObject getObjectById(String id) throws FeatureStoreException {
		return featureStore.getObjectById(id);
	}

	public AppSchema getSchema() {
		return featureStore.getSchema();
	}

	public void init() {
		// nothing to init
	}

	public boolean isAvailable() {
		return true;
	}

	public boolean isMapped(QName ftName) {
		return featureStore.isMapped(ftName);
	}

	public FeatureInputStream query(Query query) throws FeatureStoreException, FilterEvaluationException {

		return featureStore.query(applyFilterSecurity(query));
	}

	public FeatureInputStream query(Query[] queries) throws FeatureStoreException, FilterEvaluationException {
		// need to check filter for authorized geometries, for every type name
		for (int i = 0; i < queries.length; i++) {
			queries[i] = applyFilterSecurity(queries[i]);
		}
		return featureStore.query(queries);
	}

	/**
	 * @param queries
	 * @return true if user should be granted access
	 */
	private Query applyFilterSecurity(Query query) {

		if (!config.isFilterGebruikerThemaAutorisatie()) {
			// no access restrictions; return original query unmodified
			return query;
		} else {

			// we need the current bronhouderthema autorisaties for current 'gebruiker'
			final String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
			final Gebruiker gebruiker = managerDao.getGebruiker(principal);
			if (gebruiker.isSuperuser()) {
				LOG.info("WFS request by superuser " + principal + " not applying any access restrictions");
				// no access restrictions
				return query;
			}
			Assert.notNull(gebruiker, "Failed to lookup user: " + principal);
			final List<GebruikerThemaAutorisatie> themaAutorisaties = managerDao
					.getGebruikerThemaAutorisatie(gebruiker);

			TypeName[] typeNames = query.getTypeNames();

			// er kan maar een typename in een wfs getfeature request voorkomen
			Assert.isTrue(typeNames.length == 1, "A WFS request is supposed to concern a single typename, but found: "
					+ typeNames);
			QName featureTypeName = typeNames[0].getFeatureTypeName();
			String localPart = featureTypeName.getLocalPart(); // ProvinciaalGebiedBeheer

			// Check that gebruiker is autorised: gebruiker needs role RAADPLEGER for the thema with the same name
			// as
			// the featuretype that is requested
			for (GebruikerThemaAutorisatie gta : themaAutorisaties) {
				String themanaam = gta.getBronhouderThema().getThema().getNaam(); // ProvinciaalGebiedBeheer
				if (themanaam.equals(localPart) && gta.getTypeGebruik() == TypeGebruik.RAADPLEGER) {
					// gebruiker is autorized
					if (config.isFilterBronHouderGeometry()) {
						// add additional filter on bronhouder geometry
						return filterBronHouderGeometry(query, gta.getBronhouderThema().getBronhouder(),
								featureTypeName.getNamespaceURI());
					}
					// else return unmodified filter
					return query;
				}

			}
			// no matching autorisation was found. Return an empty iterator
			LOG.warn("Gebruiker {} is not autorized for featureType {}. Throwing Security Exception", new Object[] {
					principal, featureTypeName });
			throw new AccessDeniedException("Gebruiker " + principal + " heeft geen raadpleger rechten voor thema "
					+ localPart + ".");
		}
	}

	private Query filterBronHouderGeometry(Query query, Bronhouder bronhouder, String namespaceURI) {

		Filter filter = query.getFilter();
		// need extra geo filtering
		Geometry g = managerDao.getBronhouderGeometry(bronhouder);
		if (g == null) {
			LOG.warn("Bronhouder geometry is null for bronhouder{}. Throwing Security Exception",
					new Object[] { bronhouder });
			throw new AccessDeniedException("Bronhouder " + bronhouder.getNaam() + " heeft geen geldige geometrie.");
		}
		Expression exp = new ValueReference(new QName(namespaceURI, "geometrie", "imna"));
		Intersects intersectsOperator = new Intersects(exp, g);
		if (filter == null) {
			filter = new OperatorFilter(intersectsOperator);
		} else {
			And andOperator = new And(intersectsOperator, ((OperatorFilter) filter).getOperator());
			filter = new OperatorFilter(andOperator);
		}

		return new Query(query.getTypeNames(), filter, null, null, query.getSortProperties());

	}

	public int queryHits(Query arg0) throws FeatureStoreException, FilterEvaluationException {
		return featureStore.queryHits(arg0);
	}

	public int[] queryHits(Query[] arg0) throws FeatureStoreException, FilterEvaluationException {
		return featureStore.queryHits(arg0);
	}

}
