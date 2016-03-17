/**
 * 
 */
package com.yundastat.cookie;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author hp
 *
 */
public class CookieManager  {
		private final static Logger logger = Logger.getLogger(CookieManager.class);
		
	    private HttpServletRequest  request  = null;
	    private HttpServletResponse response = null;
	    
	    public static final String DOMAIN = "*.hbj.com";
	    public CookieManager(HttpServletRequest request, HttpServletResponse response) {
	        this.request  = request;
	        this.response = response;
	    }
		

	    /**
	     * ����������.
	     *
	     * @param hrep
	     * @param name
	     * @param val
	     * @param persist
	     */
	    public void addCookie(String name, String val, boolean persist) {
	        int age = -1;

	        if (persist) {
	            age = 99999999;
	        } else {
	            age = -1;
	        }

	        
	        System.out.println("cookie name is:"+name);
		     System.out.println("value name is:"+val);
		     System.out.println("age is:"+age);
	 
	        addCookie(name, val, age);
	    }

	    /**
	     * (non-Javadoc)
	     *
	     * @see com.alibaba.waf.AliCookie#addCookie(java.lang.String, java.lang.String, int)
	     */
	    public void addCookie(String name, String val, int age) {
	        Cookie c = new Cookie(name, val);

	        c.setMaxAge(age);
	        c.setPath("/");
	        c.setDomain(DOMAIN);
	        logger.debug("add cookie,name = " + name + ",\t val = " + val + ",\t domain = "
	                  + DOMAIN);
	        response.addCookie(c);

	    }
	    
	    /**
	     * ����������.
	     *
	     * @param hreq
	     * @param cookieName
	     * @param defaultvalue
	     *
	     * @return
	     */
	    public String getCookieValue(String cookieName, String defaultvalue) {
	        Cookie[] cookies = null;
	        
	        
	        System.out.println("The cookie name is:"+cookieName+"*****"+defaultvalue);

	        try {
	            cookies = request.getCookies();
	        } catch (IllegalArgumentException iae) {
	            logger.error("", iae);
	        }

	        if ((cookies == null) || (cookies.length == 0)) {
	            return defaultvalue;
	        }

	        for (int i = 0; i < cookies.length; i++) {
	            Cookie cookie = cookies[i];

	            if (cookieName.equals(cookie.getName())) {
	                return (cookie.getValue());
	            }
	        }

	        return (defaultvalue);
	    }
	    


		public HttpServletRequest getRequest() {
			return request;
		}

		public HttpServletResponse getResponse() {
			return response;
		}


		
	}
