<#setting url_escaping_charset='utf-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}333333</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="${.now}" />
<script type="text/javascript" src="${resRoot}/js/jquery/jquery${minSuffix}.js"></script>
<link href="${resRoot}/css/sn_common0925${minSuffix}.css" rel="stylesheet" type="text/css" />
${head}
</head> 
<body>
<#include "/WEB-INF/freemarker/decorators/header.ftl">
${body}
<#include "/WEB-INF/freemarker/decorators/footer.ftl">
</body> 
</html>