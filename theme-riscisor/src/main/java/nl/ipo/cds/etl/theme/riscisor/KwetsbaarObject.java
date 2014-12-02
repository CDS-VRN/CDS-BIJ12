package nl.ipo.cds.etl.theme.riscisor;

import java.sql.Timestamp;

import nl.ipo.cds.etl.PersistableFeature;
import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.geometry.Geometry;

@Table(name="riscisor_kwetsbaar_object", schema="bron")
public class KwetsbaarObject extends PersistableFeature {

	@Column (name = "risicokaart_medewerker_naam")
	private String risicokaartMedewerkerNaam;
	
	@Column (name = "extern_id")
	private String externId;
	
	@Column (name = "laatste_mutatiedatumtijd")
	private Timestamp laatsteMutatiedatumtijd;
	
	@Column (name = "instellingcode")
	private CodeType instellingcode;
	
	@Column (name = "naam")
	private String naam;
	
	@Column (name = "postcode")
	private String postcode;
	
	@Column (name = "huisnummer")
	private Integer huisnummer;
	
	@Column (name = "huisletter")
	private String huisletter;
	
	@Column (name = "huisnummertoevoeging")
	private String huisnummertoevoeging;
	
	@Column (name = "identificatiecode_nummeraanduiding_bag")
	private String identificatiecodeNummeraanduidingBAG;
	
	@Column (name = "geometrie")
	private Geometry geometrie;
	
	@Column (name = "autorisatiedatum")
	private Timestamp autorisatiedatum;
	
	@Column (name = "aantal_aanwezigen")
	private Integer aantalAanwezigen;
	
	@Column (name = "aantal_bouwlagen")
	private Integer aantalBouwlagen;
	
	@Column (name = "prevapcode")
	private CodeType prevapcode;
	
	@Column (name = "prevap_prio")
	private String prevapPrio;
	
	@MappableAttribute
	public String getRisicokaartMedewerkerNaam () {
		return risicokaartMedewerkerNaam;
	}

	@MappableAttribute
	public void setRisicokaartMedewerkerNaam (final String risicokaartMedewerkerNaam) {
		this.risicokaartMedewerkerNaam = risicokaartMedewerkerNaam;
	}

	@MappableAttribute
	public String getExternId () {
		return externId;
	}
	
	@MappableAttribute
	public void setExternId (final String externId) {
		this.externId = externId;
	}
	
	@MappableAttribute
	public Timestamp getLaatsteMutatiedatumtijd () {
		return laatsteMutatiedatumtijd;
	}
	
	@MappableAttribute
	public void setLaatsteMutatiedatumtijd (final Timestamp laatsteMutatiedatumtijd) {
		this.laatsteMutatiedatumtijd = laatsteMutatiedatumtijd;
	}
	
	@MappableAttribute
	@CodeSpace ("http://www.risicokaart.nl/codes/instellingen")
	public CodeType getInstellingcode () {
		return instellingcode;
	}
	
	@MappableAttribute
	@CodeSpace ("http://www.risicokaart.nl/codes/instellingen")
	public void setInstellingcode (final CodeType instellingcode) {
		this.instellingcode = instellingcode;
	}
	
	@MappableAttribute
	public String getNaam() {
		return naam;
	}
	
	@MappableAttribute
	public void setNaam (final String naam) {
		this.naam = naam;
	}
	
	@MappableAttribute
	public String getPostcode () {
		return postcode;
	}
	
	@MappableAttribute
	public void setPostcode (final String postcode) {
		this.postcode = postcode;
	}
	
	@MappableAttribute
	public Integer getHuisnummer () {
		return huisnummer;
	}
	
	@MappableAttribute
	public void setHuisnummer (final Integer huisnummer) {
		this.huisnummer = huisnummer;
	}
	
	@MappableAttribute
	public String getHuisletter() {
		return huisletter;
	}
	
	@MappableAttribute
	public void setHuisletter (final String huisletter) {
		this.huisletter = huisletter;
	}
	
	@MappableAttribute
	public String getHuisnummertoevoeging () {
		return huisnummertoevoeging;
	}
	
	@MappableAttribute
	public void setHuisnummertoevoeging (final String huisnummertoevoeging) {
		this.huisnummertoevoeging = huisnummertoevoeging;
	}
	
	@MappableAttribute
	public String getIdentificatiecodeNummeraanduidingBAG () {
		return identificatiecodeNummeraanduidingBAG;
	}
	
	@MappableAttribute
	public void setIdentificatiecodeNummeraanduidingBAG (final String identificatiecodeNummeraanduidingBAG) {
		this.identificatiecodeNummeraanduidingBAG = identificatiecodeNummeraanduidingBAG;
	}
	
	@MappableAttribute
	public Geometry getGeometrie () {
		return geometrie;
	}
	
	@MappableAttribute
	public void setGeometrie (final Geometry geometrie) {
		this.geometrie = geometrie;
	}
	
	@MappableAttribute
	public Timestamp getAutorisatiedatum () {
		return new Timestamp (autorisatiedatum.getTime ());
	}
	
	@MappableAttribute
	public void setAutorisatiedatum (final Timestamp autorisatiedatum) {
		this.autorisatiedatum = new Timestamp (autorisatiedatum.getTime ());
	}
	
	@MappableAttribute
	public Integer getAantalAanwezigen () {
		return aantalAanwezigen;
	}
	
	@MappableAttribute
	public void setAantalAanwezigen (final Integer aantalAanwezigen) {
		this.aantalAanwezigen = aantalAanwezigen;
	}
	
	@MappableAttribute
	public Integer getAantalBouwlagen () {
		return aantalBouwlagen;
	}
	
	@MappableAttribute
	public void setAantalBouwlagen (final Integer aantalBouwlagen) {
		this.aantalBouwlagen = aantalBouwlagen;
	}
	
	@MappableAttribute
	@CodeSpace ("http://www.risicokaart.nl/codes/prevapcodes")
	public CodeType getPrevapcode () {
		return prevapcode;
	}
	
	@MappableAttribute
	@CodeSpace ("http://www.risicokaart.nl/codes/prevapcodes")
	public void setPrevapcode (final CodeType prevapcode) {
		this.prevapcode = prevapcode;
	}
	
	@MappableAttribute
	public String getPrevapPrio () {
		return prevapPrio;
	}
	
	@MappableAttribute
	public void setPrevapPrio (final String prevapPrio) {
		this.prevapPrio = prevapPrio;
	}
}
