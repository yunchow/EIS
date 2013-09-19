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
	
	doCompleteTask: function(taskId, approve) {
		context.blockUI();
		var p = {taskId: taskId, approve: approve};
		var me = this;
		$.post("oa/leave/task/complete.htm", p, function(data) {
			context.unblockUI();
			var r = eval("("+ data +")");
			if (r.result) {
				me.onCompleteTaskSuccess(r);
			} else {
				me.onCompleteTaskFailed(r.message);
			}
		});
	},
	
	onCompleteTaskSuccess: function(r) {
		context.info("审批成功");
		context.closeTab();
	},
	
	onCompleteTaskFailed: function(message) {
		context.error("处理失败：" + message);
	},
	
	doClose: function() {
		context.closeTab();
	},
	
	/**
	 * 签收任务
	 * @param taskId
	 */
	doClaim: function(taskId) {
		context.blockUI();
		var me = this;
		$.post("oa/leave/task/claim/"+ taskId +".htm", function(data) {
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
	
	doFormSubmit: function() {
		var isValid = $("#leaveForm").form('validate');
		if (!isValid){
			context.error("请正确填写请假申请单！");
			return;
		}
		context.blockUI("正在提交申请单");
		$("#leaveForm").ajaxSubmit(function(data) {
			context.unblockUI();
			var r = eval("("+ data +")");
			if (r.result) {
				context.info("请假申请单提交成功，你可以在我的申请里查看该申请，请等待" + r.nextTaskName);
				context.closeTab();
			}
			else {
				context.error(r.message);
			}
		});
	}
});