create table bron.resultaat (
	id serial,
 	job_id bigint,
	gfid text,

	id_property integer,
	"type" text,
	object_begin_tijd timestamp,
	datum_invoeren date,
	datum_gepubliceerd date,
	publiceren boolean,
	risico_niveau text,
	numerieke_waarde decimal,
	monster_id integer,
	eenheid_code text,
	eenheid_omschrijving text,
	taxon_code text,
	taxon_omschrijving text,
	kwaliteits_oordeel_code text,
	kwaliteits_oordeel_omschrijving text,
	waarde_bewerkings_methode_code text,
	waarde_bewerkings_methode_omschrijving text,
	object_code text,
	object_omschrijving text,
	typering_waarneming_code text,
	typering_waarneming_omschrijving text,
	limiet_symbool_code text,
	limiet_symbool_omschrijving text,
	hoedanigheid_code text,
	hoedanigheid_omschrijving text,
	waarde_bepalings_methode_code text,
	waarde_bepalings_methode_omschrijving text,
	chemische_stof_code text,
	chemische_stof_omschrijving text,
	grootheid_code text,
	grootheid_omschrijving text,
	
	primary key (id),
 	constraint fk_job_id foreign key (job_id) references manager.job (id)
);

GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER ON bron.resultaat TO inspire;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE bron.resultaat_id_seq TO inspire;
----------------------------------