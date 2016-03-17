package com.yundastat.excelutil;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.util.CellReference;  
import org.apache.poi.xssf.streaming.SXSSFWorkbook;   

import com.yundastat.model.CompanyIncome;
import com.yundastat.model.DailyBalance;
import com.yundastat.model.GongziOutcome;
import com.yundastat.model.MailFenpei;
import com.yundastat.model.StatReport;
import com.yundastat.util.DateUtil;


public class Excel2007WriterGongziOutcomeYearByMonth {
	
	private Workbook wb;
	private Sheet sh;
	private int flag;
	private int mnum=0;
	private double  tprofit=0;
	private double tmailprice=0;
	private double ttransformprice=0;
	private int tnum=0;
	private double tprice=0;
	public void createSheet(String sheetname){
		if(wb==null)
		wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk  
		
		if(sheetname.equals("汇总")){
		sh = wb.createSheet(sheetname);
		 
		sh.setColumnWidth((short)0,(short)2580);
		sh.setColumnWidth((short)1,(short)3800);
		sh.setColumnWidth((short)2,(short)2600);
		sh.setColumnWidth((short)3,(short)1800);
		 
		
		}else{
			
			sh = wb.createSheet(sheetname);
			 
			sh.setColumnWidth((short)0,(short)2580);
			sh.setColumnWidth((short)1,(short)3800);
			sh.setColumnWidth((short)2,(short)2600);
			sh.setColumnWidth((short)3,(short)1800);
			sh.setColumnWidth((short)4,(short)2800);
			sh.setColumnWidth((short)5,(short)1800);
			sh.setColumnWidth((short)6,(short)1800);
			sh.setColumnWidth((short)7,(short)1800);
			sh.setColumnWidth((short)8,(short)1800);
			sh.setColumnWidth((short)9,(short)1800);
			sh.setColumnWidth((short)10,(short)1800);
			sh.setColumnWidth((short)11,(short)1800);
			sh.setColumnWidth((short)12,(short)1800);
			sh.setColumnWidth((short)13,(short)1800);	
			
		}
	 	flag=0;
	}
	
	
    public void process2(String filename,List dataList,int startnum,int total) throws Exception 
    {
     	 
        int len=0;
        if(dataList!=null&&dataList.size()>0)
   	       len=dataList.size();
    	 Font font =  wb.createFont();
         font.setFontHeightInPoints((short)10); //字体高度
         
    	   CellStyle style = wb.createCellStyle();
    	   style.setFont(font);
		    style.setBorderBottom(CellStyle.BORDER_THIN);
		    style.setBorderLeft(CellStyle.BORDER_THIN);
		    style.setBorderRight(CellStyle.BORDER_THIN);
		    style.setBorderTop(CellStyle.BORDER_THIN);
		    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		    style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		    if(flag==0){
		    	 Row row = sh.createRow(0);
	    		 
		    	   Cell cell = row.createCell(0);  
	   	            String address = new CellReference(cell).formatAsString();  
	   	            cell.setCellValue("序号");
	   	            cell.setCellStyle(style);
	   	            
	   	           
	   	            cell = row.createCell(1);  
		            address = new CellReference(cell).formatAsString();  
		            cell.setCellValue("日期");
		            cell.setCellStyle(style);
		            
		            
		             cell = row.createCell(2);  
	   	            address = new CellReference(cell).formatAsString();  
	   	            cell.setCellValue("名字");
	   	            cell.setCellStyle(style);
	   	            
	   	             
		            cell = row.createCell(3);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("工资");
		            cell.setCellStyle(style);
		          
		    	flag=1;
		    	
		    }
		    
		 
    	 for(int rownum = 0; rownum < len; rownum++)
    	 {
    		 GongziOutcome b=(GongziOutcome)dataList.get(rownum);
    		 Row row = sh.createRow(startnum+rownum+1);
    		 
    		 ttransformprice=ttransformprice+b.getSalary();
     
    		    mnum=mnum+1;
   	            Cell cell = row.createCell(0);  
   	            String address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue(startnum+rownum+1);
   	            cell.setCellStyle(style);
   	            
   	            cell = row.createCell(1);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getYear()+"年"+b.getMonth()+"月");
	            cell.setCellStyle(style);
	            
	            
	        
   	            cell = row.createCell(2);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue("公司");
	            cell.setCellStyle(style);
	     
	    
	            
	            cell = row.createCell(3);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getSalary());
	            cell.setCellStyle(style);
	            
	           
	        
	        
    	 }
    	 
    	 if(total==mnum){
    		 
    	
			double whole=0;
    		 Row row = sh.createRow(total+1);
       	      Cell cell = row.createCell(0);  
   	            String address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue("合计");
   	            cell.setCellStyle(style);
   	            
   	            cell = row.createCell(1);  
             address = new CellReference(cell).formatAsString();  
             cell.setCellValue("");
             cell.setCellStyle(style);
             
             
              cell = row.createCell(2);  
   	            address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue("");
   	            cell.setCellStyle(style);
   	        
		 
              BigDecimal   bb = new BigDecimal(ttransformprice);
   			// 保留2位小数
               ttransformprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
   					.doubleValue();
              
               cell = row.createCell(3);  
               address = new CellReference(cell).formatAsString();
               cell.setCellValue(ttransformprice);
               cell.setCellStyle(style);
               
           
    		 
    	 }
    	 
    	
    	 
    	
    	
    }
    
    
    public void process(String filename,List dataList,int startnum,int total) throws Exception 
    {
     	 
        int len=0;
        if(dataList!=null&&dataList.size()>0)
   	       len=dataList.size();
    	 Font font =  wb.createFont();
         font.setFontHeightInPoints((short)10); //字体高度
         
    	   CellStyle style = wb.createCellStyle();
    	   style.setFont(font);
		    style.setBorderBottom(CellStyle.BORDER_THIN);
		    style.setBorderLeft(CellStyle.BORDER_THIN);
		    style.setBorderRight(CellStyle.BORDER_THIN);
		    style.setBorderTop(CellStyle.BORDER_THIN);
		    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		    style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		    if(flag==0){
		    	 Row row = sh.createRow(0);
	    		 
		    	   Cell cell = row.createCell(0);  
	   	            String address = new CellReference(cell).formatAsString();  
	   	            cell.setCellValue("序号");
	   	            cell.setCellStyle(style);
	   	            
	   	           
	   	            cell = row.createCell(1);  
		            address = new CellReference(cell).formatAsString();  
		            cell.setCellValue("员工名字");
		            cell.setCellStyle(style);
		            
		            
		             cell = row.createCell(2);  
	   	            address = new CellReference(cell).formatAsString();  
	   	            cell.setCellValue("基本工资");
	   	            cell.setCellStyle(style);
	   	            
	   	            cell = row.createCell(3);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("入职日期");
		            cell.setCellStyle(style);
		            
		            cell = row.createCell(4);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("离职日期");
		            cell.setCellStyle(style);
		            
		            cell = row.createCell(5);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("加班补贴");
		            cell.setCellStyle(style);
		            
		         
		            cell = row.createCell(6);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("夜宵补贴");
		            cell.setCellStyle(style);
		          
		            cell = row.createCell(7);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("全勤奖");
		            cell.setCellStyle(style);
		            
		            cell = row.createCell(8);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("行政罚款");
		            cell.setCellStyle(style);
		            
		            cell = row.createCell(9);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("请假");
		            cell.setCellStyle(style);
		          
		            cell = row.createCell(10);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("社保");
		            cell.setCellStyle(style);
		            
		         
		            cell = row.createCell(11);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("支取");
		            cell.setCellStyle(style);
		            
		            cell = row.createCell(12);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("工资");
		            cell.setCellStyle(style);
		            
		            cell = row.createCell(13);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("发放情况");
		            cell.setCellStyle(style);
		    	flag=1;
		    	
		    }
		    ttransformprice=0;
		 
    	 for(int rownum = 0; rownum < len; rownum++)
    	 {
    		 GongziOutcome b=(GongziOutcome)dataList.get(rownum);
    		 Row row = sh.createRow(startnum+rownum+1);
    		 
    		 ttransformprice=ttransformprice+b.getSalary();
     
    		 mnum=mnum+1;
   	            Cell cell = row.createCell(0);  
   	            String address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue(startnum+rownum+1);
   	            cell.setCellStyle(style);
   	            
   	            cell = row.createCell(1);  
	            address = new CellReference(cell).formatAsString();  
	            cell.setCellValue(b.getManagerid());
	            cell.setCellStyle(style);
	            
	            
	             cell = row.createCell(2);  
   	            address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue(b.getBasicsalary());
   	            cell.setCellStyle(style);
   	            
   	            cell = row.createCell(3);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(DateUtil.formatYYYYMMDD(b.getCometime()));
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(4);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(DateUtil.formatYYYYMMDD(b.getOuttime()));
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(5);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getJiaban());
	            cell.setCellStyle(style);
	            
	        
	            cell = row.createCell(6);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getShibu());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(7);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getQuanqin());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(8);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getFakuan());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(9);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getQingjia());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(10);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getShebao());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(11);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getZhiqu());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(12);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getSalary());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(13);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue("");
	            cell.setCellStyle(style);
	        
	        
    	 }
    	 
    	 if(total==mnum){
    		 
    	
			double whole=0;
    		 Row row = sh.createRow(total+1);
       	      Cell cell = row.createCell(0);  
   	            String address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue("合计");
   	            cell.setCellStyle(style);
   	            
   	            cell = row.createCell(1);  
             address = new CellReference(cell).formatAsString();  
             cell.setCellValue("");
             cell.setCellStyle(style);
             
             
              cell = row.createCell(2);  
   	            address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue("");
   	            cell.setCellStyle(style);
   	        
			
   	       
   	           cell = row.createCell(3);  
             address = new CellReference(cell).formatAsString();
             cell.setCellValue("");
             cell.setCellStyle(style);
             
             cell = row.createCell(4);  
             address = new CellReference(cell).formatAsString();
             cell.setCellValue("");
             cell.setCellStyle(style);
           
              cell = row.createCell(5);  
              address = new CellReference(cell).formatAsString();
              cell.setCellValue("");
              cell.setCellStyle(style);
              
          
              cell = row.createCell(6);  
              address = new CellReference(cell).formatAsString();
              cell.setCellValue("");
              cell.setCellStyle(style);
              
              cell = row.createCell(7);  
              address = new CellReference(cell).formatAsString();
              cell.setCellValue("");
              cell.setCellStyle(style);
              
              cell = row.createCell(8);  
              address = new CellReference(cell).formatAsString();
              cell.setCellValue("");
              cell.setCellStyle(style);
              
              cell = row.createCell(9);  
              address = new CellReference(cell).formatAsString();
              cell.setCellValue("");
              cell.setCellStyle(style);
              
              cell = row.createCell(10);  
              address = new CellReference(cell).formatAsString();
              cell.setCellValue("");
              cell.setCellStyle(style);
              
              cell = row.createCell(11);  
              address = new CellReference(cell).formatAsString();
              cell.setCellValue("");
              cell.setCellStyle(style);
              
              BigDecimal   bb = new BigDecimal(ttransformprice);
   			// 保留2位小数
               ttransformprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
   					.doubleValue();
              
               cell = row.createCell(12);  
               address = new CellReference(cell).formatAsString();
               cell.setCellValue(ttransformprice);
               cell.setCellStyle(style);
               
               cell = row.createCell(13);  
               address = new CellReference(cell).formatAsString();
               cell.setCellValue("");
               cell.setCellStyle(style);
    		 
    	 }
    	 
    	
    	 
    	
    	
    }
    
    public void createFile(String filename){
    	
    	FileOutputStream out=null;
		try {
			out = new FileOutputStream(filename);
			try {
				wb.write(out);
				out.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	
    	System.out.println("end");
    }
}
