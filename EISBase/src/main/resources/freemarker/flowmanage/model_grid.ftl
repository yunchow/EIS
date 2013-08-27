<@require ns="flowmanage.model"/>
<@datagrid ns="flowmanage.model"
		   id="modelGrid"
		   toolbar="#modelToolbar" 
		   url="flowmanage/model/list.htm">
    <th data-options="field:'key',width:40,
    	editor: {
    		type: 'validatebox',
    		options: {
    			required: true,
    			validType: 'length[0, 20]'
    		}
    	}
    	">标识</th>
    <th data-options="field:'name',width:40,
    	editor: {
    		type: 'validatebox',
    		options: {
    			required: true,
    			validType: 'length[0, 20]'
    		}
    	}
    	">模型名称</th>
    <th data-options="field:'version',width:40">版本号</th>
    <th data-options="field:'createTime',width:40,formatter:flowmanage.model.formatDate">创建时间</th>
    <th data-options="field:'lastUpdateTime',width:40,formatter:flowmanage.model.formatDate">最后修改时间</th>
    <th data-options="field:'metaInfo',width:80,editor:'text'">元数据</th>
</@datagrid>

<@toolbar id="modelToolbar" ns="flowmanage.model" menu="#modelSearchBoxMenu">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="flowmanage.model.editFlow()">编辑流程</a>
</@toolbar>

<div id="modelSearchBoxMenu" style="width:auto">
    <div data-options="name:'name'">模型名称</div>
</div>

