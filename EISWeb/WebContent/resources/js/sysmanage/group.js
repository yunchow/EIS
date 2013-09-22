jQuery.ns("sysmanage.group");

jQuery.define(sysmanage.group, base, {
	dgId: "groupGrid",
	searchField: "name",
	deleteUrl: "sysmanage/group/delete/",
    saveUrl: "sysmanage/group/save.htm",
    updateUrl: "sysmanage/group/update.htm",
    
    init: function() {
    	
    },
    
	onReady: function() {
		context.log("sysmanage.group is ready");
	},
	
	formatUsers: function(value, row, index) {
		return sysmanage.group.formatUsersFor(tools.joinArrayIf(value), row, index);
	},
	
	formatUsersFor: function(value, row, index) {
		if (!value) {
			return "";
		}
		var names = [];
		for (var i in store.userListStore) {
			var user = store.userListStore[i];
			if (value.indexOf(user.id) >= 0) {
				names.push(user.name);
			}
		}
		return names.join(",");
	}
});