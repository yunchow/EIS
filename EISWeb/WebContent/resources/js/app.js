/**
 * Title: app.js
 * @author: nick chow
 * @date: 2013-8-12
 * @copyright: Nick All Rights Reserved
 */

jQuery.ns("app");

jQuery.extend(app, {
	
	/**
	 * 初始化
	 */
	ready: function() {
		this.enableHomeTabRightClickMenue();
		this.bindHomeTabsMenueRightEvent();
	},
	
	/**
	 * copyright declare
	 */
	copyright: function() {
		alert("Nick Chow All Rights Reserverd");
	},
	/**
	 * create new tab
	 * @param subtitle
	 * @param url
	 * @param icon
	 */
	addTab: function(subtitle, url, icon) {
		if (!$('#homeTabBar').tabs('exists', subtitle)) {
			if(url.indexOf('isIframe') != -1){
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
			$.messager.progress('close');
		}

		this.enableHomeTabRightClickMenue();
	},
	
	/**
	 * 主页tabs右击菜单
	 */
	enableHomeTabRightClickMenue: function() {
		/* 双击关闭TAB选项卡 */
		$(".tabs-inner").dblclick(function() {
			var subtitle = $(this).children(".tabs-closable").text();
			$('#homeTabBar').tabs('close', subtitle);
		})
		/* 为选项卡绑定右键 */
		$(".tabs-inner").bind('contextmenu', function(e) {
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
			var subtitle = $(this).children(".tabs-closable").text();
			$('#mm').data("currtab", subtitle);
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
			var currtab_title = $('#mm').data("currtab");
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
			$('#mm').menu('hide');
		});
	}
});
