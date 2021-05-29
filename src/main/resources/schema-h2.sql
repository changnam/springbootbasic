-- schema.sql
drop table  if exists cars;
drop sequence if exists hibernate_sequence;

CREATE TABLE cars(id int,
    name VARCHAR(255), price INT);