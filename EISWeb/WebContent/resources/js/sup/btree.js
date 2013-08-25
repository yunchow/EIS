/**
 * treegrid公共父类
 */
jQuery.ns("btree");

jQuery.define(btree, {
	tgName: undefined,
	tgMenueName: undefined,
	deleteUrl: undefined,
    saveUrl: undefined,
    updateUrl: undefined,
    
	$tg: undefined,
	$mm: undefined,
	
	editingId: undefined,
	newId: undefined,
	
	ready: function() {
		this.$tg = $("#" + this.tgName);
		this.$mm = $("#" + this.tgMenueName);
		this.onReady();
		context.log("btree is ready.");
	},
	
	/**
	 * 子类扩展此方法进行页面初始化
	 */
	onReady: function() {
		
	},
	
	onContextMenu: function(e,row){
        e.preventDefault();
        this.$tg.treegrid('select', row.id);
        this.$mm.menu('show',{
            left: e.pageX,
            top: e.pageY
        });
    },
    
    onClickRow: function(row){
    	if (this.newId){
        	this.$tg.treegrid('select', this.newId);
        }
        else if (this.editingId){
        	this.$tg.treegrid('select', this.editingId);
        }
    },
    
    onDblClickRow: function() {
    	this.edit();
    },
    
	edit: function(){
		if (this.isEditing()){
            return;
        }
        var row = this.$tg.treegrid('getSelected');
        if (row){
        	this.editingId = row.id
            this.$tg.treegrid('beginEdit', row.id);
        } else {
        	context.alert("请选择你要编辑的记录");
        }
    },
    reload: function() {
    	this.$tg.treegrid("reload");
    	this.editingId = undefined;
    	this.newId = undefined;
    },
    accept: function(){
        if (this.editingId != undefined){
            var t = this.$tg;
            if (!t.treegrid("validateRow", this.editingId)) {
            	context.alert("数据格式不正确");
            	return;
            }
            t.treegrid('endEdit', this.editingId);
            var node = t.treegrid("find", this.editingId);
            this.editingId = undefined;
            $.post(this.updateUrl, node);
        }
        else if (this.newId != undefined){
            var t = this.$tg;
            if (!t.treegrid("validateRow", this.newId)) {
            	context.alert("数据格式不正确");
            	return;
            }
            t.treegrid('endEdit', this.newId);
            var node = t.treegrid("find", this.newId);
            this.newId = undefined;
            $.post(this.saveUrl, node);
        }
    },
    reject: function(){
        if (this.editingId != undefined){
            this.$tg.treegrid('cancelEdit', this.editingId);
            this.editingId = undefined;
        }
        else if (this.newId != undefined) {
        	 this.$tg.treegrid('remove', this.newId);
        	 this.newId = undefined;
        }
        else {
        	var row = this.$tg.treegrid('getSelected');
            if (row){
            	this.$tg.treegrid('unselect', row.id);
            }
        }
    },
    
    isEditing: function() {
    	if (this.editingId){
            this.$tg.treegrid('select', this.editingId);
            return true;
        }
        if (this.newId != undefined){
            this.$tg.treegrid('select', this.newId);
            return true;
        }
    },
    
    removeit: function(){
    	var me = this;
    	if (this.editingId != undefined){
            $.get(this.deleteUrl + this.editingId +".htm", function() {
            	me.$tg.treegrid('remove', this.editingId);
            	me.editingId = undefined;
            });
        }
        else if (this.newId != undefined) {
        	this.$tg.treegrid('remove', this.newId);
        	this.newId = undefined;
        }
        else {
        	var node = this.$tg.treegrid('getSelected');
            if (node){
                $.get(this.deleteUrl + node.id +".htm", function() {
                	me.$tg.treegrid('remove', node.id);
                });
            } else {
            	context.alert("请选择你要删除的记录");
            }
        }
    },

    append: function(){
    	if (this.editingId){
            this.$tg.treegrid('select', this.editingId);
            return;
        }
        if (this.newId != undefined){
            this.$tg.treegrid('select', this.newId);
            return;
        }
        
        var id = context.uuid();
        var pid = undefined;
        var node = this.$tg.treegrid('getSelected');
        if (node) {
        	pid = node.id;
        	this.$tg.treegrid('expand', node.id);
        }
        this.$tg.treegrid('append',{
        	parent: pid,
        	data: [{
                id: id,
                pid: pid
            }]
        });
        this.$tg.treegrid('select', id);
        this.$tg.treegrid('beginEdit', id);
        this.newId = id;
    },
    collapse: function(){
        var node = this.$tg.treegrid('getSelected');
        if (node){
            this.$tg.treegrid('collapse', node.id);
        }
    },
    expand: function(){
        var node = this.$tg.treegrid('getSelected');
        if (node){
            this.$tg.treegrid('expand', node.id);
        }
    },
    collapseAll: function(){
    	this.$tg.treegrid("collapseAll");
    },
    expandAll: function(){
    	this.$tg.treegrid("expandAll");
    }
});

