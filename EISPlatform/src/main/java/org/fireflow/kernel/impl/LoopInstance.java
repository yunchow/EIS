/*
 * Copyright 2007-2009 非也
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

 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses.
 */

package org.fireflow.kernel.impl;

import java.util.ArrayList;
import java.util.List;
import org.fireflow.kernel.ILoopInstance;
import org.fireflow.kernel.INodeInstance;
import org.fireflow.kernel.IToken;
import org.fireflow.kernel.KernelException;
import org.fireflow.kernel.event.EdgeInstanceEvent;
import org.fireflow.kernel.event.IEdgeInstanceEventListener;
import org.fireflow.kernel.plugin.IKernelExtension;
import org.fireflow.kernel.plugin.IPlugable;
import org.fireflow.model.net.Loop;

/**
 *
 * @author 非也
 * @version 1.0
 * Created on Mar 18, 2009
 */
public class LoopInstance extends EdgeInstance implements ILoopInstance ,IPlugable{
    public transient static final String Extension_Target_Name = "org.fireflow.kernel.LoopInstance";
    public transient static List<String> Extension_Point_Names = new ArrayList<String>();
    public transient static final String Extension_Point_LoopInstanceEventListener = "LoopInstanceEventListener";
//    public transient static final String Extension_Point_ConditionEvaluator = "ConditionEvaluator";

    static {
        Extension_Point_Names.add(Extension_Point_LoopInstanceEventListener);
//        Extension_Point_Names.add(Extension_Point_ConditionEvaluator);
    }

//    private 

    private transient Loop loop = null;

    public LoopInstance(Loop lp){
        this.loop = lp;
    }

    public String getId() {
        return loop.getId();
    }

    public int getWeight() {
        if (weight==0){
            if (leavingNodeInstance instanceof SynchronizerInstance){
                weight=((SynchronizerInstance)this.leavingNodeInstance).getVolume();
            }else if (leavingNodeInstance instanceof StartNodeInstance){
                weight = ((StartNodeInstance)this.leavingNodeInstance).getVolume();
            }else if (leavingNodeInstance instanceof EndNodeInstance){
                weight = ((EndNodeInstance)this.leavingNodeInstance).getVolume();
            }
        }
        return weight;
    }

    public boolean take(IToken token) throws KernelException {
        boolean oldAlive = token.isAlive();

        EdgeInstanceEvent e = new EdgeInstanceEvent(this);
        e.setToken(token);
        e.setEventType(EdgeInstanceEvent.ON_TAKING_THE_TOKEN);

        for (int i = 0; this.eventListeners != null && i < this.eventListeners.size(); i++) {
            IEdgeInstanceEventListener listener =  this.eventListeners.get(i);
            listener.onEdgeInstanceEventFired(e);
        }


        boolean newAlive = token.isAlive();

        if (!newAlive){//循环条件不满足，则恢复token的alive标示
            token.setAlive(oldAlive);
            return newAlive;
        }else{//否则流转到下一个节点

            INodeInstance nodeInst = this.getLeavingNodeInstance();
            token.setValue(this.getWeight());
            nodeInst.fire(token);
            return newAlive;
        }
    }

    public Loop getLoop() {
        return loop;
    }

    public void setLoop(Loop arg0){
        this.loop = arg0;
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
        if (Extension_Point_LoopInstanceEventListener.equals(extension.getExtentionPointName())) {
            if (extension instanceof IEdgeInstanceEventListener) {
                this.eventListeners.add((IEdgeInstanceEventListener) extension);
            } else {
                throw new RuntimeException("Error:When construct the TransitionInstance,the extension MUST be a instance of ITransitionInstanceEventListener");
            }
        }
    }

}
