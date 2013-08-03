<#import "/common/spring.ftl" as spring>
<#assign snf=JspTaglibs["http://it.cnsuning.com/snf"]>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<script type="text/javascript" src="${resRoot}/js/jquery/jquery${minSuffix}.js"></script>
<link href="${resRoot}/css/sn_common0925${minSuffix}.css" rel="stylesheet" type="text/css" />
</head>
<body>
<br>
<br>
<font size="15" color="blue"></font>
<br>
<br>
<br>
<form  action="saveFtl.htm" method="POST">
		<table>
		    <TR>
				<TD>编号:</TD>
				<TD><@spring.formInput path="user.code" /></TD>
			</TR>
			<TR>
				<TD>姓名:</TD>
				<TD><@spring.formInput path="user.name" /></TD>
			</TR>
			<TR>
				<TD>电话:</TD>
				<TD><@spring.formInput path="user.mobileNo" /></TD>
			</TR>
			<TR>
				<TD>入职日期:</TD>
				<TD>
					<@spring.formInput path="user.joinDate" /></TD>
			</TR>
		</table>
		<br/>
		<input type="submit"/>
		<input type="button" value=" Canecl " onclick="history.go(-1);"/>
		
		<@snf.reDistortFormTag   name="antiDistorTest">
			<@snf.reDistortFieldTag name="price" value="100.00"/>
			<@snf.reDistortFieldTag name="inventory" value="20"/>
		</@snf.reDistortFormTag>
	</form>
</body>
</html>
