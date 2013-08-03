<#import "/common/spring.ftl" as spring>
<#assign snf=JspTaglibs["http://it.cnsuning.com/snf"]>
<#assign sf = JspTaglibs["http://www.springframework.org/tags/form"] >
<#assign c =JspTaglibs["http://java.sun.com/jsp/jstl/core"] >
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${resRoot}/js/jquery/jquery${minSuffix}.js"></script>
<script type="text/javascript" src="${resRoot}/js/jquery/jquery${minSuffix}.validate.js"></script>

<style>
.errorMessage{color:red}
</style>

 <script type="text/javascript">
    $(document).ready(function() {
      $("#form1").validate({
        rules: {
			joinDate: "required",// simple rule, converted to {required:true}
			mobileNo: {
			  required: true,
			  minlength:2,
			  mobileV: true
			}
        },
        messages: {
          joinDate: "前台验证: The joinDate can not be empty.",
          mobileNo: {
          	required: "前台验证: The mobileNo can not be empty",
          	minlength:"前台验证: The mobileNo`length should more than 2"
          }
        },
        errorClass: "errorMessage"
      });
    });
    
    
    jQuery.validator.addMethod(
		"mobileV",
		function(value, element) {
			if (element.value == "133")
			{
				return false;
			}
				else return true;
			},
				"前台验证:The Mobile No can not begin with 133."
		); 

  </script>

<link href="${resRoot}/css/sn_common0925${minSuffix}.css" rel="stylesheet" type="text/css" />
<style>.errorClass{color:red}</style>
</head>
<body>
<form  action="saveFtl.htm" method="POST" id='form1'>

	<@spring.bind "user"/>
	<@spring.showErrors  "<br>"  "errorClass"/>

<@spring.formHiddenInput path="user.code" />
<@spring.formHiddenInput path="user.name" />


		<table>
			<TR>
				<TD>姓名:</TD>
				<TD>${user.name}</TD>
				<TD></TD>
				<TD></TD>
			</TR>
			<TR>
				<TD>省份:</TD>
				<TD></TD>
				
				<TD>电话:</TD>
				<TD><@spring.formInput path="user.mobileNo" />
				<@spring.bind "user.mobileNo"/>
				 <lable class="errorMessage">${spring.status.errorMessage}</lable>  
				 </TD>
			</TR>
			<TR>
				<TD>入职日期:</TD>
				<TD>
					<@spring.formInput path="user.joinDate" /></TD>
				<TD>入职日期:</TD>
				<TD>
				 <#if user.joinDate??>${user.joinDate?string("yyyy-MM-dd HH:mm:ss")}</#if> 		
				</TD>
			</TR>
			<TR>
				<TD>角色:</TD>
				<TD colspan="3">
				<@spring.formCheckboxes  path="user.roles" options=roleMap  separator="<br/>"/>
				</TD>
			</TR>
		
		</table>
		
		<br/>
		
		<input type="submit"/>
		<input type="button" value=" Canecl " onclick="location.href='search.htm'"/>
		
		<@snf.reDistortFormTag   name="antiDistorTest">
			<@snf.reDistortFieldTag name="price" value="100.00"/>
			<@snf.reDistortFieldTag name="inventory" value="20"/>
		</@snf.reDistortFormTag>
		
		<@snf.noRepeatSubmit formName="editUser"/>
	</form>
	
	
<P>多域名处理演示:
<li><img height="288" src=${imgUrl("images/nrgl/cpjs/17397648_20111205162327.jpg")} width="700">
</body>
</html>

	
