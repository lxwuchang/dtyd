package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>DateModel</code>
 *
 * @author Rainer Hermanns
 * @version $Id: DateModel.java 2274 2006-02-26 16:49:43Z rainerh $
 */
public class DateModel extends TagModel {

    public DateModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    protected Component getBean() {
        return new Date(stack);
    }
}
