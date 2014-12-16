package nl.ipo.cds.etl.theme.vrn.domain;

import org.deegree.commons.tom.ows.CodeType;

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
public abstract class AbstractGebiedInrichting extends AbstractGebied {

    @Column(name = "status_inrichting")
    private CodeType statusInrichting;

    @Column(name = "doel_inrichting")
    private CodeType doelInrichting;

    @Column(name = "type_beheerder")
    private CodeType typeBeheerder;

    
    @MappableAttribute
    @CodeSpace("StatusInrichting")
	public CodeType getStatusInrichting() {
		return statusInrichting;
	}

    @MappableAttribute
    @CodeSpace("StatusInrichting")
	public void setStatusInrichting(CodeType statusInrichting) {
		this.statusInrichting = statusInrichting;
	}

    @MappableAttribute
    @CodeSpace("DoelRealisatie")
	public CodeType getDoelInrichting() {
		return doelInrichting;
	}

    @MappableAttribute
    @CodeSpace("DoelRealisatie")
	public void setDoelInrichting(CodeType doelInrichting) {
		this.doelInrichting = doelInrichting;
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


}
