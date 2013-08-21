/**
 * fileName: EISPlatform/com.eis.platform.web.directive/SimpleTemplateDirectiveModel.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 21, 2013
 */
package com.eis.platform.web.directive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveModel;

/**
 * Title: SimpleTemplateDirectiveModel.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 21, 2013
 */
public abstract class TemplateDirectiveModelSupport implements TemplateDirectiveModel {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected Configuration configuration;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
