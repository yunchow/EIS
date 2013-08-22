<@require ns="sysmanage.datadic"/>
<@datagrid id="datadicGrid"
			ns="sysmanage.datadic" 
		   	url="sysmanage/datadic/list.htm">
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
</@datagrid>

<@toolbar ns="sysmanage.datadic"/>

<script type="text/javascript">
    sysmanage.datadic.ready();
</script>
