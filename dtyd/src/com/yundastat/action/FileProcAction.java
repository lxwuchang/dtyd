/**
 * 
 */
package com.yundastat.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.CellView;
import jxl.Workbook;
import jxl.Cell;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.*;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.yundastat.excelutil.Excel2007Writer;
import com.yundastat.excelutil.ExcelReaderUtil;
import com.yundastat.excelutil.ExcelWriterUtil;
import com.yundastat.excelutil.IRowReader;
import com.yundastat.excelutil.MailReader;
import com.yundastat.excelutil.MailReaderNew;
import com.yundastat.excelutil.PriceReader;
import com.yundastat.excelutil.RowReader;
import com.yundastat.framework.UserHolder;
import com.yundastat.model.CompareReport;
import com.yundastat.model.DailyBalance;
import com.yundastat.model.MailInfo;
import com.yundastat.model.ManagerInfo;
import com.yundastat.model.PageBean;
import com.yundastat.model.PageParameter;
import com.yundastat.model.PriceDetailSmy;
import com.yundastat.model.ReportFlag;
import com.yundastat.model.StatReport;
import com.yundastat.model.StatSMY;
import com.yundastat.model.TransformCategory;
import com.yundastat.model.TransformMail;
import com.yundastat.model.TransformPriceRule;
import com.yundastat.model.User;
import com.yundastat.model.WeightPriceRule;
import com.yundastat.model.YearSmy;
import com.yundastat.service.MailService;
import com.yundastat.service.ReportService;
import com.yundastat.util.DateUtil;
import com.yundastat.util.FileUploadUtil;

/**
 * @author KJX843
 * 
 */
public class FileProcAction extends BaseAction {

	private static final long serialVersionUID = 5943699833329252614L;
	private File upexcel;
	private String upexcelContentType;
	private String upexcelFilename;
	private List<CompareReport> compareReportList = new ArrayList();
	private List attrNameList = new ArrayList();
	private List<MailInfo> mailList = new ArrayList();
	private List<TransformMail> mailTransformList = new ArrayList();
	private List numList = new ArrayList();
	private String importDate;
	private List<StatReport> statReportList = new ArrayList();
	private String messageInfo;
	private PageBean pageBean;
	private int totalN;
	private StatSMY flag;
	public final static int pageSize = 10;
	private List<StatSMY> reportFlagList = new ArrayList();
	private List<PriceDetailSmy> priceDetailSmyList = new ArrayList();

	public List<StatSMY> getReportFlagList() {
		return reportFlagList;
	}

	public void setReportFlagList(List<StatSMY> reportFlagList) {
		this.reportFlagList = reportFlagList;
	}

	public List<PriceDetailSmy> getPriceDetailSmyList() {
		return priceDetailSmyList;
	}

	public void setPriceDetailSmyList(List<PriceDetailSmy> priceDetailSmyList) {
		this.priceDetailSmyList = priceDetailSmyList;
	}

	public StatSMY getFlag() {
		return flag;
	}

	public void setFlag(StatSMY flag) {
		this.flag = flag;
	}

	public int getTotalN() {
		return totalN;
	}

	public void setTotalN(int totalN) {
		this.totalN = totalN;
	}

	public List<CompareReport> getCompareReportList() {
		return compareReportList;
	}

	public void setCompareReportList(List<CompareReport> compareReportList) {
		this.compareReportList = compareReportList;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List<TransformMail> getMailTransformList() {
		return mailTransformList;
	}

	public void setMailTransformList(List<TransformMail> mailTransformList) {
		this.mailTransformList = mailTransformList;
	}

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	public List<StatReport> getStatReportList() {
		return statReportList;
	}

	public void setStatReportList(List<StatReport> statReportList) {
		this.statReportList = statReportList;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	private MailService mailService;

	private ReportService reportService;

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List getNumList() {
		return numList;
	}

	public void setNumList(List numList) {
		this.numList = numList;
	}

	public List<MailInfo> getMailList() {
		return mailList;
	}

	public void setMailList(List<MailInfo> mailList) {
		this.mailList = mailList;
	}

	public List getAttrNameList() {
		return attrNameList;
	}

	public void setAttrNameList(List attrNameList) {
		this.attrNameList = attrNameList;
	}

	public File getUpexcel() {
		return upexcel;
	}

	public void setUpexcel(File excelFile) {
		this.upexcel = excelFile;
	}

	public String getUpexcelContentType() {
		return upexcelContentType;
	}

	public void setUpexcelContentType(String contentType) {
		this.upexcelContentType = contentType;
	}

	public String getUpexcelFilename() {
		return upexcelFilename;
	}

	public void setUpexcelFilename(String filename) {
		this.upexcelFilename = filename;
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub

		return INPUT;
	}

	public String downFile() throws WriteException, IOException {
		// 下载报表；

		String dateName = this.getImportDate();

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String filename = new String(request.getParameter("filename").getBytes(
				"ISO8859-1"), "utf-8");
		
		System.out.println(filename);
		String[] para = filename.split("@");
		String name = para[0];
		String datet = para[1];
		filename = datet + "/" + datet + "_" + name + ".xls";
		String filenamexlsx = datet + "/" + datet + "_" + name + ".xlsx";
		String filename2 = datet + "_" + name + ".xls";
		String filename2xlsx = datet + "_" + name + ".xlsx";

		String path = this.getEnv("downpath") + "/" + filename;// 2011-06-08_张三.xls
		String pathxlsx = this.getEnv("downpath") + "/" + filenamexlsx;// 2011-06-08_张三.xlsx
		
		System.out.println(pathxlsx);
		int flag = 0;
		File file = new File(path);
		if (!file.exists()) {

			file = new File(pathxlsx);
			if (!file.exists()) {
				this.addErrorMessage("hello", "文件不存在！");
				return REPORT_DETAIL;
			} else {
				flag = 1;

			}

		}

		response.setContentType("application/vnd.ms-excel");

		if (flag == 0)
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(filename2.getBytes("gb2312"), "iso8859-1"));
		else
			response
					.setHeader("Content-disposition", "attachment; filename="
							+ new String(filename2xlsx.getBytes("gb2312"),
									"iso8859-1"));
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			if (flag == 0)
				bis = new BufferedInputStream(new FileInputStream(path));
			else
				bis = new BufferedInputStream(new FileInputStream(pathxlsx));
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[1024];
			int bytesRead;

			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
				bos.write(buff, 0, bytesRead);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (bis != null) {

				bis.close();
			}

			if (bos != null) {

				bos.close();
			}

		}

		// 传入manager startdate

		// 获取数据

		// 生成的报表的命名:

		return INPUT;
	}

