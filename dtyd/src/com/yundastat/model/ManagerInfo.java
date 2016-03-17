/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class ManagerInfo {
	private String managerid;
	private String managername;
	private String telephone;
	private String mobilenumber;
	private String cardid;
	private String address;
	private String isdeleted;
	private String salesid;
	private String salesnick;
	private String roles;
	private String solutionid;//费用方案 id
	private String solutionname;//费用方案名称
	private String weightpriceruleid;
	private String weightpricename;
	private Date cometime;
	private Date outtime;
	private double basicsalary;
	private String state;//在职 离职
	
	
	public Date getCometime() {
		return cometime;
	}
	public void setCometime(Date cometime) {
		this.cometime = cometime;
	}
	public Date getOuttime() {
		return outtime;
	}
	public void setOuttime(Date outtime) {
		this.outtime = outtime;
	}
	public double getBasicsalary() {
		return basicsalary;
	}
	public void setBasicsalary(double basicsalary) {
		this.basicsalary = basicsalary;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWeightpriceruleid() {
		return weightpriceruleid;
	}
	public void setWeightpriceruleid(String weightpriceruleid) {
		this.weightpriceruleid = weightpriceruleid;
	}
	public String getWeightpricename() {
		return weightpricename;
	}
	public void setWeightpricename(String weightpricename) {
		this.weightpricename = weightpricename;
	}
	
	
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public void setSalesid(String salesid) {
		this.salesid = salesid;
	}
	public String getSalesid() {
		return salesid;
	}
	public void setSalesnick(String salesnick) {
		this.salesnick = salesnick;
	}
	public String getSalesnick() {
		return salesnick;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getRoles() {
		return roles;
	}
	public void setSolutionid(String solutionid) {
		this.solutionid = solutionid;
	}
	public String getSolutionid() {
		return solutionid;
	}
	public void setSolutionname(String solutionname) {
		this.solutionname = solutionname;
	}
	public String getSolutionname() {
		return solutionname;
	}
	

}
