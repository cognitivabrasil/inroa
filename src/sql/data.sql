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
		<obaa:description><xsl:value-of select="dc:rights"/></obaa:description>
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
-- TOC entry 2033 (class 0 OID 16525)
-- Dependencies: 182
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO usuarios VALUES (1, 'admin', '698dc19d489c4e4db73e28a713eab07b', 'Administrador da federação', 'PERM_MANAGE_USERS,PERM_UPDATE,PERM_MANAGE_REP,PERM_MANAGE_METADATA,PERM_MANAGE_MAPPINGS,PERM_CHANGE_DATABASE,PERM_VIEW_STATISTICS, PERM_MANAGE_STATISTICS', 'Administrador');


-- Completed on 2012-06-19 15:53:02 BRT

--
-- PostgreSQL database dump complete
--

