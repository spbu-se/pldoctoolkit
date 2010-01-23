<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://math.spbu.ru/drl" version="1.0">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>    
    <xsl:template match="*">
        <xsl:variable name="newName">
            <xsl:choose>
                <xsl:when test="name()='ProductLine' or 
                				name()='Product' or
                				name()='ProductDocumentation' or
                				name()='DocumentationCore' or
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
                <xsl:copy/>
            </xsl:for-each>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
