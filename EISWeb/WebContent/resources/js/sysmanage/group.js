jQuery.ns("sysmanage.group");

jQuery.define(sysmanage.group, base, {
	dgId: "groupGrid",
	searchField: "name",
	deleteUrl: "sysmanage/group/delete/",
    saveUrl: "sysmanage/group/save.htm",
    updateUrl: "sysmanage/group/update.htm",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("sysmanage.group is ready");
	},
	
	/**
     * 新增行
     */
    append: function(){
        if (!this.isEditing()){
        	var row = {add_add___ : true};
            this.$dg.datagrid('appendRow', row);
            this.editIndex = this.$dg.datagrid('getRows').length-1;
            this.$dg.datagrid('selectRow', this.editIndex)
                    .datagrid('beginEdit', this.editIndex);
        }
    },
    
    destroyIdField: function(index) {
    	// get the datebox editor and change its value
    	var ed = this.$dg.datagrid('getEditor', {index:index,field:'id'});
    	//$(ed.target).validatebox('disableValidation');
    	//$(ed.target).validatebox('destroy');
    	context.log(ed.target[0]);
    	$(ed.target[0]).prop("disabled", true);
    },
    
    edit: function(){
    	if (this.isEditing()){
            return;
        }
        var row = this.$dg.datagrid('getSelected');
        if (row){
        	var index = this.$dg.datagrid('getRowIndex', row)
        	this.editIndex = index
            this.$dg.datagrid('beginEdit', index);
        	this.destroyIdField(index);
        } else {
        	context.alert("请选择你要编辑的记录");
        }
    },
    onDblClickRow: function(index, rowData){
    	if (this.isEditing()){
            return false;
        }
    	this.editIndex = index;
    	this.$dg.datagrid('beginEdit', index);
    	this.destroyIdField(index);
    },
    
	formatUsers: function(value, row, index) {
		return sysmanage.group.formatUsersFor(tools.joinArrayIf(value), row, index);
	},
	
	formatUsersFor: function(value, row, index) {
		if (!value) {
			return "";
		}
		var names = [];
		for (var i in store.userListStore) {
			var user = store.userListStore[i];
			if (value.indexOf(user.id) >= 0) {
				names.push(user.name);
			}
		}
		return names.join(",");
	}
});