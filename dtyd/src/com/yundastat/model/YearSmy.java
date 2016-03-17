package com.yundastat.model;

import java.util.Date;

public class YearSmy {
	private Date reportdate;
	private Integer year;
	private Integer month;
	private int wnum;
	private int znum;
	private double wprice;//面单
	private double zprice;//中转费
	private int ynum;
	private double yprice;//业务员
	private double yfkprice;//预付款
	private double zpprice;//直跑
	private double gsprice;//公司支出
	private double gzprice;//工资
	private double qtprice;//其他
	private double fkprice;//返款
	private double pkprice;//赔款
	private double tinprice;
	private double toutprice;
	private double tpprice;
	private String bsndatestr;
	private String manager;
	private double skprice;//收款
	private String mailid;
	private String state;
	private Integer pageNo;
	private String type;
	
	public double getFkprice() {
		return fkprice;
	}

	public void setFkprice(double fkprice) {
		this.fkprice = fkprice;
	}

	public double getPkprice() {
		return pkprice;
	}

	public void setPkprice(double pkprice) {
		this.pkprice = pkprice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getSkprice() {
		return skprice;
	}

	public void setSkprice(double skprice) {
		this.skprice = skprice;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getBsndatestr() {
		return bsndatestr;
	}

	public void setBsndatestr(String bsndatestr) {
		this.bsndatestr = bsndatestr;
	}

	public Date getReportdate() {
		return reportdate;
	}

	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
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

	public int getWnum() {
		return wnum;
	}

	public void setWnum(int wnum) {
		this.wnum = wnum;
	}

	public int getZnum() {
		return znum;
	}

	public void setZnum(int znum) {
		this.znum = znum;
	}

	public double getWprice() {
		return wprice;
	}

	public void setWprice(double wprice) {
		this.wprice = wprice;
	}

	public double getZprice() {
		return zprice;
	}

	public void setZprice(double zprice) {
		this.zprice = zprice;
	}

	public int getYnum() {
		return ynum;
	}

	public void setYnum(int ynum) {
		this.ynum = ynum;
	}

	public double getYprice() {
		return yprice;
	}

	public void setYprice(double yprice) {
		this.yprice = yprice;
	}

	public double getYfkprice() {
		return yfkprice;
	}

	public void setYfkprice(double yfkprice) {
		this.yfkprice = yfkprice;
	}

	public double getZpprice() {
		return zpprice;
	}

	public void setZpprice(double zpprice) {
		this.zpprice = zpprice;
	}

	public double getGsprice() {
		return gsprice;
	}

	public void setGsprice(double gsprice) {
		this.gsprice = gsprice;
	}

	public double getGzprice() {
		return gzprice;
	}

	public void setGzprice(double gzprice) {
		this.gzprice = gzprice;
	}

	public double getQtprice() {
		return qtprice;
	}

	public void setQtprice(double qtprice) {
		this.qtprice = qtprice;
	}

	public double getTinprice() {
		return tinprice;
	}

	public void setTinprice(double tinprice) {
		this.tinprice = tinprice;
	}

	public double getToutprice() {
		return toutprice;
	}

	public void setToutprice(double toutprice) {
		this.toutprice = toutprice;
	}

	public double getTpprice() {
		return tpprice;
	}

	public void setTpprice(double tpprice) {
		this.tpprice = tpprice;
	}

}
