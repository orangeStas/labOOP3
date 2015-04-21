<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:msxsl="urn:schemas-microsoft-com:xslt" exclude-result-prefixes="msxsl"
        >
    <xsl:template match="WorkersList">
        <WorkersList>
            <xsl:apply-templates/>
        </WorkersList>
    </xsl:template>

    <xsl:template match="workers">
        <workers>
            <xsl:apply-templates/>
        </workers>
    </xsl:template>

    <xsl:template match='*'>
        <xsl:copy>
            <xsl:for-each select='@*|*[not(* or @*)]'>
                <xsl:attribute name='{name(.)}'><xsl:value-of select='.'/>
                </xsl:attribute>
            </xsl:for-each>
            <xsl:apply-templates select='*[* or @*]|text()'/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>