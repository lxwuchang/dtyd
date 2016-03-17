/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class FankuanOutcome extends PageParameter {

	private Integer outcomeid 	     ;
	private Date bsndate 	       ;
	private String managerid 	   ;
	private String managername;
	private double fankuan   ;
 
	private Integer year 		         ;
	private Integer month 		       ;
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
	public double getFankuan() {
		return fankuan;
	}
	public void setFankuan(double fankuan) {
		this.fankuan = fankuan;
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
