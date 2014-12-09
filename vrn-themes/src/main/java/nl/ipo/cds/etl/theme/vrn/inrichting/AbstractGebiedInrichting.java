package nl.ipo.cds.etl.theme.vrn.inrichting;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;
import nl.ipo.cds.etl.theme.vrn.AbstractGebied;

@Table(name = "hazard_area", schema = "bron")
public abstract class AbstractGebiedInrichting extends AbstractGebied {

    @Column(name = "status_inrichting")
    private String statusInrichting;

    @Column(name = "doel_inrichting")
    private String doelInrichting;

    @Column(name = "type_beheerder")
    private String typeBeheerder;

    
    @MappableAttribute
	public String getStatusInrichting() {
		return statusInrichting;
	}

    @MappableAttribute
	public void setStatusInrichting(String statusInrichting) {
		this.statusInrichting = statusInrichting;
	}

    @MappableAttribute
	public String getDoelInrichting() {
		return doelInrichting;
	}

    @MappableAttribute
	public void setDoelInrichting(String doelInrichting) {
		this.doelInrichting = doelInrichting;
	}

    @MappableAttribute
	public String getTypeBeheerder() {
		return typeBeheerder;
	}

    @MappableAttribute
	public void setTypeBeheerder(String typeBeheerder) {
		this.typeBeheerder = typeBeheerder;
	}


}
