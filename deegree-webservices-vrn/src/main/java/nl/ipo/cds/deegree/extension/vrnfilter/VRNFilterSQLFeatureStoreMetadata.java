package nl.ipo.cds.deegree.extension.vrnfilter;

import nl.ipo.cds.deegree.persistence.jaxb.VRNFilterSQLFeatureStoreConfig;

import org.deegree.commons.xml.jaxb.JAXBUtils;
import org.deegree.db.ConnectionProvider;
import org.deegree.db.ConnectionProviderProvider;
import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.simplesql.SimpleSqlFeatureStoreBuilder;
import org.deegree.feature.persistence.simplesql.jaxb.SimpleSQLFeatureStoreConfig;
import org.deegree.workspace.ResourceBuilder;
import org.deegree.workspace.ResourceInitException;
import org.deegree.workspace.ResourceLocation;
import org.deegree.workspace.Workspace;
import org.deegree.workspace.standard.AbstractResourceMetadata;
import org.deegree.workspace.standard.AbstractResourceProvider;
import org.deegree.workspace.standard.DefaultResourceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class VRNFilterSQLFeatureStoreMetadata extends AbstractResourceMetadata<FeatureStore> {

	private static final Logger LOG = LoggerFactory.getLogger(VRNFilterSQLFeatureStoreMetadata.class);

	private static final String CONFIG_JAXB_PACKAGE = "nl.ipo.cds.deegree.persistence.jaxb";
	
	public VRNFilterSQLFeatureStoreMetadata(Workspace workspace, ResourceLocation<FeatureStore> location,
	AbstractResourceProvider<FeatureStore> provider) {
		super(workspace, location, provider);
	}
	
	@Override
	public ResourceBuilder<FeatureStore> prepare() {
		VRNFilterSQLFeatureStoreConfig config;
		try {
			config = (VRNFilterSQLFeatureStoreConfig) JAXBUtils.unmarshall(CONFIG_JAXB_PACKAGE, VRNFilterSQLFeatureStoreProvider.CONFIG_SCHEMA , location.getAsStream(), workspace);
			
			//dependencies.add(new DefaultResourceIdentifier<ConnectionProvider>(ConnectionProviderProvider.class, null));
			return new VRNFilterSQLFeatureStoreBuilder(workspace);
		} catch (Exception e) {
			LOG.trace("Stack trace:", e);
			throw new ResourceInitException(e.getLocalizedMessage(), e);
		}
	}

}
