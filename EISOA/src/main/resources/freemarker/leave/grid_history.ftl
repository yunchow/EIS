<@require ns="oa.leave.history"/>
<@datagrid ns="oa.leave.history"
		   id="leavePendingGrid"
		   toolbar="#historyLeaveToolbar" 
		   url="oa/leave/my/history.htm">
	<th data-options="field:'leaveId',width:80">请假单编号</th>
    <th data-options="field:'processDefinitionId',width:80">流程定义编号</th>
    <th data-options="field:'processInstanceId',width:80">流程实例编号</th>
</@datagrid>

<@toolbar id="historyLeaveToolbar" ns="oa.leave.history" menu="#historyLeaveSearchBoxMenu"/>

<div id="historyLeaveSearchBoxMenu" style="width:auto">
    <div data-options="name:'status'">角色名称</div>
</div>

