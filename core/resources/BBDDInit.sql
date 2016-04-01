INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha', TRUE, TRUE, 'date.format', 'STRING','dd/MM/yyyy','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha sin dias y con año corto(dos dígitos)', TRUE, TRUE, 'date.format.without.days.short', 'STRING','MM/YY','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha y Hora', TRUE, TRUE, 'date.time.format', 'STRING','dd/MM/yyyy HH:mm:ss.SSS','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha y Hora según ISO 8601', TRUE, TRUE, 'date.time.format.ISO8601', 'STRING','yyyy-MM-dd''T''HH:mm:ss.SSSXXX','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha y Hora con guiones y sin millisegundos', TRUE, TRUE, 'date.time.format.with.hyphens.without.milliseconds', 'STRING','yyyy-MM-dd HH:mm:ss','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha y Hora sin segundos ni millisegundos', TRUE, TRUE, 'date.time.format.without.seconds', 'STRING','dd/MM/yyyy HH:mm','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha y Hora sin Millisegundos ni Año', TRUE, TRUE, 'date.time.format.without.milliseconds', 'STRING','dd/MM HH:mm.ss','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha y Hora sin segundos ni año', TRUE, TRUE, 'date.time.format.without.seconds.and.year', 'STRING','dd/MM HH:mm','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha y Hora usado en JSON', TRUE, TRUE, 'json.date.time.exchange.format', 'STRING','dd/MM/yyyy HH:mm:ss.SSS','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha nativa de SQL', TRUE, TRUE, 'native.sql.date.format', 'STRING','yyyy-MM-dd','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Fecha y Hora nativa de SQL', TRUE, TRUE, 'native.sql.date.time.format', 'STRING','yyyy-MM-dd HH:mm:ss.SSS','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Formato de Hora', TRUE, TRUE, 'hour.format', 'STRING','HH:mm','hour, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Tag de Lenguaje para locales', TRUE, TRUE, 'locale.language.tag', 'STRING','es','locale, language, tag',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Habilitación de registración de usuarios por la web', TRUE, TRUE, 'user.registration', 'BOOLEAN','false','user, registration,enabled',FALSE);

INSERT INTO "user"(id, deleted, disabledcause, enabled, enablinghash, firstlogin, hashcost, loginattempts, password, username, role_id)
    VALUES (nextval('user_seq'), FALSE, NULL, TRUE,NULL,FALSE,1,0,'$2a$08$QQwZQMLfpH7IxUZOXlI8iOOrLXu9/GiW/W73a939jkqRiKi.hmV5y', admin,1);

--Function
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'methodParkingEnd', FALSE, 'MethodParkingEnd', 'graphics', 'methodParkingEnd', NULL, '/graphic/methodParkingEnd');
  --Function
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'methodParkingEnd', FALSE, 'MethodParkingEnd', 'graphics', 'methodParkingEnd', NULL, '/graphic/methodParkingEnd');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;



