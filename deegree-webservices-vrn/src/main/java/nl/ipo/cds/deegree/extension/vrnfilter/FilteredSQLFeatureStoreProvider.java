package nl.ipo.cds.deegree.extension.vrnfilter;

import java.net.URL;

import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.sql.SqlFeatureStoreProvider;
import org.deegree.workspace.ResourceLocation;
import org.deegree.workspace.ResourceMetadata;
import org.deegree.workspace.Workspace;

/**
 * @author annes
 *
 */
public class FilteredSQLFeatureStoreProvider extends SqlFeatureStoreProvider {

	// private static final String CONFIG_NS = "urn:cds-vrn:deegree-extension:filter";
	//
	// static final URL CONFIG_SCHEMA = FilteredSQLFeatureStoreProvider.class.getResource("/META-INF/schema/vrnfiltersqlfeaturestore.xsd");
	//
	// @Override
	// public String getNamespace() {
	// return CONFIG_NS;
	// }

	static final URL CONFIG_SCHEMA = FilteredSQLFeatureStoreProvider.class.getResource("/META-INF/schema/fsql.xsd");

	@Override
	public ResourceMetadata<FeatureStore> createFromLocation(Workspace workspace, ResourceLocation<FeatureStore> location) {
		return new FilteredSQLFeatureStoreMetadata(workspace, location, this);
	}

	// @Override
	// public URL getSchema() {
	// return CONFIG_SCHEMA;
	// }

}
