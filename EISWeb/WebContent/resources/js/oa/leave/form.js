jQuery.ns("oa.leave.form");

jQuery.define(oa.leave.form, base, {
	dgId: "leaveTimeDetailGrid",
	searchField: "name",
	deleteUrl: "sysmanage/user/delete/",
    saveUrl: "sysmanage/user/save.htm",
    updateUrl: "sysmanage/user/update.htm",
    
    init: function() {
    	context.log("oa.leave.form.init called");
    },
    
	onReady: function() {
		context.log("oa.leave.form is ready");
	},
	
	doApplyLeaveFormSubmit: function() {
		$("#leaveForm").submit();
	}
});