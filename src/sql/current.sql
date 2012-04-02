ALTER TABLE info_repositorios ADD COLUMN data_xml timestamp without time zone NOT NULL DEFAULT '0001-01-01 00:00:00';

ALTER TABLE dados_subfederacoes
   ADD COLUMN data_xml timestamp without time zone NOT NULL DEFAULT '0001-01-01 00:00:00';

CREATE TABLE consultas(
   consulta character varying NOT NULL,
   ids text,
   CONSTRAINT consultas_pkey PRIMARY KEY (consulta)
) WITH (OIDS = FALSE);

ALTER TABLE consultas OWNER TO feb;

ALTER TABLE documentos ADD COLUMN deleted BOOLEAN DEFAULT false;

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
    padrao_metadados integer,
    mapeamento_id integer DEFAULT 1 NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    set character varying(45),
    data_xml timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone NOT NULL
);

ALTER TABLE public.repositorios OWNER TO feb;

ALTER TABLE ONLY repositorios
    ADD CONSTRAINT pki_rep PRIMARY KEY (id);

ALTER TABLE ONLY documentos
    ADD CONSTRAINT excluidos FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY documentos
    ADD CONSTRAINT repositorio FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE INDEX fki_padraometadados ON repositorios USING btree (padrao_metadados);


CREATE INDEX fki_mapeamento ON repositorios USING btree (mapeamento_id);


ALTER TABLE ONLY repositorios
    ADD CONSTRAINT padraomet FOREIGN KEY (padrao_metadados) REFERENCES padraometadados(id) ON UPDATE CASCADE;


ALTER TABLE ONLY repositorios
    ADD CONSTRAINT mapeamento FOREIGN KEY (mapeamento_id) REFERENCES mapeamentos(id);

-- ADIÇÃO DA TABELA mapeamentos
CREATE SEQUENCE mapeamentos_id_seq
   INCREMENT 1
   START 1;
ALTER TABLE mapeamentos_id_seq OWNER TO feb;

DROP TABLE mapeamentos;
DROP TABLE tipomapeamento;

CREATE TABLE mapeamentos
(
   id integer DEFAULT nextval('mapeamentos_id_seq'::regclass) NOT NULL, 
   nome character varying(45) NOT NULL, 
   descricao character varying(200) NOT NULL, 
   xslt text NOT NULL, 
   padrao_id integer NOT NULL, 
   CONSTRAINT mapeamento_pkey PRIMARY KEY (id), 
   CONSTRAINT padraometadados FOREIGN KEY (padrao_id) REFERENCES padraometadados (id) ON UPDATE CASCADE ON DELETE CASCADE
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE mapeamentos OWNER TO feb;

 ALTER TABLE repositorios ADD COLUMN mapeamento_id INT;

-- CRIAR A DATABASE DE TESTE
CREATE DATABASE federacao_teste WITH TEMPLATE federacao ENCODING 'UTF-8';
ALTER DATABASE federacao OWNER TO feb;