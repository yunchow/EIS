jQuery.ns("base");

jQuery.define(base, {
	editIndex: undefined,
	$dg: undefined,
	dgId: undefined,
	searchField: undefined,
	deleteUrl: undefined,
    saveUrl: undefined,
    updateUrl: undefined,
    
    init: function() {
    	store.initMenuTreeStore();
    	store.initRoleStore();
    	context.log("base module init");
    },
    
	ready: function() {
		this.$dg = $('#' + this.dgId);
		this.editIndex = undefined;
		context.log("base module is ready");
		this.onReady();
	},
	/**
	 * hook method for children
	 */
	onReady: function() {
		
	},
	
	getTreeNodeName: function(treeNode, value, names) {
		if (!!!treeNode) {
			return;
		}
		if (value.indexOf(treeNode.id) >= 0) {
			names.push(treeNode.text);
		}
		if (treeNode.children && treeNode.children.length > 0) {
			for (var child in treeNode.children) {
				this.getTreeNodeName(treeNode.children[child], value, names);
			}
		}
	},
	
	isEditing: function(){
        if (this.editIndex != undefined){
        	 this.$dg.datagrid('selectRow', this.editIndex)
        	return true
        }
        return false;
    },
    doSearch: function(value, name){
    	var param = {};
    	param[name || this.searchField] = '%'+ value +'%';
    	this.$dg.datagrid("load", param);
    	this.editIndex = undefined;
	},
    reload: function() {
    	this.$dg.datagrid("reload");
    	this.editIndex = undefined;
    },
    onClickRow: function(index){
    	if (this.isEditing()){
        	this.$dg.datagrid('selectRow', this.editIndex);
        }
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
    },
    createId: function() {
    	return context.uuid();
    },
    /**
     * child can override this thisthod if need
     * @returns {___anonymous1498_1516}
     */
    getRowValues: function() {
    	return {};
    },
    /**
     * 新增行
     */
    append: function(){
        if (!this.isEditing()){
        	var row = this.getRowValues();
        	if (!row.add_add___) {
        		row.add_add___ = true;
        	}
        	if (!row.id) {
        		row.id = this.createId();
        	}
            this.$dg.datagrid('appendRow', row);
            this.editIndex = this.$dg.datagrid('getRows').length-1;
            this.$dg.datagrid('selectRow', this.editIndex)
                    .datagrid('beginEdit', this.editIndex);
        }
    },
    /**
     * 删除选定行
     */
    removeit: function(){
        var row = this.$dg.datagrid('getSelected');
        if (row) {
        	if (config.showConfirmDialogBeforeDelete) {
        		context.confirm("你确定要删除吗？", this.onRemove, this);
        	}
        	else {
        		this.onRemove();
        	}
        }
        else {
        	context.alert("请选择要删除的记录行");
        }
    },
    /**
     * 删除表格行,子类可重载此方法
     * @param row
     */
    onRemove: function(row) {
    	var row = this.$dg.datagrid('getSelected');
    	if (!row.add) {
    		$.post(this.deleteUrl + row.id + ".htm", function() {
    			if (config.showScuessfullMessageBox) {
    				context.info("删除成功！");
    			}
    		});
    	}
    	var index = this.$dg.datagrid("getRowIndex", row);
    	this.$dg.datagrid('deleteRow', index);
    	this.editIndex = undefined;
    },
    /**
     * 保存编辑的行
     */
    accept: function(){
        if (this.isEditing()){
        	if (!this.$dg.datagrid("validateRow", this.editIndex)) {
        		context.alert("数据格式不正确");
        		return;
        	}
            this.editIndex = undefined;
            var row = this.$dg.datagrid('getSelected');
            this.$dg.datagrid('acceptChanges');
            if (row) {
            	if (row.add_add___) {
            		$.post(this.saveUrl, row, function() {
            			row.add_add___ = false;
            			if (config.showScuessfullMessageBox) {
            				context.info("保存成功！");
            			}            			
            		});
            	}
            	else {
            		$.post(this.updateUrl, row, function() {
            			if (config.showScuessfullMessageBox) {
            				context.info("修改成功！");
            			} 
            		});
            	}
            }
        }
    },
    /**
     * 取消操作
     */
    reject: function(){
    	if (this.editIndex == undefined) {
    		return;
    	}
    	var me = this;
    	if (config.showConfirmDialogBeforeDelete) {
    		context.confirm("你确定取消吗？", this.onReject, me);
    	}
    	else {
    		this.onReject();
    	}
    },
    
    /**
     * 取消处理逻辑，子类可重载此方法
     */
    onReject: function() {
    	this.$dg.datagrid('rejectChanges');
        this.editIndex = undefined;
    }
});