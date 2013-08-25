<@require ns="sysmanage.organization"/>
<@treegrid ns="sysmanage.organization" id="tgOrganization" url="sysmanage/organization/tree.htm" toolbar="#tbOrganizationGrid">
    <th data-options="field:'name',width:150,align:'left',
    	editor:{
    		type: 'validatebox',
    		options: {
    			required: true,
    			validType: 'length[0, 20]'
    		}
    	}">组织名称</th>
    <th data-options="field:'organizer',width:100,align:'center',
    	editor:{
    		type: 'validatebox',
    		options: {
    			validType: 'length[0, 50]'
    		}
    	}">负责人</th>
    <th data-options="field:'comment',width:300,
    	editor:{
    		type: 'validatebox',
    		options: {
    			validType: 'length[0, 150]'
    		}
    	}">说明</th>
</@treegrid>
<@toolbar id="tbOrganizationGrid" ns="sysmanage.organization" searchbox=false>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="sysmanage.organization.collapseAll()">全部折叠</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="sysmanage.organization.expandAll()">全部展开</a>
</@toolbar>
<@menu id="mmOrganizationGrid" ns="sysmanage.organization"/>

