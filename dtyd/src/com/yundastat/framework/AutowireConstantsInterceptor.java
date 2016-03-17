/**
* $Id: AutowireConstantsInterceptor.java,v 1.1 2012/05/17 22:50:07 wuchang Exp $
*
*/
package com.yundastat.framework;


import javax.servlet.http.HttpServletRequest;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;
import com.yundastat.util.StringUtil;

@SuppressWarnings("serial")
public class AutowireConstantsInterceptor extends AroundInterceptor {


	@Override
	protected void after(ActionInvocation dispatcher, String result)
			throws Exception {
	}

	@Override
	protected void before(ActionInvocation invocation) throws Exception {
		invocation.getInvocationContext().put("thisUser", UserHolder.getUser());
		
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(WebWorkStatics.HTTP_REQUEST);		
		StringBuffer url = request.getRequestURL();
		String queryString = request.getQueryString();
		if (!StringUtil.isBlank(queryString)) {
			url.append('?');
			url.append(queryString);
		}
		
		invocation.getInvocationContext().put("currentUrl", url);
	}

}

