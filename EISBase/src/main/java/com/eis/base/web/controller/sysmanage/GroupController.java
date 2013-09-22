/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 21, 2013
 */
package com.eis.base.web.controller.sysmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.base.domain.repository.UserRepository;
import com.eis.core.helper.UUIDHelper;

/**
 * <p>
 * Please comment here
 * 
 * @author nick.chow
 * @date: Sep 21, 2013
 */
@Controller
@RequestMapping("/sysmanage/group/")
public class GroupController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/grid")
	public String prepare() {
		return "sysmanage/group.ftl";
	}

	@RequestMapping("/save")
	@ResponseBody
	@Transactional
	public int save(@RequestParam String name, @RequestParam String userIds) {
		Group group = identityService.newGroup(UUIDHelper.uuid());
		group.setName(name);
		identityService.saveGroup(group);
		
		String[] userIdSplits = StringUtils.split(userIds, ",");
		for (String userId : userIdSplits) {
			identityService.createMembership(userId, group.getId());
		}
		return -1;
	}
	
	@RequestMapping("/users")
	@ResponseBody
	public List<Map<String, String>> findAllMenu() {
		return userRepository.findAllUser();
	}

	@RequestMapping("/update")
	@ResponseBody
	@Transactional
	public int update(@RequestParam String id, @RequestParam String name, @RequestParam String userIds) {
		identityService.deleteGroup(id);
		Group group = identityService.newGroup(id);
		group.setName(name);
		identityService.saveGroup(group);
		String[] userIdSplits = StringUtils.split(userIds, ",");
		for (String userId : userIdSplits) {
			identityService.createMembership(userId, group.getId());
		}
		return -1;
	}

	@RequestMapping("/delete/{id}")
	@ResponseBody
	@Transactional
	public int delete(@PathVariable String id) {
		identityService.deleteGroup(id);
		return -1;
	}

	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> findByPage(@RequestParam Map<String, Object> model) {
		String groupName = (String) model.get("name");
		groupName = StringUtils.defaultIfEmpty(groupName, "%%");
		Integer page = Integer.valueOf("" + model.get("page"));
		Integer rows = Integer.valueOf("" + model.get("rows"));
		Integer firstResult = (page - 1) * rows;
		
		GroupQuery query = identityService.createGroupQuery().groupNameLike(groupName);
		model.put("total", query.count());
		List<Group> groups = query.orderByGroupName().asc().listPage(firstResult, rows);
		List<Map<String, Object>> groupMapList = new  ArrayList<Map<String, Object>>(groups.size());
		
		for (Group group : groups) {
			Map<String, Object> groupMap = new HashMap<String, Object>();
			groupMap.put("id", group.getId());
			groupMap.put("name", group.getName());
			List<User> users = identityService.createUserQuery().memberOfGroup(group.getId()).list();
			
			if (!users.isEmpty()) {
				StringBuilder userIds = new StringBuilder();
				for (User user : users) {
					userIds.append(user.getId()).append(",");
				}
				userIds.deleteCharAt(userIds.length() - 1);
				groupMap.put("userIds", userIds);
			}
			groupMapList.add(groupMap);
		}
		
		model.put("rows", groupMapList);
		return model;
	}

}
