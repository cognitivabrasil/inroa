<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:obaa="http://ltsc.ieee.org/xsd/LOM"
	xmlns:oai_pmh="http://www.openarchives.org/OAI/2.0/"
    xpath-default-namespace="http://www.openarchives.org/OAI/2.0/"> 

	<xsl:output method="xml" encoding="UTF-8" />
	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="OAI-PMH">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="ListRecords">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="responseDate">
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="request">
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="record">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="header">
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="metadata">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

<xsl:template match="obaa:obaa">
<obaa:obaa xmlns:obaa="http://ltsc.ieee.org/xsd/LOM" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/obaav1.0/obaa.xsd">


			<obaa:general>

				<xsl:for-each select="obaa:general/obaa:obaaTitle">
					<obaa:title>
						<xsl:value-of select="." />
					</obaa:title>
				</xsl:for-each>

				<obaa:identifier>
					<obaa:catalog>NULL</obaa:catalog>
					<obaa:entry>
						<xsl:value-of select="obaa:general/obaa:identifier/obaa:entry[1]" />
					</obaa:entry>
				</obaa:identifier>

				<xsl:for-each select="obaa:general/obaa:obaaDescription">
					<obaa:description>
						<xsl:value-of select="." />
					</obaa:description>
				</xsl:for-each>

				<xsl:for-each select="obaa:general/obaa:obaaCoverage">
					<obaa:coverage>
						<xsl:value-of select="." />
					</obaa:coverage>
				</xsl:for-each>

				<xsl:for-each select="obaa:general/obaa:obaaKeyword">
					<obaa:keyword>
						<xsl:value-of select="." />
					</obaa:keyword>
				</xsl:for-each>

			</obaa:general>

			<obaa:lifeCycle>
				<xsl:for-each select="obaa:general/obaa:obaaEntity">
					<obaa:contribute>
						<obaa:role>author</obaa:role>
						<obaa:entity>
							<xsl:value-of select="." />
						</obaa:entity>
					</obaa:contribute>
				</xsl:for-each>

			</obaa:lifeCycle>

			<obaa:technical>
				<xsl:for-each select="obaa:general/obaa:obaaIdentifier">
					<obaa:location>
						<xsl:value-of select="." />
					</obaa:location>
				</xsl:for-each>
			</obaa:technical>


		</obaa:obaa>
		</xsl:template>

</xsl:stylesheet>

