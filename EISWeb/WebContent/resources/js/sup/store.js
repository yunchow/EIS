/**
 * 公共数据存储，比如
 * 1，常用数据字典
 * 2，菜单
 * 3，组织结构
 */
store = {
	/**
	 * 系统菜单
	 */
	menuTreeStore: undefined,
	/**
	 * 系统中所有角色
	 */
	roleListStore: undefined,
	
	initRoleStore: function() {
		if (this.roleStore != undefined) {
			return;
		}
		var me = this;
		$.post("sysmanage/user/role.htm", function(data) {
			me.roleListStore = $.parseJSON(data);
			context.log("init role list store correctly");
		});
	},
	
	initMenuTreeStore: function() {
		if (this.menuTreeStore != undefined) {
			return;
		}
		var me = this;
		$.post("sysmanage/menu/tree.htm", function(data) {
			me.menuTreeStore = $.parseJSON(data);
			context.log("init menu tree store correctly");
		});
	}
	
}