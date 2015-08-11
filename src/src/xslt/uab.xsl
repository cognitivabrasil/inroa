<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:dc="http://purl.org/dc/elements/1.1/"
xmlns:obaa="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/lomv1.0/lom.xsd"
xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
xmlns:oai_pmh="http://www.openarchives.org/OAI/2.0/"
xmlns:dcterms="http://purl.org/dc/terms/"
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
 
<xsl:template match="resumptionToken">    
</xsl:template>
  
<xsl:template match="header">
    <xsl:copy-of select="."/>
</xsl:template>
 
  <xsl:template match="metadata">
    
  <metadata>
  <obaa:obaa xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <obaa:general>
     
    <xsl:for-each select="dc:title">
        <obaa:title>
            <xsl:value-of select="."/>
        </obaa:title>
    </xsl:for-each>
     
    <xsl:for-each select="dcterms:alternative">
        <obaa:title>
            <xsl:value-of select="."/>
        </obaa:title>
    </xsl:for-each>
      
      
 
        <obaa:identifier>
            <obaa:catalog>identifier</obaa:catalog>
            <obaa:entry><xsl:value-of select="./dcterms:identifier[1]"/></obaa:entry>
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

     
              
       <xsl:for-each select="dcterms:avaliable">
            <obaa:contribute>
                <obaa:role>publisher</obaa:role>
              <obaa:entity><xsl:value-of select="../dc:publisher[0]"/></obaa:entity>
                <obaa:date><xsl:value-of select="."/></obaa:date>
            </obaa:contribute>
      </xsl:for-each>
     
    </obaa:lifeCycle>
     
    <obaa:technical>
        <xsl:for-each select="dcterms:identifier">
            <obaa:location><xsl:value-of select="."/></obaa:location>
        </xsl:for-each>
      
      <xsl:for-each select="dcterms:IMT">
            <obaa:format><xsl:value-of select="."/></obaa:format>
        </xsl:for-each>

       <xsl:for-each select="dcterms:extent">
            <obaa:size><xsl:value-of select="."/></obaa:size>
        </xsl:for-each>
      
      
    </obaa:technical>
 
    <obaa:rights>
        <obaa:description><xsl:value-of select="dc:rights"/></obaa:description>
    </obaa:rights>
</obaa:obaa>
    </metadata>  
</xsl:template>
 
</xsl:stylesheet>