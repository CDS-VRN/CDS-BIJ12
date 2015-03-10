create index gebiedbeheer_landelijk_tagged_geom_sdx on vrn.gebiedbeheer_landelijk_tagged using gist ( geometrie );

create index gebiedinrichting_landelijk_tagged_geom_sdx on vrn.gebiedinrichting_landelijk_tagged using gist ( geometrie );

create index gebiedverwerving_landelijk_tagged_geom_sdx on vrn.gebiedverwerving_landelijk_tagged using gist ( geometrie );

create index gebiedbeheer_landelijk_tagged_tag_idx on vrn.gebiedbeheer_landelijk_tagged (tag);

create index gebiedinrichting_landelijk_tagged_tag_idx on vrn.gebiedinrichting_landelijk_tagged (tag);

create index gebiedverwerving_landelijk_tagged_tag_idx on vrn.gebiedverwerving_landelijk_tagged (tag);
