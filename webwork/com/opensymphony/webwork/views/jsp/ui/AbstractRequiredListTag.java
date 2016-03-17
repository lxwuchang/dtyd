/*
 * Copyright (c) 2002-2005 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


import com.opensymphony.webwork.components.ListUIBean;

/**
 * 
 * @author tm_jee
 * @version $Date: 2005-12-16 18:48:22 +0100 (Fri, 16 Dec 2005) $ $Id: AbstractRequiredListTag.java 1714 2005-12-16 17:48:22Z tmjee $
 */
public abstract class AbstractRequiredListTag extends AbstractListTag {

	protected void populateParams() {
		super.populateParams();
		
		ListUIBean listUIBean = (ListUIBean) component;
		listUIBean.setThrowExceptionOnNullValueAttribute(true);
	}

}
