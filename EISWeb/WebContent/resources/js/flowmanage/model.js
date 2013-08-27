jQuery.ns("flowmanage.model");

jQuery.define(flowmanage.model, base, {
	dgId: "modelGrid",
	searchField: "name",
	deleteUrl: "flowmanage/model/delete/",
    saveUrl: "flowmanage/model/save.htm",
    updateUrl: "flowmanage/model/update.htm",
    
    init: function() {
    	context.log("flowmanage.model.init called");
    },
    
	onReady: function() {
		context.log("flowmanage.model is ready");
	},
	
	getRowValues: function() {
    	return {version: 1};
    },
	
    formatDate: function(value, row, index) {
    	if (value == null) {
    		value = new Date();
    	}
    	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
    },
    
    editFlow: function() {
    	if ($.browser.msie) {
    		context.warn("不支持IE浏览器");
    		return;
    	}
    	var row = this.$dg.datagrid('getSelected');
        if (row){
        	window.open("modeler/service/editor?id=" + row.id);
        	//context.openTab("流程编辑" + row.id, "modeler/service/editor?id=" + row.id, "icon-edit");
        } else {
        	context.alert("请选择你要编辑的记录");
        }
    }
});