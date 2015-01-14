package nl.ipo.cds.etl.theme.vrn.domain;

import org.deegree.commons.tom.ows.CodeType;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_DOEL_REALISATIE;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_STATUS_VERWERVING;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_TYPE_BEHEERDER_EN_EIGENAAR;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

/**
 * @author annes
 *
 * Baseclass for IMNa theme verwerving
 */
@Table
public abstract  class AbstractGebiedVerwerving extends AbstractGebied {

    @Column(name = "status_verwerving")
    private CodeType statusVerwerving;

    @Column(name = "type_eigenaar")
    private CodeType typeEigenaar;


	@Column(name = "doelverwerving")
	 private CodeType doelVerwerving;

	@MappableAttribute
	@CodeSpace(CODESPACE_STATUS_VERWERVING)
    public CodeType getStatusVerwerving() {
		return statusVerwerving;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_STATUS_VERWERVING)
	public void setStatusVerwerving(CodeType statusVerwerving) {
		this.statusVerwerving = statusVerwerving;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_TYPE_BEHEERDER_EN_EIGENAAR)
	public CodeType getTypeEigenaar() {
		return typeEigenaar;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_TYPE_BEHEERDER_EN_EIGENAAR)
	public void setTypeEigenaar(CodeType typeEigenaar) {
		this.typeEigenaar = typeEigenaar;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_DOEL_REALISATIE)
	public CodeType getDoelVerwerving() {
		return doelVerwerving;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_DOEL_REALISATIE)
	public void setDoelVerwerving(CodeType doelRealisatie) {
		this.doelVerwerving = doelRealisatie;
	}

}
