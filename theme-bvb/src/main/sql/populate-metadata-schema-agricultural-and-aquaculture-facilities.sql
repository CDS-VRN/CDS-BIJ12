
--
-- metadata AgriculturalAndAquacultureFacilities
-- 

-- One serviceprovider for all services of this INSPIRE theme
DELETE FROM metadata.si_keyword where serviceidentification_id IN (select serviceidentification_id from metadata.service where name='view_AF' OR name='download_AF');
DELETE FROM metadata.si_accessconstraint where serviceidentification_id IN (select serviceidentification_id from metadata.service where name='view_AF' OR name='download_AF');
DELETE FROM metadata.service_datasetmetadata where service_id IN (select id from metadata.service where name='view_AF' OR name='download_AF');
DELETE FROM metadata.service where name='view_AF' OR name='download_AF';
DELETE FROM metadata.serviceidentification where servicepath like '%view_AF%' OR servicepath like '%download_AF%';
DELETE FROM metadata.extendedcapabilities where metadataurl like '%view_AF%' OR metadataurl like '%download_AF%';

DELETE FROM metadata.si_keyword where serviceidentification_id IN (select serviceidentification_id from metadata.service where name='wms_AF_NL' OR name='wfs_AF_NL');
DELETE FROM metadata.si_accessconstraint where serviceidentification_id IN (select serviceidentification_id from metadata.service where name='wms_AF_NL' OR name='wfs_AF_NL');
DELETE FROM metadata.service_datasetmetadata where service_id IN (select id from metadata.service where name='wms_AF_NL' OR name='wfs_AF_NL');
DELETE FROM metadata.service where name='wms_AF_NL' OR name='wfs_AF_NL';
DELETE FROM metadata.serviceidentification where servicepath like '%wms_AF_NL%' OR servicepath like '%wfs_AF_NL%';
DELETE FROM metadata.extendedcapabilities where metadataurl like '%wms_AF_NL%' OR metadataurl like '%wfs_AF_NL%';

DELETE FROM metadata.sp_deliverypoint where serviceprovider_id IN (select id from metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%');
DELETE FROM metadata.sp_emailaddress where serviceprovider_id IN (select id from metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%');
DELETE FROM metadata.sp_faxnumber where serviceprovider_id IN (select id from metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%');
DELETE FROM metadata.sp_phonenumber where serviceprovider_id IN (select id from metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%');
DELETE FROM metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%';

INSERT INTO metadata.serviceprovider (id,administrativearea,city,contactinstructions,country,hoursofservice,individualname,onlineresource,organizationname,positionname,postalcode,providername,providersite,role)
	VALUES ((select nextval('metadata.hibernate_sequence')), '', '', NULL, '', NULL, 'Functioneel beheerder CDS Inspire, AgriculturalAndAquacultureFacilities', NULL, 'GBO provincies', 'pointOfContact', '', NULL, NULL, NULL);

-- INSPIRE View and Download service

INSERT INTO metadata.extendedcapabilities (id,metadataurl) VALUES ((select nextval('metadata.hibernate_sequence')), 'view_AF');
INSERT INTO metadata.extendedcapabilities (id,metadataurl) VALUES ((select nextval('metadata.hibernate_sequence')), 'download_AF');

INSERT INTO metadata.serviceidentification (id,abstract,fees,servicetype,servicepath,title) 
	VALUES ((select nextval('metadata.hibernate_sequence')), 'Deze View service heeft betrekking op AgriculturalAndAquacultureFacilities.', 'no conditions apply', 'WMS', 'AgriculturalAndAquacultureFacilities/services/view_AF', 'INSPIRE View service voor AgriculturalAndAquacultureFacilities van de gezamenlijke provincies');
INSERT INTO metadata.serviceidentification  (id,abstract,fees,servicetype,servicepath,title) 
	VALUES ((select nextval('metadata.hibernate_sequence')), 'Deze Download service heeft betrekking op AgriculturalAndAquacultureFacilities.', 'none', 'WFS', 'AgriculturalAndAquacultureFacilities/services/download_AF', 'INSPIRE Download service voor AgriculturalAndAquacultureFacilities van de gezamenlijke provincies');

INSERT INTO metadata.service (id,description,name,serviceidentification_id,serviceprovider_id, extendedcapabilities_id) VALUES ((select nextval('metadata.hibernate_sequence')), 'omschrijving view_AF', 'view_AF', (select id from metadata.serviceidentification where servicepath='AgriculturalAndAquacultureFacilities/services/view_AF'), (select id from metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%'), (select id from metadata.extendedcapabilities where metadataurl='view_AF')); 
INSERT INTO metadata.service (id,description,name,serviceidentification_id,serviceprovider_id, extendedcapabilities_id) VALUES ((select nextval('metadata.hibernate_sequence')), 'omschrijving download_AF', 'download_AF', (select id from metadata.serviceidentification where servicepath='AgriculturalAndAquacultureFacilities/services/download_AF'), (select id from metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%'), (select id from metadata.extendedcapabilities where metadataurl='download_AF')); 

INSERT INTO metadata.service_datasetmetadata (service_id, name, namespace, url,"index") VALUES ((select id from metadata.service where name='view_AF'), 'AF.BestandVeehouderijbedrijven.bedrijf', '', 'X', 0);
INSERT INTO metadata.service_datasetmetadata (service_id, name, namespace, url,"index") VALUES ((select id from metadata.service where name='view_AF'), 'AF.BestandVeehouderijbedrijven.gebouw', '', 'X', 1);
INSERT INTO metadata.service_datasetmetadata (service_id, name, namespace, url,"index") VALUES ((select id from metadata.service where name='view_AF'), 'AF.BestandVeehouderijbedrijven.emissiepunt', 'X', '', 2);

INSERT INTO metadata.si_accessconstraint (serviceidentification_id,accessconstraint,"index") VALUES ((select serviceidentification_id from metadata.service where name='view_AF'), 'anders', 0);

INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='view_AF'), 'ISO', 'infoMapAccessService', 0);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='view_AF'), 'GEMET - INSPIRE themes, version 1.0', 'Habitat and Biotopes', 1);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='download_AF'), 'ISO', 'infoFeatureAccessService', 0);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='download_AF'), 'GEMET - INSPIRE themes, version 1.0', 'Habitat and Biotopes', 1);

