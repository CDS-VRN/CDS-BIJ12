package nl.ipo.cds.etl.theme.vrn.verwerving;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;
import nl.ipo.cds.etl.theme.vrn.AbstractGebied;

@Table(name = "hazard_area", schema = "bron")
public abstract  class AbstractGebiedVerwerving extends AbstractGebied {

    @Column(name = "status_verwerving")
    private String statusVerwerving;

    @Column(name = "type_eigenaar")
    private String typeEigenaar;


	@Column(name = "doel_verwerving")
	 private String doelVerwerving;

	@MappableAttribute
    public String getStatusVerwerving() {
		return statusVerwerving;
	}

	@MappableAttribute
	public void setStatusVerwerving(String statusVerwerving) {
		this.statusVerwerving = statusVerwerving;
	}

	@MappableAttribute
	public String getTypeEigenaar() {
		return typeEigenaar;
	}

	@MappableAttribute
	public void setTypeEigenaar(String typeEigenaar) {
		this.typeEigenaar = typeEigenaar;
	}

	@MappableAttribute
	public String getDoelVerwerving() {
		return doelVerwerving;
	}

	@MappableAttribute
	public void setDoelVerwerving(String doelVerwerving) {
		this.doelVerwerving = doelVerwerving;
	}

}
