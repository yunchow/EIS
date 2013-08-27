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
	
    formatDate: function(value, row, index) {
    	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
    }
});