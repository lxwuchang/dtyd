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

import com.yundastat.model.DailyBalance;
import com.yundastat.model.Income;
import com.yundastat.model.MailFenpei;
import com.yundastat.model.StatReport;
import com.yundastat.util.DateUtil;


public class Excel2007WriterIncomeYearByManager {
	
	private Workbook wb;
	private Sheet sh;
	private int flag;
	private int mnum=0;
   	private double twhole=0;
   	private double tzprice=0;
   	private double tmprice=0;
   	private double tqprice=0;
	private int tnum=0;
	public void createSheet(){
		wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk  
		sh = wb.createSheet();
		 
		sh.setColumnWidth((short)0,(short)2580);
		sh.setColumnWidth((short)1,(short)3800);
		sh.setColumnWidth((short)2,(short)2600);
		sh.setColumnWidth((short)3,(short)1800);
		sh.setColumnWidth((short)4,(short)2800);
		sh.setColumnWidth((short)5,(short)1800);
	 
	 	flag=0;
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
		            cell.setCellValue("揽件人");
		            cell.setCellStyle(style);
		            
		            
		             cell = row.createCell(2);  
	   	            address = new CellReference(cell).formatAsString();  
	   	            cell.setCellValue("面单费");
	   	            cell.setCellStyle(style);
	   	            
	   	            cell = row.createCell(3);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("中转费");
		            cell.setCellStyle(style);
		            
		            cell = row.createCell(4);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("其他费用");
		            cell.setCellStyle(style);
		            
		            cell = row.createCell(5);  
		            address = new CellReference(cell).formatAsString();
		            cell.setCellValue("总金额");
		            cell.setCellStyle(style);
		            
		         
		            
		         
		    	flag=1;
		    	
		    }
		    
		 
    	 for(int rownum = 0; rownum < len; rownum++)
    	 {
    		 Income b=(Income)dataList.get(rownum);
    		 Row row = sh.createRow(startnum+rownum+1);
    	 	 twhole=twhole+b.getPrice();
    		 tzprice=tzprice+b.getZprice();
    		 tmprice=tmprice+b.getMprice();
    		 tqprice=tqprice+b.getQprice();
    		 mnum=mnum+1;
   	            Cell cell = row.createCell(0);  
   	            String address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue(startnum+rownum+1);
   	            cell.setCellStyle(style);
   	            
   	            cell = row.createCell(1);  
	            address = new CellReference(cell).formatAsString();  
	            cell.setCellValue(b.getManagername());
	            cell.setCellStyle(style);
	            
	            
	             cell = row.createCell(2);  
   	            address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue(b.getMprice());
   	            cell.setCellStyle(style);
   	            
   	            cell = row.createCell(3);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getZprice());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(4);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getQprice());
	            cell.setCellStyle(style);
	            
	            cell = row.createCell(5);  
	            address = new CellReference(cell).formatAsString();
	            cell.setCellValue(b.getPrice());
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
             
             BigDecimal  bb = new BigDecimal(tmprice);
             tmprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
			.doubleValue();
              cell = row.createCell(2);  
   	            address = new CellReference(cell).formatAsString();  
   	            cell.setCellValue(tmprice);
   	            cell.setCellStyle(style);
   	        
   	          bb = new BigDecimal(tzprice);
   	          tzprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
			.doubleValue();
    			// 保留2位小数
   	         cell = row.createCell(3);  
             address = new CellReference(cell).formatAsString();
             cell.setCellValue(tzprice);
             cell.setCellStyle(style);
             
              bb = new BigDecimal(tqprice);
             tqprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
			.doubleValue();
             cell = row.createCell(4);  
             address = new CellReference(cell).formatAsString();
             cell.setCellValue(tqprice);
             cell.setCellStyle(style);
             
             twhole = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
      
              cell = row.createCell(5);  
              address = new CellReference(cell).formatAsString();
              cell.setCellValue(twhole);
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
