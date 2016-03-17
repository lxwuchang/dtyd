/**
 * 
 */
package com.yundastat.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.velocity.texen.util.PropertiesUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;
import com.yundastat.framework.UserHolder;
import com.yundastat.model.User;

/**
 * @author hp
 *
 */
public class BaseAction extends ActionSupport{
	private static final long serialVersionUID = 6751858887754604218L;

	private static final String ENVFILE = "env.properties";
	
	public static final String STAT_HOME = "stathome";
	public static final String REG_HOME = "reghome";
	public static final String CREATE_REG = "createreg";
	public static final String MAIN_PAGE = "mainpage";
	public static final String IMPORT_PAGE = "importpage";
	public static final String STAT_REPORT = "statreport";
	public static final String REPORT = "report";
	public static final String FINIMPORT = "finimport";
	public static final String SET_WEIGHT = "setweight";
	public static final String UPDATE_WEIGHT="updateweight";
	public static final String SET_WEIGHT_PRICE = "setweightprice";
	public static final String UPDATE_WEIGHT_PRICE="updateweightprice";
	public static final String COMPARE_DATA = "comparedata";
	public static final String DESTINATION_INFO="destinationinfo";
	public static final String TRANSFORM_IMPORT="transformimport";
	public static final String RECEIVER_MANAGEMENT = "receivermanagement";
	public static final String MANAGER_MAIL_DETAIL="managermaildetail";
	public static final String MANAGER_MAIL_INFO = "managermailinfo";
	public static final String RECEIVER_MAIL_MONEY = "receivermailmoney";
	public static final String DESTINATION_MANAGE="destinationmanage";
	public static final String DESTINATION_DETAIL="destinationdetail";
	public static final String CREATE_RECEIVER = "createreceiver";
	public static final String TRANSFORM_CATEGORY="transformcategory";
	public static final String UPDATE_TRANSFORM_CATEGORY="updatetransformcategory";
	public static final String MANAGER_DETAIL = "managerdetail";
	public static final String SEARCH_MAIL = "searchmail";
	public static final String SEARCH_ONLINE_MAILINFO="searchonlinemailinfo";
	public static final String SEARCH_ONLINE_MANAGERINFO="searchonlinemanagerinfo";
	public static final String REPORT_MANAGEMENT = "reportmanagement";
	public static final String SHOW_SEARCH_ONLINE_MAIL_INFO = "showsearchonlinemailinfo";	
	public static final String SHOW_SEARCH_ONLINE_MANAGER_INFO = "showsearchonlinemanagerinfo";
	public static final String ONLINE_MAILINFO="onlinemailinfo";
	public static final String UPDATE_PASSWORD="updatepassword";
	public static final String REPORT_DETAIL = "reportdetail";
	public static final String REPORT_DETAIL_WHOLE = "reportdetailwhole";
	public static final String CREATE_MAIL = "createmail";
	public static final String SET_TRANSFOMR_PRICE = "settransformprice";
	public static final String UPDATE_TRANSFOMR_PRICE="updatetransformprice";
	public static final String CREATE_TRANSFORM_PRICE = "createtransformprice";
	public static final String TRANSFORM_PRICE = "transformprice";
	public static final String MAIL_DETAIL = "maildetail";
	public static final String LIGHT_TEST = "lighttest";
	public static final String LIGHT_TEST2 = "lighttest2";
	
	
	public static final String SET_PRICE = "setprice";
	
	protected Map page = new HashMap();
	
	protected Map errorMessage = new HashMap();
	
	public static final String PAGE_ERROR = "pageError"; 
	
	protected List resultList;
	
	protected HttpServletRequest req;

	protected User user = UserHolder.getUser();
	
	protected String message;
	
	protected String backUrl;
	public static final String appUploadPrefix = "user/";
	
	
	
	/**
	 * 
	 */
	private Properties env;

	public void setEnv(Properties env) {
		this.env = env;
	}

	public Properties getEnv() {
		return env;
	}
	
	public String getEnv(String key) {
		return env.getProperty(key);
	}

	public BaseAction() {
		super();	
		this.env = new  PropertiesUtil().load(ENVFILE);
		this.setBackUrl(env.getProperty("backUrl"));
	
	}

	/**
	 * 
	 * @param anErrorInputId  x
	 * @param anErrorMessage  
	 */
	@SuppressWarnings("unchecked")
	protected void addErrorMessage(String anErrorInputId, String anErrorMessage) {
		if (errorMessage == null)
			errorMessage = new HashMap();
		errorMessage.put(anErrorInputId,anErrorMessage);
	}
	
	protected boolean hasErrorMessage(){
		return (errorMessage != null) ? true:false;
	}
	
	
	public boolean getHasErrorMessages(){
	   return (errorMessage == null) ? false : errorMessage.size() > 0;
	}

	public Map getErrorMessage() {
		return errorMessage;
	}

	public Map getPage() {
		return page;
	}

	public List getResultList() {
		return resultList;
	}

	
	public String getMessage() {
		return message;
	}

	 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public HttpServletRequest getReq() {
		return ServletActionContext.getRequest();
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public void setErrorMessage(Map errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPage(Map page) {
		this.page = page;
	}
	
	 

}
