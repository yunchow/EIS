<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ubuntu</title>
    <script type="text/javascript" src="resources/plugins/jquery/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="resources/plugins/jquery/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="resources/js/ext/cookie.js"></script>
    <script type="text/javascript">
    	var theme = $.cookie("theme");
    	if (!theme) {
    		theme = "metro";
    	}
    	document.write('<link rel="stylesheet" type="text/css" href="resources/plugins/jquery-easyui-1.3.4/themes/'+ theme +'/easyui.css">');
    </script>
    <link rel="stylesheet" type="text/css" href="resources/plugins/jquery-easyui-1.3.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="resources/plugins/fine-uploader-3.8.2/fineuploader.css">
    <link rel="stylesheet" type="text/css" href="resources/css/icons.css">
	<link rel="stylesheet" type="text/css" href="resources/css/style.css">
    <script type="text/javascript" src="resources/plugins/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="resources/plugins/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="resources/plugins/jquery-file-download-1.4.2/jquery.fileDownload-1.4.2.js"></script>
    <script type="text/javascript" src="resources/js/ext/combotree-ext.js"></script>
    <script type="text/javascript" src="resources/js/ext/date.js"></script>
    <script type="text/javascript" src="resources/js/sup/jns.js"></script>
    <script type="text/javascript" src="resources/js/sup/tools.js"></script>
    <script type="text/javascript" src="resources/js/sup/config.js"></script>
    <script type="text/javascript" src="resources/js/sup/uuid.js"></script>
    <script type="text/javascript" src="resources/js/sup/store.js"></script>
    <script type="text/javascript" src="resources/js/sup/context.js"></script>
    <script type="text/javascript" src="resources/js/sup/base.js"></script>
    <script type="text/javascript" src="resources/js/sup/btree.js"></script>
</head>
<body  class="easyui-layout" style="overflow-y:hidden" scroll="no" data-options="boder:false">
    <div data-options="region:'north',border:false" style="height:60px;width:100%;margin:0px;padding:0px;overflow:hidden;">
    	<img src="resources/image/logo.jpg" style="height:60px;">
    	<div style="float:right;padding-right:10px;">
    		<dl>
    			<dd>
    				<span style="color: #369">当前用户:</span><span style="color: #666633">张三&nbsp;&nbsp;</span>
    				<span style="color: #369">职务</span>:<span style="color: #666633">管理员&nbsp;&nbsp;</span>
    				<span style="color: #369">主题</span>:
    				<select class="easyui-combobox" data-options="editable:false,panelHeight:'auto',onChange:context.onChangeTheme">
    					<option value="metro">Win8</option>
    					<option value="metro-blue">蓝色</option>
    					<option value="metro-gray">深灰</option>
    					<option value="metro-green">绿色</option>
    					<option value="metro-orange">橙色</option>
    					<option value="metro-red">红色</option>
    					<option value="default">经典</option>
    					<option value="pepper-grinder">磨砂</option>
    					<option value="black">黑色</option>
    					<option value="bootstrap">银灰</option>
    					<option value="gray">灰色</option>
    					<option value="dark-hive">深黑</option>
    					<option value="sunny">阳光</option>
    					<option value="cupertino">库比</option>
    				</select>
    			</dd>
    			<dd style="text-align:right;">
    				<a href="javascript:void(0);" class="easyui-menubutton" menu="#controlPanelMenue" iconCls="icon-help">控制面板</a>
    				<a href="javascript:void(0);" class="easyui-menubutton" menu="#logoutMenue" iconCls="icon-back">注销</a>
    			</dd>
    		</dl>
    	</div>
		<div id="controlPanelMenue" style="width: 100px; display: none;">
			<div onclick="openwindow('用户信息','userController.do?userinfo')">个人信息</div>
			<div class="menu-sep"></div>
			<div onclick="add('修改密码','userController.do?changepassword')">修改密码</div>
		</div>
		<div id="logoutMenue" style="width: 100px; display: none;">
			<div onclick="exit('loginController.do?logout','确定退出该系统吗 ?',1);">退出系统</div>
		</div>
    </div>
    
    <div data-options="region:'west',split:true" href="home/menu/list.htm" title="导航菜单" style="width:150px;padding:1px;">
        
    </div>
    
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="homeTabBar" class="easyui-tabs" data-options="border:false,fit:true" style="width:700px;height:250px">
	        <div class="centerTabButton" data-options="closable:true,iconCls:'icon-ok'" title="About" style="padding:10px">
	            <p style="font-size:14px">jQuery EasyUI framework help you build your web page easily.</p>
	            <ul>
	                <li>easyui is a collection of user-interface plugin based on jQuery.</li>
	                <li>easyui provides essential functionality for building modem, interactive, javascript contextlications.</li>
	                <li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
	                <li>complete framework for HTML5 web page.</li>
	                <li>easyui save your time and scales while developing your products.</li>
	                <li>easyui is very easy but powerful.</li>
	            </ul>
	        </div>
	    </div>
    </div>
    
    <div data-options="region:'east',split:true,iconCls:'icon-reload',collapsed:false" title="辅助工具" style="width:195px;overflow:hidden;">
        <div class="easyui-tabs" data-options="border:false" style="height:220px;">
        	<div title="日历" style="padding:0px;">
        		<div class="easyui-calendar" data-options="border:false"></div>
        	</div>
        </div>
        <div class="easyui-tabs" data-options="border:false">
        	<div title="在线人员" style="padding:20px;">
        		
        	</div>
        </div>
    </div>
    
    <div data-options="region:'south',split:false,border:false" style="height:25px;overflow:hidden;">
		<div align="left" style="color:#333;padding-top:4px">
			&copy;2013&nbsp; nick chow all rights reserved
		</div>
    </div>
    
    <div id="mmCenterMainTab" class="easyui-menu" style="width:150px;">
        <div id="mm-tabclose">关闭</div>
        <div id="mm-tabcloseall">全部关闭</div>
        <div id="mm-tabcloseother">除此之外全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-tabcloseright">当前页右侧全部关闭</div>
        <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	</div>
</body>
</html>