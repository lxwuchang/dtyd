/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class ZhipaoOutcome  extends PageParameter{

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
	private double weight;
	private double jipaoweight;
	private double totalweight;
	private double shouldincome;
	private double tuikuan;
	private double realincome;
	
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getJipaoweight() {
		return jipaoweight;
	}
	public void setJipaoweight(double jipaoweight) {
		this.jipaoweight = jipaoweight;
	}
	public double getTotalweight() {
		return totalweight;
	}
	public void setTotalweight(double totalweight) {
		this.totalweight = totalweight;
	}
	public double getShouldincome() {
		return shouldincome;
	}
	public void setShouldincome(double shouldincome) {
		this.shouldincome = shouldincome;
	}
	public double getTuikuan() {
		return tuikuan;
	}
	public void setTuikuan(double tuikuan) {
		this.tuikuan = tuikuan;
	}
	public double getRealincome() {
		return realincome;
	}
	public void setRealincome(double realincome) {
		this.realincome = realincome;
	}
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
