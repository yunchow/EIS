/**
 * 公共JS工具库
 * @author: nick chow
 * @date: 2013-8-23
 * @copyright: Nick All Rights Reserved
 */

tools = {
	/**
	 * 将数组中的所有对象的指定属性,进行连接成为字符串，默认根据ID拼接
	 * 对于字符串类型直接返回
	 * @param value
	 * @param prop
	 */
	joinArrayIf: function(value, prop) {
		if (prop == undefined) {
			prop = "id";
		}
		if ($.isArray(value)) {
			var c = [];
			for (var i in value) {
				c.push(value[i][prop]);
			}
			return c.join(",");
		}
		else {
			return value;
		}
	},
	
	uuid: function() {
		return new UUID().id;
	},
	
	emptyFn: function() {
		
	}
};
