--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.4
-- Dumped by pg_dump version 9.1.4
-- Started on 2012-06-19 15:53:01 BRT

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2037 (class 0 OID 0)
-- Dependencies: 170
-- Name: padraometadados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

--
-- TOC entry 2038 (class 0 OID 0)
-- Dependencies: 181
-- Name: stopwords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

SELECT pg_catalog.setval('stopwords_id_seq', 179, true);



--
-- TOC entry 2031 (class 0 OID 16455)
-- Dependencies: 169
-- Data for Name: padraometadados; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO padraometadados VALUES (1, 'LOM', 'lom', 'lom', '');
INSERT INTO padraometadados VALUES (2, 'Dublin Core', 'oai_dc', 'oai_dc', '');
INSERT INTO padraometadados VALUES (3, 'OBAA', 'obaa', 'obaa', '');


--
-- TOC entry 2034 (class 0 OID 18259)
-- Dependencies: 186 2031
-- Data for Name: mapeamentos; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO mapeamentos VALUES (2, 'DC Padrão', 'Mapeamento Dublin Core padrão', '<?xml version="1.0" encoding="UTF-8"?>
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
','', 2);
INSERT INTO mapeamentos VALUES (1, 'LOM padrão', 'Mapeamento LOM padrão', '<?xml version="1.0" encoding="UTF-8"?>
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
','', 1);

INSERT INTO mapeamentos VALUES (3, 'OBAA Padrão', 'Mapeamento OBAA padrão',
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
</xsl:template></xsl:stylesheet>','', 3);


--
-- TOC entry 2032 (class 0 OID 16514)
-- Dependencies: 180
-- Data for Name: stopwords; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO stopwords VALUES (1, 'a');
INSERT INTO stopwords VALUES (2, 'à');
INSERT INTO stopwords VALUES (3, 'agora');
INSERT INTO stopwords VALUES (4, 'ainda');
INSERT INTO stopwords VALUES (5, 'além');
INSERT INTO stopwords VALUES (6, 'alguns');
INSERT INTO stopwords VALUES (7, 'ano');
INSERT INTO stopwords VALUES (8, 'anos');
INSERT INTO stopwords VALUES (9, 'antes');
INSERT INTO stopwords VALUES (10, 'ao');
INSERT INTO stopwords VALUES (11, 'aos');
INSERT INTO stopwords VALUES (12, 'apenas');
INSERT INTO stopwords VALUES (13, 'após');
INSERT INTO stopwords VALUES (14, 'aqui');
INSERT INTO stopwords VALUES (15, 'as');
INSERT INTO stopwords VALUES (16, 'às');
INSERT INTO stopwords VALUES (17, 'assim');
INSERT INTO stopwords VALUES (18, 'até');
INSERT INTO stopwords VALUES (19, 'bem');
INSERT INTO stopwords VALUES (20, 'bom');
INSERT INTO stopwords VALUES (21, 'cada');
INSERT INTO stopwords VALUES (22, 'caso');
INSERT INTO stopwords VALUES (23, 'coisa');
INSERT INTO stopwords VALUES (24, 'com');
INSERT INTO stopwords VALUES (25, 'como');
INSERT INTO stopwords VALUES (26, 'conta');
INSERT INTO stopwords VALUES (27, 'contra');
INSERT INTO stopwords VALUES (28, 'da');
INSERT INTO stopwords VALUES (29, 'dar');
INSERT INTO stopwords VALUES (30, 'das');
INSERT INTO stopwords VALUES (31, 'de');
INSERT INTO stopwords VALUES (32, 'depois');
INSERT INTO stopwords VALUES (33, 'desde');
INSERT INTO stopwords VALUES (34, 'deve');
INSERT INTO stopwords VALUES (35, 'dia');
INSERT INTO stopwords VALUES (36, 'dias');
INSERT INTO stopwords VALUES (37, 'disse');
INSERT INTO stopwords VALUES (38, 'diz');
INSERT INTO stopwords VALUES (39, 'do');
INSERT INTO stopwords VALUES (40, 'dois');
INSERT INTO stopwords VALUES (41, 'dos');
INSERT INTO stopwords VALUES (42, 'duas');
INSERT INTO stopwords VALUES (43, 'durante');
INSERT INTO stopwords VALUES (44, 'e');
INSERT INTO stopwords VALUES (45, 'é');
INSERT INTO stopwords VALUES (46, 'ela');
INSERT INTO stopwords VALUES (47, 'ele');
INSERT INTO stopwords VALUES (48, 'eles');
INSERT INTO stopwords VALUES (49, 'em');
INSERT INTO stopwords VALUES (50, 'enquanto');
INSERT INTO stopwords VALUES (51, 'então');
INSERT INTO stopwords VALUES (52, 'entre');
INSERT INTO stopwords VALUES (53, 'era');
INSERT INTO stopwords VALUES (54, 'essa');
INSERT INTO stopwords VALUES (55, 'esse');
INSERT INTO stopwords VALUES (56, 'esta');
INSERT INTO stopwords VALUES (57, 'está');
INSERT INTO stopwords VALUES (58, 'estão');
INSERT INTO stopwords VALUES (59, 'estava');
INSERT INTO stopwords VALUES (60, 'este');
INSERT INTO stopwords VALUES (61, 'eu');
INSERT INTO stopwords VALUES (62, 'eua');
INSERT INTO stopwords VALUES (63, 'fato');
INSERT INTO stopwords VALUES (64, 'faz');
INSERT INTO stopwords VALUES (65, 'fazer');
INSERT INTO stopwords VALUES (66, 'fez');
INSERT INTO stopwords VALUES (67, 'ficou');
INSERT INTO stopwords VALUES (68, 'foi');
INSERT INTO stopwords VALUES (69, 'fora');
INSERT INTO stopwords VALUES (70, 'foram');
INSERT INTO stopwords VALUES (71, 'forma');
INSERT INTO stopwords VALUES (72, 'há');
INSERT INTO stopwords VALUES (73, 'havia');
INSERT INTO stopwords VALUES (74, 'hoje');
INSERT INTO stopwords VALUES (75, 'isso');
INSERT INTO stopwords VALUES (76, 'já');
INSERT INTO stopwords VALUES (77, 'lado');
INSERT INTO stopwords VALUES (78, 'maior');
INSERT INTO stopwords VALUES (79, 'mais');
INSERT INTO stopwords VALUES (80, 'mas');
INSERT INTO stopwords VALUES (81, 'me');
INSERT INTO stopwords VALUES (82, 'meio');
INSERT INTO stopwords VALUES (83, 'melhor');
INSERT INTO stopwords VALUES (84, 'menos');
INSERT INTO stopwords VALUES (85, 'meses');
INSERT INTO stopwords VALUES (86, 'mesma');
INSERT INTO stopwords VALUES (87, 'mesmo');
INSERT INTO stopwords VALUES (88, 'meu');
INSERT INTO stopwords VALUES (89, 'minha');
INSERT INTO stopwords VALUES (90, 'muito');
INSERT INTO stopwords VALUES (91, 'na');
INSERT INTO stopwords VALUES (92, 'nada');
INSERT INTO stopwords VALUES (93, 'não');
INSERT INTO stopwords VALUES (94, 'nas');
INSERT INTO stopwords VALUES (95, 'nem');
INSERT INTO stopwords VALUES (96, 'neste');
INSERT INTO stopwords VALUES (97, 'no');
INSERT INTO stopwords VALUES (98, 'nome');
INSERT INTO stopwords VALUES (99, 'nos');
INSERT INTO stopwords VALUES (100, 'nós');
INSERT INTO stopwords VALUES (101, 'nova');
INSERT INTO stopwords VALUES (102, 'novo');
INSERT INTO stopwords VALUES (103, 'num');
INSERT INTO stopwords VALUES (104, 'numa');
INSERT INTO stopwords VALUES (105, 'o');
INSERT INTO stopwords VALUES (106, 'onde');
INSERT INTO stopwords VALUES (107, 'ontem');
INSERT INTO stopwords VALUES (108, 'os');
INSERT INTO stopwords VALUES (109, 'ou');
INSERT INTO stopwords VALUES (110, 'outra');
INSERT INTO stopwords VALUES (111, 'outras');
INSERT INTO stopwords VALUES (112, 'outro');
INSERT INTO stopwords VALUES (113, 'outros');
INSERT INTO stopwords VALUES (114, 'para');
INSERT INTO stopwords VALUES (115, 'parte');
INSERT INTO stopwords VALUES (116, 'partir');
INSERT INTO stopwords VALUES (117, 'pela');
INSERT INTO stopwords VALUES (118, 'pelo');
INSERT INTO stopwords VALUES (119, 'pelos');
INSERT INTO stopwords VALUES (120, 'pode');
INSERT INTO stopwords VALUES (121, 'podem');
INSERT INTO stopwords VALUES (122, 'poder');
INSERT INTO stopwords VALUES (123, 'pontos');
INSERT INTO stopwords VALUES (124, 'por');
INSERT INTO stopwords VALUES (125, 'porque');
INSERT INTO stopwords VALUES (126, 'pouco');
INSERT INTO stopwords VALUES (127, 'qual');
INSERT INTO stopwords VALUES (128, 'qualquer');
INSERT INTO stopwords VALUES (129, 'quando');
INSERT INTO stopwords VALUES (130, 'quanto');
INSERT INTO stopwords VALUES (131, 'quase');
INSERT INTO stopwords VALUES (132, 'quatro');
INSERT INTO stopwords VALUES (133, 'que');
INSERT INTO stopwords VALUES (134, 'quem');
INSERT INTO stopwords VALUES (135, 'quer');
INSERT INTO stopwords VALUES (136, 'r');
INSERT INTO stopwords VALUES (137, 'são');
INSERT INTO stopwords VALUES (138, 'se');
INSERT INTO stopwords VALUES (139, 'segundo');
INSERT INTO stopwords VALUES (140, 'seja');
INSERT INTO stopwords VALUES (141, 'sem');
INSERT INTO stopwords VALUES (142, 'sempre');
INSERT INTO stopwords VALUES (143, 'sendo');
INSERT INTO stopwords VALUES (144, 'ser');
INSERT INTO stopwords VALUES (145, 'será');
INSERT INTO stopwords VALUES (146, 'serão');
INSERT INTO stopwords VALUES (147, 'seria');
INSERT INTO stopwords VALUES (148, 'setor');
INSERT INTO stopwords VALUES (149, 'seu');
INSERT INTO stopwords VALUES (150, 'seus');
INSERT INTO stopwords VALUES (151, 'sido');
INSERT INTO stopwords VALUES (152, 'só');
INSERT INTO stopwords VALUES (153, 'sobre');
INSERT INTO stopwords VALUES (154, 'sua');
INSERT INTO stopwords VALUES (155, 'suas');
INSERT INTO stopwords VALUES (156, 'sul');
INSERT INTO stopwords VALUES (157, 'também');
INSERT INTO stopwords VALUES (158, 'tão');
INSERT INTO stopwords VALUES (159, 'tel');
INSERT INTO stopwords VALUES (160, 'tem');
INSERT INTO stopwords VALUES (161, 'têm');
INSERT INTO stopwords VALUES (162, 'ter');
INSERT INTO stopwords VALUES (163, 'teve');
INSERT INTO stopwords VALUES (164, 'tinha');
INSERT INTO stopwords VALUES (165, 'toda');
INSERT INTO stopwords VALUES (166, 'todas');
INSERT INTO stopwords VALUES (167, 'todo');
INSERT INTO stopwords VALUES (168, 'todos');
INSERT INTO stopwords VALUES (169, 'três');
INSERT INTO stopwords VALUES (170, 'tudo');
INSERT INTO stopwords VALUES (171, 'um');
INSERT INTO stopwords VALUES (172, 'uma');
INSERT INTO stopwords VALUES (173, 'us');
INSERT INTO stopwords VALUES (174, 'vai');
INSERT INTO stopwords VALUES (175, 'vão');
INSERT INTO stopwords VALUES (176, 'vem');
INSERT INTO stopwords VALUES (177, 'vez');
INSERT INTO stopwords VALUES (178, 'vezes');
INSERT INTO stopwords VALUES (179, 'você');


--
-- TOC entry 2033 (class 0 OID 16525)
-- Dependencies: 182
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO usuarios VALUES (1, 'admin', '698dc19d489c4e4db73e28a713eab07b', 'Administrador da federação', 'PERM_MANAGE_USERS,PERM_UPDATE,PERM_MANAGE_REP,PERM_MANAGE_METADATA,PERM_MANAGE_MAPPINGS,PERM_CHANGE_DATABASE,PERM_VIEW_STATISTICS', 'Administrador');


-- Completed on 2012-06-19 15:53:02 BRT

--
-- PostgreSQL database dump complete
--

