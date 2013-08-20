<table id="dg" class="easyui-datagrid"
        data-options="
            singleSelect: true,
            toolbar: '#tb',
            fit: true,
            fitColumns: true,
            border: false,
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