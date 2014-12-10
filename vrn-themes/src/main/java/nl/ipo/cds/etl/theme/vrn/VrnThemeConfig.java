package nl.ipo.cds.etl.theme.vrn;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;

public class VrnThemeConfig extends ThemeConfig<AbstractGebied>{

	private final Validator<AbstractGebied> validator;

	private final OperationDiscoverer operationDiscoverer;


	public VrnThemeConfig(final Validator<AbstractGebied> validator, final OperationDiscoverer operationDiscoverer) {
		super ("AbstractGebied", AbstractGebied.class);
		this.validator = validator;
		this.operationDiscoverer = operationDiscoverer;
	}
	
	
	@Override
	public DatasetHandlers<AbstractGebied> createDatasetHandlers(EtlJob job, ManagerDao managerDao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Validator<AbstractGebied> getValidator() throws ThemeConfigException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
