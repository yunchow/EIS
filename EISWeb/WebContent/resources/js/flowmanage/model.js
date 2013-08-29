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
    
    /**
     * 新增成功后刷新grid，不然ID不正确
     * @param data
     */
    onSaveScuess: function(data) {
    	this.reload();
    },
	
    onDblClickRow: function(index, rowData){
    	this.editFlow();
    },
    
    formatDate: function(value, row, index) {
    	if (value == null) {
    		value = new Date();
    	}
    	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
    },
    
    formatDesc: function(value, row, index) {
    	if (!value && row.metaInfo) {
    		var metaInfo = $.parseJSON(row.metaInfo);
    		value = metaInfo.description;
    	}
    	return value;
    },
    
    deploy: function() {
    	var row = this.$dg.datagrid('getSelected');
        if (row){
        	$.post("flowmanage/model/deploy/"+ row.id +".htm", function() {
        		context.info("部署成功");
        	});
        } else {
        	context.alert("请选择你要部署的模型");
        }
    },
    
    exportFlow: function() {
    	var row = this.$dg.datagrid('getSelected');
        if (row){
        	$.fileDownload("flowmanage/model/export/"+ row.id +".htm", {
        		failCallback: function (html, url) {
        	        context.error("下载失败，请重试");
        	    }
            });
        } else {
        	context.alert("请选择你要导出的流程");
        }
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
        	context.alert("请选择你要编辑的模型");
        }
    }
});