/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.freemarker.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.OptionTransferSelect;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author tm_jee
 * @version $Date: 2006-01-07 16:08:43 +0100 (Sat, 07 Jan 2006) $ $Id: OptionTransferSelectModel.java 1917 2006-01-07 15:08:43Z tmjee $
 * @see OptionTransferSelect
 */
public class OptionTransferSelectModel extends TagModel {

	public OptionTransferSelectModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	protected Component getBean() {
		return new OptionTransferSelect(stack, req, res);
	}

}
