package nl.ipo.cds.deegree.extension.vrnfilter;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.deegree.persistence.jaxb.VRNFilterSQLFeatureStoreConfig;

import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.FeatureStoreProvider;
import org.deegree.workspace.ResourceBuilder;
import org.deegree.workspace.Workspace;

/**
 * @author annes
 * 
 */
public class VRNFilterSQLFeatureStoreBuilder implements ResourceBuilder<FeatureStore> {

	private final Workspace workspace;
	private VRNFilterSQLFeatureStoreMetadata metadata;
	private VRNFilterSQLFeatureStoreConfig config;
    private ManagerDao managerDao;
	public VRNFilterSQLFeatureStoreBuilder(Workspace workspace, VRNFilterSQLFeatureStoreMetadata metadata,
			VRNFilterSQLFeatureStoreConfig config, ManagerDao managerDao) {
		this.workspace = workspace;
		this.metadata = metadata;
		this.config = config;
        this.managerDao=managerDao;
	}

	@Override
	public FeatureStore build() {
		FeatureStore sqlFeatureStoreProvider = workspace.getResource(FeatureStoreProvider.class,
				config.getDelegateFeatureStoreId());

		return new VRNFilterSQLFeatureStore(sqlFeatureStoreProvider, metadata, config, workspace, managerDao);
	}

}
