jQuery.ns("oa.leave.history");

jQuery.define(oa.leave.history, base, {
	dgId: "leaveHistoryGrid",
    
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
		var url = "oa/leave/detail/history/"+ rowData.leaveId +".htm?taskId=" + rowData.taskId;
    	context.openTab("已办请假单("+ rowData.taskId +")", url);
    },
    
    /**
     * 查看明细
     */
    viewDetail: function() {
    	var row = this.getSelectedRow();
    	if (!row) {
    		context.warn("请选择你要查看的记录");
    		return;
    	}
    	var url = "oa/leave/detail/history/"+ row.leaveId +".htm?taskId=" + row.taskId;
    	context.openTab("已办请假单("+ row.taskId +")", url);
    },
	formatMenu: function(value, row, index) {
		return value;
	}
});