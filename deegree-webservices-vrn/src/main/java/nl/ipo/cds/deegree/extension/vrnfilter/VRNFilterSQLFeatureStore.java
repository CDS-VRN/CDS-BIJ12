package nl.ipo.cds.deegree.extension.vrnfilter;

import static org.slf4j.LoggerFactory.getLogger;

import java.net.URL;

import javax.xml.namespace.QName;

import nl.ipo.cds.deegree.persistence.jaxb.VRNFilterSQLFeatureStoreConfig;

import org.deegree.commons.annotations.LoggingNotes;
import org.deegree.commons.tom.gml.GMLObject;
import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.FeatureStoreException;
import org.deegree.feature.persistence.FeatureStoreTransaction;
import org.deegree.feature.persistence.lock.LockManager;
import org.deegree.feature.persistence.query.Query;
import org.deegree.feature.stream.FeatureInputStream;
import org.deegree.feature.types.AppSchema;
import org.deegree.feature.types.FeatureType;
import org.deegree.feature.types.GenericAppSchema;
import org.deegree.feature.types.GenericFeatureType;
import org.deegree.filter.Filter;
import org.deegree.filter.FilterEvaluationException;
import org.deegree.geometry.Envelope;
import org.deegree.workspace.Resource;
import org.deegree.workspace.ResourceMetadata;
import org.deegree.workspace.Workspace;
import org.slf4j.Logger;

/**
 * @author annes
 *
 */

@LoggingNotes(info = "logs problems when connecting to the DB/getting data from the DB", debug = "logs the SQL statements sent to the SQL server", trace = "logs stack traces")
public class VRNFilterSQLFeatureStore implements FeatureStore {

	static final Logger LOG = getLogger(VRNFilterSQLFeatureStore.class);

	private FeatureStore featureStore;

	private final VRNFilterSQLFeatureStoreMetadata metadata;

	private AppSchema schema;

	private VRNFilterSQLFeatureStoreConfig config;

	private Workspace workspace;

	private URL configURL;

	GenericFeatureType featureType;

	private boolean available;

	private final QName ftName;

	public VRNFilterSQLFeatureStore(FeatureStore featureStore, VRNFilterSQLFeatureStoreMetadata metadata, VRNFilterSQLFeatureStoreConfig config,
	Workspace workspace, String ftLocalName, String ftNamespace, String ftPrefix) {
		this.featureStore = featureStore;
		this.metadata = metadata;
		this.config = config;
		this.workspace = workspace;

		ftLocalName = (ftLocalName != null && !ftLocalName.isEmpty()) ? ftLocalName : "Feature";
		ftNamespace = (ftNamespace != null && !ftNamespace.isEmpty()) ? ftNamespace : "http://www.deegree.org/app";
		ftPrefix = (ftPrefix != null && !ftPrefix.isEmpty()) ? ftPrefix : "app";
		this.ftName = new QName(ftNamespace, ftLocalName, ftPrefix);

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
		AppSchema ds = featureStore.getSchema();
//		FeatureType[] delegateFeatureTypes = ds.getFeatureTypes();
//		FeatureType[] fts = new FeatureType[delegateFeatureTypes.length];
//		for (int i = 0; i < fts.length; i++) {
//			FeatureType ft = delegateFeatureTypes[i];
//		}
//		
//
//		// create a copy
//		GenericAppSchema appSchema = new GenericAppSchema(fts, ds.getFtToSuperFt(), ds.getNamespaceBindings(), ds.getGMLSchema(), ds
//		.getGmlObjectTypes(), ds.getGeometryToSuperType());
		return ds;
	}

	public void init() {
        int i=0,test=i++;
		/*
		 * featureType = DbFeatureUtils.determineFeatureType(ftName, null, null); if (featureType == null) { available = false; } else { schema = new
		 * GenericAppSchema(new FeatureType[] { featureType }, null, null, null, null, null); available = true; }
		 */
		//
	}

	public boolean isAvailable() {
		return true;
	}

	public boolean isMapped(QName ftName) {
		return featureStore.isMapped(ftName);
	}

	public FeatureInputStream query(Query query) throws FeatureStoreException, FilterEvaluationException {
		Filter filter = query.getFilter();
		// add provincie grenzen filter
		// TODO get provincie grenzen from logged in user

		return featureStore.query(query);
	}

	public FeatureInputStream query(Query[] arg0) throws FeatureStoreException, FilterEvaluationException {
		return featureStore.query(arg0);
	}

	public int queryHits(Query arg0) throws FeatureStoreException, FilterEvaluationException {
		return featureStore.queryHits(arg0);
	}

	public int[] queryHits(Query[] arg0) throws FeatureStoreException, FilterEvaluationException {
		return featureStore.queryHits(arg0);
	}

}
