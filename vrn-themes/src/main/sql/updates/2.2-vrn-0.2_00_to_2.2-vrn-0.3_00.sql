ALTER TABLE vrn.gebiedbeheer_landelijk ALTER COLUMN beheerpakket DROP NOT NULL;

ALTER TABLE vrn.gebiedbeheer_provinciaal ALTER COLUMN beheerpakket DROP NOT NULL;

ALTER TABLE vrn.gebiedbeheer_landelijk_tagged ALTER COLUMN beheerpakket DROP NOT NULL;