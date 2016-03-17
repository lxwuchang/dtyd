/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class Report {

	private String mailid;
	private double operateprice;
	private double weightprice;
	private double wholeprice;
	private double buzufei;
	private String manager;
	private String destinationcity;
	private String destinationgroup;
	private double sourceweight;
	private double sourceprice;
	private double transformweight;
	private double transformprice;
	private double finalweight;
	private double finalprice;
	private Date scandate;
	private Date sendtime;
	private Date createtime;
	private String isdeleted;
	private String state;
	
	
	
	public String getDestinationgroup() {
		return destinationgroup;
	}
	public void setDestinationgroup(String destinationgroup) {
		this.destinationgroup = destinationgroup;
	}
	public double getSourceprice() {
		return sourceprice;
	}
	public void setSourceprice(double sourceprice) {
		this.sourceprice = sourceprice;
	}
	public double getFinalprice() {
		return finalprice;
	}
	public void setFinalprice(double finalprice) {
		this.finalprice = finalprice;
	}
	public double getFinalweight() {
		return finalweight;
	}
	public void setFinalweight(double finalweight) {
		this.finalweight = finalweight;
	}
	public double getSourceweight() {
		return sourceweight;
	}
	public void setSourceweight(double sourceweight) {
		this.sourceweight = sourceweight;
	}
	public double getTransformweight() {
		return transformweight;
	}
	public void setTransformweight(double transformweight) {
		this.transformweight = transformweight;
	}
	public Date getScandate() {
		return scandate;
	}
	public void setScandate(Date scandate) {
		this.scandate = scandate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getDestinationcity() {
		return destinationcity;
	}
	public void setDestinationcity(String destinationcity) {
		this.destinationcity = destinationcity;
	}
	 
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	public double getBuzufei() {
		return buzufei;
	}
	public void setBuzufei(double buzufei) {
		this.buzufei = buzufei;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
 
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public double getTransformprice() {
		return transformprice;
	}
	public void setTransformprice(double transformprice) {
		this.transformprice = transformprice;
	}
	public double getOperateprice() {
		return operateprice;
	}
	public void setOperateprice(double operateprice) {
		this.operateprice = operateprice;
	}
	public double getWeightprice() {
		return weightprice;
	}
	public void setWeightprice(double weightprice) {
		this.weightprice = weightprice;
	}
	public double getWholeprice() {
		return wholeprice;
	}
	public void setWholeprice(double wholeprice) {
		this.wholeprice = wholeprice;
	}
	

}
