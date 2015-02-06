package nl.ipo.cds.deegree.extension.vrnfilter;

import static org.slf4j.LoggerFactory.getLogger;

import javax.xml.namespace.QName;

import org.deegree.commons.annotations.LoggingNotes;
import org.deegree.commons.tom.gml.GMLObject;
import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.FeatureStoreException;
import org.deegree.feature.persistence.FeatureStoreTransaction;
import org.deegree.feature.persistence.lock.LockManager;
import org.deegree.feature.persistence.query.Query;
import org.deegree.feature.persistence.sql.MappedAppSchema;
import org.deegree.feature.persistence.sql.SQLFeatureStore;
import org.deegree.feature.stream.FeatureInputStream;
import org.deegree.filter.Filter;
import org.deegree.filter.FilterEvaluationException;
import org.deegree.geometry.Envelope;
import org.deegree.workspace.Resource;
import org.deegree.workspace.ResourceMetadata;
import org.slf4j.Logger;

/**
 * @author annes
 *
 */
@LoggingNotes(info = "logs problems when connecting to the DB/getting data from the DB", debug = "logs the SQL statements sent to the SQL server", trace = "logs stack traces")
public class VRNFilterSQLFeatureStore implements FeatureStore {

	static final Logger LOG = getLogger(VRNFilterSQLFeatureStore.class);

	private static SQLFeatureStore sqlFeatureStore;

	public FeatureStoreTransaction acquireTransaction() throws FeatureStoreException {
		return sqlFeatureStore.acquireTransaction();
	}

	public Envelope calcEnvelope(QName ftName) throws FeatureStoreException {
		return sqlFeatureStore.calcEnvelope(ftName);
	}

	public void destroy() {
		sqlFeatureStore.destroy();
	}

	public Envelope getEnvelope(QName ftName) throws FeatureStoreException {
		return sqlFeatureStore.getEnvelope(ftName);
	}

	public LockManager getLockManager() throws FeatureStoreException {
		return sqlFeatureStore.getLockManager();
	}

	public ResourceMetadata<? extends Resource> getMetadata() {
		return sqlFeatureStore.getMetadata();
	}

	public GMLObject getObjectById(String id) throws FeatureStoreException {
		return sqlFeatureStore.getObjectById(id);
	}

	public MappedAppSchema getSchema() {
		return sqlFeatureStore.getSchema();
	}

	public int hashCode() {
		return sqlFeatureStore.hashCode();
	}

	public void init() {
		sqlFeatureStore.init();
	}

	public boolean isAvailable() {
		return sqlFeatureStore.isAvailable();
	}

	public boolean isMapped(QName ftName) {
		return sqlFeatureStore.isMapped(ftName);
	}

	public FeatureInputStream query(Query query) throws FeatureStoreException, FilterEvaluationException {
		Filter filter = query.getFilter();
		// add provincie grenzen filter
		// TODO get provincie grenzen from logged in user
	
		return sqlFeatureStore.query(query);
	}

	public FeatureInputStream query(Query[] arg0) throws FeatureStoreException, FilterEvaluationException {
		return sqlFeatureStore.query(arg0);
	}

	public int queryHits(Query arg0) throws FeatureStoreException, FilterEvaluationException {
		return sqlFeatureStore.queryHits(arg0);
	}

	public int[] queryHits(Query[] arg0) throws FeatureStoreException, FilterEvaluationException {
		return sqlFeatureStore.queryHits(arg0);
	}

	public String toString() {
		return sqlFeatureStore.toString();
	}

}
