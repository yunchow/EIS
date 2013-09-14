jQuery.ns("oa.leave.history");

jQuery.define(oa.leave.history, base, {
	dgId: "leavePendingGrid",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("oa.leave.history is ready");
	},

	/**
	 * 双击打开form表单
	 * @param index
	 * @param rowData
	 * @returns {Boolean}
	 */
	onDblClickRow: function(index, rowData){
    	context.openTab("请假单", "oa/leave/detail/history/"+ rowData.leaveId +".htm");
    },
	formatMenu: function(value, row, index) {
		return value;
	}
});