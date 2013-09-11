jQuery.ns("oa.leave.list");

jQuery.define(oa.leave.list, base, {
	dgId: "leavePendingGrid",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("oa.leave.list is ready");
	},
	
	formatMenu: function(value, row, index) {
		return value;
	}
});