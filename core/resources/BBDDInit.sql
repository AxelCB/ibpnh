INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'date.format', 'STRING','dd/MM/yyyy','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'date.format.without.days.short', 'STRING','MM/YY','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'date.time.format', 'STRING','dd/MM/yyyy HH:mm:ss.SSS','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'date.time.format.ISO8601', 'STRING','yyyy-MM-dd''T''HH:mm:ss.SSSXXX','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'date.time.format.with.hyphens.without.milliseconds', 'STRING','yyyy-MM-dd HH:mm:ss','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'date.time.format.without.seconds', 'STRING','dd/MM/yyyy HH:mm','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'date.time.format.without.milliseconds', 'STRING','dd/MM HH:mm.ss','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'date.time.format.without.seconds.and.year', 'STRING','dd/MM HH:mm','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'json.date.time.exchange.format', 'STRING','dd/MM/yyyy HH:mm:ss.SSS','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'native.sql.date.format', 'STRING','yyyy-MM-dd','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'native.sql.date.time.format', 'STRING','yyyy-MM-dd HH:mm:ss.SSS','date, time, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'hour.format', 'STRING','HH:mm','hour, format',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'locale.language.tag', 'STRING','es','locale, language, tag',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Descripción', TRUE, TRUE, 'user.registration', 'BOOLEAN','false','user, registration,enabled',FALSE);

INSERT INTO "user"(id, deleted, disabledcause, enabled, enablinghash, firstlogin,
            hashcost, loginattempts, password, username, role_id)VALUES (nextval('user_seq'), FALSE, NULL, TRUE,NULL,FALSE,1,0,jeqkbf, admin,1);




