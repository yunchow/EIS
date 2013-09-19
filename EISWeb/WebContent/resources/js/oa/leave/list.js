jQuery.ns("oa.leave.list");

jQuery.define(oa.leave.list, base, {
	dgId: "leaveListGrid",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("oa.leave.list is ready");
	},

	/**
	 * 双击打开form表单
	 * @param index
	 * @param rowData
	 * @returns {Boolean}
	 */
	onDblClickRow: function(index, rowData){
		var url = "oa/leave/detail/list/"+ rowData.leaveId +".htm?taskId=" + rowData.taskId;
    	context.openTab("我的请假单("+ rowData.processInstanceId +")", url);
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
    	var url = "oa/leave/detail/list/"+ row.leaveId +".htm?taskId=" + row.taskId;
    	context.openTab("我的请假单("+ row.processInstanceId +")", url);
    },
    
	formatMenu: function(value, row, index) {
		return value;
	}
});