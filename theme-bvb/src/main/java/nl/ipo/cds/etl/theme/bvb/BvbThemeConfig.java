package nl.ipo.cds.etl.theme.bvb;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;
import nl.ipo.cds.etl.theme.schema.SchemaHarvester;

public class BvbThemeConfig extends ThemeConfig<Bvb> {

	private final Validator<Bvb> validator;

	private final OperationDiscoverer operationDiscoverer;
	
	public BvbThemeConfig (final Validator<Bvb> validator, final OperationDiscoverer operationDiscoverer) {
		super ("Bvb", Bvb.class);
		this.validator = validator;
		this.operationDiscoverer = operationDiscoverer;
	}

	@Override
	public DatasetHandlers<Bvb> createDatasetHandlers (final EtlJob job, final ManagerDao managerDao){		
		return new BvbDatasetHandlers (operationDiscoverer, this, managerDao);
	}

	@Override
	public Validator<Bvb> getValidator () throws ThemeConfigException {
		return validator;
	}

	@Override	
	public SchemaHarvester getSchemaHarvester() {
		return new BvbAppSchemaReader();
	}

}
