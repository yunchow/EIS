/**
 * Copyright 2007-2008 非也
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
package org.fireflow.kernel.impl;

import java.util.ArrayList;
import java.util.List;
import org.fireflow.kernel.INodeInstance;
import org.fireflow.kernel.IToken;
import org.fireflow.kernel.ITransitionInstance;
import org.fireflow.kernel.KernelException;
import org.fireflow.kernel.event.IEdgeInstanceEventListener;
import org.fireflow.kernel.event.EdgeInstanceEvent;
import org.fireflow.kernel.plugin.IKernelExtension;
import org.fireflow.kernel.plugin.IPlugable;
import org.fireflow.model.net.Transition;

/**
 * @author 非也
 *
 */
public class TransitionInstance extends EdgeInstance implements ITransitionInstance, IPlugable {

    public transient static final String Extension_Target_Name = "org.fireflow.kernel.TransitionInstance";
    public transient static List<String> Extension_Point_Names = new ArrayList<String>();
    public transient static final String Extension_Point_TransitionInstanceEventListener = "TransitionInstanceEventListener";

    static {
        Extension_Point_Names.add(Extension_Point_TransitionInstanceEventListener);
    }

    private transient Transition transition = null;


    public TransitionInstance(Transition t) {
        transition = t;
    }

    public String getId() {
        return this.transition.getId();
    }
//	private int weight = 0;
    public int getWeight() {
        if (weight == 0) {
            if (enteringNodeInstance instanceof StartNodeInstance) {
                weight=1;
                return weight;
            } else if (leavingNodeInstance instanceof EndNodeInstance) {
                weight=1;
                return weight;
            } else if (leavingNodeInstance instanceof ActivityInstance) {
                SynchronizerInstance synchronizerInstance = (SynchronizerInstance) enteringNodeInstance;
                int weight = synchronizerInstance.getVolume() / enteringNodeInstance.getLeavingTransitionInstances().size();
                return weight;

            } else if (leavingNodeInstance instanceof SynchronizerInstance) {
                SynchronizerInstance synchronizerInstance = (SynchronizerInstance) leavingNodeInstance;
                int weight = synchronizerInstance.getVolume() / leavingNodeInstance.getEnteringTransitionInstances().size();
                return weight;
            }
        }

        return weight;
    }
//	public void setWeight(int i){
//		this.weight = i;
//	}


    public boolean take(IToken token) throws KernelException {
        EdgeInstanceEvent e = new EdgeInstanceEvent(this);
        e.setToken(token);
        e.setEventType(EdgeInstanceEvent.ON_TAKING_THE_TOKEN);

        for (int i = 0; this.eventListeners != null && i < this.eventListeners.size(); i++) {
            IEdgeInstanceEventListener listener =  this.eventListeners.get(i);
            listener.onEdgeInstanceEventFired(e);
        }

        INodeInstance nodeInst = this.getLeavingNodeInstance();
        token.setValue(this.getWeight());
        boolean alive = token.isAlive();


        nodeInst.fire(token);

        return alive;
    }

    public Transition getTransition() {
        return this.transition;
    }

    public String getExtensionTargetName() {
        return this.Extension_Target_Name;
    }

    public List<String> getExtensionPointNames() {
        return this.Extension_Point_Names;
    }

    public void registExtension(IKernelExtension extension) throws RuntimeException {
        if (!Extension_Target_Name.equals(extension.getExtentionTargetName())) {
            return;
//			throw new KenelException("Error:When construct the TansitionInstance,the Extension_Target_Name is mismatching");
        }
        if (Extension_Point_TransitionInstanceEventListener.equals(extension.getExtentionPointName())) {
            if (extension instanceof IEdgeInstanceEventListener) {
                this.eventListeners.add((IEdgeInstanceEventListener) extension);
            } else {
                throw new RuntimeException("Error:When construct the TransitionInstance,the extension MUST be a instance of ITransitionInstanceEventListener");
            }
        }
    }
}
