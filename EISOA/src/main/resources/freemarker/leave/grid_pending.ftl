<@require ns="oa.leave.pending"/>
<@datagrid ns="oa.leave.pending"
		   id="leavePendingGrid"
		   toolbar="#pendingLeaveToolbar" 
		   url="oa/leave/my/pending.htm">
	<th data-options="field:'leaveId',width:80">请假单编号</th>
    <th data-options="field:'processDefinitionId',width:80">流程定义编号</th>
    <th data-options="field:'processInstanceId',width:80">流程实例编号</th>
</@datagrid>

<@toolbar id="pendingLeaveToolbar" ns="oa.leave.pending" menu="#pendingLeaveSearchBoxMenu"/>

<div id="pendingLeaveSearchBoxMenu" style="width:auto">
    <div data-options="name:'status'">角色名称</div>
</div>

