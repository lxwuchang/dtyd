/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

/**
 * An interface indicating the lifecycle of an ObjectFactory implementation.
 * 
 * @author tm_jee
 * @version $Date: 2006-03-16 15:44:02 +0100 (Thu, 16 Mar 2006) $ $Id: ObjectFactoryLifecycle.java 2423 2006-03-16 14:44:02Z tmjee $
 * 
 * @see ObjectFactoryLifecycle
 * @see com.opensymphony.xwork.ObjectFactory
 * @see com.opensymphony.webwork.util.ObjectFactoryInitializable
 * @see com.opensymphony.webwork.util.ObjectFactoryDestroyable
 */
public interface ObjectFactoryLifecycle extends ObjectFactoryInitializable, ObjectFactoryDestroyable {
	
}
