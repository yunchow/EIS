<@require ns="sysmanage.group"/>
<@datagrid ns="sysmanage.group"
		   id="groupGrid"
		   toolbar="#groupToolbar" 
		   url="sysmanage/group/list.htm">
	<#--<th data-options="field:'id',width:120">ID</th>-->
    <th data-options="field:'name',width:40,
    	editor:{
    		type:'validatebox',
    		options:{
    			required:true,
                validType: 'length[1, 20]'
    		}
    	}
    ">工作组名称</th>
    <th data-options="field:'userIds',width:200,
    	editor:{
    		type:'combotree',
    		options:{
    			data: store.userListStore,
    			multiple: true,
    			required: true,
    			panelHeight:'auto',
    			lines: true
    		}
    	},
    	formatter: sysmanage.group.formatUsers
    ">工作组人员</th>
</@datagrid>

<@toolbar id="groupToolbar" ns="sysmanage.group" menu="#groupSearchBoxMenu"/>

<div id="groupSearchBoxMenu" style="width:auto">
    <div data-options="name:'name'">角色名称</div>
</div>

