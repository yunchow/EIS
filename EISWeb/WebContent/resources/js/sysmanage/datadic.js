jQuery.ns("sysmanage.datadic");

jQuery.define(sysmanage.datadic, base, {
	dgId: "dg",
	searchField: "type",
	deleteUrl: "sysmanage/datadic/delete/",
    saveUrl: "sysmanage/datadic/save.htm",
    updateUrl: "sysmanage/datadic/update.htm",
    
	ready: function() {
		parents.ready();
	}

});