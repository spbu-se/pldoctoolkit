<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:v="http://tepkom.ru/drl"
    version="2.0">
    
    <xsl:output indent="yes"/>
    
    <xsl:template match="document-node()">
        <xsl:copy>
            <xsl:apply-templates select="processing-instruction() | text() | comment() | node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="products">
        <xsl:element 
            name="v:Product">
            <xsl:apply-templates select="@* | text() | comment() | node()"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="v:ProductLine">
        <xsl:copy><xsl:apply-templates select="@* | text() | comment() | node()"/></xsl:copy>
    </xsl:template>
                      
    <xsl:template match="processing-instruction() | attribute() | text() | comment() | node()">
        <xsl:copy-of select="."/>
    </xsl:template>
</xsl:stylesheet>