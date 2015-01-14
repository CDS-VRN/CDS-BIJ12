package nl.ipo.cds.etl.theme.vrn.domain;

import org.deegree.commons.tom.ows.CodeType;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_DOEL_REALISATIE;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_STATUS_INRICHTING;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_TYPE_BEHEERDER_EN_EIGENAAR;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

/**
 * @author annes
 * 
 * Baseclass for IMNa theme Inrichting
 *
 */
@Table
public abstract class AbstractGebiedInrichting extends AbstractGebied {

    @Column(name = "status_inrichting")
    private CodeType statusInrichting;

    @Column(name = "doelinrichting")
    private CodeType doelInrichting;

    @Column(name = "type_beheerder")
    private CodeType typeBeheerder;

    
    @MappableAttribute
    @CodeSpace(CODESPACE_STATUS_INRICHTING)
	public CodeType getStatusInrichting() {
		return statusInrichting;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_STATUS_INRICHTING)
	public void setStatusInrichting(CodeType statusInrichting) {
		this.statusInrichting = statusInrichting;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_DOEL_REALISATIE)
	public CodeType getDoelInrichting() {
		return doelInrichting;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_DOEL_REALISATIE)
	public void setDoelInrichting(CodeType doelInrichting) {
		this.doelInrichting = doelInrichting;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_TYPE_BEHEERDER_EN_EIGENAAR)
	public CodeType getTypeBeheerder() {
		return typeBeheerder;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_TYPE_BEHEERDER_EN_EIGENAAR)
	public void setTypeBeheerder(CodeType typeBeheerder) {
		this.typeBeheerder = typeBeheerder;
	}


}
