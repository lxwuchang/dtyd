/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.xwork.interceptor;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Validateable;
import com.opensymphony.xwork.ValidationAware;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <!-- START SNIPPET: description -->
 *
 * An interceptor that does some basic validation workflow before allowing the interceptor chain to continue.
 *
 * <p/>This interceptor does nothing if the name of the method being invoked is specified in the <b>excludeMethods</b>
 * parameter. <b>excludeMethods</b> accepts a comma-delimited list of method names. For example, requests to
 * <b>foo!input.action</b> and <b>foo!back.action</b> will be skipped by this interceptor if you set the
 * <b>excludeMethods</b> parameter to "input, back".
 *
 * <p/>The order of execution in the workflow is:
 *
 * <ol>
 *
 * <li>If the action being executed implements {@link Validateable}, the action's {@link Validateable#validate()
 * validate} method is called.</li>
 *
 * <li>Next, if the action implements {@link ValidationAware}, the action's {@link ValidationAware#hasErrors()
 * hasErrors} method is called. If this method returns true, this interceptor stops the chain from continuing and
 * immediately returns {@link Action#INPUT}</li>
 *
 * </ol>
 *
 * <p/> Note: if the action doesn't implement either interface, this interceptor effectively does nothing. This
 * interceptor is often used with the <b>validation</b> interceptor. However, it does not have to be, especially if you
 * wish to write all your validation rules by hand in the validate() method rather than in XML files.
 *
 * <p/>
 *
 * <b>NOTE:</b> As this method extends off MethodFilterInterceptor, it is capable of
 * deciding if it is applicable only to selective methods in the action class. See
 * <code>MethodFilterInterceptor</code> for more info.
 * 
 * <p/><b>Update:</b> Added logic to execute a validate{MethodName} rather than a general validate method. 
 * This allows us to run some validation logic based on the method name we specify in the 
 * ActionProxy. For example, you can specify a validateInput() method, or even a validateDoInput() 
 * method that will be run before the invocation of the input method.
 * 
 * <!-- END SNIPPET: description -->
 *
 * <p/> <u>Interceptor parameters:</u>
 *
 * <!-- START SNIPPET: parameters -->
 *
 * <ul>
 *
 * <li>alwaysInvokeValidate - Default to true. If true validate() method will always
 * be invoked, otherwise it will not.</li>
 *
 * </ul>
 *
 * <!-- END SNIPPET: parameters -->
 *
 * <p/> <u>Extending the interceptor:</u>
 *
 * <p/>
 *
 * <!-- START SNIPPET: extending -->
 *
 * There are no known extension points for this interceptor.
 *
 * <!-- END SNIPPET: extending -->
 *
 * <p/> <u>Example code:</u>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt;action name="someAction" class="com.examples.SomeAction"&gt;
 *     &lt;interceptor-ref name="params"/&gt;
 *     &lt;interceptor-ref name="validation"/&gt;
 *     &lt;interceptor-ref name="workflow"/&gt;
 *     &lt;result name="success"&gt;good_result.ftl&lt;/result&gt;
 * &lt;/action&gt;
 * 
 * &lt;-- In this case myMethod of the action class will not pass through 
 *        the workflow process --&gt;
 * &lt;action name="someAction" class="com.examples.SomeAction"&gt;
 *     &lt;interceptor-ref name="params"/&gt;
 *     &lt;interceptor-ref name="validation"/&gt;
 *     &lt;interceptor-ref name="workflow"&gt;
 *         &lt;param name="excludeMethods"&gt;myMethod&lt;/param&gt;
 *     &lt;/interceptor-ref name="workflow"&gt;
 *     &lt;result name="success"&gt;good_result.ftl&lt;/result&gt;
 * &lt;/action&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Jason Carreira
 * @author Rainer Hermanns
 * @author <a href='mailto:the_mindstorm[at]evolva[dot]ro'>Alexandru Popescu</a>
 * @author Philip Luppens
 * @author tm_jee
 * 
 * @version $Date: 2006-09-14 18:41:07 +0200 (Do, 14 Sep 2006) $ $Id: DefaultWorkflowInterceptor.java 1135 2006-09-14 16:41:07Z tm_jee $
 */
public class DefaultWorkflowInterceptor extends MethodFilterInterceptor {
	
	private static final long serialVersionUID = 7563014655616490865L;

	private static final Log _log = LogFactory.getLog(DefaultWorkflowInterceptor.class);
	
	private final static String VALIDATE_PREFIX = "validate";
	private final static String ALT_VALIDATE_PREFIX = "validateDo";
	
	private boolean alwaysInvokeValidate = true;
	
	
	public void setAlwaysInvokeValidate(String alwaysInvokeValidate) {
        this.alwaysInvokeValidate = Boolean.valueOf(alwaysInvokeValidate).booleanValue();
	}
	
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        
        
        if (action instanceof Validateable) {
        	// keep exception that might occured in validateXXX or validateDoXXX
        	Exception exception = null; 
        	
            Validateable validateable = (Validateable) action;
            if (_log.isDebugEnabled()) {
            	_log.debug("Invoking validate() on action "+validateable);
            }
            
            try {
            	PrefixMethodInvocationUtil.invokePrefixMethod(
            			invocation, 
            			new String[] { VALIDATE_PREFIX, ALT_VALIDATE_PREFIX });
            }
            catch(Exception e) {
            	// If any exception occurred while doing reflection, we want 
            	// validate() to be executed
            	_log.warn("an exception occured while executing the prefix method", e);
            	exception = e;
            }
            
            
            if (alwaysInvokeValidate) {
            	validateable.validate();
            }
            
            if (exception != null) { 
            	// rethrow if something is wrong while doing validateXXX / validateDoXXX 
            	throw exception;
            }
        }
        

        if (action instanceof ValidationAware) {
            ValidationAware validationAwareAction = (ValidationAware) action;

            if (validationAwareAction.hasErrors()) {
            	if (_log.isDebugEnabled()) {
            		_log.debug("Errors on action "+validationAwareAction+", returning result name 'input'");
            	}
                return Action.INPUT;
            }
        }

        return invocation.invoke();
    }
}
