package com.yundastat.excelutil;

public class ExcelReaderUtil {
	 
    public static final String EXCEL03_EXTENSION = ".xls";  
   
    public static final String EXCEL07_EXTENSION = ".xlsx";  
      
    /** 
     * 
     * @param excel03 
     * @param excel07 
     * @param fileName 
     * @throws Exception  
     */  
    public static void readExcel(IRowReader reader,String fileName) throws Exception{  
       
        if (fileName.endsWith(EXCEL03_EXTENSION)){  
            Excel2003Reader excel03 = new Excel2003Reader();  
            excel03.setRowReader(reader);  
            excel03.process(fileName);  
      
        } else if (fileName.endsWith(EXCEL07_EXTENSION)){  
            Excel2007Reader excel07 = new Excel2007Reader();  
            excel07.setRowReader(reader);  
            excel07.process(fileName);  
        } else {  
            throw new  Exception("The file must be end with xls or xlsx!");  
        }  
    }  

}
