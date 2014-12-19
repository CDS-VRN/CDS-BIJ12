package nl.ipo.cds.etl.theme.vrn;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.DefaultDatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;

public class VrnThemeConfig<T extends AbstractGebied> extends ThemeConfig<T>{

	private final Validator<T> validator;

	private final OperationDiscoverer operationDiscoverer;
	

	public VrnThemeConfig(final Validator<T> validator, final OperationDiscoverer operationDiscoverer, final Class<T> clazz) {
		super (clazz.getSimpleName(),  clazz);
		this.validator = validator;
		this.operationDiscoverer = operationDiscoverer;
	}
	

	@Override
	public DatasetHandlers<T> createDatasetHandlers(EtlJob job, ManagerDao managerDao) {
		return new DefaultDatasetHandlers<> (operationDiscoverer, this, managerDao);
	}

	@Override
	public Validator<T> getValidator() throws ThemeConfigException {
		return validator;
	}

	
}
