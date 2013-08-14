<table id="tg" class="easyui-treegrid"
        data-options="
            iconCls: 'icon-ok',
            rownumbers: true,
            animate: false,
            collapsible: false,
            fit: true,
            border: false,
            fitColumns: true,
            url: 'menu/list.htm',
            idField: 'id',
            treeField: 'name',
            onContextMenu: onContextMenu">
    <thead>
        <tr>
            <th data-options="field:'name',width:50,align:'left'">菜单名称</th>
            <th data-options="field:'icon',width:20,formatter:formatIcon,align:'center'">菜单图标</th>
            <th data-options="field:'url',width:80">菜单URL</th>
            <th data-options="field:'comment',width:300,formatter:formatProgress">菜单说明</th>
        </tr>
    </thead>
</table>
<div id="mm" class="easyui-menu" style="width:120px;">
    <div onclick="append()" data-options="iconCls:'icon-add'">Append</div>
    <div onclick="removeIt()" data-options="iconCls:'icon-remove'">Remove</div>
    <div class="menu-sep"></div>
    <div onclick="collapse()">Collapse</div>
    <div onclick="expand()">Expand</div>
</div>
<script type="text/javascript">
	function formatIcon(value) {
		if (!value) {
			return "";
		}
		return '<span class="icon '+ value +'">&nbsp;</span>';
	}
    function formatProgress(value){
        if (value){
            var s = '<div style="width:100%;border:1px solid #ccc">' +
                    '<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
                    '</div>';
            return s;
        } else {
            return 'N/A' ;
        }
    }
    function onContextMenu(e,row){
        e.preventDefault();
        $(this).treegrid('select', row.id);
        $('#mm').menu('show',{
            left: e.pageX,
            top: e.pageY
        });
    }
    var idIndex = 100;
    function append(){
        idIndex++;
        var d1 = new Date();
        var d2 = new Date();
        d2.setMonth(d2.getMonth()+1);
        var node = $('#tg').treegrid('getSelected');
        $('#tg').treegrid('append',{
            parent: node.id,
            data: [{
                id: idIndex,
                name: 'New Task'+idIndex,
                persons: parseInt(Math.random()*10),
                begin: $.fn.datebox.defaults.formatter(d1),
                end: $.fn.datebox.defaults.formatter(d2),
                progress: parseInt(Math.random()*100)
            }]
        })
    }
    function removeIt(){
        var node = $('#tg').treegrid('getSelected');
        if (node){
            $('#tg').treegrid('remove', node.id);
        }
    }
    function collapse(){
        var node = $('#tg').treegrid('getSelected');
        if (node){
            $('#tg').treegrid('collapse', node.id);
        }
    }
    function expand(){
        var node = $('#tg').treegrid('getSelected');
        if (node){
            $('#tg').treegrid('expand', node.id);
        }
    }
</script>