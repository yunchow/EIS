/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 11, 2013
 */
package com.eis.core.context;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.InitializingBean;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 11, 2013
 */
public class ObjectMapper extends org.codehaus.jackson.map.ObjectMapper implements InitializingBean {

	@SuppressWarnings("deprecation")
	@Override
	public void afterPropertiesSet() throws Exception {
		getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		getSerializationConfig().withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	
}
