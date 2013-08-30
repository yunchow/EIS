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

<@dialog id="deployPackageDialog" title="发布流程"  ns="flowmanage.deploy" width="400px" height="200px">
		The dialog content.11111111111111111
</@dialog>
