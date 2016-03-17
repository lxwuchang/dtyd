/**
 * 
 */
package com.yundastat.cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hp
 *
 */
public class CookieManagerBase extends CookieManagerBasement {
private static final String COOKIE_USER_TMP = "COOKIE_USER_TMP";
	
	public static final String USER_ID = "USER_ID";
	public static final String LOGIN_ID = "LOGIN_ID";
	public static final String USER_NAME = "USER_NAME";
	public static final String PASSWORD = "PASSWORD";
	public static final String KEYWORD = "KEYWORD";
	
	public static final String ROLE = "ROLE";
	public static final String USER_DOMAIN = ".wuchang.com";
	public static final String USER_PATH = "/";
	
	public CookieManagerBase(HttpServletRequest request, HttpServletResponse response, 
			String domain, String path) {
		
		super(request, response, domain, path);
	}

	public CookieManagerBase(HttpServletRequest request) {
		this(request, null, null, null);
	}
	
	
	/**
	 * �����ʱcookie�е�һ����ֵ
	 */
	public String getTempCookie(String key) {
		return getTempCookie(key, null);
	}

	/**
	 * �����ʱcookie�е�һ����ֵ����Ϊ�գ��򷵻�defaultValue
	 */
	public String getTempCookie(String key, String defaultValue) {
		return getValue(COOKIE_USER_TMP, key, defaultValue);
	}
	
	/**
	 * ������ʱcookie��һ����ֵ����valueΪ�գ����൱��removeTempCookie
	 */
	public void setTempCookie(String key, String value) {
		setValue(COOKIE_USER_TMP, key, value);
	}
	
	/**
	 * ɾ��һ����ʱcookie��
	 */
	public void removeTempCookie(String key) {
		setValue(COOKIE_USER_TMP, key, null);
	}
	
	/**
	 * ������ʱcookie�����е������д��response
	 */
	public void saveTempCookie() {
		save(COOKIE_USER_TMP, TEMP_COOKIE_AGE);
	}


}
