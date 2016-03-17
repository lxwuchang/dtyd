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

import com.yundastat.model.PriceDetail;
import com.yundastat.model.StatReport;
import com.yundastat.util.DateUtil;

public class Excel2007WriterPriceDetail {

	private Workbook wb;
	private Sheet sh;
	private Sheet sh2;
	private int flag;
	private int mnum = 0;
	private double tweight = 0;
	private double tscanfei = 0;
	private double tweightprice = 0;
	private double tbuzhufei = 0;
	private double ttransformprice = 0;

	public void createSheet(String name) {
	 		wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding
	 
			sh = wb.createSheet(name);
			sh.setColumnWidth((short) 0, (short) 2380);
			sh.setColumnWidth((short) 1, (short) 3300);
			sh.setColumnWidth((short) 2, (short) 2600);
			sh.setColumnWidth((short) 3, (short) 1500);
			sh.setColumnWidth((short) 4, (short) 2800);
			sh.setColumnWidth((short) 5, (short) 1500);
			sh.setColumnWidth((short) 6, (short) 1500);
			sh.setColumnWidth((short) 7, (short) 1500);

	 
		flag = 0;
	}
 
	public void process(String filename, List dataList, int startnum, int total)
			throws Exception {

		int len = 0;
		if (dataList != null && dataList.size() > 0)
			len = dataList.size();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 10); // 字体高度

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

		if (flag == 0) {
			Row row = sh.createRow(0);
			// 结算日期 单号 分部名称 费用小类 结算单位 结算金额 备注
			Cell cell = row.createCell(0);
			String address = new CellReference(cell).formatAsString();
			cell.setCellValue("结算日期");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("单号");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("揽件人");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("费用类型");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("费用小类");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("结算单位");
			cell.setCellStyle(style);

			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("结算金额");
			cell.setCellStyle(style);

			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("备注");
			cell.setCellStyle(style);
			flag = 1;

		}

		for (int rownum = 0; rownum < len; rownum++) {
			PriceDetail b = (PriceDetail) dataList.get(rownum);
			Row row = sh.createRow(startnum + rownum + 1);

			ttransformprice = ttransformprice + b.getPrice();
			mnum = mnum + 1;
			Cell cell = row.createCell(0);
			String address = new CellReference(cell).formatAsString();
			cell.setCellValue(DateUtil.formatYYYYMMDD(b.getBsndate()));
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getMailid());
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getManager());
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getPricetype());
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getSubpricetype());
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getStatunit());
			cell.setCellStyle(style);

			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getPrice());
			cell.setCellStyle(style);

			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getMemo());
			cell.setCellStyle(style);

		}

		if (total == mnum) {

			double whole = 0;
			Row row = sh.createRow(total + 1);
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

			BigDecimal bb = new BigDecimal(ttransformprice);
			// 保留2位小数
			ttransformprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(ttransformprice);
			cell.setCellStyle(style);

			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("");
			cell.setCellStyle(style);

		}

	}

	public void createFile(String filename) {

		FileOutputStream out = null;
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
