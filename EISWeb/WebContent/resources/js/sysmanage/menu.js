/**
 * 系统管理 - 菜单管理
 */
jQuery.ns("sysmanage.menu");

jQuery.define(sysmanage.menu, {
	editIndex: undefined,
	
	ready: function() {
		$("#mmMenuGrid").bind('contextmenu',function(e){
            e.preventDefault();
        });
		context.log("sysmanage.menu.ready is called.");
	},
	
	onContextMenu: function(e,row){
        e.preventDefault();
        $('#tgMenuSetting').treegrid('select', row.id);
        $('#mmMenuGrid').menu('show',{
            left: e.pageX,
            top: e.pageY
        });
    },
    
    formatIcon: function(value) {
		if (!value) {
			return "";
		}
		return '<span class="icon '+ value +'">&nbsp;</span>';
	},
	
	editingId: undefined,
	
	editMenuItem: function(){
        if (sysmanage.menu.editingId != undefined){
            $('#tgMenuSetting').treegrid('select', sysmanage.menu.editingId);
            return;
        }
        var row = $('#tgMenuSetting').treegrid('getSelected');
        if (row){
        	sysmanage.menu.editingId = row.id
            $('#tgMenuSetting').treegrid('beginEdit', row.id);
        }
    },
    saveMenuItem: function(){
        if (sysmanage.menu.editingId != undefined){
            var t = $('#tgMenuSetting');
            t.treegrid('endEdit', sysmanage.menu.editingId);
            sysmanage.menu.editingId = undefined;
            var persons = 0;
            var rows = t.treegrid('getChildren');
            for(var i=0; i<rows.length; i++){
                var p = parseInt(rows[i].persons);
                if (!isNaN(p)){
                    persons += p;
                }
            }
            var frow = t.treegrid('getFooterRows')[0];
            frow.persons = persons;
            t.treegrid('reloadFooter');
        }
    },
    cancel: function(){
        if (sysmanage.menu.editingId != undefined){
            $('#tgMenuSetting').treegrid('cancelEdit', sysmanage.menu.editingId);
            sysmanage.menu.editingId = undefined;
        }
    },
    
    /*******************************************************
     * onContextMenu methods
     ******************************************************/
    
    idIndex: 150,
    
    appendMenu: function(){
        sysmanage.menu.idIndex++;
        var d1 = new Date();
        var d2 = new Date();
        d2.setMonth(d2.getMonth()+1);
        var node = $('#tgMenuSetting').treegrid('getSelected');
        $('#tgMenuSetting').treegrid('append',{
            parent: node.id,
            data: [{
//                id: sysmanage.menu.idIndex,
//                name: 'New Task' + sysmanage.menu.idIndex,
//                url: 'xxx',
//                icon: 'pencil',
//                comment: ''
            }]
        })
    },
    onRemoveMenu: function(){
        var node = $('#tgMenuSetting').treegrid('getSelected');
        if (node){
            $('#tgMenuSetting').treegrid('remove', node.id);
        }
    },
    collapse: function(){
        var node = $('#tgMenuSetting').treegrid('getSelected');
        if (node){
            $('#tgMenuSetting').treegrid('collapse', node.id);
        }
    },
    expand: function(){
        var node = $('#tgMenuSetting').treegrid('getSelected');
        if (node){
            $('#tgMenuSetting').treegrid('expand', node.id);
        }
    },
	
    /*******************************************************
     * Toolbar methods
     ******************************************************/
	endEditing: function(){
        if (sysmanage.menu.editIndex == undefined) {
        	return true;
        }
        if ($('#tgMenuSetting').datagrid('validateRow', sysmanage.menu.editIndex)){
            var ed = $('#tgMenuSetting').datagrid('getEditor', {index:sysmanage.menu.editIndex,field:'productid'});
            var productname = $(ed.target).combobox('getText');
            $('#tgMenuSetting').datagrid('getRows')[sysmanage.menu.editIndex]['productname'] = productname;
            $('#tgMenuSetting').datagrid('endEdit', sysmanage.menu.editIndex);
            sysmanage.menu.editIndex = undefined;
            return true;
        } else {
            return false;
        }
    },
    onClickRow: function(index, rowData){
        if (sysmanage.menu.editIndex != index){
            if (sysmanage.menu.endEditing()){
                $('#tgMenuSetting').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                sysmanage.menu.editIndex = index;
            } else {
                $('#tgMenuSetting').datagrid('selectRow', sysmanage.menu.editIndex);
            }
        }
    },
    append: function(){
        if (sysmanage.menu.endEditing()){
            $('#tgMenuSetting').datagrid('appendRow',{status:'P'});
            sysmanage.menu.editIndex = $('#tgMenuSetting').datagrid('getRows').length-1;
            $('#tgMenuSetting').datagrid('selectRow', sysmanage.menu.editIndex)
                    .datagrid('beginEdit', sysmanage.menu.editIndex);
        }
    },
    removeit: function(){
        if (sysmanage.menu.editIndex == undefined){return}
        $('#tgMenuSetting').datagrid('cancelEdit', sysmanage.menu.editIndex)
                .datagrid('deleteRow', sysmanage.menu.editIndex);
        sysmanage.menu.editIndex = undefined;
    },
    accept: function(){
        if (sysmanage.menu.endEditing()){
            $('#tgMenuSetting').datagrid('acceptChanges');
        }
    },
    reject: function(){
        $('#tgMenuSetting').datagrid('rejectChanges');
        sysmanage.menu.editIndex = undefined;
    },
    getChanges: function(){
        var rows = $('#tgMenuSetting').datagrid('getChanges');
        alert(rows.length+' rows are changed!');
    }
});

