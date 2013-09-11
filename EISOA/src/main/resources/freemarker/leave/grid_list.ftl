<@require ns="oa.leave.list"/>
<@datagrid ns="oa.leave.list"
		   id="leavePendingGrid"
		   toolbar="#listLeaveToolbar" 
		   url="oa/leave/my/list.htm">
	<th data-options="field:'leaveId',width:120">请假单编号</th>
    <th data-options="field:'type',width:80">请假类型</th>
    <th data-options="field:'reason',width:80">请假理由</th>
    <th data-options="field:'startTime',width:80">请假开始时间</th>
    <th data-options="field:'endTime',width:80">请假结束时间</th>
    <th data-options="field:'createTime',width:80">申请时间</th>
</@datagrid>

<@toolbar id="listLeaveToolbar" ns="oa.leave.list" menu="#listLeaveSearchBoxMenu"/>

<div id="listLeaveSearchBoxMenu" style="width:auto">
    <div data-options="name:'status'">请假描述</div>
</div>

