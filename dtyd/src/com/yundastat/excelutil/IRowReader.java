package com.yundastat.excelutil;
import java.util.HashSet;
import java.util.List;
import java.sql.*;


public interface IRowReader {
 
 /**ҵ���߼�ʵ�ַ���
  * @param sheetIndex
  * @param curRow
  * @param rowlist
  */
 public  void getRows(int sheetIndex,int curRow, List<String> rowlist)throws SQLException;
 
 public int getNum();
 
 public double getPrice();
 
 public String getManagerlist();
 
 public HashSet<String> getManagerSet();
 
 public HashSet<String> getManagerDestSet();
}


