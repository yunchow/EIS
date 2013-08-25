/**
 * 系统管理 - 菜单管理
 */
jQuery.ns("sysmanage.organization");

jQuery.define(sysmanage.organization, btree, {
	tgName: "tgOrganization",
	tgMenuName: "mmOrganizationGrid",
	deleteUrl: "sysmanage/organization/delete/",
    saveUrl: "sysmanage/organization/save.htm",
    updateUrl: "sysmanage/organization/update.htm",
    
	onReady: function() {
		context.log("sysmanage.organization.ready is called.");
	}
  
});

