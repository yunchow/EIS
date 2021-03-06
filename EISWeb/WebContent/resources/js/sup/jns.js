/**
 * This jQuery Plugin is to support namespace feature
 * @author: nick chow
 * @date: 2013-8-12
 * @copyright: Nick Chow All Rights Reserved
 */
jQuery.extend({
	consoleEnabled: undefined,
	log: function(message) {
		if (this.consoleEnabled == undefined) {
			try {
				console.log("install log first");
				this.consoleEnabled = true;
			} catch (error) {
				this.consoleEnabled = false;
			}
		}
		if (this.consoleEnabled == true) {
			console.log(message);
		}
	}
});
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
     * define modules to gover variables into different namespaces
     * for example:
     * jQuery.define("foo.bar", {....}); or
     * jQuery.define(foo.bar, {...});
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
    	if (arguments.length >= 2) {
    		jQuery.extend(obj, arguments[1]);
    	}
    	if (arguments.length >= 3) {
    		jQuery.extend(obj, arguments[2]);
    	}
    	if (obj.init) {
			try {
				obj.init.call(obj);
	    	}
	    	catch (e)  {
	    		jQuery.log("Error: init exception");
        		jQuery.log(e);
	    	}
		}
    	$(function() {
    		if (obj.ready) {
    			try {
        			obj.ready.call(obj);
            	}
            	catch (e)  {
            		jQuery.log("Error: ready exception");
            		jQuery.log(e);
            	}
    		}
    	});
    },
    /**
     * require another modules or instances to be loaded before render the current page
     * support dynamic arguments
     * for example:
     * jQuery.require("context") or 
     * jQuery.require("context", "sysmanage.menu") or
     * jQuery.require(["context", "sysmanage.menu"], "foo.bar")
     */
    basePath: "resources/js/",
    require: function() {
    	if (arguments.length < 1) {
    		throw new Error("Illegal arguments error");
    	}
    	var paths = [];
    	for (i = 0, j = arguments.length; i < j; i++) {
    		var arg = arguments[i];
    		if (!!!arg) {
    			continue;
    		}
    		if (jQuery.type(arg) == 'array') {
    			for (k = 0; k < arg.length; k++) {
    				var path = arg[k].replace(/\./g, "\/");
        			paths.push(path);
    			}
    		}
    		else if (jQuery.type(arg) == 'string') {
    			var path = arg.replace(/\./g, "\/");
    			paths.push(path);
    		}
    	}
    	for (i = 0; i < paths.length; i++) {
    		var url = this.basePath + paths[i] +".js";
    		jQuery.log("need loaded script --> " + url);
    		var t1 = new Date().getTime();
    		jQuery.ajax(url, {
				dataType: 'script',
				async: false,
				cache: true,
				success: function() {
					var t2 = new Date().getTime();
					jQuery.log("script [" + url + "] loaded successfully and cost "
							+ (t2 - t1) +" ms");
				}
			});
    	}
    },
    load: function() {
    	if (arguments.length < 1) {
    		throw new Error("Illegal arguments error");
    	}
    	var paths = [];
    	for (i = 0, j = arguments.length; i < j; i++) {
    		var arg = arguments[i];
    		if (!!!arg) {
    			continue;
    		}
    		if (jQuery.type(arg) == 'array') {
    			for (k = 0; k < arg.length; k++) {
        			paths.push(arg[k]);
    			}
    		}
    		else if (jQuery.type(arg) == 'string') {
    			paths.push(arg);
    		}
    	}
    	for (i = 0; i < paths.length; i++) {
    		var url = paths[i];
    		jQuery.log("need loaded resource --> " + url);
    		var t1 = new Date().getTime();
    		jQuery.ajax(url, {
				dataType: 'script',
				async: false,
				cache: true,
				success: function() {
					var t2 = new Date().getTime();
					jQuery.log("script [" + url + "] loaded successfully and cost "
							+ (t2 - t1) +" ms");
				}
			});
    	}
    }
});
/**
 * alias for namespace method
 */
jQuery.ns = jQuery.namespace;
