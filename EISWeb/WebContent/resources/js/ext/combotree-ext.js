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
			var valus = jQuery(target).combotree('getValues');
			if (!!!valus) return '';
			return valus.join(',');
		},
		setValue : function(target, value) {
			if (!!!value) return;
			var t = value, c = [];
			if ($.isArray(value)) {
				for (var i in value) {
					c.push(value[i].id);
				}
				t = c.join(",");
			}
			var values = t.split(',');
			jQuery(target).combotree('setValues', values);
		},
		resize : function(target, width) {
			jQuery(target).combotree('resize', width);
		}
	}
});