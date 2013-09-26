/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 24, 2013
 */
package com.eis.base.util;

import java.util.List;

import org.activiti.engine.identity.Group;

import com.eis.base.dto.User;
import com.eis.core.context.ContextHolder;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 24, 2013
 */
public abstract class CurrentUserUtil {
	private static final String CURRENT_USER = "CURRENT_USER";
	
	public static void setCurrentUser(User user) {
		if (user != null) {
			ContextHolder.set(CURRENT_USER, user);
		}
	}
	
	public static User getUser() {
		return (User) ContextHolder.getObject(CURRENT_USER);
	}
	
	public static List<Group> getUserGroups() {
		return getUser().getGroups();
	}
	
	public static String getUserId() {
		return getUser().getId();
	}
	
	public static String getUserName() {
		return getUser().getName();
	}
	
}
