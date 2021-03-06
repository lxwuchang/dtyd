/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.ActionMessage;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * ActionMessage Tag.
 * 
 * @author tm_jee
 * @version $Date: 2005-12-22 17:24:25 +0100 (Thu, 22 Dec 2005) $ $Id: ActionMessageTag.java 1832 2005-12-22 16:24:25Z tmjee $
 */
public class ActionMessageTag extends AbstractUITag {

	public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new ActionMessage(stack, req, res);
	}
}
