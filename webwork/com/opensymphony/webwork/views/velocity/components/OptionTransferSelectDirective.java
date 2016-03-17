/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.OptionTransferSelect;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author tm_jee
 * @author Rainer Hermanns
 * @version $Date: 2006-01-31 21:51:25 +0100 (Tue, 31 Jan 2006) $ $Id: OptionTransferSelectDirective.java 2049 2006-01-31 20:51:25Z rainerh $
 * @see OptionTransferSelect
 */
public class OptionTransferSelectDirective extends AbstractDirective {

	public String getBeanName() {
		return "optiontransferselect";
	}

	protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new OptionTransferSelect(stack, req, res);
	}

}
