/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class MailFenpei {
	
	private Integer mailinfoid;
	private Date bsndate    ;
	private String managerid;
	private String type     ;
	private int num     ;
	private double perprice ;
	private double price    ;
	private String memo     ;
	private Date createtime ;
	private Integer year    ;
	private Integer month   ;
	private String bsndatestr;
	private String managername;
	private int cnum;
	private double cprice;
	private int pnum;
	private double pprice;
	private int dnum;
	private double dprice;
	private int rnum;
	private double rprice;
	private int fnum;
	private double fprice;
	private int yfnum;//已付数量
	private double yfprice;//已付金额
	private int wfnum;//未付数量
	private double wfprice;//未付金额

	
	
	public int getYfnum() {
		return yfnum;
	}
	public void setYfnum(int yfnum) {
		this.yfnum = yfnum;
	}
	public double getYfprice() {
		return yfprice;
	}
	public void setYfprice(double yfprice) {
		this.yfprice = yfprice;
	}
	public int getWfnum() {
		return wfnum;
	}
	public void setWfnum(int wfnum) {
		this.wfnum = wfnum;
	}
	public double getWfprice() {
		return wfprice;
	}
	public void setWfprice(double wfprice) {
		this.wfprice = wfprice;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public double getRprice() {
		return rprice;
	}
	public void setRprice(double rprice) {
		this.rprice = rprice;
	}
	public int getFnum() {
		return fnum;
	}
	public void setFnum(int fnum) {
		this.fnum = fnum;
	}
	public double getFprice() {
		return fprice;
	}
	public void setFprice(double fprice) {
		this.fprice = fprice;
	}
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public double getCprice() {
		return cprice;
	}
	public void setCprice(double cprice) {
		this.cprice = cprice;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public double getPprice() {
		return pprice;
	}
	public void setPprice(double pprice) {
		this.pprice = pprice;
	}
	public int getDnum() {
		return dnum;
	}
	public void setDnum(int dnum) {
		this.dnum = dnum;
	}
	public double getDprice() {
		return dprice;
	}
	public void setDprice(double dprice) {
		this.dprice = dprice;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public String getBsndatestr() {
		return bsndatestr;
	}
	public void setBsndatestr(String bsndatestr) {
		this.bsndatestr = bsndatestr;
	}
	 
	public Integer getMailinfoid() {
		return mailinfoid;
	}
	public void setMailinfoid(Integer mailinfoid) {
		this.mailinfoid = mailinfoid;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getPerprice() {
		return perprice;
	}
	public void setPerprice(double perprice) {
		this.perprice = perprice;
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
	
	
	

}
