/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Radio;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @see Radio
 * @author $author$
 * @version $Date: 2005-12-16 18:48:22 +0100 (Fri, 16 Dec 2005) $ $Id: RadioTag.java 1714 2005-12-16 17:48:22Z tmjee $
 */
public class RadioTag extends AbstractRequiredListTag {
    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Radio(stack, req, res);
    }
}
