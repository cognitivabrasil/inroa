
--------------------------DADOS SUBFEDERAÇÕES-------------------------
ALTER TABLE dados_subfederacoes
   ADD COLUMN data_xml timestamp without time zone NOT NULL DEFAULT '0001-01-01 00:00:00';

-------------------------LRU-------------------------
CREATE TABLE consultas(
   consulta character varying NOT NULL,
   ids text,
   CONSTRAINT consultas_pkey PRIMARY KEY (consulta)
) WITH (OIDS = FALSE);
ALTER TABLE consultas OWNER TO feb;

-------------------------DOCUMENTOS-------------------------
ALTER TABLE documentos 
    ADD COLUMN deleted BOOLEAN DEFAULT false,
    ADD COLUMN obaaXml VARCHAR,

    ADD CONSTRAINT excluidos FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;
--adiciona os documentos exluidos na tabela
UPDATE documentos d SET deleted=true FROM
(
	SELECT obaa_entry FROM excluidos
) AS ex
WHERE d.obaa_entry=ex.obaa_entry;




-------------------------REPOSITORIOS-------------------------
ALTER TABLE repositorios             
    ADD COLUMN data_ultima_atualizacao timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone,
    ADD COLUMN periodicidade_horas integer,
    ADD COLUMN url_or_ip character varying(200) NOT NULL DEFAULT '',
    ADD COLUMN padrao_metadados integer,
    ADD COLUMN mapeamento_id integer DEFAULT 1 NOT NULL,
    ADD COLUMN metadata_prefix character varying(45),
    ADD COLUMN name_space character varying(45),
    ADD COLUMN set character varying(45),
    ADD COLUMN data_xml timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone NOT NULL,
    ADD COLUMN mapeamento_id INT,

    ADD CONSTRAINT mapeamento FOREIGN KEY (mapeamento_id) REFERENCES mapeamentos(id),
    ADD CONSTRAINT padraomet FOREIGN KEY (padrao_metadados) REFERENCES padraometadados(id) ON UPDATE CASCADE;
    
--ALTER TABLE ONLY repositorios
--    ADD CONSTRAINT pki_rep PRIMARY KEY (id);

--adiciona os dados da info_repositorios na repositorios
UPDATE repositorios r SET id=id_repositorio, data_ultima_atualizacao=ir.data_ultima_atualizacao, 
periodicidade_horas=ir.periodicidade_horas, url_or_ip=ir.url_or_ip, padrao_metadados=ir.padrao_metadados, 
mapeamento_id=tipo_mapeamento_id, metadata_prefix=ir.metadata_prefix, name_space=ir.name_space, set=ir.set, 
data_xml=ir.data_xml FROM
(
	SELECT id_repositorio, data_ultima_atualizacao, periodicidade_horas, url_or_ip, 
	padrao_metadados, tipo_mapeamento_id, metadata_prefix, name_space, set, data_xml 
	FROM info_repositorios
) AS ir
WHERE r.id=ir.id_repositorio;



CREATE INDEX fki_padraometadados ON repositorios USING btree (padrao_metadados);
CREATE INDEX fki_mapeamento ON repositorios USING btree (mapeamento_id);

-------------------------AUTORES-------------------------
CREATE TABLE autores
(
  nome character varying NOT NULL,
  documento integer NOT NULL,
  CONSTRAINT pki_autores PRIMARY KEY (nome, documento),
  CONSTRAINT fki_documentos FOREIGN KEY (documento)
      REFERENCES documentos (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE autores OWNER TO feb;

CREATE INDEX fki_fki_documentos
  ON autores
  USING btree
  (documento);

--popular tabela autores com dados da objetos
INSERT INTO autores (nome, documento) 
	SELECT o.valor, d.id FROM objetos o, documentos d WHERE o.atributo='obaaEntity' AND d.id=o.documento;

-------------------------MAPEAMENTOS-------------------------
DROP TABLE mapeamentos;

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

DROP TABLE tipomapeamento;
DROP TABLE excluidos;
DROP TABLE info_repositorios;
DROP TABLE mapeamentocomposto;


-- CRIAR A DATABASE DE TESTE
CREATE DATABASE federacao_teste WITH TEMPLATE federacao ENCODING 'UTF-8';
ALTER DATABASE federacao OWNER TO feb;

--Padrao de metadados em texto--

ALTER TABLE padraometadados
   ADD COLUMN atributos text;
    --TEMPORARIO: concatena todos os valores da tabela atributos e coloca na text atributos do padraometadados
UPDATE padraometadados p set atributos=(SELECT array_to_string(array(SELECT atributo FROM atributos a WHERE a.id_padrao = p.id and a.id>0), ';'));

DROP TABLE atributos;

DROP SEQUENCE excluidos_id_seq;
DROP SEQUENCE atributos_id_seq;