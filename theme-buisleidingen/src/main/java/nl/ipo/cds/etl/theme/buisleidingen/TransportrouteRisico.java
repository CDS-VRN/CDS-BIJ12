package nl.ipo.cds.etl.theme.buisleidingen;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.geometry.Geometry;

@Table(name="buisleidingen_transportrouterisico", schema="bron")
public class TransportrouteRisico extends AbstractBuisleidingenFeature {
	@Column (name = "risicocontour10_6")
	private Geometry risicocontour;
	
	@MappableAttribute
	public Geometry getRisicocontour() {
		return risicocontour;
	}

	@MappableAttribute
	public void setRisicocontour(Geometry risicocontour) {
		this.risicocontour = risicocontour;
	}
}
