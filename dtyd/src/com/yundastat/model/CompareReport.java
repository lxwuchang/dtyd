/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class CompareReport {

	private String mailid;
	private String lmanager;
	private String tmanager;

	private String ldestinationcity;
	private String transformcorp;
    
	private double lweight;
	private double tweight;
    
	private double ltransformprice;
	private double stransformprice;
	private double sstransformprice;
	private double wholeprice;
	
	private Date tscandate;
	private Date inhousedate;
	private Date lscandate;
 
	
	private Date createtime;
	private String isdeleted;
	private String state;
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public String getLmanager() {
		return lmanager;
	}
	public void setLmanager(String lmanager) {
		this.lmanager = lmanager;
	}
	public String getTmanager() {
		return tmanager;
	}
	public void setTmanager(String tmanager) {
		this.tmanager = tmanager;
	}
	public String getLdestinationcity() {
		return ldestinationcity;
	}
	public void setLdestinationcity(String ldestinationcity) {
		this.ldestinationcity = ldestinationcity;
	}
	public String getTransformcorp() {
		return transformcorp;
	}
	public void setTransformcorp(String transformcorp) {
		this.transformcorp = transformcorp;
	}
	public double getLweight() {
		return lweight;
	}
	public void setLweight(double lweight) {
		this.lweight = lweight;
	}
	public double getTweight() {
		return tweight;
	}
	public void setTweight(double tweight) {
		this.tweight = tweight;
	}
	public double getLtransformprice() {
		return ltransformprice;
	}
	public void setLtransformprice(double ltransformprice) {
		this.ltransformprice = ltransformprice;
	}
	public double getStransformprice() {
		return stransformprice;
	}
	public void setStransformprice(double stransformprice) {
		this.stransformprice = stransformprice;
	}
	public double getSstransformprice() {
		return sstransformprice;
	}
	public void setSstransformprice(double sstransformprice) {
		this.sstransformprice = sstransformprice;
	}
	public double getWholeprice() {
		return wholeprice;
	}
	public void setWholeprice(double wholeprice) {
		this.wholeprice = wholeprice;
	}
	public Date getLscandate() {
		return lscandate;
	}
	public void setLscandate(Date lscandate) {
		this.lscandate = lscandate;
	}
	public Date getInhousedate() {
		return inhousedate;
	}
	public void setInhousedate(Date inhousedate) {
		this.inhousedate = inhousedate;
	}
	public Date getTscandate() {
		return tscandate;
	}
	public void setTscandate(Date tscandate) {
		this.tscandate = tscandate;
	}
	 
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	

}
