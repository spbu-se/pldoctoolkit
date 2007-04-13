<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns="http://math.spbu.ru/drl" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:saxon="http://saxon.sf.net/">
	
	<xsl:param name="finalinfproductid"/>
	
	<xsl:variable name="FinalInfProduct" select="/ProductDocumentation/FinalInfProduct[@id = $finalinfproductid or not($finalinfproductid)][1]"/>
	<xsl:variable name="productid" select="/ProductDocumentation/@productid"/>
	
	<xsl:variable name="err" select="QName('http://math.spbu.ru/drl', 'error')"/>
	
	<xsl:template match="/">
		<xsl:apply-templates select="$FinalInfProduct"/>
	</xsl:template>
	
	<xsl:template match="FinalInfProduct">
		<xsl:variable name="uri" select="concat('drlresolve://Core/InfProduct/', @infproductid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:choose>
			<xsl:when test="$root">
				<xsl:apply-templates select="$root/DocumentationCore/InfProduct[@id = current()/@infproductid]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="error($err, concat('InfProduct with id ''', @infproductid, ''' unresolved'), .)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="InfProduct">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="InfElemRef">
		<xsl:variable name="uri" select="concat('drlresolve://Core/InfElement/', @infelemid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:choose>
			<xsl:when test="$root">
				<xsl:apply-templates select="$root/DocumentationCore/InfElement[@id = current/@infelemid]">
					<xsl:with-param name="refid" select="@id"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="error($err, concat('InfElement with id ''', @infelemid, ''' unresolved'), .)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="InfElement">
		<xsl:param name="refid"/>
		<xsl:apply-templates>
			<xsl:with-param name="refid" select="$refid"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="Nest">
		<xsl:param name="refid"/>
		<xsl:variable name="id" select="@id"/>
		<xsl:variable name="InfElement" select="ancestor::InfElement"/>
		<xsl:variable name="Replace-Nest" select="$FinalInfProduct/Adapter[@infelemrefid = $refid]/Replace-Nest[@nestid = $id]"/>
		
		<xsl:apply-templates select="$FinalInfProduct/Adapter[@infelemrefid = $refid]/Insert-Before[@nestid = $id]/node()"/>
		<xsl:choose>
			<xsl:when test="$Replace-Nest">
				<xsl:apply-templates select="$Replace-Nest/node()"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="node()"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates select="$FinalInfProduct/Adapter[@infelemrefid = $refid]/Insert-After[@nestid = $id]/node()"/>
	</xsl:template>
	
	<xsl:template match="Conditional">
		<xsl:variable name="varname" select="substring-before(@condition, '=')"/>
		<xsl:variable name="varvalue" select="substring-after(@condition, '=')"/>
		<xsl:variable name="varnode" select="$FinalInfProduct/SetValue[@id = $varname]"/>
		
		<xsl:if test="$varname = '' or $varvalue = ''">
			<xsl:value-of select="error($err, 'syntax error in Conditional expression', .)"/>
		</xsl:if>
		
		<xsl:choose>
			<xsl:when test="not($varnode)">
				<xsl:value-of select="error($err, concat('variable ''', $varname, ''' undefined'), .)"/>
			</xsl:when>
			<xsl:when test="count($varnode) &gt; 1">
				<xsl:value-of select="error($err, concat('variable ''', $varname, ''' defined multiple times'), $varnode)"/>
			</xsl:when>
			<xsl:when test="$varnode[@value = $varvalue]">
				<xsl:apply-templates select="node()"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="DictRef">
		<xsl:variable name="uri" select="concat('drlresolve://', $productid, '/Dictionary/', @dictid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:variable name="Dictionary" select="$root//Dictionary[@id = current()/@dictid]"/>
		<xsl:variable name="Entry" select="$Dictionary/Entry[@id = current()/@entryid]"/>
		<xsl:choose>
			<xsl:when test="not($Dictionary)">
				<xsl:value-of select="error($err, concat('Dictionary with id ''', @dictid, ''' unresolved'), .)"/>
			</xsl:when>
			<xsl:when test="not($Entry)">
				<xsl:value-of select="error($err, concat('Dictionary with id ''', @dictid, ''' doesn''t contain an Entry with id ''', @entryid,''''), .)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="$Entry/node()"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
				
	<xsl:template match="text()">
		<xsl:copy-of select="."/>
	</xsl:template>
	
	<xsl:template match="node()" priority="-1">
		<xsl:param name="refid"/>
		<xsl:copy>
			<xsl:copy-of select="attribute::node()"/>
			<xsl:attribute namespace="http://math.spbu.ru/drl" name="system-id" select="saxon:system-id()"/>
			<xsl:attribute namespace="http://math.spbu.ru/drl" name="line-number" select="saxon:line-number()"/>
			<xsl:apply-templates>
				<xsl:with-param name="refid" select="$refid"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>