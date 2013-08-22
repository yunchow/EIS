<@require ns="sysmanage.role"/>
<@datagrid ns="sysmanage.role"
		   id="roleGrid"
		   toolbar="#roleToolbar" 
		   url="sysmanage/role/list.htm">
	<th data-options="field:'id',width:120">ID</th>
    <th data-options="field:'name',width:80,
    	editor:{
    		type:'validatebox',
    		options:{
    			required:true,
                validType: 'length[1, 20]'
    		}
    	}
    ">角色名称</th>
    <th data-options="field:'comment',width:250,editor:'text'">说明</th>
</@datagrid>

<@toolbar id="roleToolbar" ns="sysmanage.role"/>

<script type="text/javascript">
    sysmanage.role.ready();
</script>
