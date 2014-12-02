
create table bron.buisleidingen_transportroutedeel (
	id serial,
 	job_id bigint,
	gfid text, 	

	risicokaart_medewerker_naam text not null,
	transportroute_id character varying (30) not null,
 	transportroutedeel_id character varying (30) not null,
 	laatste_mutatiedatumtijd timestamp not null,
 	transportroute_naam text not null,
 	omschrijving character varying (240) not null,
 	buisleiding_type character varying (2) not null,
 	naam_eigenaar character varying (240) not null,
 	uitwendige_diameter integer not null,
 	wanddikte integer not null,
 	maximale_werkdruk integer not null,
 	ligging_bovenkant numeric not null,
 	materiaalsoort character varying (40) not null,
 	cas_nr_maatgevende_stof text not null,
 	transportroutedeel_toestand character varying (10) not null,
 	effectafstand_dodelijk integer,
 	maatgevend_scenario_dodelijk character varying (1),
 	
 	primary key (id),
 	constraint fk_job_id foreign key (job_id) references manager.job (id)
);
SELECT AddGeometryColumn ('bron','buisleidingen_transportroutedeel','geometrie',28992,'GEOMETRY',2);
ALTER TABLE bron.buisleidingen_transportroutedeel ALTER COLUMN "geometrie" SET NOT NULL;

create table bron.buisleidingen_transportrouterisico (
	id serial,
 	job_id bigint,
	gfid text, 	
 	
 	transportroute_id character varying (30) not null,
 	laatste_mutatiedatumtijd timestamp not null,
 	
 	primary key (id),
 	constraint fk_job_id foreign key (job_id) references manager.job (id)
);
SELECT AddGeometryColumn ('bron','buisleidingen_transportrouterisico','risicocontour10_6',28992,'GEOMETRY',2);
ALTER TABLE bron.buisleidingen_transportrouterisico ALTER COLUMN risicocontour10_6 SET NOT NULL;

create index bron_transportroutedeel_job on bron.buisleidingen_transportroutedeel (job_id);
create index bron_transportroutedeel_transportroute_id on bron.buisleidingen_transportroutedeel (transportroute_id);

GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER ON bron.buisleidingen_transportroutedeel TO inspire;
GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER ON bron.buisleidingen_transportrouterisico TO inspire;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE bron.buisleidingen_transportroutedeel_id_seq TO inspire;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE bron.buisleidingen_transportrouterisico_id_seq TO inspire;
----------------------------------
