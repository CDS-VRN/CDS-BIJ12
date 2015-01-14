package nl.ipo.cds.etl.theme.vrn.domain;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_BRONHOUDER;

import java.math.BigInteger;
import java.sql.Timestamp;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.geometry.Geometry;

/**
 * @author annes
 * 
 *         Base class for all IMNa gebied classes and themes
 * 
 */
@Table
public abstract class AbstractGebied extends PersistableFeature {

	@Column(name = "begintijd")
	private Timestamp begintijd;

	@Column(name = "eindtijd")
	private Timestamp eindtijd;

	@Column(name = "identificatie")
	private String identificatie;

	@Column(name = "imna_bronhouder")
	private CodeType imnaBronhouder;

	@Column(name = "contractnummer")
	private BigInteger contractnummer;

	@Column(name = "geometrie")
	private Geometry geometrie;

	@Column(name = "relatienummer")
	private BigInteger relatienummer;

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
	@CodeSpace(CODESPACE_BRONHOUDER)
	public CodeType getImnaBronhouder() {
		return imnaBronhouder;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_BRONHOUDER)
	public void setImnaBronhouder(CodeType imnaBronhouder) {
		this.imnaBronhouder = imnaBronhouder;
	}

	@MappableAttribute
	public BigInteger getContractnummer() {
		return contractnummer;
	}

	@MappableAttribute
	public void setContractnummer(BigInteger contractnummer) {
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
	public BigInteger getRelatienummer() {
		return relatienummer;
	}

	@MappableAttribute
	public void setRelatienummer(BigInteger relatienummer) {
		this.relatienummer = relatienummer;
	}

}
