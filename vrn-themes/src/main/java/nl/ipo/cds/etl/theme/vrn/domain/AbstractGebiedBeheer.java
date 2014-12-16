package nl.ipo.cds.etl.theme.vrn.domain;

import org.deegree.commons.tom.ows.CodeType;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

/**
 * @author annes
 * 
 * Baseclass for IMNa theme Beheer
 *
 */
@Table
public abstract class AbstractGebiedBeheer extends AbstractGebied {

    @Column(name = "status_beheer")
    private CodeType statusBeheer;

    @Column(name = "beheerpakket")
    private CodeType beheerpakket;

    @Column(name = "doel_beheer")
    private CodeType doelBeheer;

    @Column(name = "type_beheerder")
    private CodeType typeBeheerder;

    @Column(name="eenheidnummer")
    private String eenheidnummer;

    @MappableAttribute
    @CodeSpace("StatusBeheer")
	public CodeType getStatusBeheer() {
		return statusBeheer;
	}

    @MappableAttribute
    @CodeSpace("StatusBeheer")
	public void setStatusBeheer(CodeType statusBeheer) {
		this.statusBeheer = statusBeheer;
	}

    @MappableAttribute
    @CodeSpace("BeheerPakket")
	public CodeType getBeheerpakket() {
		return beheerpakket;
	}

    @MappableAttribute
    @CodeSpace("BeheerPakket")
	public void setBeheerpakket(CodeType beheerpakket) {
		this.beheerpakket = beheerpakket;
	}

    @MappableAttribute
    @CodeSpace("DoelRealisatie")
	public CodeType getDoelBeheer() {
		return doelBeheer;
	}

    @MappableAttribute
    @CodeSpace("DoelRealisatie")
	public void setDoelBeheer(CodeType doelBeheer) {
		this.doelBeheer = doelBeheer;
	}

    @MappableAttribute
    @CodeSpace("TypeBeheerderEnEigenaar")
	public CodeType getTypeBeheerder() {
		return typeBeheerder;
	}

    @MappableAttribute
    @CodeSpace("TypeBeheerderEnEigenaar")
	public void setTypeBeheerder(CodeType typeBeheerder) {
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
