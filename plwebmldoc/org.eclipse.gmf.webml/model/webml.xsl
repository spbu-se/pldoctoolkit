<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

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
	</xsl:attribute-set>
	<xsl:attribute-set name="page_attrs">
		<xsl:attribute name="xsi_type">webml:Page</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="@name" />
		</xsl:attribute>
		<xsl:attribute name="Id">
			<xsl:value-of select="@id" />
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
	</xsl:attribute-set>
	<xsl:attribute-set name="ou_attrs">
		<xsl:attribute name="xsi_type">webml:OperationUnit</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="@name" />
		</xsl:attribute>
		<xsl:attribute name="Id">
			<xsl:value-of select="@id" />
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
    <xsl:template match="EntryUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="DataUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="IndexUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="HierarchicalIndexUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="MultiDataUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="ScrollerUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="PowerIndexUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="MultiChoiceIndexUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="RecursiveHierarchicalIndexUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="EventCalendarUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="MultiEntryUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="MultiMessageUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="AlphabetUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
    <xsl:template match="NoOpContentUnit">
        <element xsl:use-attribute-sets="cu_attrs"/>
    </xsl:template>
        
    <!-- Operation Unit Transformations  -->
    <xsl:template match="CreateUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="DeleteUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="ModifyUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="ConnectUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="DisconnectUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="ReconnectUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="SwitchUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="IsNotNullUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="MailUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="StoredProcedureUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="NoOpOperationUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    
    <xsl:template match="SetUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="GetUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="ResetUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="LoginUnit">
        <element xsl:use-attribute-sets="ou_attrs"/> 
    </xsl:template>
    <xsl:template match="LogoutUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
    <xsl:template match="ChangeGroupUnit">
        <element xsl:use-attribute-sets="ou_attrs"/>
    </xsl:template>
	
</xsl:stylesheet>