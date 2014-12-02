package nl.ipo.cds.etl.theme.monsterpunt;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.geometry.Geometry;

@Table(name = "monsterpunt", schema = "bron")
public class Monsterpunt extends PersistableFeature {

	@Column(name = "id_property")
	private Integer idProperty;

	@Column(name = "locatie")
	private Geometry locatie;

	@Column(name = "meting_omschrijving")
	private String metingOmschrijving;

	@Column(name = "zwemwaterlocatie_id")
	private Integer zwemwaterlocatieId;

	@Column(name = "waterbeheerder_code")
	private String waterbeheerderCode;

	@Column(name = "waterbeheerder_omschrijving")
	private String waterbeheerderOmschrijving;

	@Column(name = "type_code")
	private String typeCode;

	@Column(name = "type_omschrijving")
	private String typeOmschrijving;

	@MappableAttribute
	public Integer getIdProperty() {
		return idProperty;
	}

	@MappableAttribute
	public void setIdProperty(Integer idProperty) {
		this.idProperty = idProperty;
	}

	@MappableAttribute
	public Geometry getLocatie() {
		return locatie;
	}

	@MappableAttribute
	public void setLocatie(Geometry locatie) {
		this.locatie = locatie;
	}

	@MappableAttribute
	public String getMetingOmschrijving() {
		return metingOmschrijving;
	}

	@MappableAttribute
	public void setMetingOmschrijving(String metingOmschrijving) {
		this.metingOmschrijving = metingOmschrijving;
	}

	@MappableAttribute
	public Integer getZwemwaterlocatieId() {
		return zwemwaterlocatieId;
	}

	@MappableAttribute
	public void setZwemwaterlocatieId(Integer zwemwaterlocatieId) {
		this.zwemwaterlocatieId = zwemwaterlocatieId;
	}

	@MappableAttribute
	public String getWaterbeheerderCode() {
		return waterbeheerderCode;
	}

	@MappableAttribute
	public void setWaterbeheerderCode(String waterbeheerderCode) {
		this.waterbeheerderCode = waterbeheerderCode;
	}

	@MappableAttribute
	public String getWaterbeheerderOmschrijving() {
		return waterbeheerderOmschrijving;
	}

	@MappableAttribute
	public void setWaterbeheerderOmschrijving(String waterbeheerderOmschrijving) {
		this.waterbeheerderOmschrijving = waterbeheerderOmschrijving;
	}

	@MappableAttribute
	public String getTypeCode() {
		return typeCode;
	}

	@MappableAttribute
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@MappableAttribute
	public String getTypeOmschrijving() {
		return typeOmschrijving;
	}

	@MappableAttribute
	public void setTypeOmschrijving(String typeOmschrijving) {
		this.typeOmschrijving = typeOmschrijving;
	}

}
