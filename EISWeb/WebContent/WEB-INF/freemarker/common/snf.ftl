<#ftl strip_whitespace=true>
<#-- 
苏宁freemarker框架
v0.2 陈琦
-->
<#-- 
jsurl 格式化js的url加入版本号，用list形式可一次可以传入多个
-->
<#macro jsurl  url=[] resConcat='false'>
<#if resConcat == 'true'><script type="text/javascript" src="${resRoot}/??<#list url as js>${js}${minSuffix}.js<#if js_has_next>,</#if></#list>?t=${buildNo}.js"></script>
<#else><#list url as js><script type="text/javascript" src="${resRoot}/${js}${minSuffix}.js" ></script></#list>
</#if>
</#macro>
<#--
cssurl 格式化css的url加入版本号，用list形式可一次可以传入多个
-->
<#macro cssurl url=[] resConcat='false'>
<#if resConcat =='true'><link rel="stylesheet" type="text/css"  href="${resRoot}/??<#list url as css>${css}${minSuffix}.css<#if css_has_next>,</#if></#list>?t=${buildNo}.css" />
<#else><#list url as css><link rel="stylesheet" type="text/css" href="${resRoot}/${css}${minSuffix}.css" /></#list>
</#if>
</#macro>