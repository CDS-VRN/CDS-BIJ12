ALTER TABLE vrn.gebiedbeheer_landelijk ALTER COLUMN beheerpakket DROP NOT NULL;
ALTER TABLE vrn.gebiedbeheer_provinciaal ALTER COLUMN beheerpakket DROP NOT NULL;
ALTER TABLE vrn.gebiedbeheer_landelijk_tagged ALTER COLUMN beheerpakket DROP NOT NULL;


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
