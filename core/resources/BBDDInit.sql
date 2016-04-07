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

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Costo del algoritmo de hashing de contraseñas', TRUE, TRUE, 'hash.cost', 'LONG','10','password,hash,cost',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Cantidad de intentos de inicio de sesión antes de bloqueo', TRUE, TRUE, 'login.max.attempts', 'LONG','10','login,max,attempts',FALSE);

INSERT INTO parameter (id, deleted, description, fixed, global, name, type, value, tags,viewed)
VALUES (nextval('parameter_seq'), FALSE, 'Cantidad de items por página', TRUE, TRUE, 'items.per.page', 'LONG','10','items,page',FALSE);

INSERT INTO roletype (id, deleted, description, name, passwordreseter_id, roletypeenum) VALUES ( 1, false, 'Administrador general del sistema', 'Administrador', null, 0);

INSERT INTO role (id,deleted,roletype_id) VALUES (1,FALSE,1);

INSERT INTO "user"(id, deleted, disabledcause, enabled, enablinghash, firstlogin, hashcost, loginattempts, password, username, role_id)
    VALUES (nextval('user_seq'), FALSE, NULL, TRUE,NULL,FALSE,NULL,0,'dca3ab8d1c098cb98c8a9ea6c7f1b645132428a625f4d5a176ee14ef778fb2d4cdc874e6364bb3ef300e301c0f66206b46b2cafdd1c27dec4c139be63f9dc7d8', 'admin',1);

