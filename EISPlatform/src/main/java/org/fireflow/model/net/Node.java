/**
 * Copyright 2004-2008 非也
 * All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation。
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses. *
 */
package org.fireflow.model.net;

import org.fireflow.model.AbstractWFElement;
import org.fireflow.model.WorkflowProcess;

/**
 * 工作流网的节点。
 * @author 非也,nychen2000@163.com
 *
 */
public class Node extends AbstractWFElement {

    public Node() {
    }

    public Node(WorkflowProcess workflowProcess, String name) {
        super(workflowProcess, name);
    // TODO Auto-generated constructor stub
    }
}
