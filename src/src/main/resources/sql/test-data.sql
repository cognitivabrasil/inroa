INSERT INTO usuarios (id,login,senha,nome,permissions) VALUES (1,'admin','698dc19d489c4e4db73e28a713eab07b','Administrador da federacao','PERM_MANAGE_USERS,PERM_MANAGE_MAPPING');
INSERT INTO usuarios (id,login,senha,nome) VALUES (2,'admin2','698dc19d489c4e4db73e28a713eab07b','Administrador da federacao');

INSERT INTO padraometadados (id,nome,metadata_prefix,name_space,atributos) VALUES (1,'lom','oai_lom','lom','');
INSERT INTO padraometadados (id,nome,metadata_prefix,name_space,atributos) VALUES (2,'lom2','oai_lom','lom','');
INSERT INTO padraometadados (id,nome,metadata_prefix,name_space,atributos) VALUES (3,'lom3','oai_lom','lom','');

INSERT INTO mapeamentos (id,nome,descricao,xslt,padrao_id) VALUES (1,'Padrao','Blabla','xslt',1);
INSERT INTO mapeamentos (id,nome,descricao,xslt,padrao_id) VALUES (2,'Padrao2','Blabla','xslt',1);


INSERT INTO repositorios (id,nome,descricao,url_or_ip,data_ultima_atualizacao,data_xml,metadata_prefix,padrao_metadados,mapeamento_id,name_space) 
VALUES (1,'Cesta','dfsd','http://cesta2.cinted.ufrgs.br/oai/request','2014-04-28 07:51:22.48377','1984-08-21T07:35:00Z','lom',1,1,'lom');

INSERT INTO repositorios (id,nome,descricao,url_or_ip,data_ultima_atualizacao,padrao_metadados,mapeamento_id,metadata_prefix,name_space)
VALUES (2,'Teste2','dfsd','http://','1984-08-21 00:00:00',1,1,'lom','lom');

INSERT INTO repositorios (id,nome,descricao,url_or_ip,data_ultima_atualizacao,padrao_metadados,mapeamento_id,metadata_prefix,name_space) 
VALUES (3,'Teste3','dfsd','http://','9014-08-21 07:51:22.48377',1,1,'lom','lom');

INSERT INTO repositorios (id,nome,descricao,url_or_ip,data_ultima_atualizacao,padrao_metadados,mapeamento_id,metadata_prefix,name_space)
VALUES (4,'marcos','x','htttp://x','2014-04-01 23:59:59.48377',1,1,'obaa','obaa');


INSERT INTO dados_subfederacoes (id,nome,url,data_xml,data_ultima_atualizacao,descricao)
VALUES (1,'UFRGS','http://','2012-03-09 07:51:22.48377','2012-03-09 07:51:22.48377','Bla');
 
INSERT INTO dados_subfederacoes (id,nome,url,data_xml,data_ultima_atualizacao,descricao)
VALUES (2,'Ble','http://','2012-03-09 07:51:22.48377','2012-03-09 07:51:22.48377','Bla');
    
INSERT INTO dados_subfederacoes (id,nome,url,data_xml,data_ultima_atualizacao,descricao)
VALUES (3,'Bla','http://','2012-03-09 07:51:22.48377','2012-03-09 07:51:22.48377','Bla');
   
INSERT INTO dados_subfederacoes (id,nome,url,data_xml,data_ultima_atualizacao,descricao)
VALUES (4,'marcos','http://','2012-03-09 07:51:22.48377','2012-03-09 07:51:22.48377','Bla');


INSERT INTO repositorios_subfed (id,nome,id_subfed) VALUES (1,'RepUfrgs1',1);

INSERT INTO documentos (id,obaa_entry, id_repositorio,created,deleted)
VALUES (1,'oai:cesta2.cinted.ufrgs.br:123456789/57',1,'2012-03-09 07:51:22.48377','false');

INSERT INTO documentos (id,obaa_entry, id_repositorio,created,deleted)
VALUES (2,'dois',1,'2012-03-09 07:51:22.48377','false');
 
INSERT INTO documentos (id,obaa_entry, id_repositorio,created,deleted)
VALUES (3,'tres',1,'2012-03-09 07:51:22.48377','false');
 
INSERT INTO documentos (id,obaa_entry, id_repositorio,created,deleted)
VALUES (4,'quatro',1,'2012-03-09 07:51:22.48377','true');
 
INSERT INTO documentos (id,obaa_entry, id_repositorio,created,deleted)
VALUES (5,'cinco',1,'2012-03-09 07:51:22.48377','false');
 
INSERT INTO documentos (id,obaa_entry, id_rep_subfed,created,deleted)
VALUES (6,'cinco2',1,'2012-03-09 07:51:22.48377','false');
INSERT INTO documentos (id,obaa_entry, id_rep_subfed,created,deleted)
VALUES (7,'cinco3',1,'2012-03-09 07:51:22.48377','false');

INSERT INTO searches (text,time) VALUES ('jorjao','2012-07-09 12:06:02');
INSERT INTO searches (text,time) VALUES ('jorjao','2012-07-09 12:06:01');
INSERT INTO searches (text,time) VALUES ('jorjao','2012-07-09 12:06:00');
INSERT INTO searches (text,time) VALUES ('teste','2012-07-09 12:06:06');
INSERT INTO searches (text,time) VALUES ('teste','2012-07-09 12:06:07');
INSERT INTO searches (text,time) VALUES ('bla','2012-06-29 12:06:01');
INSERT INTO searches (text,time) VALUES ('bla','2012-06-29 12:06:02');
INSERT INTO searches (text,time) VALUES ('jorjao','2011-12-09 12:06:01');