jQuery.ns("oa.leave.pending");

jQuery.define(oa.leave.pending, base, {
	dgId: "leavePendingGrid",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("oa.leave.pending is ready");
	},
	
	formatMenu: function(value, row, index) {
		return value;
	}
});