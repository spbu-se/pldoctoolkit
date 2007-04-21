<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:drl="http://math.spbu.ru/drl" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:saxon="http://saxon.sf.net/">
	
	<xsl:param name="finalinfproductid"/>
	
	<xsl:variable name="FinalInfProduct" 
		select="/drl:ProductDocumentation/drl:FinalInfProduct[@id = $finalinfproductid or not($finalinfproductid)][1]"/>
	<xsl:variable name="productid" select="/drl:ProductDocumentation/@productid"/>
	
	<xsl:variable name="err" select="QName('http://math.spbu.ru/drl', 'error')"/>
	
	<xsl:template match="/">
		<xsl:apply-templates select="$FinalInfProduct"/>
	</xsl:template>
	
	<xsl:template match="drl:FinalInfProduct">
		<xsl:variable name="uri" select="concat('drlresolve://Core/InfProduct/', @infproductid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:choose>
			<xsl:when test="$root">
				<xsl:apply-templates select="$root/drl:DocumentationCore/drl:InfProduct[@id = current()/@infproductid]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="error($err, concat('InfProduct with id ''', @infproductid, ''' unresolved.'), .)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="drl:InfProduct">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="drl:InfElemRef">
		<xsl:variable name="uri" select="concat('drlresolve://Core/InfElement/', @infelemid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:if test="(not(@optional) or @optional = 'false') and not($FinalInfProduct/drl:Adapter[@infelemrefid = current()/@id])">
			<xsl:value-of select="error($err, 'Reference is not optional, you must provide an adapter.', .)"/>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="$root">
				<xsl:apply-templates select="$root/drl:DocumentationCore/drl:InfElement[@id = current()/@infelemid]">
					<xsl:with-param name="refid" select="@id"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="error($err, concat('InfElement with id ''', @infelemid, ''' unresolved.'), .)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="drl:InfElement">
		<xsl:param name="refid"/>
		<xsl:apply-templates>
			<xsl:with-param name="refid" select="$refid"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="drl:Nest">
		<xsl:param name="refid"/>
		<xsl:variable name="id" select="@id"/>
		<xsl:variable name="InfElement" select="ancestor::drl:InfElement"/>
		<xsl:variable name="Replace-Nest" select="$FinalInfProduct/drl:Adapter[@infelemrefid = $refid]/drl:Replace-Nest[@nestid = $id]"/>
		
		<xsl:apply-templates select="$FinalInfProduct/drl:Adapter[@infelemrefid = $refid]/drl:Insert-Before[@nestid = $id]/node()"/>
		<xsl:choose>
			<xsl:when test="$Replace-Nest">
				<xsl:apply-templates select="$Replace-Nest/node()"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="node()"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates select="$FinalInfProduct/drl:Adapter[@infelemrefid = $refid]/drl:Insert-After[@nestid = $id]/node()"/>
	</xsl:template>
	
	<xsl:template match="drl:Conditional">
		<xsl:variable name="varname" select="substring-before(@condition, '=')"/>
		<xsl:variable name="varvalue" select="substring-after(@condition, '=')"/>
		<xsl:variable name="varnode" select="$FinalInfProduct/drl:SetValue[@id = $varname]"/>
		
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
	
	<xsl:template match="drl:DictRef">
		<xsl:variable name="uri" select="concat('drlresolve://Product', $productid, '/Dictionary/', @dictid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:variable name="Dictionary" select="$root//drl:Dictionary[@id = current()/@dictid]"/>
		<xsl:variable name="Entry" select="$Dictionary/drl:Entry[@id = current()/@entryid]"/>
		<xsl:choose>
			<xsl:when test="not($Dictionary)">
				<xsl:value-of select="error($err, concat('Dictionary with id ''', @dictid, ''' unresolved.'), .)"/>
			</xsl:when>
			<xsl:when test="not($Entry)">
				<xsl:value-of select="error($err, concat('Dictionary with id ''', @dictid, ''' doesn''t contain an Entry with id ''', @entryid,'''.'), .)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="$Entry/node()"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="drl:InfElemRefGroup">
		
	</xsl:template>
	
	<xsl:template match="text()">
		<xsl:copy-of select="."/>
	</xsl:template>
	
	<xsl:template match="node()" priority="-1">
		<xsl:param name="refid"/>
		<xsl:copy>
			<xsl:copy-of select="attribute::node()"/>
			<xsl:attribute name="drl:system-id" select="saxon:system-id()"/>
			<xsl:attribute name="drl:line-number" select="saxon:line-number()"/>
			<xsl:apply-templates>
				<xsl:with-param name="refid" select="$refid"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>