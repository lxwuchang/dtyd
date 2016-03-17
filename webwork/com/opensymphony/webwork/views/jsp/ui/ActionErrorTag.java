/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.ActionError;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * ActionError Tag.
 * 
 * @author tm_jee
 * @version $Date: 2005-12-22 16:05:26 +0100 (Thu, 22 Dec 2005) $ $Id: ActionErrorTag.java 1828 2005-12-22 15:05:26Z tmjee $
 */
public class ActionErrorTag extends AbstractUITag {

	public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new ActionError(stack, req, res);
	}

}