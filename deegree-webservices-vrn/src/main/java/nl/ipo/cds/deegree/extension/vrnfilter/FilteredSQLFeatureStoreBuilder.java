package nl.ipo.cds.deegree.extension.vrnfilter;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.net.MalformedURLException;

import org.deegree.db.ConnectionProvider;
import org.deegree.db.ConnectionProviderProvider;
import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.sql.SQLFeatureStore;
import org.deegree.feature.persistence.sql.SqlFeatureStoreBuilder;
import org.deegree.feature.persistence.sql.SqlFeatureStoreMetadata;
import org.deegree.feature.persistence.sql.jaxb.SQLFeatureStoreJAXB;
import org.deegree.workspace.ResourceBuilder;
import org.deegree.workspace.Workspace;
import org.slf4j.Logger;

/**
 * @author annes
 *
 */
public class FilteredSQLFeatureStoreBuilder implements ResourceBuilder<FeatureStore> {

	private static final Logger LOG = getLogger(SqlFeatureStoreBuilder.class);

	private SqlFeatureStoreMetadata metadata;

	private SQLFeatureStoreJAXB config;

	private Workspace workspace;

	public FilteredSQLFeatureStoreBuilder(SqlFeatureStoreMetadata metadata, SQLFeatureStoreJAXB config, Workspace workspace) {
		this.metadata = metadata;
		this.config = config;
		this.workspace = workspace;
	}

	@Override
	public FeatureStore build() {
		ConnectionProvider conn = workspace.getResource(ConnectionProviderProvider.class, config.getJDBCConnId().getValue());
		File file = metadata.getLocation().resolveToFile(metadata.getIdentifier().getId() + ".xml");
		SQLFeatureStore fs = null;
		try {
			// TODO rewrite needed to properly resolve files using resource location
			fs = new FilteredSQLFeatureStore(config, file.toURI().toURL(), conn.getDialect(), metadata, workspace);
		} catch (MalformedURLException e) {
			LOG.trace("Stack trace:", e);
		}
		return fs;
	}
}
