<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:d="http://math.spbu.ru/drl">
    
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
	
    <xsl:template match="Insert-After">
	    <xsl:for-each select="row">
		    <xsl:element name="FakeTable">
			    <xsl:element name="row">
				    <xsl:value-of select="current()"/>
				</xsl:element>
			</xsl:element>
		</xsl:for-each>
	</xsl:for-each>
    
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
