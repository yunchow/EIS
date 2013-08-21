jQuery.ns("base");

jQuery.define(base, {
	editIndex: undefined,
	$dg: undefined,
	dgId: undefined,
	searchField: undefined,
	deleteUrl: undefined,
    saveUrl: undefined,
    updateUrl: undefined,
    
	ready: function() {
		me.$dg = $('#' + me.dgId);
		me.editIndex = undefined;
		context.log("base module is ready");
	},
	isEditing: function(){
        if (me.editIndex != undefined){
        	 me.$dg.datagrid('selectRow', me.editIndex)
        	return true
        }
        return false;
    },
    doSearch: function(value){
    	var param = {};
    	param[me.searchField] = '%'+ value +'%';
    	me.$dg.datagrid("load", param);
    	me.editIndex = undefined;
	},
    reload: function() {
    	me.$dg.datagrid("reload");
    	me.editIndex = undefined;
    },
    onClickRow: function(index){
    	if (me.isEditing()){
        	me.$dg.datagrid('selectRow', me.editIndex);
        }
    },
    edit: function(){
    	if (me.isEditing()){
            return;
        }
        var row = me.$dg.datagrid('getSelected');
        if (row){
        	var index = me.$dg.datagrid('getRowIndex', row)
        	me.editIndex = index
            me.$dg.datagrid('beginEdit', index);
        } else {
        	context.alert("请选择你要编辑的记录");
        }
    },
    onDblClickRow: function(index, rowData){
    	if (me.isEditing()){
            return false;
        }
    	me.editIndex = index;
    	me.$dg.datagrid('beginEdit', index);
    },
    createId: function() {
    	return context.uuid();
    },
    /**
     * child can override this method if need
     * @returns {___anonymous1498_1516}
     */
    getRowValues: function() {
    	return {};
    },
    /**
     * 新增行
     */
    append: function(){
        if (!me.isEditing()){
        	var row = me.getRowValues();
        	if (!row.add_add___) {
        		row.add_add___ = true;
        	}
        	if (!row.id) {
        		row.id = me.createId();
        	}
            me.$dg.datagrid('appendRow', row);
            me.editIndex = me.$dg.datagrid('getRows').length-1;
            me.$dg.datagrid('selectRow', me.editIndex)
                    .datagrid('beginEdit', me.editIndex);
        }
    },
    /**
     * 删除选定行
     */
    removeit: function(){
        var row = me.$dg.datagrid('getSelected');
        if (row) {
        	if (!row.add) {
        		$.post(me.deleteUrl + row.id + ".htm");
        	}
        	var index = me.$dg.datagrid("getRowIndex", row);
        	me.$dg.datagrid('deleteRow', index);
        	me.editIndex = undefined;
        }
        else {
        	context.alert("请选择要删除的记录行");
        }
    },
    /**
     * 保存编辑的行
     */
    accept: function(){
        if (me.isEditing()){
        	if (!me.$dg.datagrid("validateRow", me.editIndex)) {
        		context.alert("数据格式不正确");
        		return;
        	}
            me.editIndex = undefined;
            var row = me.$dg.datagrid('getSelected');
            me.$dg.datagrid('acceptChanges');
            if (row) {
            	if (row.add_add___) {
            		$.post(me.saveUrl, row, function() {
            			row.add_add___ = false;
            		});
            	}
            	else {
            		$.post(me.updateUrl, row);
            	}
            }
        }
    },
    /**
     * 取消操作
     */
    reject: function(){
        me.$dg.datagrid('rejectChanges');
        me.editIndex = undefined;
    }
});