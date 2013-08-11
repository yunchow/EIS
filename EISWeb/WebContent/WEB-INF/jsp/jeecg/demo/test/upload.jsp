<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglib.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>Excel导入</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
   <fieldset class="step">
    <div class="form">
     <t:upload name="fiels" buttonText="选择要导入的文件" uploader="jpPersonController.do?importExcel" extend="*.xls" id="file_upload" formData="documentTitle"></t:upload>
    </div>
    <div class="form" id="filediv" style="height:50px">
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
