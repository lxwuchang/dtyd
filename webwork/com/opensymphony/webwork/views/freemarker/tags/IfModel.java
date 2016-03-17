/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.freemarker.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.If;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date: 2006-07-22 09:13:41 +0200 (Sat, 22 Jul 2006) $ $Id: IfModel.java 2664 2006-07-22 07:13:41Z tmjee $
 */
public class IfModel extends TagModel {

	public IfModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}
	
	protected Component getBean() {
		return new If(stack);
	}
}
