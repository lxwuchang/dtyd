/**
 * 
 */
package com.yundastat.excelutil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.*;
import com.yundastat.model.MailInfo;
import com.yundastat.model.PriceDetail;
import com.yundastat.service.MailService;
import com.yundastat.util.DateUtil;

/**
 * @author Administrator
 * 
 */

public class PriceReader implements IRowReader {
	
    private MailService mailService;
	private String date;
	private String pricetype;
 	private String flag="0";
 	private int num;
 	private double price;

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public PriceReader() {
		 

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

	public PriceReader(String dt,String dp,MailService m) {
	    date=dt;
		mailService=m;
		pricetype=dp;
		num=0;
		price=0;
	 		
	}

	/*
	 * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List)
	 */
	public void getRows(int sheetIndex, int curRow, List<String> rowlist)
			throws SQLException {
		// TODO Auto-generated method stub

		   int year = Integer.parseInt(date.substring(0, 4));
	     	String statdate = rowlist.get(0);
	    	//20150801
	     	if(statdate.indexOf("日期")==-1&&curRow>0){

	    	  String yearstr=statdate.substring(0,4);
	    	  String monthstr=statdate.substring(4,6);
	    	  String daystr=statdate.substring(6,8);
	         Date a=null;
			try {
				a = DateUtil.parse(yearstr+"-"+monthstr+"-"+daystr+" 00:00:00", DateUtil.DATE_TIME_FORMAT);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    		 
	    	 
			String mailid =rowlist.get(1);
			String manager= rowlist.get(2).trim();
	        String  subpricetype= rowlist.get(3);
	        String statunit= rowlist.get(4);
		 	String price1 = rowlist.get(5);
		 	String memo="";
		 	if(rowlist.size()==7)
			 memo = rowlist.get(6).trim();
		 	 	 	
			PriceDetail  mails = new PriceDetail();
			mails.setMailid(mailid);
			try {
				mails.setBsndate(DateUtil.parse(date+" 00:00:00",DateUtil.DATE_TIME_FORMAT));
				mails.setStatdate(a);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 操作费暂时设置为1.1
			
			
			mails.setSubpricetype(subpricetype);
		    mails.setPricetype(pricetype);
			mails.setManager(manager);
			mails.setStatunit(statunit);
			mails.setMemo(memo);
			mails.setPrice(Double.parseDouble(price1));
		    
			mails.setYear(year);
		    num=num+1;
		    price=price+mails.getPrice();	 
		 	mailService.createPriceDetail(mails);
			
	     	}
	 
		

	}

	public String getManagerlist() {
		// TODO Auto-generated method stub
		return null;
	}

	public HashSet<String> getManagerSet() {
		// TODO Auto-generated method stub
		return null;
	}

	public HashSet<String> getManagerDestSet() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
