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
    <link rel="stylesheet" type="text/css" href="plugins/easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="plugins/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="plugins/accordion/css/accordion.css">
    <script type="text/javascript" src="plugins/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="plugins/tools/dataformat.js"></script>
    <script type="text/javascript" src="plugins/easyui/jquery.easyui.min.1.3.2.js"></script>
    <script type="text/javascript" src="plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body  class="easyui-layout" style="overflow-y:hidden" scroll="no">
    <div data-options="region:'north',border:false" style="height:60px;width:100%;margin:0px;padding:0px;overflow:hidden;">
    	<img src="plugins/login/images/toplogo-main.png" >
    	<div style="float:right;padding-right:10px;">
    		<dl>
    			<dd>
    				<span style="color: #CC33FF">当前用户:</span><span style="color: #666633">admin &nbsp;&nbsp; <span style="color: #CC33FF">职务</span>:<span style="color: #666633">管理员</span>
    			</dd>
    			<dd>
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
    
    <div data-options="region:'west',split:true"  title="导航菜单" style="width:180px;padding:1px;">
        <div class="easyui-accordion" data-options="fit:true,border:false">
            <div title="Title1" style="padding:10px;">
                content1
            </div>
            <div title="Title2" data-options="selected:true" style="padding:10px;">
                content2
            </div>
            <div title="Title3" style="padding:10px">
                content3
            </div>
        </div>
    </div>
    
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="homeTabMenueBar" class="easyui-tabs" data-options="border:false,fit:true" style="width:700px;height:250px">
	        <div class="centerTabButton" data-options="closable:true" title="About" style="padding:10px">
	            <p style="font-size:14px">jQuery EasyUI framework help you build your web page easily.</p>
	            <ul>
	                <li>easyui is a collection of user-interface plugin based on jQuery.</li>
	                <li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
	                <li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
	                <li>complete framework for HTML5 web page.</li>
	                <li>easyui save your time and scales while developing your products.</li>
	                <li>easyui is very easy but powerful.</li>
	            </ul>
	        </div>
	        <div class="centerTabButton" title="My Documents" data-options="closable:true" style="padding:10px">
	            <ul class="easyui-tree" data-options="url:'../tabs/tree_data1.json',animate:true"></ul>
	        </div>
	        <div class="centerTabButton" title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
	            This is the help content.
	        </div>
	    </div>
    </div>
    
    <div data-options="region:'east',split:false,iconCls:'icon-reload',collapsed:true" title="辅助工具" style="width:190px;">
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
			&copy; 版权所有<span class="tip"><a href="http://www.jeecg.org" title="JEECG_v3.1 Simple版本">JEECG_v3.1</a> (推荐谷歌浏览器，获得更快响应速度)  技术支持:<a href="#" title="JEECG_v3.1 Simple版本">JEECG_v3.1</a></span>
		</div>
    </div>
    
    <div id="mm" class="easyui-menu" style="width:150px;">
        <div id="mm-tabclose">关闭</div>
        <div id="mm-tabcloseall">全部关闭</div>
        <div id="mm-tabcloseother">除此之外全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-tabcloseright">当前页右侧全部关闭</div>
        <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	</div>

	<script>
		function menuHandler(item){
		    alert(item.name);
		}
		$(function(){
		    $(".tabs li").bind('contextmenu',function(e){
		        e.preventDefault();
		        $('#mm').menu('show', {
		            left: e.pageX,
		            top: e.pageY
		        });
		    });
		});
    </script>
</body>
</html>