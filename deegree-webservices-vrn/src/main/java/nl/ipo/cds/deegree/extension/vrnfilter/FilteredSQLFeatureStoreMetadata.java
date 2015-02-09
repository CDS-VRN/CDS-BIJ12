package nl.ipo.cds.deegree.extension.vrnfilter;

import org.deegree.commons.xml.jaxb.JAXBUtils;
import org.deegree.db.ConnectionProvider;
import org.deegree.db.ConnectionProviderProvider;
import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.sql.SqlFeatureStoreMetadata;
import org.deegree.feature.persistence.sql.jaxb.SQLFeatureStoreJAXB;
import org.deegree.workspace.ResourceBuilder;
import org.deegree.workspace.ResourceInitException;
import org.deegree.workspace.ResourceLocation;
import org.deegree.workspace.Workspace;
import org.deegree.workspace.standard.AbstractResourceProvider;
import org.deegree.workspace.standard.DefaultResourceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilteredSQLFeatureStoreMetadata extends SqlFeatureStoreMetadata {

	private static final Logger LOG = LoggerFactory.getLogger(FilteredSQLFeatureStoreMetadata.class);

	private static final String CONFIG_JAXB_PACKAGE = "nl.ipo.cds.deegree.persistence.jaxb";

	public FilteredSQLFeatureStoreMetadata(Workspace workspace, ResourceLocation<FeatureStore> location,
	AbstractResourceProvider<FeatureStore> provider) {
		super(workspace, location, provider);
	}

	@Override
	public ResourceBuilder<FeatureStore> prepare() {
		try {
			SQLFeatureStoreJAXB cfg = (SQLFeatureStoreJAXB) JAXBUtils.unmarshall(CONFIG_JAXB_PACKAGE, FilteredSQLFeatureStoreProvider.CONFIG_SCHEMA,
			location.getAsStream(), workspace);
			String connid = cfg.getJDBCConnId().getValue();
			dependencies.add(new DefaultResourceIdentifier<ConnectionProvider>(ConnectionProviderProvider.class, connid));
			dependencies.add(new DefaultResourceIdentifier<ConnectionProvider>(ConnectionProviderProvider.class, "LOCK_DB"));
			return new FilteredSQLFeatureStoreBuilder(this, cfg, workspace);
		} catch (Exception e) {
			throw new ResourceInitException(e.getLocalizedMessage(), e);
		}
	}

}
