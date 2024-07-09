--liquibase formatted sql
--changeset emelnikov:users logicalFilePath:users endDelimiter:;
create table users (
    id serial,
    profile text,
    password text,
    name text,
    surname text,
    lastname text,
    email text,
    phone text,
    foto bytea,
    birth date,
    active boolean,
    ldts date
);
