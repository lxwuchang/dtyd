package com.yundastat.excelutil;
import java.io.InputStream;    
import java.util.ArrayList;   
import java.util.Iterator;   
import java.util.List;   
import org.apache.poi.xssf.eventusermodel.XSSFReader;   
import org.apache.poi.xssf.model.SharedStringsTable;   
import org.apache.poi.xssf.usermodel.XSSFRichTextString;   
import org.apache.poi.openxml4j.opc.OPCPackage;   
import org.xml.sax.Attributes;   
import org.xml.sax.InputSource;   
import org.xml.sax.SAXException;   
import org.xml.sax.XMLReader;   
import org.xml.sax.helpers.DefaultHandler;   
import org.xml.sax.helpers.XMLReaderFactory;   
  
  
public  class Excel2007Reader  extends DefaultHandler {   
    private SharedStringsTable sst;   
    private String lastContents;   
    private boolean nextIsString;   
  
    private int sheetIndex = -1;   
    private List<String> rowlist = new ArrayList<String>();   
    private int curRow = 0;   
    private int curCol = 0;   

    private IRowReader rowReader;  
    public void setRowReader(IRowReader rowReader){  
        this.rowReader = rowReader;  
    }  
    
    
     
    public void processOneSheet(String filename,int sheetId) throws Exception {   
        OPCPackage pkg = OPCPackage.open(filename);   
        XSSFReader r = new XSSFReader(pkg);   
        SharedStringsTable sst = r.getSharedStringsTable();   
           
        XMLReader parser = fetchSheetParser(sst);   
   
        InputStream sheet2 = r.getSheet("rId"+sheetId);   
        sheetIndex++;   
        InputSource sheetSource = new InputSource(sheet2);   
        parser.parse(sheetSource);   
        sheet2.close();   
    }   
  
   
    public void process(String filename) throws Exception {   
        OPCPackage pkg = OPCPackage.open(filename);   
        XSSFReader r = new XSSFReader(pkg);   
        SharedStringsTable sst = r.getSharedStringsTable();   
  
        XMLReader parser = fetchSheetParser(sst);   
  
        Iterator<InputStream> sheets = r.getSheetsData();   
        while (sheets.hasNext()) {   
            curRow = 0;   
            sheetIndex++;   
            InputStream sheet = sheets.next();   
            InputSource sheetSource = new InputSource(sheet);   
            parser.parse(sheetSource);   
            sheet.close();   
        }   
   }   
  
    public XMLReader fetchSheetParser(SharedStringsTable sst)   
            throws SAXException {   
        XMLReader parser = XMLReaderFactory   
               .createXMLReader("org.apache.xerces.parsers.SAXParser");   
       this.sst = sst;   
        parser.setContentHandler(this);   
        return parser;   
    }   
  
    public void startElement(String uri, String localName, String name,   
           Attributes attributes) throws SAXException {   
      
      if (name.equals("c")) {                
            String cellType = attributes.getValue("t");   
             
           if (cellType != null && cellType.equals("s")) {   
               nextIsString = true;   
            } else {   
               nextIsString = false;   
          }   
}   
      
       lastContents = "";   
   }   
 
    public void endElement(String uri, String localName, String name)   
            throws SAXException {   
        
        if (nextIsString) {   
            try {   
                int idx = Integer.parseInt(lastContents);   
              lastContents = new XSSFRichTextString(sst.getEntryAt(idx))   
                        .toString();   
            } catch (Exception e) {   
  
            }   
       }   
 
        
        if (name.equals("v")) {   
            String value = lastContents.trim();   
           value = value.equals("")?" ":value;   
           rowlist.add(curCol, value);   
           curCol++;   
        }else{
            
           if (name.equals("row")) {   
               try {   
               //   optRows(sheetIndex,curRow,rowlist);   
            	   rowReader.getRows(sheetIndex,curRow, rowlist); 
               
               } catch (Exception e) {   
                    e.printStackTrace();   
                }   
                rowlist.clear();   
                curRow++;   
                curCol = 0;   
           }   
       }   
    }   
  
   public void characters(char[] ch, int start, int length)   
            throws SAXException {   
       
        lastContents += new String(ch, start, length);   
   }   
} 
