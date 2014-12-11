package nl.ipo.cds.etl.theme.vrn;

import java.sql.Timestamp;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import com.vividsolutions.jts.geom.Geometry;

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

	
	@MappableAttribute
	public Timestamp getBegintijd() {
		return begintijd;
	}
	
	@MappableAttribute
	public void setBegintijd(Timestamp begintijd) {
		this.begintijd = begintijd;
	}

	@MappableAttribute
	public Timestamp getEindtijd() {
		return eindtijd;
	}

	@MappableAttribute
	public void setEindtijd(Timestamp eindtijd) {
		this.eindtijd = eindtijd;
	}

	@MappableAttribute
	public String getIdentificatie() {
		return identificatie;
	}

	@MappableAttribute
	public void setIdentificatie(String identificatie) {
		this.identificatie = identificatie;
	}

	@MappableAttribute
	public String getImnaBronhouder() {
		return imnaBronhouder;
	}

	@MappableAttribute
	public void setImnaBronhouder(String imnaBronhouder) {
		this.imnaBronhouder = imnaBronhouder;
	}

	@MappableAttribute
	public Integer getContractnummer() {
		return contractnummer;
	}

	@MappableAttribute
	public void setContractnummer(Integer contractnummer) {
		this.contractnummer = contractnummer;
	}

	@MappableAttribute
	public Geometry getGeometrie() {
		return geometrie;
	}

	@MappableAttribute
	public void setGeometrie(Geometry geometrie) {
		this.geometrie = geometrie;
	}

	@MappableAttribute
	public Integer getRelatienummer() {
		return relatienummer;
	}
	
	@MappableAttribute
	public void setRelatienummer(Integer relatienummer) {
		this.relatienummer = relatienummer;
	}

}
