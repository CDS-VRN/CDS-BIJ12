package nl.ipo.cds.etl.theme.vrn.domain;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_BRONHOUDER;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;
import nl.ipo.cds.etl.theme.vrn.VrnThemeConfig;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.cs.persistence.CRSManager;
import org.deegree.cs.refs.coordinatesystem.CRSRef;
import org.deegree.geometry.Geometry;
import org.deegree.geometry.io.WKBReader;
import org.deegree.geometry.io.WKBWriter;
import org.deegree.geometry.multi.MultiSurface;
import org.deegree.geometry.primitive.Surface;

import com.vividsolutions.jts.io.ParseException;

/**
 * @author annes
 * 
 *         Base class for all IMNa gebied classes and themes
 * 
 */
@Table
public abstract class AbstractGebied extends PersistableFeature implements Serializable {

	private static CRSRef rdCrsRef = CRSManager.getCRSRef("EPSG:28992");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * 
	 * @param ois
	 *            The input stream.
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, ParseException {
		// Read default serializable properties.
		ois.defaultReadObject();

		// Also serialize ID since this is not serializable from PersistentFeature. This is a hack.
		setId(ois.readUTF());
		imnaBronhouder = codeTypeReader(ois);

		// Read the Geometry with corresponding coordinate system.
		// ICRS icrs = (ICRS)ois.readObject();
		// coordinate system not correctly deserializable (thanks to bug in deegree).
		// ignore it for now.
		geometrie = WKBReader.read(ois, null);
	}

	/**
	 * Custom serialization because deegree types are not serializable.
	 * 
	 * @param oos
	 *            The output stream.
	 * @throws IOException
	 * @throws ParseException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException, ParseException {
		// Write default serializable properties.
		oos.defaultWriteObject();

		oos.writeUTF(getId());
		codeTypeWriter(imnaBronhouder, oos);

		// Write Geometry and its coordinate system.
		// coordinate system not correctly deserializable (thanks to bug in deegree).
		// ignore it for now.
		// oos.writeObject(geometrie.getCoordinateSystem());
		WKBWriter.write(geometrie, oos);
	}

	/**
	 * Helper method to serialize code type.
	 */
	protected void codeTypeWriter(CodeType codeType, ObjectOutputStream oos) throws IOException {
		// check for null, for example beheerpakket can be null.
		if (codeType != null) {
			oos.writeUTF(codeType.getCode());
			oos.writeUTF(codeType.getCodeSpace());
		}
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
		if (!(o instanceof AbstractGebied)) {
			return false;
		}

		AbstractGebied that = (AbstractGebied) o;
		return this.getId().equals(that.getId()) && this.getBegintijd().equals(that.getBegintijd())
		&& this.getContractnummer().equals(that.getContractnummer()) && this.getEindtijd().equals(that.getEindtijd())
		&& this.getGeometrie().toString().equals(that.getGeometrie().toString()) && this.getIdentificatie().equals(that.getIdentificatie())
		&& this.getImnaBronhouder().equals(that.getImnaBronhouder()) && this.getRelatienummer().equals(that.getRelatienummer());
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
		// prevent null replacement of multisurface
		if (geometrie == null) {
			return;
		}
		// replace contained surface if needed
		// validator TODO now allows multisurfaces with single surface? 
		geometrie = extractSurface(geometrie);
		// hack #1 override ESRI SRID style RD
		for (String rdReplacement : VrnThemeConfig.getRdReplacements()) {
			if (geometrie != null && geometrie.getCoordinateSystem().toString().contains(rdReplacement)) {
				geometrie.setCoordinateSystem(rdCrsRef);
				continue;
			}
		}
		this.geometrie = geometrie;
	}

	/** Extra geometry mapping for multisurfaces, maybe getter not needed */
	@MappableAttribute
	public Geometry getGeometrieMultiSurface() {
		return geometrie;
	}

	/** Extra geometry mapping for multisurfaces */
	@MappableAttribute
	public void setGeometrieMultiSurface(Geometry geometrie) {
		this.setGeometrie(geometrie);
	}

	/**
	 * If geometrie is a MultiSurface containing a single Surface, return the extracted Surface; otherwise return the geometrie 'as is'.
	 * 
	 * @param geometrie
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Geometry extractSurface(Geometry geometrie) {
		// hack #2 convert multisurface
		if (geometrie instanceof MultiSurface) {
			MultiSurface<Surface> multiSurface = (MultiSurface<Surface>) geometrie;
			if (multiSurface.size() == 1) {
				return multiSurface.get(0);

			}
		}
		return geometrie;
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
	 * This method is required for the AbstractGebiedExpression in the validation package to work correctly. It is not the nicest solution, but the
	 * most pragmatic thing to do (more info in the respective expression class).
	 * 
	 * @return Itself.
	 */
	public AbstractGebied getAbstractGebied() {
		return this;
	}

	/**
	 * This method is needed for the validation of DoelRealisatie codes. Because the doel attribute consists of multiple codes, seperated by ';', we
	 * need access to the raw string value.
	 * 
	 * @return
	 */
	public abstract String getDoelRealisatieValue();

}
