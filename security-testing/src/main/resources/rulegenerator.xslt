<?xml version='1.0'?>

<!--
	Author:tharter
	
	Generate JSON data structure which is compatible with Apache Ranger and contains policies
	equivalent to those in a policy description XML file as used by TestGenerator.
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text"/>

	<xsl:template match="/">
		<xsl:text>
		{
			"service": "hdfs",
			"name": "policy generator test",
			"isAuditable": true,
		</xsl:text>
		<xsl:apply-templates/>
		<xsl:text>}</xsl:text>
	</xsl:template>
	
	<xsl:template match="securitytest/resources">
		<xsl:text>"resources": {</xsl:text>
		<xsl:apply-templates/>
		<xsl:text>},</xsl:text>
	</xsl:template>
	
	<xsl:template match="securitytest/rules">
		<xsl:text>"policyItems": [</xsl:text><xsl:apply-templates mode="inrules"/><xsl:text>]</xsl:text>
	</xsl:template>
	
	<xsl:template match="rule" mode="inrules">
		<xsl:text>{
			"users": [
			],
			"groups": [
			],
			"delegateAdmin": false,
			"accesses": [ </xsl:text><xsl:apply-templates  mode="inrule"/><xsl:text> ],
			"conditions": []
		},</xsl:text>
	</xsl:template>
	
	<xsl:template match="actions/action" mode="inrule">
		<xsl:apply-templates mode="actionsub" select="/securitytest/actions/action[@class=substring-before(current()/@ref,'_') and @name=substring-after(current()/@ref,'_')]"/>
	</xsl:template>
	
	<xsl:template match="action" mode="actionsub">
		<xsl:text>{ "isAllowed": true, "type": </xsl:text><xsl:value-of select="@name"/><xsl:text></xsl:text>
		<xsl:text>},</xsl:text>
	</xsl:template>
	
	<xsl:template match="resource[@class='hdfsdir']">
		<xsl:text>
			"path": {
				"values": [ "</xsl:text><xsl:value-of select="./@name"/><xsl:text>" ]
			},
		</xsl:text>
	</xsl:template>
	
</xsl:stylesheet>