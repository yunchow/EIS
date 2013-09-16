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
		var url = "oa/leave/detail/pending/"+ rowData.leaveId +".htm?taskId=" + rowData.taskId;
    	context.openTab("待办请假单("+ rowData.taskId +")", url);
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
    	var url = "oa/leave/detail/pending/"+ row.leaveId +".htm?taskId=" + row.taskId;
    	context.openTab("待办请假单("+ row.taskId +")", url);
    },
    
    /**
     * 挂起
     */
    suspend: function() {
    	
    },
    
    /**
     * 激活
     */
    active: function() {
    	
    },
	
	formatMenu: function(value, row, index) {
		return value;
	}
});