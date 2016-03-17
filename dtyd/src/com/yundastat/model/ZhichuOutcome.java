/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class ZhichuOutcome  extends PageParameter{

	private Integer outcomeid 	     ;
	private Date bsndate 	       ;
	private String managerid 	   ;
	private String managername;
	private double price   ;
 
	private Integer year 		         ;
	private Integer month 		       ;
	private String memo;
	private String pricetype;
	private String bsndatestr;
	
	
	
	public String getBsndatestr() {
		return bsndatestr;
	}
	public void setBsndatestr(String bsndatestr) {
		this.bsndatestr = bsndatestr;
	}
	public String getPricetype() {
		return pricetype;
	}
	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
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
	 
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	 
}
