package nl.ipo.cds.etl.theme.resultaat;

import java.sql.Date;
import java.sql.Timestamp;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

@Table(name = "resultaat", schema = "bron")
public class Resultaat extends PersistableFeature {

	@Column(name = "id_property")
	private Integer idProperty;

	@Column(name = "type")
	private String type;

	@Column(name = "object_begin_tijd")
	private Timestamp objectBeginTijd;

	@Column(name = "datum_invoeren")
	private Date datumInvoeren;

	@Column(name = "datum_gepubliceerd")
	private Date datumGepubliceerd;

	@Column(name = "publiceren")
	private Boolean publiceren;

	@Column(name = "risico_niveau")
	private String risicoNiveau;

	@Column(name = "numerieke_waarde")
	private Double numeriekeWaarde;

	@Column(name = "monster_id")
	private Integer monsterId;

	@Column(name = "eenheid_code")
	private String eenheidCode;

	@Column(name = "eenheid_omschrijving")
	private String eenheidOmschrijving;

	@Column(name = "taxon_code")
	private String taxonCode;

	@Column(name = "taxon_omschrijving")
	private String taxonOmschrijving;

	@Column(name = "kwaliteits_oordeel_code")
	private String kwaliteitsOordeelCode;

	@Column(name = "kwaliteits_oordeel_omschrijving")
	private String kwaliteitsOordeelOmschrijving;

	@Column(name = "waarde_bewerkings_methode_code")
	private String waardeBewerkingsMethodeCode;

	@Column(name = "waarde_bewerkings_methode_omschrijving")
	private String waardeBewerkingsMethodeOmschrijving;

	@Column(name = "object_code")
	private String objectCode;

	@Column(name = "object_omschrijving")
	private String objectOmschrijving;

	@Column(name = "typering_waarneming_code")
	private String typeringWaarnemingCode;

	@Column(name = "typering_waarneming_omschrijving")
	private String typeringWaarnemingOmschrijving;

	@Column(name = "limiet_symbool_code")
	private String limietSymboolCode;

	@Column(name = "limiet_symbool_omschrijving")
	private String limietSymboolOmschrijving;

	@Column(name = "hoedanigheid_code")
	private String hoedanigheidCode;

	@Column(name = "hoedanigheid_omschrijving")
	private String hoedanigheidOmschrijving;

	@Column(name = "waarde_bepalings_methode_code")
	private String waardeBepalingsMethodeCode;

	@Column(name = "waarde_bepalings_methode_omschrijving")
	private String waardeBepalingsMethodeOmschrijving;

	@Column(name = "chemische_stof_code")
	private String chemischeStofCode;

	@Column(name = "chemische_stof_omschrijving")
	private String chemischeStofOmschrijving;

	@Column(name = "grootheid_code")
	private String grootheidCode;

	@Column(name = "grootheid_omschrijving")
	private String grootheidOmschrijving;

	@MappableAttribute
	public Integer getIdProperty() {
		return idProperty;
	}

	@MappableAttribute
	public void setIdProperty(Integer idProperty) {
		this.idProperty = idProperty;
	}

	@MappableAttribute
	public String getType() {
		return type;
	}

