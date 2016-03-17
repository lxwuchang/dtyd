/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.freemarker.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.OptGroup;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date: 2006-07-07 19:10:06 +0200 (Fri, 07 Jul 2006) $ $Id: OptGroupModel.java 2634 2006-07-07 17:10:06Z tmjee $
 */
public class OptGroupModel extends TagModel {
	
	public OptGroupModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	protected Component getBean() {
		return new OptGroup(stack, req, res);
	}

}
