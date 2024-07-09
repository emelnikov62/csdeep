--liquibase formatted sql
--changeset emelnikov:authority logicalFilePath:authority endDelimiter:;
create table authority (
    id serial,
    code text
);