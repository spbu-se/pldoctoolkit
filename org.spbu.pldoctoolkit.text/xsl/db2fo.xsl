<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:sverb="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.Verbatim"
        xmlns:xverb="com.nwalsh.xalan.Verbatim"
        xmlns:lxslt="http://xml.apache.org/xslt">

  <xsl:import href="./docbook/fo/docbook.xsl"/>

  <xsl:param name="callout.graphics" select="'0'"/>
  <xsl:param name="callout.unicode" select="1"/>
  <!--xsl:param name="callout.defaultcolumn" select="'80'"/-->
  <xsl:param name="draft.mode" select="'no'"/> 
  <xsl:param name="ulink.hyphenate" select="'&#xAD;'"/>
  <xsl:param name="insert.xref.page.number" select="'no'"/>
  <xsl:template name="force.page.count">no-force</xsl:template>
<!--
  <xsl:param name="use.extensions" select="'1'"/>
  <xsl:param name="xep.extensions" select="1"/>
-->

</xsl:stylesheet>
