
--------------------------DADOS SUBFEDERAÇÕES-------------------------
ALTER TABLE dados_subfederacoes DROP COLUMN data_xml;
ALTER TABLE dados_subfederacoes ADD COLUMN data_xml character varying;

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
    ADD COLUMN data_ultima_atualizacao timestamp without time zone,
    ADD COLUMN periodicidade_horas integer,
    ADD COLUMN url_or_ip character varying(200) NOT NULL DEFAULT '',
    ADD COLUMN padrao_metadados integer,
    ADD COLUMN mapeamento_id integer DEFAULT 1 NOT NULL,
    ADD COLUMN metadata_prefix character varying(45),
    ADD COLUMN name_space character varying(45),
    ADD COLUMN set character varying(45),
    ADD COLUMN data_xml timestamp without time zone,
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


INSERT INTO padraometadados VALUES(1, 'LOM', 'lom', 'lom', '');
INSERT INTO padraometadados VALUES(2, 'Dublin Core', 'oai_dc', 'oai_dc', '');

INSERT INTO mapeamentos VALUES (2, 'DC Padrão', 'Mapeamento Dublin Core padrão',
'<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:dc="http://purl.org/dc/elements/1.1/"
xmlns:obaa="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/lomv1.0/lom.xsd"
xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
xmlns:oai_pmh="http://www.openarchives.org/OAI/2.0/"
exclude-result-prefixes="dc"
xpath-default-namespace="http://www.openarchives.org/OAI/2.0/">

<xsl:output method="xml" encoding="UTF-8"/>
<xsl:template match="/">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="OAI-PMH">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="ListRecords">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="responseDate">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="request">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="record">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="header">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="metadata">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="oai_dc:dc">
<obaa:obaa  xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/obaav1.0/obaa.xsd">
	<obaa:general>
	
	<xsl:for-each select="dc:title">
		<obaa:title>
			<xsl:value-of select="."/>
		</obaa:title>
	</xsl:for-each>

		<obaa:identifier>
			<obaa:catalog>NULL</obaa:catalog>
			<obaa:entry><xsl:value-of select="./dc:identifier[1]"/></obaa:entry>
		</obaa:identifier>

	<xsl:for-each select="dc:description">
		<obaa:description>
			<xsl:value-of select="."/>
		</obaa:description>
	</xsl:for-each>

	<xsl:for-each select="dc:coverage">
		<obaa:coverage>
			<xsl:value-of select="."/>
		</obaa:coverage>
	</xsl:for-each>

	<xsl:for-each select="dc:subject">
		<obaa:keyword>
			<xsl:value-of select="."/>
		</obaa:keyword>
	</xsl:for-each>
	
	</obaa:general>	

	<obaa:lifeCycle>
		<xsl:for-each select="dc:creator">
			<obaa:contribute>
				<obaa:role>author</obaa:role>
				<obaa:entity><xsl:value-of select="."/></obaa:entity>
				<obaa:date><xsl:value-of select="../dc:date[1]"/></obaa:date>
			</obaa:contribute>
		</xsl:for-each>
		<xsl:for-each select="dc:contributor">
			<obaa:contribute>
				<obaa:role>unknown</obaa:role>
				<obaa:entity><xsl:value-of select="."/></obaa:entity>
			</obaa:contribute>
		</xsl:for-each>
		<xsl:for-each select="dc:publisher">
			<obaa:contribute>
				<obaa:role>publisher</obaa:role>
				<obaa:entity><xsl:value-of select="."/></obaa:entity>
			</obaa:contribute>
		</xsl:for-each>
	
	</obaa:lifeCycle>
	
	<obaa:technical>
		<xsl:for-each select="dc:identifier">
			<obaa:location><xsl:value-of select="."/></obaa:location>
		</xsl:for-each>
	</obaa:technical>

	<obaa:rights>
		<obaa:description><xsl:value-of select="dc:right"/></obaa:description>
	</obaa:rights>
</obaa:obaa>
</xsl:template>

</xsl:stylesheet>
'
, 2);

