/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Administrator
 * @date 10 Jan 2011
 */
public class UserInfo {
	private int userId;
	private String userName;
	private Date date;
	private String hobby;
	private String keyword;
	private String ipadid;
	private String isdeadline;
	private String  deadline;
	
	
	
	
	public String getIsdeadline() {
		return isdeadline;
	}
	public void setIsdeadline(String isdeadline) {
		this.isdeadline = isdeadline;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getIpadid() {
		return ipadid;
	}
	public void setIpadid(String ipadid) {
		this.ipadid = ipadid;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
