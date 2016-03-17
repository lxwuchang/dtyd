/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class CompanyIncome {
	
	private Integer dmid           ;
	private Date bsndate        ;
	private String type           ;
	private String manager        ;
	private Integer num            ;
	private double price          ;
	private Date createtime     ;
	private double transformprice ;
	private double mailprice      ;
	private String  memo          ;
	private String pricetype      ;
	
	private String manager1;
	private String pricetype1;
	private String bsndatestr;
	private Integer year;
	
	private Integer month;
	private double profit;
	
	
	
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
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
	public String getBsndatestr() {
		return bsndatestr;
	}
	public void setBsndatestr(String bsndatestr) {
		this.bsndatestr = bsndatestr;
	}
	public String getManager1() {
		return manager1;
	}
	public void setManager1(String manager1) {
		this.manager1 = manager1;
	}
	public String getPricetype1() {
		return pricetype1;
	}
	public void setPricetype1(String pricetype1) {
		this.pricetype1 = pricetype1;
	}
	public Integer getDmid() {
		return dmid;
	}
	public void setDmid(Integer dmid) {
		this.dmid = dmid;
	}
	public Date getBsndate() {
		return bsndate;
	}
	public void setBsndate(Date bsndate) {
		this.bsndate = bsndate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public double getTransformprice() {
		return transformprice;
	}
	public void setTransformprice(double transformprice) {
		this.transformprice = transformprice;
	}
	public double getMailprice() {
		return mailprice;
	}
	public void setMailprice(double mailprice) {
		this.mailprice = mailprice;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPricetype() {
		return pricetype;
	}
	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}


}
