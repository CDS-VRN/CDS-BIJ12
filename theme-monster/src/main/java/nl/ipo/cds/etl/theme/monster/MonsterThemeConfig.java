package nl.ipo.cds.etl.theme.monster;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.DefaultDatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;

public class MonsterThemeConfig extends ThemeConfig<Monster> {

	private final Validator<Monster> validator;

	private final OperationDiscoverer operationDiscoverer;

	public MonsterThemeConfig (final Validator<Monster> validator, final OperationDiscoverer operationDiscoverer) {
		super ("Monster", Monster.class);
		this.validator = validator;
		this.operationDiscoverer = operationDiscoverer;
	}

	@Override
	public DatasetHandlers<Monster> createDatasetHandlers (final EtlJob job,	final ManagerDao managerDao) {
		return new DefaultDatasetHandlers<> (operationDiscoverer, this, managerDao);
	}

	@Override
	public Validator<Monster> getValidator () throws ThemeConfigException {
		return validator;
	}
}
