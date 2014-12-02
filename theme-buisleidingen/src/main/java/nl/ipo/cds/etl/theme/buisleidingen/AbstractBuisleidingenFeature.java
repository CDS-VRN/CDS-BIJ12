package nl.ipo.cds.etl.theme.buisleidingen;

import java.sql.Timestamp;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

public abstract class AbstractBuisleidingenFeature extends PersistableFeature {
	@Column (name = "transportroute_id")
	private String transportrouteId;		// Max. 30 not null
	
	@Column (name = "laatste_mutatiedatumtijd")
	private Timestamp laatsteMutatiedatumtijd;	// not null
	
	@MappableAttribute
	public String getTransportrouteId() {
		return transportrouteId;
	}

	@MappableAttribute
	public void setTransportrouteId (String transportrouteId) {
		this.transportrouteId = transportrouteId;
	}
	
	@MappableAttribute
	public Timestamp getLaatsteMutatiedatumtijd() {
		return laatsteMutatiedatumtijd == null ?  null : new Timestamp (laatsteMutatiedatumtijd.getTime ());
	}

	@MappableAttribute
	public void setLaatsteMutatiedatumtijd(Timestamp laatsteMutatiedatumtijd) {
		this.laatsteMutatiedatumtijd = laatsteMutatiedatumtijd == null ? null : new Timestamp (laatsteMutatiedatumtijd.getTime ());
	}
}
