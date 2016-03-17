package com.yundastat.excelutil;
import java.util.HashSet;
import java.util.List;
import java.sql.*;

import com.yundastat.model.TransformPriceRule;
public class RowReader implements IRowReader{
	
	
/* ҵ���߼�ʵ�ַ���
  * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List)
  */
 public void getRows(int sheetIndex, int curRow, List<String> rowlist)throws SQLException {
  // TODO Auto-generated method stub
  System.out.print("sheet"+sheetIndex+" "+curRow+" ");
  for (int i = 0; i < rowlist.size(); i++) {
   System.out.print(rowlist.get(i) + " ");
  }
  System.out.println();
 }

public int getNum() {
	// TODO Auto-generated method stub
	return 0;
}

public double getPrice() {
	// TODO Auto-generated method stub
	return 0;
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
