<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version = '2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:d="http://math.spbu.ru/drl" xmlns:db="http://docbook.org/ns/docbook">
    
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no"/>
    
    <xsl:template match="/">
        <xsl:text>&#xa;</xsl:text>
        <xsl:apply-templates/>
    </xsl:template>    
    
        
    
    <xsl:template name="otherwise">
      <xsl:element name="{local-name()}">
        <xsl:for-each select="@*, text(), *">
          <xsl:choose>
            <xsl:when test="index-of(../*,current())">
              <xsl:apply-templates select="current()"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:copy />
            </xsl:otherwise>
          </xsl:choose>
        </xsl:for-each>
      </xsl:element>
    </xsl:template>
    
    <xsl:template match="d:Nest">
      <xsl:choose>
        <xsl:when test="parent::db:tbody">
          <xsl:element name="FakeRow">
            <xsl:element name="FakeEntry">
              <xsl:call-template name="otherwise"/>
            </xsl:element>
          </xsl:element>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="otherwise"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:template>    
    
    <xsl:template match="*">
      <xsl:choose>
        <xsl:when test="name()='d:InfElement' or
                                 name()='d:InfProduct' or
                                 name()='d:FinalInfProduct' or
                                 name()='d:DirTemplate' or
                                 name()='d:Dictionary' or
                                 name()='d:Directory' or
                                 name()='d:Product' ">
          <xsl:result-document method="xml" href ="{resolve-uri(concat(@id,'_',generate-id(/),'.drl'),document-uri(/))}">
            <xsl:element name="{local-name()}">
                <xsl:attribute name="filename">
                  <xsl:value-of select="tokenize(document-uri(/), '/')[last()]"/>
                </xsl:attribute>
                <xsl:attribute name="parenttype">
                  <xsl:value-of select="local-name(..)"/>
                </xsl:attribute>
                <xsl:if test="name()='d:FinalInfProduct' or
                                  name()='d:DirTemplate' or
                                  name()='d:Dictionary' or
                                  name()='d:Directory'">
                  <xsl:attribute name="productid">
                    <xsl:value-of select="@productid[..]"/>
                  </xsl:attribute>
                </xsl:if>
                <xsl:if test="name()='d:Product'">
                  <xsl:attribute name="parentnameattr">
                    <xsl:value-of select="@name[..]"/>
                  </xsl:attribute>
                </xsl:if>
                <xsl:for-each select="@*">
                  <xsl:copy/>
                </xsl:for-each>
				<xsl:for-each select="../@*">
          <xsl:copy/>
				</xsl:for-each>
                <xsl:apply-templates/>
            </xsl:element>
          </xsl:result-document>
        </xsl:when>
        <xsl:when test="name() = 'd:Insert-After' or
								name() = 'd:Insert-Before' or
								name() = 'd:Replace-Nest'">
          <xsl:choose>
            <xsl:when test="db:row or db:entry">
              <xsl:element name="{local-name()}">
                <xsl:attribute name="nestid">
                  <xsl:value-of select="@nestid"/>
                </xsl:attribute>
                <xsl:choose>
              <xsl:when test="not(exists(db:tbody))">
                <xsl:element name="FakeTable">
                  <xsl:attribute name="cols">
                    <xsl:value-of select="count(db:row[1]/*)+count(db:entry)"/>
                  </xsl:attribute>
                  <xsl:element name="FakeTableBody">
                    <xsl:for-each select="*">
                      <xsl:choose>
                        <xsl:when test="local-name()='row'">
                          <xsl:call-template name="otherwise"/>
                        </xsl:when>
                        <xsl:when test="local-name()='entry'">
                          <xsl:element name="FakeRow">
                            <xsl:copy-of select="current()"/>
                          </xsl:element>
                        </xsl:when>
                        <xsl:otherwise>
                          <xsl:call-template name="otherwise"/>
                        </xsl:otherwise>
                      </xsl:choose>
                    </xsl:for-each>
                  </xsl:element>
                </xsl:element>
              </xsl:when>
              <xsl:otherwise>
                <xsl:call-template name="otherwise"/>
              </xsl:otherwise>
              </xsl:choose>
              </xsl:element>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="otherwise"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <xsl:element name="{local-name()}">
              <xsl:for-each select="@*">
                  <xsl:copy/>
              </xsl:for-each>
              <xsl:apply-templates/>
          </xsl:element>
         </xsl:otherwise>
     </xsl:choose>
 </xsl:template>
    
    <xsl:variable name="tab" select="'&#009;'"/>
    <xsl:template match="text()">
        <xsl:choose>
            <xsl:when test="string-length(normalize-space(.))=0">
                <xsl:value-of select="translate(. , concat(' ', $tab), '')"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="normalize-space(.)"/>
            </xsl:otherwise>
        </xsl:choose>            
    </xsl:template>

    
</xsl:stylesheet>
