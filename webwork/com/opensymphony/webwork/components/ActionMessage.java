/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Render action messages if they exists, specific rendering layout depends on the 
 * theme itself.
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 *    &lt;ww:actionmessage /&gt;
 *    &lt;ww:form .... &gt;
 *       ....
 *    &lt;/ww:form&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author tm_jee
 * @version $Date: 2006-01-07 21:43:28 +0100 (Sat, 07 Jan 2006) $ $Id: ActionMessage.java 1924 2006-01-07 20:43:28Z plightbo $
 * @ww.tag name="actionmessage" tld-body-content="empty" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.ActionMessageTag"
 * description="Render action messages if they exists"
 * @since 2.2
 */
public class ActionMessage extends UIBean {

    private static final String TEMPLATE = "actionmessage";

    public ActionMessage(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}
