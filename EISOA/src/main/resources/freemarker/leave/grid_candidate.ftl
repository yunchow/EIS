<@require ns="oa.leave.candidate"/>
<@datagrid ns="oa.leave.candidate"
		   id="leaveCandidateGrid"
		   toolbar="#candidateLeaveToolbar" 
		   url="oa/leave/candidate/task.htm">
	<!--<th data-options="field:'leaveId',width:80">请假单编号</th>-->
	<th data-options="field:'taskId',width:40">任务标识</th>
	<th data-options="field:'name',width:80">任务名称</th>
	<th data-options="field:'applicant',width:60">申请人</th>
	<th data-options="field:'leaveDays',width:40">请假天数</th>
	<th data-options="field:'description',width:80">任务描述</th>
	<th data-options="field:'taskCreateTime',width:80">任务创建时间</th>
	<th data-options="field:'dueDate',width:80">任务过期时间</th>
	<th data-options="field:'priority',width:40">任务优先级</th>
	<th data-options="field:'suspend',width:40">是否挂起</th>
    <th data-options="field:'processDefinitionId',width:80">流程定义编号</th>
    <th data-options="field:'processInstanceId',width:80">流程实例编号</th>
</@datagrid>

<div id="candidateLeaveToolbar" style="height:auto;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'text',plain:true" onclick="oa.leave.candidate.viewDetail()">查看明细</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="oa.leave.candidate.reload()">刷新</a>
	<input class="easyui-searchbox" data-options="prompt:'请输入关键字',searcher:oa.leave.candidate.doSearch,menu:'#candidateLeaveSearchBoxMenu'" style="width:300px;float:right;"></input>
</div>

<div id="candidateLeaveSearchBoxMenu" style="width:auto">
    <div data-options="name:'description'">任务描述</div>
</div>

