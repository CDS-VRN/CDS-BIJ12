package nl.ipo.cds.etl.theme.vrn.domain;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_BEHEER_PAKKET;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_DOEL_REALISATIE;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_STATUS_BEHEER;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_TYPE_BEHEERDER;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.commons.tom.ows.CodeType;

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

    @Column(name = "doelbeheer")
    private CodeType doelBeheer;

    @Column(name = "type_beheerder")
    private CodeType typeBeheerder;

    @Column(name="eenheidnummer")
    private String eenheidnummer;

    @MappableAttribute
    @CodeSpace(CODESPACE_STATUS_BEHEER)
	public CodeType getStatusBeheer() {
		return statusBeheer;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_STATUS_BEHEER)
	public void setStatusBeheer(CodeType statusBeheer) {
		this.statusBeheer = statusBeheer;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_BEHEER_PAKKET)
	public CodeType getBeheerpakket() {
		return beheerpakket;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_BEHEER_PAKKET)
	public void setBeheerpakket(CodeType beheerpakket) {
		this.beheerpakket = beheerpakket;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_DOEL_REALISATIE)
	public CodeType getDoelBeheer() {
		return doelBeheer;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_DOEL_REALISATIE)
	public void setDoelBeheer(CodeType doelBeheer) {
		this.doelBeheer = doelBeheer;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_TYPE_BEHEERDER)
	public CodeType getTypeBeheerder() {
		return typeBeheerder;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_TYPE_BEHEERDER)
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
