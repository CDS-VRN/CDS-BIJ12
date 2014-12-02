package nl.ipo.cds.etl.theme.buisleidingen;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.geometry.Geometry;

@Table(name="buisleidingen_transportroutedeel", schema="bron")
public class Transportroutedeel extends AbstractBuisleidingenFeature {
	
	@Column (name = "risicokaart_medewerker_naam")
	private String risicokaartMedewerkerNaam;
	
	@Column (name = "transportroutedeel_id")
	private String transportroutedeelId;		// Max. 30, not null
	
	@Column (name = "transportroute_naam")
	private String transportrouteNaam;			// not null
	
	@Column (name = "omschrijving")
	private String omschrijving;				// Max. 240, not null
	
	@Column (name = "buisleiding_type")
	private String buisleidingType;				// Max. 2, not null
	
	@Column (name = "naam_eigenaar")
	private String naamEigenaar;				// Max. 240, not null
	
	@Column (name = "uitwendige_diameter")
	private Integer uitwendigeDiameter;			// not null
	
	@Column (name = "wanddikte")
	private Integer wanddikte;					// not null
	
	@Column (name = "maximale_werkdruk")
	private Integer maximaleWerkdruk;			// not null
	
	@Column (name = "geometrie")
	private Geometry geometrie;					// not null
	
	@Column (name = "ligging_bovenkant")
	private Double liggingBovenkant;			// not null
	
	@Column (name = "materiaalsoort")
	private String materiaalsoort;				// Max. 40, not null
	
	@Column (name = "cas_nr_maatgevende_stof")
	private CodeType casNrMaatgevendeStof;		// not null
	
	@Column (name = "transportroutedeel_toestand")
	private String transportroutedeelToestand;	// Max. 10, not null
	
	@Column (name = "effectafstand_dodelijk")
	private Integer effectafstandDodelijk;
	
	@Column (name = "maatgevend_scenario_dodelijk")
	private String maatgevendScenarioDodelijk;	// Max. 1
	
	@MappableAttribute
	public String getRisicokaartMedewerkerNaam () {
		return risicokaartMedewerkerNaam;
	}

	@MappableAttribute
	public void setRisicokaartMedewerkerNaam (final String risicokaartMedewerkerNaam) {
		this.risicokaartMedewerkerNaam = risicokaartMedewerkerNaam;
	}

	@MappableAttribute
	public String getTransportroutedeelId() {
		return transportroutedeelId;
	}

	@MappableAttribute
	public void setTransportroutedeelId(String transportroutedeelId) {
		this.transportroutedeelId = transportroutedeelId;
	}

	@MappableAttribute
	public String getTransportrouteNaam() {
		return transportrouteNaam;
	}

	@MappableAttribute
	public void setTransportrouteNaam(String transportrouteNaam) {
		this.transportrouteNaam = transportrouteNaam;
	}

	@MappableAttribute
	public String getOmschrijving() {
		return omschrijving;
	}

	@MappableAttribute
	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	@MappableAttribute
	public String getBuisleidingType() {
		return buisleidingType;
	}

	@MappableAttribute
	public void setBuisleidingType(String buisleidingType) {
		this.buisleidingType = buisleidingType;
	}

	@MappableAttribute
	public String getNaamEigenaar() {
		return naamEigenaar;
	}

	@MappableAttribute
	public void setNaamEigenaar(String naamEigenaar) {
		this.naamEigenaar = naamEigenaar;
	}

	@MappableAttribute
	public Integer getUitwendigeDiameter() {
		return uitwendigeDiameter;
	}

	@MappableAttribute
	public void setUitwendigeDiameter(Integer uitwendigeDiameter) {
		this.uitwendigeDiameter = uitwendigeDiameter;
	}

	@MappableAttribute
	public Integer getWanddikte() {
		return wanddikte;
	}

	@MappableAttribute
	public void setWanddikte(Integer wanddikte) {
		this.wanddikte = wanddikte;
	}

	@MappableAttribute
	public Integer getMaximaleWerkdruk() {
		return maximaleWerkdruk;
	}

	@MappableAttribute
	public void setMaximaleWerkdruk(Integer maximaleWerkdruk) {
		this.maximaleWerkdruk = maximaleWerkdruk;
	}

	@MappableAttribute
	public Geometry getGeometrie() {
		return geometrie;
	}

	@MappableAttribute
	public void setGeometrie (final Geometry geometrie) {
		this.geometrie = geometrie;
	}

	@MappableAttribute
	public Double getLiggingBovenkant() {
		return liggingBovenkant;
	}

	@MappableAttribute
	public void setLiggingBovenkant(Double liggingBovenkant) {
		this.liggingBovenkant = liggingBovenkant;
	}

	@MappableAttribute
	public String getMateriaalsoort() {
		return materiaalsoort;
	}

	@MappableAttribute
	public void setMateriaalsoort(String materiaalsoort) {
		this.materiaalsoort = materiaalsoort;
	}

	@MappableAttribute
	@CodeSpace ("http://www.risicokaart.nl/codes/stoffen")
	public CodeType getCasNrMaatgevendeStof() {
		return casNrMaatgevendeStof;
	}

	@MappableAttribute
	@CodeSpace ("http://www.risicokaart.nl/codes/stoffen")
	public void setCasNrMaatgevendeStof(CodeType casNrMaatgevendeStof) {
		this.casNrMaatgevendeStof = casNrMaatgevendeStof;
	}

	@MappableAttribute
	public String getTransportroutedeelToestand() {
		return transportroutedeelToestand;
	}

	@MappableAttribute
	public void setTransportroutedeelToestand(String transportroutedeelToestand) {
		this.transportroutedeelToestand = transportroutedeelToestand;
	}

	@MappableAttribute
	public Integer getEffectafstandDodelijk() {
		return effectafstandDodelijk;
	}

	@MappableAttribute
	public void setEffectafstandDodelijk(Integer effectafstandDodelijk) {
		this.effectafstandDodelijk = effectafstandDodelijk;
	}

	@MappableAttribute
	public String getMaatgevendScenarioDodelijk() {
		return maatgevendScenarioDodelijk;
	}

	@MappableAttribute
	public void setMaatgevendScenarioDodelijk(String maatgevendScenarioDodelijk) {
		this.maatgevendScenarioDodelijk = maatgevendScenarioDodelijk;
	}
}
