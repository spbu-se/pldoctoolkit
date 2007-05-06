<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:v="http://math.spbu.ru/drl" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:resolver="java:org.spbu.pldoctoolkit.graph.util.IdUtil"
    xmlns:saxon="http://icl.com/saxon"
    extension-element-prefixes="resolver saxon"
    version="2.0">
    
    <xsl:output indent="yes"/>
    
<!--    <xsl:variable name="project-name" select="resolver:parseProjectName(saxon:systemid())"/>-->
    <xsl:param name="project-name"/>
    
    <xsl:template match="v:Product">
        <products>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </products>
    </xsl:template>
    
    <xsl:template match="v:InfElement | v:InfProduct">
        <xsl:variable name="type" select="name()"/>
        <xsl:element name="parts">
            <xsl:apply-templates select="attribute()"/>
            <xsl:attribute name="xsi:type"><xsl:value-of select="$type"/></xsl:attribute>
            <xsl:apply-templates select="node() | text() | comment()"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="v:InfElemRef">
        <infElemRefs>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </infElemRefs>
    </xsl:template>
    
    <xsl:template match="v:InfElemRefGroup">
        <groups>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </groups>
    </xsl:template>
    
    <!-- infelem id reference -->
    <!-- TODO @infelemid -->
    <xsl:template match="@infelem">
        <xsl:variable name="id"><xsl:value-of select="."/></xsl:variable>
        <xsl:attribute name="infelem">
            <xsl:value-of select="resolver:idToUriString($project-name, $id)"/>
        </xsl:attribute>
    </xsl:template>

    <!-- InfElemRef -> group reference -->
    <!-- TODO @groupid -->
    <xsl:template match="@group">
        <xsl:variable name="id"><xsl:value-of select="."/></xsl:variable>
        <xsl:choose>
            <xsl:when test="$id = ''">
                <xsl:copy-of select="."/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:attribute name="group">
                    <xsl:value-of select="concat('#', $id)"/>
                </xsl:attribute>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="node() | attribute() | text() | comment()">
        <xsl:copy>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>            
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
