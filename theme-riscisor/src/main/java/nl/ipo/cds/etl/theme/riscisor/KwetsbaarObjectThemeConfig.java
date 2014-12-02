package nl.ipo.cds.etl.theme.riscisor;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.DefaultDatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;

public class KwetsbaarObjectThemeConfig extends ThemeConfig<KwetsbaarObject> {

	private final Validator<KwetsbaarObject> validator;

	private final OperationDiscoverer operationDiscoverer;
	
	public KwetsbaarObjectThemeConfig (final Validator<KwetsbaarObject> validator, final OperationDiscoverer operationDiscoverer) {
		super ("RISC-ISOR - Kwetsbaar object", KwetsbaarObject.class);
		
		this.validator = validator;
		this.operationDiscoverer = operationDiscoverer;
	}

	@Override
	public DatasetHandlers<KwetsbaarObject> createDatasetHandlers (final EtlJob job, final ManagerDao managerDao) {		
		return new DefaultDatasetHandlers<> (operationDiscoverer, this, managerDao);
	}

	@Override
	public Validator<KwetsbaarObject> getValidator () throws ThemeConfigException {
		return validator;
	}
}
