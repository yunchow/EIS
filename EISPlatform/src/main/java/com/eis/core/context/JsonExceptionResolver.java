/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 11, 2013
 */
package com.eis.core.context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 11, 2013
 */
public class JsonExceptionResolver implements HandlerExceptionResolver {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("error", true);
		map.put("message", sw.toString());
		map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		ObjectMapper objectMapper = new ObjectMapper();
		response.setContentType("text/html;charset=UTF-8");
		
		try {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			objectMapper.writeValue(response.getOutputStream(), map);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
