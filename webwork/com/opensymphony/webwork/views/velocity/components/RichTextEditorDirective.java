/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.RichTextEditor;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author tm_jee
 * @version $Date: 2006-02-18 06:29:27 +0100 (Sat, 18 Feb 2006) $ $Id: RichTextEditorDirective.java 2210 2006-02-18 05:29:27Z tmjee $
 * @see RichTextEditor
 */
public class RichTextEditorDirective extends AbstractDirective {
	
	public String getBeanName() {
		return "richtexteditor";
	}

	protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new RichTextEditor(stack, req, res);
	}
}
