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

import com.yundastat.model.CompanyIncome;
import com.yundastat.model.DailyBalance;
import com.yundastat.model.MailFenpei;
import com.yundastat.model.StatReport;
import com.yundastat.util.DateUtil;

public class Excel2007WriterMailPriceMonthByDay {

	private Workbook wb;
	private Sheet sh;
	private int flag;
	private int mnum = 0;

	private int tnum = 0;
	private double tprice = 0;
	private int tpnum = 0;
	private double tpprice = 0;
	private int tdnum = 0;
	private double tdprice = 0;
	private int tcnum = 0;
	private double tcprice = 0;
	private int trnum = 0;
	private double trprice = 0;
	private int tfnum = 0;
	private double tfprice = 0;

	public void createSheet() {
		wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows
										// will be flushed to disk
		sh = wb.createSheet();

		sh.setColumnWidth((short) 0, (short) 2580);
		sh.setColumnWidth((short) 1, (short) 3800);
		sh.setColumnWidth((short) 2, (short) 2600);
		sh.setColumnWidth((short) 3, (short) 1800);
		sh.setColumnWidth((short) 4, (short) 2800);
		sh.setColumnWidth((short) 6, (short) 1800);
		sh.setColumnWidth((short) 7, (short) 1800);
		sh.setColumnWidth((short) 8, (short) 1800);
		sh.setColumnWidth((short) 9, (short) 1800);
		sh.setColumnWidth((short) 10, (short) 1800);
		sh.setColumnWidth((short) 11, (short) 1800);
		sh.setColumnWidth((short) 12, (short) 1800);

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

			Cell cell = row.createCell(0);
			String address = new CellReference(cell).formatAsString();
			cell.setCellValue("日期");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("普通面单票数");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("普通面单金额");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("电子面单票数");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("电子面单金额");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("菜鸟面单票数");
			cell.setCellStyle(style);

			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("菜鸟面单金额");
			cell.setCellStyle(style);
			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("热敏纸数");
			cell.setCellStyle(style);

			cell = row.createCell(8);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("热敏纸金额");
			cell.setCellStyle(style);

			cell = row.createCell(9);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("防水袋数");
			cell.setCellStyle(style);

			cell = row.createCell(10);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("防水袋金额");
			cell.setCellStyle(style);

			cell = row.createCell(11);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("总票数");
			cell.setCellStyle(style);

			cell = row.createCell(12);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("总金额");
			cell.setCellStyle(style);

			flag = 1;

		}

		for (int rownum = 0; rownum < len; rownum++) {
			MailFenpei b = (MailFenpei) dataList.get(rownum);
			Row row = sh.createRow(startnum + rownum + 1);

			tpnum = tpnum + b.getPnum();
			tpprice = tpprice + b.getPprice();
			tdnum = tdnum + b.getDnum();
			tdprice = tdprice + b.getDprice();
			tcnum = tcnum + b.getCnum();
			tcprice = tcprice + b.getCprice();
			trprice = trprice + b.getRprice();
			trnum = trnum + b.getRnum();

			tfprice = tfprice + b.getFprice();
			tfnum = tfnum + b.getFnum();

			tprice = tprice + b.getPrice();
			tnum = tnum + b.getNum();
			mnum = mnum + 1;
			Cell cell = row.createCell(0);
			String address = new CellReference(cell).formatAsString();
			cell.setCellValue(DateUtil.formatYYYYMMDD(b.getBsndate()));
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getPnum());
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getPprice());
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getDnum());
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getDprice());
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getCnum());
			cell.setCellStyle(style);

			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getCprice());
			cell.setCellStyle(style);

			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getRnum());
			cell.setCellStyle(style);

			cell = row.createCell(8);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getRprice());
			cell.setCellStyle(style);

			cell = row.createCell(9);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getFnum());
			cell.setCellStyle(style);

			cell = row.createCell(10);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getFprice());
			cell.setCellStyle(style);

			cell = row.createCell(11);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getNum());
			cell.setCellStyle(style);

			cell = row.createCell(12);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getPrice());
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
			cell.setCellValue(tpnum);
			cell.setCellStyle(style);

			BigDecimal bb = new BigDecimal(tpprice);
			// 保留2位小数
			tpprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tpprice);
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tdnum);
			cell.setCellStyle(style);

			bb = new BigDecimal(tdprice);
			// 保留2位小数
			tdprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tdprice);
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tcnum);
			cell.setCellStyle(style);

			bb = new BigDecimal(tcprice);
			// 保留2位小数
			tcprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tcprice);
			cell.setCellStyle(style);

			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(trnum);
			cell.setCellStyle(style);

			bb = new BigDecimal(trprice);
			trprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			cell = row.createCell(8);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(trprice);
			cell.setCellStyle(style);

			cell = row.createCell(9);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tfnum);
			cell.setCellStyle(style);

			bb = new BigDecimal(tfprice);
			tfprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			cell = row.createCell(10);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tfprice);
			cell.setCellStyle(style);

			cell = row.createCell(11);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tnum);
			cell.setCellStyle(style);

			bb = new BigDecimal(tprice);
			tprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			cell = row.createCell(12);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(tprice);
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
