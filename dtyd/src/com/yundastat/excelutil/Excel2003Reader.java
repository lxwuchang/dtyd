package com.yundastat.excelutil;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
public   class Excel2003Reader implements HSSFListener{  
	
 
    private int minColumns = -1;  
    private POIFSFileSystem fs;  
    private int lastRowNumber;  
    private int lastColumnNumber;  
  
    /** Should we output the formula, or the value it has? */  
    private boolean outputFormulaValues = true;  
  
    /** For parsing Formulas */  
    private SheetRecordCollectingListener workbookBuildingListener;  
    private HSSFWorkbook stubWorkbook;  
  
    // Records we pick up as we process   
    private SSTRecord sstRecord;  
    private FormatTrackingHSSFListener formatListener;  
  
   
    private int sheetIndex = -1;  
    private BoundSheetRecord[] orderedBSRs;  
    @SuppressWarnings("unchecked")  
    private ArrayList boundSheetRecords = new ArrayList();  
  
    // For handling formulas with string results   
    private int nextRow;  
    private int nextColumn;  
    private boolean outputNextStringRecord;  
   
    private int curRow = 0;  
   
    private List<String> rowlist = new ArrayList<String>();;  
    @SuppressWarnings( "unused")  
    private String sheetName;  
      
    private IRowReader rowReader;  
  
      
    public void setRowReader(IRowReader rowReader){  
        this.rowReader = rowReader;  
    }  
    
    public void process(String fileName) throws IOException {  
        this.fs = new POIFSFileSystem(new FileInputStream(fileName));  
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(  
                this);  
        formatListener = new FormatTrackingHSSFListener(listener);  
        HSSFEventFactory factory = new HSSFEventFactory();  
        HSSFRequest request = new HSSFRequest();  
        if (outputFormulaValues) {  
            request.addListenerForAllRecords(formatListener);  
        } else {  
            workbookBuildingListener = new SheetRecordCollectingListener(  
                    formatListener);  
            request.addListenerForAllRecords(workbookBuildingListener);  
        }  
        factory.processWorkbookEvents(request, fs);  
       
    }  
      
    /** 
     * HSSFListener ��������� Record 
     */  
    @SuppressWarnings("unchecked")  
    public void processRecord(Record record) {  
        int thisRow = -1;  
        int thisColumn = -1;  
        String thisStr = null;  
        String value = null;  
        switch (record.getSid()) {  
            case BoundSheetRecord.sid:  
                boundSheetRecords.add(record);  
                break;  
            case BOFRecord.sid:  
                BOFRecord br = (BOFRecord) record;  
                if (br.getType() == BOFRecord.TYPE_WORKSHEET) {  
                 
                    if (workbookBuildingListener != null && stubWorkbook == null) {  
                        stubWorkbook = workbookBuildingListener  
                                .getStubHSSFWorkbook();  
                    }  
                      
                    sheetIndex++;  
                    if (orderedBSRs == null) {  
                        orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);  
                    }  
                    sheetName = orderedBSRs[sheetIndex].getSheetname();  
                }  
                break;  
      
            case SSTRecord.sid:  
                sstRecord = (SSTRecord) record;  
                break;  
      
            case BlankRecord.sid:  
                BlankRecord brec = (BlankRecord) record;  
                thisRow = brec.getRow();  
                thisColumn = brec.getColumn();  
                thisStr = "";  
                rowlist.add(thisColumn, thisStr);  
                break;  
            case BoolErrRecord.sid:  
                BoolErrRecord berec = (BoolErrRecord) record;  
                thisRow = berec.getRow();  
                thisColumn = berec.getColumn();  
                thisStr = berec.getBooleanValue()+"";  
                rowlist.add(thisColumn, thisStr);  
                break;  
      
            case FormulaRecord.sid:  
                FormulaRecord frec = (FormulaRecord) record;  
                thisRow = frec.getRow();  
                thisColumn = frec.getColumn();  
                if (outputFormulaValues) {  
                    if (Double.isNaN(frec.getValue())) {  
                        // Formula result is a string   
                        // This is stored in the next record   
                        outputNextStringRecord = true;  
                        nextRow = frec.getRow();  
                        nextColumn = frec.getColumn();  
                    } else {  
                        thisStr = formatListener.formatNumberDateCell(frec);  
                    }  
                } else {  
                    thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook,  
                            frec.getParsedExpression()) + '"';  
                }  
                rowlist.add(thisColumn,thisStr);  
                break;  
            case StringRecord.sid: 
                if (outputNextStringRecord) {  
                    // String for formula   
                    StringRecord srec = (StringRecord) record;  
                    thisStr = srec.getString();  
                    thisRow = nextRow;  
                    thisColumn = nextColumn;  
                    outputNextStringRecord = false;  
                }  
                break;  
            case LabelRecord.sid:  
                LabelRecord lrec = (LabelRecord) record;  
                curRow = thisRow = lrec.getRow();  
                thisColumn = lrec.getColumn();  
                value = lrec.getValue().trim();  
                value = value.equals("")?" ":value;  
                this.rowlist.add(thisColumn, value);  
                break;  
            case LabelSSTRecord.sid:   
                LabelSSTRecord lsrec = (LabelSSTRecord) record;  
                curRow = thisRow = lsrec.getRow();  
                thisColumn = lsrec.getColumn();  
                if (sstRecord == null) {  
                    rowlist.add(thisColumn, " ");  
                } else {  
                    value =  sstRecord  
                    .getString(lsrec.getSSTIndex()).toString().trim();  
                    value = value.equals("")?" ":value;  
                    rowlist.add(thisColumn,value);  
                }  
                break;  
            case NumberRecord.sid:   
                NumberRecord numrec = (NumberRecord) record;  
                curRow = thisRow = numrec.getRow();  
                thisColumn = numrec.getColumn();  
                value = formatListener.formatNumberDateCell(numrec).trim();  
                value = value.equals("")?" ":value;  
                
                rowlist.add(thisColumn, value);  
                break;  
            default:  
                break;  
        }  
  
        
        if (thisRow != -1 && thisRow != lastRowNumber) {  
            lastColumnNumber = -1;  
        }  
  
        
        if (record instanceof MissingCellDummyRecord) {  
            MissingCellDummyRecord mc = (MissingCellDummyRecord) record;  
            curRow = thisRow = mc.getRow();  
            thisColumn = mc.getColumn();  
            rowlist.add(thisColumn," ");  
        }  
  
        
        if (thisRow > -1)  
            lastRowNumber = thisRow;  
        if (thisColumn > -1)  
            lastColumnNumber = thisColumn;  
   
        if (record instanceof LastCellOfRowDummyRecord) {  
            if (minColumns > 0) {  
               
                if (lastColumnNumber == -1) {  
                    lastColumnNumber = 0;  
                }  
            }  
            lastColumnNumber = -1;  
                
           try {
            	
            rowReader.getRows(sheetIndex,curRow, rowlist);  
            }catch(Exception e)
            {
            	  e.printStackTrace();   
            }
            
         
          rowlist.clear();  
            
            
        
        }  
    }  
      
}  
