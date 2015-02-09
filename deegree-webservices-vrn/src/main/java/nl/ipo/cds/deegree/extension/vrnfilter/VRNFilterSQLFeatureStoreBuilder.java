package nl.ipo.cds.deegree.extension.vrnfilter;

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

	public VRNFilterSQLFeatureStoreBuilder(Workspace workspace, VRNFilterSQLFeatureStoreMetadata metadata, VRNFilterSQLFeatureStoreConfig config) {
		this.workspace = workspace;
		this.metadata = metadata;
		this.config = config;
	}


	
	@Override
	public FeatureStore build() {
		FeatureStore sqlFeatureStoreProvider =  workspace.getResource(FeatureStoreProvider.class, "gebiedbeheer_provinciaal");

//		String name = config.getFeatureTypeName();
//        String ns = config.getFeatureTypeNamespace();
//        String prefix = config.getFeatureTypePrefix();
		return new VRNFilterSQLFeatureStore(sqlFeatureStoreProvider, metadata, config, workspace,null,null,null);//, name, ns, prefix);
	}

}
