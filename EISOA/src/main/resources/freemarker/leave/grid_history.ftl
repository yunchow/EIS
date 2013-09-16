<@require ns="oa.leave.history"/>
<@datagrid ns="oa.leave.history"
		   id="leavePendingGrid"
		   toolbar="#historyLeaveToolbar" 
		   url="oa/leave/my/history.htm">
	<th data-options="field:'taskId',width:40">任务标识</th>
	<th data-options="field:'name',width:80">任务名称</th>
	<th data-options="field:'applicant',width:60">申请人</th>
	<th data-options="field:'leaveDays',width:40">请假天数</th>
	<th data-options="field:'owner',width:80">任务所有人</th>
	<th data-options="field:'assignee',width:80">任务处理人</th>
	<th data-options="field:'description',width:80">任务描述</th>
	<th data-options="field:'startTime',width:80">任务开始时间</th>
	<th data-options="field:'endTime',width:80">任务结束时间</th>
	<th data-options="field:'priority',width:40">任务优先级</th>
	<th data-options="field:'suspend',width:40">是否挂起</th>
</@datagrid>

<div id="historyLeaveToolbar" style="height:auto;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'text',plain:true" onclick="oa.leave.history.viewDetail()">查看明细</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="oa.leave.history.reload()">刷新</a>
	<input class="easyui-searchbox" data-options="prompt:'请输入关键字',searcher:oa.leave.history.doSearch,menu:'#historyLeaveSearchBoxMenu'" style="width:300px;float:right;"></input>
</div>
<div id="historyLeaveSearchBoxMenu" style="width:auto">
    <div data-options="name:'status'">角色名称</div>
</div>

