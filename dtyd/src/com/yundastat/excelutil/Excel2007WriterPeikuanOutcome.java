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
import com.yundastat.model.FankuanOutcome;
import com.yundastat.model.GongziOutcome;
import com.yundastat.model.MailFenpei;
import com.yundastat.model.PeikuanOutcome;
import com.yundastat.model.StatReport;
import com.yundastat.model.ZhichuOutcome;
import com.yundastat.util.DateUtil;

public class Excel2007WriterPeikuanOutcome {

	private Workbook wb;
	private Sheet sh;
	private int flag;
	private int mnum = 0;
	private double ttransformprice = 0;
	private int tnum = 0;
	private double tprice = 0;

	public void createSheet() {
		wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows
										// will be flushed to disk
		sh = wb.createSheet();

		sh.setColumnWidth((short) 0, (short) 2580);
		sh.setColumnWidth((short) 1, (short) 3800);
		sh.setColumnWidth((short) 2, (short) 2600);
		sh.setColumnWidth((short) 3, (short) 2800);
		sh.setColumnWidth((short) 4, (short) 2800);
		sh.setColumnWidth((short) 5, (short) 2800);
		sh.setColumnWidth((short) 6, (short) 2800);
		sh.setColumnWidth((short) 7, (short) 2800);
		sh.setColumnWidth((short) 8, (short) 2800);

		flag = 0;
	}

	public void process(String filename, List dataList, List smyList,
			int startnum, int total) throws Exception {

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
			cell.setCellValue("序号");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("日期");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("员工名字");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("票数");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("应赔金额");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("我司赔付金额");
			cell.setCellStyle(style);

			flag = 1;

		}

		int len = 0;

		if (smyList != null && smyList.size() > 0)
			len = smyList.size();

		for (int rownum = 0; rownum < len; rownum++) {
			PeikuanOutcome b = (PeikuanOutcome) smyList.get(rownum);
			Row row = sh.createRow(startnum + rownum + 1);

			ttransformprice = ttransformprice + b.getYingpeiprice();
			tprice=tprice+b.getRealpeiprice();

			mnum = mnum + 1;
			Cell cell = row.createCell(0);
			String address = new CellReference(cell).formatAsString();
			cell.setCellValue(startnum + rownum + 1);
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getYear() + "年" + b.getMonth() + "月");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getManagername());
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getNum());
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getYingpeiprice());
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getRealpeiprice());
			cell.setCellStyle(style);

		}
		
		Row row = sh.createRow(total+1);
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

		BigDecimal bb = new BigDecimal(ttransformprice);
		// 保留2位小数
		ttransformprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		cell = row.createCell(4);
		address = new CellReference(cell).formatAsString();
		cell.setCellValue(ttransformprice);
		cell.setCellStyle(style);

		 bb = new BigDecimal(tprice);
			// 保留2位小数
		 tprice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		
		cell = row.createCell(5);
		address = new CellReference(cell).formatAsString();
		cell.setCellValue(tprice);
		cell.setCellStyle(style);
		

		if (flag == 1) {
			row = sh.createRow(total+3);

			cell = row.createCell(0);
		    address = new CellReference(cell).formatAsString();
			cell.setCellValue("序号");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("日期");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("单号");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("揽件人");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("金额");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("原因");
			cell.setCellStyle(style);
			
			
			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("处理情况");
			cell.setCellStyle(style);
			
			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("目的地");
			cell.setCellStyle(style);
			
			cell = row.createCell(8);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue("备注");
			cell.setCellStyle(style);

			flag = 2;

		}
		total=total+3;
		

		if (dataList != null && dataList.size() > 0)
			len = dataList.size();
		for (int rownum = 0; rownum < len; rownum++) {
			PeikuanOutcome b = (PeikuanOutcome) dataList.get(rownum);
			row = sh.createRow(total + rownum + 1);

			 
			mnum = mnum + 1;
			cell = row.createCell(0);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(startnum + rownum + 1);
			cell.setCellStyle(style);

			cell = row.createCell(1);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(DateUtil.formatYYYYMMDD(b.getBsndate()));
			cell.setCellStyle(style);

			cell = row.createCell(2);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getMailid());
			cell.setCellStyle(style);

			cell = row.createCell(3);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getManagername());
			cell.setCellStyle(style);

			cell = row.createCell(4);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getYingpeiprice());
			cell.setCellStyle(style);

			cell = row.createCell(5);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getReason());
			cell.setCellStyle(style);
			
			cell = row.createCell(6);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getDealstate());
			cell.setCellStyle(style);
			
			cell = row.createCell(7);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getDest());
			cell.setCellStyle(style);
			
			
			cell = row.createCell(8);
			address = new CellReference(cell).formatAsString();
			cell.setCellValue(b.getMemo());
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
