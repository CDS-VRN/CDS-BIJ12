-- CREATE VRN SCHEMA
create schema vrn;
-- GRANT VRN SCHEMA
GRANT USAGE ON SCHEMA vrn TO inspire;
-- ---------------------------------
CREATE TABLE vrn.gebiedbeheer_landelijk ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	job_id bigint,
	gfid text,
	beheerpakket text,
	status_beheer text NOT NULL,
	doelbeheer text NOT NULL,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	eenheidnummer text,
	geometrie geometry NOT NULL,
	relatienummer integer,
	type_beheerder text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;
COMMENT ON TABLE vrn.gebiedbeheer_landelijk
    IS 'provincie aan BIJ12'
;

CREATE TABLE vrn.gebiedbeheer_landelijk_tagged ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	tag text NOT NULL,
	job_id bigint,
	gfid text,
	beheerpakket text,
	status_beheer text NOT NULL,
	doelbeheer text NOT NULL,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	eenheidnummer text,
	geometrie geometry NOT NULL,
	relatienummer integer,
	type_beheerder text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;
COMMENT ON TABLE vrn.gebiedbeheer_landelijk_tagged
    IS 'provincie aan BIJ12'
;

CREATE TABLE vrn.gebiedbeheer_provinciaal ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	job_id bigint,
	gfid text,
	beheerpakket text,
	status_beheer text NOT NULL,
	doelbeheer text NOT NULL,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	eenheidnummer text,
	geometrie geometry NOT NULL,
	relatienummer integer,
	type_beheerder text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;
COMMENT ON TABLE vrn.gebiedbeheer_provinciaal
    IS 'natuurbeheerder/RVO aan provincie'
;

CREATE TABLE vrn.gebiedinrichting_landelijk ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	job_id bigint,
	gfid text,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	geometrie geometry NOT NULL,
	relatienummer integer,
	status_inrichting text NOT NULL,
	doelinrichting text,
	type_beheerder text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;

CREATE TABLE vrn.gebiedinrichting_landelijk_tagged ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	tag text NOT NULL,
	job_id bigint,
	gfid text,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	geometrie geometry NOT NULL,
	relatienummer integer,
	status_inrichting text NOT NULL,
	doelinrichting text,
	type_beheerder text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;

CREATE TABLE vrn.gebiedinrichting_provinciaal ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	job_id bigint,
	gfid text,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	geometrie geometry NOT NULL,
	relatienummer integer,
	status_inrichting text NOT NULL,
	doelinrichting text NOT NULL,
	type_beheerder text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;

CREATE TABLE vrn.gebiedverwerving_landelijk ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	job_id bigint,
	gfid text,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	geometrie geometry NOT NULL,
	relatienummer integer,
	status_verwerving text NOT NULL,
	doelverwerving text,
	type_eigenaar text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;

CREATE TABLE vrn.gebiedverwerving_landelijk_tagged ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	tag text NOT NULL,
	job_id bigint,
	gfid text,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	geometrie geometry NOT NULL,
	relatienummer integer,
	status_verwerving text NOT NULL,
	doelverwerving text,
	type_eigenaar text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;

CREATE TABLE vrn.gebiedverwerving_provinciaal ( 
	id serial NOT NULL,
	identificatie text NOT NULL,
	job_id bigint,
	gfid text,
	imna_bronhouder text NOT NULL,
	contractnummer integer,
	geometrie geometry NOT NULL,
	relatienummer integer,
	status_verwerving text NOT NULL,
	doelverwerving text,
	type_eigenaar text NOT NULL,
	begintijd timestamp NOT NULL,
	eindtijd timestamp
)
;


ALTER TABLE vrn.gebiedbeheer_landelijk ADD CONSTRAINT PK_gebiedbeheer_landelijk 
	PRIMARY KEY (id)
;


ALTER TABLE vrn.gebiedbeheer_landelijk_tagged ADD CONSTRAINT PK_gebiedbeheer_tagged 
	PRIMARY KEY (id)
;


ALTER TABLE vrn.gebiedbeheer_provinciaal ADD CONSTRAINT PK_gebiedbeheer_provinciaal 
	PRIMARY KEY (id)
