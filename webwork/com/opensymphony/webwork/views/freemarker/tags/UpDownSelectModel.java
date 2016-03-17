/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.views.freemarker.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.UpDownSelect;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date: 2006-02-01 17:30:16 +0100 (Wed, 01 Feb 2006) $ $Id: UpDownSelectModel.java 2055 2006-02-01 16:30:16Z tmjee $
 */
public class UpDownSelectModel extends TagModel {

	public UpDownSelectModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	protected Component getBean() {
		return new UpDownSelect(stack, req, res);
	}

}
