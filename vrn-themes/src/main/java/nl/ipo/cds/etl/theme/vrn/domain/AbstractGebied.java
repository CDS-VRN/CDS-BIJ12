package nl.ipo.cds.etl.theme.vrn.domain;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_BRONHOUDER;

import java.math.BigInteger;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;

import com.vividsolutions.jts.io.ParseException;
import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.cs.coordinatesystems.ICRS;
import org.deegree.geometry.Geometry;
import org.deegree.geometry.io.WKBReader;
import org.deegree.geometry.io.WKBWriter;
import org.deegree.geometry.io.WKTWriter;
import org.deegree.portal.cataloguemanager.model.Abstract;


/**
 * @author annes
 * 
 *         Base class for all IMNa gebied classes and themes
 * 
 */
@Table
public abstract class AbstractGebied extends PersistableFeature implements Serializable {

	@Column(name = "begintijd")
	private Timestamp begintijd;

	@Column(name = "eindtijd")
	private Timestamp eindtijd;

	@Column(name = "identificatie")
	private String identificatie;

	@Column(name = "imna_bronhouder")
	private transient CodeType imnaBronhouder;

	@Column(name = "contractnummer")
	private BigInteger contractnummer;

    @Column(name = "geometrie")
    private transient Geometry geometrie;
	
	@Column(name = "relatienummer")
	private BigInteger relatienummer;

	/**
	 * Custom deserialization because Geometry type is not serializable by default, nor is CodeType.
	 * @param ois The input stream.
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, ParseException {
		// Read default serializable properties.
		ois.defaultReadObject();

		// Also serialize ID since this is not serializable from PersistentFeature. This is a hack.
		setId(ois.readUTF());
		imnaBronhouder = codeTypeReader(ois);

		// Read the Geometry with corresponding coordinate system.
		ICRS icrs = (ICRS)ois.readObject();
		geometrie = WKBReader.read(ois, icrs);
	}

	/**
	 * Custom serialization because deegree types are not serializable.
	 * @param oos The output stream.
	 * @throws IOException
	 * @throws ParseException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException, ParseException {
		// Write default serializable properties.
		oos.defaultWriteObject();


		oos.writeUTF(getId());
		codeTypeWriter(imnaBronhouder, oos);

		// Write Geometry and its coordinate system.
		oos.writeObject(geometrie.getCoordinateSystem());
		WKBWriter.write(geometrie, oos);
	}

	/**
	 * Helper method to serialize code type.
	 */
	protected void codeTypeWriter(CodeType codeType, ObjectOutputStream oos) throws IOException {
		oos.writeUTF(codeType.getCode());
		oos.writeUTF(codeType.getCodeSpace());
	}

	/**
	 * Helper method to deserialize code type.
	 */
	protected CodeType codeTypeReader(ObjectInputStream ois) throws IOException {
		String imnaCode = ois.readUTF();
		String imnaCodeSpace = ois.readUTF();
		return new CodeType(imnaCode, imnaCodeSpace);
	}


	public boolean equals(Object o) {
		if(!(o instanceof AbstractGebied)) {
			return false;
		}

        AbstractGebied that = (AbstractGebied)o;
        return this.getId().equals(that.getId()) &&
                this.getBegintijd().equals(that.getBegintijd()) &&
                this.getContractnummer().equals(that.getContractnummer()) &&
                this.getEindtijd().equals(that.getEindtijd()) &&
                this.getGeometrie().toString().equals(that.getGeometrie().toString()) &&
                this.getIdentificatie().equals(that.getIdentificatie()) &&
                this.getImnaBronhouder().equals(that.getImnaBronhouder()) &&
                this.getRelatienummer().equals(that.getRelatienummer());
	}

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

	/**
	 * This method is required for the AbstractGebiedExpression in the validation package to work correctly.
	 * It is not the nicest solution, but the most pragmatic thing to do (more info in the respective expression class).
	 * @return Itself.
	 */
	public AbstractGebied getAbstractGebied() {
		return this;
	}

}
