/**
 * 
 */
package com.yundastat.model;

/**
 * @author Administrator
 *
 */
public class TransformCategory {
	public int transformcategoryid;
	public String transformcategoryname; 
	public String status;	 
	public String isdeleted;
	public String getIsdeleted() {
		return isdeleted;
	}
	
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	public int getTransformcategoryid() {
		return transformcategoryid;
	}
	public void setTransformcategoryid(int transformcategoryid) {
		this.transformcategoryid = transformcategoryid;
	}
	public String getTransformcategoryname() {
		return transformcategoryname;
	}
	public void setTransformcategoryname(String transformcategoryname) {
		this.transformcategoryname = transformcategoryname;
	}
	 
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
 
	

}
