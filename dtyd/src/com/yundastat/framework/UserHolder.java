/**
 * 
 */
package com.yundastat.framework;

import java.util.HashMap;
import java.util.Map;

import com.yundastat.model.User;


/**
 * @author hp
 *
 */
public class UserHolder {
private static final String USER = "USER";
private static final String FOLDER = "FOLDER";
	private static final ThreadLocal context = new ThreadLocal() {
		@Override
		protected Object initialValue() {
			return new HashMap();
		}
	};
	
	public static Map getContext() {
		return (Map) context.get();
	}
	
	public static void clear() {
		getContext().clear();
	}
	
	public static User getUser() {
		return (User) getContext().get(USER);
	}
	
	public static void setUser(User user) {
		getContext().put(USER, user);
	}
	

}
