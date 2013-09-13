jQuery.ns("oa.leave.candidate");

jQuery.define(oa.leave.candidate, base, {
	dgId: "leaveCandidateGrid",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("oa.leave.candidate is ready");
	},
	
	/**
	 * 双击打开form表单
	 * @param index
	 * @param rowData
	 * @returns {Boolean}
	 */
	onDblClickRow: function(index, rowData){
    	context.openTab("请假单("+ rowData.taskId +")", "oa/leave/detail/"+ rowData.leaveId +".htm");
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
    	context.openTab("请假单("+ row.taskId +")", "oa/leave/detail/"+ row.leaveId +".htm");
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