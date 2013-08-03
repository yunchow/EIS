<#import "/common/spring.ftl" as spring>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${base}/js/json/json2.js"></script>
</head>
<body>
<br>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="<@spring.url "/ftl/user/insUser.htm" />">新建用户</a>
<br>
<br>
<table>
<th>
<td width="70px">编号</td>
<td width="100px">姓名</td>
<td width="70px">省市</td>
<td width="100px">手机</td>
<td width="120px">注册时间</td>
<td width="70px">操作</td>
</th>
<#list result.datas as datas>
<tr>
<td></td>
<td><a href="<@spring.url "/ftl/user/showUser_${datas.code}.htm" />">${datas.code}</a></td>
<td>${datas.name}</td>
<td>${datas.province}</td>
<td>${datas.mobileNo}</td>
<td>${datas.joinDate?string("yyyy-MM-dd")}</td>
<td><a href="<@spring.url "/ftl/user/showUser_${datas.code}.htm" />">修改</a>&nbsp;&nbsp;<a href="<@spring.url "/ftl/user/delUser_${datas.code}.htm" />">删除</a></td>
</tr>
</#list>
</table>
<span id="ttlcnt">total: ${result.totalDataCount}</span>
</body>
</html>