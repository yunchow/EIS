jQuery.ns("oa.leave.form");

jQuery.define(oa.leave.form, {
    taskId: undefined,
    
    init: function() {
    	context.log("oa.leave.form.init called");
    },
    
	ready: function() {
		/*var status = $("#status").val();
		if (status != 'new') {
			$("input").disable();
		}*/
		this.taskId = $("#taskId").val();
		context.log("oa.leave.form is ready");
	},
	
	doClose: function() {
		context.closeTab();
	},
	
	/**
	 * 签收任务
	 * @param taskId
	 */
	doClaim: function() {
		context.blockUI();
		var me = this;
		$.post("oa/leave/task/claim/"+ this.taskId +".htm", function(data) {
			context.unblockUI();
			var r = eval("("+ data +")");
			if (r.result) {
				me.onClaimSuccess(r);
			} else {
				me.onClaimFailed(r.message);
			}
		});
	},
	
	onClaimSuccess: function(r) {
		context.info("签收成功，请尽快处理此任务");
		context.reloadTab();
	},
	
	onClaimFailed: function(message) {
		context.error("签收失败：" + message);
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