package nl.ipo.cds.etl.theme.vrn.beheer;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;
import nl.ipo.cds.etl.theme.vrn.AbstractGebied;

@Table(name = "hazard_area", schema = "bron")
public abstract class AbstractGebiedBeheer extends AbstractGebied {

    @Column(name = "status_beheer")
    private String statusBeheer;

    @Column(name = "beheerpakket")
    private String beheerpakket;

    @Column(name = "doel_beheer")
    private String doelBeheer;

    @Column(name = "type_beheerder")
    private String typeBeheerder;

    @Column(name="eenheidnummer")
    private String eenheidnummer;

    @MappableAttribute
	public String getStatusBeheer() {
		return statusBeheer;
	}

    @MappableAttribute
	public void setStatusBeheer(String statusBeheer) {
		this.statusBeheer = statusBeheer;
	}

    @MappableAttribute
	public String getBeheerpakket() {
		return beheerpakket;
	}

    @MappableAttribute
	public void setBeheerpakket(String beheerpakket) {
		this.beheerpakket = beheerpakket;
	}

    @MappableAttribute
	public String getDoelBeheer() {
		return doelBeheer;
	}

    @MappableAttribute
	public void setDoelBeheer(String doelBeheer) {
		this.doelBeheer = doelBeheer;
	}

    @MappableAttribute
	public String getTypeBeheerder() {
		return typeBeheerder;
	}

    @MappableAttribute
	public void setTypeBeheerder(String typeBeheerder) {
		this.typeBeheerder = typeBeheerder;
	}

    @MappableAttribute
	public String getEenheidnummer() {
		return eenheidnummer;
	}

    @MappableAttribute
	public void setEenheidnummer(String eenheidnummer) {
		this.eenheidnummer = eenheidnummer;
	}


}
