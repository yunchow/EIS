/**
 * 增加 jQuery 插件对名称空间进行支持
 * @author: nick chow
 * @date: 2013-8-12
 * @copyright: Nick All Rights Reserved
 */
jQuery.extend({
	/**
	 * create new package and object instance if not exists
	 * <code>namespace</code> comply with OGNL notion and semantics
	 * for example:
	 * <code>
	 * 	jQuery.namespace("foo.bar");
	 *  jQuery.namespace("foo.obj", "bar.obj");
	 * </code>
	 * @returns
	 */
	namespace : function(){
        var len1 = arguments.length,
            i = 0,
            len2,
            j,
            main,
            ns,
            sub,
            current;
        for(; i < len1; ++i) {
            main = arguments[i];
            ns = arguments[i].split('.');
            current = window[ns[0]];
            if (current === undefined) {
                current = window[ns[0]] = {};
            }
            sub = ns.slice(1);
            len2 = sub.length;
            for(j = 0; j < len2; ++j) {
                current = current[sub[j]] = current[sub[j]] || {};
            }
        }
        return current;
    },
    /**
     * define modules to gover variables for different ns
     */
    define: function() {
    	if (arguments.length < 2) {
    		throw new Error("Illegal arguments state error");
    	}
    	var a1 = arguments[0], obj;
    	if (typeof(a1) == "string") {
    		obj = this.namespace(a1);
    	} 
    	else if (typeof(a1) == "object") {
    		obj = a1;
    	}
    	else {
    		throw new Error("Illegal arguments type error");
    	}
    	if (arguments.length == 2) {
    		jQuery.extend(obj, arguments[1]);
    	}
    }
});
/**
 * alias for namespace method
 */
jQuery.ns = jQuery.namespace;
