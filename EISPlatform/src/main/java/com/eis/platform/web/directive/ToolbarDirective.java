/**
 * fileName: EISWeb/com.eis.web.directive/ToolbarDirective.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 15, 2013
 */
package com.eis.platform.web.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.utility.StringUtil;

 /**
 * Title: ToolbarDirective.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 15, 2013
 */
public class ToolbarDirective implements TemplateDirectiveModel {
	private static final String KEY = "ns";
	
	/**
	 * slf4j logger
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
     * Executes this user-defined directive; called by FreeMarker when the user-defined
     * directive is called in the template.
     *
     * @throws TemplateException
     * @throws IOException
     */
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
		if (params.isEmpty()) {
			throw new IOException("param ns is required");
		}
		TemplateModel value = (TemplateModel) params.get(KEY);
		String[] values = StringUtil.split(value.toString(), ',');
		StringBuilder args = new StringBuilder();
		for (String ns : values) {
			args.append("'"+ ns +"',");
		}
		args.deleteCharAt(args.length() - 1);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Require directive args is " + args);
		}
		Writer out = env.getOut();
		out.write("<script type='text/javascript'>\n");
		out.write("\tjQuery.require("+ args +");\n");
		out.write("</script>\n");
	}

}
