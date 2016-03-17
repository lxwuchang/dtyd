/**
 * 
 */
package com.yundastat.framework;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yundastat.model.User;



/**
 * @author hp
 *
 */
public class SecurityContextFilter implements  Filter {

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	
//	/**
//	 
//	 * 
//	 * **/
//	public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException, ServletException {
//		// TODO Auto-generated method stub
//	   
//		HttpServletRequest request = (HttpServletRequest)req;
//		ServletResponse response = (ServletResponse)rsp;
//
//        HttpSession session = request.getSession();
//        System.out.println("Session id in secure :"+session.getId());
//		CookieManagerBase cookieManager = new CookieManagerBase(request,null,CookieManagerBase.USER_DOMAIN,CookieManagerBase.USER_PATH);
//		
//		try{
//			//��cookie �еõ�,���cookie ֵ�Ǿ�����ܵ�
//		String user_id= cookieManager.getTempCookie(CookieManagerBase.USER_ID);	
//	    System.out.println("user_id:"+user_id);
//		String loginId=cookieManager.getTempCookie(CookieManagerBase.LOGIN_ID);
//		String userName= cookieManager.getTempCookie(CookieManagerBase.USER_NAME);
//	   String  password=cookieManager.getTempCookie(CookieManagerBase.PASSWORD);
//	   String role=cookieManager.getTempCookie(CookieManagerBase.ROLE);
//			if(!StringUtil.isEmpty(user_id)){
//				User user= new User();
//				
//				int userId=Integer.parseInt(user_id);
//				user.setUserId(userId);
//			    user.setLoginId(loginId);
//			    
//			    //��Ӧ��ҲҪ���û���ԭ    
//		
//			    System.out.println("userName before decode in secureFilter: "+userName);
//			   userName=new String(Base64.decode(userName),"GB2312");
//			   
//			   System.out.println("userName after decode in secureFilter: "+userName);
//			    user.setUserName(userName);
//			    user.setPassword(password);
//			    user.setRole(role);
////			    try {
////			    	System.out.println("went to the security");
////				UserHolder.setFolder(mailUserBean.getConnect(user));
////				} catch (Exception e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//				UserHolder.setUser(user);	
//			}else{
//				
//				UserHolder.clear();
//			}	
//			
//		
//		chain.doFilter(request, response);
//	} finally {
//		//UserHolder.clear();
//	
//	}
//		
//	}

	
	/**
	 * 
	 * 
	 * **/
	public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
	   
		HttpServletRequest request = (HttpServletRequest)req;
		ServletResponse response = rsp;

        HttpSession session = request.getSession();
        User u=(User) session.getAttribute("user");
			if(u!=null){
				
				UserHolder.setUser(u);	
			}else{
				
				UserHolder.clear();
			}	
			
		
		chain.doFilter(request, response);
	
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
