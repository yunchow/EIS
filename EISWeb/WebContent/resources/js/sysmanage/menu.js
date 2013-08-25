/**
 * 系统管理 - 菜单管理
 */
jQuery.ns("sysmanage.menu");

jQuery.define(sysmanage.menu, btree, {
	tgName: "tgMenuSetting",
	tgMenuName: "mmMenuGrid",
	deleteUrl: "sysmanage/menu/delete/",
    saveUrl: "sysmanage/menu/save.htm",
    updateUrl: "sysmanage/menu/update.htm",
    
	onReady: function() {
		context.log("sysmanage.menu.ready is called.");
	},
	
    formatIcon: function(value, row, index) {
		if (!value) {
			return "";
		}
		return '<span class="icon '+ value +'">&nbsp;</span>';
	},
	
	formatStatus: function(value, row, index) {
		if (!value) {
			return "";
		}
		if (value == 'Y') {
			return '<span style="color:#333;">显示</span>';
		} else if (value == 'N') {
			return '<span style="color:blue;">隐藏</span>';
		} else {
			return '<span style="color:red;">错误</span>';
		}
	}
  
});

