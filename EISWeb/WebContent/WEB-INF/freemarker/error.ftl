<#setting url_escaping_charset='utf-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="${resRoot}/js/jquery/jquery${minSuffix}.js"></script>
<link href="${resRoot}/css/sn_common0925${minSuffix}.css" rel="stylesheet" type="text/css" />
${head}
</head> 
<body>
<#include "/WEB-INF/freemarker/decorators/header.ftl">
<!-- 错误信息提示 -->
    <div class="errorMessageShowTop"></div>
<div class="errorMessageShow">
    	<span></span>
     <p>
         <b><font color="red" size="5">
         ${exception.friendlyMessage}
         </font> 	
       	
    </p>
    <a href="http://www.suning.com/" class="backIndex"></a>
    <dl>
       	<dt>您可以：</dt>
        <dd>1.检查刚才输入。</dd>
        <dd>2.看看我们的帮助，<a href="/wcsstore/ConsumerDirectStorefrontAssetStore/OtherArea/bzzx/xssl.html" class="firtLink">新手上路</a></dd>
        <dd>3.去其他地方逛逛：<a href="http://www.suning.com/" class="firtLink">苏宁易购</a>|<a href="http://zhishi.suning.com/zhishitang/home/home.action?userId=-1002&userName=&email=">易购社区</a></dd>
    </dl>
  </div>
    
    <!-- 底部广告 -->
    <div class="bottomRecom">
    	<dl>
        	<dd class="addRecom01"><a href="http://www.suning.com/wcsstore/ConsumerDirectStorefrontAssetStore/images/advertise/yy/0413gou/no.jpg"><img src="${resRoot}/images/no.jpg" /></a></dd>
        	<dd class="addRecom02"><a href="http://www.suning.com/wcsstore/ConsumerDirectStorefrontAssetStore/images/advertise/yx/2011420xydl/index.html"><img src="${resRoot}/images/br.jpg" /></a></dd>
        </dl>
        <div class="clear"></div>
    </div>
<#include "/WEB-INF/freemarker/decorators/footer.ftl">
</body> 
</html> 

