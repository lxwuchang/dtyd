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
import com.yundastat.model.StatReport;
import com.yundastat.util.DateUtil;

public class Excel2007WriterDailyBalanceMonthByManager {

	private Workbook wb;
	private Sheet sh;
	private int flag;
	private int mnum = 0;
	private double tjijian = 0;
	private double tfakuan = 0;
	private double tweightprice = 0;
	private double tbuzhufei = 0;
	private double ttransformprice = 0;
	private double tpaisong = 0;
	private double tzhuanjian = 0;
	private double tdiujian = 0;
	private double twhole = 0;
	private int tnum = 0;

	public void createSheet(String sheetname) {
		if (wb == null)
			wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding
		// rows will be flushed to disk

		if (sheetname.equals("结算用户")) {
			sh = wb.createSheet(sheetname);

			sh.setColumnWidth((short) 0, (short) 2180);
			sh.setColumnWidth((short) 1, (short) 3300);
			sh.setColumnWidth((short) 2, (short) 2600);
			sh.setColumnWidth((short) 3, (short) 1500);
			sh.setColumnWidth((short) 4, (short) 2800);
			sh.setColumnWidth((short) 5, (short) 1500);
			sh.setColumnWidth((short) 6, (short) 1500);
			sh.setColumnWidth((short) 7, (short) 1500);
			sh.setColumnWidth((short) 8, (short) 1500);
			sh.setColumnWidth((short) 9, (short) 1500);
			sh.setColumnWidth((short) 10, (short) 1500);
			sh.setColumnWidth((short) 11, (short) 1500);
		} else {

			sh = wb.createSheet(sheetname);

			sh.setColumnWidth((short) 0, (short) 2180);
			sh.setColumnWidth((short) 1, (short) 3300);
			sh.setColumnWidth((short) 2, (short) 2600);
			sh.setColumnWidth((short) 3, (short) 1500);
			sh.setColumnWidth((short) 4, (short) 2800);
			sh.setColumnWidth((short) 5, (short) 1500);
			sh.setColumnWidth((short) 6, (short) 1500);
			sh.setColumnWidth((short) 7, (short) 1500);
			sh.setColumnWidth((short) 8, (short) 1500);
			sh.setColumnWidth((short) 9, (short) 1500);
			sh.setColumnWidth((short) 10, (short) 1500);
			sh.setColumnWidth((short) 11, (short) 1500);

		}
		flag = 0;
	}

	public void process(String filename, List dataList, int startnum, int total)
			throws Exception {

		int len = 0;
		mnum = 0;
		tnum = 0;
		tjijian = 0;
		tfakuan = 0;
		tweightprice = 0;
		tbuzhufei = 0;
		ttransformprice = 0;
		tpaisong = 0;
		tzhuanjian = 0;
		tdiujian = 0;
		twhole = 0;
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

			Cell cell = row.createCell(0);
			String address = new CellReference(cell).formatAsString();
			cell.setCellValue("日期");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("揽件人");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("票数");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("计件费");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("称重费");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("区域补助费");
			cell.setCellStyle(style);

			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("中转费(元)");
			cell.setCellStyle(style);

			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("大笔罚款");
			cell.setCellStyle(style);

			cell = row.createCell(8);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("有偿派费");
			cell.setCellStyle(style);

			cell = row.createCell(9);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("转件费");
			cell.setCellStyle(style);

			cell = row.createCell(10);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("丢件费");
			cell.setCellStyle(style);

			cell = row.createCell(11);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("合计");
			cell.setCellStyle(style);
			flag = 1;

		}

		for (int rownum = 0; rownum < len; rownum++) {
			DailyBalance b = (DailyBalance) dataList.get(rownum);
			Row row = sh.createRow(startnum + rownum + 1);
			tjijian = tjijian + b.getJijianprice();
			tweightprice = tweightprice + b.getWeightprice();
			tbuzhufei = tbuzhufei + b.getBuzuprice();
			ttransformprice = ttransformprice + b.getTransformprice();
			tfakuan = tfakuan + b.getFakuanprice();
			tpaisong = tpaisong + b.getPaisongprice();
			tzhuanjian = tzhuanjian + b.getZhuanjianprice();
			tdiujian = tdiujian + b.getDiujianprice();
			twhole = twhole + b.getWholeprice();
			tnum = tnum + b.getNum();
			mnum = mnum + 1;
			Cell cell = row.createCell(0);
			String address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getYear() + "-" + b.getMonth());
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getManager());
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getNum());
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getJijianprice());
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getWeightprice());
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getBuzuprice());
			cell.setCellStyle(style);

			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getTransformprice());
			cell.setCellStyle(style);

			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getFakuanprice());
			cell.setCellStyle(style);

			cell = row.createCell(8);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getPaisongprice());
			cell.setCellStyle(style);

			cell = row.createCell(9);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getZhuanjianprice());
			cell.setCellStyle(style);

			cell = row.createCell(10);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getDiujianprice());
			cell.setCellStyle(style);

			cell = row.createCell(11);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getWholeprice());
			cell.setCellStyle(style);

		}

		if (total == mnum) {

			double whole = 0;
			Row row = sh.createRow(total + 1);
			Cell cell = row.createCell(0);
			String address = new CellReference(cell).formatAsString();
			cell.setCellValue("");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("合计");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tnum);
			cell.setCellStyle(style);

			BigDecimal bb = new BigDecimal(tjijian);
			// 保留2位小数
			tjijian = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tjijian);
			cell.setCellStyle(style);

			bb = new BigDecimal(tweightprice);
			// 保留2位小数
			tweightprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tweightprice);
			cell.setCellStyle(style);

			bb = new BigDecimal(tbuzhufei);
			// 保留2位小数
			tbuzhufei = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tbuzhufei);
			cell.setCellStyle(style);

			bb = new BigDecimal(ttransformprice);
			// 保留2位小数
			ttransformprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(ttransformprice);
			cell.setCellStyle(style);

			bb = new BigDecimal(tfakuan);
			// 保留2位小数
			tfakuan = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tfakuan);
			cell.setCellStyle(style);

			bb = new BigDecimal(tpaisong);
			// 保留2位小数
			tpaisong = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			cell = row.createCell(8);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tpaisong);
			cell.setCellStyle(style);

			cell = row.createCell(9);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tzhuanjian);
			cell.setCellStyle(style);

			cell = row.createCell(10);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tdiujian);
			cell.setCellStyle(style);

			bb = new BigDecimal(ttransformprice + tbuzhufei + tweightprice
					+ tjijian + tfakuan + tpaisong + tzhuanjian + tdiujian);
			// 保留2位小数
			whole = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			cell = row.createCell(11);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(whole);
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
