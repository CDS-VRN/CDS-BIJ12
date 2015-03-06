package nl.ipo.cds.deegree.extension.vrnfilter;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.deegree.persistence.jaxb.VRNFilterSQLFeatureStoreConfig;
import nl.ipo.cds.deegree.persistence.jaxb.VRNFilterSQLFeatureStoreConfig.FilterVendorRequestKVP;
import nl.ipo.cds.domain.BronhouderThema;
import nl.ipo.cds.domain.Gebruiker;
import nl.ipo.cds.domain.GebruikerThemaAutorisatie;
import nl.ipo.cds.domain.TypeGebruik;

import org.deegree.commons.annotations.LoggingNotes;
import org.deegree.commons.tom.gml.GMLObject;
import org.deegree.commons.tom.gml.GMLObjectType;
import org.deegree.commons.tom.primitive.PrimitiveValue;
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
import org.deegree.filter.MatchAction;
import org.deegree.filter.Operator;
import org.deegree.filter.OperatorFilter;
import org.deegree.filter.comparison.PropertyIsEqualTo;
import org.deegree.filter.expression.Literal;
import org.deegree.filter.expression.ValueReference;
import org.deegree.filter.logical.And;
import org.deegree.filter.logical.Or;
import org.deegree.filter.spatial.Intersects;
import org.deegree.geometry.Envelope;
import org.deegree.geometry.Geometry;
import org.deegree.geometry.Geometry.GeometryType;
import org.deegree.geometry.primitive.Polygon;
import org.deegree.geometry.standard.multi.DefaultMultiPolygon;
import org.deegree.protocol.wfs.getfeature.TypeName;
import org.deegree.workspace.Resource;
import org.deegree.workspace.ResourceMetadata;
import org.deegree.workspace.Workspace;
import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Applies additional filtering on a delegate feature store.
 * <ul>
 * <li>if {@link VRNFilterSQLFeatureStoreConfig#isFilterGebruikerThemaAutorisatie()} the authorisations for a user for
 * the requested feature types are checked</li>
 * <li>if {@link VRNFilterSQLFeatureStoreConfig#isFilterBronHouderGeometry())} an additional filter on bronhouder
 * geometry is added</li>
 * <li>if {@link VRNFilterSQLFeatureStoreConfig#getFilterVendorRequestKVP()} is specified, an addiontal filter based on
 * a request parameter is added geometry is added</li>
 * </ul>
 * 
 * @author annes
 * @author reinoldp
 * 
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

		return featureStore.query(applyFilters(query));
	}

	public FeatureInputStream query(Query[] queries) throws FeatureStoreException, FilterEvaluationException {
		// need to check filter for authorized geometries, for every type name
		for (int i = 0; i < queries.length; i++) {
			queries[i] = applyFilters(queries[i]);
		}
		return featureStore.query(queries);
	}

	/**
	 * @param queries
	 * @return true if user should be granted access
	 */
	private Query applyFilters(Query query) {
		TypeName[] typeNames = query.getTypeNames();

		// er kan maar een typename in een wfs getfeature request voorkomen
		Assert.isTrue(typeNames.length == 1, "A WFS request is supposed to concern a single typename, but found: "
				+ typeNames);
		QName featureTypeName = typeNames[0].getFeatureTypeName();
		String localPart = featureTypeName.getLocalPart();
		String namespaceURI = featureTypeName.getNamespaceURI();

		query = applyKVPFilter(query, namespaceURI);
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

			// Check that gebruiker is autorised: gebruiker needs role RAADPLEGER for the thema with the same name
			// as
			// the featuretype that is requested
			boolean allowed = false;
			List<Geometry> bronhouderGeometries = new ArrayList<Geometry>();
			for (GebruikerThemaAutorisatie gta : themaAutorisaties) {
				BronhouderThema bronhouderThema = gta.getBronhouderThema();
				String themanaam = bronhouderThema.getThema().getNaam(); // ProvinciaalGebiedBeheer
				if (themanaam.equals(localPart) && gta.getTypeGebruik().isAllowed(TypeGebruik.RAADPLEGER)) {
					// gebruiker is authorized
					LOG.debug("User is allowed to query featuretype {} by bronhouder {}", new Object[] { localPart,
							bronhouderThema.getBronhouder() });
					allowed = true;
					if (config.isFilterBronHouderGeometry()) {
						// add additional filter on bronhouder geometry
						Geometry g = managerDao.getBronhouderGeometry(bronhouderThema.getBronhouder());
						if (g != null) {
							bronhouderGeometries.add(g);
						}
					}
				}
			}
			if (allowed && !config.isFilterBronHouderGeometry()) {
				// allowed and no additional spatial filtering is necessary
				return query;
			} else if (allowed && config.isFilterBronHouderGeometry() && bronhouderGeometries.size() >= 1) {
				// allowed, but need to filter on bronhouder geometry
				return filterBronHouderGeometry(query, bronhouderGeometries, namespaceURI);
			}
			// no matching autorisation or no valid geometry was found. Return an empty iterator
			LOG.warn("Gebruiker {} is not autorized for featureType {}. Throwing Security Exception", new Object[] {
					principal, featureTypeName });
			throw new AccessDeniedException("Gebruiker " + principal + " heeft geen raadpleger rechten voor thema "
					+ localPart + ".");
		}
	}

	/**
	 * Apply custom attribute filters as specified in request.
	 * 
	 * @param query
	 * @param namespaceURI
	 * @return
	 */
	private Query applyKVPFilter(Query query, String namespaceURI) {
		if (config.getFilterVendorRequestKVP().size() == 0) {
			// none specified in config
			return query;
		}
		// create IsEquals filter for each matching kvp
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		List<Operator> operators = new ArrayList<Operator>();
		for (FilterVendorRequestKVP key : config.getFilterVendorRequestKVP()) {
			String[] values = request.getParameterValues(key.getValue());
			if (values == null || values.length == 0) {
				// no matching query KVP found
				continue;
			}
			operators.add(createPropertyIsEqualsOperator(key.getValue(), values, namespaceURI, key.isMatchCase()));
		}
		return and(query, operators);
	}

	private Query and(Query query, List<Operator> operators) {
		if (operators.size() == 0) {
			return query;
		} else if (query.getFilter() == null && operators.size() == 1) {
			return new Query(query.getTypeNames(), new OperatorFilter(operators.get(0)), null, null,
					query.getSortProperties());
		} else {
			// combine using and
			operators.add(((OperatorFilter) query.getFilter()).getOperator());
			return new Query(query.getTypeNames(), new OperatorFilter(new And(operators.toArray(new Operator[operators
					.size()]))), null, null, query.getSortProperties());
		}
	}

	private Operator createPropertyIsEqualsOperator(String key, String[] values, String namespaceURI, Boolean matchCase) {
		List<PropertyIsEqualTo> operators = new ArrayList<PropertyIsEqualTo>();
		Expression exp = new ValueReference(new QName(namespaceURI, key));
		for (String value : values) {
			PropertyIsEqualTo piet = new PropertyIsEqualTo(exp, new Literal<PrimitiveValue>(value), matchCase,
					MatchAction.ANY);
			operators.add(piet);
		}
		return operators.size() == 1 ? operators.get(0) : new Or(operators.toArray(new PropertyIsEqualTo[operators
				.size()]));

	}

	private Query filterBronHouderGeometry(Query query, List<Geometry> bronhouderGeometries, String namespaceURI) {

		Filter filter = query.getFilter();
		// need extra geo filtering
		// create spatial join of bronhouderGeometries
		Geometry filterGeometry = bronhouderGeometries.get(0);
		for (int i = 1; i < bronhouderGeometries.size(); i++) {
			filterGeometry = filterGeometry.getUnion(bronhouderGeometries.get(i));
		}
		
		Expression exp = new ValueReference(new QName(namespaceURI, "geometrie"));
		Intersects intersectsOperator = new Intersects(exp, filterGeometry);
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
