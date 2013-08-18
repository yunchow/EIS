jQuery.ns("sysmanage.datadic");

jQuery.define(sysmanage.datadic, {
	
	ready: function() {
		
	},
	editIndex: undefined,
	isEditing: function(){
        if (sysmanage.datadic.editIndex != undefined){
        	 $('#dg').datagrid('selectRow', sysmanage.datadic.editIndex)
        	return true
        }
        return false;
    },
    doSearch: function(value){
    	$("#dg").datagrid("load", {type: '%'+ value +'%'});
	},
    reload: function() {
    	$("#dg").datagrid("reload");
    	sysmanage.datadic.editIndex = undefined;
    },
    onClickRow: function(index){
    	if (sysmanage.datadic.isEditing()){
        	$('#dg').datagrid('selectRow', sysmanage.datadic.editIndex);
        }
    },
    edit: function(){
    	if (sysmanage.datadic.isEditing()){
            return;
        }
        var row = $('#dg').datagrid('getSelected');
        if (row){
        	var index = $('#dg').datagrid('getRowIndex', row)
        	sysmanage.datadic.editIndex = index
            $('#dg').datagrid('beginEdit', index);
        } else {
        	context.alert("请选择你要编辑的记录");
        }
    },
    onEditRow: function(index, rowData){
    	if (sysmanage.datadic.isEditing()){
            return false;
        }
    	sysmanage.datadic.editIndex = index;
    	$('#dg').datagrid('beginEdit', index);
    },
    append: function(){
        if (!sysmanage.datadic.isEditing()){
        	var id = context.uuid();
            $('#dg').datagrid('appendRow', {id: id, add: true});
            sysmanage.datadic.editIndex = $('#dg').datagrid('getRows').length-1;
            $('#dg').datagrid('selectRow', sysmanage.datadic.editIndex)
                    .datagrid('beginEdit', sysmanage.datadic.editIndex);
        }
    },
    removeit: function(){
        var row = $('#dg').datagrid('getSelected');
        if (row) {
        	if (!row.add) {
        		$.post("sysmanage/datadic/delete/" + row.id + ".htm");
        	}
        	var index = $('#dg').datagrid("getRowIndex", row);
        	$('#dg').datagrid('deleteRow', index);
        	sysmanage.datadic.editIndex = undefined;
        }
        else {
        	context.alert("请选择要删除的记录行");
        }
    },
    accept: function(){
        if (sysmanage.datadic.isEditing()){
        	if (!$("#dg").datagrid("validateRow", sysmanage.datadic.editIndex)) {
        		context.alert("数据格式不正确");
        		return;
        	}
            sysmanage.datadic.editIndex = undefined;
            var row = $('#dg').datagrid('getSelected');
            $('#dg').datagrid('acceptChanges');
            if (row) {
            	if (row.add) {
            		$.post("sysmanage/datadic/save.htm", row, function() {
            			row.add = false;
            		});
            	}
            	else {
            		$.post("sysmanage/datadic/update.htm", row);
            	}
            }
        }
    },
    reject: function(){
        $('#dg').datagrid('rejectChanges');
        sysmanage.datadic.editIndex = undefined;
    },
    getChanges: function(){
        var rows = $('#dg').datagrid('getChanges');
        alert(rows.length+' rows are changed!');
    }
});