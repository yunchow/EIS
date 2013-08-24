<@require ns="sysmanage.user"/>
<@datagrid ns="sysmanage.user"
		   id="userGrid"
		   toolbar="#userToolbar" 
		   url="sysmanage/user/list.htm">
	<#--<th data-options="field:'id',width:120">ID</th>-->
    <th data-options="field:'trueName',width:40,
    	editor:{
    		type:'validatebox',
    		options:{
    			required:true,
                validType: 'length[0, 20]'
    		}
    	}
    ">员工名</th>
    <th data-options="field:'name',width:40,
    	editor:{
    		type:'validatebox',
    		options:{
    			required:true,
                validType: 'length[0, 50]'
    		}
    	}
    ">登录名</th>
    <th data-options="field:'password',width:40,formatter:sysmanage.user.formatPassword,
    	editor:{
    		type:'validatebox',
    		options:{
    			required: true,
                validType: 'length[0, 50]'
    		}
    	}
    ">登录密码</th>
    <th data-options="field:'gender',width:40,formatter:sysmanage.user.formatGender,
    	editor:{
    		type:'combobox',
    		options:{
    			required:true,
                panelHeight: 'auto',
                editable: false,
                valueField: 'id',
                textField: 'text',
                value: 'M',
                data: [
                	{id: 'M', text: '男性'},
                	{id: 'W', text: '女性'}
                ]
    		}
    	}
    ">性别</th>
    <th data-options="field:'status',width:40,
    	editor:{
    		type:'combobox',
    		options:{
    			required:true,
                panelHeight: 'auto',
                editable: false,
                url: 'sysmanage/datadic/userStatus.htm',
                value: 'C'
    		}
    	}
    ">员工状态</th>
    <th data-options="field:'userroles',width:100,
    	editor:{
    		type:'combotree',
    		options:{
    			data: store.roleListStore,
    			multiple: true,
    			required: true,
    			panelHeight:'auto',
    			lines: true
    		}
    	},
    	formatter: sysmanage.user.formatRole
    ">员工角色</th>
    <th data-options="field:'comment',width:80,editor:'text'">备注</th>
</@datagrid>

<@toolbar id="userToolbar" ns="sysmanage.user" menu="#userSearchBoxMenu"/>

<div id="userSearchBoxMenu" style="width:auto">
    <div data-options="name:'name'">用户名称</div>
</div>

