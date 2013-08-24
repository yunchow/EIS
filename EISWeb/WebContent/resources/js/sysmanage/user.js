jQuery.ns("sysmanage.user");

jQuery.define(sysmanage.user, base, {
	dgId: "userGrid",
	searchField: "name",
	deleteUrl: "sysmanage/user/delete/",
    saveUrl: "sysmanage/user/save.htm",
    updateUrl: "sysmanage/user/update.htm",
    
    init: function() {
    	
    	context.log("sysmanage.user.init called");
    },
    
	onReady: function() {
		context.log("sysmanage.user is ready");
	},
	
	formatRole: function(value, row, index) {
		if (!value) {
			return "";
		}
		if ($.isArray(value) && value.length == 0) {
			return "";
		}
		context.log("format role: value = " + value);
		return sysmanage.user.formatRoleFor(tools.joinArrayIf(value), row, index);
	},
	
	formatRoleFor: function(value, row, index) {
		if (!value) {
			return "";
		}
		context.log("format role for: value = " + value);
		var names = [];
		for (var i in store.roleListStore) {
			var role = store.roleListStore[i];
			if (value.indexOf(role.id) >= 0) {
				names.push(role.name);
			}
		}
		return names.join(",");
	},
	
	formatGender: function(value, row, index) {
		return value == "M" ? "男性" : "女性";
	},
	
	formatPassword: function(value, row, index) {
		return "******";
	},
	
	formatMenu: function(value, row, index) {
		return sysmanage.user.formatMenuFor(tools.joinArrayIf(value), row, index);
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