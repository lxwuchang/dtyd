/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class Income extends PageParameter{
	
	private Integer incomeid;
	private Date bsndate    ;
	private String managerid;
	private String fukuanman;
	private String pricetype     ;//
	private String fukuantype;
	private int num     ;
	private double price    ;
	private String memo     ;
	private Date createtime ;
	private Integer year    ;
	private Integer month   ;
	private String bsndatestr;
	private String managername;
    private String imgid;
    private double mprice;
    private double zprice;
    private double qprice;
 
    
    
    
	public double getMprice() {
		return mprice;
	}
	public void setMprice(double mprice) {
		this.mprice = mprice;
	}
	public double getZprice() {
		return zprice;
	}
	public void setZprice(double zprice) {
		this.zprice = zprice;
	}
	public double getQprice() {
		return qprice;
	}
	public void setQprice(double qprice) {
		this.qprice = qprice;
	}
	public Integer getIncomeid() {
		return incomeid;
	}
	public void setIncomeid(Integer incomeid) {
		this.incomeid = incomeid;
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
	public String getFukuanman() {
		return fukuanman;
	}
	public void setFukuanman(String fukuanman) {
		this.fukuanman = fukuanman;
	}
	public String getPricetype() {
		return pricetype;
	}
	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}
	public String getFukuantype() {
		return fukuantype;
	}
	public void setFukuantype(String fukuantype) {
		this.fukuantype = fukuantype;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public String getImgid() {
		return imgid;
	}
	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	
	 
	
	
	

}
