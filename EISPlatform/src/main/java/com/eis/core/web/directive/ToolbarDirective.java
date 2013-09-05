/**
 * fileName: EISWeb/com.eis.web.directive/ToolbarDirective.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 15, 2013
 */
package com.eis.core.web.directive;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.util.Assert;

import com.eis.core.web.directive.TemplateDirectiveModelSupport;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

 /**
 * Title: ToolbarDirective.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 15, 2013
 */
public class ToolbarDirective extends TemplateDirectiveModelSupport {
	

	/**
     * Executes this user-defined directive; called by FreeMarker when the user-defined
     * directive is called in the template.
     *
     * @throws TemplateException
     * @throws IOException
     */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
		params.put(TOKEN, getToken());
		SimpleScalar ns = (SimpleScalar) params.get("ns");
		logger.debug("ns is {}", ns);
		Assert.notNull(ns, "ns property is required for toolbar derictive");
		
		if (body != null) {
			StringWriter sw = new StringWriter();
			body.render(sw);
			params.put("append", new SimpleScalar(sw.toString()));
		}
		Writer out = env.getOut();
		Template template = configuration.getTemplate("toolbar.ftl");
		template.process(params, out);
	}

}
