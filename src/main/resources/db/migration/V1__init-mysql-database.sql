drop table if exists car_entity;
create table car_entity (id varchar(36) not null, brand varchar(255) not null, model varchar(20) not null, release_year smallint not null, primary key (id)) engine=InnoDB;
