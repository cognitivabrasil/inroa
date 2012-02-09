<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:dc="http://purl.org/dc/elements/1.1/"
xmlns:lom="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/lomv1.0/lom.xsd"
exclude-result-prefixes="dc"
xpath-default-namespace="http://purl.org/dc/elements/1.1/">

<xsl:output method="xml" encoding="UTF-8"/>
<xsl:template match="/">

<lom:lom  xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/lomv1.0/lom.xsd">
	<lom:general>
	
	<xsl:for-each select="//title">
		<lom:title>
			<xsl:value-of select="."/>
		</lom:title>
	</xsl:for-each>

	<xsl:for-each select="//description">
		<lom:description>
			<xsl:value-of select="."/>
		</lom:description>
	</xsl:for-each>

	<xsl:for-each select="//subject">
		<lom:keyword>
			<xsl:value-of select="."/>
		</lom:keyword>
	</xsl:for-each>
	
	</lom:general>	

	<lom:lifeCycle>
		<xsl:for-each select="//creator">
			<lom:contribute>
				<lom:role>author</lom:role>
				<lom:entity><xsl:value-of select="."/></lom:entity>
			</lom:contribute>
		</xsl:for-each>
		<xsl:for-each select="//contributor">
			<lom:contribute>
				<lom:role>contributor</lom:role>
				<lom:entity><xsl:value-of select="."/></lom:entity>
			</lom:contribute>
		</xsl:for-each>
		<xsl:for-each select="//publisher">
			<lom:contribute>
				<lom:role>publisher</lom:role>
				<lom:entity><xsl:value-of select="."/></lom:entity>
			</lom:contribute>
		</xsl:for-each>
	
	</lom:lifeCycle>
</lom:lom>

</xsl:template>
</xsl:stylesheet>

