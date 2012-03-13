ALTER TABLE info_repositorios ADD COLUMN data_xml timestamp without time zone NOT NULL DEFAULT '0001-01-01 00:00:00';

ALTER TABLE dados_subfederacoes
   ADD COLUMN data_xml timestamp without time zone NOT NULL DEFAULT '0001-01-01 00:00:00';

CREATE TABLE consultas(
   consulta character varying NOT NULL,
   ids text,
   CONSTRAINT consultas_pkey PRIMARY KEY (consulta)
) WITH (OIDS = FALSE);

ALTER TABLE consultas OWNER TO feb;

ALTER TABLE documentos ADD COLUMN deleted BOOLEAN;

ALTER TABLE documentos ADD COLUMN obaaXml VARCHAR;

CREATE VIEW repositorios_view AS select * from repositorios,info_repositorios
WHERE id = id_repositorio;

ALTER VIEW repositorios_view OWNER TO feb;

CREATE TABLE mapeamentos_xslt (id INTEGER, description VARCHAR, xslt VARCHAR);

ALTER TABLE mapeamentos_xslt OWNER TO feb;
