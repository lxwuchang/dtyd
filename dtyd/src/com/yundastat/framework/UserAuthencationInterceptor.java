/**
 * 
 */
package com.yundastat.framework;


import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.yundastat.model.User;

/**
 * @author hp
 *
 */
public class UserAuthencationInterceptor implements Interceptor {

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork.interceptor.Interceptor#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork.interceptor.Interceptor#init()
	 */
	public void init() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		
		User user= UserHolder.getUser();
		if(user==null){
			
			System.out.println("Useris not logined!");
			return "input";
		}
		return invocation.invoke();
	}

}
