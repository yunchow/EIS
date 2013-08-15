<@require ns="sysmanage.menu" />
<table id="tgMenuSetting" class="easyui-treegrid"
        data-options="
            iconCls: 'icon-edit',
            toolbar: '#tbMenuGrid',
            rownumbers: true,
            animate: false,
            collapsible: false,
            fit: true,
            border: false,
            fitColumns: true,
            url: 'sysmanage/menu/json.htm',
            idField: 'id',
            treeField: 'name',
            onContextMenu: sysmanage.menu.onContextMenu">
    <thead>
        <tr>
            <th data-options="field:'name',width:150,align:'left',
            	editor:{
            		type: 'validatebox',
            		options: {
            			required: true
            		}
            	}">菜单名称</th>
            <th data-options="field:'icon',width:120,formattter:sysmanage.menu.formatIcon,
            	editor:{
            		type: 'combobox',
            		options: {
            			required: true,
            			
            		}
            	}">显示图标</th>
            <th data-options="field:'status',width:120,formattter:sysmanage.menu.formatIcon,
            	editor:{
            		type: 'combobox',
            		options: {
            			required: true,
            			
            		}
            	}">菜单状态</th>
            <th data-options="field:'url',width:180,editor:{
            		type: 'validatebox',
            		options: {
            			required: true
            		}
            	}">菜单链接</th>
            <th data-options="field:'comment',width:300,editor:'text'">菜单说明</th>
        </tr>
    </thead>
</table>
<div id="tbMenuGrid" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="sysmanage.menu.append()">增加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="sysmanage.menu.editMenuItem()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="sysmanage.menu.cancel()">取消</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="sysmanage.menu.onRemoveMenu()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="sysmanage.menu.saveMenuItem()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="sysmanage.menu.reload()">刷新</a>
</div>
<div id="mmMenuGrid" class="easyui-menu" style="width:120px;">
    <div onclick="sysmanage.menu.appendMenu()" data-options="iconCls:'icon-add'">追加菜单</div>
    <div onclick="sysmanage.menu.onRemoveMenu()" data-options="iconCls:'icon-remove'">删除当前菜单</div>
    <div class="menu-sep"></div>
    <div onclick="sysmanage.menu.collapse()">折叠</div>
    <div onclick="sysmanage.menu.expand()">展开</div>
</div>
<script type="text/javascript">
	sysmanage.menu.ready();
</script>