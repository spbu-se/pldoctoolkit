<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns="http://docbook.org/ns/docbook" 
	xmlns:d="http://math.spbu.ru/drl" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:variable name="document" select="/d:document"/>
	
	<xsl:template match="d:document">
		<xsl:apply-templates select="document(concat(/d:document/@family, '.xml'), $document)/d:family/node()"/>
	</xsl:template>
	
	<xsl:template match="d:ref">
		<xsl:apply-templates select="document(concat(@part, '.xml'), $document)/d:part/node()"/>
	</xsl:template>
	
	<xsl:template match="d:nest">
		<xsl:variable name="part" select="ancestor::d:part/@id"/>
		<xsl:variable name="replace" select="$document/d:adapter[@part = $part]/d:replace[current()/@id = @id]"/>

		<xsl:apply-templates select="$document/d:adapter[@part = $part]/d:insert-before[current()/@id = @id]/node()"/>
		<xsl:choose>
			<xsl:when test="$replace">
				<xsl:apply-templates select="$replace/node()"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="node()"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates select="$document/d:adapter[@part = $part]/d:insert-after[current()/@id = @id]/node()"/>
	</xsl:template>
	
	<xsl:template match="*">
		<xsl:copy>
			<xsl:copy-of select="attribute::node()"/>
			<xsl:apply-templates select="node()"/>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>