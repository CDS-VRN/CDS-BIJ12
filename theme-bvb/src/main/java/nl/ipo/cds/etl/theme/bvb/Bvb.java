package nl.ipo.cds.etl.theme.bvb;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.geometry.Geometry;

@Table(name = "bvb", schema = "bron")
public class Bvb extends PersistableFeature {

	@Column(name = "identificatie")
	private Integer identificatie;

	@Column(name = "gemeente")
	private Integer gemeente;

	@Column(name = "dossier")
	private String dossier;

	@Column(name = "bedrijfs_pchn")
	private String bedrijfsPchn;

	@Column(name = "gebouwnummer")
	private String gebouwnummer;

	@Column(name = "emissienummer")
	private String emissienummer;

	@Column(name = "stalnummer")
	private String stalnummer;
	
	@Column(name = "geometry")
	private Geometry geometry;

	@Column(name = "herkomst_xy")
	private String herkomstXy;

	@Column(name = "beschikking")
	private String beschikking;
	
	@Column(name = "datum_besluit")
	private String datumBesluit;

	@Column(name = "status")
	private String status;

	@Column(name = "diercategorie")
	private String diercategorie;

	@Column(name = "rav_code")
	private String ravCode;
	
	@Column(name = "additionele_techniek_rav")
	private String additioneleTechniekRav;	

	@Column(name = "dieraantal")
	private Integer dieraantal;

	@Column(name = "nh3_factor")
	private Double nh3Factor;

	@Column(name = "gebouwhoogte")
	private Double gebouwhoogte;

	@Column(name = "diameter")
	private Double diameter;

	@Column(name = "schoorsteenhoogte")
	private Double schoorsteenhoogte;

	@Column(name = "herkomst_ep_info")
	private String herkomstEpInfo;

	@MappableAttribute
	public Integer getIdentificatie() {		
		return identificatie;
	}

	@MappableAttribute
	public void setIdentificatie(Integer identificatie) {
		this.identificatie = identificatie;
	}

	@MappableAttribute
	public Integer getGemeente() {
		return gemeente;
	}

	@MappableAttribute
	public void setGemeente(Integer gemeente) {
		this.gemeente = gemeente;
	}

	@MappableAttribute
	public String getDossier() {
		return dossier;
	}

	@MappableAttribute
	public void setDossier(String dossier) {
		this.dossier = dossier;
	}

	@MappableAttribute
	public String getBedrijfsPchn() {
		return bedrijfsPchn;
	}

	@MappableAttribute
	public void setBedrijfsPchn(String bedrijfsPchn) {
		this.bedrijfsPchn = bedrijfsPchn;
	}

	@MappableAttribute
	public String getGebouwnummer() {
		return gebouwnummer;
	}

	@MappableAttribute
	public void setGebouwnummer(String gebouwnummer) {
		this.gebouwnummer = gebouwnummer;
	}

	@MappableAttribute
	public String getEmissienummer() {
		return emissienummer;
	}

	@MappableAttribute
	public void setEmissienummer(String emissienummer) {
		this.emissienummer = emissienummer;
	}

	@MappableAttribute
	public String getStalnummer() {
		return stalnummer;
	}

	@MappableAttribute
	public void setStalnumme(String stalnummer) {
		this.stalnummer = stalnummer;
	}	
	
	@MappableAttribute
	public Geometry getGeometry() {
		return geometry;
	}

	@MappableAttribute
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	@MappableAttribute
	public String getHerkomstXy() {
		return herkomstXy;
	}

	@MappableAttribute
	public void setHerkomstXy(String herkomstXy) {
		this.herkomstXy = herkomstXy;
	}

	@MappableAttribute
	public String getBeschikking() {
		return beschikking;
	}

	@MappableAttribute
	public void setBeschikking(String beschikking) {
		this.beschikking = beschikking;
	}	
	
	@MappableAttribute
	public String getDatumBesluit() {
		return datumBesluit;
	}

	@MappableAttribute
	public void setDatumBesluit(String datumBesluit) {
		this.datumBesluit = datumBesluit;
	}

	@MappableAttribute
	public String getStatus() {
		return status;
	}

	@MappableAttribute
	public void setStatus(String status) {
		this.status = status;
	}

	@MappableAttribute
	public String getDiercategorie() {
		return diercategorie;
	}

	@MappableAttribute
	public void setDiercategorie(String diercategorie) {
		this.diercategorie = diercategorie;
	}

	@MappableAttribute
	public String getRavCode() {
		return ravCode;
	}

	@MappableAttribute
	public void setRavCode(String ravCode) {
		this.ravCode = ravCode;
	}

	@MappableAttribute
	public String getAdditioneleTechniekRav() {
		return additioneleTechniekRav;
	}

	@MappableAttribute
	public void setAdditioneleTechniekRav(String additioneleTechniekRav) {
		this.additioneleTechniekRav = additioneleTechniekRav;
	}	
	
	@MappableAttribute
	public Integer getDieraantal() {
		return dieraantal;
	}

	@MappableAttribute
	public void setDieraantal(Integer dieraantal) {
		this.dieraantal = dieraantal;
	}

	@MappableAttribute
	public Double getNh3Factor() {
		return nh3Factor;
	}

	@MappableAttribute
	public void setNh3Factor(Double nh3Factor) {
		this.nh3Factor = nh3Factor;
	}

	@MappableAttribute
	public Double getGebouwhoogte() {
		return gebouwhoogte;
	}

	@MappableAttribute
	public void setGebouwhoogte(Double gebouwhoogte) {
		this.gebouwhoogte = gebouwhoogte;
	}

	@MappableAttribute
	public Double getDiameter() {
		return diameter;
	}

	@MappableAttribute
	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	@MappableAttribute
	public Double getSchoorsteenhoogte() {
		return schoorsteenhoogte;
	}

	@MappableAttribute
	public void setSchoorsteenhoogte(Double schoorsteenhoogte) {
		this.schoorsteenhoogte = schoorsteenhoogte;
	}

	@MappableAttribute
	public String getHerkomstEpInfo() {
		return herkomstEpInfo;
	}

	@MappableAttribute
	public void setHerkomstEpInfo(String herkomstEpInfo) {
		this.herkomstEpInfo = herkomstEpInfo;
	}

}
