jQuery.ns("oa.leave.form");

jQuery.define(oa.leave.form, base, {
	dgId: "leaveTimeDetailGrid",
    
    init: function() {
    	context.log("oa.leave.form.init called");
    },
    
	onReady: function() {
		context.log("oa.leave.form is ready");
	},
	
	doApplyLeaveFormSubmit: function() {
		context.blockUI("正在提交申请单");
		$("#leaveForm").ajaxSubmit(function(data) {
			context.unblockUI();
			var r = eval("("+ data +")");
			if (r.result) {
				context.info("请假申请单提交成功，请等待" + r.nextTaskName);
			}
			else {
				context.error("请假申请单提交失败，请重试！");
			}
		});
	}
});