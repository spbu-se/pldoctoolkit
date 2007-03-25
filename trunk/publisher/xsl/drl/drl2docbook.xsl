<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:d="http://math.spbu.ru/drl" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:saxon="http://saxon.sf.net/">
	
	<xsl:variable name="document" select="/d:document"/>
	
	<xsl:template match="d:document">
		<xsl:call-template name="process-external">
			<xsl:with-param name="id" select="@family"/>
			<xsl:with-param name="node" select="."/>
		</xsl:call-template>
	</xsl:template>
			
	<xsl:template match="d:ref">
		<xsl:call-template name="process-external">
			<xsl:with-param name="id" select="@part"/>
			<xsl:with-param name="node" select="."/>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="d:part">
		<xsl:apply-templates select="node()"/>
	</xsl:template>
	
	<xsl:template match="d:family">
		<xsl:apply-templates select="node()"/>
	</xsl:template>
			
	<xsl:template match="d:nest">
		<xsl:variable name="id" select="@id"/>
		<xsl:variable name="part" select="/d:part"/>
		<xsl:variable name="replace" select="$document/d:adapter[@part = $part/@id]/d:replace[$id = @nest]"/>
		
		<xsl:apply-templates select="$document/d:adapter[@part = $part/@id]/d:insert-before[$id = @nest]/node()"/>
		<xsl:choose>
			<xsl:when test="$replace">
				<xsl:apply-templates select="$replace/node()"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="node()"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates select="$document/d:adapter[@part = $part/@id]/d:insert-after[$id = @nest]/node()"/>
	</xsl:template>
	
	<xsl:template match="d:if">
		<xsl:variable name="varname" select="substring-before(@test, '=')"/>
		<xsl:variable name="varvalue" select="substring-after(@test, '=')"/>
		<xsl:variable name="part" select="/d:part"/>
		
		<xsl:if test="$varname = '' or $varvalue = ''">
			<xsl:value-of select="error(QName('http://math.spbu.ru/drl', 'bad_if_syntax'), 'syntax error in ''if'' expression', .)"/>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="/d:family">
				<xsl:call-template name="iftemplate">
					<xsl:with-param name="variable" select="$document/d:variable[@name = $varname]"/>
					<xsl:with-param name="value" select="$varvalue"/>
					<xsl:with-param name="name" select="$varname"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="/d:part">
				<xsl:call-template name="iftemplate">
					<xsl:with-param name="variable" select="$document/d:adapter[@part = $part/@id]/d:variable[@name = $varname]"/>
					<xsl:with-param name="value" select="$varvalue"/>
					<xsl:with-param name="name" select="$varname"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="iftemplate">
		<xsl:param name="variable"/>
		<xsl:param name="value"/>
		<xsl:param name="name"/>
		<xsl:choose>
			<xsl:when test="not($variable)">
				<xsl:value-of select="error(QName('http://math.spbu.ru/drl', 'variable_undefined'), concat('variable ''', $name, ''' undefined'), .)"/>
			</xsl:when>
			<xsl:when test="count($variable) &gt; 1">
				<xsl:value-of select="error(QName('http://math.spbu.ru/drl', 'multiple_variable_definitions'), concat('variable ''', $name, ''' defined multiple times'), $variable)"/>
			</xsl:when>
			<xsl:when test="$variable[@value = $value]">
				<xsl:apply-templates select="node()"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="d:dictentry">
		<xsl:variable name="dictionary" select="document(concat('drlresolve://', @dict))/d:dictionary"/>
		<xsl:variable name="entry" select="$dictionary/d:entry[@name = current()/@entry]"/>
		<xsl:choose>
			<xsl:when test="not($dictionary)">
				<xsl:value-of select="error(QName('http://math.spbu.ru/drl', 'id_unresolved'), concat('id ''', @dict, ''' unresolved'), .)"/>
			</xsl:when>
			<xsl:when test="not($entry)">
				<xsl:value-of select="error(QName('http://math.spbu.ru/drl', 'entry_undefined'), concat('entry ''', @entry, ''' for dictionary ''', @dict, ''' undefined'), .)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="$entry/node()"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="node()">
		<xsl:copy>
			<xsl:copy-of select="attribute::node()"/>
			<xsl:attribute name="d:system-id" select="saxon:system-id()"/>
			<xsl:attribute name="d:line-number" select="saxon:line-number()"/>
			<xsl:apply-templates select="node()"/>
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="text()">
		<xsl:copy-of select="."/>
	</xsl:template>
	
	<xsl:template name="process-external">
		<xsl:param name="id"/>
		<xsl:param name="node"/>
		<xsl:variable name="external-root" select="document(concat('drlresolve://', $id))"/>
		<xsl:choose>
			<xsl:when test="$external-root">
				<xsl:apply-templates select="$external-root/node()"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="error(QName('http://math.spbu.ru/drl', 'id_unresolved'), concat('id ''', $id, ''' unresolved'), $node)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>