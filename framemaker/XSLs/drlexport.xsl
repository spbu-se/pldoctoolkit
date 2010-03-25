<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://math.spbu.ru/drl" xmlns="http://docbook.org/ns/docbook">
	<!--xmlns="http://docbook.org/ns/docbook" -->
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="no" />
	
	<xsl:template name="copy_entry">
    <xsl:if test="* or normalize-space()">
      <xsl:copy-of select="."/>
    </xsl:if>
	</xsl:template>
	
  <xsl:template name="copy_row">
    <xsl:for-each select="*">
      <xsl:choose>
        <xsl:when test="local-name()='entry'">
          <xsl:call-template name="copy_entry"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="otherwise"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template name="otherwise">
    <xsl:element name="{local-name()}">
		<xsl:copy-of select="attribute()"/>
		<xsl:apply-templates/>
    </xsl:element>
  </xsl:template>
  
	<xsl:template name="remove_fakes">
		<xsl:element name="{concat('d:',local-name())}">
			<xsl:attribute name="nestid"><xsl:value-of select="@nestid"/></xsl:attribute>
			<xsl:for-each select="*">
				<xsl:choose>
					<xsl:when test="local-name()='FakeTable'">
						<xsl:for-each select="*">
							<xsl:choose>
								<xsl:when test="local-name()='FakeTableBody'">
									<xsl:for-each select="*">
										<xsl:choose>
											<xsl:when test="local-name()='row'">
												<xsl:element name="row">
													<xsl:call-template name="copy_row"/>
												</xsl:element>
											</xsl:when>
											<xsl:when test="local-name()='FakeRow'">
												<xsl:call-template name="copy_row"/>
											</xsl:when>
											<xsl:otherwise>
                        <xsl:call-template name="otherwise"/>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="otherwise"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="otherwise"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="graphic">
		<xsl:element name="graphic">
			<xsl:for-each select="@*">
				<xsl:choose>
					<xsl:when test="name()='entityref'">
					</xsl:when>
					<xsl:otherwise>
						<xsl:copy></xsl:copy>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			<xsl:variable name="ent"><xsl:value-of select="@entityref"/></xsl:variable>
			<xsl:attribute name="fileref"><xsl:value-of select="unparsed-entity-uri($ent)"/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="add_namespace_prefix">
    <xsl:param name="exclude"/>
    <xsl:variable name="newName">
      <xsl:choose>
        <xsl:when test="name()='ProductLine' or 
                      name()='Product' or
                      name()='DocumentationCore' or
                      name()='ProductDocumentation' or
                      name()='InfProduct' or
                      name()='InfElement' or
                      name()='Dictionary' or
                      name()='Entry' or
                      name()='FinalInfProduct' or
                      name()='SetValue' or
                      name()='Adapter' or
                      name()='Replace-Nest' or
                      name()='Insert-Before' or
                      name()='Insert-After' or
                      name()='DirRef' or
                      name()='DictRef' or
                      name()='Directory' or
                      name()='Attr' or
                      name()='DirTemplate' or
                      name()='AttrRef' or
                      name()='InfElemRef' or
                      name()='InfElemRefGroup' or
                      name()='Conditional' or
                      name()='Nest'
                      ">
          <xsl:value-of select="concat('d:', name())"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="name()"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:element name="{$newName}">
      <xsl:for-each select="@*">
        <xsl:if test="not(exclude and (local-name()='productid')) and 
                          not(local-name()='parenttype') and 
                          not(local-name()='filename' and 
                          not(local-name()='parentnameattr'))">
          <xsl:copy/>
        </xsl:if>
      </xsl:for-each>
      <xsl:apply-templates/>
    </xsl:element>
	</xsl:template>	

	<xsl:template match="*" >
    <xsl:choose>
      <xsl:when test="local-name() = 'ProductLine' or 
                              local-name() = 'DocumentationCore' or
                              local-name() = 'ProductDocumentation'">
        <xsl:result-document method="xml" href="{resolve-uri(@filename,document-uri(/))}">
          <xsl:call-template name="add_namespace_prefix">
            <xsl:with-param name="exclude" select="false"/>
          </xsl:call-template>
        </xsl:result-document>
      </xsl:when>
      <xsl:when test="local-name() = 'Insert-After' or
                              local-name() = 'Insert-Before' or
                              local-name() = 'Replace-Nest'">
        <xsl:call-template name="remove_fakes"/>
      </xsl:when>
      <xsl:when test="local-name() = 'FinalInfProduct' or
                              local-name() = 'Dictionary' or
                              local-name() = 'Directory' or
                              local-name() = 'DirTemplate'">
        <xsl:call-template name="add_namespace_prefix">
          <xsl:with-param name="exclude" select="true"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="add_namespace_prefix">
          <xsl:with-param name="exclude" select="false"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
	</xsl:template>
	
	<!-- Attributes to exclude -->
	<xsl:template match="@parenttype">
    <xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="@filename"/>
	<xsl:template match="@parentnameattr"/>
	
</xsl:stylesheet>
