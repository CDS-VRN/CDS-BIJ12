package nl.ipo.cds.etl.theme.buisleidingen;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;

public class TransportrouteRisicoThemeConfig extends AbstractBuisleidingenThemeConfig<TransportrouteRisico> {

	private final static String THEME_NAME = "Buisleidingen - Transportrouterisico";
	
	public TransportrouteRisicoThemeConfig (final TransportrouteRisicoValidator validator, final OperationDiscoverer operationDiscoverer) {
		super (THEME_NAME, TransportrouteRisico.class, validator, operationDiscoverer);
	}
}