;


ALTER TABLE vrn.gebiedinrichting_landelijk ADD CONSTRAINT PK_gebiedinrichting_landelijk 
	PRIMARY KEY (id)
;


ALTER TABLE vrn.gebiedinrichting_landelijk_tagged ADD CONSTRAINT PK_gebiedinrichting_tagged 
	PRIMARY KEY (id)
;


ALTER TABLE vrn.gebiedinrichting_provinciaal ADD CONSTRAINT PK_gebiedinrichting_provinciaal 
	PRIMARY KEY (id)
;


ALTER TABLE vrn.gebiedverwerving_landelijk ADD CONSTRAINT PK_gebiedverwerving_landelijk 
	PRIMARY KEY (id)
;


ALTER TABLE vrn.gebiedverwerving_landelijk_tagged ADD CONSTRAINT PK_gebiedverwerving_tagged 
	PRIMARY KEY (id)
;


ALTER TABLE vrn.gebiedverwerving_provinciaal ADD CONSTRAINT PK_gebiedverwerving_provinciaal 
	PRIMARY KEY (id)
;



ALTER TABLE vrn.gebiedbeheer_landelijk
	ADD CONSTRAINT UQ_gebiedbeheer_landelijk_identificatie UNIQUE (identificatie)
;
ALTER TABLE vrn.gebiedbeheer_landelijk_tagged
	ADD CONSTRAINT UQ_gebiedbeheer_tagged UNIQUE (identificatie, tag)
;
ALTER TABLE vrn.gebiedbeheer_provinciaal
	ADD CONSTRAINT UQ_gebiedbeheer_provinciaal_identificatie UNIQUE (identificatie)
;
ALTER TABLE vrn.gebiedinrichting_landelijk
	ADD CONSTRAINT UQ_gebiedinrichting_landelijk_identificatie UNIQUE (identificatie)
;
ALTER TABLE vrn.gebiedinrichting_landelijk_tagged
	ADD CONSTRAINT UQ_gebiedinrichting_tagged UNIQUE (identificatie, tag)
;
ALTER TABLE vrn.gebiedinrichting_provinciaal
	ADD CONSTRAINT UQ_gebiedinrichting_provinciaal_identificatie UNIQUE (identificatie)
;
ALTER TABLE vrn.gebiedverwerving_landelijk
	ADD CONSTRAINT UQ_gebiedverwerving_landelijk_identificatie UNIQUE (identificatie)
;
ALTER TABLE vrn.gebiedverwerving_landelijk_tagged
	ADD CONSTRAINT UQ_gebiedverwerving_tagged UNIQUE (identificatie, tag)
;
ALTER TABLE vrn.gebiedverwerving_provinciaal
	ADD CONSTRAINT UQ_gebiedverwerving_provinciaal_identificatie UNIQUE (identificatie)
;
alter table vrn.gebiedbeheer_landelijk add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedbeheer_landelijk add constraint enforce_srid_geometrie check (st_srid(geometrie) = 28992);

alter table vrn.gebiedbeheer_provinciaal add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedbeheer_provinciaal add constraint enforce_srid_geometrie check (st_srid(geometrie) = 28992);

alter table vrn.gebiedinrichting_landelijk add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedinrichting_landelijk add constraint enforce_srid_geometrie check (st_srid(geometrie) = 28992);

alter table vrn.gebiedinrichting_provinciaal add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedinrichting_provinciaal add constraint enforce_srid_geometrie check (st_srid(geometrie) = 28992);

alter table vrn.gebiedverwerving_landelijk add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedverwerving_landelijk add constraint enforce_srid_geometrie check (st_srid(geometrie) = 28992);

alter table vrn.gebiedverwerving_provinciaal add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedverwerving_provinciaal add constraint enforce_srid_geometrie check (st_srid(geometrie) = 28992);drop index if exists vrn.gebiedbeheer_landelijk_geom_sdx;
create index gebiedbeheer_landelijk_geom_sdx on vrn.gebiedbeheer_landelijk using gist ( geometrie );

