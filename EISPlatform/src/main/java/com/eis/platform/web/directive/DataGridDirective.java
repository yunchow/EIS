/**
 * fileName: EISPlatform/com.eis.platform.web.directive/DataGridDirective.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 21, 2013
 */
package com.eis.platform.web.directive;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.util.Assert;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

 /**
 * Title: DataGridDirective.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 21, 2013
 */
public class DataGridDirective extends TemplateDirectiveModelSupport {

	private String templateName = "datagrid.ftl";
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
		params.put(TOKEN, getToken());
		SimpleScalar ns = (SimpleScalar) params.get("ns");
		logger.debug("ns is {}", ns);
		Assert.notNull(ns, "ns property is required for datagrid derictive");
		
		SimpleScalar url = (SimpleScalar) params.get("url");
		logger.debug("url is {}", url);
		Assert.notNull(url, "url property is required for datagrid derictive");
		
		StringWriter sw = new StringWriter();
		body.render(sw);
		params.put("body", new SimpleScalar(sw.toString()));
		
		Writer out = env.getOut();
		Template template = configuration.getTemplate(templateName);
		template.process(params, out);
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
}
