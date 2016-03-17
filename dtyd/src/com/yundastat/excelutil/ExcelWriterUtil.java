package com.yundastat.excelutil;
import java.io.*;
import java.util.*;
public class ExcelWriterUtil {
 
    public static final String EXCEL03 = "2003";  
    
    public static final String EXCEL07 = "2007";
	public static void writeExcel(String fileName,String type,List dataList) throws Exception{  
 
	   if (type.endsWith(EXCEL03)){  
	            //Excel2003Reader excel03 = new Excel2003Reader();  
	            //excel03.setRowReader(reader);  
	            //excel03.process(fileName);  
		  
		   
		 	Excel2003Writer excel03=new Excel2003Writer(); 
	    	excel03.process("",dataList);
	   
	      
	    } else if (type.endsWith(EXCEL07)){  
	            //Excel2007Reader excel07 = new Excel2007Reader();  
	            //excel07.setRowReader(reader);  
	            //excel07.process(fileName);  
	    	
	    	Excel2007Writer excel07=new Excel2007Writer(); 
	    	excel07.process(fileName,dataList,0,1000);
	    	 
	    } else {  
	            throw new  Exception("The file must be end with xls or xlsx");  
	    }  
	}
}
