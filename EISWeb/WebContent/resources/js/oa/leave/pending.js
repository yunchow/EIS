jQuery.ns("oa.leave.pending");

jQuery.define(oa.leave.pending, base, {
	dgId: "leavePendingGrid",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("oa.leave.pending is ready");
	},
	
	/**
	 * 双击打开form表单
	 * @param index
	 * @param rowData
	 * @returns {Boolean}
	 */
	onDblClickRow: function(index, rowData){
    	context.openTab("请假单", "oa/leave/detail/"+ rowData.leaveId +".htm");
    },
	
	formatMenu: function(value, row, index) {
		return value;
	}
});