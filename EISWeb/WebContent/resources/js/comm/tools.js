/**
 * 公共JS工具库
 * @author: nick chow
 * @date: 2013-8-23
 * @copyright: Nick All Rights Reserved
 */

jQuery.ns("tools");

jQuery.define(tools, {
	/**
	 * 将数组中的所有元素用,进行连接成为字符串
	 * 对于字符串类型直接返回
	 * @param value
	 */
	joinArrayIf: function(value) {
		if ($.isArray(value)) {
			var c = [];
			for (var i in value) {
				c.push(value[i].mid);
			}
			return c.join(",");
		}
		else {
			return value;
		}
	}
});
