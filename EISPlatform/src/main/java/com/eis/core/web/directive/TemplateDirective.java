/**
 * fileName: EISPlatform/com.eis.platform.web.directive/TemplateDirective.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 25, 2013
 */
package com.eis.core.web.directive;

import java.io.IOException;
import java.io.StringWriter;
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
 * Title: TemplateDirective.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 25, 2013
 */
public class TemplateDirective extends TemplateDirectiveModelSupport {
	
	private String template;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, 
			TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		params.put(TOKEN, getToken());
		SimpleScalar ns = (SimpleScalar) params.get("ns");
		logger.debug("ns is {}", ns);
		Assert.notNull(ns, "ns property is required for datagrid derictive");
		if (body != null) {
			StringWriter sw = new StringWriter();
			body.render(sw);
			params.put("body", new SimpleScalar(sw.toString()));
		}
		Template template = configuration.getTemplate(this.template);
		template.process(params, env.getOut());
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