drop index if exists vrn.gebiedbeheer_provinciaal_geom_sdx;
create index gebiedbeheer_provinciaal_geom_sdx on vrn.gebiedbeheer_provinciaal using gist ( geometrie );

drop index if exists vrn.gebiedinrichting_landelijk_geom_sdx;
create index gebiedinrichting_landelijk_geom_sdx on vrn.gebiedinrichting_landelijk using gist ( geometrie );

drop index if exists vrn.gebiedinrichting_provinciaal_geom_sdx;
create index gebiedinrichting_provinciaal_geom_sdx on vrn.gebiedinrichting_provinciaal using gist ( geometrie );

drop index if exists vrn.gebiedverwerving_landelijk_geom_sdx;
create index gebiedverwerving_landelijk_geom_sdx on vrn.gebiedverwerving_landelijk using gist ( geometrie );

drop index if exists vrn.gebiedverwerving_provinciaal_geom_sdx;
create index gebiedverwerving_provinciaal_geom_sdx on vrn.gebiedverwerving_provinciaal using gist ( geometrie );

-- LandelijkGebiedBeheer
-- ProvinciaalGebiedBeheer
-- ProvinciaalGebiedInrichting
-- LandelijkGebiedInrichting
-- LandelijkGebiedVerwerving
-- ProvinciaalGebiedVerwerving
--
-- Names must match constants defined corresponding ThemeConfig
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedBeheer');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedBeheer');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedInrichting');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedInrichting');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedVerwerving');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedVerwerving');
	
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedBeheer', (select id from manager.thema t where t.naam = 'LandelijkGebiedBeheer'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedBeheer', (select id from manager.thema t where t.naam = 'ProvinciaalGebiedBeheer'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedInrichting', (select id from manager.thema t where t.naam = 'LandelijkGebiedInrichting'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedInrichting', (select id from manager.thema t where t.naam = 'ProvinciaalGebiedInrichting'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedVerwerving', (select id from manager.thema t where t.naam = 'LandelijkGebiedVerwerving'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedVerwerving', (select id from manager.thema t where t.naam = 'ProvinciaalGebiedVerwerving'));
	
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9931') )	
	;
--
-- CodelistMapping

INSERT INTO manager.codelistmapping VALUES ('TypeBeheerder', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/typeBeheerder.xml');
INSERT INTO manager.codelistmapping VALUES ('DoelRealisatie', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/doelRealisatie.xml');
INSERT INTO manager.codelistmapping VALUES ('BeheerPakket', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/beheerPakket.xml');
INSERT INTO manager.codelistmapping VALUES ('StatusBeheer', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/statusBeheer.xml');
INSERT INTO manager.codelistmapping VALUES ('Bronhouder', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/bronhouder.xml');
INSERT INTO manager.codelistmapping VALUES ('StatusInrichting', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/statusIngericht.xml');
INSERT INTO manager.codelistmapping VALUES ('StatusVerwerving', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/statusVerwerving.xml');
INSERT INTO manager.codelistmapping VALUES ('TypeBeheerderEnEigenaar', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/typeBeheerder.xml');

-- Stop the permission denied errors please!
GRANT ALL ON ALL TABLES IN SCHEMA vrn TO inspire;
GRANT ALL ON ALL SEQUENCES IN SCHEMA vrn TO inspire;

GRANT ALL ON ALL TABLES IN SCHEMA bron TO inspire;
GRANT ALL ON ALL SEQUENCES IN SCHEMA bron TO inspire;

GRANT ALL ON ALL TABLES IN SCHEMA metadata TO inspire;
GRANT ALL ON ALL SEQUENCES IN SCHEMA metadata TO inspire;

GRANT ALL ON ALL TABLES IN SCHEMA public TO inspire;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO inspire;

GRANT ALL ON ALL TABLES IN SCHEMA manager TO inspire;
GRANT ALL ON ALL SEQUENCES IN SCHEMA manager TO inspire;

GRANT ALL ON ALL TABLES IN SCHEMA inspire TO inspire;
GRANT ALL ON ALL SEQUENCES IN SCHEMA inspire TO inspire;
