-- CodelistMapping

INSERT INTO manager.codelistmapping VALUES ('TypeBeheerder', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/typeBeheerder.xml');
INSERT INTO manager.codelistmapping VALUES ('DoelRealisatie', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/doelRealisatie.xml');
INSERT INTO manager.codelistmapping VALUES ('BeheerPakket', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/beheerPakket.xml');
INSERT INTO manager.codelistmapping VALUES ('StatusBeheer', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/statusBeheer.xml');
INSERT INTO manager.codelistmapping VALUES ('Bronhouder', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/bronhouder.xml');
INSERT INTO manager.codelistmapping VALUES ('StatusInrichting', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/statusIngericht.xml');
INSERT INTO manager.codelistmapping VALUES ('StatusVerwerving', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/statusVerwerving.xml');
INSERT INTO manager.codelistmapping VALUES ('TypeBeheerderEnEigenaar', 'https://raw.githubusercontent.com/CDS-VRN/CDS-BIJ12/master/vrn-themes/src/main/feeds/typeBeheerder.xml');

-- Dataset
INSERT INTO manager.dataset (id, actief, naam, uuid, bronhouder_id, type_id) VALUES (100, true, 'test', 'http://ftp.geodan.nl/vrn/data/test/beheer/provinciaal/test_beheer_10.xml;http://ftp.geodan.nl/vrn/data/test/beheer/provinciaal/imna_vrn_beheer.xsd;GebiedBeheer', 14, 81);


-- MappingOperation
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (103, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (108, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (111, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (116, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (119, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (122, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (125, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (128, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (133, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (136, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (141, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (146, NULL, 0, 'conditionalTransform', 'TRANSFORM_OPERATION', '{"conditions":[]}', NULL);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (102, 'DATE_TIME', 0, 'beginTijd', 'INPUT_OPERATION', NULL, 103);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (107, NULL, 0, 'toCodeTypeTransform', 'TRANSFORM_OPERATION', NULL, 108);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (105, 'STRING', 0, 'beheerPakket', 'INPUT_OPERATION', NULL, 107);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (106, NULL, 1, 'stringConstantInput', 'TRANSFORM_OPERATION', '{"value":"BeheerPakket"}', 107);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (110, 'INTEGER', 0, 'contractNummer', 'INPUT_OPERATION', NULL, 111);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (115, NULL, 0, 'toCodeTypeTransform', 'TRANSFORM_OPERATION', NULL, 116);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (113, 'STRING', 0, 'doelBeheer', 'INPUT_OPERATION', NULL, 115);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (114, NULL, 1, 'stringConstantInput', 'TRANSFORM_OPERATION', '{"value":"DoelRealisatie"}', 115);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (118, 'STRING', 0, 'eenheidNummer', 'INPUT_OPERATION', NULL, 119);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (121, 'DATE_TIME', 0, 'eindTijd', 'INPUT_OPERATION', NULL, 122);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (124, 'GEOMETRY', 0, 'geometrie', 'INPUT_OPERATION', NULL, 125);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (127, 'STRING', 0, 'identificatie', 'INPUT_OPERATION', NULL, 128);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (132, NULL, 0, 'toCodeTypeTransform', 'TRANSFORM_OPERATION', NULL, 133);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (130, 'STRING', 0, 'bronHouder', 'INPUT_OPERATION', NULL, 132);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (131, NULL, 1, 'stringConstantInput', 'TRANSFORM_OPERATION', '{"value":"Bronhouder"}', 132);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (135, 'INTEGER', 0, 'relatieNummer', 'INPUT_OPERATION', NULL, 136);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (140, NULL, 0, 'toCodeTypeTransform', 'TRANSFORM_OPERATION', NULL, 141);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (138, 'STRING', 0, 'statusBeheer', 'INPUT_OPERATION', NULL, 140);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (139, NULL, 1, 'stringConstantInput', 'TRANSFORM_OPERATION', '{"value":"StatusBeheer"}', 140);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (145, NULL, 0, 'toCodeTypeTransform', 'TRANSFORM_OPERATION', NULL, 146);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (143, 'STRING', 0, 'typeBeheerder', 'INPUT_OPERATION', NULL, 145);
INSERT INTO manager.mappingoperation (id, inputattributetype, operation_index, operationname, operationtype, properties, parent_id) VALUES (144, NULL, 1, 'stringConstantInput', 'TRANSFORM_OPERATION', '{"value":"TypeBeheerder"}', 145);

-- AttributeMapping
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (101, 'begintijd', true, 100, 103);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (104, 'beheerpakket', true, 100, 108);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (109, 'contractnummer', true, 100, 111);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (112, 'doelBeheer', true, 100, 116);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (117, 'eenheidnummer', true, 100, 119);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (120, 'eindtijd', true, 100, 122);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (123, 'geometrie', true, 100, 125);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (126, 'identificatie', true, 100, 128);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (129, 'imnaBronhouder', true, 100, 133);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (134, 'relatienummer', true, 100, 136);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (137, 'statusBeheer', true, 100, 141);
INSERT INTO manager.attributemapping (id, attributename, valid, dataset_id, rootoperation_id) VALUES (142, 'typeBeheerder', true, 100, 146);

-- Set start of Hibernate sequence 
DROP SEQUENCE IF EXISTS manager.hibernate_sequence;
CREATE SEQUENCE manager.hibernate_sequence START 200;
ALTER TABLE manager.hibernate_sequence
  OWNER TO cds_owner;
GRANT ALL ON SEQUENCE manager.hibernate_sequence TO cds_owner;
GRANT ALL ON SEQUENCE manager.hibernate_sequence TO inspire;

