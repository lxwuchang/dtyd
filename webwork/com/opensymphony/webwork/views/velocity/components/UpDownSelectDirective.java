/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.UpDownSelect;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date: 2006-03-15 17:35:38 +0100 (Wed, 15 Mar 2006) $ $Id: UpDownSelectDirective.java 2416 2006-03-15 16:35:38Z tmjee $
 */
public class UpDownSelectDirective extends AbstractDirective {

	public String getBeanName() {
		return "updownselect";
	}

	protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new UpDownSelect(stack, req, res);
	}
}
