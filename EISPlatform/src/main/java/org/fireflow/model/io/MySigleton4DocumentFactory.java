/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fireflow.model.io;

import org.dom4j.DocumentFactory;
import org.dom4j.util.SingletonStrategy;

/**
 *
 * @author chennieyun
 */
public class MySigleton4DocumentFactory implements SingletonStrategy {

    public Object instance() {
        System.out.println("============InSideMySigleton4DocumentFactory........");
        return new DocumentFactory();
    }

    public void reset() {

    }

    public void setSingletonClassName(String arg0) {

    }

}
