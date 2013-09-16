<@require ns="oa.leave.history"/>
<@datagrid ns="oa.leave.history"
		   id="leavePendingGrid"
		   toolbar="#historyLeaveToolbar" 
		   url="oa/leave/my/history.htm">
	<th data-options="field:'leaveId',width:80">请假单编号</th>
    <th data-options="field:'processDefinitionId',width:80">流程定义编号</th>
    <th data-options="field:'processInstanceId',width:80">流程实例编号</th>
</@datagrid>

<div id="historyLeaveToolbar" style="height:auto;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'text',plain:true" onclick="oa.leave.history.viewDetail()">查看明细</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="oa.leave.history.reload()">刷新</a>
	<input class="easyui-searchbox" data-options="prompt:'请输入关键字',searcher:oa.leave.history.doSearch,menu:'#historyLeaveSearchBoxMenu'" style="width:300px;float:right;"></input>
</div>
<div id="historyLeaveSearchBoxMenu" style="width:auto">
    <div data-options="name:'status'">角色名称</div>
</div>

