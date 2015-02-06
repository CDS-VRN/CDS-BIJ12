package nl.ipo.cds.deegree.extension.vrnfilter;

import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.sql.SqlFeatureStoreProvider;
import org.deegree.workspace.ResourceBuilder;
import org.deegree.workspace.Workspace;

/**
 * @author annes
 *
 */
public class VRNFilterSQLFeatureStoreBuilder implements ResourceBuilder<FeatureStore> {

	private final Workspace workspace;

	public VRNFilterSQLFeatureStoreBuilder(Workspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public FeatureStore build() {
		FeatureStore sqlFeatureStoreProvider =  workspace.getResource(SqlFeatureStoreProvider.class, "gebiedbeheer_provinciaal");

		return new VRNFilterSQLFeatureStore(sqlFeatureStoreProvider);
	}

}