--Daily Devotional Functions
--Function: Create Daily Devotional
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'dailyDevotional', FALSE, 'CreateDailyDevotional', 'devotional', 'createDailyDevotional', NULL, '/devotional/create');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Delete Daily Devotional
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'dailyDevotional', FALSE, 'DeleteDailyDevotional', 'devotional', 'deleteDailyDevotional', NULL, '/devotional/delete');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Modify Daily Devotional
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'dailyDevotional', FALSE, 'ModifyDailyDevotional', 'devotional', 'modifyDailyDevotional', NULL, '/devotional/modify');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Search Daily Devotional
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'dailyDevotional', FALSE, 'SearchDailyDevotional', 'devotional', 'searchDailyDevotional', NULL , '/devotional/search');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: List Daily Devotional
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'dailyDevotional', FALSE, 'ListDailyDevotional', 'devotional', 'listDailyDevotional', NULL, '/devotional/list');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Bible Studies Functions
--Function: Create Bible Study
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'bibleStudy', FALSE, 'CreateBibleStudy', 'devotional', 'createBibleStudy', NULL, '/bibleStudy/create');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Delete Bible Study
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'bibleStudy', FALSE, 'DeleteBibleStudy', 'devotional', 'deleteBibleStudy', NULL, '/bibleStudy/delete');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Modify Bible Study
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'bibleStudy', FALSE, 'ModifyBibleStudy', 'devotional', 'modifyBibleStudy', NULL, '/bibleStudy/modify');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Search Bible Study
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'bibleStudy', FALSE, 'SearchBibleStudy', 'devotional', 'searchBibleStudy', NULL , '/bibleStudy/search');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: List Bible Study
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'bibleStudy', FALSE, 'ListBibleStudy', 'devotional', 'listBibleStudy', NULL, '/bibleStudy/list');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Role Type Parameters
--Function: Create Role Type
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'roleType', FALSE, 'CreateRoleType', 'configuration', 'createRoleType', NULL, '/roleType/create');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Delete Role Type
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'roleType', FALSE, 'DeleteRoleType', 'configuration', 'deleteRoleType', NULL, '/roleType/delete');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Modify Role Type
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'roleType', FALSE, 'ModifyRoleType', 'configuration', 'modifyRoleType', NULL, '/roleType/modify');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Search Role Type
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'roleType', FALSE, 'SearchRoleType', 'configuration', 'searchRoleType', NULL, '/roleType/search');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: List Role Type
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'roleType', FALSE, 'ListRoleType', 'configuration', 'listRoleType', NULL, '/roleType/list');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Parameter Functions
--Function: Create Parameter
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'parameter', FALSE, 'CreateParameter', 'configuration', 'createParameter', NULL, '/parameter/create');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Modify Parameter
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'parameter', FALSE, 'ModifyParameter', 'configuration', 'modifyParameter', NULL, '/parameter/modify');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Delete Parameter
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'parameter', FALSE, 'DeleteParameter', 'configuration', 'deleteParameter', NULL, '/parameter/delete');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Search Parameter
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'parameter', FALSE, 'SearchParameter', 'configuration', 'searchParameter', NULL, '/parameter/search');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: List Parameter
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'parameter', FALSE, 'ListParameter', 'configuration', 'listParameter', NULL, '/parameter/list');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Mail Functions
--Function: Create Mail
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'mail', FALSE, 'CreateMail', 'configuration', 'createMail', NULL, '/mail/create');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Modify Mail
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'mail', FALSE, 'ModifyMail', 'configuration', 'modifyMail', NULL, '/mail/modify');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Delete Mail
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'mail', FALSE, 'DeleteMail', 'configuration', 'deleteMail', NULL, '/mail/delete');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Search Mail
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'mail', FALSE, 'SearchMail', 'configuration', 'searchMail', NULL, '/mail/search');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: List Mail
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'mail', FALSE, 'ListeMail', 'configuration', 'listMail', NULL, '/mail/list');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Trigger  Mail
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'mail', FALSE, 'TriggerMail', 'configuration', 'triggerMail', NULL, '/mail/trigger');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--DocumentType Functions
--Function: Create DocumentType
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'documentType', FALSE, 'CreateDocumentType', 'configuration', 'createDocumentType', NULL, '/documentType/create');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Modify DocumentType
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'documentType', FALSE, 'ModifyDocumentType', 'configuration', 'modifyDocumentType', NULL, '/documentType/modify');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Delete DocumentType
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'documentType', FALSE, 'DeleteDocumentType', 'configuration', 'deleteDocumentType', NULL, '/documentType/delete');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Search DocumentType
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'documentType', FALSE, 'SearchDocumentType', 'configuration', 'searchDocumentType', NULL, '/documentType/search');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: List DocumentType
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'documentType', FALSE, 'ListDocumentType', 'configuration', 'listDocumentType', NULL, '/documentType/list');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--User Functions
--Function: Create User
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'user', FALSE, 'CreateUser', 'configuration', 'createUser', NULL, '/user/create');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Modify User
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'user', FALSE, 'DeleteUser', 'configuration', 'deleteUser', NULL, '/user/delete');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Search User
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'user', FALSE, 'SearchUser', 'configuration', 'searchUser', NULL, '/user/search');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: List User
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'user', FALSE, 'ListUser', 'configuration', 'listUser', NULL, '/user/list');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Enable User
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'user', FALSE, 'EnableUser', 'configuration', 'enableUser', NULL, '/user/enable');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Disable User
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'user', FALSE, 'DisableUser', 'configuration', 'disableUser', NULL, '/user/delete');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id)SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Modify User Functions
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'user', FALSE, 'ModifyUserFunctions', 'configuration', 'modifyUserFunctions', NULL, '/user/modifyUserFunctions');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id) SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;

--Function: Reset Any User Password
INSERT INTO function (id, actionname, deleted, description, menuname, name, submenuname, uri)
  VALUES (nextval('function_seq'), 'user', FALSE, 'ResetAnyUserPassword', 'configuration', 'resetAnyUserPassword', NULL, '/user/resetAnyUserPassword');
-- Role types function
INSERT INTO roletypefunction (id, deleted, disabledcause, enabled, function_id, roletype_id) VALUES (nextval('roletypefunction_seq'), false, NULL, true, currval('function_seq'), 1);
-- Role types
INSERT INTO rolefunction (id, deleted, disabledcause, enabled, function_id, role_id) SELECT nextval('rolefunction_seq'), false, NULL, true, currval('function_seq'), id_role_type.id FROM (SELECT distinct(r.id) FROM role r WHERE r.roletype_id = 1) as id_role_type;
