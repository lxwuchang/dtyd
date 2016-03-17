/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class WeightPriceRule {
	
	private int weightpriceruleid;
	private String weightpricerulecontent;
	private String weightpricename;
	private double price;
	private double scanfei;
	private double chujianfei;
	private String isactive;
	private Date createtime;
	private Date effdate;
	private Date enddate;
	private String isdeleted;
	private double firstweight;
	
	
	public double getFirstweight() {
		return firstweight;
	}
	public void setFirstweight(double firstweight) {
		this.firstweight = firstweight;
	}
	public double getScanfei() {
		return scanfei;
	}
	public void setScanfei(double scanfei) {
		this.scanfei = scanfei;
	}
	public double getChujianfei() {
		return chujianfei;
	}
	public void setChujianfei(double chujianfei) {
		this.chujianfei = chujianfei;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getWeightpricename() {
		return weightpricename;
	}
	public void setWeightpricename(String weightpricename) {
		this.weightpricename = weightpricename;
	}
	public Date getEffdate() {
		return effdate;
	}
	public void setEffdate(Date effdate) {
		this.effdate = effdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getWeightpriceruleid() {
		return weightpriceruleid;
	}
	public void setWeightpriceruleid(int weightpriceruleid) {
		this.weightpriceruleid = weightpriceruleid;
	}
	public String getWeightpricerulecontent() {
		return weightpricerulecontent;
	}
	public void setWeightpricerulecontent(String weightpricerulecontent) {
		this.weightpricerulecontent = weightpricerulecontent;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	

}
