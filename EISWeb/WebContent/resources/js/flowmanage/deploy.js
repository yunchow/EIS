jQuery.ns("flowmanage.deploy");

jQuery.define(flowmanage.deploy, base, {
	dgId: "deployPackageGrid",
	searchField: "name",
	deleteUrl: "flowmanage/deploy/delete/",
    saveUrl: "flowmanage/deploy/save.htm",
    updateUrl: "flowmanage/deploy/update.htm",
    
    init: function() {
    	context.log("flowmanage.deploy.init called");
    },
    
	onReady: function() {
		context.log("flowmanage.deploy is ready");
	},
	
	getRowValues: function() {
    	return {version: 1};
    },
    
    onDialogConfirmClick: function() {
		context.info("onDialogConfirmClick");
	},
    
    onDblClickRow: function(index, rowData){
    	
    },
    
    formatDate: function(value, row, index) {
    	if (value == null) {
    		value = new Date();
    	}
    	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
    },
    
    deployFlow: function() {
    	$('#deployPackageDialog').dialog('open');
    }
});