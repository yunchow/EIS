<@require ns="sysmanage.menu"/>
<table id="tgMenuSetting" class="easyui-treegrid"
        data-options="
            iconCls: 'icon-edit',
            toolbar: '#tbMenuGrid',
            rownumbers: true,
            animate: false,
            collapsible: true,
            fit: true,
            border: false,
            fitColumns: true,
            url: 'sysmanage/menu/json.htm',
            idField: 'id',
            treeField: 'name',
            onDblClickRow: sysmanage.menu.editMenuItem,
            onContextMenu: sysmanage.menu.onContextMenu">
    <thead>
        <tr>
            <th data-options="field:'name',width:150,align:'left',
            	editor:{
            		type: 'validatebox',
            		options: {
            			required: true,
            			validType: 'length[0, 20]'
            		}
            	}">菜单名称</th>
            <th data-options="field:'icon',width:120,formatter:sysmanage.menu.formatIcon,align:'center',
            	editor:{
            		type: 'combobox',
            		options: {
            			required: true,
            			editable: false,
            			valueField: 'key',
            			textField: 'value',
            			panelHeight: 'auto',
            			data: [{
            				key: 'pencil',
            				value: '铅笔'
            			},{
            				key: 'pictures',
            				value: '图片'
            			},{
            				key: 'blank',
            				value: '空白'
            			},{
            				key: 'table',
            				value: '表格'
            			},{
            				key: 'line',
            				value: '曲线'
            			},{
            				key: 'message',
            				value: '消息'
            			},{
            				key: 'map',
            				value: '地图'
            			},{
            				key: 'text',
            				value: '文本'
            			},{
            				key: 'book_edit',
            				value: '书本'
            			},{
            				key: 'icon-help',
            				value: '帮助'
            			},{
            				key: 'icon-tip',
            				value: '电灯'
            			},{
            				key: 'bar',
            				value: '柱状图'
            			},{
            				key: 'folder',
            				value: '文件夹'
            			},{
            				key: 'group_add',
            				value: '用户组'
            			}]
            		}
            	}">显示图标</th>
            <th data-options="field:'status',width:120,formatter:sysmanage.menu.formatStatus,align:'center',
            	editor:{
            		type: 'combobox',
            		options: {
            			required: true,
            			editable: false,
            			valueField: 'key',
            			textField: 'value',
            			panelHeight: 'auto',
            			data: [{
            				key: 'Y',
            				value: '显示'
            			},{
            				key: 'N',
            				value: '隐藏'
            			}]
            		}
            	}">菜单状态</th>
            <th data-options="field:'seq',width:100,align:'center',
            	editor:{
            		type: 'numberbox',
            		options: {
            			required: true,
            			min: 1,
            			max: 100000,
            			increment: 10
            		}
            	}">菜单显示顺序</th>
            <th data-options="field:'url',width:180,editor:{
            		type: 'validatebox',
            		options: {
            			required: false,
            			validType: ['length[0, 50]']
            		}
            	}">菜单链接</th>
            <th data-options="field:'comment',width:300,
            	editor:{
            		type: 'validatebox',
            		options: {
            			validType: 'length[0, 50]'
            		}
            	}">菜单说明</th>
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
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="sysmanage.menu.collapseAll()">全部折叠</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="sysmanage.menu.expandAll()">全部展开</a>
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
