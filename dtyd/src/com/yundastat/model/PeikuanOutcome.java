/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class PeikuanOutcome  extends PageParameter{

	private Integer outcomeid 	     ;
	private Date bsndate 	       ;
	private String managerid 	   ;
	private String managername;
	private double yingpeiprice   ;
    private double realpeiprice  ;
	private Integer year 		         ;
	private Integer month 		       ;
	private String memo;
	private String dest;
	private String bsndatestr;
	private String dealstate;
	private String  reason;
	private String mailid;
	private int num;
	
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Integer getOutcomeid() {
		return outcomeid;
	}
	public void setOutcomeid(Integer outcomeid) {
		this.outcomeid = outcomeid;
	}
	public Date getBsndate() {
		return bsndate;
	}
	public void setBsndate(Date bsndate) {
		this.bsndate = bsndate;
	}
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public double getYingpeiprice() {
		return yingpeiprice;
	}
	public void setYingpeiprice(double yingpeiprice) {
		this.yingpeiprice = yingpeiprice;
	}
	public double getRealpeiprice() {
		return realpeiprice;
	}
	public void setRealpeiprice(double realpeiprice) {
		this.realpeiprice = realpeiprice;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getBsndatestr() {
		return bsndatestr;
	}
	public void setBsndatestr(String bsndatestr) {
		this.bsndatestr = bsndatestr;
	}
	public String getDealstate() {
		return dealstate;
	}
	public void setDealstate(String dealstate) {
		this.dealstate = dealstate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	
	 
}
