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

CREATE TABLE mapeamentos_xslt (id INTEGER, description VARCHAR, xslt VARCHAR);

ALTER TABLE mapeamentos_xslt OWNER TO feb;

CREATE VIEW repositorios_view AS select * from repositorios,info_repositorios
WHERE id = id_repositorio;
ALTER VIEW repositorios_view OWNER TO feb;

ALTER TABLE info_repositorios DROP COLUMN nome_na_federacao;



DROP VIEW repositorios_view;

DROP TABLE info_repositorios;

DROP TABLE repositorios CASCADE;

CREATE TABLE repositorios (
  id integer DEFAULT nextval('repositorios_id_seq'::regclass) NOT NULL,
    nome character varying(45) NOT NULL,
    descricao character varying(455) DEFAULT NULL::character varying,
    data_ultima_atualizacao timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone,
    periodicidade_horas integer,
    url_or_ip character varying(200) NOT NULL,
    tipo_sincronizacao character varying(45) DEFAULT 'OAI'::character varying,
    padrao_metadados integer,
    tipo_mapeamento_id integer DEFAULT 1 NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    set character varying(45)
);

ALTER TABLE public.repositorios OWNER TO feb;
