
--
-- metadata EnvironmentalMonitoringFacilities
-- 

-- One serviceprovider for all services of this INSPIRE theme
DELETE FROM metadata.si_keyword where serviceidentification_id IN (select serviceidentification_id from metadata.service where name='view_EF' OR name='download_EF');
DELETE FROM metadata.si_accessconstraint where serviceidentification_id IN (select serviceidentification_id from metadata.service where name='view_EF' OR name='download_EF');
DELETE FROM metadata.service_datasetmetadata where service_id IN (select id from metadata.service where name='view_EF' OR name='download_EF');
DELETE FROM metadata.service where name='view_EF' OR name='download_EF';
DELETE FROM metadata.serviceidentification where servicepath like '%view_EF%' OR servicepath like '%download_EF%';
DELETE FROM metadata.extendedcapabilities where metadataurl like '%view_EF%' OR metadataurl like '%download_EF%';

DELETE FROM metadata.si_keyword where serviceidentification_id IN (select serviceidentification_id from metadata.service where name='wms_EF_NL' OR name='wfs_EF_NL');
DELETE FROM metadata.si_accessconstraint where serviceidentification_id IN (select serviceidentification_id from metadata.service where name='wms_EF_NL' OR name='wfs_EF_NL');
DELETE FROM metadata.service_datasetmetadata where service_id IN (select id from metadata.service where name='wms_EF_NL' OR name='wfs_EF_NL');
DELETE FROM metadata.service where name='wms_EF_NL' OR name='wfs_EF_NL';
DELETE FROM metadata.serviceidentification where servicepath like '%wms_EF_NL%' OR servicepath like '%wfs_EF_NL%';
DELETE FROM metadata.extendedcapabilities where metadataurl like '%wms_EF_NL%' OR metadataurl like '%wfs_EF_NL%';

DELETE FROM metadata.sp_deliverypoint where serviceprovider_id IN (select id from metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%');
DELETE FROM metadata.sp_emailaddress where serviceprovider_id IN (select id from metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%');
DELETE FROM metadata.sp_faxnumber where serviceprovider_id IN (select id from metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%');
DELETE FROM metadata.sp_phonenumber where serviceprovider_id IN (select id from metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%');
DELETE FROM metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%';

INSERT INTO metadata.serviceprovider (id,administrativearea,city,contactinstructions,country,hoursofservice,individualname,onlineresource,organizationname,positionname,postalcode,providername,providersite,role)
	VALUES ((select nextval('metadata.hibernate_sequence')), '', '', NULL, '', NULL, 'Functioneel beheerder CDS Inspire, EnvironmentalMonitoringFacilities', NULL, 'GBO provincies', 'pointOfContact', '', NULL, NULL, NULL);

-- INSPIRE View and Download service

INSERT INTO metadata.extendedcapabilities (id,metadataurl) VALUES ((select nextval('metadata.hibernate_sequence')), 'view_EF');
INSERT INTO metadata.extendedcapabilities (id,metadataurl) VALUES ((select nextval('metadata.hibernate_sequence')), 'download_EF');

INSERT INTO metadata.serviceidentification (id,abstract,fees,servicetype,servicepath,title) 
	VALUES ((select nextval('metadata.hibernate_sequence')), 'Deze View service heeft betrekking op EnvironmentalMonitoringFacilities.', 'no conditions apply', 'WMS', 'EnvironmentalMonitoringFacilities/services/view_EF', 'INSPIRE View service voor EnvironmentalMonitoringFacilities van de gezamenlijke provincies');
INSERT INTO metadata.serviceidentification  (id,abstract,fees,servicetype,servicepath,title) 
	VALUES ((select nextval('metadata.hibernate_sequence')), 'Deze Download service heeft betrekking op EnvironmentalMonitoringFacilities.', 'none', 'WFS', 'EnvironmentalMonitoringFacilities/services/download_EF', 'INSPIRE Download service voor EnvironmentalMonitoringFacilities van de gezamenlijke provincies');

INSERT INTO metadata.service (id,description,name,serviceidentification_id,serviceprovider_id, extendedcapabilities_id) VALUES ((select nextval('metadata.hibernate_sequence')), 'omschrijving view_EF', 'view_EF', (select id from metadata.serviceidentification where servicepath='EnvironmentalMonitoringFacilities/services/view_EF'), (select id from metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%'), (select id from metadata.extendedcapabilities where metadataurl='view_EF')); 
INSERT INTO metadata.service (id,description,name,serviceidentification_id,serviceprovider_id, extendedcapabilities_id) VALUES ((select nextval('metadata.hibernate_sequence')), 'omschrijving download_EF', 'download_EF', (select id from metadata.serviceidentification where servicepath='EnvironmentalMonitoringFacilities/services/download_EF'), (select id from metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%'), (select id from metadata.extendedcapabilities where metadataurl='download_EF')); 

INSERT INTO metadata.service_datasetmetadata (service_id, name, namespace, url,"index") VALUES ((select id from metadata.service where name='view_EF'), 'EF.Monsterpunt', '', 'X', 0);

INSERT INTO metadata.si_accessconstraint (serviceidentification_id,accessconstraint,"index") VALUES ((select serviceidentification_id from metadata.service where name='view_EF'), 'anders', 0);

INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='view_EF'), 'ISO', 'infoMapAccessService', 0);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='view_EF'), 'GEMET - INSPIRE themes, version 1.0', 'Habitat and Biotopes', 1);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='download_EF'), 'ISO', 'infoFeatureAccessService', 0);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='download_EF'), 'GEMET - INSPIRE themes, version 1.0', 'Habitat and Biotopes', 1);

