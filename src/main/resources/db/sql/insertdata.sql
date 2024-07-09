--liquibase formatted sql
--changeset emelnikov:insertdata logicalFilePath:insertdata endDelimiter:;
insert into users(profile, password, active, ldts) values('login', 'hash', true, now());
insert into authority(code) values('AD');
insert into user_authority values(1, 1);