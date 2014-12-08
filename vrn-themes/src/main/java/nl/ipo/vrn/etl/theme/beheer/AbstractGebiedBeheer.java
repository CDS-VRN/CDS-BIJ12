package nl.ipo.vrn.etl.theme.beheer;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.vrn.etl.theme.AbstractGebied;

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


}
