/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class PriceDetail {
	private Date statdate         ;
	private Date bsndate          ;
	private String mailid         ;
	private String manager        ;
	private String pricetype      ;
	private String subpricetype   ;
	private double weight         ;
	private double price          ;
	private String destination    ;
	private String destprovince   ;
	private String destcity       ;
	private String receivemanager ;
	private int year;
	private String statunit;
	private String memo;
	private String creator;
	private Date createtime;
	
	
	public String getStatunit() {
		return statunit;
	}
	public void setStatunit(String statunit) {
		this.statunit = statunit;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Date getStatdate() {
		return statdate;
	}
	public void setStatdate(Date statdate) {
		this.statdate = statdate;
	}
	public Date getBsndate() {
		return bsndate;
	}
	public void setBsndate(Date bsndate) {
		this.bsndate = bsndate;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getPricetype() {
		return pricetype;
	}
	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}
	public String getSubpricetype() {
		return subpricetype;
	}
	public void setSubpricetype(String subpricetype) {
		this.subpricetype = subpricetype;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDestprovince() {
		return destprovince;
	}
	public void setDestprovince(String destprovince) {
		this.destprovince = destprovince;
	}
	public String getDestcity() {
		return destcity;
	}
	public void setDestcity(String destcity) {
		this.destcity = destcity;
	}
	public String getReceivemanager() {
		return receivemanager;
	}
	public void setReceivemanager(String receivemanager) {
		this.receivemanager = receivemanager;
	}

	

}
