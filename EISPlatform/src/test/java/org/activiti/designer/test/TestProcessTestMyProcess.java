/*package org.activiti.designer.test;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:conf/beans-*.xml")
public class TestProcessTestMyProcess {

	private String filename = "/process/LeaveProcess.bpmn";

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Rule
	public ActivitiRule activitiSpringRule;

	@Test
	public void testProcess() throws Exception {
		RepositoryService repositoryService = activitiSpringRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("myProcess.bpmn20.xml", getClass().getResourceAsStream(filename)).deploy();

		RuntimeService runtimeService = activitiSpringRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " " + processInstance.getProcessDefinitionId());

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		Assert.assertEquals("User Task", task.getName());
		System.out.println("Task Name is: " + task.getName());

		//Map<String, Object> vars = task.getProcessVariables();
		//Assert.assertEquals("Activiti", vars.get("name"));

		taskService.claim(task.getId(), "nick");
		taskService.complete(task.getId());
		Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().count());
		System.out.println("Go through LeaveProcess.bpm sucessfully.");
	}
}*/