--liquibase formatted sql
--changeset emelnikov:user_authority logicalFilePath:user_authority endDelimiter:;
create table user_authority (
    id_user int,
    id_authority int
);