	@MappableAttribute
	public void setType(String type) {
		this.type = type;
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
	public Date getDatumInvoeren() {
		return datumInvoeren;
	}

	@MappableAttribute
	public void setDatumInvoeren(Date datumInvoeren) {
		this.datumInvoeren = datumInvoeren;
	}

	@MappableAttribute
	public Date getDatumGepubliceerd() {
		return datumGepubliceerd;
	}

	@MappableAttribute
	public void setDatumGepubliceerd(Date datumGepubliceerd) {
		this.datumGepubliceerd = datumGepubliceerd;
	}

	@MappableAttribute
	public Boolean getPubliceren() {
		return publiceren;
	}

	@MappableAttribute
	public void setPubliceren(Boolean publiceren) {
		this.publiceren = publiceren;
	}

	@MappableAttribute
	public String getRisicoNiveau() {
		return risicoNiveau;
	}

	@MappableAttribute
	public void setRisicoNiveau(String risicoNiveau) {
		this.risicoNiveau = risicoNiveau;
	}

	@MappableAttribute
	public Double getNumeriekeWaarde() {
		return numeriekeWaarde;
	}

	@MappableAttribute
	public void setNumeriekeWaarde(Double numeriekeWaarde) {
		this.numeriekeWaarde = numeriekeWaarde;
	}

	@MappableAttribute
	public Integer getMonsterId() {
		return monsterId;
	}

	@MappableAttribute
	public void setMonsterId(Integer monsterId) {
		this.monsterId = monsterId;
	}

	@MappableAttribute
	public String getEenheidCode() {
		return eenheidCode;
	}

	@MappableAttribute
	public void setEenheidCode(String eenheidCode) {
		this.eenheidCode = eenheidCode;
	}

	@MappableAttribute
	public String getEenheidOmschrijving() {
		return eenheidOmschrijving;
	}

	@MappableAttribute
	public void setEenheidOmschrijving(String eenheidOmschrijving) {
		this.eenheidOmschrijving = eenheidOmschrijving;
	}

	@MappableAttribute
	public String getTaxonCode() {
		return taxonCode;
	}

	@MappableAttribute
	public void setTaxonCode(String taxonCode) {
		this.taxonCode = taxonCode;
	}

	@MappableAttribute
	public String getTaxonOmschrijving() {
		return taxonOmschrijving;
	}

	@MappableAttribute
	public void setTaxonOmschrijving(String taxonOmschrijving) {
		this.taxonOmschrijving = taxonOmschrijving;
	}

	@MappableAttribute
	public String getKwaliteitsOordeelCode() {
		return kwaliteitsOordeelCode;
	}

	@MappableAttribute
	public void setKwaliteitsOordeelCode(String kwaliteitsOordeelCode) {
		this.kwaliteitsOordeelCode = kwaliteitsOordeelCode;
	}

	@MappableAttribute
	public String getKwaliteitsOordeelOmschrijving() {
		return kwaliteitsOordeelOmschrijving;
	}

	@MappableAttribute
	public void setKwaliteitsOordeelOmschrijving(
			String kwaliteitsOordeelOmschrijving) {
		this.kwaliteitsOordeelOmschrijving = kwaliteitsOordeelOmschrijving;
	}

	@MappableAttribute
	public String getWaardeBewerkingsMethodeCode() {
		return waardeBewerkingsMethodeCode;
	}

	@MappableAttribute
	public void setWaardeBewerkingsMethodeCode(
			String waardeBewerkingsMethodeCode) {
		this.waardeBewerkingsMethodeCode = waardeBewerkingsMethodeCode;
	}

	@MappableAttribute
	public String getWaardeBewerkingsMethodeOmschrijving() {
		return waardeBewerkingsMethodeOmschrijving;
	}

	@MappableAttribute
	public void setWaardeBewerkingsMethodeOmschrijving(
			String waardeBewerkingsMethodeOmschrijving) {
		this.waardeBewerkingsMethodeOmschrijving = waardeBewerkingsMethodeOmschrijving;
	}

	@MappableAttribute
	public String getObjectCode() {
		return objectCode;
	}

	@MappableAttribute
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	@MappableAttribute
	public String getObjectOmschrijving() {
		return objectOmschrijving;
	}

	@MappableAttribute
	public void setObjectOmschrijving(String objectOmschrijving) {
		this.objectOmschrijving = objectOmschrijving;
	}

	@MappableAttribute
	public String getTyperingWaarnemingCode() {
		return typeringWaarnemingCode;
	}

	@MappableAttribute
	public void setTyperingWaarnemingCode(String typeringWaarnemingCode) {
		this.typeringWaarnemingCode = typeringWaarnemingCode;
	}

	@MappableAttribute
	public String getTyperingWaarnemingOmschrijving() {
		return typeringWaarnemingOmschrijving;
	}

	@MappableAttribute
	public void setTyperingWaarnemingOmschrijving(
			String typeringWaarnemingOmschrijving) {
		this.typeringWaarnemingOmschrijving = typeringWaarnemingOmschrijving;
	}

	@MappableAttribute
	public String getLimietSymboolCode() {
		return limietSymboolCode;
	}

	@MappableAttribute
	public void setLimietSymboolCode(String limietSymboolCode) {
		this.limietSymboolCode = limietSymboolCode;
	}

	@MappableAttribute
	public String getLimietSymboolOmschrijving() {
		return limietSymboolOmschrijving;
	}

	@MappableAttribute
	public void setLimietSymboolOmschrijving(String limietSymboolOmschrijving) {
		this.limietSymboolOmschrijving = limietSymboolOmschrijving;
	}

	@MappableAttribute
	public String getHoedanigheidCode() {
		return hoedanigheidCode;
	}

	@MappableAttribute
	public void setHoedanigheidCode(String hoedanigheidCode) {
		this.hoedanigheidCode = hoedanigheidCode;
	}

	@MappableAttribute
	public String getHoedanigheidOmschrijving() {
		return hoedanigheidOmschrijving;
	}

	@MappableAttribute
	public void setHoedanigheidOmschrijving(String hoedanigheidOmschrijving) {
		this.hoedanigheidOmschrijving = hoedanigheidOmschrijving;
	}

	@MappableAttribute
	public String getWaardeBepalingsMethodeCode() {
		return waardeBepalingsMethodeCode;
	}

	@MappableAttribute
	public void setWaardeBepalingsMethodeCode(String waardeBepalingsMethodeCode) {
		this.waardeBepalingsMethodeCode = waardeBepalingsMethodeCode;
	}

	@MappableAttribute
	public String getWaardeBepalingsMethodeOmschrijving() {
		return waardeBepalingsMethodeOmschrijving;
	}

	@MappableAttribute
	public void setWaardeBepalingsMethodeOmschrijving(
			String waardeBepalingsMethodeOmschrijving) {
		this.waardeBepalingsMethodeOmschrijving = waardeBepalingsMethodeOmschrijving;
	}

	@MappableAttribute
	public String getChemischeStofCode() {
		return chemischeStofCode;
	}

	@MappableAttribute
	public void setChemischeStofCode(String chemischeStofCode) {
		this.chemischeStofCode = chemischeStofCode;
	}

	@MappableAttribute
	public String getChemischeStofOmschrijving() {
		return chemischeStofOmschrijving;
	}

	@MappableAttribute
	public void setChemischeStofOmschrijving(String chemischeStofOmschrijving) {
		this.chemischeStofOmschrijving = chemischeStofOmschrijving;
	}

	@MappableAttribute
	public String getGrootheidCode() {
		return grootheidCode;
	}

	@MappableAttribute
	public void setGrootheidCode(String grootheidCode) {
		this.grootheidCode = grootheidCode;
	}

	@MappableAttribute
	public String getGrootheidOmschrijving() {
		return grootheidOmschrijving;
	}

	@MappableAttribute
	public void setGrootheidOmschrijving(String grootheidOmschrijving) {
		this.grootheidOmschrijving = grootheidOmschrijving;
	}

}
