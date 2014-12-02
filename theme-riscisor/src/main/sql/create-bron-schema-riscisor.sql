
create table bron.riscisor_kwetsbaar_object (
	id serial,
 	job_id bigint,
	gfid text,
	
	risicokaart_medewerker_naam text not null,
	extern_id character varying (36) not null,
	laatste_mutatiedatumtijd timestamp not null,
	instellingcode text not null,
	naam character varying (240),
	postcode character varying (6),
	huisnummer integer,
	huisletter character varying (1),
	huisnummertoevoeging character varying (4),
	identificatiecode_nummeraanduiding_bag character varying (16),
	autorisatiedatum timestamp,
	aantal_aanwezigen integer,
	aantal_bouwlagen integer,
	prevapcode text not null,
	prevap_prio character varying (8),
	
	primary key (id),
 	constraint fk_job_id foreign key (job_id) references manager.job (id)
);
SELECT AddGeometryColumn ('bron','riscisor_kwetsbaar_object','geometrie',28992,'POINT',2);
ALTER TABLE bron.riscisor_kwetsbaar_object ALTER COLUMN geometrie SET NOT NULL;

CREATE INDEX idx_bron_riscisor_kwetsbaar_object_geometrie ON bron.riscisor_kwetsbaar_object USING GIST (geometrie);

GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER ON bron.riscisor_kwetsbaar_object TO inspire;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE bron.riscisor_kwetsbaar_object_id_seq TO inspire;
----------------------------------
