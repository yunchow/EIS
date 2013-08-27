<@require ns="flowmanage.model"/>
<@datagrid ns="flowmanage.model"
		   id="modelGrid"
		   toolbar="#modelToolbar" 
		   url="flowmanage/model/list.htm">
    <th data-options="field:'id',width:40">模型ID</th>
    <th data-options="field:'name',width:40">模型名称</th>
    <th data-options="field:'version',width:40">版本号</th>
    <th data-options="field:'createTime',width:40,formatter:flowmanage.model.formatDate">创建时间</th>
    <th data-options="field:'lastUpdateTime',width:40,formatter:flowmanage.model.formatDate">最后修改时间</th>
    <th data-options="field:'metaInfo',width:80">元数据</th>
</@datagrid>

<@toolbar id="modelToolbar" ns="flowmanage.model" menu="#modelSearchBoxMenu"/>

<div id="modelSearchBoxMenu" style="width:auto">
    <div data-options="name:'name'">模型名称</div>
</div>

