package nl.ipo.cds.etl.theme.buisleidingen;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;

public class TransportroutedeelThemeConfig extends AbstractBuisleidingenThemeConfig<Transportroutedeel> {

	private final static String THEME_NAME = "Buisleidingen - Transportroutedelen";

	
	public TransportroutedeelThemeConfig (final TransportroutedeelValidator validator, final OperationDiscoverer operationDiscoverer) {
		super (THEME_NAME, Transportroutedeel.class, validator, operationDiscoverer);
	}
}
