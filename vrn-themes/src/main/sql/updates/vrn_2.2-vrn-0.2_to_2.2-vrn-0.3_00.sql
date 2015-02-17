
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

-- Fix nullability of optional attributes.
ALTER TABLE vrn.gebiedbeheer_provinciaal ALTER COLUMN doelbeheer DROP NOT NULL;


ALTER TABLE vrn.gebiedinrichting_provinciaal ALTER COLUMN doelinrichting DROP NOT NULL;
ALTER TABLE vrn.gebiedinrichting_landelijk ALTER COLUMN doelinrichting SET NOT NULL;
ALTER TABLE vrn.gebiedinrichting_landelijk_tagged ALTER COLUMN doelinrichting SET NOT NULL;

ALTER TABLE vrn.gebiedverwerving_landelijk ALTER COLUMN doelverwerving SET NOT NULL;
ALTER TABLE vrn.gebiedverwerving_landelijk_tagged ALTER COLUMN doelverwerving SET NOT NULL;
