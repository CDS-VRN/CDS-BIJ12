/**
 * 
 */
package nl.ipo.cds.deegree.extension.vrnfilter;

import java.net.URL;

import org.deegree.feature.persistence.FeatureStoreException;
import org.deegree.feature.persistence.query.Query;
import org.deegree.feature.persistence.sql.SQLFeatureStore;
import org.deegree.feature.persistence.sql.SqlFeatureStoreMetadata;
import org.deegree.feature.persistence.sql.jaxb.SQLFeatureStoreJAXB;
import org.deegree.feature.stream.FeatureInputStream;
import org.deegree.filter.FilterEvaluationException;
import org.deegree.sqldialect.SQLDialect;
import org.deegree.workspace.Workspace;

/**
 * @author annes
 *
 */
public class FilteredSQLFeatureStore extends SQLFeatureStore {

	/**
	 * @param config
	 * @param configURL
	 * @param dialect
	 * @param metadata
	 * @param workspace
	 */
	public FilteredSQLFeatureStore(SQLFeatureStoreJAXB config, URL configURL, SQLDialect dialect, SqlFeatureStoreMetadata metadata,
	Workspace workspace) {
		super(config, configURL, dialect, metadata, workspace);
	}

	@Override
	public FeatureInputStream query(Query query) throws FeatureStoreException, FilterEvaluationException {
		// TODO Auto-generated method stub
		return super.query(query);
	}
	
	

}
