/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 24, 2013
 */
package com.eis.base.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.DispatcherServlet;

import com.eis.base.dto.User;
import com.eis.base.util.CurrentUserUtil;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 24, 2013
 */
public class ControllerServlet extends DispatcherServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if (user != null || request.getRequestURI().indexOf("/login.htm") >= 0) {
			CurrentUserUtil.setCurrentUser((User)user);
			super.service(request, response);
		} else if (request.getRequestURI().indexOf("/home.htm") >= 0) {
			response.sendRedirect("index.html");
		} else {
			response.getWriter().write("{timeout:true}");
		}
	}
	
}
