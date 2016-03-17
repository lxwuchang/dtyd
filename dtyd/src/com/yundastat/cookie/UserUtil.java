/**
 * 
 */
package com.yundastat.cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yundastat.model.User;



/**
 * @author hp
 *
 */
public class UserUtil {

	/* (non-Javadoc)
	 * @see com.wuchang.common.cookie.IUserUtil#isSignIn(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public boolean isSignIn(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		CookieManager cookieManager = new CookieManager(request,response);
		String username = cookieManager.getCookieValue(UserConstant.USER_COOKIE_LOGIN_ID, "");
		
		return username != null;
	
	}

	
	/* (non-Javadoc)
	 * @see com.wuchang.common.cookie.IUserUtil#signOut(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void signOut(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
//		CookieManager cookieManager = new CookieManager(request,response);
//		cookieManager.addCookie(UserConstant.USER_COOKIE_LOGIN_ID, null, true);
       CookieManagerBase cookieManager = new CookieManagerBase(request,response,CookieManagerBase.USER_DOMAIN,CookieManagerBase.USER_PATH);
	
		cookieManager.setTempCookie(CookieManagerBase.USER_ID,null);
		cookieManager.removeTempCookie(CookieManagerBase.USER_ID);
		cookieManager.saveTempCookie();
		
	}

	public void userSignIn(User user, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		
		CookieManagerBase cookieManager = new CookieManagerBase(request,response,CookieManagerBase.USER_DOMAIN,CookieManagerBase.USER_PATH);
		
		cookieManager.setTempCookie(CookieManagerBase.USER_ID,new Integer(user.getUserid()).toString());
		//cookieManager.setTempCookie(CookieManagerBase.LOGIN_ID,user.getLoginId());
		cookieManager.setTempCookie(CookieManagerBase.USER_NAME,user.getUsername());
		cookieManager.setTempCookie(CookieManagerBase.PASSWORD,user.getPassword());
	
		//cookieManager.setTempCookie(CookieManagerBase.ROLE,user.getRole());
		cookieManager.saveTempCookie();

		
	}

	

}
