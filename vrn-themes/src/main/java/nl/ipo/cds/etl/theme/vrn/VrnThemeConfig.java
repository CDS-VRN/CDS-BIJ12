package nl.ipo.cds.etl.theme.vrn;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;

public class VrnThemeConfig extends ThemeConfig<AbstractGebied>{

	public VrnThemeConfig(String themeName, Class<AbstractGebied> featureTypeClass) {
		super(themeName, featureTypeClass);
		// TODO Auto-generated constructor stub
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
