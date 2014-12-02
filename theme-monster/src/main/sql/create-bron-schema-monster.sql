create table bron.monster (
	id serial,
 	job_id bigint,
	gfid text,

	id_property integer,
	monster_status text,
	naam text,
	object_begin_tijd timestamp,
	compartiment_code text,
	compartiment_omschrijving text,
	monsterpunt_id integer,
	
	primary key (id),
 	constraint fk_job_id foreign key (job_id) references manager.job (id)
);

GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER ON bron.monster TO inspire;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE bron.monster_id_seq TO inspire;
----------------------------------
