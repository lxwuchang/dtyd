/**
 * 
 */
package com.yundastat.model;

import java.util.Date;

/**
 * @author Simon
 *
 */
public class TransformPriceRule {
	            
	private int transformpriceruleid;
	private int transformcategoryid;
	private String code;
	private int weightruleid;
	private int weightpriceruleid;
	private String weightrulecontent;
	private String isactive;
	private Date createtime;
	private String destination;
	private String destinationcity;
	private String pricelist;
	private Date effdate;
	private Date enddate;
	private String weightpricelist;
	private String isdeleted;
	private String mtype;
	
	
	
	
	
	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	public int getWeightpriceruleid() {
		return weightpriceruleid;
	}
	public void setWeightpriceruleid(int weightpriceruleid) {
		this.weightpriceruleid = weightpriceruleid;
	}
	public int getTransformcategoryid() {
		return transformcategoryid;
	}
	public void setTransformcategoryid(int transformcategoryid) {
		this.transformcategoryid = transformcategoryid;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getWeightpricelist() {
		return weightpricelist;
	}
	public void setWeightpricelist(String weightpricelist) {
		this.weightpricelist = weightpricelist;
	}
	public String getWeightrulecontent() {
		return weightrulecontent;
	}
	public void setWeightrulecontent(String weightrulecontent) {
		this.weightrulecontent = weightrulecontent;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDestinationcity() {
		return destinationcity;
	}
	public void setDestinationcity(String destinationcity) {
		this.destinationcity = destinationcity;
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
	public int getTransformpriceruleid() {
		return transformpriceruleid;
	}
	public void setTransformpriceruleid(int transformpriceruleid) {
		this.transformpriceruleid = transformpriceruleid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getWeightruleid() {
		return weightruleid;
	}
	public void setWeightruleid(int weightruleid) {
		this.weightruleid = weightruleid;
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
	public String getPricelist() {
		return pricelist;
	}
	public void setPricelist(String pricelist) {
		this.pricelist = pricelist;
	}
	

}
