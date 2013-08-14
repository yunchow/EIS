/**
 * 系统管理 - 菜单管理
 */
jQuery.ns("sysmanage.menu");

jQuery.define(sysmanage.menu, {
	
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
	newId: undefined,
	
	editMenuItem: function(){
		if (sysmanage.menu.isEditing()){
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
        }
        else if (sysmanage.menu.newId != undefined){
            var t = $('#tgMenuSetting');
            t.treegrid('endEdit', sysmanage.menu.newId);
            sysmanage.menu.newId = undefined;
        }
    },
    cancel: function(){
        if (sysmanage.menu.editingId != undefined){
            $('#tgMenuSetting').treegrid('cancelEdit', sysmanage.menu.editingId);
            sysmanage.menu.editingId = undefined;
        }
        else if (sysmanage.menu.newId != undefined) {
        	 $('#tgMenuSetting').treegrid('remove', sysmanage.menu.newId);
        	 sysmanage.menu.newId = undefined;
        }
        else {
        	var row = $('#tgMenuSetting').treegrid('getSelected');
            if (row){
            	$('#tgMenuSetting').treegrid('unselect', row.id);
            }
        }
    },
    
    /*******************************************************
     * onContextMenu methods
     ******************************************************/
    
    isEditing: function() {
    	if (sysmanage.menu.editingId){
            $('#tgMenuSetting').treegrid('select', sysmanage.menu.editingId);
            return true;
        }
        if (sysmanage.menu.newId != undefined){
            $('#tgMenuSetting').treegrid('select', sysmanage.menu.newId);
            return true;
        }
    },
    
    appendMenu: function(){
    	if (sysmanage.menu.isEditing()){
            return;
        }
    	var id = new Date().getTime();
        var node = $('#tgMenuSetting').treegrid('getSelected');
        $('#tgMenuSetting').treegrid('append',{
            parent: node.id,
            data: [{
                id: id,
                name: '',
                url: '',
                icon: 'pencil',
                comment: ''
            }]
        });
        $('#tgMenuSetting').treegrid('beginEdit', id);
        sysmanage.menu.newId = id;
    },
    onRemoveMenu: function(){
    	if (sysmanage.menu.isEditing()){
            return;
        }
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
        if (sysmanage.menu.editingId == undefined) {
        	return true;
        }
        if ($('#tgMenuSetting').datagrid('validateRow', sysmanage.menu.editingId)){
            var ed = $('#tgMenuSetting').datagrid('getEditor', {index:sysmanage.menu.editingId,field:'productid'});
            var productname = $(ed.target).combobox('getText');
            $('#tgMenuSetting').datagrid('getRows')[sysmanage.menu.editingId]['productname'] = productname;
            $('#tgMenuSetting').datagrid('endEdit', sysmanage.menu.editingId);
            sysmanage.menu.editingId = undefined;
            return true;
        } else {
            return false;
        }
    },
    onClickRow: function(index, rowData){
        if (sysmanage.menu.editingId != index){
            if (sysmanage.menu.endEditing()){
                $('#tgMenuSetting').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                sysmanage.menu.editingId = index;
            } else {
                $('#tgMenuSetting').datagrid('selectRow', sysmanage.menu.editingId);
            }
        }
    },
    append: function(){
    	if (sysmanage.menu.editingId){
            $('#tgMenuSetting').treegrid('select', sysmanage.menu.editingId);
            return;
        }
        if (sysmanage.menu.newId != undefined){
            $('#tgMenuSetting').treegrid('select', sysmanage.menu.newId);
            return;
        }
        
        var id = new Date().getTime();
        var pid = undefined;
        var node = $('#tgMenuSetting').treegrid('getSelected');
        if (node){
            pid = node.id;
        }
        $('#tgMenuSetting').treegrid('append',{
        	parent: pid,
        	data: [{
                id: id,
                name: '',
                url: '',
                icon: 'pencil',
                comment: ''
            }]
        });
        $('#tgMenuSetting').treegrid('beginEdit', id);
        sysmanage.menu.newId = id;
    }
});

