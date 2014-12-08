package nl.ipo.vrn.etl.theme;

import java.sql.Timestamp;

import com.vividsolutions.jts.geom.Geometry;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;

public abstract class AbstractGebied extends PersistableFeature {

	@Column(name = "begintijd")
	private Timestamp begintijd;

	@Column(name = "eindtijd")
	private Timestamp eindtijd;

	@Column(name = "identificatie")
	private String identificatie;

	@Column(name = "imna_bronhouder")
	private String imnaBronhouder;

	@Column(name = "contractnummer")
	private Integer contractnummer;

    @Column(name = "geometrie")
    private Geometry geometrie;
	
	@Column(name = "relatienummer")
	private Integer relatienummer;



}
