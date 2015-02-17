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
alter table vrn.gebiedverwerving_provinciaal add constraint enforce_srid_geometrie check (st_srid(geometrie) = 28992);

-- Add constraints for tagged tables.
alter table vrn.gebiedbeheer_landelijk_tagged add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedbeheer_landelijk_tagged add constraint enforce_srid_geometrie check (st_srid(geometrie) =
28992);
alter table vrn.gebiedinrichting_landelijk_tagged add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedinrichting_landelijk_tagged add constraint enforce_srid_geometrie check (st_srid(geometrie) =
28992);
alter table vrn.gebiedverwerving_landelijk_tagged add constraint enforce_dims_geometrie check (st_ndims(geometrie) = 2);
alter table vrn.gebiedverwerving_landelijk_tagged add constraint enforce_srid_geometrie check (st_srid(geometrie) =
28992);