	public String uploadTransformFile() throws IOException, ParseException,
			SQLException {

		user = UserHolder.getUser();
		Date dat = new Date();
		String dateName = DateUtil.formatYYYYMMDD(dat);
		importDate = dateName;
		FileUploadUtil.uploadFile(upexcel, "xls", dateName + "中转站", this
				.getEnv("tranformexcel"), "/");

		String path = this.getEnv("tranformexcel") + "/" + dateName + "中转站"
				+ ".xls";
		Workbook rwb = null;
		InputStream is = null;
		int record = 0;
		int recordNum = 0;
		try {
			is = new FileInputStream(path);

			try {

				rwb = Workbook.getWorkbook(is);
				Sheet sh = rwb.getSheet(0);
				int rowN = sh.getRows();
				Date createTime = DateUtil.parse(dateName + " 12:00:00",
						DateUtil.DATE_TIME_FORMAT);
				mailTransformList = new ArrayList();

				for (int j = 1; j < rowN; j++) {
					record++;
					recordNum++;
					System.out.println(j);
					TransformMail mail = new TransformMail();
					Cell[] cells = sh.getRow(j);
					String linenum = cells[0].getContents();
					String mailid = cells[1].getContents().trim();

					Date inhousetime = DateUtil.parse(cells[2].getContents(),
							DateUtil.DATE_TIME_FORMAT);
					Date scantime = DateUtil.parse(cells[3].getContents(),
							DateUtil.DATE_TIME_FORMAT);

					String manager = cells[4].getContents();
					String managercorp = cells[5].getContents();
					String sendcorp = cells[6].getContents();
					String type = cells[7].getContents();
					double weight = 0;
					double stransfromprice = 0;
					double sstransfromprice = 0;
					double errorsend = 0;
					double bucha = 0;
					double returnmail = 0;
					double wholeprice = 0;
					try {
						weight = Double.parseDouble(cells[8].getContents()) / 1000;
						stransfromprice = Double.parseDouble(cells[9]
								.getContents());
						sstransfromprice = Double.parseDouble(cells[10]
								.getContents());
						if (cells[11].getContents() != null
								&& !cells[11].getContents().equals(""))
							errorsend = Double.parseDouble(cells[11]
									.getContents());
						if (cells[11].getContents() != null
								&& !cells[11].getContents().equals(""))
							bucha = Double.parseDouble(cells[12].getContents());
						if (cells[11].getContents() != null
								&& !cells[11].getContents().equals(""))
							returnmail = Double.parseDouble(cells[13]
									.getContents());

						wholeprice = Double
								.parseDouble(cells[14].getContents());

					} catch (NumberFormatException e) {
						e.printStackTrace();
						this.addErrorMessage("hello", "其中存在快件重量数值格式异常");
						return FINIMPORT;
					}

					mail.setLinenum(linenum);
					mail.setMailid(mailid);
					mail.setInhousedate(inhousetime);
					mail.setScandate(scantime);
					mail.setManager(manager);
					mail.setManagercorp(managercorp);
					mail.setSendcorp(sendcorp);
					mail.setType(type);
					mail.setWeight(weight);
					mail.setStransformprice(stransfromprice);
					mail.setSstransformprice(sstransfromprice);
					mail.setErrorsend(errorsend);
					mail.setBucha(bucha);
					mail.setReturnmail(returnmail);
					mail.setWholeprice(wholeprice);

					mail.setCreatetime(createTime);
					mail.setIsdeleted("n");
					mailTransformList.add(record - 1, mail);
					if (record == 500) {// 每达到500条，则到数据库中去查找所需要的信息。
						record = 0;
						try {
							mailService.insertTransformMail(mailTransformList);

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}

						List<CompareReport> creportList = new ArrayList();
						for (int k = 0; k < mailTransformList.size(); k++) {

							TransformMail m = mailTransformList.get(k);
							// List<StatReport>
							// reportList=mailService.queryForMailByMailId(m.getMailid(),null);
							List<StatReport> reportList = null;
							CompareReport c = new CompareReport();
							c.setIsdeleted("n");
							c.setCreatetime(createTime);
							c.setMailid(m.getMailid());
							c.setTmanager(m.getManager());
							c.setTransformcorp(m.getSendcorp());
							c.setTweight(m.getWeight());
							c.setWholeprice(m.getWholeprice());
							c.setStransformprice(m.getStransformprice());
							c.setSstransformprice(m.getSstransformprice());
							c.setTscandate(m.getScandate());
							c.setInhousedate(m.getInhousedate());
							if (reportList == null || reportList.size() == 0) {

								// 本地

								c.setLdestinationcity("");
								c.setLmanager("");
								c.setLweight(0);
								c.setLtransformprice(0);
								c.setLscandate(null);
								c.setState("no");// 表示未匹配

							} else {

								StatReport s = reportList.get(0);
								c.setLdestinationcity(s.getDestinationcity());
								c.setLmanager(s.getManager());
								c.setLweight(s.getWeight());
								c.setLtransformprice(s.getTransformprice());
								c.setLscandate(s.getSendtime());
								c.setState("yes");// 表示未匹配
							}

							creportList.add(c);

						}
						mailService.insertCompareReportList(creportList);

						mailTransformList = new ArrayList();

						// 在500行内

					}

				}

				try {
					mailService.insertTransformMail(mailTransformList);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

				List<CompareReport> creportList = new ArrayList();
				int len = mailTransformList.size();
				for (int k = 0; k < len; k++) {

					TransformMail m = mailTransformList.get(k);
					// List<StatReport>
					// reportList=mailService.queryForMailByMailId(m.getMailid(),null);
					List<StatReport> reportList = null;
					CompareReport c = new CompareReport();
					c.setIsdeleted("n");
					c.setCreatetime(createTime);
					c.setMailid(m.getMailid());
					c.setTmanager(m.getManager());
					c.setTransformcorp(m.getSendcorp());
					c.setTweight(m.getWeight());
					c.setWholeprice(m.getWholeprice());
					c.setStransformprice(m.getStransformprice());
					c.setSstransformprice(m.getSstransformprice());
					c.setTscandate(m.getScandate());
					c.setInhousedate(m.getInhousedate());

					if (reportList == null || reportList.size() == 0) {

						// 本地

						c.setLdestinationcity("");
						c.setLmanager("");
						c.setLweight(0);
						c.setLtransformprice(0);
						c.setLscandate(null);
						c.setState("no");// 表示未匹配

					} else {

						StatReport s = reportList.get(0);
						c.setLdestinationcity(s.getDestinationcity());
						c.setLmanager(s.getManager());
						c.setLweight(s.getWeight());
						System.out.println(m.getManagercorp() + "2.3");
						c.setLtransformprice(s.getTransformprice());
						c.setLscandate(s.getSendtime());
						c.setState("yes");// 表示未匹配

					}

					creportList.add(c);

				}

				mailService.insertCompareReportList(creportList);

			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

			rwb.close();
			is.close();

		}

		PageParameter pe = new PageParameter();
		pe.setStart(0);
		pe.setSize(recordNum);
		pe.setEndtime(dateName + " 12:00:00");
		pe.setStarttime(dateName + " 12:00:00");
		pe.setState(null);

		compareReportList = mailService.queryForCompareReportPage(pe);

		totalN = compareReportList.size();
		// this.createCompareExcelFile(statReportList, null, dateName + "_全部",
		// allStatReport);
		// 

		// 分页显示

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(pageSize);
		p.setEndtime(dateName + " 12:00:00");
		p.setStarttime(dateName + " 12:00:00");
		compareReportList = mailService.queryForCompareReportPage(p);
		pageBean = new PageBean(pageSize, 1, recordNum);

		return TRANSFORM_IMPORT;
	}

	public String searchComparePage() throws UnsupportedEncodingException {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		importDate = new String(request.getParameter("importDate").getBytes(
				"ISO8859-1"), "utf-8");

		String pageNo = new String(request.getParameter("pageNo").getBytes(
				"ISO8859-1"), "utf-8");

		String totalNum = new String(request.getParameter("totalNum").getBytes(
				"ISO8859-1"), "utf-8");

		int pageN = Integer.parseInt(pageNo);
		totalN = Integer.parseInt(totalNum);

		String starttime = importDate + " " + "12:00:00";
		String endtime = importDate + " " + "12:00:00";

		PageParameter page2 = new PageParameter();

		page2.setEndtime(endtime);
		page2.setStarttime(starttime);
		page2.setSize(pageSize);
		page2.setStart((pageN - 1) * 100);

		compareReportList = mailService.queryForCompareReportPage(page2);

		pageBean = new PageBean(pageSize, pageN, totalN);
		return TRANSFORM_IMPORT;
	}

	public String uploadMoreCategoryFileNew() throws ParseException,
			IOException, SQLException, WriteException, BiffException {

		user = UserHolder.getUser();
		String dateName = this.getImportDate();
		Date bsndate = DateUtil.parse(dateName + " 12:00:00",
				DateUtil.DATE_TIME_FORMAT);
		Date reportdate = DateUtil.parse(dateName + " 00:00:00",
				DateUtil.DATE_TIME_FORMAT);
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String ftype = request.getParameter("ftype");// 1:表示xls,2表示 xlsx

		// 先要判定当天的报表是否已经生成-->目前的功能是如果当天的报表已经存在，则要求确认先将那天的数据先删除。

		PageParameter p2 = new PageParameter();
		p2.setBsndate(bsndate);
		p2.setSize(Integer.MAX_VALUE);
		p2.setStart(0);

		List<StatSMY> flg = mailService.queryReportFlagByPage(p2);

		if (flg != null && flg.size() == 1
				&& flg.get(0).getReportdate().equals(this.getImportDate())) {

			flag = flg.get(0);
			importDate = request.getParameter("importDate");
			String startTime = importDate + " 00:00:00";
			String endTime = importDate + " 23:59:59";
			int mon = Integer.parseInt(importDate.substring(5, 7));

			PageParameter pageParameter = new PageParameter();
			pageParameter.setStarttime(startTime);
			pageParameter.setEndtime(endTime);
			pageParameter.setBsndate(bsndate);
			pageParameter.setMon(mon);
			mailService.deleteExitedMailAndStatReport(pageParameter);

		} else {

			flag = new StatSMY();
			flag.setReportdate(dateName);
			flag.setBsndate(bsndate);
			mailService.createReportFlag(flag);

		}

		HashSet set = new HashSet();

		// 而计重规则则是根据isactive=y取出目前正在使用的计重规则。
		WeightPriceRule weightPriceRule = new WeightPriceRule();
		weightPriceRule.setIsdeleted("n");

		List<WeightPriceRule> weightPriceRuleList = mailService
				.queryForWeightPriceRuleList(weightPriceRule);
		if (weightPriceRuleList == null || weightPriceRuleList.size() == 0) {
			this.addErrorMessage("hello", "请先设置计重费规则或者启用计重费规则！");
			return FINIMPORT;

		}

		HashMap<String, WeightPriceRule> weightMap = new HashMap<String, WeightPriceRule>();
		HashMap<String, WeightPriceRule> userWeightMap = new HashMap<String, WeightPriceRule>();
		for (int i = 0; i < weightPriceRuleList.size(); i++) {

			weightMap.put(weightPriceRuleList.get(i).getWeightpriceruleid()
					+ "", weightPriceRuleList.get(i));

			if (weightPriceRuleList.get(i).getWeightpricename().equals("默认计重")) {
				weightPriceRule = weightPriceRuleList.get(i);

			}

		}

		// 中转费的信息，则是非常复杂多样，有单价式的，也有首重+续重式的。//只需要选出isdeleted='n'的中转费信息即可。

		// TransformCategory
		TransformCategory c = new TransformCategory();
		List<TransformCategory> transformCategoryList = mailService
				.getTransformCategory(c);

		String defaultPrice = "";

		for (int kk = 0; kk < transformCategoryList.size(); kk++) {

			if (transformCategoryList.get(kk).getTransformcategoryname()
					.equals("默认价格")) {
				defaultPrice = transformCategoryList.get(kk)
						.getTransformcategoryid()
						+ "";
				break;
			}

		}
		Map<String, List<TransformPriceRule>> transformPriceMap = new HashMap<String, List<TransformPriceRule>>();
		if (transformCategoryList == null || transformCategoryList.size() == 0) {
			this.addErrorMessage("hello", "请先设置中转费价格方案！");
			return FINIMPORT;
		}

		int len = transformCategoryList.size();
		for (int kk = 0; kk < len; kk++) {
			TransformCategory cc = transformCategoryList.get(kk);
			TransformPriceRule r = new TransformPriceRule();
			r.setIsdeleted("n");
			r.setTransformcategoryid(cc.getTransformcategoryid());
			List<TransformPriceRule> ransformricerulelist = mailService
					.queryForTransformPriceRuleList(r);

			transformPriceMap.put(cc.getTransformcategoryid() + "",
					ransformricerulelist);

		}

		    ManagerInfo  m = new ManagerInfo();
			m.setIsdeleted("n");
			m.setRoles("statuser");
			List<ManagerInfo> managerInfoList = mailService.queryForManagerInfoNew(m);
	  
		HashMap<String, String> managerMap = new HashMap<String, String>();
		HashMap<String, String> managerCategoryMap = new HashMap<String, String>();
		HashSet<String> sets = new HashSet<String>();
		int kk = 0;
		if (managerInfoList != null && managerInfoList.size() > 0) {
			kk = managerInfoList.size();
			for (int i = 0; i < managerInfoList.size(); i++) {
				managerMap.put(managerInfoList.get(i).getManagername(),
						managerInfoList.get(i).getManagerid());
				managerCategoryMap.put(managerInfoList.get(i).getManagerid(),
						managerInfoList.get(i).getSolutionid());

				WeightPriceRule w = weightMap.get(managerInfoList.get(i)
						.getWeightpriceruleid());
				userWeightMap.put(managerInfoList.get(i).getManagerid(), w);
				sets.add(managerInfoList.get(i).getManagername());
			}

		}
		// 中转费的设置：要根据目的地。
		double alltransformprice = 0;
		double alloperateprice = 0;
		double allweightprice = 0;
		double allbuzufei = 0;
		double allwholeprice = 0;
		double allweight = 0;
		double allchujianfei = 0;
		double allscanfei = 0;

		double scanfei = 0;

		importDate = dateName;

		int mon = Integer.parseInt(importDate.substring(5, 7));
		int year = Integer.parseInt(importDate.substring(0, 4));
		String path = null;
		IRowReader mailReader = null;
		if (ftype.equals("xls")) {
			// 先弄xls
			FileUploadUtil.uploadFile(upexcel, "xls", dateName, this
					.getEnv("path"), "/");

			path = this.getEnv("path") + "/" + dateName + ".xls";

			Workbook rwb = null;
			InputStream is = null;

			List<MailInfo> allMailList = new ArrayList();

			// 在将数据插入到数据库中前，要扫描数据库中是否已经有当天的数据
			// 如果有，则将原来的update为deleted。--》由于为追加模式，不需要将当天的数据删除

			Date createTime = DateUtil.parse(dateName + " 12:00:00",
					DateUtil.DATE_TIME_FORMAT);

			int record = 0;
			int recordNum = 0;

			try {
				is = new FileInputStream(path);

				rwb = Workbook.getWorkbook(is);
				Sheet sh = rwb.getSheet(0);
				int rowN = sh.getRows();

				for (int j = 1; j < rowN; j++) {
					record++;
					recordNum++;
					MailInfo mail = new MailInfo();
					Cell[] cells = sh.getRow(j);
					if (cells == null
							|| cells.length == 0
							|| (cells.length > 0 && cells[0].getContents()
									.trim().equals(""))) {
						record = 500;
					} else {

						String mailid = cells[0].getContents();
						String manager = cells[1].getContents().trim();

						if (manager.indexOf("】") != -1) {

							manager = manager
									.substring(manager.indexOf("】") + 1);
						}
						if (manager != null && !manager.equals(""))
							set.add(manager);

						Date sendtime = null;
						String datetime = cells[2].getContents();

						if (datetime.indexOf("/") != -1) {
							String years[] = datetime.split("/");

							sendtime = DateUtil.parse(years[2] + "-" + years[1]
									+ "-" + years[0] + " " + years[3] + ":"
									+ years[4] + ":00",
									DateUtil.DATE_TIME_FORMAT);

						}

						String subcompany = cells[3].getContents().trim();

						if (subcompany.indexOf("】") != -1) {

							subcompany = subcompany.substring(subcompany
									.indexOf("】") + 1);
						}
						double weight = 0;

						try {
							weight = Double.parseDouble(cells[4].getContents());

						} catch (Exception e) {
							e.printStackTrace();

							return FINIMPORT;
						}

						mail = new MailInfo();
						mail.setMailid(mailid);
						mail.setManager(manager);
						mail.setSubcompany(subcompany);
						mail.setWeight(weight);
						mail.setCreatetime(createTime);
						mail.setIsdeleted("n");
						mail.setSendtime(sendtime);

						allMailList.add(record - 1, mail);
					}
					// 每500条插入一次

					if (record == 500 || rowN == (recordNum + 1)) {

						for (int i = 0; i < allMailList.size(); i++) {
							mail = allMailList.get(i);
							allweight = allweight + mail.getWeight();
							double transformPrice = 0;
							double buzufei = 0;
							// 得出重量区间，从而得到一个index
							double weightPrice = 0;

							// 计重 0,15,m,0;15,30,m,1;30,9999,m,2

							StatReport s = MailReaderNew.calPriceNew(mail,
									managerMap, defaultPrice, weightPriceRule,
									managerCategoryMap, userWeightMap,
									transformPriceMap);

							alltransformprice = alltransformprice
									+ s.getTransformprice();
							allweightprice = allweightprice
									+ s.getWeightprice();
							allchujianfei = allchujianfei + 0;
							allscanfei = allscanfei + s.getScanfei();
							allbuzufei = allbuzufei + s.getBuzufei();
							// 得出一个
							StatReport statReport = new StatReport();
							statReport.setMailid(mail.getMailid());

							statReport.setTransformprice(s.getTransformprice());
							statReport.setWeightprice(s.getWeightprice());
							statReport.setManager(mail.getManager());
							statReport.setBuzufei(s.getBuzufei());
							statReport.setScanfei(s.getScanfei());
							statReport.setSendtime(mail.getSendtime());
							statReport.setWeight(mail.getWeight());
							statReport.setChujianfei(0);
							statReport.setIsdeleted("n");
							statReport.setOperateprice(0);
							statReport.setDestinationcity(mail.getSubcompany());// 目前设置目标组为中转站:条码显示实名
							statReport.setCreatetime(createTime);
							statReport.setState("normal");
							statReport.setMon(mon);
							double whole = weightPrice + transformPrice
									+ scanfei + buzufei;
							BigDecimal b = new BigDecimal(whole);
							// 保留2位小数
							whole = b.setScale(2, BigDecimal.ROUND_HALF_UP)
									.doubleValue();
							allwholeprice = allwholeprice + whole;
							statReport.setWholeprice(whole);

							mailService.createStatReport(statReport);

						}

						allMailList = new ArrayList();
						record = 0;

					}

				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.addErrorMessage("hello", "文件不存在！");
				return FINIMPORT;

			} finally {
				if (rwb != null)
					rwb.close();
				if (is != null)
					is.close();

			}

		} else {

			FileUploadUtil.uploadFile(upexcel, "xlsx", dateName, this
					.getEnv("path"), "/");

			path = this.getEnv("path") + "/" + dateName + ".xlsx";

			mailReader = new MailReaderNew(importDate, mailService, managerMap,
					defaultPrice, weightPriceRule, managerCategoryMap,
					userWeightMap, transformPriceMap, sets);

			try {
				ExcelReaderUtil.readExcel(mailReader, path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// 分页显示

		if (!mailReader.getManagerlist().equals("")||mailReader.getManagerDestSet().size()>0) {
			String pageNoStr = request.getParameter("pageNo");
			
			//delete mail			
			//delete statsmy			
			//delete reportflag
			String startTime = importDate + " 00:00:00";
			String endTime = importDate + " 23:59:59";
				

			
			PageParameter pageParameter = new PageParameter();
			pageParameter.setStarttime(startTime);
			pageParameter.setEndtime(endTime);
			pageParameter.setBsndate(bsndate);
			pageParameter.setMon(mon);
			mailService.deleteExitedMailAndStatReport(pageParameter);
		
		 
			StatSMY flgs = new StatSMY();
			flgs.setReportdate(importDate);
			mailService.deleteReportFlag(flgs);
			
			int start = 0;
			int pageN = 1;

			if (pageNoStr != null && !pageNoStr.equals(""))
				pageN = Integer.parseInt(pageNoStr);

			PageParameter p = new PageParameter();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			reportFlagList = mailService.queryReportFlagByPage(p);

			pageBean = new PageBean(pageSize, pageN, reportFlagList.size());

			p = new PageParameter();
			start = (pageN - 1) * 15;
			p.setStart(start);
			p.setSize(15);
        	reportFlagList = mailService.queryReportFlagByPage(p);
			

        	Iterator it=mailReader.getManagerDestSet().iterator();
        	String str="";
        	while(it.hasNext())
        		str=str+it.next()+"、";
        	
        	
        	String error1=mailReader.getManagerlist()+"用户不存在，先到用户管理中定义！";
        	String error2=str+"目的地未定义";
			
			String error="";
			
			if(!mailReader.getManagerlist().equals(""))
				error=error1;
			
			 if(mailReader.getManagerSet().size()>0)
				error=error+error2;
			this.addErrorMessage("hello", error);

			return "finimport";

		}
		PageParameter pp = new PageParameter();
		pp.setBsndate(bsndate);
		pp.setMon(mon);
		ReportFlag fl = mailService.getStatReportSmy(pp);

		// //生成报表
		// 读取weightRule，
		flag.setWholewholeprice(fl.getWholeprice());
		flag.setWholetransformprice(fl.getWholetransformprice());
		flag.setWholeweightprice(fl.getWholeweightprice());
		flag.setWholebuzufei(fl.getWholebuzufei());
		flag.setWholechujianfei(fl.getWholechujianfei());
		flag.setWholescanfei(fl.getWholescanfei());
		flag.setReportdate(dateName);
		flag.setWholeweight(fl.getWholeweight());
		flag.setWholenum(fl.getWholenum());

		mailService.updateReportFlag(flag);

		String starttime = importDate + " " + "12:00:00";
		String endtime = importDate + " " + "12:00:00";
		
		
		String downPath = this.getEnv("downpath") + "/" + importDate;
		File files = new File(downPath);

		if (!files.exists())
			files.mkdir();

		// 将所有揽件人的信息 输入到文件中

		String bsndates = importDate + " " + "12:00:00";

		Date dates = DateUtil.parse(bsndates, DateUtil.DATE_TIME_FORMAT);

		PageParameter pe = new PageParameter();
		pe.setMon(mon);
		pe.setBsndate(dates);
		mailService.createStatSMY(pe);

		// 汇总所有的揽件人的数据，插入到STATSMY

		String outfilename = this.getEnv("downpath") + "/" + importDate + "/"
				+ dateName + "_全部.xlsx";
		try {

			Excel2007Writer writer = new Excel2007Writer();
			writer.createSheet();
			pageBean = new PageBean(pageSize, 1, fl.getWholenum());

			pe = new PageParameter();

			pe.setEndtime(endtime);
			pe.setStarttime(starttime);
			pe.setMon(mon);

			int pagesize = fl.getWholenum() / 10000;

			if (pagesize * 10000 < fl.getWholenum()) {
				pagesize = pagesize + 1;
			}
			for (int i = 0; i < pagesize; i++) {
				pe.setStart(i * 10000);
				pe.setSize(10000);
				statReportList = mailService.queryForStatReportPage(pe);
				writer.process(outfilename, statReportList, i * 10000, fl
						.getWholenum());
			}

			writer.createFile(outfilename);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator itr = set.iterator();

		// 同时对每个人的信息进行汇总。插入到新的表中。
		// 生成汇总的excel
		PageParameter p = new PageParameter();
		p.setBsndate(dates);
		p.setManager(null);
		p.setMon(mon);
		List<StatSMY> statSMYList = mailService.queryForStatSMY(p);

		createSMYExcelFile(statSMYList, dateName + "/" + dateName + "_汇总", flag);

		for (int i = 0; i < statSMYList.size(); i++) {
			StatSMY smy = statSMYList.get(i);
			String manager = smy.getManager();
			String outfile = this.getEnv("downpath") + "/" + importDate + "/"
					+ dateName + "_" + manager + ".xlsx";
			try {

				Excel2007Writer writer = new Excel2007Writer();
				writer.createSheet();

				pe = new PageParameter();
				pe.setEndtime(endtime);
				pe.setStarttime(starttime);
				pe.setMon(mon);

				int pagesize = smy.getWholenum() / 10000;

				if (pagesize * 10000 < smy.getWholenum()) {
					pagesize = pagesize + 1;
				}
				for (int s = 0; s < pagesize; s++) {
					pe.setStart(s * 10000);
					pe.setSize(10000);
					pe.setManager(manager);
					statReportList = mailService.queryForStatReportPage(pe);
					writer.process(outfile, statReportList, s * 10000, smy
							.getWholenum());
				}

				writer.createFile(outfile);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 记录

		HashSet<String> managerSet = mailReader.getManagerSet();// managerSet
																// 表示报表里含有的揽件人的名字

		DailyBalance db = new DailyBalance();
		db.setBsndate(reportdate);
		List<DailyBalance> dailybalanceList = reportService.getDailyBalance(db);

		if (dailybalanceList != null && dailybalanceList.size() > 0) {

			int sk = 0;
			if (statSMYList != null && statSMYList.size() > 0)
				sk = statSMYList.size();
			for (int i = 0; i < sk; i++) {

				StatSMY smy = statSMYList.get(i);
				DailyBalance d = new DailyBalance();
				d.setBsndate(reportdate);
				d.setManager(smy.getManager());
				List<DailyBalance> dbList = reportService.getDailyBalance(d);

				if (dbList != null && dbList.size() > 0) {

					DailyBalance db2 = dbList.get(0);

					db2.setJijianprice(smy.getWholescanfei());

					db2.setWeightprice(smy.getWholeweightprice());

					db2.setBuzuprice(smy.getWholebuzufei());

					db2.setTransformprice(smy.getWholetransformprice());

					db2.setNum(smy.getWholenum());

					double wprice = db2.getJijianprice() + db2.getWeightprice()
							+ db2.getBuzuprice() + db2.getTransformprice()
							+ db2.getFakuanprice() + db2.getPaisongprice()
							+ db2.getZhuanjianprice() + db2.getDiujianprice();
					BigDecimal cc = new BigDecimal(wprice);
					// 保留2位小数
					wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					db2.setWholeprice(wprice);

					reportService.updateDailyBalance(db2);
				} else {

					DailyBalance db2 = new DailyBalance();
					db2.setBsndate(reportdate);
					db2.setManager(smy.getManager());
					db2.setYear(year);
					db2.setMonth(mon);
					db2.setJijianprice(smy.getWholescanfei());
					db2.setWeightprice(smy.getWholeweightprice());
					db2.setBuzuprice(smy.getWholebuzufei());
					db2.setTransformprice(smy.getWholetransformprice());
					db2.setNum(smy.getWholenum());
					db2.setFakuanprice(0);
					db2.setPaisongprice(0);
					db2.setZhuanjianprice(0);
					db2.setDiujianprice(0);
					db2.setCreatetime(new Date());
					db2.setStatus("normal");
					db2.setChujianfei(0);

					double wprice = db2.getJijianprice() + db2.getWeightprice()
							+ db2.getBuzuprice() + db2.getTransformprice()
							+ db2.getFakuanprice() + db2.getPaisongprice()
							+ db2.getZhuanjianprice() + db2.getDiujianprice();
					BigDecimal cc = new BigDecimal(wprice);
					// 保留2位小数
					wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					db2.setWholeprice(wprice);
					reportService.createDailyBalance(db2);

				}

			}

		} else {// 当日 ，按人汇总的数据没有时，则新增

			int sk = 0;
			if (statSMYList != null && statSMYList.size() > 0)
				sk = statSMYList.size();
			for (int i = 0; i < sk; i++) {
				StatSMY smy = statSMYList.get(i);
				// 存在则更新
				DailyBalance db2 = new DailyBalance();
				db2.setBsndate(reportdate);
				db2.setManager(smy.getManager());
				db2.setYear(year);
				db2.setMonth(mon);
				db2.setJijianprice(smy.getWholescanfei());
				db2.setWeightprice(smy.getWholeweightprice());
				db2.setBuzuprice(smy.getWholebuzufei());
				db2.setTransformprice(smy.getWholetransformprice());
				db2.setNum(smy.getWholenum());
				db2.setFakuanprice(0);
				db2.setPaisongprice(0);
				db2.setZhuanjianprice(0);
				db2.setDiujianprice(0);
				db2.setCreatetime(new Date());
				db2.setStatus("normal");
				db2.setChujianfei(0);

				double wprice = db2.getJijianprice() + db2.getWeightprice()
						+ db2.getBuzuprice() + db2.getTransformprice()
						+ db2.getFakuanprice() + db2.getPaisongprice()
						+ db2.getZhuanjianprice() + db2.getDiujianprice();
				BigDecimal cc = new BigDecimal(wprice);
				// 保留2位小数
				wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				db2.setWholeprice(wprice);
				reportService.createDailyBalance(db2);

			}

			for (String manager : sets) {

				if (!managerSet.contains(manager)) {

					DailyBalance db2 = new DailyBalance();
					db2.setYear(year);
					db2.setMonth(mon);
					db2.setBsndate(reportdate);
					db2.setManager(manager);
					db2.setNum(0);
					db2.setJijianprice(0);
					db2.setWeightprice(0);
					db2.setBuzuprice(0);
					db2.setTransformprice(0);
					db2.setFakuanprice(0);
					db2.setPaisongprice(0);
					db2.setZhuanjianprice(0);
					db2.setDiujianprice(0);
					db2.setCreatetime(new Date());
					db2.setStatus("normal");
					db2.setChujianfei(0);

					double wprice = db2.getJijianprice() + db2.getWeightprice()
							+ db2.getBuzuprice() + db2.getTransformprice()
							+ db2.getFakuanprice() + db2.getPaisongprice()
							+ db2.getZhuanjianprice() + db2.getDiujianprice();
					BigDecimal cc = new BigDecimal(wprice);
					// 保留2位小数
					wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					db2.setWholeprice(wprice);
					reportService.createDailyBalance(db2);

				}

			}

		}

		DailyBalance qu = new DailyBalance();
		qu.setBsndate(reportdate);
		DailyBalance ddd = reportService.getDailyBalanceSmy(qu);
		if (ddd == null)
			ddd = new DailyBalance();
		// 更新 YearSmy
		YearSmy smy = new YearSmy();
		smy.setReportdate(reportdate);
		List<YearSmy> ysList = reportService.getYearSmy(smy);

		if (ysList != null && ysList.size() > 0) {

			YearSmy ys = ysList.get(0);
			ys.setZnum(ddd.getNum());
			ys.setZprice(ddd.getWholeprice());

			double tinprice = ys.getZprice() + ys.getWprice() + ys.getYprice();
			ys.setTinprice(tinprice);
			double tpprice = ys.getTinprice() - ys.getToutprice();

			ys.setTpprice(tpprice);
			reportService.updateYearSmy(ys);

		} else {
			// 如果不存在则插入新的

			YearSmy ys = new YearSmy();
			ys.setReportdate(reportdate);
			ys.setYear(year);
			ys.setMonth(mon);
			ys.setWnum(0);
			ys.setZnum(ddd.getNum());
			ys.setYnum(0);
			ys.setWprice(0);
			ys.setZprice(ddd.getWholeprice());
			ys.setYprice(0);

			ys.setYfkprice(0);
			ys.setZpprice(0);
			ys.setGsprice(0);
			ys.setGzprice(0);
			ys.setQtprice(0);

			ys.setTinprice(ddd.getWholeprice());
			ys.setToutprice(0);
			ys.setTpprice(ddd.getWholeprice());

			reportService.createYearSmy(ys);

		}

		this.addErrorMessage("hello", "导入成功！");
		// 分页显示
		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		reportFlagList = mailService.queryReportFlagByPage(p);

		pageBean = new PageBean(10, pageN, reportFlagList.size());

		p = new PageParameter();
		start = (pageN - 1) * 10;
		p.setStart(start);
		p.setSize(10);

		reportFlagList = mailService.queryReportFlagByPage(p);
		return FINIMPORT;
	}

	public String uploadNewFile() throws SQLException, ParseException,
			WriteException {

		user = UserHolder.getUser();
		String dateName = this.getImportDate();
		Date bsndate = null;
		try {
			bsndate = DateUtil.parse(dateName + " 12:00:00",
					DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 先要判定当天的报表是否已经生成-->目前的功能是如果当天的报表已经存在，则要求确认先将那天的数据先删除。

		PageParameter p2 = new PageParameter();
		p2.setBsndate(bsndate);
		p2.setSize(Integer.MAX_VALUE);
		p2.setStart(0);

		List<StatSMY> flg = mailService.queryReportFlagByPage(p2);

		if (flg != null && flg.size() == 1
				&& flg.get(0).getReportdate().equals(this.getImportDate())) {

			this.addErrorMessage("hello", this.getImportDate()
					+ "的报表已经生成，不允许再导入当天的数据，如果要覆盖这天的数据，先将原先的报表数据删除！");
			return FINIMPORT;
		}

		flag = new StatSMY();
		flag.setReportdate(dateName);
		flag.setBsndate(bsndate);

		mailService.createReportFlag(flag);
		HashSet set = new HashSet();

		// 而计重规则则是根据isactive=y取出目前正在使用的计重规则。
		WeightPriceRule weightPriceRule = new WeightPriceRule();
		weightPriceRule.setIsactive("y");
		weightPriceRule.setIsdeleted("n");

		List<WeightPriceRule> weightPriceRuleList = mailService
				.queryForWeightPriceRuleList(weightPriceRule);
		if (weightPriceRuleList == null || weightPriceRuleList.size() == 0) {
			this.addErrorMessage("hello", "请先设置计重费规则或者启用计重费规则！");
			return FINIMPORT;

		}
		weightPriceRule = weightPriceRuleList.get(0);

		// 中转费的信息，则是非常复杂多样，有单价式的，也有首重+续重式的。//只需要选出isdeleted='n'的中转费信息即可。
		TransformPriceRule r = new TransformPriceRule();
		r.setIsdeleted("n");
		List<TransformPriceRule> ransformricerulelist = mailService
				.queryForTransformPriceRuleList(r);

		if (ransformricerulelist == null || ransformricerulelist.size() == 0) {
			this.addErrorMessage("hello", "请先设置各个目的地的中转费！");
			return FINIMPORT;

		}

		// 中转费的设置：要根据目的地。

		importDate = dateName;

		int mon = Integer.parseInt(importDate.substring(5, 7));

		FileUploadUtil.uploadFile(upexcel, "xlsx", dateName, this
				.getEnv("path"), "/");

		String path = this.getEnv("path") + "/" + dateName + ".xlsx";

		IRowReader mailReader = new MailReader(weightPriceRule,
				ransformricerulelist, importDate, mailService);

		try {
			ExcelReaderUtil.readExcel(mailReader, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PageParameter pp = new PageParameter();
		pp.setBsndate(DateUtil.parse(importDate + " 12:00:00",
				DateUtil.DATE_TIME_FORMAT));
		pp.setMon(mon);
		ReportFlag fl = mailService.getStatReportSmy(pp);

		// //生成报表
		// 读取weightRule，
		flag.setWholewholeprice(fl.getWholeprice());
		flag.setWholetransformprice(fl.getWholetransformprice());
		flag.setWholeweightprice(fl.getWholeweightprice());
		flag.setWholebuzufei(fl.getWholebuzufei());
		flag.setWholechujianfei(fl.getWholechujianfei());
		flag.setWholescanfei(fl.getWholescanfei());
		flag.setReportdate(dateName);
		flag.setWholeweight(fl.getWholeweight());
		flag.setWholenum(fl.getWholenum());

		mailService.updateReportFlag(flag);

		String starttime = importDate + " " + "12:00:00";
		String endtime = importDate + " " + "12:00:00";

		String downPath = this.getEnv("downpath") + "/" + importDate;
		File files = new File(downPath);

		if (!files.exists())
			files.mkdir();

		// 将所有揽件人的信息 输入到文件中

		String bsndates = importDate + " " + "12:00:00";

		Date dates = DateUtil.parse(bsndates, DateUtil.DATE_TIME_FORMAT);

		PageParameter pe = new PageParameter();
		pe.setMon(mon);
		pe.setBsndate(dates);
		mailService.createStatSMY(pe);

		// 汇总所有的揽件人的数据，插入到STATSMY

		String outfilename = this.getEnv("downpath") + "/" + importDate + "/"
				+ dateName + "_全部.xlsx";
		try {

			Excel2007Writer writer = new Excel2007Writer();
			writer.createSheet();
			pageBean = new PageBean(pageSize, 1, fl.getWholenum());

			pe = new PageParameter();

			pe.setEndtime(endtime);
			pe.setStarttime(starttime);
			pe.setMon(mon);

			int pagesize = fl.getWholenum() / 10000;

			if (pagesize * 10000 < fl.getWholenum()) {
				pagesize = pagesize + 1;
			}
			for (int i = 0; i < pagesize; i++) {
				pe.setStart(i * 10000);
				pe.setSize(10000);
				statReportList = mailService.queryForStatReportPage(pe);
				writer.process(outfilename, statReportList, i * 10000, fl
						.getWholenum());
			}

			writer.createFile(outfilename);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator itr = set.iterator();

		// 同时对每个人的信息进行汇总。插入到新的表中。
		// 生成汇总的excel
		PageParameter p = new PageParameter();
		p.setBsndate(dates);
		p.setManager(null);
		p.setMon(mon);
		List<StatSMY> statSMYList = mailService.queryForStatSMY(p);

		// p = new PageParameter();
		// p.setBsndate(dates);
		// allStatReport= mailService.queryForAllStatReport(p);

		createSMYExcelFile(statSMYList, dateName + "/" + dateName + "_汇总", flag);

		for (int i = 0; i < statSMYList.size(); i++) {
			StatSMY smy = statSMYList.get(i);
			String manager = smy.getManager();
			String outfile = this.getEnv("downpath") + "/" + importDate + "/"
					+ dateName + "_" + manager + ".xlsx";
			try {

				Excel2007Writer writer = new Excel2007Writer();
				writer.createSheet();

				pe = new PageParameter();
				pe.setEndtime(endtime);
				pe.setStarttime(starttime);
				pe.setMon(mon);

				int pagesize = smy.getWholenum() / 10000;

				if (pagesize * 10000 < smy.getWholenum()) {
					pagesize = pagesize + 1;
				}
				for (int s = 0; s < pagesize; s++) {
					pe.setStart(s * 10000);
					pe.setSize(10000);
					pe.setManager(manager);
					statReportList = mailService.queryForStatReportPage(pe);
					writer.process(outfile, statReportList, s * 10000, smy
							.getWholenum());
				}

				writer.createFile(outfile);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 分页显示

		p = new PageParameter();
		p.setStart(0);
		p.setSize(pageSize);
		p.setEndtime(endtime);
		p.setStarttime(starttime);
		p.setMon(mon);
		statReportList = mailService.queryForStatReportPage(p);

		return FINIMPORT;
	}

	public String uploadMoreCategoryFile() throws ParseException, IOException,
			SQLException, WriteException, BiffException {

		user = UserHolder.getUser();
		String dateName = this.getImportDate();
		Date bsndate = DateUtil.parse(dateName + " 12:00:00",
				DateUtil.DATE_TIME_FORMAT);

		// 先要判定当天的报表是否已经生成-->目前的功能是如果当天的报表已经存在，则要求确认先将那天的数据先删除。

		PageParameter p2 = new PageParameter();
		p2.setBsndate(bsndate);
		p2.setSize(Integer.MAX_VALUE);
		p2.setStart(0);

		List<StatSMY> flg = mailService.queryReportFlagByPage(p2);

		if (flg != null && flg.size() == 1
				&& flg.get(0).getReportdate().equals(this.getImportDate())) {

			this.addErrorMessage("hello", this.getImportDate()
					+ "的报表已经生成，不允许再导入当天的数据，如果要覆盖这天的数据，先将原先的报表数据删除！");
			return FINIMPORT;
		}

		flag = new StatSMY();
		flag.setReportdate(dateName);
		flag.setBsndate(bsndate);

		mailService.createReportFlag(flag);
		HashSet set = new HashSet();

		// 而计重规则则是根据isactive=y取出目前正在使用的计重规则。
		WeightPriceRule weightPriceRule = new WeightPriceRule();
		weightPriceRule.setIsactive("y");
		weightPriceRule.setIsdeleted("n");

		List<WeightPriceRule> weightPriceRuleList = mailService
				.queryForWeightPriceRuleList(weightPriceRule);
		if (weightPriceRuleList == null || weightPriceRuleList.size() == 0) {
			this.addErrorMessage("hello", "请先设置计重费规则或者启用计重费规则！");
			return FINIMPORT;

		}
		weightPriceRule = weightPriceRuleList.get(0);

		// 中转费的信息，则是非常复杂多样，有单价式的，也有首重+续重式的。//只需要选出isdeleted='n'的中转费信息即可。

		// TransformCategory
		TransformCategory c = new TransformCategory();
		List<TransformCategory> transformCategoryList = mailService
				.getTransformCategory(c);

		String defaultPrice = "";

		for (int kk = 0; kk < transformCategoryList.size(); kk++) {

			if (transformCategoryList.get(kk).getTransformcategoryname()
					.equals("默认价格")) {
				defaultPrice = transformCategoryList.get(kk)
						.getTransformcategoryid()
						+ "";
				break;
			}

		}
		Map<String, List<TransformPriceRule>> transformPriceMap = new HashMap<String, List<TransformPriceRule>>();
		if (transformCategoryList == null || transformCategoryList.size() == 0) {
			this.addErrorMessage("hello", "请先设置中转费价格方案！");
			return FINIMPORT;
		}

		int len = transformCategoryList.size();
		for (int kk = 0; kk < len; kk++) {
			TransformCategory cc = transformCategoryList.get(kk);
			TransformPriceRule r = new TransformPriceRule();
			r.setIsdeleted("n");
			r.setTransformcategoryid(cc.getTransformcategoryid());
			List<TransformPriceRule> ransformricerulelist = mailService
					.queryForTransformPriceRuleList(r);

			transformPriceMap.put(cc.getTransformcategoryid() + "",
					ransformricerulelist);

		}

		List<ManagerInfo> managerInfoList = mailService.queryForManagerInfo(
				null, null);
		HashMap<String, String> managerMap = new HashMap<String, String>();
		HashMap<String, String> managerCategoryMap = new HashMap<String, String>();

		for (int i = 0; i < managerInfoList.size(); i++) {
			managerMap.put(managerInfoList.get(i).getManagername(),
					managerInfoList.get(i).getManagerid());
			managerCategoryMap.put(managerInfoList.get(i).getManagerid(),
					managerInfoList.get(i).getSolutionid());
		}

		// 中转费的设置：要根据目的地。
		double alltransformprice = 0;
		double alloperateprice = 0;
		double allweightprice = 0;
		double allbuzufei = 0;
		double allwholeprice = 0;
		double allweight = 0;
		double allchujianfei = 0;
		double allscanfei = 0;

		double scanfei = 0;
		weightPriceRule.getScanfei();
		double chujianfei = 0;
		weightPriceRule.getChujianfei();
		importDate = dateName;

		int mon = Integer.parseInt(importDate.substring(5, 7));

		FileUploadUtil.uploadFile(upexcel, "xls", dateName,
				this.getEnv("path"), "/");

		String path = this.getEnv("path") + "/" + dateName + ".xls";
		Workbook rwb = null;
		InputStream is = null;

		List<MailInfo> allMailList = new ArrayList();

		// 在将数据插入到数据库中前，要扫描数据库中是否已经有当天的数据
		// 如果有，则将原来的update为deleted。--》由于为追加模式，不需要将当天的数据删除

		Date createTime = DateUtil.parse(dateName + " 12:00:00",
				DateUtil.DATE_TIME_FORMAT);

		int record = 0;
		int recordNum = 0;

		try {
			is = new FileInputStream(path);

			rwb = Workbook.getWorkbook(is);

			Sheet sh = rwb.getSheet(0);

			int rowN = sh.getRows();

			for (int j = 1; j < rowN; j++) {
				record++;
				recordNum++;
				MailInfo mail = new MailInfo();
				Cell[] cells = sh.getRow(j);
				if (cells == null
						|| cells.length == 0
						|| (cells.length > 0 && cells[0].getContents().trim()
								.equals(""))) {
					record = 500;
				} else {

					String linenum = cells[0].getContents();
					String status = cells[1].getContents();
					String mailid = cells[2].getContents();
					String manager = cells[3].getContents().trim();

					if (manager.indexOf("】") != -1) {

						manager = manager.substring(manager.indexOf("】") + 1);
					}
					if (manager != null && !manager.equals(""))
						set.add(manager);

					String subcompany = cells[4].getContents().trim();

					if (subcompany.indexOf("】") != -1) {

						subcompany = subcompany.substring(subcompany
								.indexOf("】") + 1);
					}
					double weight = 0;

					try {
						weight = Double.parseDouble(cells[5].getContents());

					} catch (Exception e) {
						e.printStackTrace();

						return FINIMPORT;
					}

					mail = new MailInfo();
					mail.setLinenum(linenum);
					mail.setMailid(mailid);
					mail.setSendtime(new Date());
					mail.setManager(manager);
					mail.setSubcompany(subcompany);
					mail.setWeight(weight);
					mail.setCreatetime(createTime);
					mail.setIsdeleted("n");

					allMailList.add(record - 1, mail);
				}
				// 每500条插入一次

				if (record == 500 || rowN == (recordNum + 1)) {

					// try {
					// mailService.insertSourceMailList(allMailList);
					//
					// } catch (SQLException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					//
					// }

					for (int i = 0; i < allMailList.size(); i++) {
						mail = allMailList.get(i);
						allweight = allweight + mail.getWeight();
						double transformPrice = 0;
						double buzufei = 0;
						// 得出重量区间，从而得到一个index

						double weightPrice = 0;

						// 计重 0,15,m,0;15,30,m,1;30,9999,m,2

						if (mail.getWeight() <= weightPriceRule
								.getFirstweight()) {
							weightPrice = weightPriceRule.getPrice()
									* weightPriceRule.getFirstweight();

						} else {

							weightPrice = mail.getWeight()
									* weightPriceRule.getPrice();
						}

						BigDecimal bb = new BigDecimal(weightPrice);
						// 保留2位小数
						weightPrice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();

						// 获取目的地，就可以去transformpricerule找;
						String managerid = null;
						String categoryid = null;
						managerid = managerMap.get(mail.getManager());
						if (managerid == null) {

							categoryid = defaultPrice;
						} else {

							categoryid = managerCategoryMap.get(managerid);

						}

						List<TransformPriceRule> transformPriceRuleList = transformPriceMap
								.get(categoryid);
						int transformLen = 0;
						if (transformPriceRuleList != null)
							transformLen = transformPriceRuleList.size();

						String pricelist = "";
						for (int k = 0; k < transformLen; k++) {

							TransformPriceRule rule = transformPriceRuleList
									.get(k);

							if (rule.getDestinationcity().trim().equals(
									mail.getSubcompany().trim())) {

								pricelist = rule.getPricelist();
								break;
							}

						}

						if (pricelist.equals("")) {// 表示该目的地不存在；
							this.addErrorMessage("hello",
									"在导入的文件中，部分目的地的中转费没有设置！");
							BigDecimal cc = new BigDecimal(0);
							// 保留2位小数
							transformPrice = cc.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue();

						} else {
							// 3:2

							// price=中转费方案:首重:单价:补助费
							// price=中转费方案：首重：首重价格,续重单价：补助费
							// // price=中转费方案：最小结算重量:加成：首重：首重价格,续重单价：补助费
							String strprice[] = pricelist.split(":");

							if (strprice[0].equals("1")) {// 单价式计算
								buzufei = Double.parseDouble(strprice[3]);
								scanfei = weightPriceRule.getScanfei();
								chujianfei = weightPriceRule.getChujianfei();
								double shouzhong = Double
										.parseDouble(strprice[1]);

								if (mail.getWeight() <= shouzhong) {
									transformPrice = Double
											.parseDouble(strprice[1])
											* Double.parseDouble(strprice[2]);
								} else {

									transformPrice = mail.getWeight()
											* Double.parseDouble(strprice[2]);
								}

								BigDecimal cc = new BigDecimal(transformPrice);
								// 保留2位小数
								transformPrice = cc.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

							} else if (strprice[0].equals("2")) {// 首重式计算
								scanfei = weightPriceRule.getScanfei();
								chujianfei = weightPriceRule.getChujianfei();
								buzufei = Double.parseDouble(strprice[3]);

								double shouzhong = Double
										.parseDouble(strprice[1]);
								String sss[] = strprice[2].split(",");

								if (mail.getWeight() <= shouzhong) {
									transformPrice = Double.parseDouble(sss[0]);
								} else {

									transformPrice = Double.parseDouble(sss[0])
											+ (mail.getWeight() - shouzhong)
											* Double.parseDouble(sss[1]);
								}

								BigDecimal cc = new BigDecimal(transformPrice);
								// 保留2位小数
								transformPrice = cc.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

							} else if (strprice[0].equals("3")) {
								scanfei = weightPriceRule.getScanfei();
								chujianfei = weightPriceRule.getChujianfei();
								buzufei = Double.parseDouble(strprice[5]);
								double minweight = Double
										.parseDouble(strprice[1]);
								double jiacheng = Double
										.parseDouble(strprice[2]);
								double shouzhong = Double
										.parseDouble(strprice[3]);
								String sss[] = strprice[4].split(",");

								double weight = 0;
								if (mail.getWeight() <= minweight) {
									weight = minweight;
								} else
									weight = mail.getWeight();

								if (weight <= shouzhong)
									transformPrice = Double.parseDouble(sss[0])
											* weight + jiacheng;
								else {

									transformPrice = Double.parseDouble(sss[0])
											* shouzhong + jiacheng
											+ (weight - shouzhong)
											* Double.parseDouble(sss[1]);
								}

								BigDecimal cd = new BigDecimal(transformPrice);
								// 保留2位小数
								transformPrice = cd.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

							} else if (strprice[0].equals("4")) {
								// 三段式
								// price=中转费方案：首重:首重价格：续重,续重单价:超重,超重费：补助费
								buzufei = 0;
								chujianfei = 0;
								scanfei = 0;
								weightPrice = 0;
								double shouzhong = Double
										.parseDouble(strprice[1]);

								String sss[] = strprice[3].split(",");
								double xuzhong = Double.parseDouble(sss[0]);
								if (mail.getWeight() <= shouzhong) {
									transformPrice = Double
											.parseDouble(strprice[2]);
								} else if (mail.getWeight() <= xuzhong) {
									transformPrice = Double.parseDouble(sss[1]);

								} else {

									double ww = 0;
									double pp = 0;
									if (strprice[4].indexOf(",") != -1) {
										ww = Double.parseDouble(strprice[4]
												.split(",")[0]);
										pp = Double.parseDouble(strprice[4]
												.split(",")[1]);
									} else {
										ww = 1;
										pp = Double.parseDouble(strprice[4]);
									}

									double chaoweight = mail.getWeight()
											- xuzhong;
									int f = (int) (chaoweight / ww);
									if (f * ww < chaoweight) {
										f = f + 1;
									}

									transformPrice = Double.parseDouble(sss[1])
											+ f * pp;

								}

								BigDecimal cc = new BigDecimal(transformPrice);
								// 保留2位小数
								transformPrice = cc.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

							} else if (strprice[0].equals("5")) {
								// 二段取整式
								buzufei = 0;
								chujianfei = 0;
								scanfei = 0;
								weightPrice = 0;
								double shouzhong = Double
										.parseDouble(strprice[1]);
								String sss[] = strprice[2].split(",");

								if (mail.getWeight() <= shouzhong) {
									transformPrice = Double.parseDouble(sss[0]);
								} else {

									transformPrice = Double.parseDouble(sss[0])
											+ (Math.ceil(mail.getWeight()) - shouzhong)
											* Double.parseDouble(sss[1]);
								}

								BigDecimal cc = new BigDecimal(transformPrice);
								// 保留2位小数
								transformPrice = cc.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();

							} else {
								// 小件与超重
								// price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格：中转费方案：首重,首重价格,续重单价：补助费

								double maxweight = 0;
								if (strprice.length >= 5) {
									String str1[] = strprice[1].split(",");
									if (mail.getWeight() > Double
											.parseDouble(str1[0])
											&& mail.getWeight() <= Double
													.parseDouble(str1[1])) {
										buzufei = 0;
										chujianfei = 0;
										scanfei = 0;
										weightPrice = 0;
										BigDecimal cc = new BigDecimal(Double
												.parseDouble(str1[2]));
										// 保留2位小数
										transformPrice = cc.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();

									}
									maxweight = Double.parseDouble(str1[1]);
								}

								if (strprice.length >= 6) {
									String str2[] = strprice[2].split(",");
									if (mail.getWeight() > Double
											.parseDouble(str2[0])
											&& mail.getWeight() <= Double
													.parseDouble(str2[1])) {
										buzufei = 0;
										chujianfei = 0;
										scanfei = 0;
										weightPrice = 0;
										BigDecimal cc = new BigDecimal(Double
												.parseDouble(str2[2]));
										// 保留2位小数
										transformPrice = cc.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();
									}
									maxweight = Double.parseDouble(str2[1]);
								}

								if (strprice.length >= 7) {
									String str3[] = strprice[3].split(",");
									if (mail.getWeight() > Double
											.parseDouble(str3[0])
											&& mail.getWeight() <= Double
													.parseDouble(str3[1])) {
										buzufei = 0;
										chujianfei = 0;
										scanfei = 0;
										weightPrice = 0;
										BigDecimal cc = new BigDecimal(Double
												.parseDouble(str3[2]));
										// 保留2位小数
										transformPrice = cc.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();
									}
									maxweight = Double.parseDouble(str3[1]);
								}

								if (mail.getWeight() > maxweight) {

									String str5[] = strprice[strprice.length - 2]
											.split(",");
									scanfei = weightPriceRule.getScanfei();
									chujianfei = weightPriceRule
											.getChujianfei();
									if (strprice[strprice.length - 3]
											.equals("7")) {
										double shouzhong = Double
												.parseDouble(str5[0]);

										if (mail.getWeight() <= shouzhong) {
											transformPrice = shouzhong
													* Double
															.parseDouble(str5[1]);
										} else {

											transformPrice = mail.getWeight()
													* Double
															.parseDouble(str5[1]);
										}

										BigDecimal cc = new BigDecimal(
												transformPrice);
										// 保留2位小数
										transformPrice = cc.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();

										buzufei = Double
												.parseDouble(strprice[strprice.length - 1]);
									} else {

										double shouzhong = Double
												.parseDouble(str5[0]);

										if (mail.getWeight() <= shouzhong) {
											transformPrice = Double
													.parseDouble(str5[1]);
										} else {

											transformPrice = Double
													.parseDouble(str5[1])
													+ (mail.getWeight() - shouzhong)
													* Double
															.parseDouble(str5[2]);
										}
										BigDecimal cc = new BigDecimal(
												transformPrice);
										// 保留2位小数
										transformPrice = cc.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										buzufei = Double
												.parseDouble(strprice[strprice.length - 1]);

									}

								}

							}

						}

						alltransformprice = alltransformprice + transformPrice;
						allweightprice = allweightprice + weightPrice;
						allchujianfei = allchujianfei + chujianfei;
						allscanfei = allscanfei + scanfei;
						allbuzufei = allbuzufei + buzufei;
						// 得出一个
						StatReport statReport = new StatReport();
						statReport.setMailid(mail.getMailid());
						statReport.setOperateprice(1.1);// 操作费暂时设置为1.1
						statReport.setTransformprice(transformPrice);
						statReport.setWeightprice(weightPrice);
						statReport.setManager(mail.getManager());
						statReport.setBuzufei(buzufei);
						statReport.setScanfei(scanfei);
						statReport.setChujianfei(chujianfei);
						statReport.setSendtime(mail.getSendtime());
						statReport.setWeight(mail.getWeight());
						statReport.setIsdeleted("n");
						statReport.setDestinationcity(mail.getSubcompany());// 目前设置目标组为中转站:条码显示实名
						statReport.setCreatetime(createTime);
						statReport.setState("normal");
						statReport.setMon(mon);
						double whole = weightPrice + transformPrice + scanfei
								+ chujianfei + buzufei;
						BigDecimal b = new BigDecimal(whole);
						// 保留2位小数
						whole = b.setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						allwholeprice = allwholeprice + whole;
						statReport.setWholeprice(whole);

						mailService.createStatReport(statReport);

					}

					allMailList = new ArrayList();
					record = 0;

				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.addErrorMessage("hello", "文件不存在！");
			return FINIMPORT;

		} finally {
			if (rwb != null)
				rwb.close();
			if (is != null)
				is.close();

		}

		// //生成报表
		// 读取weightRule，
		// 读取weightPrice;
		// 读取transformpricerule
		BigDecimal cc = new BigDecimal(allwholeprice);
		// 保留2位小数
		allwholeprice = cc.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal d = new BigDecimal(allweightprice);
		// 保留2位小数
		allweightprice = d.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal e = new BigDecimal(alltransformprice);
		// 保留2位小数
		alltransformprice = e.setScale(1, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		BigDecimal f = new BigDecimal(allscanfei);
		// 保留2位小数
		allscanfei = f.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal l = new BigDecimal(allchujianfei);

		allchujianfei = l.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();// 总操作费

		BigDecimal g = new BigDecimal(alloperateprice);
		// 保留2位小数
		alloperateprice = g.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal y = new BigDecimal(allbuzufei);
		// 保留2位小数
		allbuzufei = y.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal h = new BigDecimal(allweight);
		// 保留2位小数
		allweight = h.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		flag.setWholewholeprice(allwholeprice);
		flag.setWholetransformprice(alltransformprice);
		flag.setWholeweightprice(allweightprice);
		flag.setWholebuzufei(allbuzufei);
		flag.setWholechujianfei(allchujianfei);
		flag.setWholescanfei(allscanfei);
		flag.setReportdate(dateName);
		flag.setWholeweight(allweight);
		flag.setWholenum(recordNum);

		mailService.updateReportFlag(flag);

		String starttime = importDate + " " + "12:00:00";
		String endtime = importDate + " " + "12:00:00";

		String downPath = this.getEnv("downpath") + "/" + importDate;

		File files = new File(downPath);

		if (!files.exists())
			files.mkdir();

		pageBean = new PageBean(pageSize, 1, recordNum);

		PageParameter pe = new PageParameter();
		pe.setStart(0);
		pe.setSize(recordNum);
		pe.setEndtime(endtime);
		pe.setStarttime(starttime);
		pe.setMon(mon);
		statReportList = mailService.queryForStatReportPage(pe);

		createExcelFile(statReportList, flag, importDate + "/" + dateName
				+ "_全部");

		// 将所有揽件人的信息 输入到文件中

		String bsndates = importDate + " " + "12:00:00";

		Date dates = DateUtil.parse(bsndates, DateUtil.DATE_TIME_FORMAT);

		pe = new PageParameter();
		pe.setMon(mon);
		pe.setBsndate(dates);

		mailService.createStatSMY(pe);

		// 汇总所有的揽件人的数据，插入到STATSMY

		Iterator itr = set.iterator();

		// 同时对每个人的信息进行汇总。插入到新的表中。
		//

		// 生成汇总的excel
		PageParameter p = new PageParameter();
		p.setBsndate(dates);
		p.setManager(null);
		p.setMon(mon);
		List<StatSMY> statSMYList = mailService.queryForStatSMY(p);

		//		
		// p = new PageParameter();
		// p.setBsndate(dates);
		// allStatReport= mailService.queryForAllStatReport(p);
		//	 
		//	    
		createSMYExcelFile(statSMYList, dateName + "/" + dateName + "_汇总", flag);

		while (itr.hasNext()) {
			StatSMY smy = new StatSMY();
			String manager = (String) itr.next();
			p = new PageParameter();
			p.setStart(0);
			p.setSize(recordNum);
			p.setEndtime(endtime);
			p.setStarttime(starttime);
			p.setManager(manager);
			p.setMon(mon);
			statReportList = mailService.queryForStatReportPage(p);

			p = new PageParameter();
			p.setBsndate(dates);
			p.setManager(manager);

			smy = mailService.queryForStatSMY(p).get(0);

			createExcelFile(statReportList, smy, dateName + "/" + dateName
					+ "_" + manager);
		}

		// File filess=new File(targetPath);
		// compress(filess,downPath);
		//		
		// 分页显示

		p = new PageParameter();
		p.setStart(0);
		p.setSize(pageSize);
		p.setEndtime(endtime);
		p.setStarttime(starttime);
		p.setMon(mon);
		statReportList = mailService.queryForStatReportPage(p);

		return FINIMPORT;
	}

	public String uploadPriceFile() {

		// 先定义用户
		user = UserHolder.getUser();

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String post = request.getParameter("str");
		String type = request.getParameter("type");
		String dateName = request.getParameter("importDate");
		Date bsndate = null;
		try {
			bsndate = DateUtil.parse(dateName + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		importDate = dateName;

		int mon = Integer.parseInt(importDate.substring(5, 7));
		int year = Integer.parseInt(importDate.substring(0, 4));
		FileUploadUtil.uploadFile(upexcel, post, dateName + "_other", this
				.getEnv("path"), "/");

		String path = this.getEnv("path") + "/" + dateName + "_other." + post;

		IRowReader mailReader = null;
		PriceDetailSmy priceDetailSmy = new PriceDetailSmy();
		priceDetailSmy.setBsndate(bsndate);
		priceDetailSmy.setMon(year);

		if (type.equals("d")) {
			priceDetailSmy.setPricetype("大笔罚款");
			mailService.deletePriceDetail(priceDetailSmy);

		}

		if (type.equals("y")) {
			priceDetailSmy.setPricetype("有偿派费");
			mailService.deletePriceDetail(priceDetailSmy);

		}
		if (type.equals("z")) {
			priceDetailSmy.setPricetype("转件费");
			mailService.deletePriceDetail(priceDetailSmy);

		}
		if (type.equals("l")) {
			priceDetailSmy.setPricetype("丢件费");
			mailService.deletePriceDetail(priceDetailSmy);

		}

		if (post.equals("xlsx")) {
			mailReader = new PriceReader(importDate, priceDetailSmy
					.getPricetype(), mailService);

			try {
				ExcelReaderUtil.readExcel(mailReader, path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			priceDetailSmy.setNum(mailReader.getNum());
			BigDecimal bb = new BigDecimal(mailReader.getPrice());
			// 保留2位小数
			double price = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

			priceDetailSmy.setPrice(price);
			priceDetailSmy.setCreatetime(new Date());
			priceDetailSmy.setCreator(user.getUsername());

			// 先删除当天汇总
			mailService.deletePriceDetailSmy(priceDetailSmy);
			mailService.createPriceDetailSmy(priceDetailSmy);
			// 先删除当天揽件人汇总
			mailService.deletePriceDetailManagerSmy(priceDetailSmy);
			mailService.createPriceDetailManagerSmy(priceDetailSmy);

			PageParameter pp = new PageParameter();
			pp.setStart(0);
			pp.setSize(Integer.MAX_VALUE);
			pp.setBsndate(bsndate);
			pp.setPricetype(priceDetailSmy.getPricetype());

			List<PriceDetailSmy> managerSmyList = mailService
					.getPriceDetailManagerSmy(pp);

			int kk = 0;
			HashSet<String> managerSet = new HashSet<String>();

			if (managerSmyList != null && managerSmyList.size() > 0) {
				kk = managerSmyList.size();

				for (int i = 0; i < kk; i++) {
					PriceDetailSmy s = managerSmyList.get(i);
					managerSet.add(s.getManager());
				}
			}

			HashSet<String> sets = new HashSet<String>();
			List<ManagerInfo> managerInfoList = mailService
					.queryForManagerInfoNew(new ManagerInfo());

			if (managerInfoList != null && managerInfoList.size() > 0) {
				int l = managerInfoList.size();
				for (int k = 0; k < l; k++)
					sets.add(managerInfoList.get(k).getManagername());

			}

			// 先定义才能导入，也就控制不正确的用户

			for (String m : managerSet) {

				if (!sets.contains(m)) {
					this.addErrorMessage("hello", m + "未定义,请到用户管理定义用户");
					mailService.deletePriceDetail(priceDetailSmy);
					mailService.deletePriceDetailSmy(priceDetailSmy);
					// 先删除当天揽件人汇总
					mailService.deletePriceDetailManagerSmy(priceDetailSmy);
					PageParameter p = new PageParameter();
					p.setStart(0);
					p.setSize(Integer.MAX_VALUE);
					p.setEndtime(null);
					p.setStarttime(null);
					int recordNum = mailService.getPriceDetailSmyCount(p);
					pageBean = new PageBean(30, 1, recordNum);
					p = new PageParameter();
					p.setStart(0);
					p.setSize(15);
					p.setBsndate(null);

					priceDetailSmyList = mailService.getPriceDetailSmy(p);
					return "showimportpricepage";

				}

			}

			DailyBalance db = new DailyBalance();
			db.setBsndate(bsndate);
			List<DailyBalance> dailybalanceList = reportService
					.getDailyBalance(db);

			if (dailybalanceList != null && dailybalanceList.size() > 0) {

				int len = 0;
				if (managerSmyList != null && managerSmyList.size() > 0)
					len = managerSmyList.size();
				for (int i = 0; i < len; i++) {

					PriceDetailSmy smy = managerSmyList.get(i);

					DailyBalance d = new DailyBalance();
					d.setBsndate(bsndate);
					d.setManager(smy.getManager());
					List<DailyBalance> dbList = reportService
							.getDailyBalance(d);

					if (dbList != null && dbList.size() > 0) {

						DailyBalance db2 = dbList.get(0);

						if (type.equals("d")) {
							db2.setFakuanprice(smy.getPrice());
						}

						if (type.equals("y")) {
							db2.setPaisongprice(smy.getPrice());
						}
						if (type.equals("z")) {
							db2.setZhuanjianprice(smy.getPrice());
						}
						if (type.equals("l")) {
							db2.setDiujianprice(smy.getPrice());
						}

						double wprice = db2.getJijianprice()
								+ db2.getWeightprice() + db2.getBuzuprice()
								+ db2.getTransformprice()
								+ db2.getFakuanprice() + db2.getPaisongprice()
								+ db2.getZhuanjianprice()
								+ db2.getDiujianprice();
						BigDecimal cc = new BigDecimal(wprice);
						// 保留2位小数
						wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						db2.setWholeprice(wprice);

						reportService.updateDailyBalance(db2);
					} else {

						DailyBalance db2 = new DailyBalance();
						db2.setBsndate(bsndate);
						db2.setManager(smy.getManager());
						db2.setYear(year);
						db2.setMonth(mon);
						db2.setNum(0);
						db2.setJijianprice(0);
						db2.setWeightprice(0);
						db2.setBuzuprice(0);
						db2.setTransformprice(0);
						db2.setFakuanprice(0);
						db2.setPaisongprice(0);
						db2.setZhuanjianprice(0);
						db2.setDiujianprice(0);
						db2.setCreatetime(new Date());
						db2.setStatus("normal");
						db2.setChujianfei(0);

						if (type.equals("d")) {
							db2.setFakuanprice(smy.getPrice());
						}

						if (type.equals("y")) {
							db2.setPaisongprice(smy.getPrice());
						}
						if (type.equals("z")) {
							db2.setZhuanjianprice(smy.getPrice());
						}
						if (type.equals("l")) {
							db2.setDiujianprice(smy.getPrice());
						}

						double wprice = db2.getJijianprice()
								+ db2.getWeightprice() + db2.getBuzuprice()
								+ db2.getTransformprice()
								+ db2.getFakuanprice() + db2.getPaisongprice()
								+ db2.getZhuanjianprice()
								+ db2.getDiujianprice();
						BigDecimal cc = new BigDecimal(wprice);
						// 保留2位小数
						wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						db2.setWholeprice(wprice);
						reportService.createDailyBalance(db2);

					}

				}

			} else {// 当日 ，按人汇总的数据没有时，则新增

				for (int i = 0; i < kk; i++) {
					PriceDetailSmy s = managerSmyList.get(i);

					// 存在则更新
					DailyBalance db2 = new DailyBalance();
					db2.setBsndate(bsndate);
					db2.setManager(s.getManager());
					db2.setYear(year);
					db2.setMonth(mon);
					db2.setNum(0);
					db2.setJijianprice(0);
					db2.setWeightprice(0);
					db2.setBuzuprice(0);
					db2.setTransformprice(0);
					db2.setFakuanprice(0);
					db2.setPaisongprice(0);
					db2.setZhuanjianprice(0);
					db2.setDiujianprice(0);
					db2.setCreatetime(new Date());
					db2.setStatus("normal");
					db2.setChujianfei(0);

					if (type.equals("d")) {
						db2.setFakuanprice(s.getPrice());
					}

					if (type.equals("y")) {
						db2.setPaisongprice(s.getPrice());
					}
					if (type.equals("z")) {
						db2.setZhuanjianprice(s.getPrice());
					}
					if (type.equals("l")) {
						db2.setDiujianprice(s.getPrice());
					}

					double wprice = db2.getJijianprice() + db2.getWeightprice()
							+ db2.getBuzuprice() + db2.getTransformprice()
							+ db2.getFakuanprice() + db2.getPaisongprice()
							+ db2.getZhuanjianprice() + db2.getDiujianprice();
					BigDecimal cc = new BigDecimal(wprice);
					// 保留2位小数
					wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					db2.setWholeprice(wprice);
					reportService.createDailyBalance(db2);

				}

				for (String manager : sets) {

					if (!managerSet.contains(manager)) {

						DailyBalance db2 = new DailyBalance();
						db2.setYear(year);
						db2.setMonth(mon);
						db2.setBsndate(bsndate);
						db2.setManager(manager);
						db2.setNum(0);
						db2.setJijianprice(0);
						db2.setWeightprice(0);
						db2.setBuzuprice(0);
						db2.setTransformprice(0);
						db2.setFakuanprice(0);
						db2.setPaisongprice(0);
						db2.setZhuanjianprice(0);
						db2.setDiujianprice(0);
						db2.setCreatetime(new Date());
						db2.setStatus("normal");
						db2.setChujianfei(0);

						double wprice = db2.getJijianprice()
								+ db2.getWeightprice() + db2.getBuzuprice()
								+ db2.getTransformprice()
								+ db2.getFakuanprice() + db2.getPaisongprice()
								+ db2.getZhuanjianprice()
								+ db2.getDiujianprice();
						BigDecimal cc = new BigDecimal(wprice);
						// 保留2位小数
						wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						db2.setWholeprice(wprice);
						reportService.createDailyBalance(db2);

					}

				}

			}

		}

		// 获取汇总值
		DailyBalance qu = new DailyBalance();
		qu.setBsndate(bsndate);
		DailyBalance db = reportService.getDailyBalanceSmy(qu);
		if (db == null)
			db = new DailyBalance();

		// 更新 YearSmy
		YearSmy smy = new YearSmy();
		smy.setReportdate(bsndate);
		List<YearSmy> ysList = reportService.getYearSmy(smy);

		if (ysList != null && ysList.size() > 0) {

			YearSmy ys = ysList.get(0);

			ys.setZprice(db.getWholeprice());

			double tinprice = ys.getZprice() + ys.getWprice() + ys.getYprice();
			ys.setTinprice(tinprice);
			double tpprice = ys.getTinprice() - ys.getToutprice();

			ys.setTpprice(tpprice);
			reportService.updateYearSmy(ys);

		} else {
			// 如果不存在则插入新的

			YearSmy ys = new YearSmy();
			ys.setReportdate(bsndate);
			ys.setYear(year);
			ys.setMonth(mon);
			ys.setWnum(0);
			ys.setZnum(0);
			ys.setYnum(0);
			ys.setWprice(0);
			ys.setZprice(db.getWholeprice());
			ys.setYprice(0);

			ys.setYfkprice(0);
			ys.setZpprice(0);
			ys.setGsprice(0);
			ys.setGzprice(0);
			ys.setQtprice(0);

			ys.setTinprice(db.getWholeprice());
			ys.setToutprice(0);
			ys.setTpprice(db.getWholeprice());

			reportService.createYearSmy(ys);

		}

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setEndtime(null);
		p.setStarttime(null);
		int recordNum = mailService.getPriceDetailSmyCount(p);
		pageBean = new PageBean(15, 1, recordNum);
		p = new PageParameter();
		p.setStart(0);
		p.setSize(15);
		p.setBsndate(null);

		priceDetailSmyList = mailService.getPriceDetailSmy(p);
		return "showimportpricepage";

	}

	public String deleteAllMail() throws ParseException {

		user = UserHolder.getUser();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		String dateName = request.getParameter("importDate");
		importDate = request.getParameter("importDate");
		int mon = Integer.parseInt(importDate.substring(5, 7));
		String startTime = importDate + " 00:00:00";
		String endTime = importDate + " 23:59:59";
		PageParameter pageParameter = new PageParameter();
		pageParameter.setStarttime(startTime);
		pageParameter.setEndtime(endTime);
		pageParameter.setMon(mon);
		mailService.deleteExitedMailAndStatReport(pageParameter);

		StatSMY flg = new StatSMY();
		flg.setReportdate(importDate);

		mailService.deleteReportFlag(flg);

		this.addErrorMessage("hello", "刚插入的" + dateName + "快件全部删除！");

		return FINIMPORT;
	}

	private void upload(String fileFileName) {
		if (upexcel != null) {
			FileOutputStream outputStream;
			try {

				String filePath = this.getEnv("path") + "/" + fileFileName;
				try {
					// 创建目录
					File f = new File(this.getEnv("path"));
					f.mkdirs();
				} catch (Exception e) {
					e.printStackTrace();
				}
				outputStream = new FileOutputStream(filePath);
				FileInputStream fileIn = new FileInputStream(upexcel);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fileIn.read(buffer)) > 0) {
					outputStream.write(buffer, 0, len);
				}
				fileIn.close();
				outputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("uploadfile name=" + fileFileName);
		} else {
			System.out.println("file is null!");
		}
	}

	public void compress(File zipFile, String srcPathName) {
		File srcdir = new File(srcPathName);
		if (!srcdir.exists())
			throw new RuntimeException(srcPathName + "不存在！");

		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		// fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹
		// eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); 排除哪些文件或文件夹
		zip.addFileset(fileSet);

		zip.execute();
	}

	public void createLabel(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 8);
		// Define the cell format
		WritableCellFormat times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);
		times.setBorder(Border.ALL, BorderLineStyle.THIN);
		times.setAlignment(Alignment.CENTRE);
		times.setVerticalAlignment(VerticalAlignment.CENTRE);
		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setSize(8);

		// Write a few headers
		Label label00 = new Label(0, 0, "日期", times);
		sheet.addCell(label00);
		Label label0 = new Label(1, 0, "状态", times);
		sheet.addCell(label0);
		Label label1 = new Label(2, 0, "单件号", times);
		sheet.addCell(label1);
		Label label2 = new Label(3, 0, "揽件人", times);
		sheet.addCell(label2);
		Label label3 = new Label(4, 0, "重量", times);
		sheet.addCell(label3);
		Label label4 = new Label(5, 0, "目的地", times);
		sheet.addCell(label4);
		Label label5 = new Label(6, 0, "计件费", times);
		sheet.addCell(label5);

		Label label7 = new Label(7, 0, "称重费", times);
		sheet.addCell(label7);
		Label label10 = new Label(8, 0, "区域补贴", times);
		sheet.addCell(label10);

		Label label8 = new Label(9, 0, "中转费", times);
		sheet.addCell(label8);

		Label label11 = new Label(10, 0, " ", times);
		sheet.addCell(label11);

		Label label9 = new Label(11, 0, "小结", times);
		sheet.addCell(label9);

	}

	public void createLabelSMY(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 8);
		// Define the cell format
		WritableCellFormat times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);
		times.setBorder(Border.ALL, BorderLineStyle.THIN);
		times.setAlignment(Alignment.CENTRE);
		times.setVerticalAlignment(VerticalAlignment.CENTRE);
		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setSize(80);

		// Write a few headers
		Label label0 = new Label(0, 0, "时间", times);
		sheet.addCell(label0);
		Label label1 = new Label(1, 0, "揽件人", times);
		sheet.addCell(label1);
		Label label2 = new Label(2, 0, "件数", times);
		sheet.addCell(label2);
		Label label3 = new Label(3, 0, "重量", times);
		sheet.addCell(label3);
		Label label4 = new Label(4, 0, "计件费", times);
		sheet.addCell(label4);
		Label label6 = new Label(5, 0, "称重费", times);
		sheet.addCell(label6);

		Label label7 = new Label(6, 0, "区域补贴", times);
		sheet.addCell(label7);
		Label label10 = new Label(7, 0, "中转费", times);
		sheet.addCell(label10);

		Label label8 = new Label(8, 0, " ", times);
		sheet.addCell(label8);

		Label label11 = new Label(9, 0, "小结 ", times);
		sheet.addCell(label11);

	}

	public void createLabel2(WritableSheet sheet, int len)
			throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 8);
		// Define the cell format
		WritableCellFormat times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);
		times.setBorder(Border.ALL, BorderLineStyle.THIN);
		times.setAlignment(Alignment.CENTRE);
		times.setVerticalAlignment(VerticalAlignment.CENTRE);
		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setSize(80);

		// Write a few headers
		Label label00 = new Label(0, len + 2, "时间", times);
		sheet.addCell(label00);
		Label label0 = new Label(1, len + 2, "状态", times);
		sheet.addCell(label0);
		Label label3 = new Label(2, len + 2, "总件数", times);
		sheet.addCell(label3);
		Label label2 = new Label(3, len + 2, "", times);
		sheet.addCell(label2);
		Label label4 = new Label(4, len + 2, "总重量", times);
		sheet.addCell(label4);

		Label label5 = new Label(5, len + 2, "", times);
		sheet.addCell(label5);

		Label label7 = new Label(6, len + 2, "总扫描费", times);
		sheet.addCell(label7);
		Label label8 = new Label(7, len + 2, "总出件费", times);
		sheet.addCell(label8);

		Label label9 = new Label(8, len + 2, "总计重费", times);
		sheet.addCell(label9);
		Label label12 = new Label(9, len + 2, "总派送补贴", times);
		sheet.addCell(label12);
		Label label10 = new Label(10, len + 2, "总中转费", times);
		sheet.addCell(label10);
		Label label6 = new Label(11, len + 2, "", times);
		sheet.addCell(label6);
		Label label11 = new Label(12, len + 2, "总费用", times);
		sheet.addCell(label11);

	}

	public static void createLabelSMY2(WritableSheet sheet, int len)
			throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 8);
		// Define the cell format
		WritableCellFormat times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);
		times.setBorder(Border.ALL, BorderLineStyle.THIN);
		times.setAlignment(Alignment.CENTRE);
		times.setVerticalAlignment(VerticalAlignment.CENTRE);
		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setSize(80);

		// Write a few headers
		Label label1 = new Label(0, len + 2, "时间", times);
		sheet.addCell(label1);
		Label label3 = new Label(1, len + 2, "揽件人", times);
		sheet.addCell(label3);
		Label label2 = new Label(2, len + 2, "总件数", times);
		sheet.addCell(label2);
		Label label4 = new Label(3, len + 2, "总重量", times);
		sheet.addCell(label4);

		Label label7 = new Label(4, len + 2, "总计件费", times);
		sheet.addCell(label7);
		Label label9 = new Label(5, len + 2, "总称重费", times);
		sheet.addCell(label9);
		Label label12 = new Label(6, len + 2, "总区域补贴", times);
		sheet.addCell(label12);
		Label label10 = new Label(7, len + 2, "总中转费", times);
		sheet.addCell(label10);
		Label label13 = new Label(8, len + 2, "  ", times);
		sheet.addCell(label13);
		Label label6 = new Label(9, len + 2, "总费用", times);
		sheet.addCell(label6);

	}

	public void createSMYExcelFile(List<StatSMY> reportList, String filename,
			StatSMY allStatReport) throws WriteException {

		Date d = new Date();
		String day = DateUtil.formatYYYYMMDD(d);

		String filepath = this.getEnv("downpath") + "/" + filename + ".xls";

		File dir = new File(this.getEnv("path"));

		if (dir.exists()) {

			File files[] = dir.listFiles();

			for (int i = 0; i < files.length; i++) {

				files[i].deleteOnExit();
			}

			dir.delete();
		}

		File file = new File(filepath);

		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(Locale.CHINA);
		WritableWorkbook workbook;

		try {
			// 揽件日期 揽件人姓名 总件数 总重量 总扫描费 总出件费 总计重费 总派送补贴 总中转费 总费用
			workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet(day, 0);
			WritableSheet excelSheet = workbook.getSheet(0);
			excelSheet.setColumnView(0, 12);
			excelSheet.setColumnView(1, 11);
			excelSheet.setColumnView(2, 5);
			excelSheet.setColumnView(3, 6);
			excelSheet.setColumnView(4, 6);
			excelSheet.setColumnView(5, 6);
			excelSheet.setColumnView(6, 6);
			excelSheet.setColumnView(7, 6);
			excelSheet.setColumnView(8, 6);
			excelSheet.setColumnView(9, 6);

			WritableFont times12pt = new WritableFont(WritableFont.ARIAL, 8);
			// Define the cell format
			WritableCellFormat times = new WritableCellFormat(times12pt);
			// Lets automatically wrap the cells
			times.setWrap(true);

			times.setBorder(Border.ALL, BorderLineStyle.THIN);
			times.setAlignment(Alignment.CENTRE);
			times.setVerticalAlignment(VerticalAlignment.CENTRE);
			// Create create a bold font with unterlines

			CellView cv = new CellView();
			cv.setFormat(times);
			cv.setSize(9);
			int len = 0;

			// Write a few headers
			if (reportList != null && reportList.size() > 0) {
				createLabelSMY(excelSheet);
				for (int i = 0; i < reportList.size(); i++) {

					StatSMY statSMY = reportList.get(i);
					Label label = new Label(0, i + 1, DateUtil
							.formatYYYYMMDD(statSMY.getBsndate()), times);
					excelSheet.addCell(label);
					Label label1 = new Label(1, i + 1, statSMY.getManager(),
							times);
					excelSheet.addCell(label1);
					Number label2 = new Number(2, i + 1, statSMY.getWholenum(),
							times);
					excelSheet.addCell(label2);

					Number label3 = new Number(3, i + 1, statSMY
							.getWholeweight(), times);
					excelSheet.addCell(label3);

					Number label8 = new Number(4, i + 1, statSMY
							.getWholescanfei(), times);
					excelSheet.addCell(label8);

					Number label5 = new Number(5, i + 1, statSMY
							.getWholeweightprice(), times);
					excelSheet.addCell(label5);
					Number label6 = new Number(6, i + 1, statSMY
							.getWholebuzufei(), times);
					excelSheet.addCell(label6);
					Number label7 = new Number(7, i + 1, statSMY
							.getWholetransformprice(), times);
					excelSheet.addCell(label7);

					Label label10 = new Label(8, i + 1, "", times);
					excelSheet.addCell(label10);

					Number label11 = new Number(9, i + 1, statSMY
							.getWholewholeprice(), times);
					excelSheet.addCell(label11);

				}
				len = len + reportList.size();

			}

			if (allStatReport != null) {

				createLabelSMY2(excelSheet, len);
				//		 
				Label label0 = new Label(0, len + 3, day, times);
				excelSheet.addCell(label0);
				Label label1 = new Label(1, len + 3, "全部", times);
				excelSheet.addCell(label1);
				Number label2 = new Number(2, len + 3, allStatReport
						.getWholenum(), times);
				excelSheet.addCell(label2);

				Number label4 = new Number(3, len + 3, allStatReport
						.getWholeweight(), times);
				excelSheet.addCell(label4);

				Number label11 = new Number(4, len + 3, allStatReport
						.getWholescanfei(), times);
				excelSheet.addCell(label11);

				Number label7 = new Number(5, len + 3, allStatReport
						.getWholeweightprice(), times);
				excelSheet.addCell(label7);

				Number label8 = new Number(6, len + 3, allStatReport
						.getWholebuzufei(), times);
				excelSheet.addCell(label8);

				Number label10 = new Number(7, len + 3, allStatReport
						.getWholetransformprice(), times);
				excelSheet.addCell(label10);

				Label label13 = new Label(8, len + 3, "", times);
				excelSheet.addCell(label13);

				Number label12 = new Number(9, len + 3, allStatReport
						.getWholewholeprice(), times);
				excelSheet.addCell(label12);

			}

			reportList = null;
			workbook.write();
			workbook.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public void createExcelFile(List<StatReport> reportList, StatSMY smy,
			String filename) throws WriteException {

		Date d = new Date();
		String day = DateUtil.formatYYYYMMDD(d);

		String filepath = this.getEnv("downpath") + "/" + filename + ".xls";

		File dir = new File(this.getEnv("downpath"));

		if (dir.exists()) {

			File files[] = dir.listFiles();

			for (int i = 0; i < files.length; i++) {

				files[i].deleteOnExit();
			}

			dir.delete();
		}

		File file = new File(filepath);

		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(Locale.CHINA);
		WritableWorkbook workbook;

		try {

			workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet(day, 0);
			WritableSheet excelSheet = workbook.getSheet(0);

			excelSheet.setColumnView(0, 10);
			excelSheet.setColumnView(1, 5);
			excelSheet.setColumnView(2, 13);
			excelSheet.setColumnView(3, 11);
			excelSheet.setColumnView(4, 6);
			excelSheet.setColumnView(5, 17);
			excelSheet.setColumnView(6, 6);
			excelSheet.setColumnView(7, 6);
			excelSheet.setColumnView(8, 6);
			excelSheet.setColumnView(9, 6);
			excelSheet.setColumnView(10, 2);
			excelSheet.setColumnView(11, 6);

			WritableFont times12pt = new WritableFont(WritableFont.ARIAL, 8);
			// Define the cell format
			WritableCellFormat times = new WritableCellFormat(times12pt);
			// Lets automatically wrap the cells
			times.setWrap(true);

			times.setBorder(Border.ALL, BorderLineStyle.THIN);
			times.setAlignment(Alignment.CENTRE);
			times.setVerticalAlignment(VerticalAlignment.CENTRE);
			// Create create a bold font with unterlines

			CellView cv = new CellView();
			cv.setFormat(times);
			cv.setSize(8);
			int len = 0;

			// Write a few headers
			if (reportList != null && reportList.size() > 0) {
				createLabel(excelSheet);
				for (int i = 0; i < reportList.size(); i++) {
					Label label00 = new Label(0, i + 1, DateUtil
							.formatYYYYMMDD(reportList.get(i).getCreatetime()),
							times);
					excelSheet.addCell(label00);
					Label label0 = new Label(1, i + 1, "正常", times);
					excelSheet.addCell(label0);
					Label label1 = new Label(2, i + 1, reportList.get(i)
							.getMailid(), times);
					excelSheet.addCell(label1);
					Label label2 = new Label(3, i + 1, reportList.get(i)
							.getManager(), times);
					excelSheet.addCell(label2);

					Number label3 = new Number(4, i + 1, reportList.get(i)
							.getWeight(), times);
					excelSheet.addCell(label3);

					Label label8 = new Label(5, i + 1, reportList.get(i)
							.getDestinationcity(), times);
					excelSheet.addCell(label8);

					Number label4 = new Number(6, i + 1, reportList.get(i)
							.getScanfei(), times);
					excelSheet.addCell(label4);
					Number label5 = new Number(7, i + 1, reportList.get(i)
							.getChujianfei(), times);
					excelSheet.addCell(label5);
					Number label6 = new Number(8, i + 1, reportList.get(i)
							.getWeightprice(), times);
					excelSheet.addCell(label6);
					Number label7 = new Number(9, i + 1, reportList.get(i)
							.getBuzufei(), times);
					excelSheet.addCell(label7);

					Number label10 = new Number(10, i + 1, reportList.get(i)
							.getTransformprice(), times);
					excelSheet.addCell(label10);

					Label label11 = new Label(11, i + 1, "", times);
					excelSheet.addCell(label11);
					Number label9 = new Number(12, i + 1, reportList.get(i)
							.getWholeprice(), times);
					excelSheet.addCell(label9);

				}
				len = len + reportList.size();

			}

			if (smy != null) {

				createLabel2(excelSheet, len);
				//
				Label label00 = new Label(0, len + 3, "", times);
				excelSheet.addCell(label00);
				Label label0 = new Label(1, len + 3, "正常", times);
				excelSheet.addCell(label0);
				Number label1 = new Number(2, len + 3, smy.getWholenum(), times);
				excelSheet.addCell(label1);
				Label label2 = new Label(3, len + 3, "", times);
				excelSheet.addCell(label2);

				Number label4 = new Number(4, len + 3, smy.getWholeweight(),
						times);
				excelSheet.addCell(label4);

				Label label11 = new Label(5, len + 3, "", times);
				excelSheet.addCell(label11);

				Number label5 = new Number(6, len + 3, smy.getWholescanfei(),
						times);
				excelSheet.addCell(label5);
				Number label7 = new Number(7, len + 3,
						smy.getWholechujianfei(), times);
				excelSheet.addCell(label7);

				Number label8 = new Number(8, len + 3, smy
						.getWholeweightprice(), times);
				excelSheet.addCell(label8);

				Number label10 = new Number(9, len + 3, smy.getWholebuzufei(),
						times);
				excelSheet.addCell(label10);

				Number label14 = new Number(10, len + 3, smy
						.getWholetransformprice(), times);
				excelSheet.addCell(label14);

				Label label12 = new Label(11, len + 3, "", times);
				excelSheet.addCell(label12);

				Number label13 = new Number(12, len + 3, smy
						.getWholewholeprice(), times);
				excelSheet.addCell(label13);

			}

			reportList = null;
			workbook.write();
			workbook.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void main(String[] args) throws WriteException, IOException {
		// String path ="D:/saveexcel/test.xlsx";
		//		
		// IRowReader reader = new MailReader();
		//		
		// try {
		// ExcelReaderUtil.readExcel(reader, path);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		System.out.println(Integer.parseInt("2013-07-01".substring(5, 7)));
	}

}
