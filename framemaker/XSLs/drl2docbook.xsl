<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:drl="http://math.spbu.ru/drl" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:saxon="http://saxon.sf.net/" exclude-result-prefixes="saxon"
	xmlns:error="java:org.spbu.publishutil.BasicPublishAction">
	
	<xsl:param name="finalinfproductid"/>
	
	<!-- 
	Параметр, характеризующий качество проверки входящего документа
	TODO: продумать уровни проверки
	TODO: неободимо выставлять извне
 	-->
	<xsl:param name="strictdrl" select="10"/>
	
	<xsl:variable name="FinalInfProduct" 
		select="/drl:ProductDocumentation/drl:FinalInfProduct[@id = $finalinfproductid or not($finalinfproductid)][1]"/>
	<xsl:variable name="productid" select="/drl:ProductDocumentation/@productid"/>
	
	<xsl:variable name="err" select="QName('http://math.spbu.ru/drl', 'error')"/>
	
	<xsl:template match="/">
		<!-- Проверка уникальности Dictionary с одинаковыми id -->
		<xsl:for-each select="//drl:Dictionary">
			<xsl:choose>
				<xsl:when test="count(parent::*/drl:Dictionary[@id=current()/@id]) &gt; 1">
					<!-- сообщение о неуникальности Dictionary с данным id -->
					<xsl:value-of select="error:parseError(concat('There are multiple Dictionaries with same id ''', @id, '''.'), . , 2)" />
				</xsl:when> 
				<xsl:otherwise>
				<!-- Проверка уникальности Entry с одинаковыми id -->
					<xsl:for-each select="drl:Entry">
						<xsl:if test="count(parent::*/drl:Entry[@id=current()/@id]) &gt; 1">
							<!-- сообщение о неуникальности Entry для данной переменной-->
							<xsl:value-of select="error:parseError(concat('There are multiple Entries with same id ''', @id, '''.'), . , 2)" />
						</xsl:if> 
					</xsl:for-each>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		
		<!-- TODO: make same checks for directory -->
	
		<!-- Применение основных шаблонов -->
		<xsl:apply-templates select="$FinalInfProduct"/>
		
		<!-- Прерывание процесса преобразовния, если во время применения шаблонов возникли ошибки, и отображение первой из ошибок -->
		<xsl:if test="error:isErrorHappened()">
			<xsl:value-of select="error($err, error:getFirstErrorMessage(), error:getFirstErrorNode())"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="drl:FinalInfProduct">
		<!-- Проверка уникальности Adapter с одинаковыми infelemrefid -->
		<xsl:for-each select="drl:Adapter">
			<xsl:if test="count(parent::*/drl:Adapter[@infelemrefid=current()/@infelemrefid]) &gt; 1">
				<!-- сообщение о неуникальности Adapter -->
				<xsl:value-of select="error:parseError(concat('There are multiple Adapter elements applying with reference element id ''', @infelemrefid, '''.'), . , 4)" />
			</xsl:if> 
		</xsl:for-each>
	
		<!-- проверка корректности ссылок в элементах Adapter и элементах со ссылкой на Nest,
		 а так же проверка уникальности внутренних элементов -->
		<xsl:apply-templates select="drl:Adapter"/>
		
		<!-- Проверка уникальности SetValue с одинаковыми nestid -->
		<xsl:for-each select="drl:SetValue">
			<xsl:if test="count(parent::*/drl:SetValue[@id=current()/@id]) &gt; 1">
				<!-- сообщение о неуникальности SetValue для данной переменной-->
				<xsl:value-of select="error:parseError(concat('Value for variable ''', @id, ''' defined multiple times'), . , 3)" />
			</xsl:if> 
		</xsl:for-each>
	
		<!-- Применение дальнейших шаблонов -->
		<xsl:variable name="uri" select="concat('drlresolve://Core/InfProduct/', @infproductid)"/>
		<xsl:variable name="root" select="document($uri)"/>		
		<xsl:choose>
			<xsl:when test="$root">
				<xsl:apply-templates select="$root/drl:DocumentationCore/drl:InfProduct[@id = current()/@infproductid]"/>
			</xsl:when>
			<xsl:otherwise>
				<!-- сообщение о том, что не найден необходимый информационный продукт (InfProduct) -->
				<xsl:value-of select="error($err, concat('InfProduct with id ''', @infproductid, ''' not resolved.'), .)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="drl:Adapter">
		<!-- Проверка существования элемента, который указан по ссылке -->
		<xsl:variable name="uri" select="concat('drlresolve://Core/InfElemRef/', @infelemrefid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:choose>
			<xsl:when test="$root">
				<!-- Дальнейшие проверки корректности сслылок в элементах со ссылками на Nest-Point -->
				<xsl:apply-templates select="*[@nestid]">
					<!-- Параметр, содержащий id элемента InfElement, внутри которого должны находиться Nest -->
					<xsl:with-param name="infelemid" select="$root/node()//drl:InfElemRef[@id = current()/@infelemrefid]/@infelemid"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<!-- сообщение о том, что не найден элемент InfElemRef, указанный в ссылке -->
				<xsl:value-of select="error:parseError(concat('InfElemRef with id ''', @infelemrefid, ''' not resolved.'), . , 1)" />
			</xsl:otherwise>
		</xsl:choose>
		
		<!-- Проверка уникальности Insert-Before с одинаковыми nestid -->
		<xsl:for-each select="drl:Insert-Before">
			<xsl:if test="count(parent::*/drl:Insert-Before[@nestid=current()/@nestid]) &gt; 1">
				<!-- сообщение о том, что элемент Insert-Before не уникален для данного Nest -->
				<xsl:value-of select="error:parseError(concat('There are multiple Insert-Before applying to nest with id ''', @nestid, '''.'), . , 3)" />
			</xsl:if> 
		</xsl:for-each>
		
		<!-- Проверка уникальности Replace-Nest с одинаковыми nestid -->
		<xsl:for-each select="drl:Replace-Nest">
			<xsl:if test="count(parent::*/drl:Replace-Nest[@nestid=current()/@nestid]) &gt; 1">
				<!-- сообщение о том, что элемент Replace-Nest не уникален для данного Nest -->
				<xsl:value-of select="error:parseError(concat('There are multiple Replace-Nest applying to nest with id ''', @nestid, '''.'), . , 3)" />
			</xsl:if> 
		</xsl:for-each>
		
		<!-- Проверка уникальности Insert-After с одинаковыми nestid -->
		<xsl:for-each select="drl:Insert-After">
			<xsl:if test="count(parent::*/drl:Insert-After[@nestid=current()/@nestid]) &gt; 1">
				<!-- сообщение о том, что элемент Insert-After не уникален для данного Nest -->
				<xsl:value-of select="error:parseError(concat('There are multiple Insert-After applying to nest with id ''', @nestid, '''.'), . , 3)" />
			</xsl:if> 
		</xsl:for-each>
		
	</xsl:template>
	
	<xsl:template match="*[@nestid]">
		<xsl:param name="infelemid"/>
		<!-- Проверка существования элемента, который указал по ссылке -->
		<xsl:variable name="uri" select="concat('drlresolve://Core/Nest/', @nestid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:choose>
			<xsl:when test="not($root)">
				<!-- сообщение о том, что не найден элемент Nest, указанный в ссылке -->
				<xsl:value-of select="error:parseError(concat('Nest point with id ''', @nestid, ''' not resolved.'), . , 1)" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="nest-point" select="$root/node()//drl:Nest[@id = current()/@nestid]"/>
				<xsl:if test="$nest-point/ancestor::drl:InfElement/@id != $infelemid">
					<xsl:message><xsl:text>Oops..</xsl:text></xsl:message>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="drl:InfProduct">
		<!-- Проверка уникальности InfElemRef с одинаковыми id -->
		<xsl:for-each select="//drl:InfElemRef">
			<xsl:if test="count(parent::*/drl:InfElemRef[@id=current()/@id]) &gt; 1">
				<!-- сообщение о неуникальности InfElemRef -->
				<xsl:value-of select="error:parseError(concat('There are multiple InfElemRef elements with the same id ''', @id, '''.'), . , 1)" />
			</xsl:if> 
		</xsl:for-each>
		
		<!-- Проверка уникальности InfElemRefGroup с одинаковыми id -->
		<xsl:for-each select="//drl:InfElemRefGroup">
			<xsl:if test="count(parent::*/drl:InfElemRefGroup[@id=current()/@id]) &gt; 1">
				<!-- сообщение о неуникальности InfElemRefGroup -->
				<xsl:value-of select="error:parseError(concat('There are multiple InfElemRefGroup elements with the same id ''', @id, '''.'), . , 1)" />
			</xsl:if> 
		</xsl:for-each>
		
		<!-- Применение дальнейших шаблонов -->
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="drl:InfElemRefGroup">
	</xsl:template>
	
	<xsl:template match="drl:InfElemRef">
		<!-- проверка корректности информации о группе элементов -->
		<xsl:if test="string-length(@groupid) &gt; 0">
			<xsl:variable name="uri" select="concat('drlresolve://Core/InfElemRefGroup/', @groupid)"/>
			<xsl:variable name="root" select="document($uri)"/>
			<xsl:if test="not($root)">
				<!-- сообщение о том, что не найдена группа, указанныя в ссылке -->
				<xsl:value-of select="error:parseError(concat('InfElemRefGroup with id ''', @groupid, ''' not resolved.'), . , 2)" />
			</xsl:if>
			
			<!-- проверка, что элемент, находящийся в группе необязательный -->
			<xsl:if test="not(@optional = 'true')">
				<!-- сообщение о том, что элемент в группе является обязательный -->
				<xsl:value-of select="error:parseError(concat('InfElemRef with id ''', @id, ''' should be optional to be in the group.'), . , 3)" />
			</xsl:if>
		</xsl:if>
		
		<xsl:variable name="uri" select="concat('drlresolve://Core/InfElement/', @infelemid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:choose>
			<xsl:when test="(@optional = 'true') and not($FinalInfProduct/drl:Adapter[@infelemrefid = current()/@id])"/>
			<xsl:when test="$root">
				<xsl:variable name="InfElement" select="$root/drl:DocumentationCore/drl:InfElement[@id = current()/@infelemid]"/>
				<xsl:if test="count($InfElement) &gt; 1">
					<!-- сообщение о неуникальности информационного элемента -->
					<xsl:value-of select="error:parseError(concat('InfElement with id ''', current()/@infelemid, ''' defined multiple times.'), . , 1)" />
				</xsl:if>
				<xsl:apply-templates select="$InfElement">
					<xsl:with-param name="refid" select="@id"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<!-- сообщение о том, что не найден необходимый информационный элемент (InfElement) -->
				<xsl:value-of select="error:parseError(concat('InfElement with id ''', @infelemid, ''' not resolved.'), . , 1)" />
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
		
		<xsl:choose>
			<xsl:when test="$varname = '' or $varvalue = ''">
				<!-- сообщение о некорректности синтаксиса условного выражения -->
				<xsl:value-of select="error:parseError('Syntax error in Conditional expression', . , 1)" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="varnode" select="$FinalInfProduct/drl:SetValue[@id = $varname]"/>
				<xsl:if test="not($varnode)">
					<xsl:value-of select="error:parseError(concat('Variable ''', $varname, ''' undefined'), $FinalInfProduct , 1)" />
				</xsl:if>
				
				<xsl:if test="$varnode[@value = $varvalue]">
					<xsl:apply-templates/>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="drl:DictRef">
		<xsl:variable name="uri" select="concat('drlresolve://Product', $productid, '/Dictionary/', @dictid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:variable name="Dictionary" select="$root/node()/drl:Dictionary[@id = current()/@dictid]"/>
		<xsl:variable name="Entry" select="$Dictionary/drl:Entry[@id = current()/@entryid]"/>
		
		<xsl:if test="not($Dictionary)">
			<xsl:value-of select="error($err, concat('Dictionary with id ''', @dictid, ''' not resolved.'), .)"/>
		</xsl:if>
		<xsl:if test="count($Dictionary) &gt; 1">
			<xsl:value-of select="error($err, concat('Dictionary with id ''', @dictid, ''' defined multiple times.'), .)"/>
		</xsl:if>
		
		<xsl:if test="not($Entry)">
			<xsl:value-of select="error($err, concat('Dictionary with id ''', @dictid, ''' doesn''t contain an Entry with id ''', @entryid,'''.'), .)"/>
		</xsl:if>
		<xsl:if test="count($Entry) &gt; 1">
			<xsl:value-of select="error($err, concat('Entry with id ''', @entryid,''' defined multiple times in Dictionary with id ''', @dictid, '''.'), .)"/>
		</xsl:if>
		
		<xsl:apply-templates select="$Entry/node()"/>
	</xsl:template>
	
	<xsl:template match="drl:DirRef">
		<xsl:variable name="uri" select="concat('drlresolve://Product', $productid, '/DirTemplate/', @templateid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		<xsl:if test="not($root)">
			<xsl:value-of select="error($err, concat('Template with id ', @templateid, ' not resolved', .))"/>
		</xsl:if>
		<xsl:apply-templates select="$root/node()/drl:DirTemplate[@id = current()/@templateid]">
			<xsl:with-param name="entryid" select="@entryid"/>
			<xsl:with-param name="dirref" select="."/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="drl:DirTemplate">
		<xsl:param name="entryid"/>
		<xsl:param name="dirref"/>
		
		<xsl:variable name="uri" select="concat('drlresolve://Product', $productid, '/Directory/', @directoryid)"/>
		<xsl:variable name="root" select="document($uri)"/>
		
		<xsl:if test="not($root)">
			<xsl:value-of select="error($err, concat('Directory with id ', @directoryid, ' not resolved'))"/>
		</xsl:if>
		
		<xsl:variable name="entry" select="$root/node()/drl:Directory[@id = current()/@directoryid]/drl:Entry[@id = $entryid]"/>
		
		<xsl:if test="not($entry)">
			<xsl:value-of select="error($err, concat('Entry with id ', $entryid, ' not resolved for Directory with id ', @directoryid), $dirref)"/>
		</xsl:if>
		
		<xsl:apply-templates>
			<xsl:with-param name="entry" select="$entry"/>
			<xsl:with-param name="dirref" select="$dirref"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="drl:AttrRef">
		<xsl:param name="entry"/>
		<xsl:param name="dirref"/>
		<xsl:variable name="Attr" select="$entry/drl:Attr[@id = current()/@attrid]"/>
		<xsl:if test="not($Attr)">
			<xsl:value-of select="error($err, concat('Attribute with id ', @attrid, ' not resolved'), .)"/>
		</xsl:if>
		<xsl:apply-templates select="$Attr"/>
	</xsl:template>
	
	<xsl:template match="drl:Attr">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="text()">
		<xsl:copy-of select="."/>
	</xsl:template>
	
	<xsl:template match="node()" priority="-1">
		<xsl:param name="refid"/>
		<xsl:param name="entry"/>
		<xsl:param name="dirref"/>
		
		<xsl:copy>
			<xsl:copy-of select="attribute::node()"/>
			<xsl:attribute name="drl:system-id" select="saxon:system-id()"/>
			<xsl:attribute name="drl:line-number" select="saxon:line-number()"/>
			<xsl:apply-templates>
				<xsl:with-param name="refid" select="$refid"/>
				<xsl:with-param name="entry" select="$entry"/>
				<xsl:with-param name="dirref" select="$dirref"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>