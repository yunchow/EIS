<@require ns="flowmanage.deploy"/>
<@datagrid ns="flowmanage.deploy"
		   id="deployPackageGrid"
		   toolbar="#dplToolbar" 
		   url="flowmanage/deploy/list.htm">
    <th data-options="field:'id',width:20">部署ID</th>
    <th data-options="field:'name',width:20,
    	editor: {
    		type: 'validatebox',
    		options: {
    			required: true,
    			validType: 'length[0, 20]'
    		}
    	}
    	">部署包名称</th>
    <th data-options="field:'category',width:20">类别</th>
    <th data-options="field:'deploymentTime',width:20,formatter:flowmanage.deploy.formatDate">部署时间</th>
</@datagrid>

<@toolbar id="dplToolbar" ns="flowmanage.deploy" add=false delete=false update=false cancel=false save=false menu="#deploySearchBoxMenu">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="flowmanage.deploy.deployFlow()">部署流程</a>
</@toolbar>

<div id="deploySearchBoxMenu" style="width:auto">
    <div data-options="name:'name'">部署名称</div>
</div>

<@dialog id="deployPackageDialog" title="部署流程文件" confirm=false  ns="flowmanage.deploy" confirmText="确定" width="600px">
	<div id="file-uploader">		
		<noscript>			
			<p>使打开浏览器 JavaScript功能使用上传</p>
		</noscript>         
	</div>
	<div class="alert">
		温馨提醒：选择文件后系统会自动上传并部署（支持同时选择多个文件）
	</div>
	<div class="alert">
		支持文件格式:&nbsp;&nbsp;xml,&nbsp;&nbsp;zip,&nbsp;&nbsp;rar&nbsp;&nbsp;(文件最大不超过5MB)
		<br>
		支持浏览器:&nbsp;&nbsp;
		Internet Explorer >=7,&nbsp;&nbsp;
		Firefox,&nbsp;&nbsp;
		Chrome,&nbsp;&nbsp;
		Android >=2.3.x,&nbsp;&nbsp;
		iOS >=6.0,&nbsp;&nbsp;
		Safari >=5 (OS X)
	</div>
</@dialog>
