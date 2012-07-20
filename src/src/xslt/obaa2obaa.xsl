<?xml version="1.0" encoding="UTF-8"?>
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
</xsl:template></xsl:stylesheet>