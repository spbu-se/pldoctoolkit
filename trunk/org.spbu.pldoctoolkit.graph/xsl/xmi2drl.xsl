<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:v="http://math.spbu.ru/drl"
    version="2.0"
    exclude-result-prefixes="xsi">

    <xsl:output indent="yes"/>

    <xsl:template match="products">
        <v:Product>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </v:Product>
    </xsl:template>

    <xsl:template match="parts">
        <xsl:variable name="type"><xsl:value-of select="@xsi:type"/></xsl:variable>
        <xsl:element name="{$type}">
            <xsl:copy-of select="@*[not(name()='xsi:type')]"/>
            <xsl:apply-templates select="text() | comment() | node()"/>
        </xsl:element>
    </xsl:template>                      

    <xsl:template match="infElemRefs">
        <v:InfElemRef>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </v:InfElemRef>
    </xsl:template>

    <xsl:template match="groups">
        <v:InfElemRefGroup>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </v:InfElemRefGroup>
    </xsl:template>

    <xsl:template match="node() | attribute() | text() | comment()">
        <xsl:copy>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
