/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author KJX843
 *
 */
public class MailInfo {
	private String linenum;
	private String mailid;
	private Date sendtime;
	private Date scandate;
	private String sendgroup;
	private String manager;
	private String destinationgroup;
	private String subcompany;
	private String classification;
	private String transformationtype;
	private double weight;
	private String lanjianfei;
	private String paisongfei;
	private String wholeprice;
	private Date createtime;
	private String isdeleted;
	private double transformweight;
	private int mon;
	
	
	//for create mail
	
	public int getMon() {
		return mon;
	}
	public void setMon(int mon) {
		this.mon = mon;
	}
	private String mailtype;
	private String scantime;
	
	
	public String getMailtype() {
		return mailtype;
	}
	public void setMailtype(String mailtype) {
		this.mailtype = mailtype;
	}
	public String getScantime() {
		return scantime;
	}
	public void setScantime(String scantime) {
		this.scantime = scantime;
	}
	private String state;
	
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
	public double getTransformweight() {
		return transformweight;
	}
	public void setTransformweight(double transformweight) {
		this.transformweight = transformweight;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getLinenum() {
		return linenum;
	}
	public void setLinenum(String linenum) {
		this.linenum = linenum;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public String getSendgroup() {
		return sendgroup;
	}
	public void setSendgroup(String sendgroup) {
		this.sendgroup = sendgroup;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	public String getDestinationgroup() {
		return destinationgroup;
	}
	public void setDestinationgroup(String destinationgroup) {
		this.destinationgroup = destinationgroup;
	}
	public String getSubcompany() {
		return subcompany;
	}
	public void setSubcompany(String subcompany) {
		this.subcompany = subcompany;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getTransformationtype() {
		return transformationtype;
	}
	public void setTransformationtype(String transformationtype) {
		this.transformationtype = transformationtype;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getLanjianfei() {
		return lanjianfei;
	}
	public void setLanjianfei(String lanjianfei) {
		this.lanjianfei = lanjianfei;
	}
	public String getPaisongfei() {
		return paisongfei;
	}
	public void setPaisongfei(String paisongfei) {
		this.paisongfei = paisongfei;
	}
	public String getWholeprice() {
		return wholeprice;
	}
	public void setWholeprice(String wholeprice) {
		this.wholeprice = wholeprice;
	}
	

}
