--
-- Buisleidingen
--
-- Names must match constants defined corresponding ThemeConfig
insert into manager.thema (id, naam) values 
	((select nextval('manager.hibernate_sequence')), 'Buisleidingen - Transportroutedelen'),
	((select nextval('manager.hibernate_sequence')), 'Buisleidingen - Transportrouterisico');
	
insert into manager.datasettype (id, naam, thema_id) values
	((select nextval('manager.hibernate_sequence')), 'Transportroutedelen', (select id from manager.thema t where t.naam = 'Buisleidingen - Transportroutedelen')),
	((select nextval('manager.hibernate_sequence')), 'Transportrouterisico', (select id from manager.thema t where t.naam = 'Buisleidingen - Transportrouterisico'));
	
-- ---------------------------------------
-- -- Create "bronhouder" and datasets: --
-- ---------------------------------------
insert into manager.bronhouder (id, code, naam, contact_naam, contact_emailadres, common_name) select nextval('manager.hibernate_sequence'), 'gasunie', 'Gasunie', 'Gasunie', 'inspire@idgis.nl', 'gasunie' where not exists (select * from manager.bronhouder where code = 'gasunie');
insert into manager.bronhouder (id, code, naam, contact_naam, contact_emailadres, common_name) select nextval('manager.hibernate_sequence'), 'nam', 'NAM', 'NAM', 'inspire@idgis.nl', 'nam' where not exists (select * from manager.bronhouder where code = 'nam');
	
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'Buisleidingen - Transportroutedelen'), (select id from manager.bronhouder where code = 'gasunie') ),
	( (select id from manager.thema where naam = 'Buisleidingen - Transportrouterisico'), (select id from manager.bronhouder where code = 'gasunie') );
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'Buisleidingen - Transportroutedelen'), (select id from manager.bronhouder where code = 'nam') ),
	( (select id from manager.thema where naam = 'Buisleidingen - Transportrouterisico'), (select id from manager.bronhouder where code = 'nam') );
	
insert into manager.dataset (id, actief, uuid, bronhouder_id, type_id) values
	(
		(select nextval('manager.hibernate_sequence')), 
		true, 
		'http://localhost/gasunie/Transportroutedeel.gml;http://localhost/gasunie/Transportroutedeel.xsd;Transportroutedeel',
		(select id from manager.bronhouder where code = 'gasunie'),
		(select id from manager.datasettype where naam = 'Transportroutedelen')
	),
	(
		(select nextval('manager.hibernate_sequence')),
		true,
		'http://localhost/gasunie/TransportrouteRisico.gml;http://localhost/gasunie/TransportrouteRisico.xsd;TransportrouteRisico',
		(select id from manager.bronhouder where code = 'gasunie'),
		(select id from manager.datasettype where naam = 'Transportrouterisico')
	);
insert into manager.dataset (id, actief, uuid, bronhouder_id, type_id) values
	(
		(select nextval('manager.hibernate_sequence')), 
		true, 
		'http://localhost/nam/Transportroutedeel.gml;http://localhost/nam/Transportroutedeel.xsd;Transportroutedeel',
		(select id from manager.bronhouder where code = 'nam'),
		(select id from manager.datasettype where naam = 'Transportroutedelen')
	),
	(
		(select nextval('manager.hibernate_sequence')),
		true,
		'http://localhost/nam/TransportrouteRisico.gml;http://localhost/nam/TransportrouteRisico.xsd;TransportrouteRisico',
		(select id from manager.bronhouder where code = 'nam'),
		(select id from manager.datasettype where naam = 'Transportrouterisico')
	);
--

