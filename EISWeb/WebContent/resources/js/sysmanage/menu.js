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
    
    onClickRow: function(row){
    	if (sysmanage.menu.newId){
        	$('#tgMenuSetting').treegrid('select', sysmanage.menu.newId);
        }
        else if (sysmanage.menu.editingId){
        	$('#tgMenuSetting').treegrid('select', sysmanage.menu.editingId);
        }
    },
    
    formatIcon: function(value, row, index) {
		if (!value) {
			return "";
		}
		return '<span class="icon '+ value +'">&nbsp;</span>';
	},
	
	formatStatus: function(value, row, index) {
		if (!value) {
			return "";
		}
		if (value == 'Y') {
			return '<span style="color:#333;">显示</span>';
		} else if (value == 'N') {
			return '<span style="color:blue;">隐藏</span>';
		} else {
			return '<span style="color:red;">错误</span>';
		}
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
        } else {
        	context.alert("请选择你要编辑的记录");
        }
    },
    reload: function() {
    	$("#tgMenuSetting").treegrid("reload");
    	sysmanage.menu.editingId = undefined;
    	sysmanage.menu.newId = undefined;
    },
    saveMenuItem: function(){
        if (sysmanage.menu.editingId != undefined){
            var t = $('#tgMenuSetting');
            if (!t.treegrid("validateRow", sysmanage.menu.editingId)) {
            	context.alert("数据格式不正确");
            	return;
            }
            t.treegrid('endEdit', sysmanage.menu.editingId);
            var node = t.treegrid("find", sysmanage.menu.editingId);
            sysmanage.menu.editingId = undefined;
            $.post("sysmanage/menu/update.htm", node);
        }
        else if (sysmanage.menu.newId != undefined){
            var t = $('#tgMenuSetting');
            if (!t.treegrid("validateRow", sysmanage.menu.newId)) {
            	context.alert("数据格式不正确");
            	return;
            }
            t.treegrid('endEdit', sysmanage.menu.newId);
            var node = t.treegrid("find", sysmanage.menu.newId);
            sysmanage.menu.newId = undefined;
            $.post("sysmanage/menu/add.htm", node, function(uuid) {
            	t.treegrid("reload");
            });
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
    
    /*appendMenu: function(){
    	if (sysmanage.menu.isEditing()){
            return;
        }
    	var g = $('#tgMenuSetting');
        var node = g.treegrid('getSelected');
        if (node) {
        	if (node.parent) {
        		context.alert("只支持两级菜单");
        		return;
        	}
        }
        var id = new Date().getTime();
        g.treegrid('append',{
            parent: node.id,
            data: [{
                id: id,
                name: '',
                url: '',
                icon: 'pencil',
                status: 'Y',
                seq: 500,
                comment: ''
            }]
        });
        g.treegrid('select', id);
        g.treegrid('beginEdit', id);
        sysmanage.menu.newId = id;
    },*/
    onRemoveMenu: function(){
    	if (sysmanage.menu.editingId != undefined){
            $.get("sysmanage/menu/delete/"+ sysmanage.menu.editingId +".htm", function() {
            	$('#tgMenuSetting').treegrid('remove', sysmanage.menu.editingId);
                sysmanage.menu.editingId = undefined;
            });
        }
        else if (sysmanage.menu.newId != undefined) {
        	$('#tgMenuSetting').treegrid('remove', sysmanage.menu.newId);
        	sysmanage.menu.newId = undefined;
        }
        else {
        	var node = $('#tgMenuSetting').treegrid('getSelected');
            if (node){
                $.get("sysmanage/menu/delete/"+ node.id +".htm", function() {
                	$('#tgMenuSetting').treegrid('remove', node.id);
                });
            } else {
            	context.alert("请选择你要删除的记录");
            }
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
        if (node) {
        	if (node.parent) {
        		context.alert("只支持两级菜单");
        		return;
        	}
        	pid = node.id;
        	$('#tgMenuSetting').treegrid('expand', node.id);
        }
        $('#tgMenuSetting').treegrid('append',{
        	parent: pid,
        	data: [{
                id: id,
                name: '',
                url: '',
                icon: 'pencil',
                status: 'Y',
                seq: 500,
                comment: ''
            }]
        });
        $('#tgMenuSetting').treegrid('select', id);
        $('#tgMenuSetting').treegrid('beginEdit', id);
        sysmanage.menu.newId = id;
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
    collapseAll: function(){
    	$('#tgMenuSetting').treegrid("collapseAll");
    },
    expandAll: function(){
    	$('#tgMenuSetting').treegrid("expandAll");
    }
});

