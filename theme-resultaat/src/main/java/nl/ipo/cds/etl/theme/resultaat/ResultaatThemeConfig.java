package nl.ipo.cds.etl.theme.resultaat;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.DefaultDatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;

public class ResultaatThemeConfig extends ThemeConfig<Resultaat> {

	private final Validator<Resultaat> validator;

	private final OperationDiscoverer operationDiscoverer;

	public ResultaatThemeConfig(final Validator<Resultaat> validator,
			final OperationDiscoverer operationDiscoverer) {
		super("Resultaat", Resultaat.class);
		this.validator = validator;
		this.operationDiscoverer = operationDiscoverer;
	}

	@Override
	public DatasetHandlers<Resultaat> createDatasetHandlers(final EtlJob job,
			final ManagerDao managerDao) {
		return new DefaultDatasetHandlers<>(operationDiscoverer, this,
				managerDao);
	}

	@Override
	public Validator<Resultaat> getValidator() throws ThemeConfigException {
		return validator;
	}
}
