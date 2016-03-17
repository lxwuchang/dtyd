/**
 * 
 */
package com.yundastat.service;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.yundastat.framework.UserHolder;
import com.yundastat.model.User;
import com.yundastat.model.UserInfo;

/**
 * @author hp
 * 
 */
public class UserServiceImpl {
	private final static Logger logger = Logger
			.getLogger(UserServiceImpl.class);
	//private UserUtil userUtilService;
	private UserService userService;

	 private final String basePath="C:/Program Files/Apache Software Foundation/Tomcat 5.5/webapps/jsp-examples/hbj/";
	public String getBasePath() { 
		return basePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wuchang.userService.IUserService#doLoginIn(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean doLoginIn(final User u,
			ActionContext ctx) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	
		 List<User> user = userService.queryUserByLoginid(u.getLoginid());

		if (user != null &&user.size()==1&& user.get(0).getPassword().equals(u.getPassword())) {

			HttpServletRequest req = (HttpServletRequest) ctx
					.get(WebWorkStatics.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) ctx
					.get(WebWorkStatics.HTTP_RESPONSE);
            
			req.getSession().setAttribute("user",user.get(0));
			UserHolder.setUser(user.get(0)); 
 	
			return true;
		}
		return false;
	}

	

	/***************************************************************************
	 * �ж������Ƿ���ȷ
	 * 
	 */

	public boolean validatePassword(String password) {

//		String userName = UserHolder.getUser().getUserName();
//
//		System.out.println("IN the updatepassword:I can get the loginId"
//				+ userName);
//
//		User user = userService.queryUserByUserName(userName);
//
//		if (user.getPassword().equals(password))
//			return true;
		return false;

	}

	/***************************************************************************
	 * �û�ע��
	 * 
	 **************************************************************************/

	public void doLogout(int userId, ActionContext ctx) {

		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		//userUtilService.signOut(request, response);
		
		request.getSession().setAttribute("user",null);
	}

	public int updateUser(User user){
		
		return userService.updateUser(user);
	}
	
	public int registerUser(User user) {
		return userService.registerUser(user);
	}
	
	public int deleteUser(User user){
		return userService.deleteUser(user);
	}
	public User getUser(int userId){
		
		return userService.queryUserByUserId(userId);
	}
	
	public  List<User> getUserByLoginid(String   id){
		
		return  userService.queryUserByLoginid(id);
	}
	
	
   public User getUserByDate(int  realdate){
		
		return userService.queryUserByDate(realdate);
	}
	

	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	 
	 
}
