/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class PriceDetailSmy {
	
	private Date bsndate;
	private Date statdate;
	private int num;
	private double price;
	private String pricetype;
	private String subpricetype;
	private String manager;
	private Date createtime;
	private String creator;
	private int mon;
	
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public int getMon() {
		return mon;
	}
	public void setMon(int mon) {
		this.mon = mon;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public Date getBsndate() {
		return bsndate;
	}
	public void setBsndate(Date bsndate) {
		this.bsndate = bsndate;
	}
	public Date getStatdate() {
		return statdate;
	}
	public void setStatdate(Date statdate) {
		this.statdate = statdate;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num =  num;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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

}
