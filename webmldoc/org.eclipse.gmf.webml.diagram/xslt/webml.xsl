<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:gr="http://www.webratio.com/2006/WebML/Graph">

	<xsl:template match="/">
        <xsl:text>
</xsl:text>
        <webml:Siteview xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:webml="webml">
        <xsl:text>
</xsl:text>
            <xsl:apply-templates/>
			<xsl:apply-templates  mode="linkprocessing"/>
        <xsl:text>
</xsl:text>
        </webml:Siteview>
    </xsl:template>
	
	<xsl:attribute-set name="area_attrs">
		<xsl:attribute name="xsi_type">webml:Area</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="@name" />
		</xsl:attribute>
		<xsl:attribute name="Id">
			<xsl:value-of select="@id" />
		</xsl:attribute>
		<xsl:attribute name="x">
			<xsl:value-of select="@gr:x" />
		</xsl:attribute>
		<xsl:attribute name="y">
			<xsl:value-of select="@gr:y" />
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="page_attrs">
		<xsl:attribute name="xsi_type">webml:Page</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="@name" />
		</xsl:attribute>
		<xsl:attribute name="Id">
			<xsl:value-of select="@id" />
		</xsl:attribute>
		<xsl:attribute name="x">
			<xsl:value-of select="@gr:x" />
		</xsl:attribute>
		<xsl:attribute name="y">
			<xsl:value-of select="@gr:y" />
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="cu_attrs">
		<xsl:attribute name="xsi_type">webml:ContentUnit</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="@name" />
		</xsl:attribute>
		<xsl:attribute name="Id">
			<xsl:value-of select="@id" />
		</xsl:attribute>
		<xsl:attribute name="x">
			<xsl:value-of select="@gr:x" />
		</xsl:attribute>
		<xsl:attribute name="y">
			<xsl:value-of select="@gr:y" />
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="ou_attrs">
		<xsl:attribute name="xsi_type">webml:OperationUnit</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="@name" />
		</xsl:attribute>
		<xsl:attribute name="Id">
			<xsl:value-of select="@id" />
		</xsl:attribute>
		<xsl:attribute name="x">
			<xsl:value-of select="@gr:x" />
		</xsl:attribute>
		<xsl:attribute name="y">
			<xsl:value-of select="@gr:y" />
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="link_attrs">
		<xsl:attribute name="source">
			<xsl:value-of select="../@id" />
		</xsl:attribute>
		<xsl:attribute name="target">
			<xsl:value-of select="@to" />
		</xsl:attribute>
	</xsl:attribute-set>
	
	
    <!-- Link Transformations  -->	
	<xsl:template match="Link[@type='normal']"/>
    <xsl:template match="Link[@type='normal']" mode="linkprocessing">
		<nlink xsl:use-attribute-sets="link_attrs"/>
	</xsl:template>
	
	<xsl:template match="Link[@type='automatic']"/>
    <xsl:template match="Link[@type='automatic']" mode="linkprocessing">
		<nlink xsl:use-attribute-sets="link_attrs"/>
	</xsl:template>
	
	<xsl:template match="Link[@type='transport']"/>
    <xsl:template match="Link[@type='transport']" mode="linkprocessing">
		<tlink xsl:use-attribute-sets="link_attrs"/>
	</xsl:template>
	
	<xsl:template match="OKLink"/>
    <xsl:template match="OKLink" mode="linkprocessing">
		<oklink xsl:use-attribute-sets="link_attrs"/>
	</xsl:template>
	
	<xsl:template match="KOLink"/>
    <xsl:template match="KOLink" mode="linkprocessing">
		<kolink xsl:use-attribute-sets="link_attrs"/>
	</xsl:template>
	
    <!-- Area Transformations  -->
	 <xsl:template match="Area">
        <element xsl:use-attribute-sets="area_attrs">
            <xsl:apply-templates/>
        </element>
    </xsl:template>
    
    <!-- Page Transformations  -->
	 <xsl:template match="Page">
        <element xsl:use-attribute-sets="page_attrs">
            <xsl:apply-templates/>
        </element>
    </xsl:template>
    
    <!-- Content Unit Transformations  -->
    <xsl:template match="ContentUnits/*">
		<element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
	
    <!-- Operation Unit Transformations  -->
    <xsl:template match="OperationUnits/*">
		<element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
	

</xsl:stylesheet>