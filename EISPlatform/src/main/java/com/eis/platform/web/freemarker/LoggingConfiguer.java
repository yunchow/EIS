/**
 * fileName: EISPlatform/com.eis.platform.web.freemarker/LoggingConfiguer.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 21, 2013
 */
package com.eis.platform.web.freemarker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

 /**
 * Title: LoggingConfiguer.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 21, 2013
 */
public class LoggingConfiguer implements InitializingBean {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void afterPropertiesSet() throws Exception {
		freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_SLF4J);
		logger.info("set freemarkder logging to slf4j");
	}

}
