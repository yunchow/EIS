<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Complex Layout - XXX</title>
    <style type="text/css">
		a {
			color: Black;
			text-decoration: none;
		}
		
		a:hover {
			color: black;
			text-decoration: none;
		}
		
    </style>
    <link rel="stylesheet" type="text/css" href="plugins/easyui/themes/metro/easyui.css">
    <link rel="stylesheet" type="text/css" href="plugins/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="resources/accordion/css/accordion.css">
    <link rel="stylesheet" type="text/css" href="resources/accordion/css/icons.css">
	<link rel="stylesheet  type="text/css" href="resources/accordion/css/accordion.css">
    <script type="text/javascript" src="plugins/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="plugins/tools/dataformat.js"></script>
    <script type="text/javascript" src="plugins/easyui/jquery.easyui.min.1.3.2.js"></script>
    <script type="text/javascript" src="plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="resources/js/core.js"></script>
    <script type="text/javascript" src="resources/js/context.js"></script>
</head>
<body  class="easyui-layout" style="overflow-y:hidden" scroll="no">
    <div data-options="region:'north',border:false" style="height:60px;width:100%;margin:0px;padding:0px;overflow:hidden;">
    	<!--<img src="plugins/login/images/toplogo-main.png" >-->
    	<font style="font-size:18px;">XXXX系统</font>
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
    
    <div data-options="region:'west',split:true"  title="导航菜单" style="width:150px;padding:1px;">
        <div class="easyui-accordion" data-options="fit:true,border:false">
            <div title="系统管理" data-options="iconCls:'group_add'" style="padding:0px">
                <ul>
                	<li>
						<div data-options="iconCls:'pencil'" onclick="context.addTab('菜单管理','sysmanage/menu/page.htm','pencil')">
							<a href="javascript:;">
								<span class="icon pencil">&nbsp;</span>
								<span class="nav">菜单管理</span>
							</a>
						</div>
					</li>
                </ul>
            </div>
            <div title="业务管理" data-options="iconCls:'folder'" style="padding:0px;">
                <ul>
                	<li>
						<div url="userController.do?user" title="用户管理" onclick="context.addTab('用户管理','http://www.baidu.com','pencil')">
							<a href="#">
								<span class="icon pencil">&nbsp;</span>
								<span class="nav">用户管理</span>
							</a>
						</div>
					</li>
                </ul>
            </div>
            
        </div>
    </div>
    
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="homeTabBar" class="easyui-tabs" data-options="border:false,fit:true,onContextMenu:context.onMainTabContextMenu" style="width:700px;height:250px">
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
    
    <div data-options="region:'east',split:false,iconCls:'icon-reload',collapsed:false" title="辅助工具" style="width:185px;overflow:hidden;">
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
		<div align="center" style="color: #CC99FF; padding-top: 2px">
			&copy; 版权所有<span class="tip"><a href="#" title="JEECG_v3.1 Simple版本">nick</a> (推荐谷歌浏览器，获得更快响应速度)  技术支持:<a href="#" title="nick Simple版本">nick</a></span>
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

	<script>
		$(function(){
			context.ready();
		});
    </script>
</body>
</html>