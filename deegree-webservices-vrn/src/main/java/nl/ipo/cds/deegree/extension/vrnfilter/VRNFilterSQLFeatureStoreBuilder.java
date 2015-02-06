package nl.ipo.cds.deegree.extension.vrnfilter;

import org.deegree.feature.persistence.FeatureStore;
import org.deegree.workspace.ResourceBuilder;

/**
 * @author annes
 *
 */
public class VRNFilterSQLFeatureStoreBuilder implements ResourceBuilder<FeatureStore> {

	@Override
	public FeatureStore build() {

		return new VRNFilterSQLFeatureStore();
	}

}
