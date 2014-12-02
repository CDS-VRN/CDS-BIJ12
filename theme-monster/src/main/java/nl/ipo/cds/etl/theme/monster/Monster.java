package nl.ipo.cds.etl.theme.monster;

import java.sql.Timestamp;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

@Table(name = "monster", schema = "bron")
public class Monster extends PersistableFeature {

	@Column(name = "id_property")
	private Integer idProperty;

	@Column(name = "monster_status")
	private String monsterStatus;

	@Column(name = "naam")
	private String naam;

	@Column(name = "object_begin_tijd")
	private Timestamp objectBeginTijd;

	@Column(name = "compartiment_code")
	private String compartimentCode;

	@Column(name = "compartiment_omschrijving")
	private String compartimentOmschrijving;

	@Column(name = "monsterpunt_id")
	private Integer monsterpuntId;

	@MappableAttribute
	public Integer getIdProperty() {
		return idProperty;
	}

	@MappableAttribute
	public void setIdProperty(Integer idProperty) {
		this.idProperty = idProperty;
	}

	@MappableAttribute
	public String getMonsterStatus() {
		return monsterStatus;
	}

	@MappableAttribute
	public void setMonsterStatus(String monsterStatus) {
		this.monsterStatus = monsterStatus;
	}

	@MappableAttribute
	public String getNaam() {
		return naam;
	}

	@MappableAttribute
	public void setNaam(String naam) {
		this.naam = naam;
	}

	@MappableAttribute
	public Timestamp getObjectBeginTijd() {
		return objectBeginTijd;
	}

	@MappableAttribute
	public void setObjectBeginTijd(Timestamp objectBeginTijd) {
		this.objectBeginTijd = objectBeginTijd;
	}

	@MappableAttribute
	public String getCompartimentCode() {
		return compartimentCode;
	}

	@MappableAttribute
	public void setCompartimentCode(String compartimentCode) {
		this.compartimentCode = compartimentCode;
	}

	@MappableAttribute
	public String getCompartimentOmschrijving() {
		return compartimentOmschrijving;
	}

	@MappableAttribute
	public void setCompartimentOmschrijving(String compartimentOmschrijving) {
		this.compartimentOmschrijving = compartimentOmschrijving;
	}

	@MappableAttribute
	public Integer getMonsterpuntId() {
		return monsterpuntId;
	}

	@MappableAttribute
	public void setMonsterpuntId(Integer monsterpuntId) {
		this.monsterpuntId = monsterpuntId;
	}

}
