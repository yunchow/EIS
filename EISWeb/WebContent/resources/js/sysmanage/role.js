jQuery.ns("sysmanage.role");

jQuery.define(sysmanage.role, base, {
	dgId: "roleGrid",
	searchField: "name",
	deleteUrl: "sysmanage/role/delete/",
    saveUrl: "sysmanage/role/save.htm",
    updateUrl: "sysmanage/role/update.htm",
    
	onReady: function() {
		context.log("sysmanage.role is ready");
	}

});