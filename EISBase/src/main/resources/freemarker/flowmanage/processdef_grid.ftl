<@require ns="flowmanage.processdef"/>
<@datagrid ns="flowmanage.processdef"
		   id="processdefGrid"
		   toolbar="#processdefToolbar" 
		   url="flowmanage/processdef/list.htm">
    <th data-options="field:'id',width:50">ID</th>
    <th data-options="field:'key',width:40">标识</th>
    <#--<th data-options="field:'name',width:40">名称</th>-->
    <th data-options="field:'resourceName',width:40,formatter:flowmanage.processdef.formatXML">流程文档</th>
    <th data-options="field:'diagramResourceName',width:50,formatter:flowmanage.processdef.formatDiagram">流程图</th>
    <th data-options="field:'category',width:60">类别</th>
    <th data-options="field:'version',width:20">版本号</th>
    <th data-options="field:'deploymentId',width:20">部署编号</th>
    <th data-options="field:'suspend',width:20,formatter:flowmanage.processdef.formatSuspend">状态</th>
    <th data-options="field:'description',width:40">描述</th>
</@datagrid>

<@toolbar id="processdefToolbar" ns="flowmanage.processdef" delete=false update=false cancel=false save=false add=false menu="#processdefSearchBoxMenu">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="flowmanage.processdef.viewFlowPicture()">查看流程图</a>
	<a id="suspend" style="display:none;" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="flowmanage.processdef.suspend()">挂起</a>
	<a id="activate" style="display:none;" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="flowmanage.processdef.activate()">激活</a>
</@toolbar>

<div id="processdefSearchBoxMenu" style="width:auto">
    <div data-options="name:'name'">流程定义名称</div>
</div>

