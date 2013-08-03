<#--
jquery实现的异步加载的宏，需要先加载jquery
-->
<#macro insertAfter url targetId>
<script type="text/javascript">$.get("${url}",function(html){$(html).insertAfter('#${targetId}')});</script> 
</#macro>
<#macro insertBefore url targetId>
<script type="text/javascript">$.get("${url}",function(html){$(html).insertBefore('#${targetId}')});</script> 
</#macro>
<#macro appendTo url targetId>
<script type="text/javascript">$.get("${url}",function(html){$(html).appendTo('#${targetId}')});</script> 
</#macro>
<#macro prependTo url targetId>
<script type="text/javascript">$.get("${url}",function(html){$(html).prependTo('#${targetId}')});</script> 
</#macro>
<#macro load url targetId targetClass>
<div id="${targetId}" class="${targetClass}"><script type="text/javascript">$("#${targetId}").load("${url}");</script></div>
</#macro>