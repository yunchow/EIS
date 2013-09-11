jQuery.ns("oa.leave.history");

jQuery.define(oa.leave.history, base, {
	dgId: "leavePendingGrid",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("oa.leave.history is ready");
	},
	
	formatMenu: function(value, row, index) {
		return value;
	}
});