-- si-version is not filled

INSERT INTO metadata.sp_deliverypoint (serviceprovider_id,deliverypoint,"index") VALUES ((select serviceprovider_id from metadata.service where name='view_EF'), '', 0);

INSERT INTO metadata.sp_emailaddress (serviceprovider_id, emailaddress,"index") VALUES ((select serviceprovider_id from metadata.service where name='view_EF'), 'inspire@gbo-provincie.nl', 0);

INSERT INTO metadata.sp_faxnumber (serviceprovider_id ,faxnumber,"index") VALUES ((select serviceprovider_id from metadata.service where name='view_EF'), '', 0);

INSERT INTO metadata.sp_phonenumber (serviceprovider_id,phonenumber,"index") VALUES ((select serviceprovider_id from metadata.service where name='view_EF'), '', 0);
--------------------------------

-- WMS & WFS with NL / flat structure


INSERT INTO metadata.extendedcapabilities (id,metadataurl) VALUES ((select nextval('metadata.hibernate_sequence')), 'wms_EF_NL');
INSERT INTO metadata.extendedcapabilities (id,metadataurl) VALUES ((select nextval('metadata.hibernate_sequence')), 'wfs_EF_NL');

INSERT INTO metadata.serviceidentification (id,abstract,fees,servicetype,servicepath,title) 
	VALUES ((select nextval('metadata.hibernate_sequence')), 'Deze WMS service heeft betrekking op EnvironmentalMonitoringFacilities.', 'no conditions apply', 'WMS', 'EnvironmentalMonitoringFacilities/services/wms_EF_NL', 'WMS service voor EnvironmentalMonitoringFacilities van de gezamenlijke provincies');
INSERT INTO metadata.serviceidentification  (id,abstract,fees,servicetype,servicepath,title) 
	VALUES ((select nextval('metadata.hibernate_sequence')), 'Deze WFS service heeft betrekking op EnvironmentalMonitoringFacilities.', 'none', 'WFS', 'EnvironmentalMonitoringFacilities/services/wfs_EF_NL', 'WFS service voor EnvironmentalMonitoringFacilities van de gezamenlijke provincies');

INSERT INTO metadata.service (id,description,name,serviceidentification_id,serviceprovider_id, extendedcapabilities_id) VALUES ((select nextval('metadata.hibernate_sequence')), 'omschrijving wms_EF_NL', 'wms_EF_NL', (select id from metadata.serviceidentification where servicepath='EnvironmentalMonitoringFacilities/services/wms_EF_NL'), (select id from metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%'), (select id from metadata.extendedcapabilities where metadataurl='wms_EF_NL')); 
INSERT INTO metadata.service (id,description,name,serviceidentification_id,serviceprovider_id, extendedcapabilities_id) VALUES ((select nextval('metadata.hibernate_sequence')), 'omschrijving wfs_EF_NL', 'wfs_EF_NL', (select id from metadata.serviceidentification where servicepath='EnvironmentalMonitoringFacilities/services/wfs_EF_NL'), (select id from metadata.serviceprovider where individualname like '%EnvironmentalMonitoringFacilities%'), (select id from metadata.extendedcapabilities where metadataurl='wfs_EF_NL')); 

INSERT INTO metadata.service_datasetmetadata (service_id, name, namespace, url,"index") VALUES ((select id from metadata.service where name='wms_EF_NL'), 'EF.Monsterpunt', '', 'X', 0);

INSERT INTO metadata.si_accessconstraint (serviceidentification_id,accessconstraint,"index") VALUES ((select serviceidentification_id from metadata.service where name='wms_EF_NL'), 'anders', 0);

INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='wms_EF_NL'), 'ISO', 'infoMapAccessService', 0);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='wms_EF_NL'), 'GEMET - INSPIRE themes, version 1.0', 'Habitat and Biotopes', 1);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='wfs_EF_NL'), 'ISO', 'infoFeatureAccessService', 0);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='wfs_EF_NL'), 'GEMET - INSPIRE themes, version 1.0', 'Habitat and Biotopes', 1);
