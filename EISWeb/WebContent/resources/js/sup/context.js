/**
 * Title: context.js
 * @author: nick chow
 * @date: 2013-8-12
 * @copyright: Nick All Rights Reserved
 */

jQuery.ns("context");

jQuery.define(context, {
	
	/**
	 * 初始化
	 */
	ready: function() {
		this.init();
		$("#mmCenterMainTab").bind('contextmenu',function(e){
            e.preventDefault();
        });
		this.enableHomeTabRightClickMenue();
		this.bindHomeTabsMenueRightEvent();
	},
	init: function() {
		$.ajaxSetup({
			global: true,
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				jQuery.messager.alert("系统消息", "系统产生内部错误！", "error");
			}
		});
		/*$("body").bind("dblclick", function(e) {
			e.preventDefault();
			e.stopPropagation();
			context.log("body dblClick fired");
			return false;
		});*/
	},	
	/**
	 * 修改主题
	 */
	onChangeTheme: function(theme) {
		if (theme =='dark-hive') {
			$(".nav").css("color", "#FFF");
		} else {
			$(".nav").css("color", "#333");
		}
		$.cookie("theme", theme);
		$("link:first").prop('href', 'resources/plugins/jquery-easyui-1.3.4/themes/'+ theme +'/easyui.css');
	},
	confirm: function() {
		this.openDialog(arguments, "confirm");
	},
	prompt: function() {
		this.openDialog(arguments, "prompt");
	},
	openDialog: function(arguments, type) {
		var title = "系统消息";
		var message = "你确定要继续吗？";
		var onConfirmClick = tools.emptyFn;
		var onCancelClick = tools.emptyFn;
		if (arguments.length == 2) {
			message = arguments[0];
		}
		else if (arguments.length == 3) {
			if ($.isFunction(arguments[1])) {
				message = arguments[0];
				onConfirmClick = arguments[1];
			}
			else {
				title = arguments[0];
				message = arguments[1];
			}
		}
		else if (arguments.length == 4) {
			if ($.isFunction(arguments[1])) {
				message = arguments[0];
				onConfirmClick = arguments[1];
				onCancelClick = arguments[2];
			}
			else {
				title = arguments[0];
				message = arguments[1];
				onConfirmClick = arguments[2];
			}
		}
		else if (arguments.length == 5) {
			title = arguments[0];
			message = arguments[1];
			onConfirmClick = arguments[2];
			onCancelClick = arguments[3];
		}
		var scope = arguments[arguments.length - 1];
		var callbackFn = function(r) {
			if (r){
            	onConfirmClick.call(scope, r);
            }
            else {
            	onCancelClick.call(scope, r);
            }
		};
		if (type == "confirm") {
			$.messager.confirm(title, message, callbackFn);
		}
		else {
			$.messager.prompt(title, message, callbackFn);
		}
	},
	warn: function() {
		if (arguments.length == 1) {
			jQuery.messager.alert("系统警告", arguments[0], "warning");
		}
		else if (arguments.length == 2) {
			jQuery.messager.alert(arguments[0], arguments[1], "warning");
		}
	},
	info: function() {
		if (arguments.length == 1) {
			jQuery.messager.alert("系统消息", arguments[0], "info");
		}
		else if (arguments.length == 2) {
			jQuery.messager.alert(arguments[0], arguments[1], "info");
		}
	},
	error: function() {
		if (arguments.length == 1) {
			jQuery.messager.alert("系统错误", arguments[0], "error");
		}
		else if (arguments.length == 2) {
			jQuery.messager.alert(arguments[0], arguments[1], "error");
		}
	},
	alert: function() {
		if (arguments.length == 1) {
			jQuery.messager.alert("系统消息", arguments[0], "warning");
		}
		else if (arguments.length == 2) {
			jQuery.messager.alert(arguments[0], arguments[1], "warning");
		}
	},
	uuid: function() {
		return tools.uuid();
	},
	/**
	 * 记录日志
	 * @param message
	 */
	log: function(message) {
		jQuery.log(message);
	},
	/**
	 * copyright declare
	 */
	copyright: "Nick Chow All Rights Reserverd",
	
	getCopyright: function() {
		return this.copyright;
	},
	
	echo: function() {
		alert(this.getCopyright());
	},
	/**
	 * create new tab
	 * @param subtitle
	 * @param url
	 * @param icon
	 */
	addTab: function(subtitle, url, icon) {
		if (!$('#homeTabBar').tabs('exists', subtitle)) {
			if(url.indexOf('if') != -1){
				$('#homeTabBar').tabs('add', {
					title : subtitle,
					content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					closable : true,
					icon : icon
				});			
			} else {
				$('#homeTabBar').tabs('add', {
					title : subtitle,
					href : url,
					closable : true,
					icon : icon
				});			
			}
		} else {
			$('#homeTabBar').tabs('select', subtitle);
		}
		this.enableHomeTabRightClickMenue();
	},
	
	openTab: function(subtitle, url, icon) {
		if (!$('#homeTabBar').tabs('exists', subtitle)) {
			$('#homeTabBar').tabs('add', {
				title : subtitle,
				content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
				closable : true,
				icon : icon
			});
		} else {
			$('#homeTabBar').tabs('select', subtitle);
		}
	},
	
	/**
	 * 主页tabs右击菜单和双击事件
	 */
	enableHomeTabRightClickMenue: function() {
		/*$(".tabs li").on("dblclick", function(e) {
			e.preventDefault();
			e.stopPropagation();
			var subtitle = $(this).find(".tabs-title").html();
			context.log("准备关闭TAB:" + subtitle);
			$('#homeTabBar').tabs('close', subtitle);
			return false;
		})*/
		$(".tabs-inner").on('contextmenu', function(e) {
			context.log("主页面tab右键菜单Fired");
			e.preventDefault();
			context.log(e);
	        $('#mmCenterMainTab').menu('show',{
	            left: e.pageX,
	            top: e.pageY
	        });
	        var subtitle = $(this).children(".tabs-closable").text();
	        context.log("当前tab是" + subtitle);
			$('#mmCenterMainTab').data("currtab", subtitle);
	        return false;
		});
	},
	
	/**
	 * 绑定主页tab右键菜单事件
	 */
	bindHomeTabsMenueRightEvent: function() {
		$('#mm-tabupdate').click(function() { // 刷新
			var currTab = $('#homeTabBar').tabs('getSelected');
			var url = $(currTab.panel('options').content).attr('src');
			$('#homeTabBar').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(url)
				}
			})
		})
		// 关闭当前
		$('#mm-tabclose').click(function() {
			var currtab_title = $('#mmCenterMainTab').data("currtab");
			$('#homeTabBar').tabs('close', currtab_title);
		})
		// 全部关闭
		$('#mm-tabcloseall').click(function() {
			$('.tabs-inner span').each(function(i, n) {
				var t = $(n).text();
				$('#homeTabBar').tabs('close', t);
			});
		});
		// 关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function() {
			$('#mm-tabcloseright').click();
			$('#mm-tabcloseleft').click();
		});
		// 关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function() {
			var nextall = $('.tabs-selected').nextAll();
			if (nextall.length == 0) {
				return false;
			}
			nextall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				$('#homeTabBar').tabs('close', t);
			});
			return false;
		});
		// 关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function() {
			var prevall = $('.tabs-selected').prevAll();
			if (prevall.length == 0) {
				return false;
			}
			prevall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				$('#homeTabBar').tabs('close', t);
			});
			return false;
		});

		// 退出
		$("#mm-exit").click(function() {
			$('#mmCenterMainTab').menu('hide');
		});
	}
});