INSERT INTO mapeamentos VALUES (1, 'LOM padrão', 'Mapeamento LOM padrão',
'<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:dc="http://purl.org/dc/elements/1.1/"
xmlns:lom="http://ltsc.ieee.org/xsd/LOM"
xmlns:obaa="http://ltsc.ieee.org/xsd/LOM"
xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
xmlns:oai_pmh="http://www.openarchives.org/OAI/2.0/"
exclude-result-prefixes="dc"
xpath-default-namespace="http://www.openarchives.org/OAI/2.0/">

<xsl:output method="xml" encoding="UTF-8"/>
<xsl:template match="/">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="OAI-PMH">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="ListRecords">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="responseDate">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="request">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="record">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="header">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="metadata">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="lom:lom">
<obaa:obaa xmlns:obaa="http://ltsc.ieee.org/xsd/LOM" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/obaav1.0/obaa.xsd">
	<xsl:apply-templates/>
</obaa:obaa>

</xsl:template>

<xsl:template match="@*|node()">
  <xsl:copy>
    <xsl:apply-templates select="@*|node()"/>
  </xsl:copy>
</xsl:template>

</xsl:stylesheet>
', 1);

DROP TABLE tipomapeamento;
DROP TABLE excluidos;
DROP TABLE info_repositorios;
DROP TABLE mapeamentocomposto;


-- CRIAR A DATABASE DE TESTE
    CREATE DATABASE federacao_teste WITH TEMPLATE federacao ENCODING 'UTF-8';
    ALTER DATABASE federacao_teste OWNER TO feb;

--Padrao de metadados em texto--

ALTER TABLE padraometadados
   ADD COLUMN atributos text;
    --TEMPORARIO: concatena todos os valores da tabela atributos e coloca na text atributos do padraometadados
UPDATE padraometadados p set atributos=(SELECT array_to_string(array(SELECT atributo FROM atributos a WHERE a.id_padrao = p.id and a.id>0), ';'));

DROP TABLE atributos;

DROP SEQUENCE excluidos_id_seq;
DROP SEQUENCE atributos_id_seq;
 
---

ALTER TABLE documentos
   ALTER COLUMN deleted SET DEFAULT false;
ALTER TABLE documentos
   ALTER COLUMN deleted SET NOT NULL;

--- 26/04/12
ALTER TABLE dados_subfederacoes
   ALTER COLUMN data_ultima_atualizacao DROP DEFAULT;


--- 9/5/12
ALTER TABLE usuarios ADD COLUMN permissions VARCHAR(200);
UPDATE usuarios SET permissions = 'PERM_MANAGE_USERS,PERM_UPDATE,PERM_MANAGE_REP,PERM_MANAGE_METADATA,PERM_MANAGE_MAPPINGS,PERM_CHANGE_DATABASE,PERM_VIEW_STATISTICS' WHERE login = 'admin';

ALTER TABLE usuarios ADD COLUMN role VARCHAR(20);
UPDATE usuarios SET role = 'Administrador' WHERE login = 'admin';

-- 21/05/2012
CREATE SEQUENCE autores_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE autores_id_seq
  OWNER TO feb;


ALTER TABLE autores ADD COLUMN id integer;
ALTER TABLE autores ALTER COLUMN id SET NOT NULL;
ALTER TABLE autores ALTER COLUMN id SET DEFAULT nextval('autores_id_seq'::regclass);


-- 29-6-2012
INSERT INTO mapeamentos VALUES (3, 'OBAA Padrão', 'Mapeamento OBAA padrão, só copia os dados',
'<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:obaa="http://ltsc.ieee.org/xsd/LOM"
xmlns:oai_pmh="http://www.openarchives.org/OAI/2.0/"
xpath-default-namespace="http://www.openarchives.org/OAI/2.0/">
<xsl:template match="@*|node()">
  <xsl:copy>
    <xsl:apply-templates select="@*|node()"/>
  </xsl:copy>
</xsl:template></xsl:stylesheet>', 3);

