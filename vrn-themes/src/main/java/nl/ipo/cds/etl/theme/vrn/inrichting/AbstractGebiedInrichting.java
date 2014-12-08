package nl.ipo.cds.etl.theme.vrn.inrichting;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.vrn.AbstractGebied;

@Table(name = "hazard_area", schema = "bron")
public abstract class AbstractGebiedInrichting extends AbstractGebied {

    @Column(name = "status_inrichting")
    private String statusInrichting;

    @Column(name = "doel_inrichting")
    private String doelInrichting;

    @Column(name = "type_beheerder")
    private String typeBeheerder;


}
