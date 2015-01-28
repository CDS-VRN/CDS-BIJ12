-- Names must match constants defined corresponding ThemeConfig
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'RISC-ISOR - Kwetsbaar object');
	
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'Kwetsbaar object', (select id from manager.thema t where t.naam = 'RISC-ISOR - Kwetsbaar object'));

insert into manager.bronhouder (id, code, naam, contact_naam, contact_emailadres, common_name) values ((select nextval('manager.hibernate_sequence')), 'risc', 'Risc', 'Risc', 'inspire@gbo-provincies.nl', 'risc');
	
insert into manager.bronhouderthema (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'RISC-ISOR - Kwetsbaar object'), (select id from manager.bronhouder where code = 'risc') );
	