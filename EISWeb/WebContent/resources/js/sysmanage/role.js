jQuery.ns("sysmanage.role");

jQuery.define(sysmanage.role, base, {
	dgId: "roleGrid",
	searchField: "name",
	deleteUrl: "sysmanage/role/delete/",
    saveUrl: "sysmanage/role/save.htm",
    updateUrl: "sysmanage/role/update.htm",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("sysmanage.role is ready");
	},
	
	formatMenu: function(value, row, index) {
		return sysmanage.role.formatMenuFor(tools.joinArrayIf(value), row, index);
	},
	
	formatMenuFor: function(value, row, index) {
		if (!value) {
			return "";
		}
		var names = [];
		for (var i in store.menuTreeStore) {
			var menu = store.menuTreeStore[i];
			this.getTreeNodeName(menu, value, names);
		}
		return names.join(",");
	}
});