-- si-version is not filled

INSERT INTO metadata.sp_deliverypoint (serviceprovider_id,deliverypoint,"index") VALUES ((select serviceprovider_id from metadata.service where name='view_AF'), '', 0);

INSERT INTO metadata.sp_emailaddress (serviceprovider_id, emailaddress,"index") VALUES ((select serviceprovider_id from metadata.service where name='view_AF'), 'inspire@gbo-provincie.nl', 0);

INSERT INTO metadata.sp_faxnumber (serviceprovider_id ,faxnumber,"index") VALUES ((select serviceprovider_id from metadata.service where name='view_AF'), '', 0);

INSERT INTO metadata.sp_phonenumber (serviceprovider_id,phonenumber,"index") VALUES ((select serviceprovider_id from metadata.service where name='view_AF'), '', 0);
--------------------------------

-- WMS & WFS with NL / flat structure


INSERT INTO metadata.extendedcapabilities (id,metadataurl) VALUES ((select nextval('metadata.hibernate_sequence')), 'wms_AF_NL');
INSERT INTO metadata.extendedcapabilities (id,metadataurl) VALUES ((select nextval('metadata.hibernate_sequence')), 'wfs_AF_NL');

INSERT INTO metadata.serviceidentification (id,abstract,fees,servicetype,servicepath,title) 
	VALUES ((select nextval('metadata.hibernate_sequence')), 'Deze WMS service heeft betrekking op AgriculturalAndAquacultureFacilities.', 'no conditions apply', 'WMS', 'AgriculturalAndAquacultureFacilities/services/wms_AF_NL', 'WMS service voor AgriculturalAndAquacultureFacilities van de gezamenlijke provincies');
INSERT INTO metadata.serviceidentification  (id,abstract,fees,servicetype,servicepath,title) 
	VALUES ((select nextval('metadata.hibernate_sequence')), 'Deze WFS service heeft betrekking op AgriculturalAndAquacultureFacilities.', 'none', 'WFS', 'AgriculturalAndAquacultureFacilities/services/wfs_AF_NL', 'WFS service voor AgriculturalAndAquacultureFacilities van de gezamenlijke provincies');

INSERT INTO metadata.service (id,description,name,serviceidentification_id,serviceprovider_id, extendedcapabilities_id) VALUES ((select nextval('metadata.hibernate_sequence')), 'omschrijving wms_AF_NL', 'wms_AF_NL', (select id from metadata.serviceidentification where servicepath='AgriculturalAndAquacultureFacilities/services/wms_AF_NL'), (select id from metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%'), (select id from metadata.extendedcapabilities where metadataurl='wms_AF_NL')); 
INSERT INTO metadata.service (id,description,name,serviceidentification_id,serviceprovider_id, extendedcapabilities_id) VALUES ((select nextval('metadata.hibernate_sequence')), 'omschrijving wfs_AF_NL', 'wfs_AF_NL', (select id from metadata.serviceidentification where servicepath='AgriculturalAndAquacultureFacilities/services/wfs_AF_NL'), (select id from metadata.serviceprovider where individualname like '%AgriculturalAndAquacultureFacilities%'), (select id from metadata.extendedcapabilities where metadataurl='wfs_AF_NL')); 

INSERT INTO metadata.service_datasetmetadata (service_id, name, namespace, url,"index") VALUES ((select id from metadata.service where name='wms_AF_NL'), 'AF.BestandVeehouderijbedrijven.bedrijf', '', 'X', 0);
INSERT INTO metadata.service_datasetmetadata (service_id, name, namespace, url,"index") VALUES ((select id from metadata.service where name='wms_AF_NL'), 'AF.BestandVeehouderijbedrijven.gebouw', '', 'X', 1);
INSERT INTO metadata.service_datasetmetadata (service_id, name, namespace, url,"index") VALUES ((select id from metadata.service where name='wms_AF_NL'), 'AF.BestandVeehouderijbedrijven.emissiepunt', '', 'X', 2);

INSERT INTO metadata.si_accessconstraint (serviceidentification_id,accessconstraint,"index") VALUES ((select serviceidentification_id from metadata.service where name='wms_AF_NL'), 'anders', 0);

INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='wms_AF_NL'), 'ISO', 'infoMapAccessService', 0);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='wms_AF_NL'), 'GEMET - INSPIRE themes, version 1.0', 'Habitat and Biotopes', 1);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='wfs_AF_NL'), 'ISO', 'infoFeatureAccessService', 0);
INSERT INTO metadata.si_keyword (serviceidentification_id,codespace,"value","index" ) VALUES ((select serviceidentification_id from metadata.service where name='wfs_AF_NL'), 'GEMET - INSPIRE themes, version 1.0', 'Habitat and Biotopes', 1);
