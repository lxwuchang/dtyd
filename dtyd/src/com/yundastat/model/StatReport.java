/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class StatReport extends PageParameter{
	private int statreportid;
	private String mailid;
	private double transformprice;
	private double operateprice;
	private double weightprice;
	private double wholeprice;
	private double buzufei;
	private String manager;
	private String destinationcity;
	private double weight;
	private Date sendtime;
	private String isdeleted;
	private String state;
	private Date createtime;
	private double scanfei;
	private double chujianfei;
	private int mon;
	
	
	public int getMon() {
		return mon;
	}
	public void setMon(int mon) {
		this.mon = mon;
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
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
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
	public int getStatreportid() {
		return statreportid;
	}
	public void setStatreportid(int statreportid) {
		this.statreportid = statreportid;
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
