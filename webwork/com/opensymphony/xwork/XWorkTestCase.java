/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork;

import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.XWorkConverter;
import junit.framework.TestCase;

/**
 * Base JUnit TestCase to extend for XWork specific unit tests.
 *
 * @author plightbo
 * @author tmjee
 * 
 * @version $Date: 2007-06-02 11:01:04 +0200 (Sat, 02 Jun 2007) $ $Id: XWorkTestCase.java 1533 2007-06-02 09:01:04Z tm_jee $
 */
public abstract class XWorkTestCase extends TestCase {
    
    protected void setUp() throws Exception {
        // Reset the value stack
        OgnlValueStack stack = new OgnlValueStack();
        ActionContext.setContext(new ActionContext(stack.getContext()));

        //  clear out configuration
        ConfigurationManager.destroyConfiguration();
        ConfigurationManager.setConfiguration(null);

        // clear out localization
        LocalizedTextUtil.reset();

        // type conversion
        XWorkConverter.resetInstance();

        // reset ognl
        OgnlValueStack.reset();
    }

    protected void tearDown() throws Exception {
        // reset the old object factory
        ObjectFactory.setObjectFactory(new ObjectFactory());
        
        //  clear out configuration
        ConfigurationManager.destroyConfiguration();
        ConfigurationManager.setConfiguration(null);
    }
}
