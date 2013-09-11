<@require ns="oa.leave.form"/>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',fit:false,border:false">
    	<div class="datagrid-toolbar">
		    <a href="#" onclick="javascript:oa.leave.form.doApplyLeaveFormSubmit();" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true">提交</a>
		    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a>
		</div>
    </div>
    <div data-options="region:'center',border:false" style="padding:5px;margin:0px;">
    	<form id="leaveForm" action="oa/leave/do/apply.htm" method="post">
			<fieldset>
			    <legend><span class="icon pictures">&nbsp;</span>基本信息</legend>
					<table cellspacing="0" cellpadding="3" class="eistable">
						<tr>
							<td class="title">姓名：</td>
							<td><input class="easyui-validatebox" type="text" name="name" value="value" data-options="required:true,tipPosition:'top'" /></td>
							<td class="title">工号：</td>
							<td><input class="easyui-validatebox" type="text" name="empno" value="value" data-options="required:true" /></td>
							<td class="title">公司：</td>
							<td><input class="easyui-validatebox" type="text" name="company" value="value" data-options="required:true" /></td>
						</tr>
						<tr>
							<td class="title">部门：</td>
							<td><input class="easyui-validatebox" type="text" name="department" value="value" data-options="required:true" /></td>
							<td class="title">联系电话：</td>
							<td><input class="easyui-validatebox" type="text" name="phone" value="value" data-options="required:true" /></td>
							<td class="title">邮件地址：</td>
							<td><input class="easyui-validatebox" type="text" name="email" value="value" data-options="required:true" /></td>
						</tr>
						<tr>
							<td class="title">申请时间：</td>
							<td><input class="easyui-validatebox" type="text" name="createTime" value="value" data-options="required:true" /></td>
							<td class="title">申请编号：</td>
							<td><input class="easyui-validatebox" type="text" name="leaveNo" value="value" data-options="required:true" /></td>
						</tr>
					</table>
			</fieldset>
			<fieldset>
			    <legend><span class="icon icon-edit">&nbsp;</span>请假理由</legend>
					<table width="95%" border="0" cellspacing="0" cellpadding="0" class="eistable">
						<tr>
							<td class="title">类型：</td>
							<td>
								<select id="cc" class="easyui-combobox" name="type" style="width:200px;">
								    <option value="病假">病假</option>
								    <option value="事假">事假</option>
								    <option value="年假">年假</option>
								    <option value="产假">产假</option>
								    <option value="其他">其他</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">理由：</td>
							<td><textarea name="reason" rows="5">理由你妹啊</textarea></td>
						</tr>
					</table>
			</fieldset>
			
			<fieldset>
			    <legend><span class="icon icon-edit">&nbsp;</span>请假时间</legend>
					<table width="95%" border="0" cellspacing="0" cellpadding="0" class="eistable">
						<tr>
							<td class="title">开始时间：</td>
							<td>
								<input class="easyui-datebox" type="text" name="startTime" data-options="required:true" />
							</td>
							<td class="title">结束时间：</td>
							<td><input class="easyui-datebox" type="text" name="endTime" data-options="required:true" /></td>
						</tr>
					</table>
			</fieldset>
			
			<#-- 
			<div style="width:98%;padding:0px;margin:0px;margin-top:10px;height:auto;">
				<div class="easyui-panel" title="请假时间明细" data-options="collapsible:true">
					<@toolbar id="leaveDateTimeDetailToolbar" ns="oa.leave.form" reload=false searchbox=false save=false>
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="oa.leave.form.accept()">确定</a>
					</@toolbar>
					<table id="leaveTimeDetailGridzz" class="easyui-datagrid"
					        data-options="
					            singleSelect: true,
					            toolbar: '#leaveDateTimeDetailToolbar',
					            fitColumns: true,
					            border: false,
					            rownumbers: true">
					    <thead>
					        <tr>
					           <th data-options="field:'startTime',width:40">开始时间</th>
							   <th data-options="field:'endTime',width:40">结束时间</th>
							   <th data-options="field:'comment',width:80,editor:'text'">备注</th>
					        </tr>
					    </thead>
					    <tbody>
					    	<tr><td></td><td></td><td></td></tr>
					    </tbody>
					</table>
				</div>
			</div>
			-->
			
			<fieldset>
			    <legend><span class="icon icon-edit">&nbsp;</span>变动日志</legend>
			    <div style="padding:10px;">
					<div>部门经理于9-10号审批通过，审批意见：同意</div>
					<div>人事专员于9-12号审批通过，审批意见：已确认</div>
				</div>
			</fieldset>
			
			<fieldset>
			    <legend><span class="icon icon-edit">&nbsp;</span>审核</legend>
					<table width="95%" border="0" cellspacing="0" cellpadding="0" class="eistable">
						<tr>
							<td class="title">审核：</td>
							<td>
								<select id="cc" class="easyui-combobox" name="approveType" style="width:200px;">
								    <option>通过</option>
								    <option>拒绝</option>
								    <option>挂起</option>
								    <option>其他</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">意见说明：</td>
							<td><textarea name="approveContent" rows="5">平时表现不好，继续努力</textarea></td>
						</tr>
					</table>
			</fieldset>
		</form>
		<div style="margin:50px;"></div>
    </div>
</div>
