<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" 
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:sverb="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.Verbatim"
        xmlns:xverb="com.nwalsh.xalan.Verbatim"
        xmlns:lxslt="http://xml.apache.org/xslt">

  <xsl:import href="./docbook/htmlhelp/htmlhelp.xsl"/>

  <xsl:param name="l10n.gentext.language" select="'ru'"/>
  <xsl:param name="htmlhelp.encoding" select="'windows-1251'"/>
  <xsl:param name="default.encoding" select="'windows-1251'"/>
  <xsl:param name="chunker.output.encoding" select="'windows-1251'"/>
  <xsl:param name="textdata.default.encoding" select="'windows-1251'"/>
  <xsl:param name="saxon.character.representation" select="'native'"/>
  <xsl:param name="draft.mode" select="'no'"/>

                                   
</xsl:stylesheet>
