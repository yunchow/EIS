<@require ns="flowmanage.dodeploy"/>
<table id="dodeployPackageGrid" class="easyui-datagrid"
        data-options="
            singleSelect: true,
            toolbar: '#dodplToolbar',
            fit: true,
            fitColumns: true,
            border: false,
            method: 'post',
            rownumbers: true,
            singleSelect: false
        ">
    <thead>
        <tr>
            <th data-options="checkbox:true"></th>
            <th data-options="field:'fileName',width:20">文件名</th>
            <th data-options="field:'fileType',width:20">文件类型</th>
            <th data-options="field:'fileSize',width:20">文件大小</th>
            <th data-options="field:'lastUpdateTime',width:20">最后修改时间</th>
        </tr>
    </thead>
</table>

<div id="dodplToolbar" style="height:auto;">
    <div id="progress" style="padding:5px;">
    	<span class="fileinput-button">
	        <i class="glyphicon glyphicon-plus"></i>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加文件...</a>
	        <button id="fileupload"></button>
	    </span>
	    <a href="javascript:void(0)" onclick="flowmanage.dodeploy.addFiles()" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">增加文件...</a>
	    <a href="javascript:void(0)" onclick="flowmanage.dodeploy.deleteItems()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">删除选中</a>
	    <a href="javascript:void(0)" onclick="flowmanage.dodeploy.doDeploy()" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">确定部署</a>
    </div>
</div>
<div id="swfupload-control"></div>
		