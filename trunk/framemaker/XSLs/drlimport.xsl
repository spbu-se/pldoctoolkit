<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:d="http://math.spbu.ru/drl" xmlns:db="http://docbook.org/ns/docbook">
    
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no"/>
    
    <xsl:template match="/">
        <xsl:text>&#xa;</xsl:text>
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="*">
        <xsl:element name="{local-name()}">
            <xsl:for-each select="@*">
                <xsl:copy/>
            </xsl:for-each>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="d:Insert-After">
	    <xsl:element name="Insert-After">
      <xsl:attribute name="nestid">
        <xsl:value-of select="@nestid"/>
      </xsl:attribute>
		    <xsl:choose>
			    <xsl:when test="db:row">
				    <xsl:choose>
					<xsl:when test="not(exists(db:tbody))">
				        <xsl:element name="FakeTable">
				        <xsl:attribute name="cols">10</xsl:attribute>
				            <xsl:element name="FakeTableBody">
	                            <xsl:for-each select="*">
			                        <xsl:choose>
			                            <xsl:when test="local-name()='row'">
				                            <xsl:copy-of select="current()"/>
				                        </xsl:when>
					                    <xsl:when test="local-name()='entry'">
						                    <xsl:element name="FakeRow">
							                    <xsl:copy-of select="current()"/>
							                </xsl:element>
					                    </xsl:when>
					                    <xsl:otherwise>
					                        <xsl:copy-of select="current()"/>
					                    </xsl:otherwise>
				                    </xsl:choose>
		                        </xsl:for-each>
			                </xsl:element>
			            </xsl:element>
					</xsl:when>
					<xsl:otherwise>
					    <xsl:for-each select="*">
					        <xsl:copy-of select="current()"/>
						</xsl:for-each>
					</xsl:otherwise>
					</xsl:choose>
			    </xsl:when>
			    <xsl:otherwise>
				    <xsl:for-each select="*">
			            <xsl:copy-of select="current()"/>
					</xsl:for-each>
			    </xsl:otherwise>
			</xsl:choose>
	    </xsl:element>
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
