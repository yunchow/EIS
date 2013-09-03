<div style="padding:0px;padding-bottom:5px;margin-bottom:10px;width:98%;border-bottom:1px dashed #369;">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">Add</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">Remove</a>
</div>
<!--
<div style="width:90%;font-size:18px;font-weight:bold;color:#333;text-align:center;">张三的请假申请单</div>
-->
<style>
	td {
		 text-align:left;
		 //border: 1px solid #EEE;
	}
	tr {
		border: 1px solid #EEE;
	}
	input {
		width: 95%;
	}
</style>
<!--
<div id="p" class="easyui-panel" title="基本信息" 
    data-options="collapsible:true" style="padding:0px;">
	<table width="96%" cellspacing="0" cellpadding="3"  style="margin:auto;">
		<tr>
			<td width="7%">姓名：</td>
			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
			<td width="7%">工号：</td>
			<td><input class="easyui-validatebox" type="text" name="no" data-options="required:true" /></td>
			<td width="7%">公司：</td>
			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
		</tr>
		<tr>
			<td width="7%">部门：</td>
			<td><input class="easyui-validatebox" type="text" name="no" data-options="required:true" /></td>
			<td width="7%">联系电话：</td>
			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
			<td width="7%">邮件地址：</td>
			<td><input class="easyui-validatebox" type="text" name="no" data-options="required:true" /></td>
		</tr>
		<tr>
			<td width="7%">申请时间：</td>
			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
			<td width="7%">申请编号：</td>
			<td><input class="easyui-validatebox" type="text" name="no" data-options="required:true" /></td>
		</tr>
	</table>
</div>
<div style="margin:5px;"></div>
-->
<fieldset>
    <legend>基本信息</legend>
	<div class="easyui-panel" data-options="icon:icon-edit,fit:true" style="padding:0px;text-align:center;">
		<table width="96%" cellspacing="0" cellpadding="3"  style="margin:auto;">
			<tr>
				<td width="7%">姓名：</td>
				<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
				<td width="7%">工号：</td>
				<td><input class="easyui-validatebox" type="text" name="no" data-options="required:true" /></td>
				<td width="7%">公司：</td>
				<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
			</tr>
			<tr>
				<td width="7%">部门：</td>
				<td><input class="easyui-validatebox" type="text" name="no" data-options="required:true" /></td>
				<td width="7%">联系电话：</td>
				<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
				<td width="7%">邮件地址：</td>
				<td><input class="easyui-validatebox" type="text" name="no" data-options="required:true" /></td>
			</tr>
			<tr>
				<td width="7%">申请时间：</td>
				<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
				<td width="7%">申请编号：</td>
				<td><input class="easyui-validatebox" type="text" name="no" data-options="required:true" /></td>
			</tr>
		</table>
	</div>
</fieldset>

<div class="easyui-panel" data-options="icon:icon-edit,fit:true,border:true" style="padding:5px;text-align:center;display:none;">
	<table width="95%" border="0" cellspacing="0" cellpadding="0" style="margin:auto;">
		<tr>
			<td>类型：</td>
			<td><input class="easyui-combobox" type="text" name="name" data-options="required:true" /></td>
		</tr>
		<tr>
			<td>理由：</td>
			<td><textarea></textarea></td>
		</tr>
	</table>
</div>
