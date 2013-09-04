<@require ns="oa.leave.form"/>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',fit:false,border:false" style="height:35px;">
    	<div style="padding:0px;padding-bottom:5px;width:100%;border-bottom:1px dashed #369;">
		    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
		    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
		</div>
    </div>
    <div data-options="region:'center',border:false" style="padding:0px;margin:0px;">
    	<form id="leaveForm" action="?" method="post">
			<fieldset>
			    <legend><span class="icon pictures">&nbsp;</span>基本信息</legend>
					<table cellspacing="0" cellpadding="3" class="eistable">
						<tr>
							<td class="title">姓名：</td>
							<td><input class="easyui-validatebox" type="text" name="name" value="value" data-options="required:true,tipPosition:'top'" /></td>
							<td class="title">工号：</td>
							<td><input class="easyui-validatebox" type="text" name="no" value="value" data-options="required:true" /></td>
							<td class="title">公司：</td>
							<td><input class="easyui-validatebox" type="text" name="name" value="value" data-options="required:true" /></td>
						</tr>
						<tr>
							<td class="title">部门：</td>
							<td><input class="easyui-validatebox" type="text" name="no" value="value" data-options="required:true" /></td>
							<td class="title">联系电话：</td>
							<td><input class="easyui-validatebox" type="text" name="name" value="value" data-options="required:true" /></td>
							<td class="title">邮件地址：</td>
							<td><input class="easyui-validatebox" type="text" name="no" value="value" data-options="required:true" /></td>
						</tr>
						<tr>
							<td class="title">申请时间：</td>
							<td><input class="easyui-validatebox" type="text" name="name" value="value" data-options="required:true" /></td>
							<td class="title">申请编号：</td>
							<td><input class="easyui-validatebox" type="text" name="no" value="value" data-options="required:true" /></td>
						</tr>
					</table>
			</fieldset>
			<fieldset>
			    <legend><span class="icon icon-edit">&nbsp;</span>请假理由</legend>
					<table width="95%" border="0" cellspacing="0" cellpadding="0" class="eistable">
						<tr>
							<td class="title">类型：</td>
							<td>
								<select id="cc" class="easyui-combobox" name="dept" style="width:200px;">
								    <option>病假</option>
								    <option>事假</option>
								    <option>年假</option>
								    <option>调休</option>
								    <option>产假</option>
								    <option>其他</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">理由：</td>
							<td><textarea rows="5">val</textarea></td>
						</tr>
					</table>
			</fieldset>
			
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
					           <th data-options="field:'name',width:40">开始时间</th>
							   <th data-options="field:'gender',width:40">结束时间</th>
							   <th data-options="field:'comment',width:80,editor:'text'">备注</th>
					        </tr>
					    </thead>
					    <tbody>
					    	<tr><td></td><td></td><td></td></tr>
					    </tbody>
					</table>
				</div>
			</div>
			
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
								<select id="cc" class="easyui-combobox" name="dept" style="width:200px;">
								    <option>通过</option>
								    <option>拒绝</option>
								    <option>挂起</option>
								    <option>其他</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">意见说明：</td>
							<td><textarea rows="5">平时表现不好，继续努力</textarea></td>
						</tr>
					</table>
			</fieldset>
		</form>
		<div style="margin:50px;"></div>
    </div>
</div>
