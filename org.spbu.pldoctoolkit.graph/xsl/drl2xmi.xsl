<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:v="http://tepkom.ru/drl" 
    version="2.0">
    
    <xsl:template match="/">
        <xsl:copy>
            <xsl:apply-templates select="node()"/>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="v:ProductLine">
        <xsl:copy>
            <xsl:copy-of select="@*"/>
            <scheme>
                <xsl:apply-templates select="text() | comment() | node()"/>
            </scheme>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="v:Product">
        <products>
            <xsl:apply-templates select="@* | text() | comment() | node()"/>
        </products>
    </xsl:template>
    
    <xsl:template match="node() | @* | text() | comment()">
        <xsl:copy-of select="."/>
    </xsl:template>
</xsl:stylesheet>