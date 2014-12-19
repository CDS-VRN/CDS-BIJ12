-- LandelijkGebiedBeheer
-- ProvinciaalGebiedBeheer
-- ProvinciaalGebiedInrichting
-- LandelijkGebiedInrichting
-- LandelijkGebiedVerwerving
-- ProvinciaalGebiedVerwerving
--
-- Names must match constants defined corresponding ThemeConfig
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedBeheer');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedBeheer');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedInrichting');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedInrichting');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedVerwerving');
insert into manager.thema (id, naam) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedVerwerving');
	
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedBeheer', (select id from manager.thema t where t.naam = 'LandelijkGebiedBeheer'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedBeheer', (select id from manager.thema t where t.naam = 'ProvinciaalGebiedBeheer'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedInrichting', (select id from manager.thema t where t.naam = 'LandelijkGebiedInrichting'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedInrichting', (select id from manager.thema t where t.naam = 'ProvinciaalGebiedInrichting'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'LandelijkGebiedVerwerving', (select id from manager.thema t where t.naam = 'LandelijkGebiedVerwerving'));
insert into manager.datasettype (id, naam, thema_id) values ((select nextval('manager.hibernate_sequence')), 'ProvinciaalGebiedVerwerving', (select id from manager.thema t where t.naam = 'ProvinciaalGebiedVerwerving'));
	
insert into manager.themabronhouderauthorization (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedBeheer'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.themabronhouderauthorization (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedInrichting'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.themabronhouderauthorization (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'ProvinciaalGebiedVerwerving'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.themabronhouderauthorization (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedBeheer'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.themabronhouderauthorization (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedInrichting'), (select id from manager.bronhouder where code = '9931') )	
	;
	
insert into manager.themabronhouderauthorization (thema_id, bronhouder_id) values
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9920') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9921') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9922') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9923') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9924') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9925') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9926') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9927') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9928') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9929') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9930') ),
	( (select id from manager.thema where naam = 'LandelijkGebiedVerwerving'), (select id from manager.bronhouder where code = '9931') )	
	;
--
