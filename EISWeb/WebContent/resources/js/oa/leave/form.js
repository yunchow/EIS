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
		var isValid = $("#leaveForm").form('validate');
		if (!isValid){
			context.error("请正确填写请假申请单！");
			return;
		}
		context.blockUI("正在提交申请单");
		/*$('#leaveForm').form('submit', {
			success: function(data){
				context.unblockUI();
				try {
					var r = eval("("+ data +")");
					if (r.result) {
						context.info("请假申请单提交成功，请等待" + r.nextTaskName);
					}
					else {
						context.error("请假申请单提交失败，请重试！");
					}
				}
				catch (error) {
					context.error("请假申请单提交失败，请重试！");
				}
			}
		});*/
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