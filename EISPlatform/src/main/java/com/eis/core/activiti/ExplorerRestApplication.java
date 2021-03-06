package com.eis.core.activiti;

import org.activiti.diagram.rest.application.DiagramServicesInit;
import org.activiti.editor.rest.application.ModelerServicesInit;
import org.activiti.rest.api.DefaultResource;
import org.activiti.rest.application.ActivitiRestApplication;
import org.activiti.rest.filter.JsonpFilter;
import org.restlet.Restlet;
import org.restlet.routing.Router;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 18, 2013
 */
public class ExplorerRestApplication extends ActivitiRestApplication {

	public ExplorerRestApplication() {
		super();
	}

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attachDefault(DefaultResource.class);
		ModelerServicesInit.attachResources(router);
		DiagramServicesInit.attachResources(router);
		JsonpFilter jsonpFilter = new JsonpFilter(getContext());
		jsonpFilter.setNext(router);
		return jsonpFilter;
	}

}
