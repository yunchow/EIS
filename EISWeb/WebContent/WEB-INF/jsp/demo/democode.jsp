<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglib.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>DEMO添加</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript" src="plugins/SyntaxHighlighter/scripts/shCore.js"></script>
  <script type="text/javascript" src="plugins/SyntaxHighlighter/scripts/shBrushJScript.js"></script>
  <link type="text/css" rel="stylesheet" href="plugins/SyntaxHighlighter/styles/shCoreDefault.css" />
  <script type="text/javascript">
	SyntaxHighlighter.config.bloggerMode = true;
	SyntaxHighlighter.all();
</script>
 </head>
 <body style="overflow: auto">
  <pre class="brush:js">
   ${demo.democode}
   </pre>
 </body>
</html>
