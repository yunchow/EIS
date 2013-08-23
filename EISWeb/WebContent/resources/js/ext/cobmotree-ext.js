/**
 * 重写combotree组件，支持多选 
 */
jQuery.extend(jQuery.fn.datagrid.defaults.editors, {
	combotree : {
		init : function(container, options) {
			var editor = jQuery('<input type="text">').appendTo(container);
			editor.combotree(options);
			return editor;
		},
		destroy : function(target) {
			jQuery(target).combotree('destroy');
		},
		getValue : function(target) {
			var temp = jQuery(target).combotree('getValues');
			return temp.join(',');
		},
		setValue : function(target, value) {
			var temp = value.split(',');
			jQuery(target).combotree('setValues', temp);
		},
		resize : function(target, width) {
			jQuery(target).combotree('resize', width);
		}
	}
});