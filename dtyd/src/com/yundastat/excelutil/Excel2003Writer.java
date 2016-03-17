package com.yundastat.excelutil;
import java.util.ArrayList;
import java.util.List;
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.OutputStream; 
import java.util.Date; 

import org.apache.poi.hssf.usermodel.HSSFDateUtil; 
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.ss.usermodel.Sheet; 
import org.apache.poi.ss.usermodel.Workbook; 
import org.apache.poi.ss.usermodel.WorkbookFactory; 

public class Excel2003Writer {
 
	public void process(String filename,List dataList) throws Exception 
	{
		
		HSSFWorkbook hssfwb=new HSSFWorkbook();
		
        Sheet sheet= hssfwb.createSheet(); 
        
        Cell cell=null; 
        int c=0;
        for(int i=0;i<dataList.size();i++)
		{
        	Row row=sheet.createRow(c);
        	//1
        	cell=row.createCell(0); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //2
    		cell=row.createCell(1); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //3
    		cell=row.createCell(2); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //4
    		cell=row.createCell(3); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //5
    		cell=row.createCell(4); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //6
    		cell=row.createCell(5); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //7
    		cell=row.createCell(6); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //8
    		cell=row.createCell(7); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //9
    		cell=row.createCell(8); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //10
    		cell=row.createCell(9); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            //11
    		cell=row.createCell(10); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
    		//12
    		cell=row.createCell(11); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
    		//13
    		cell=row.createCell(12); 
    		cell.setCellType(Cell.CELL_TYPE_STRING); 
    		cell.setCellValue(i); 
            c++;
            if(c>65000)
            {
            	c=0;
            	sheet= hssfwb.createSheet();
            }
		}
        filename="D://abc.xls";
        OutputStream os=new FileOutputStream(filename); 
		hssfwb.write(os); 
		os.close(); 
        /*
        try { 
    		File f=new File("D://abc.xls"); 
    		if(!f.exists()){ 
    		f.createNewFile(); 
    		} 
    		OutputStream os=new FileOutputStream(f); 
    		hssfwb.write(os); 
    		os.close(); 
    		} catch (Exception e) { 
    		e.printStackTrace(); 
    		} 
        */
	System.out.println("��ɣ�");
		
	  
	}
}
