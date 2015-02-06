drop index if exists vrn.gebiedbeheer_landelijk_geom_sdx;
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
