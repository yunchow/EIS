jQuery.ns("sysmanage.role");

jQuery.define(sysmanage.role, base, {
	dgId: "roleGrid",
	searchField: "name",
	deleteUrl: "sysmanage/role/delete/",
    saveUrl: "sysmanage/role/save.htm",
    updateUrl: "sysmanage/role/update.htm",
    
    init: function() {
    	this.loadMenuTreeStore();
    },
    
	onReady: function() {
		context.log("sysmanage.role is ready");
	},
	
	formatMenu: function(value, row, index) {
		
		if (!value) {
			return "";
		}
		return "格式：" + value;
	},

});