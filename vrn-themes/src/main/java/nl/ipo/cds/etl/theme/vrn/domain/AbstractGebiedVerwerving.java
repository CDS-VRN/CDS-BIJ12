package nl.ipo.cds.etl.theme.vrn.domain;

import org.deegree.commons.tom.ows.CodeType;

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
	 private CodeType doelRealisatie;

	@MappableAttribute
	@CodeSpace("StatusVerwerving")
    public CodeType getStatusVerwerving() {
		return statusVerwerving;
	}

	@MappableAttribute
	@CodeSpace("StatusVerwerving")
	public void setStatusVerwerving(CodeType statusVerwerving) {
		this.statusVerwerving = statusVerwerving;
	}

	@MappableAttribute
	@CodeSpace("TypeBeheerderEnEigenaar")
	public CodeType getTypeEigenaar() {
		return typeEigenaar;
	}

	@MappableAttribute
	@CodeSpace("TypeBeheerderEnEigenaar")
	public void setTypeEigenaar(CodeType typeEigenaar) {
		this.typeEigenaar = typeEigenaar;
	}

	@MappableAttribute
	@CodeSpace("DoelRealisatie")
	public CodeType getDoelRealisatie() {
		return doelRealisatie;
	}

	@MappableAttribute
	@CodeSpace("DoelRealisatie")
	public void setDoelRealisatie(CodeType doelRealisatie) {
		this.doelRealisatie = doelRealisatie;
	}

}
