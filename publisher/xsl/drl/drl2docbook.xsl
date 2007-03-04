<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
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
	
	<xsl:template match="d:if">
		<xsl:variable name="varname" select="substring-before(@test, '=')"/>
		<xsl:variable name="varvalue" select="substring-after(@test, '=')"/>
		<xsl:if test="$varname = '' or $varvalue = ''">
			<xsl:value-of select="error(QName('', 'ifsyntax'), 'syntax error in &lt;if&gt; expression', .)"/>
		</xsl:if>
		<xsl:if test="not($document/d:variable[@name = $varname])">
			<xsl:value-of select="error(QName('', 'ifvarundefined'), concat('Variable &lt;', $varname,'&gt; undefined'), .)"/>
		</xsl:if>
		<xsl:if test="$document/d:variable[@name = $varname][@value = $varvalue]">
			<xsl:apply-templates select="node()"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="d:dictentry">
		<xsl:variable name="entry" select="document(concat(@dict, '.xml'), $document)/d:dictionary/d:entry[@name = current()/@entry]"/>
		<xsl:choose>
			<xsl:when test="$entry">
				<xsl:apply-templates select="$entry/node()"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="error(QName('', 'dictentryundefined'), 'dictionary entry undefined', .)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="*">
		<xsl:copy>
			<xsl:copy-of select="attribute::node()"/>
			<xsl:apply-templates select="node()"/>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>