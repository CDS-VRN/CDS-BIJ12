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
