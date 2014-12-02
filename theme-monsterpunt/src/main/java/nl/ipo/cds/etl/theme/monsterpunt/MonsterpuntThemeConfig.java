package nl.ipo.cds.etl.theme.monsterpunt;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.DefaultDatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;

public class MonsterpuntThemeConfig extends ThemeConfig<Monsterpunt> {

	private final Validator<Monsterpunt> validator;

	private final OperationDiscoverer operationDiscoverer;

	public MonsterpuntThemeConfig (final Validator<Monsterpunt> validator, final OperationDiscoverer operationDiscoverer) {
		super ("Monsterpunt", Monsterpunt.class);
		this.validator = validator;
		this.operationDiscoverer = operationDiscoverer;
	}

	@Override
	public DatasetHandlers<Monsterpunt> createDatasetHandlers (final EtlJob job, final ManagerDao managerDao) {
		return new DefaultDatasetHandlers<> (operationDiscoverer, this, managerDao);
	}

	@Override
	public Validator<Monsterpunt> getValidator () throws ThemeConfigException {
		return validator;
	}
}
