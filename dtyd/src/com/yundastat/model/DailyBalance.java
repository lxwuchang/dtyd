/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class DailyBalance {
	     private int num;
	     private Integer year;
	     private Integer month;
		private Date   bsndate          ;
		private Date   statdate;
		private String manager          ;
		private double jijianprice        ;
		private double chujianfei       ;
		private double weightprice      ;
		private double buzuprice        ;
		private double transformprice   ;
		private double wuxiangprice;
		private double fakuanprice       ;
		private double zhuanjianprice    ;
	 	private double paisongprice      ;
		private double diujianprice    ;
	  	private double otherprice       ;//其他费用B
	 	private double mailprice        ;
		private double wholeprice       ;
		private Date   createtime;
		private String status;
		
		
		
	 
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
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
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
		public String getManager() {
			return manager;
		}
		public void setManager(String manager) {
			this.manager = manager;
		}
		public double getJijianprice() {
			return jijianprice;
		}
		public void setJijianprice(double jijianprice) {
			this.jijianprice = jijianprice;
		}
		public double getChujianfei() {
			return chujianfei;
		}
		public void setChujianfei(double chujianfei) {
			this.chujianfei = chujianfei;
		}
		public double getWeightprice() {
			return weightprice;
		}
		public void setWeightprice(double weightprice) {
			this.weightprice = weightprice;
		}
		public double getBuzuprice() {
			return buzuprice;
		}
		public void setBuzuprice(double buzuprice) {
			this.buzuprice = buzuprice;
		}
		public double getTransformprice() {
			return transformprice;
		}
		public void setTransformprice(double transformprice) {
			this.transformprice = transformprice;
		}
		public double getWuxiangprice() {
			return wuxiangprice;
		}
		public void setWuxiangprice(double wuxiangprice) {
			this.wuxiangprice = wuxiangprice;
		}
		public double getFakuanprice() {
			return fakuanprice;
		}
		public void setFakuanprice(double fakuanprice) {
			this.fakuanprice = fakuanprice;
		}
		public double getZhuanjianprice() {
			return zhuanjianprice;
		}
		public void setZhuanjianprice(double zhuanjianprice) {
			this.zhuanjianprice = zhuanjianprice;
		}
		public double getPaisongprice() {
			return paisongprice;
		}
		public void setPaisongprice(double paisongprice) {
			this.paisongprice = paisongprice;
		}
		public double getDiujianprice() {
			return diujianprice;
		}
		public void setDiujianprice(double diujianprice) {
			this.diujianprice = diujianprice;
		}
	    public double getOtherprice() {
			return otherprice;
		}
		public void setOtherprice(double otherprice) {
			this.otherprice = otherprice;
		}
		public double getMailprice() {
			return mailprice;
		}
		public void setMailprice(double mailprice) {
			this.mailprice = mailprice;
		}
		public double getWholeprice() {
			return wholeprice;
		}
		public void setWholeprice(double wholeprice) {
			this.wholeprice = wholeprice;
		}
		public Date getCreatetime() {
			return createtime;
		}
		public void setCreatetime(Date createtime) {
			this.createtime = createtime;
		}
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	 
}
