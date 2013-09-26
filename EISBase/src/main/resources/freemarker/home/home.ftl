<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${Session.user.trueName}您好，欢迎使用</title>
    <link rel="stylesheet" type="text/css" href="resources/plugins/jquery-easyui-1.3.4/themes/${theme!'default'}/easyui.css">
    <link rel="stylesheet" type="text/css" href="resources/plugins/jquery-easyui-1.3.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="resources/plugins/fine-uploader-3.8.2/fineuploader.css">
    <link rel="stylesheet" type="text/css" href="resources/css/icons.css">
	<link rel="stylesheet" type="text/css" href="resources/css/style.css">
	
	<script type="text/javascript" src="resources/plugins/jquery/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="resources/plugins/jquery/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="resources/js/ext/cookie.js"></script>
    <script type="text/javascript" src="resources/plugins/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="resources/plugins/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="resources/plugins/jquery-file-download-1.4.2/jquery.fileDownload-1.4.2.js"></script>
    <script type="text/javascript" src="resources/plugins/blockui/jquery.blockUI.js"></script>
    <script type="text/javascript" src="resources/plugins/jquery-form/jquery.form.min.js"></script>
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
    				<span style="color: #369">当前用户:</span><span style="color: #333;font-weight:bold;">&nbsp;&nbsp;${Session.user.trueName}&nbsp;&nbsp;</span>
    				<a href="logout.htm" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-back">注销</a>
    				<span style="color: #369">主题</span>:
    				<select class="easyui-combobox" data-options="editable:false,panelHeight:'auto',onChange:context.onChangeTheme">
    					<option value="metro" <#if theme == 'metro'>selected</#if>>Win8</option>
    					<option value="metro-blue" <#if theme == 'metro-blue'>selected</#if>>蓝色</option>
    					<option value="metro-gray" <#if theme == 'metro-gray'>selected</#if>>深灰</option>
    					<option value="metro-green" <#if theme == 'metro-green'>selected</#if>>绿色</option>
    					<option value="metro-orange" <#if theme == 'metro-orange'>selected</#if>>橙色</option>
    					<option value="metro-red" <#if theme == 'metro-red'>selected</#if>>红色</option>
    					<option value="default" <#if theme == 'default'>selected</#if>>经典</option>
    					<option value="pepper-grinder" <#if theme == 'pepper-grinder'>selected</#if>>磨砂</option>
    					<option value="black" <#if theme == 'black'>selected</#if>>黑色</option>
    					<option value="bootstrap" <#if theme == 'bootstrap'>selected</#if>>银灰</option>
    					<option value="gray" <#if theme == 'gray'>selected</#if>>灰色</option>
    					<option value="dark-hive" <#if theme == 'dark-hive'>selected</#if>>深黑</option>
    					<option value="sunny" <#if theme == 'sunny'>selected</#if>>阳光</option>
    					<option value="cupertino" <#if theme == 'cupertino'>selected</#if>>库比</option>
    				</select>
    			</dd>
    		</dl>
    	</div>
    </div>
    
    <div data-options="region:'west',split:true" href="home/menu/list.htm" title="功能导航" style="width:150px;padding:1px;">
        
    </div>
    
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="homeTabBar" class="easyui-tabs" data-options="border:false,fit:true" style="width:700px;height:250px">
	        <div class="centerTabButton" data-options="closable:true,iconCls:'icon-reload'" title="About" style="padding:10px">
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
    
    <div data-options="region:'east',split:true,iconCls:'icon-reload',collapsed:true" title="辅助工具" style="width:195px;overflow:hidden;">
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
    
    <div data-options="region:'south',split:false,border:false" style="height:30px;overflow:hidden;">
		<div align="left" style="color:#333;padding:4px 0px">
			&nbsp;&nbsp;<input class="easyui-searchbox" data-options="prompt:'请输入单号'" style="width:300px;clear:both;"></input>
			<span style="float:right;padding-right:10px;">&copy;2013&nbsp; nick chow all rights reserved</span>
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