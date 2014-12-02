package nl.ipo.cds.etl.theme.buisleidingen;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.DefaultDatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;

public abstract class AbstractBuisleidingenThemeConfig<T extends AbstractBuisleidingenFeature> extends ThemeConfig<T> {
	
	protected final Validator<T> validator;

	protected final OperationDiscoverer operationDiscoverer;
	
	public AbstractBuisleidingenThemeConfig (final String themeName, final Class<T> featureTypeClass, final Validator<T> validator, final OperationDiscoverer operationDiscoverer) {
		super(themeName, featureTypeClass);

		assert (validator != null);
		assert (operationDiscoverer != null);
		
		this.validator = validator;
		this.operationDiscoverer = operationDiscoverer;
	}
	
	@Override
	public Validator<T> getValidator () throws ThemeConfigException {
		return validator;
	}
	
	@Override
	public boolean isJobSupported (final EtlJob job) {
		return job.getDatasetType () != null
				&& job.getDatasetType ().getThema () != null
				&& getThemeName ().equals (job.getDatasetType ().getThema ().getNaam ());
	}
	
	@Override
	public DatasetHandlers<T> createDatasetHandlers (final EtlJob job, final ManagerDao managerDao) {
		return new DefaultDatasetHandlers<> (operationDiscoverer, this, managerDao );
	}
}
