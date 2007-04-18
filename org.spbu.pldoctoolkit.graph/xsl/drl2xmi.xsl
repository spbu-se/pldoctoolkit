<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:v="http://math.spbu.ru/drl" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    version="2.0">
    
    <xsl:output indent="yes"/>
    
    <xsl:template match="v:Product">
        <products>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </products>
    </xsl:template>
    
    <xsl:template match="v:InfElement | v:InfProduct">
        <xsl:variable name="type" select="name()"/>
        <xsl:element name="parts">
            <xsl:copy-of select="@*"/>
            <xsl:attribute name="xsi:type"><xsl:value-of select="$type"/></xsl:attribute>
            <xsl:apply-templates select="node() | text() | comment()"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="v:InfElemRef">
        <infElemRefs>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </infElemRefs>
    </xsl:template>
    
    <xsl:template match="node() | attribute() | text() | comment()">
        <xsl:copy>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>            
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
