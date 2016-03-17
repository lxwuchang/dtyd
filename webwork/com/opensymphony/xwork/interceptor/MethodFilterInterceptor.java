/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */

package com.opensymphony.xwork.interceptor;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.TextParseUtil;


/**
 * <!-- START SNIPPET: javadoc -->
 * 
 * An abstract <code>Interceptor</code> that is applied to selectively according
 * to specified included/excluded method lists.
 * 
 * <p/>
 * 
 * Setable parameters are as follows:
 * 
 * <ul>
 * 		<li>excludeMethods - methods name to be excluded</li>
 * 		<li>includeMethods - methods name to be included</li>
 * </ul>
 * 
 * <p/>
 * 
 * <b>NOTE:</b> If method name are available in both includeMethods and 
 * excludeMethods, it will still be considered as an included method. In short
 * includeMethods takes precedence over excludeMethods.
 * 
 * <p/>
 * 
 * Interceptors that extends this capability would be :-
 * 
 * <ul>
 *    <li>TokenInterceptor</li>
 *    <li>TokenSessionStoreInterceptor</li>
 *    <li>DefaultWorkflowInterceptor</li>
 *    <li>ValidationInterceptor</li>
 * </ul>
 * 
 * <!-- END SNIPPET: javadoc -->
 * 
 * @author <a href='mailto:the_mindstorm[at]evolva[dot]ro'>Alexandru Popescu</a>
 * @author Rainer Hermanns
 * 
 * @see com.opensymphony.webwork.interceptor.TokenInterceptor
 * @see com.opensymphony.webwork.interceptor.TokenSessionStoreInterceptor
 * @see com.opensymphony.xwork.interceptor.DefaultWorkflowInterceptor
 * @see com.opensymphony.xwork.validator.ValidationInterceptor
 * 
 * @version $Date: 2006-05-15 09:42:45 +0200 (Mo, 15 Mai 2006) $ $Id: MethodFilterInterceptor.java 1017 2006-05-15 07:42:45Z tmjee $
 */
public abstract class MethodFilterInterceptor implements Interceptor {
    protected transient Log log = LogFactory.getLog(getClass());
    
    protected Set excludeMethods = Collections.EMPTY_SET;
    protected Set includeMethods = Collections.EMPTY_SET;

    public void setExcludeMethods(String excludeMethods) {
        this.excludeMethods = TextParseUtil.commaDelimitedStringToSet(excludeMethods);
    }
    
    public Set getExcludeMethodsSet() {
    	return excludeMethods;
    }

    public void setIncludeMethods(String includeMethods) {
        this.includeMethods = TextParseUtil.commaDelimitedStringToSet(includeMethods);
    }
    
    public Set getIncludeMethodsSet() {
    	return includeMethods;
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        if (applyInterceptor(invocation)) {
            return doIntercept(invocation);
        } 
        return invocation.invoke();
    }

    protected boolean applyInterceptor(ActionInvocation invocation) {
        String method = invocation.getProxy().getMethod();
        // ValidationInterceptor
        boolean applyMethod = MethodFilterInterceptorUtil.applyMethod(excludeMethods, includeMethods, method);
        if (log.isDebugEnabled()) {
        	if (!applyMethod) {
        		log.debug("Skipping Interceptor... Method [" + method + "] found in exclude list.");
        	}
        }
        return applyMethod;
    }
    
    /**
     * Subclasses must override to implement the interceptor logic.
     * 
     * @param invocation the action invocation
     * @return the result of invocation
     * @throws Exception
     */
    protected abstract String doIntercept(ActionInvocation invocation) throws Exception;
    
    public void destroy() {
    }

    public void init() {
    }
}
