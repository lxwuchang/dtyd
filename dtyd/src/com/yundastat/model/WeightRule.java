/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class WeightRule {
	private int weightruleid;
	private String weightrulecontent;
	private String isactive;
	private Date createtime;
	private Date effdate;
	private Date enddate;
	private String weightrulename;
	private String isdeleted;
	
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getWeightrulename() {
		return weightrulename;
	}
	public void setWeightrulename(String weightrulename) {
		this.weightrulename = weightrulename;
	}
	public Date getEffdate() {
		return effdate;
	}
	public void setEffdate(Date effdate) {
		this.effdate = effdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getWeightruleid() {
		return weightruleid;
	}
	public void setWeightruleid(int weightruleid) {
		this.weightruleid = weightruleid;
	}
	public String getWeightrulecontent() {
		return weightrulecontent;
	}
	public void setWeightrulecontent(String weightrulecontent) {
		this.weightrulecontent = weightrulecontent;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	

}
