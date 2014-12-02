
create table bron.bvb (
	id serial,
 	job_id bigint,
	gfid text,

	identificatie integer not null,
	gemeente integer not null,
	dossier text,
	bedrijfs_pchn text not null,
	gebouwnummer text,
	emissienummer text,
	stalnummer text,
	herkomst_xy text not null,
	beschikking text,
	datum_besluit text,
	status text,
	diercategorie text,
	rav_code text,
	additionele_techniek_rav text,
	dieraantal integer,
	nh3_factor decimal,
	gebouwhoogte decimal,
	diameter decimal,
	schoorsteenhoogte decimal,
	herkomst_ep_info text,
	
	primary key (id),
 	constraint fk_job_id foreign key (job_id) references manager.job (id)
);
SELECT AddGeometryColumn ('bron','bvb','geometry',28992,'POINT',2);
ALTER TABLE bron.bvb ALTER COLUMN "geometry" SET NOT NULL;

CREATE INDEX idx_bron_bvb_geometry ON bron.bvb USING GIST (geometry);
CREATE INDEX idx_bron_bvb_herkomst_xy ON bron.bvb (herkomst_xy);

GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER ON bron.bvb TO inspire;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE bron.bvb_id_seq TO inspire;
----------------------------------
