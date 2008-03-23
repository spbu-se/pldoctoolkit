<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:d="http://math.spbu.ru/drl" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:resolver="java:org.spbu.pldoctoolkit.graph.util.IdUtil"
    xmlns:saxon="http://icl.com/saxon"
    extension-element-prefixes="resolver saxon xsi"
    version="2.0">
    
    <xsl:param name="project-name"/>
    
    <xsl:template match="d:Product">
        <products>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </products>
    </xsl:template>
    
    <xsl:template match="d:InfElement | d:InfProduct">
        <xsl:variable name="type" select="name()"/>
        <xsl:element name="parts">
            <xsl:apply-templates select="attribute()"/>
            <xsl:attribute name="xsi:type"><xsl:value-of select="$type"/></xsl:attribute>
            <xsl:apply-templates select="node() | text() | comment()"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="d:InfElemRef">
        <infElemRefs>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </infElemRefs>
    </xsl:template>
    
    <xsl:template match="d:InfElemRefGroup">
        <groups>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </groups>
    </xsl:template>
    
    <!-- infelem id reference -->
    <xsl:template match="@infelemid">
        <xsl:variable name="id"><xsl:value-of select="."/></xsl:variable>
        <xsl:attribute name="infelem">
            <xsl:value-of select="resolver:idToUriString($project-name, $id)"/>
        </xsl:attribute>
    </xsl:template>

    <!-- InfElemRef -> group reference -->
    <xsl:template match="@groupid">
        <xsl:variable name="id"><xsl:value-of select="."/></xsl:variable>
        <xsl:choose>
            <xsl:when test="$id = ''">
                <xsl:attribute name="group"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:attribute name="group">
                    <xsl:value-of select="concat('#', $id)"/>
                </xsl:attribute>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <!-- ProductDocumentation -> Product reference -->
    <xsl:template match="@productid">
        <xsl:variable name="productId"><xsl:value-of select="."/></xsl:variable>
        <xsl:attribute name="product">
            <xsl:value-of select="resolver:idToUriString($project-name, $productId)"/>
        </xsl:attribute>
    </xsl:template>
    
    <xsl:template match="d:FinalInfProduct">
        <finalInfProducts>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>
        </finalInfProducts>
    </xsl:template>
    
    <!-- FinalInfProduct -> InfProduct reference -->
    <xsl:template match="@infproductid">
        <xsl:variable name="id"><xsl:value-of select="."/></xsl:variable>
        <xsl:attribute name="product">
            <xsl:value-of select="resolver:idToUriString($project-name, $id)"/>
        </xsl:attribute>
    </xsl:template>
    
    <xsl:template match="node() | attribute() | text() | comment()">
        <xsl:copy>
            <xsl:apply-templates select="node() | attribute() | text() | comment()"/>            
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
