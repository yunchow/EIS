<@require ns="sysmanage.datadic"/>
<table id="dg" class="easyui-datagrid" style="width:700px;height:auto"
        data-options="
            singleSelect: true,
            toolbar: '#tb',
            fit: true,
            fitColumns: true,
            url: 'sysmanage/datadic/list.htm',
            method: 'post',
            rownumbers: true,
            pagination: true,
            onClickRow: sysmanage.datadic.onClickRow,
            onDblClickRow: sysmanage.datadic.onEditRow
        ">
    <thead>
        <tr>
            <th data-options="field:'id',width:120">ID</th>
            <th data-options="field:'type',width:80,
                    editor:{
                        type:'validatebox',
                        options:{
                            required:true,
                            validType: 'length[1, 20]'
                        }
                    }">字典类型</th>
            <th data-options="field:'value',width:80,
            	editor:{
            		type:'validatebox',
            		options:{
            			required:true,
                        validType: 'length[1, 20]'
            		}
            	}
            ">字典值</th>
            <th data-options="field:'text',width:80,
            	editor:{
            		type: 'validatebox',
            		options: {
            			required: true,
            			validType: 'length[0,20]'
            		}
            	}
            ">显示内容</th>
            <th data-options="field:'comment',width:250,editor:'text'">说明</th>
        </tr>
    </thead>
</table>
<style>
	.searchbox {
		float:right;
		margin: 2px 18px;
	}
</style>
<div id="tb" style="height:auto;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="sysmanage.datadic.append()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="sysmanage.datadic.edit()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="sysmanage.datadic.removeit()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="sysmanage.datadic.accept()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="sysmanage.datadic.reject()">取消</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="sysmanage.datadic.reload()">刷新</a>
    <input class="easyui-searchbox" data-options="prompt:'请输入数据字典类型',searcher:sysmanage.datadic.doSearch" style="width:150px;float:right;"></input>
</div>

<script type="text/javascript">
    
</script>
