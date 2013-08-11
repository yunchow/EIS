<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglib.jsp"%>
<t:tabs id="typeGroupTabs" iframe="false"  tabPosition="bottom">
 <c:forEach items="${typegroupList}" var="typegroup">
  <t:tab iframe="systemController.do?typeList&typegroupid=${typegroup.id}" icon="icon-add" title="${typegroup.typegroupname}" id="${typegroup.typegroupcode}"></t:tab>
 </c:forEach>
</t:tabs>
