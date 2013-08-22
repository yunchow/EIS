jQuery.ns("sysmanage.datadic");

jQuery.define(sysmanage.datadic, base, {
	dgId: "datadicGrid",
	searchField: "type",
	deleteUrl: "sysmanage/datadic/delete/",
    saveUrl: "sysmanage/datadic/save.htm",
    updateUrl: "sysmanage/datadic/update.htm",
    
	onReady: function() {
		context.log("sysmanage.datadic is ready");
	}

});