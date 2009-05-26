<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:sverb="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.Verbatim"
        xmlns:xverb="com.nwalsh.xalan.Verbatim"
        xmlns:lxslt="http://xml.apache.org/xslt">

  <xsl:import href="./docbook/fo/docbook.xsl"/>

  <xsl:param name="callout.graphics" select="'0'"/>
  <xsl:param name="callout.unicode" select="'1'"/>
  <!--xsl:param name="callout.defaultcolumn" select="'80'"/-->
  <xsl:param name="draft.mode" select="'no'"/> 
  <xsl:param name="ulink.hyphenate" select="'&#xAD;'"/>
  <xsl:param name="insert.xref.page.number" select="'no'"/>
<!--
  <xsl:param name="use.extensions" select="'1'"/>
  <xsl:param name="xep.extensions" select="1"/>
-->

<!--
   Fix bug of Docbook XSL 1.69+ and FOP compatibility: 
   return to wrapper representation of anchors insted of inline
-->
<xsl:template match="anchor">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <fo:wrapper id="{$id}"/>
</xsl:template>


<!--
   Remove pagebreak after title page 
-->
<xsl:template name="book.titlepage.separator"/>

<!--
   Remove "verso" title page 
-->
<xsl:template name="book.titlepage.before.verso"/>
<xsl:template name="book.titlepage.verso"/>

<!--
   Remove pagenumbers in para references 
-->
<xsl:template match="para" mode="page.citation"/>

<!--
   Add "Chapter N." to TOC titles. 
-->
<xsl:template name="toc.line">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="label">
    <xsl:apply-templates select="." mode="label.markup"/>
  </xsl:variable>


  <fo:block text-align-last="justify"
            end-indent="{$toc.indent.width}pt"
            last-line-end-indent="-{$toc.indent.width}pt">
    <xsl:choose>
      <xsl:when test="local-name(.) = 'part'">
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="font-family">Helvetica</xsl:attribute>
        <xsl:attribute name="font-size">16pt</xsl:attribute>
        <xsl:attribute name="space-before">10pt</xsl:attribute>
        <xsl:attribute name="space-after">10pt</xsl:attribute>
        <xsl:attribute name="text-align-last">left</xsl:attribute>
      </xsl:when>
      <xsl:when test="local-name(.)='chapter'
                    or local-name(.)='appendix'
                    or local-name(.)='glossary'
                    or local-name(.)='index'
                    or local-name(.)='preface'">
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="font-size">12pt</xsl:attribute>
        <xsl:attribute name="space-before">4pt</xsl:attribute>
        <xsl:attribute name="space-after">2pt</xsl:attribute>
      </xsl:when>
    </xsl:choose>
    <fo:inline keep-with-next.within-line="always">
      <fo:basic-link internal-destination="{$id}">
        <xsl:choose>
   <xsl:when test="local-name(.) = 'part'">
     <xsl:call-template name="gentext">
       <xsl:with-param name="key" select="'Part'"/>
     </xsl:call-template>
     <xsl:text> </xsl:text>
   </xsl:when>
   <xsl:when test="local-name(.) = 'chapter'">
     <xsl:call-template name="gentext">
       <xsl:with-param name="key" select="'Chapter'"/>
     </xsl:call-template>
     <xsl:text> </xsl:text>
   </xsl:when>
   <xsl:when test="local-name(.) = 'appendix'">
     <xsl:call-template name="gentext">
       <xsl:with-param name="key" select="'Appendix'"/>
     </xsl:call-template>
     <xsl:text> </xsl:text>
   </xsl:when>
 </xsl:choose>
        <xsl:if test="$label != ''">
          <xsl:copy-of select="$label"/>
          <xsl:value-of select="$autotoc.label.separator"/>
        </xsl:if>
        <xsl:apply-templates select="." mode="title.markup"/>
      </fo:basic-link>
    </fo:inline>
    <xsl:if test="local-name(.) != 'part'">
      <fo:inline keep-together.within-line="always">
        <xsl:text> </xsl:text>
        <fo:leader leader-pattern="dots"
                   leader-pattern-width="3pt"
                   leader-alignment="reference-area"
                   keep-with-next.within-line="always"/>
        <xsl:text> </xsl:text>
        <fo:basic-link internal-destination="{$id}">
          <fo:page-number-citation ref-id="{$id}"/>
        </fo:basic-link>
      </fo:inline>
    </xsl:if>
  </fo:block>
</xsl:template>

</xsl:stylesheet>
