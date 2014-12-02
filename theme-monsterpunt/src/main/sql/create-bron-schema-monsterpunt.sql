
create table bron.monsterpunt (
	id serial,
 	job_id bigint,
	gfid text,

	id_property integer,
	meting_omschrijving text,
	zwemwaterlocatie_id integer,
	waterbeheerder_code text,
	waterbeheerder_omschrijving text,
	type_code text,
	type_omschrijving text,
	
	primary key (id),
 	constraint fk_job_id foreign key (job_id) references manager.job (id)
);
SELECT AddGeometryColumn ('bron','monsterpunt','locatie',28992,'GEOMETRY',2);

CREATE INDEX idx_bron_monsterpunt_locatie ON bron.monsterpunt USING GIST (locatie);

GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER ON bron.monsterpunt TO inspire;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE bron.monsterpunt_id_seq TO inspire;
----------------------------------