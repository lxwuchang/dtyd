/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author hbj
 * @date 11 May 2009
 */
public class User {
	private int userid;
	private String password;
	private String loginid;
	private String isdeleted;
	private String company;
	private String username;
	private String role;
	private int effdays;
	private String isdeadline;
	private String deadline;
	private Date lastlogintime;
	

	
	public Date getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	public int getEffdays() {
		return effdays;
	}
	public void setEffdays(int effdays) {
		this.effdays = effdays;
	}
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUsername() {
		return username;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public void setUsername(String userName) {
		this.username = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static void main(String [] args){
		
		
//		String dd="0,0.5;0.5,1;1;";
//		String[] str=dd.split(";");
//		System.out.println(str[2]);
		
		double s=4.28;
		double d=1;
		double ss=1;
		
		int f=(int)((s-d)/ss);
		double re=0;
		if(s>ss*f)
			re=ss*f+ss;
		else re=ss*f;
		System.out.println(re);
		//System.out.println(Math.round(f));
		//System.out.println(Math.floor(f));
	}
	
	
}
