/**
 * 
 */
package com.yundastat.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.ObjectFindingVisitor;

import com.yundastat.util.*;

import jxl.write.WriteException;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.yundastat.excelutil.Excel2007Writer;
import com.yundastat.excelutil.Excel2007WriterPriceDetail;
import com.yundastat.framework.UserHolder;
import com.yundastat.model.DailyBalance;
import com.yundastat.model.MailInfo;
import com.yundastat.model.ManagerInfo;
import com.yundastat.model.PageBean;
import com.yundastat.model.PageParameter;
import com.yundastat.model.PriceDetail;
import com.yundastat.model.PriceDetailSmy;
import com.yundastat.model.Report;
import com.yundastat.model.StatReport;
import com.yundastat.model.StatSMY;
import com.yundastat.model.TransformCategory;
import com.yundastat.model.TransformPriceRule;
import com.yundastat.model.User;
import com.yundastat.model.WeightPriceRule;
import com.yundastat.model.WeightRule;
import com.yundastat.model.YearSmy;
import com.yundastat.service.MailService;
import com.yundastat.service.ParseHTML;
import com.yundastat.service.ParseLocalHtml;
import com.yundastat.service.ReportService;
import com.yundastat.service.UserServiceImpl;
import com.yundastat.util.DateUtil;

/**
 * @author hp
 *
 */
/**
 * @author Simon
 * 
 */
public class StatAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6349575037710335925L;
	/**
	 * 
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork.Action#execute()
	 */
	protected Map errorMessage = new HashMap();
	private UserServiceImpl userServiceImpl;
	private MailService mailService;
	private String weightInfo;
	private String weightPriceInfo;
	private String messageInfo;
	private String price;
	private String scanfei;
	private String chujianfei;
	private List<String> weightRuleList = new ArrayList();
	private List<StatSMY> statSmyList = new ArrayList();
	private int weightRuleId;
	private List<String> transformPriceRuleList = new ArrayList();
	private List<TransformPriceRule> transformPriceRuleNewList = new ArrayList();
	private List<TransformCategory> transformCategoryList = new ArrayList();
	private TransformCategory transformCategory;
	private List<String> managerList = new ArrayList();
	private List<ManagerInfo> managerInfoList = new ArrayList();
	private String importDate;
	private List<StatReport> statReportList = new ArrayList();
	private List<WeightRule> weightList = new ArrayList();
	private List<WeightPriceRule> weightPriceList = new ArrayList();
	private List<StatReport> statReportListMore = new ArrayList();
	private List<Report> statSMY = new ArrayList();
	private HashMap<String, List<Report>> reportMap = new HashMap();
	private PageBean pageBean;
	private ManagerInfo manager;
	private List<StatSMY> reportFlagList = new ArrayList();
	private String mailidList;
	private StatReport statReport;
	private MailInfo mailInfo;
	private String mailid;
	private int mailNum;
	private static Logger logger = Logger.getLogger(StatAction.class.getName());
	private Map<String, List> numList = new HashMap();
	private Map<String, List> decList = new HashMap();
	private Map<String, List> tenList = new HashMap();
	private List<List> numListL = new ArrayList();
	private List<List> decListL = new ArrayList();
	private String weightrulename;
	private String weightpricename;
	private int weightruleid;
	private TransformPriceRule transformPriceRule;
	private StatSMY statSmy;
	private List<Report> reportList;
	 private StatSMY flag;
	 private String transformcategoryid;
	 private List<PriceDetailSmy> priceDetailSmyList=new ArrayList();
	 private List<PriceDetail> priceDetailList=new ArrayList();
	 private ReportService reportService;
	 private List<ManagerInfo> pmanagerInfoList=new ArrayList();
	 public final static int pageSize = 10;
 
		public List<ManagerInfo> getPmanagerInfoList() {
		return pmanagerInfoList;
	}

	public void setPmanagerInfoList(List<ManagerInfo> pmanagerInfoList) {
		this.pmanagerInfoList = pmanagerInfoList;
	}

		public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

		public List<PriceDetail> getPriceDetailList() {
		return priceDetailList;
	}

	public void setPriceDetailList(List<PriceDetail> priceDetailList) {
		this.priceDetailList = priceDetailList;
	}

		public List<PriceDetailSmy> getPriceDetailSmyList() {
		return priceDetailSmyList;
	}

	public void setPriceDetailSmyList(List<PriceDetailSmy> priceDetailSmyList) {
		this.priceDetailSmyList = priceDetailSmyList;
	}

		public String getTransformcategoryid() {
		return transformcategoryid;
	}

	public void setTransformcategoryid(String transformcategoryid) {
		this.transformcategoryid = transformcategoryid;
	}

		public List<TransformCategory> getTransformCategoryList() {
		return transformCategoryList;
	}

	public void setTransformCategoryList(
			List<TransformCategory> transformCategoryList) {
		this.transformCategoryList = transformCategoryList;
	}

	public TransformCategory getTransformCategory() {
		return transformCategory;
	}

	public void setTransformCategory(TransformCategory transformCategory) {
		this.transformCategory = transformCategory;
	}

		public String getMailid() {
			return mailid;
		}

		public void setMailid(String mailid) {
			this.mailid = mailid;
		}

		public int getMailNum() {
			return mailNum;
		}

		public void setMailNum(int mailNum) {
			this.mailNum = mailNum;
		}
	public StatSMY getFlag() {
		return flag;
	}

	public void setFlag(StatSMY flag) {
		this.flag = flag;
	}

	public StatSMY getStatSmy() {
		return statSmy;
	}

	public void setStatSmy(StatSMY statSmy) {
		this.statSmy = statSmy;
	}

	public List<StatSMY> getStatSmyList() {
		return statSmyList;
	}

	public void setStatSmyList(List<StatSMY> statSmyList) {
		this.statSmyList = statSmyList;
	}

	public TransformPriceRule getTransformPriceRule() {
		return transformPriceRule;
	}

	public void setTransformPriceRule(TransformPriceRule transformPriceRule) {
		this.transformPriceRule = transformPriceRule;
	}

	public String getScanfei() {
		return scanfei;
	}

	public void setScanfei(String scanfei) {
		this.scanfei = scanfei;
	}

	public String getChujianfei() {
		return chujianfei;
	}

	public void setChujianfei(String chujianfei) {
		this.chujianfei = chujianfei;
	}

	public String getWeightpricename() {
		return weightpricename;
	}

	public void setWeightpricename(String weightpricename) {
		this.weightpricename = weightpricename;
	}

	public int getWeightruleid() {
		return weightruleid;
	}

	public void setWeightruleid(int weightruleid) {
		this.weightruleid = weightruleid;
	}

	public String getWeightrulename() {
		return weightrulename;
	}

	public void setWeightrulename(String weightrulename) {
		this.weightrulename = weightrulename;
	}
 

	public List<WeightRule> getWeightList() {
		return weightList;
	}

	public void setWeightList(List<WeightRule> weightList) {
		this.weightList = weightList;
	}

	public List<List> getNumListL() {
		return numListL;
	}

	public void setNumListL(List<List> numListL) {
		this.numListL = numListL;
	}

	public List<List> getDecListL() {
		return decListL;
	}

	public void setDecListL(List<List> decListL) {
		this.decListL = decListL;
	}

	public Map<String, List> getTenList() {
		return tenList;
	}

	public void setTenList(Map<String, List> tenList) {
		this.tenList = tenList;
	}

	public Map<String, List> getDecList() {
		return decList;
	}

	public void setDecList(Map<String, List> decList) {
		this.decList = decList;
	}

	public Map<String, List> getNumList() {
		return numList;
	}

	public void setNumList(Map<String, List> numList) {
		this.numList = numList;
	}


	 
	public MailInfo getMailInfo() {
		return mailInfo;
	}

	public void setMailInfo(MailInfo mailInfo) {
		this.mailInfo = mailInfo;
	}

	public StatReport getStatReport() {
		return statReport;
	}

	public void setStatReport(StatReport statReport) {
		this.statReport = statReport;
	}

	public List<TransformPriceRule> getTransformPriceRuleNewList() {
		return transformPriceRuleNewList;
	}

	public void setTransformPriceRuleNewList(
			List<TransformPriceRule> transformPriceRuleNewList) {
		this.transformPriceRuleNewList = transformPriceRuleNewList;
	}

	 
	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public List<StatSMY> getReportFlagList() {
		return reportFlagList;
	}

	public void setReportFlagList(List<StatSMY> reportFlagList) {
		this.reportFlagList = reportFlagList;
	}

	public List<StatReport> getStatReportListMore() {
		return statReportListMore;
	}

	public void setStatReportListMore(List<StatReport> statReportListMore) {
		this.statReportListMore = statReportListMore;
	}

	public String getMailidList() {
		return mailidList;
	}

	public void setMailidList(String mailidList) {
		this.mailidList = mailidList;
	}

	public List<ManagerInfo> getManagerInfoList() {
		return managerInfoList;
	}

	public void setManagerInfoList(List<ManagerInfo> managerInfoList) {
		this.managerInfoList = managerInfoList;
	}

	public ManagerInfo getManager() {
		return manager;
	}

	public void setManager(ManagerInfo manager) {
		this.manager = manager;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public HashMap<String, List<Report>> getReportMap() {
		return reportMap;
	}

	public void setReportMap(HashMap<String, List<Report>> reportMap) {
		this.reportMap = reportMap;
	}

	public List<Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}

	public List<WeightPriceRule> getWeightPriceList() {
		return weightPriceList;
	}

	public void setWeightPriceList(List<WeightPriceRule> weightPriceList) {
		this.weightPriceList = weightPriceList;
	}
 
	private String searchInfo;

	public String getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}

	public List<String> getManagerList() {
		return managerList;
	}

	public void setManagerList(List<String> managerList) {
		this.managerList = managerList;
	}

	public List<StatReport> getStatReportList() {
		return statReportList;
	}

	public void setStatReportList(List<StatReport> statReportList) {
		this.statReportList = statReportList;
	}

	public List<String> getTransformPriceRuleList() {
		return transformPriceRuleList;
	}

	public void setTransformPriceRuleList(List<String> transformPriceRuleList) {
		this.transformPriceRuleList = transformPriceRuleList;
	}

	public int getWeightRuleId() {
		return weightRuleId;
	}

	public void setWeightRuleId(int weightRuleId) {
		this.weightRuleId = weightRuleId;
	}

	 
	public List<String> getWeightRuleList() {
		return weightRuleList;
	}

	public void setWeightRuleList(List<String> weightRuleList) {
		this.weightRuleList = weightRuleList;
	}

	public String getWeightPriceInfo() {
		return weightPriceInfo;
	}

	public void setWeightPriceInfo(String weightPriceInfo) {
		this.weightPriceInfo = weightPriceInfo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	public String getWeightInfo() {
		return weightInfo;
	}

	public void setWeightInfo(String weightInfo) {
		this.weightInfo = weightInfo;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setErrorMessage(Map errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub

		return INPUT;
	}

	public String showStatPage() throws IOException {

		logger.info("Show StatPage!");
		return MAIN_PAGE;

	}

	public String showImportDataPage() throws IOException {

		//
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		
		
		String pageNoStr = request.getParameter("pageNo");
		 
		int start=0;
		int pageN=1;
		
		if(pageNoStr==null||pageNoStr.equals(""))
			pageN=1;
		else pageN=Integer.parseInt(pageNoStr);
		
		PageParameter	p = new PageParameter();
	 
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		reportFlagList=mailService.queryReportFlagByPage(p);
		
 
		pageBean = new PageBean(15, pageN, reportFlagList.size());

		p = new PageParameter();
		start = (pageN - 1) *15;
		p.setStart(start);
		p.setSize(15);
	  
		reportFlagList = mailService.queryReportFlagByPage(p);
		 
		return IMPORT_PAGE;

	}

	public String showImportTransformPage() throws IOException {

		return TRANSFORM_IMPORT;

	}

	 


	public String deleteMailAndStatReport() throws UnsupportedEncodingException, ParseException {

		//
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		importDate = request.getParameter("importDate");
		String startTime = importDate + " 00:00:00";
		String endTime = importDate + " 23:59:59";
		int mon=Integer.parseInt(importDate.substring(5, 7));
		//同时删除汇总数据
		Date bsndate= DateUtil.parse(importDate + " 12:00:00",
				DateUtil.DATE_TIME_FORMAT);
		Date reportdate= DateUtil.parse(importDate + " 00:00:00",
				DateUtil.DATE_TIME_FORMAT);
			
		
		PageParameter p1 = new PageParameter();
		p1.setBsndate(bsndate);
		p1.setManager(null);
		p1.setMon(mon);
		List<StatSMY> statSMYList = mailService.queryForStatSMY(p1);
		
//
//		PageParameter	p = new PageParameter();
//	 
//		p.setStart(0);
//		p.setSize(Integer.MAX_VALUE);
//		p.setBsndate(bsndate);
//		reportFlagList=mailService.queryReportFlagByPage(p);
//		if(reportFlagList!=null&&reportFlagList.size()>0)
//			flag=reportFlagList.get(0);
		
		PageParameter pageParameter = new PageParameter();
		pageParameter.setStarttime(startTime);
		pageParameter.setEndtime(endTime);
		pageParameter.setBsndate(bsndate);
		pageParameter.setMon(mon);
		mailService.deleteExitedMailAndStatReport(pageParameter);
	
	 
		StatSMY flg = new StatSMY();
		flg.setReportdate(importDate);
		mailService.deleteReportFlag(flg);
		
		
		 DailyBalance db=new DailyBalance();
		 db.setBsndate(reportdate);
		 List<DailyBalance> dailybalanceList=reportService.getDailyBalance(db);
		 
		 if(dailybalanceList!=null&&dailybalanceList.size()>0){
			 
			   int sk=0;
			   if(statSMYList!=null&&statSMYList.size()>0)
				   sk=statSMYList.size();
				for (int i = 0; i < sk; i++) {
					
					StatSMY smy = statSMYList.get(i);
				 	DailyBalance d=new DailyBalance();
					d.setBsndate(reportdate);
					d.setManager(smy.getManager());
					List<DailyBalance> dbList = reportService.getDailyBalance(d);
					
					if(dbList!=null&&dbList.size()>0){
						
						DailyBalance db2 = dbList.get(0);
					 
							db2.setJijianprice(0);
						 
							db2.setWeightprice(0);
					 
							db2.setBuzuprice(0);
					 
							db2.setTransformprice(0);
					 
							db2.setNum(0);
							
							
						double wprice=db2.getJijianprice()+db2.getWeightprice()+db2.getBuzuprice()+db2.getTransformprice()
						+ db2.getFakuanprice()+ db2.getPaisongprice()+ db2.getZhuanjianprice()+ db2.getDiujianprice();
						BigDecimal cc = new BigDecimal(wprice);
						// 保留2位小数
						wprice = cc.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						db2.setWholeprice(wprice);
						
						reportService.updateDailyBalance(db2);
					}
			 			
				}
				
		 }
		 
		    DailyBalance qu = new DailyBalance();
			qu.setBsndate(reportdate);
			DailyBalance ddd=reportService.getDailyBalanceSmy(qu);
			if(ddd==null)
				ddd=new DailyBalance();
			
		   YearSmy smy=new YearSmy();
			smy.setReportdate(reportdate);
			List<YearSmy> ysList=reportService.getYearSmy(smy);
		
			if(ysList!=null&&ysList.size()>0){
				
				YearSmy ys=ysList.get(0);
				ys.setZnum(ddd.getNum());
				ys.setZprice(ddd.getWholeprice());
				
				double tinprice=ys.getZprice()+ys.getWprice()+ys.getYprice();
				ys.setTinprice(tinprice);
				double tpprice=ys.getTinprice()-ys.getToutprice();
			
				ys.setTpprice(tpprice);
				reportService.updateYearSmy(ys);
				
				
			}

		this.addErrorMessage("hello", importDate + "快件和报表已被全部删除！");
     	int start=0;
		int pageN=1;
		
		PageParameter	p = new PageParameter();
	 
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		reportFlagList=mailService.queryReportFlagByPage(p);
		
 
		pageBean = new PageBean(15, pageN, reportFlagList.size());

		p = new PageParameter();
		start = (pageN - 1) *15;
		p.setStart(start);
		p.setSize(15);
	  
		reportFlagList = mailService.queryReportFlagByPage(p);
		return "finimport";

	}
 
 
	public String searchStatPage() throws UnsupportedEncodingException, ParseException {
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
		int totalN = Integer.parseInt(totalNum);

		String starttime = importDate + " " + "12:00:00";
		String endtime = importDate + " " + "12:00:00";
		  int mon=Integer.parseInt(importDate.substring(5, 7));
		PageParameter page2 = new PageParameter();

		page2.setEndtime(endtime);
		page2.setStarttime(starttime);
		page2.setManager(null);
		page2.setSize(FileProcAction.pageSize);
		page2.setStart((pageN - 1) * FileProcAction.pageSize);
        page2.setMon(mon);
		statReportList = mailService.queryForStatReportPage(page2);
		pageBean = new PageBean(FileProcAction.pageSize, pageN, totalN);
		
		Date bsndate = DateUtil.parse(importDate + " 12:00:00",
				DateUtil.DATE_TIME_FORMAT);

		PageParameter	p = new PageParameter();	 
		p.setStart(0);
		p.setSize(30);
		p.setBsndate(bsndate);
		reportFlagList=mailService.queryReportFlagByPage(p);
		
		flag=reportFlagList.get(0);
	 	
		
		return FINIMPORT;
	}

//	

	public String searchStatReport() throws UnsupportedEncodingException,
			WriteException, ParseException {
		// 展示时一部分， 下载的快件是全部
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str = new String(request.getParameter("searchInfo").getBytes(
				"ISO8859-1"), "utf-8");
		searchInfo = str;
		String[] para = str.split("@");

		String manager = para[0];
		String startdate = para[1];
		String starttime = startdate + " 12:00:00";
		String pageNo = request.getParameter("pageNo");
		Date bsndate=DateUtil.parse(starttime,
				DateUtil.DATE_TIME_FORMAT);
		int mon=Integer.parseInt(startdate.substring(5, 7));
		int start = 0;
		int totalRow = 0;

		List<ManagerInfo> tempList = mailService
				.queryForManagerInfo(null, null);
	 
		for (int i = 0; i < tempList.size(); i++) {
			managerList.add(i, tempList.get(i).getManagername());

		}

		// 将查询结果直接插入到Excel 文件中
		int currentPage = 1;
		if (pageNo != null) {

			currentPage = Integer.parseInt(pageNo);
		}

	 
	 //我个人认为，不需要取出全部，只要取出当前页的数据和总和就可以。
		
			PageParameter	p = new PageParameter();	 
			p.setManager(manager);
			p.setBsndate(bsndate);
			
			reportFlagList=mailService.queryForStatSMY(p);
			
			if(reportFlagList==null||reportFlagList.size()==0){
				
				
				this.addErrorMessage("hello", "不存在任何快件！");
			}else{
		 
			
         	p = new PageParameter();
			start = (currentPage - 1) * FileProcAction.pageSize;
			p.setStart(start);
			p.setSize(FileProcAction.pageSize);
			p.setEndtime(starttime);
			p.setStarttime(starttime);
			p.setState("normal");
			if (manager.equals("全部"))
				p.setManager(null);
			else
				p.setManager(manager);
            p.setMon(mon);
			statReportList = mailService.queryForStatReportPage(p);
			
			pageBean = new PageBean(FileProcAction.pageSize, currentPage,
					totalRow);
		

		// /操作excel
			
			}

		return STAT_REPORT;
	}


	public String searchStatReportNew() throws UnsupportedEncodingException,
			WriteException, ParseException {
		// 展示时一部分， 下载的快件是全部
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

	 
		String manager =statReport.getManager();
	
		String starttime = statReport.getStarttime() + " 12:00:00";
		String endtime = statReport.getEndtime() + " 12:00:00";
		String pageNo = request.getParameter("pageNo");
		Date bsndate=DateUtil.parse(starttime,
				DateUtil.DATE_TIME_FORMAT);
		int mons=Integer.parseInt(starttime.substring(5, 7));
		int mone=Integer.parseInt(endtime.substring(5, 7));

		if(mons!=mone){
			
			List<ManagerInfo> tempList = mailService
			.queryForManagerInfo(null, null);
 
	     for (int i = 0; i < tempList.size(); i++) {
		managerList.add(i, tempList.get(i).getManagername());

	    }
	     this.addErrorMessage("hello", "数据日期要在同一个月内！");
          return STAT_REPORT;
		}
		
		// 将查询结果直接插入到Excel 文件中
		int currentPage = 1;
		if (pageNo != null) {

			currentPage = Integer.parseInt(pageNo);
		}


		int start = 0;
		int totalRow = 0;
		 

	    PageParameter 	pp = new PageParameter();
	 	pp.setEndtime(endtime);
		pp.setStarttime(starttime);
		pp.setState("normal");
		if (manager.equals("all"))
			pp.setManager(null);
		else
			pp.setManager(manager);
        pp.setMon(mons);
        totalRow = mailService.queryForStatReportCount(pp);
			
		    PageParameter 	p = new PageParameter();
			start = (currentPage - 1) * FileProcAction.pageSize;
			p.setStart(start);
			p.setSize(FileProcAction.pageSize);
			p.setEndtime(endtime);
			p.setStarttime(starttime);
			p.setState("normal");
			if (manager.equals("all"))
				p.setManager(null);
			else
				p.setManager(manager);
            p.setMon(mons);
			statReportList = mailService.queryForStatReportPage(p);
			
			pageBean = new PageBean(FileProcAction.pageSize, currentPage,
					totalRow);
		
			

			List<ManagerInfo> tempList = mailService
					.queryForManagerInfo(null, null);
		 
			for (int i = 0; i < tempList.size(); i++) {
				managerList.add(i, tempList.get(i).getManagername());

			}

		return STAT_REPORT;
	}

	
	//导出excel by xlsx
	public String exportStatReportNew() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		 
		String manager =statReport.getManager();
	
		String starttime = statReport.getStarttime() + " 12:00:00";
		String endtime = statReport.getEndtime() + " 12:00:00";
		String pageNo = request.getParameter("pageNo");
		try {
			Date bsndate=DateUtil.parse(starttime,
					DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int mons=Integer.parseInt(starttime.substring(5, 7));
		int mone=Integer.parseInt(endtime.substring(5, 7));

		if(mons!=mone){
			
			List<ManagerInfo> tempList = mailService
			.queryForManagerInfo(null, null);
 
	     for (int i = 0; i < tempList.size(); i++) {
		managerList.add(i, tempList.get(i).getManagername());

	    }
	     this.addErrorMessage("hello", "数据日期要在同一个月内！");
          return STAT_REPORT;
		}
		
	      PageParameter 	p = new PageParameter();		 
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setEndtime(endtime);
			p.setStarttime(starttime);
			p.setState("normal");
			if (manager.equals("all"))
				p.setManager(null);
			else
				p.setManager(manager);
            p.setMon(mons);
			int wholenum = mailService.queryForStatReportCount(p);
			
			String outfilename = this.getEnv("downpath") + "/" + statReport.getStarttime() + "_search.xlsx";
	try {

		Excel2007Writer writer = new Excel2007Writer();
		writer.createSheet();
		 
		PageParameter	pe = new PageParameter();
		pe.setEndtime(endtime);
		pe.setStarttime(starttime);
		pe.setMon(mons);

		int pagesize =wholenum / 10000;

		if (pagesize * 10000 < wholenum) {
			pagesize = pagesize + 1;
		}
		for (int i = 0; i < pagesize; i++) {
			pe.setStart(i * 10000);
			pe.setSize(10000);
			if (manager.equals("all"))
				pe.setManager(null);
			else
				pe.setManager(manager);
			statReportList = mailService.queryForStatReportPage(pe);
			writer.process(outfilename, statReportList, i * 10000, wholenum);
		}

		writer.createFile(outfilename);

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			
		 

		// 保存文件
		response.setContentType("application/vnd.ms-excel;charset=GBK");

		try {
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String((statReport.getStarttime() + "_search.xlsx").getBytes("gb2312"),
							"iso8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(outfilename));

			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[1024];
			int bytesRead;

			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
				bos.write(buff, 0, bytesRead);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();

		}

	 
		return IMPORT_PAGE;
	}

	
	
	public String exportStatReport() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		 
		String manager =statReport.getManager();
	
		String starttime = statReport.getStarttime() + " 12:00:00";
		String endtime = statReport.getEndtime() + " 12:00:00";
		String pageNo = request.getParameter("pageNo");
		try {
			Date bsndate=DateUtil.parse(starttime,
					DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int mons=Integer.parseInt(starttime.substring(5, 7));
		int mone=Integer.parseInt(endtime.substring(5, 7));

		if(mons!=mone){
			
			List<ManagerInfo> tempList = mailService
			.queryForManagerInfo(null, null);
 
	     for (int i = 0; i < tempList.size(); i++) {
		managerList.add(i, tempList.get(i).getManagername());

	    }
	     this.addErrorMessage("hello", "数据日期要在同一个月内！");
          return STAT_REPORT;
		}
		
	      PageParameter 	p = new PageParameter();		 
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setEndtime(endtime);
			p.setStarttime(starttime);
			p.setState("normal");
			if (manager.equals("all"))
				p.setManager(null);
			else
				p.setManager(manager);
            p.setMon(mons);
			statReportList = mailService.queryForStatReportPage(p);
			
			
		   FileProcAction fp = new FileProcAction();
		try {
			fp.createExcelFile(statReportList, null,  statReport.getStarttime()+ "_search");
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String path = this.getEnv("downpath") + "/" + statReport.getStarttime()+ "_search.xls";

		// 保存文件
		response.setContentType("application/vnd.ms-excel;charset=GBK");

		try {
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String((statReport.getStarttime() + "_search.xls").getBytes("gb2312"),
							"iso8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(path));

			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[1024];
			int bytesRead;

			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
				bos.write(buff, 0, bytesRead);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();

		}

	 
		return IMPORT_PAGE;
	}

	
	
	
	public String showStatReport() throws IOException {

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		managerInfoList = mailService.queryForManagerInfoNew(m);
	 
		if(managerInfoList!=null){
			
			int len=managerInfoList.size();
		for (int i = 0; i < len; i++) {
			managerList.add(i, managerInfoList.get(i).getManagername());

		}

		}
		
		return STAT_REPORT;

	}

	

	public String showTransformCategory() throws SQLException {

		transformCategoryList = mailService
				.getTransformCategory(new TransformCategory());
		return TRANSFORM_CATEGORY;
	}
	public String deleteTransformCategory() {

		// 首先检查该categoryid下是否有列表，如果为空或者为0，则删除，否则无法删除
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		transformcategoryid = request.getParameter("transformcategoryid");
		TransformPriceRule r = new TransformPriceRule();
		r.setTransformcategoryid(Integer.parseInt(transformcategoryid));
		r.setIsdeleted("n");
		transformPriceRuleNewList = mailService
				.queryForTransformPriceRuleList(r);
 
		if(transformPriceRuleNewList!=null){
			
			for(int i=0;i<transformPriceRuleNewList.size();i++){
			TransformPriceRule ts=	transformPriceRuleNewList.get(i);
			mailService.deleteTransformPrice(ts.getCode(), ts.getTransformcategoryid());
			}
			
		}
		
			TransformCategory t = new TransformCategory();
			t.setTransformcategoryid(Integer.parseInt(transformcategoryid));
			mailService.deleteTransformCategory(t);
			this.addErrorMessage("hello", "费用方案删除成功！");
	 
		transformCategoryList = mailService
				.getTransformCategory(new TransformCategory());
		return TRANSFORM_CATEGORY;

	}
	
	public String showUpdateTransformCategory() throws SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		String transformcategoryid = request
				.getParameter("transformcategoryid");
		TransformCategory c = new TransformCategory();
		c.setTransformcategoryid(Integer.parseInt(transformcategoryid));
		transformCategoryList = mailService.getTransformCategory(c);
		if (transformCategoryList != null && transformCategoryList.size() == 1)
			transformCategory = transformCategoryList.get(0);
		return "showupdatetransformcategory";
	}

	public String updateTransformCategory() throws UnsupportedEncodingException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		transformcategoryid = request.getParameter("transformcategoryid");

		String trname = new String(request
				.getParameter("transformcategoryname").getBytes("ISO8859-1"),
				"utf-8");
		TransformCategory r = new TransformCategory();
		r.setTransformcategoryid(Integer.parseInt(transformcategoryid));
		r.setIsdeleted("n");
		transformCategoryList = mailService.getTransformCategory(r);

		if (transformCategoryList.size() != 1) {
			this.addErrorMessage("hello", "无法更新费用方案。");
		} else {
			TransformCategory t = transformCategoryList.get(0);
			t.setTransformcategoryname(trname);
			mailService.updateTransformCategory(t);
			this.addErrorMessage("hello", "费用方案更新成功！");
		}
		TransformCategory c = new TransformCategory();
		transformCategoryList = mailService.getTransformCategory(c);

		if (transformCategoryList != null && transformCategoryList.size() == 1)
			transformCategory = transformCategoryList.get(0);
		return "showupdatetransformcategory";

	}
	public String setTransformCategory() throws SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		// 在mailid=kkkkkk&flag=one or mailid=kkkkkk@kkkkk&flag=more

		transformCategory.setStatus("n");
		transformCategory.setIsdeleted("n");
		mailService.createTransformCategory(transformCategory);
		this.addErrorMessage("hello", "费用方案添加成功！");
		transformCategoryList = mailService
				.getTransformCategory(new TransformCategory());
		return TRANSFORM_CATEGORY;
	}
	
	public String copyTransformCategory() throws SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		// 在mailid=kkkkkk&flag=one or mailid=kkkkkk@kkkkk&flag=more
		String transformCategoryidstr=request.getParameter("transformCategoryid");
		
		TransformCategory t=new TransformCategory();
		t.setTransformcategoryid(Integer.parseInt(transformCategoryidstr));
		List<TransformCategory> tList = mailService
		.getTransformCategory(t);
		
		//create TransformCategory
		
		if(tList!=null&&tList.size()>0){
			TransformCategory tt=tList.get(0);
			tt.setTransformcategoryname(tt.getTransformcategoryname()+"_复制");
			int tid=mailService.createTransformCategory(tt);
			
			//copy all transformruleprice
		 	TransformPriceRule r = new TransformPriceRule();
			r.setTransformcategoryid(Integer.parseInt(transformCategoryidstr));
			r.setIsdeleted("n");
			transformPriceRuleNewList = mailService
					.queryForTransformPriceRuleList(r);
			
			if(transformPriceRuleNewList!=null){
			for(int i=0;i<transformPriceRuleNewList.size();i++){
				TransformPriceRule ts=	transformPriceRuleNewList.get(i);
			    ts.setCreatetime(new Date());
			    ts.setTransformcategoryid(tid);
			     mailService.createNewTransformPriceRule(ts);
			
			}
			this.addErrorMessage("hello", "费用方案复制成功！");
		}
		
		
		}else{
			this.addErrorMessage("hello", "费用方案复制失败！");
		}
		
		
		transformCategoryList = mailService
				.getTransformCategory(new TransformCategory());
		return TRANSFORM_CATEGORY;
	}
	
	public String showReport() throws IOException {
 
		// 读取业务员列表
		List<ManagerInfo> tempList = mailService
				.queryForManagerInfo(null, null);
		managerList.add(0, "全部");
		for (int i = 0; i < tempList.size(); i++) {
			managerList.add(i + 1, tempList.get(i).getManagername());

		}

		return REPORT;

	}

	public String showCompareData() throws IOException {

		return COMPARE_DATA;
	}

	

	/**
	 * 取出的信息：1，省市的列表；2，当前的重量方案;3,列出所有中转站的价格
	 */
	public String showSetTransformPrice() throws IOException {

		WeightRule weightRule = mailService.queryForWeightRule();

		if (weightRule == null) {

			return SET_TRANSFOMR_PRICE;
		}

		// 列出所有的价格
		transformPriceRuleNewList = mailService.queryForTransformPriceRule("");

		if (transformPriceRuleNewList == null
				|| transformPriceRuleNewList.size() == 0)
			return SET_TRANSFOMR_PRICE;
		
		int tlen=transformPriceRuleNewList.size();

		for (int i = 0; i < tlen; i++) {
			String weightprice = "";
			String weightRuleContent = transformPriceRuleNewList.get(i)
					.getWeightrulecontent();
			String pricelist = transformPriceRuleNewList.get(i).getPricelist();
			// 此处要考虑到重量区间未添加的情况。
			String[] weight = weightRuleContent.split(";");
			String[] price = pricelist.split(",");
			int len = weight.length - 1;
			int lenp = price.length - 1;

			for (int j = 0; j < len; j++) {
				String[] tmp = weight[j].split(",");
				weightprice = weightprice + "(" + tmp[0] + "-" + tmp[1]
						+ ")公斤内价格:   " + price[j] + "  元;  ";

			}

			String tmpStr[] = weight[len - 1].split(",");
			String xuweight = tmpStr[1];
			weightprice = weightprice + "续重价格(>" + xuweight + "公斤):   "
					+ price[lenp - 1] + " 元;   ";
			weightprice = weightprice + "补助费:  " + price[lenp] + "  元";

			transformPriceRuleNewList.get(i).setWeightpricelist(weightprice);

		}

		return SET_TRANSFOMR_PRICE;

	}

	/**
	 * 取出的信息：1，省市的列表；2，当前的重量方案;3,列出所有中转站的价格
	 */
	public String showTransformPriceList() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		// 在mailid=kkkkkk&flag=one or mailid=kkkkkk@kkkkk&flag=more

		// 列出所有的价格
		String transformcategoryidstr=request.getParameter("transformcategoryid");
		int transformcategoryid=0;
		if(transformcategoryidstr!=null)
			transformcategoryid=Integer.parseInt(transformcategoryidstr);
			
		TransformPriceRule r = new TransformPriceRule();
		r.setTransformcategoryid(transformcategoryid);
		r.setIsdeleted("n");
		transformPriceRuleNewList = mailService
				.queryForTransformPriceRuleList(r);

		TransformCategory c=new TransformCategory();
		c.setTransformcategoryid(transformcategoryid );
		List<TransformCategory> listC=mailService.getTransformCategory(c);
		
		if(listC!=null&&listC.size()==1){
			
			transformCategory=listC.get(0);
		}
		 
		if (transformPriceRuleNewList == null
				|| transformPriceRuleNewList.size() == 0)
			return SET_TRANSFOMR_PRICE;

		for (int i = 0; i < transformPriceRuleNewList.size(); i++) {
			String weightprice = "";

			String pricelist = transformPriceRuleNewList.get(i).getPricelist();
			// 此处要考虑到重量区间未添加的情况。
			// price=中转费方案:首重:单价
			// price=中转费方案：首重：首重价格,续重单价
            // //price=中转费方案：首重:首重价格：续重,续重单价:超重,超重费：补助费
			// 单价式
			String str[] = pricelist.split(":");
			if (str[0].equals("1")) {
				// 1 表示单价式
				weightprice = "单价式中转费价格方案：<br> 当重量在" + str[1] + "公斤以内时，中转费= 首重*单价；" + "<br> 当重量大于"
						+ str[1] + "公斤时，中转费= 重量*单价；其中单价为：" + str[2]
						+ "元/公斤；<br> 区域补贴：" + str[3] + "元/票";

			} else if (str[0].equals("2")){
				// 1 表示首重式

				String ss[] = str[2].split(",");
				weightprice = "首重式中转费价格方案： <br>   当重量在" + str[1] + "公斤以内时，中转费=首重价格；<br>  当重量大于"
						+ str[1] + "公斤时，中转费=" + ss[0]
						+ "+ (重量-首重)*续重单价；<br> 其中首重价格 为：" + ss[0] + "元；续重单价为："
						+ ss[1] + "元/公斤；<br> 区域补贴：" + str[3] + "元/票";

			}else if (str[0].equals("3")){
				
				String ss[] = str[4].split(",");
				weightprice = "分段式中转费价格方案：<br>    当重量在" + str[1] + "公斤以内时，中转费=最小结算重量*第一段价格+第一段固定加成；<br>  当重量大于"
						+ str[1] + "公斤且小于等于重量分界点"+str[3]+"时，中转费=重量*第一段价格+第一段固定加成；"+"<br> 当重量大于"
						+ str[3] +"重量分界点时；中转费=重量分界点*第一段价格+第一段固定加成+(重量-重量分界点)*第二段价格；<br> 其中第一段价格 为：" + ss[0] + "元/公斤；第二段续重价格 为"+ss[1]+"元/公斤；第一段固定加成为"+str[2]+"元；<br> 区域补贴：" + str[5] + "元/票";
			}else if (str[0].equals("4")){
				
				String ss[] = str[3].split(",");
				String w="";
				String p="";
				if(str[4].indexOf(",")!=-1){
					w=str[4].split(",")[0];
					p=str[4].split(",")[1];
					
				}else{
					w="1";
					p=str[4];
				}
					
				weightprice = "三段式中转费价格方案：<br>     当重量在" + str[1] + "公斤以内时，中转费=首重价格； 当重量大于"
						+ str[1] + "公斤小于等于"+ss[0]+"公斤时，中转费=续重价格；当重量大于" +ss[0]+ "公斤时，中转费=续重价格+（取整重量-续重)*超重单价；其中首重价格 为：" +  str[2]+ "元；续重单价为："
						+ ss[1] + "元；每超重"+w+"公斤费用为"+p+"元";
				
			}else if (str[0].equals("5")){
				
				String ss[] = str[2].split(",");
				weightprice = "二段式取整价格方案：  <br>   当重量在" + str[1] + "公斤以内时，中转费=首重价格； 当重量大于"
						+ str[1] + "公斤时，中转费=" + ss[0]
						+ "+ (取整重量-首重)*续重单价；<br>  其中首重价格 为：" + ss[0] + "元；续重单价为："
						+ ss[1] + "元/公斤";
			}else if (str[0].equals("6")){
				 //price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格：中转费方案：首重,首重价格,续重单价：补助费
				
				String ss1[] = str[1].split(",");
				
				if(str.length>=5){
					weightprice = "小件与超重中转费方案： <br>    当重量在" + ss1[0] + "-"+ss1[1]+"公斤以内时，中转费="+ss1[2]+"元； ";	
					
				}
				if(str.length>=6){
					String ss2[] = str[2].split(",");
					weightprice =weightprice+ " <br> 当重量在" + ss2[0] + "-"+ss2[1]+"公斤以内时，中转费="+ss2[2]+"元；";	
					
				}
				if(str.length>=7){
					
					String ss3[] = str[3].split(",");
					weightprice =weightprice+ "<br>  当重量在" + ss3[0] + "-"+ss3[1]+"公斤以内时，中转费="+ss3[2]+"元；";	
				}
				   if (str.length >= 8)
			        {
			          String[] ss3 = str[4].split(",");
			          weightprice = weightprice + "<br>  当重量在" + ss3[0] + "-" + ss3[1] + "公斤以内时，中转费=" + ss3[2] + "元；";
			        }
				
				 String ss5[] = str[str.length-2].split(",");
				if(str[str.length-3].equals("7")){
					weightprice=weightprice+";<br>  单价式：中转费=单价*重量;其中 单价为"+ss5[1]+"元/公斤；派送补贴："+str[str.length-1] + "元/票";
				}
				if(str[str.length-3].equals("8")){
					weightprice=weightprice+";<br>  首重式：中转费=首重价格+（重量-首重)*续重单价;其中首重价格为"+ss5[1]+"元；续重单价为"+ss5[2]+"元/公斤；派送补贴："+str[str.length-1] + "元/票";
				}		
				
			}else{
				
				///price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格:首重,首重二,价格： 首重价格:续重单价：补助费
				String ss1[] = str[1].split(",");
				
				if(str.length>=5){
					weightprice = "分段单价式中转费价格方案：  <br>    当重量在" + ss1[0] + "-"+ss1[1]+"公斤以内时，中转费="+ss1[2]+"元； ";	
					
				}
				if(str.length>=6){
					String ss2[] = str[2].split(",");
					weightprice =weightprice+ " <br> 当重量在" + ss2[0] + "-"+ss2[1]+"公斤以内时，中转费="+ss2[2]+"元；";	
					
				}
				if(str.length>=7){
					
					String ss3[] = str[3].split(",");
					weightprice =weightprice+ "<br>  当重量在" + ss3[0] + "-"+ss3[1]+"公斤以内时，中转费="+ss3[2]+"元；";	
				 }
				 if (str.length >= 8)
			        {
			          String[] ss3 = str[4].split(",");
			          weightprice = weightprice + "<br>  当重量在" + ss3[0] + "-" + ss3[1] + "公斤以内时，中转费=" + ss3[2] + "元；";
			        }
				
		 		
				// 1 表示单价式
				 weightprice = weightprice+"  <br> 当重量在2公斤以内时，中转费= 重量*"+str[str.length-3] + "元/公斤;<br>当重量大于2公斤时，中转费= 重量*"+str[str.length-2]+ "元/公斤；<br> 区域补贴：" +str[str.length-1] + "元/票";
				
			}

			transformPriceRuleNewList.get(i).setWeightpricelist(weightprice);

		}
		return SET_TRANSFOMR_PRICE;

	}

	public String showUpdateTransformPrice() {

		// 显示信息

		TransformPriceRule r = new TransformPriceRule();
		r.setTransformpriceruleid(transformPriceRule.getTransformpriceruleid());
		transformPriceRuleNewList = mailService
				.queryForTransformPriceRuleList(r);

		transformPriceRule = transformPriceRuleNewList.get(0);

		price = transformPriceRule.getPricelist();

		return UPDATE_TRANSFOMR_PRICE;
	}

	

	public String showCreateTransformPrice() {

		WeightRule weightRule = mailService.queryForWeightRule();

		if (weightRule == null) {

			return SET_PRICE;
		}
		String weightRuleContent = weightRule.getWeightrulecontent();

		// 此处要考虑到重量区间未添加的情况。
		String[] weight = weightRuleContent.split(";");
		weightRuleId = weightRule.getWeightruleid();

		int len = weight.length - 1;

		for (int i = 0; i < len; i++) {
			String[] tmp = weight[i].split(",");
			weightRuleList.add(i, tmp[0] + "-" + tmp[1] + "公斤内价格");

		}

		String tmpStr[] = weight[len - 1].split(",");
		String xuweight = tmpStr[1];
		weightRuleList.add(len, "续重价格(>" + xuweight + "公斤)");

		return CREATE_TRANSFORM_PRICE;
	}

	public String updateTransformPrice() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		price = request.getParameter("price");

		TransformPriceRule rule = new TransformPriceRule();
		rule.setTransformpriceruleid(transformPriceRule.getTransformpriceruleid());

		rule = mailService.queryForTransformPriceRuleList(rule)
				.get(0);
		
		rule.setCode(transformPriceRule.getCode());
		rule.setDestination(transformPriceRule.getDestination());
		rule.setDestinationcity(transformPriceRule.getDestinationcity());
		rule.setMtype(transformPriceRule.getMtype());
		rule.setPricelist(price);
		rule.setWeightruleid(Integer.parseInt("0"));
		rule.setTransformcategoryid(transformPriceRule.getTransformcategoryid());
			
		Date now = new Date();
		String day = DateUtil.formatYYYYMMDD(now);
		Date effdate = DateUtil.parse(day + " 00:00:00",
				DateUtil.DATE_TIME_FORMAT);

		rule.setEffdate(effdate);
		rule.setIsdeleted("n");

		mailService.updateTransformPriceRuleNew(rule);// 更新原来的rule，设置成为无效，重新插入一条新的记录

		this.addErrorMessage("hello", "中转费费更新成功！");
		rule = new TransformPriceRule();
		rule.setTransformpriceruleid(transformPriceRule.getTransformpriceruleid());

		transformPriceRule = mailService.queryForTransformPriceRuleList(rule)
				.get(0);
		price = transformPriceRule.getPricelist();
		

		TransformCategory c=new TransformCategory();
		c.setTransformcategoryid( transformPriceRule.getTransformcategoryid() );
		List<TransformCategory> listC=mailService.getTransformCategory(c);
		
		if(listC!=null&&listC.size()==1){
			
			transformCategory=listC.get(0);
		}
		return UPDATE_TRANSFOMR_PRICE;
	}

	public String deleteTransformPrice() {

		// 取出所有目的地的信息
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
	 	// 删除目的中转费
		TransformPriceRule r1= new TransformPriceRule();
		r1.setTransformpriceruleid(transformPriceRule.getTransformpriceruleid());
		mailService.deleteTransformPrice(r1);

		this.addErrorMessage("hello", "中转费删除成功");


		// 取出中转费方案的对应的重量方案。

		WeightRule w = new WeightRule();
		w.setIsdeleted("n");
		weightList = mailService.queryForWeightRuleList(w);

		// 列出所有的价格
		TransformPriceRule r = new TransformPriceRule();
        r.setTransformcategoryid(transformPriceRule.getTransformcategoryid());
		transformPriceRuleNewList = mailService
				.queryForTransformPriceRuleList(r);

		if (transformPriceRuleNewList == null
				|| transformPriceRuleNewList.size() == 0)
			return SET_TRANSFOMR_PRICE;

		for (int i = 0; i < transformPriceRuleNewList.size(); i++) {
			String weightprice = "";

			String pricelist = transformPriceRuleNewList.get(i).getPricelist();
			// 此处要考虑到重量区间未添加的情况。
			// price=中转费方案:首重:单价
			// price=中转费方案：最小结算重量：加成：重量分界点：第一段价格,第二段价格：补助费
			 // //price=中转费方案：首重:首重价格：续重,续重单价:超重费：补助费
			// 单价式
			String str[] = pricelist.split(":");
			if (str[0].equals("1")) {
				// 1 表示单价式
				weightprice = "单价式中转费价格方案：<br> 当重量在" + str[1] + "公斤以内时，中转费= 首重*单价；" + "<br> 当重量大于"
						+ str[1] + "公斤时，中转费= 重量*单价；其中单价为：" + str[2]
						+ "元/公斤；<br> 区域补贴：" + str[3] + "元/票";

			} else if (str[0].equals("2")){
				// 1 表示首重式

				String ss[] = str[2].split(",");
				weightprice = "首重式中转费价格方案：    <br>  当重量在" + str[1] + "公斤以内时，中转费=首重价格； <br> 当重量大于"
						+ str[1] + "公斤时，中转费=" + ss[0]
						+ "+ (重量-首重)*续重单价；其中首重价格 为：" + ss[0] + "元；续重单价为："
						+ ss[1] + "元/公斤；<br> 区域补贴：" + str[3] + "元/票";

			}else if (str[0].equals("3")){
				
				String ss[] = str[4].split(",");
				weightprice = "分段式中转费价格方案： <br>     当重量在" + str[1] + "公斤以内时，中转费=最小结算重量*第一段价格+第一段固定加成；<br>  当重量大于"
						+ str[1] + "公斤且小于等于重量分界点"+str[3]+"时，中转费=重量*第一段价格+第一段固定加成；"+"<br> 当重量大于"
						+ str[3] +"重量分界点时；中转费=重量分界点*第一段价格+第一段固定加成+(重量-重量分界点)*第二段价格；<br> 其中第一段价格 为：" + ss[0] + "元/公斤；第二段续重价格 为"+ss[1]+"元/公斤；第一段固定加成为"+str[2]+"元；<br> 区域补贴：" + str[5] + "元/票";
			}else  if (str[0].equals("4")){
				
				String ss[] = str[3].split(",");
				String we="";
				String p="";
				if(str[4].indexOf(",")!=-1){
					we=str[4].split(",")[0];
					p=str[4].split(",")[1];
					
				}else{
					we="1";
					p=str[4];
				}
					
				weightprice = "三段式中转费价格方案：<br>     当重量在" + str[1] + "公斤以内时，中转费=首重价格；<br>  当重量大于"
						+ str[1] + "公斤小于等于"+ss[0]+"公斤时，中转费=续重价格；当重量大于" +ss[0]+ "公斤时，中转费=续重价格+（取整重量-续重)*超重单价；<br> 其中首重价格 为：" +  str[2]+ "元；续重单价为："
						+ ss[1] + "元；每超重"+we+"公斤费用为"+p+"元";
				
			}else if (str[0].equals("5")){
				
				String ss[] = str[2].split(",");
				weightprice = "二段式取整价格方案： <br>    当重量在" + str[1] + "公斤以内时，中转费=首重价格；<br>  当重量大于"
						+ str[1] + "公斤时，中转费=" + ss[0]
						+ "+ (取整重量-首重)*续重单价；<br>  其中首重价格 为：" + ss[0] + "元；续重单价为："
						+ ss[1] + "元/公斤";
			}else  if (str[0].equals("6")){
				 //price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格：中转费方案：首重,首重价格,续重单价：补助费
				
				String ss1[] = str[1].split(",");
				
				if(str.length>=5){
					weightprice = "小件与超重中转费方案：<br>     当重量在" + ss1[0] + "-"+ss1[1]+"公斤以内时，中转费="+ss1[2]+"元； ";	
					
				}
				if(str.length>=6){
					String ss2[] = str[2].split(",");
					weightprice =weightprice+ "<br>  当重量在" + ss2[0] + "-"+ss2[1]+"公斤以内时，中转费="+ss2[2]+"元；";	
					
				}
				if(str.length>=7){
					
					String ss3[] = str[3].split(",");
					weightprice =weightprice+ "<br>  当重量在" + ss3[0] + "-"+ss3[1]+"公斤以内时，中转费="+ss3[2]+"元；";	
				}
				   if (str.length >= 8)
			        {
			          String[] ss3 = str[4].split(",");
			          weightprice = weightprice + "<br>  当重量在" + ss3[0] + "-" + ss3[1] + "公斤以内时，中转费=" + ss3[2] + "元；";
			        }
				 String ss5[] = str[str.length-2].split(",");
				if(str[str.length-3].equals("7")){
					weightprice=weightprice+"; <br> 单价式：中转费=单价*重量;其中 单价为"+ss5[1]+"元/公斤；派送补贴："+str[str.length-1] + "元/票";
				}
				if(str[str.length-3].equals("8")){
					weightprice=weightprice+"; <br> 首重式：中转费=首重价格+（重量-首重)*续重单价;其中首重价格为"+ss5[1]+"元；续重单价为"+ss5[2]+"元/公斤；派送补贴："+str[str.length-1] + "元/票";
				}		
				
			}else{
				
				///price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格:首重,首重二,价格： 首重价格:续重单价：补助费
				String ss1[] = str[1].split(",");
				
				if(str.length>=5){
					weightprice = "分段单价式中转费价格方案：  <br>    当重量在" + ss1[0] + "-"+ss1[1]+"公斤以内时，中转费="+ss1[2]+"元； ";	
					
				}
				if(str.length>=6){
					String ss2[] = str[2].split(",");
					weightprice =weightprice+ " <br> 当重量在" + ss2[0] + "-"+ss2[1]+"公斤以内时，中转费="+ss2[2]+"元；";	
					
				}
				if(str.length>=7){
					
					String ss3[] = str[3].split(",");
					weightprice =weightprice+ "<br>  当重量在" + ss3[0] + "-"+ss3[1]+"公斤以内时，中转费="+ss3[2]+"元；";	
				 }
				 if (str.length >= 8)
			        {
			          String[] ss3 = str[4].split(",");
			          weightprice = weightprice + "<br>  当重量在" + ss3[0] + "-" + ss3[1] + "公斤以内时，中转费=" + ss3[2] + "元；";
			        }
				
		 		
				// 1 表示单价式
				 weightprice = weightprice+"  <br> 当重量在2公斤以内时，中转费= 重量*"+str[str.length-3] + "元/公斤;<br>当重量大于2公斤时，中转费= 重量*"+str[str.length-2]+ "元/公斤；<br> 区域补贴：" +str[str.length-1] + "元/票";
			
			}
			transformPriceRuleNewList.get(i).setWeightpricelist(weightprice);

		}
		
		TransformCategory c=new TransformCategory();
		c.setTransformcategoryid( transformPriceRule.getTransformcategoryid() );
		List<TransformCategory> listC=mailService.getTransformCategory(c);
		
		if(listC!=null&&listC.size()==1){
			
			transformCategory=listC.get(0);
		}
		
		return SET_TRANSFOMR_PRICE;

	}


	public String createTransformPrice() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		price = request.getParameter("price");

		// 查询是否已经定义该中转站的费用，code ,transformcategoryid
        
		TransformPriceRule rule = new TransformPriceRule();
		rule.setCode(transformPriceRule.getCode());
		rule.setMtype(transformPriceRule.getMtype());
		rule.setTransformcategoryid(transformPriceRule.getTransformcategoryid());

		if (mailService.queryForTransformPriceRuleList(rule).size() == 1) {
			this.addErrorMessage("hello", "目的地中转费已经定义！");

		} else {

			rule = new TransformPriceRule();
			rule.setCode(transformPriceRule.getCode());
			rule.setDestination(transformPriceRule.getDestination());
			rule.setDestinationcity(transformPriceRule.getDestinationcity());
			rule.setTransformcategoryid(transformPriceRule.getTransformcategoryid());
			rule.setPricelist(price);
			rule.setMtype(transformPriceRule.getMtype());

			Date now = new Date();
			String day = DateUtil.formatYYYYMMDD(now);
			Date effdate = DateUtil.parse(day + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			rule.setEffdate(effdate);
			rule.setIsdeleted("n");
			mailService.createNewTransformPriceRule(rule);
			this.addErrorMessage("hello", "目的地中转费创建成功！");
		}

		// 列出所有的价格

		TransformPriceRule r = new TransformPriceRule();
		r.setTransformcategoryid(transformPriceRule.getTransformcategoryid());
		  r.setIsdeleted("n");
		transformPriceRuleNewList = mailService
				.queryForTransformPriceRuleList(r);

		if (transformPriceRuleNewList == null
				|| transformPriceRuleNewList.size() == 0)
			return SET_TRANSFOMR_PRICE;

		for (int i = 0; i < transformPriceRuleNewList.size(); i++) {
			String weightprice = "";

			String pricelist = transformPriceRuleNewList.get(i).getPricelist();
			// 此处要考虑到重量区间未添加的情况。
			// price=中转费方案:首重:单价：补助费
			// price=中转费方案：首重：首重价格,续重单价：补助费
			 // //price=中转费方案：首重:首重价格：续重,续重单价:超重费：补助费
			  //price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格：中转费方案：首重,首重价格,续重单价：补助费
		    //price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格:中转费方案：首重,单价：补助费
			// 单价式
			String str[] = pricelist.split(":");
			if (str[0].equals("1")) {
				// 1 表示单价式
				weightprice = "单价式中转费价格方案：    <br> 当重量在" + str[1] + "公斤以内时，中转费= 首重*单价；" + "当重量大于"
						+ str[1] + "公斤时，中转费= 重量*单价；其中单价为：" + str[2]
						+ "元/公斤；<br> 区域补贴：" + str[3] + "元/票";

			} else if (str[0].equals("2")){
				// 1 表示首重式

				String ss[] = str[2].split(",");
				weightprice = "首重式中转费价格方案：    当重量在" + str[1] + "公斤以内时，中转费=首重价格； <br> 当重量大于"
						+ str[1] + "公斤时，中转费=" + ss[0]
						+ "+ (重量-首重)*续重单价；其中首重价格 为：" + ss[0] + "元；续重单价为："
						+ ss[1] + "元/公斤；<br> 区域补贴：" + str[3] + "元/票";

			}else if (str[0].equals("3")){
				
				String ss[] = str[4].split(",");
				weightprice = "分段式中转费价格方案：   <br>  当重量在" + str[1] + "公斤以内时，中转费=最小结算重量*第一段价格+第一段固定加成；<br> 当重量大于"
						+ str[1] + "公斤且小于等于重量分界点"+str[3]+"时，中转费=重量*第一段价格+第一段固定加成；"+"<br> 当重量大于"
						+ str[3] +"重量分界点时；中转费=重量分界点*第一段价格+第一段固定加成+(重量-重量分界点)*第二段价格；<br> 其中第一段价格 为：" + ss[0] + "元/公斤；第二段续重价格 为"+ss[1]+"元/公斤；第一段固定加成为"+str[2]+"元；区域补贴：" + str[5] + "元/票";
			}else if (str[0].equals("4")){
				
				String ss[] = str[3].split(",");
				String w="";
				String p="";
				if(str[4].indexOf(",")!=-1){
					w=str[4].split(",")[0];
					p=str[4].split(",")[1];
					
				}else{
					w="1";
					p=str[4];
				}
					
				weightprice = "三段式中转费价格方案：<br>     当重量在" + str[1] + "公斤以内时，中转费=首重价格； 当重量大于"
						+ str[1] + "公斤小于等于"+ss[0]+"公斤时，中转费=续重价格；<br> 当重量大于" +ss[0]+ "公斤时，中转费=续重价格+（取整重量-续重)*超重单价；<br> 其中首重价格 为：" +  str[2]+ "元；续重单价为："
						+ ss[1] + "元；每超重"+w+"公斤费用为"+p+"元";
				
			}else if (str[0].equals("5")){
				
				String ss[] = str[2].split(",");
				weightprice = "二段式取整价格方案： <br>    当重量在" + str[1] + "公斤以内时，中转费=首重价格； 当重量大于"
						+ str[1] + "公斤时，中转费=" + ss[0]
						+ "+ (取整重量-首重)*续重单价； <br> 其中首重价格 为：" + ss[0] + "元；续重单价为："
						+ ss[1] + "元/公斤";
			}else if (str[0].equals("6")){
				 //price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格：中转费方案：首重,首重价格,续重单价：补助费
				
				String ss1[] = str[1].split(",");
				
				if(str.length>=5){
					weightprice = "小件与超重中转费方案：   <br>  当重量在" + ss1[0] + "-"+ss1[1]+"公斤以内时，中转费="+ss1[2]+"元； ";	
					
				}
				if(str.length>=6){
					String ss2[] = str[2].split(",");
					weightprice =weightprice+ " <br> 当重量在" + ss2[0] + "-"+ss2[1]+"公斤以内时，中转费="+ss2[2]+"元；";	
					
				}
				if(str.length>=7){
					
					String ss3[] = str[3].split(",");
					weightprice =weightprice+ "<br>  当重量在" + ss3[0] + "-"+ss3[1]+"公斤以内时，中转费="+ss3[2]+"元；";	
				}
				   if (str.length >= 8)
			        {
			          String[] ss3 = str[4].split(",");
			          weightprice = weightprice + "<br>  当重量在" + ss3[0] + "-" + ss3[1] + "公斤以内时，中转费=" + ss3[2] + "元；";
			        }
				 String ss5[] = str[str.length-2].split(",");
				if(str[str.length-3].equals("7")){
					weightprice=weightprice+"; <br> 单价式：中转费=单价*重量;其中 单价为"+ss5[1]+"元/公斤；区域补贴："+str[str.length-1] + "元/票";
				}
				if(str[str.length-3].equals("8")){
					weightprice=weightprice+";<br>  首重式：中转费=首重价格+（重量-首重)*续重单价;其中首重价格为"+ss5[1]+"元；续重单价为"+ss5[2]+"元/公斤；区域补贴："+str[str.length-1] + "元/票";
				}		
				
			}else{
				
				///price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格:首重,首重二,价格： 首重价格:续重单价：补助费
				String ss1[] = str[1].split(",");
				
				if(str.length>=5){
					weightprice = "分段单价式中转费价格方案：  <br>    当重量在" + ss1[0] + "-"+ss1[1]+"公斤以内时，中转费="+ss1[2]+"元； ";	
					
				}
				if(str.length>=6){
					String ss2[] = str[2].split(",");
					weightprice =weightprice+ " <br> 当重量在" + ss2[0] + "-"+ss2[1]+"公斤以内时，中转费="+ss2[2]+"元；";	
					
				}
				if(str.length>=7){
					
					String ss3[] = str[3].split(",");
					weightprice =weightprice+ "<br>  当重量在" + ss3[0] + "-"+ss3[1]+"公斤以内时，中转费="+ss3[2]+"元；";	
				 }
				 if (str.length >= 8)
			        {
			          String[] ss3 = str[4].split(",");
			          weightprice = weightprice + "<br>  当重量在" + ss3[0] + "-" + ss3[1] + "公斤以内时，中转费=" + ss3[2] + "元；";
			        }
				
		 		
				// 1 表示单价式
				 weightprice = weightprice+"  <br> 当重量在2公斤以内时，中转费= 重量*"+str[str.length-3] + "元/公斤;<br>当重量大于2公斤时，中转费= 重量*"+str[str.length-2]+ "元/公斤；<br> 区域补贴：" +str[str.length-1] + "元/票";
			
			}
		 

			transformPriceRuleNewList.get(i).setWeightpricelist(weightprice);

		}
		TransformCategory c=new TransformCategory();
		c.setTransformcategoryid( transformPriceRule.getTransformcategoryid() );
		List<TransformCategory> listC=mailService.getTransformCategory(c);
		
		if(listC!=null&&listC.size()==1){
			
			transformCategory=listC.get(0);
		}
		
		return SET_TRANSFOMR_PRICE;

	}
	
	public String showUpdateWeight() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str = request.getParameter("weightruleid");

		WeightRule w = new WeightRule();
		w.setWeightruleid(Integer.parseInt(str));
		w.setIsdeleted("n");
		WeightRule rule = mailService.queryForWeightRuleList(w).get(0);

		weightInfo = rule.getWeightrulecontent();

		weightrulename = rule.getWeightrulename();

		weightruleid = rule.getWeightruleid();

		return UPDATE_WEIGHT;
	}

	// 未开发完成
	public String updateWeightRule() throws ParseException,
			UnsupportedEncodingException {

		// /
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str = request.getParameter("weightInfo");
		weightrulename = new String(request.getParameter("weightrulename")
				.getBytes("ISO8859-1"), "utf-8");
		if (str != null && !str.equals("") && str.indexOf(";") != -1) {
			WeightRule rule = new WeightRule();
			rule.setWeightrulecontent(str);
			rule.setWeightrulename(weightrulename);
			Date now = new Date();
			String day = DateUtil.formatYYYYMMDD(now);
			Date effdate = DateUtil.parse(day + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
			rule.setEffdate(effdate);
			rule.setIsdeleted("n");
			// /注意：要把更新当前的规则为inactive 和插入一条新重量规则的放在同一个事物中。
			// /判断content和price是否发生变化

			// 查找所有的weightrule
			// 如果为null或者size为0， 则建立新的weightrule;
			// 如果不为空，则判定是否为本身 ，如果为本身,则更新，如果为为其他的id，则存在

			List<WeightRule> weightrule = mailService
					.queryForWeightRuleList(new WeightRule());

			boolean flag = false;

			if (weightrule != null) {

				for (int i = 0; i < weightrule.size(); i++) {
					WeightRule r = weightrule.get(i);
					if (r.getWeightrulecontent().equals(str)) {

						if (r.getWeightruleid() != weightruleid) {
							this.addErrorMessage("hello", "重量规则已经存在！");
							flag = true;
							break;
						}
					}

				}

				if (flag != true) {
					rule.setWeightruleid(weightruleid);
					mailService.updateWeightRule(rule);
					this.addErrorMessage("hello", "重量规则更新成功！");
				}

			}
		} else {
			this.addErrorMessage("hello", "重量规则不能为空！");
		}

		WeightRule w = new WeightRule();
		w.setWeightruleid(weightruleid);
		w.setIsdeleted("n");
		WeightRule rule = mailService.queryForWeightRuleList(w).get(0);
		weightInfo = rule.getWeightrulecontent();
		return UPDATE_WEIGHT;
	}

	public String deleteWeightRule() {

		WeightRule rule = new WeightRule();
		rule.setIsdeleted("n");
		rule.setWeightruleid(weightRuleId);
		rule = mailService.queryForWeightRuleList(rule).get(0);
		rule.setIsdeleted("y");
		mailService.updateWeightRule(rule);
		this.addErrorMessage("hello", "删除成功！");

		WeightRule w = new WeightRule();
		w.setIsdeleted("n");
		weightList = mailService.queryForWeightRuleList(w);

		for (int i = 0; i < weightList.size(); i++) {

			rule = weightList.get(i);

			String weightRuleContent = rule.getWeightrulecontent();
			String tmp1 = "";
			if (!weightRuleContent.equals("")) {
				// 此处要考虑到重量区间未添加的情况。
				String[] weight = weightRuleContent.split(";");
				weightRuleId = rule.getWeightruleid();

				int len = weight.length - 1;
				tmp1 = "首重：";

				for (int j = 0; j < len; j++) {
					String[] tmp = weight[j].split(",");
					tmp1 = tmp1 + tmp[0] + "-" + tmp[1] + "公斤 ;    ";

				}

				tmp1 = tmp1 + " 取整单位： " + weight[len] + "公斤  ";
			} else {

				tmp1 = "单价式";
			}
			weightList.get(i).setWeightrulecontent(tmp1);

		}

		return SET_WEIGHT;
	}

	public String showSetWeight() throws IOException {

		WeightRule w = new WeightRule();
		w.setIsdeleted("n");
		weightList = mailService.queryForWeightRuleList(w);

		for (int i = 0; i < weightList.size(); i++) {

			WeightRule rule = weightList.get(i);

			String weightRuleContent = rule.getWeightrulecontent();
			String tmp1 = "";
			if (!weightRuleContent.equals("")) {
				// 此处要考虑到重量区间未添加的情况。
				String[] weight = weightRuleContent.split(";");
				weightRuleId = rule.getWeightruleid();

				tmp1 = "首重：" + weight[0] + "公斤 ;    " + " 取整单位： " + weight[1]
						+ "公斤  ";

			} else {

				tmp1 = "单价式";
			}
			weightList.get(i).setWeightrulecontent(tmp1);

		}

		return SET_WEIGHT;

	}

	public String setWeight() throws IOException, ParseException {

		// // 获得
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str = request.getParameter("weightInfo");
		String name = request.getParameter("weightrulename");

		if (str != null && !str.equals("") && str.indexOf(";") != -1) {
			WeightRule rule = new WeightRule();
			rule.setWeightrulecontent(str);

			rule.setWeightrulename(name);
			Date now = new Date();
			String day = DateUtil.formatYYYYMMDD(now);
			Date effdate = DateUtil.parse(day + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
			rule.setEffdate(effdate);
			rule.setIsdeleted("n");
			// /注意：要把更新当前的规则为inactive 和插入一条新重量规则的放在同一个事物中。
			// /判断content和price是否发生变化

			boolean flag = false;
			List<WeightRule> weightrule = mailService
					.queryForWeightRuleList(new WeightRule());

			if (weightrule != null) {

				for (int i = 0; i < weightrule.size(); i++) {
					WeightRule r = weightrule.get(i);
					if (r.getWeightrulecontent().equals(str)) {
						rule.setWeightruleid(r.getWeightruleid());
						mailService.updateWeightRule(rule);
						this.addErrorMessage("hello", "重量规则创建成功！");
						flag = true;
						break;
					}
				}
				if (flag == false) {
					mailService.createNewWeightRule(rule);
					this.addErrorMessage("hello", "重量规则创建成功！");
				}

			}

		} else {
			// 如果weightInfo 为空，则表示，仅输入了一个方案名
			// 这种请况下，为单价式
			boolean flag = false;
			WeightRule w = new WeightRule();
			w.setIsdeleted("n");
			weightList = mailService.queryForWeightRuleList(w);
			for (int i = 0; i < weightList.size(); i++) {
				WeightRule r = weightList.get(i);
				if (r.getWeightrulecontent().equals("")) {
					flag = true;
					this.addErrorMessage("hello", "单价式重量规则已经存在！");
					break;
				}
			}

			if (flag == false) {
				WeightRule rule = new WeightRule();
				rule.setWeightrulecontent("");

				rule.setWeightrulename(name);
				Date now = new Date();
				String day = DateUtil.formatYYYYMMDD(now);
				Date effdate = DateUtil.parse(day + " 00:00:00",
						DateUtil.DATE_TIME_FORMAT);
				rule.setEffdate(effdate);
				rule.setIsdeleted("n");

				mailService.createNewWeightRule(rule);
				this.addErrorMessage("hello", "重量规则创建成功！");
			}

		}
		WeightRule w = new WeightRule();
		w.setIsdeleted("n");
		weightList = mailService.queryForWeightRuleList(w);

		for (int i = 0; i < weightList.size(); i++) {
			WeightRule rule = weightList.get(i);
			String weightRuleContent = rule.getWeightrulecontent();
			String tmp1 = "";
			if (!weightRuleContent.equals("")) {
				// 此处要考虑到重量区间未添加的情况。
				// 0.5;0.5
				String[] weight = weightRuleContent.split(";");
				weightRuleId = rule.getWeightruleid();

				tmp1 = "首重：" + weight[0] + "公斤 ;    " + " 取整单位： " + weight[1]
						+ "公斤  ";

			} else {
				tmp1 = "单价式";
			}
			weightList.get(i).setWeightrulecontent(tmp1);

		}

		weightrulename = name;
		weightInfo = "";
		return SET_WEIGHT;

	}

	public String setWeightPrice() throws IOException, SQLException,
			ParseException {

		// 获得
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String strshouzhong = request.getParameter("weightPriceInfo");
		String name = new String(request.getParameter("weightpricename")
				.getBytes("ISO8859-1"), "utf-8");

		String strprice = request.getParameter("price");
		String strscanfei = request.getParameter("scanfei");
		String strchujianfei = request.getParameter("chujianfei");
		double price = 0;
		double scanfei = 0;
		double chujianfei = 0;
		double shouzhong = 0;

		try {
			shouzhong = Double.parseDouble(strshouzhong);

			price = Double.parseDouble(strprice);
			scanfei = Double.parseDouble(strscanfei);
			chujianfei = Double.parseDouble(strchujianfei);

			WeightPriceRule rule = new WeightPriceRule();
			rule.setFirstweight(shouzhong);
			rule.setPrice(price);
			rule.setScanfei(scanfei);
			rule.setChujianfei(chujianfei);
			Date now = new Date();
			String day = DateUtil.formatYYYYMMDD(now);
			Date effdate = DateUtil.parse(day + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			rule.setEffdate(effdate);
			rule.setIsdeleted("n");
			rule.setWeightpricename(name);
			rule.setIsactive("n");

			// /判断content和price是否发生变化

			mailService.createNewWeightPriceRule(rule);

		} catch (NumberFormatException e) {

			this.addErrorMessage("hello", "请按数字格式正确填写");
		}

		WeightPriceRule sss = new WeightPriceRule();
		sss.setIsdeleted("n");
		weightPriceList = mailService.queryForWeightPriceRuleList(sss);

		for (int i = 0; i < weightPriceList.size(); i++) {

			WeightPriceRule rule = weightPriceList.get(i);
			double price1= rule.getPrice()*rule.getFirstweight();
			BigDecimal bb = new BigDecimal(price1);
			// 保留2位小数
			price1 = bb.setScale(3, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			String tmp1 = "当重量在" + rule.getFirstweight() + "公斤以内，计重费="+
					+price1+ "元;  " + "当重量大于"
					+ rule.getFirstweight() + "公斤时，计重费=(重量-首重("
					+ rule.getFirstweight() + "公斤))*单价,其中单价为" +new String( rule.getPrice()+"")
					+ "元/公斤)";
			weightPriceList.get(i).setWeightpricerulecontent(tmp1);

		}
		weightPriceInfo = "";
		return SET_WEIGHT_PRICE;

	}

	public String updateWeightPrice() throws UnsupportedEncodingException,
			ParseException, SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		String str = request.getParameter("weightPriceInfo");
		String name = new String(request.getParameter("weightpricename")
				.getBytes("ISO8859-1"), "utf-8");
		String strprice = request.getParameter("price");
		String strscanfei = request.getParameter("scanfei");
		String strchujianfei = request.getParameter("chujianfei");
		double price2 = 0;
		double scanfei2 = 0;
		double chujianfei2 = 0;
		double shouzhong = 0;

		try {

			price2 = Double.parseDouble(strprice);
			scanfei2 = Double.parseDouble(strscanfei);
			chujianfei2 = Double.parseDouble(strchujianfei);
			shouzhong = Double.parseDouble(str);

			WeightPriceRule rule = new WeightPriceRule();
			rule.setFirstweight(shouzhong);
			rule.setPrice(price2);
			rule.setScanfei(scanfei2);
			rule.setChujianfei(chujianfei2);
			Date now = new Date();
			String day = DateUtil.formatYYYYMMDD(now);
			Date effdate = DateUtil.parse(day + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			rule.setEffdate(effdate);
			rule.setIsdeleted("n");
			rule.setWeightpricename(name);
			rule.setIsactive("n");
			// /判断content和price是否发生变化

			List<WeightPriceRule> pricerule = mailService
					.queryForWeightPriceRuleList(new WeightPriceRule());
			// 当pricerule为null时，说明在数据库中还没有数据，可以直接将数据插入到数据库中
			// 当pricerule不为空时，则要比较content和price的值是否有改变,如果有一项不同则更新加插入

			rule.setWeightpriceruleid(weightruleid);
			mailService.updateWeightPriceRule(rule);
			this.addErrorMessage("hello", "计重规则更新成功！");

		} catch (NumberFormatException e) {

			this.addErrorMessage("hello", "请按数字格式正确填写");
		}
		// //

		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		p.setIsactive("n");
		p.setWeightpriceruleid(weightruleid);

		p = mailService.queryForWeightPriceRuleList(p).get(0);

		weightPriceInfo = p.getFirstweight() + "";
		weightpricename = p.getWeightpricename();
		price = "" + p.getPrice();
		scanfei = "" + p.getScanfei();
		chujianfei = "" + p.getChujianfei();
		weightruleid = p.getWeightpriceruleid();
		return UPDATE_WEIGHT_PRICE;
	}

	public String showUpdateWeightPrice() throws SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str = request.getParameter("weightpriceid");

		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		p.setWeightpriceruleid(Integer.parseInt(str));

		p = mailService.queryForWeightPriceRuleList(p).get(0);

		weightPriceInfo = p.getFirstweight() + "";
		weightpricename = p.getWeightpricename();
		weightruleid = p.getWeightpriceruleid();
		price = "" + p.getPrice();
		scanfei = "" + p.getScanfei();
		chujianfei = "" + p.getChujianfei();

		return UPDATE_WEIGHT_PRICE;
	}

	public String activateWeightPrice() throws IOException, SQLException {

		//

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str = request.getParameter("weightpriceid");
		String status = request.getParameter("weightrulename");// weightrulename
		// 是用于记录启用的状态

		if (status.equals("y")) {// 表示从启用转为禁用
			WeightPriceRule p = new WeightPriceRule();
			p.setIsdeleted("n");
			p.setIsactive("y");
			p.setWeightpriceruleid(Integer.parseInt(str));

			p = mailService.queryForWeightPriceRuleList(p).get(0);

			p.setIsactive("n");
			mailService.updateWeightPriceRule(p);
			this.addErrorMessage("hello", "该计重规则禁用成功！");
		} else {// 从禁用转为启用，则需要判断当前是否有启用的，如果有，则将其更新为禁用，再将本身更新为启用。

			WeightPriceRule p = new WeightPriceRule();
			p.setIsdeleted("n");
			p.setIsactive("y");
			List<WeightPriceRule> t2 = mailService
					.queryForWeightPriceRuleList(p);

			if (t2.size() > 0) {

				for (int i = 0; i < t2.size(); i++) {
					WeightPriceRule ss = t2.get(i);
					ss.setIsactive("n");
					mailService.updateWeightPriceRule(ss);
				}

			}

			WeightPriceRule r = new WeightPriceRule();
			r.setIsdeleted("n");
			r.setIsactive("n");
			r.setWeightpriceruleid(Integer.parseInt(str));

			r = mailService.queryForWeightPriceRuleList(r).get(0);
			r.setIsactive("y");
			mailService.updateWeightPriceRule(r);
			this.addErrorMessage("hello", "该计重规则启用成功！");

		}

		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		weightPriceList = mailService.queryForWeightPriceRuleList(p);

		for (int i = 0; i < weightPriceList.size(); i++) {

			WeightPriceRule rule = weightPriceList.get(i);
			String tmp1 = "当重量在" + rule.getFirstweight() + "公斤以内，计重费= "
					+ rule.getPrice()*rule.getFirstweight() + "元;  " + "当重量大于"
					+ rule.getFirstweight() + "公斤时，计重费=(重量-首重("
					+ rule.getFirstweight() + "公斤))*单价,其中单价为" + rule.getPrice()
					+ "元/公斤";
			weightPriceList.get(i).setWeightpricerulecontent(tmp1);

		}
		return SET_WEIGHT_PRICE;

	}

	public String showSetWeightPrice() throws IOException, SQLException {

		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		weightPriceList = mailService.queryForWeightPriceRuleList(p);

		for (int i = 0; i < weightPriceList.size(); i++) {
			WeightPriceRule rule = weightPriceList.get(i);
			double price= rule.getPrice()*rule.getFirstweight();
			BigDecimal bb = new BigDecimal(price);
			// 保留2位小数
			price = bb.setScale(4, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			String tmp1 = "当重量在" + rule.getFirstweight() + "公斤以内，计重费= "
					+price + "元;  " + "当重量大于"
					+ rule.getFirstweight() + "公斤时，计重费=(重量-首重("
					+ rule.getFirstweight() + "公斤))*单价,其中单价为" + new String(rule.getPrice()+"")
					+ "元/公斤";
			weightPriceList.get(i).setWeightpricerulecontent(tmp1);

		}
		return SET_WEIGHT_PRICE;

	}

	public String deleteWeightPriceRule() throws SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str = request.getParameter("weightpriceid");

		TransformPriceRule r = new TransformPriceRule();

		r.setWeightpriceruleid(Integer.parseInt(str));

		WeightPriceRule rule = new WeightPriceRule();
		rule.setIsdeleted("n");
		rule.setWeightpriceruleid(Integer.parseInt(str));
		rule = mailService.queryForWeightPriceRuleList(rule).get(0);
		rule.setIsdeleted("y");
		rule.setIsactive("n");
		mailService.updateWeightPriceRule(rule);
		this.addErrorMessage("hello", "计重规则删除成功！");

		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		weightPriceList = mailService.queryForWeightPriceRuleList(p);

		for (int i = 0; i < weightPriceList.size(); i++) {
		    rule = weightPriceList.get(i);
			double price= rule.getPrice()*rule.getFirstweight();
			BigDecimal bb = new BigDecimal(price);
			// 保留2位小数
			price = bb.setScale(4, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			String tmp1 = "当重量在" + rule.getFirstweight() + "公斤以内，计重费="+
					+price+ "元;  " + "当重量大于"
					+ rule.getFirstweight() + "公斤时，计重费=(重量-首重("
					+ rule.getFirstweight() + "公斤))*单价,其中单价为" +new String( rule.getPrice()+"")
					+ "元/公斤)";
			weightPriceList.get(i).setWeightpricerulecontent(tmp1);

		}
		return SET_WEIGHT_PRICE;
	}

	public String showReceiverMailMoney() {
		//		

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		return RECEIVER_MAIL_MONEY;
	}

	public String showManagerMailDetail() throws UnsupportedEncodingException {

		// 展示时一部分， 下载的快件是全部
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str = request.getParameter("managerdd");

		if (str != null) {
			str = new String(str.getBytes("ISO8859-1"), "utf-8");
		}
		String pageNoStr = request.getParameter("pageNo");

		importDate = request.getParameter("importdate");

		int start = 0;
		int totalRow = 0;
		int pageN = 1;
		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		// 获取所有的费用新信息
		String bsndate = importDate + " " + "12:00:00";
		String starttime = importDate + " " + "12:00:00";
		String endtime = importDate + " " + "12:00:00";
		
		  int mon=Integer.parseInt(importDate.substring(5, 7));
		Date dates = null;
		try {
			dates = DateUtil.parse(bsndate, DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageParameter p = new PageParameter();
		p.setBsndate(dates);
		p.setManager(str);
		statSmyList = mailService.queryForStatSMY(p);
		if (statSmyList.size() == 1)
			statSmy = statSmyList.get(0);

		// 获取一页 的信息
		if (statSmy != null)
			totalRow = statSmy.getWholenum();
		p = new PageParameter();
		start = (pageN - 1) * FileProcAction.pageSize;
		p.setStart(start);
		p.setSize(FileProcAction.pageSize);
		p.setEndtime(endtime);
		p.setStarttime(starttime);
		p.setState("normal");
		p.setManager(str);
        p.setMon(mon);
		statReportList = mailService.queryForStatReportPage(p);

		pageBean = new PageBean(FileProcAction.pageSize, pageN, totalRow);

		return MANAGER_MAIL_DETAIL;
	}

	public String showReceiverManagement() throws SQLException {
		//		

		TransformCategory c=new TransformCategory();
	    transformCategoryList=mailService.getTransformCategory(c);
	    
		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		weightPriceList = mailService.queryForWeightPriceRuleList(p);

	    
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		managerInfoList = mailService.queryForManagerInfoNew(m);
		
		
		m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		pmanagerInfoList = mailService.queryForManagerInfoNew(m);
	
	 
		return RECEIVER_MANAGEMENT;
	}

	public String showEmployeePage() throws SQLException {
		 
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("employee");
		managerInfoList = mailService.queryForManagerInfoNew(m);
	 
		importDate=DateUtil.formatYYYYMMDD(new Date());
		
		return "showemployeepage";
	}

	
	
	public String showManagerMailInfo() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		importDate = request.getParameter("importdate");

		String bsndate = importDate + " " + "12:00:00";

		Date dates = DateUtil.parse(bsndate, DateUtil.DATE_TIME_FORMAT);
		PageParameter p = new PageParameter();
		p.setBsndate(dates);
		statSmyList = mailService.queryForStatSMY(p);

		return MANAGER_MAIL_INFO;
	}
	
	public String showUpdateEmployeePage() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str=null;
		try {
			str = new String(request.getParameter("managerid").getBytes(
			"ISO8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 ManagerInfo ms = new ManagerInfo();
		 ms.setIsdeleted("n");
		 ms.setRoles("employee");
		 ms.setManagerid(str);
		 managerInfoList = mailService.queryForManagerInfoNew( ms);
		 
		 if(managerInfoList!=null&&managerInfoList.size()==1)
			 manager=managerInfoList.get(0);

		return "showupdateemployeepage";
	}

	public String createEmployee() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		ManagerInfo m = new ManagerInfo();
 	 
		String managerstr =manager.getManagerid();
		
		m.setManagerid(managerstr);
	    m.setRoles("employee");
		if (mailService.queryForManagerInfoNew(m).size() > 0) {
				this.addErrorMessage("hello", "员工已经存在！");
 
		} else {
			manager.setRoles("employee");
			manager.setManagername(manager.getManagerid());
			mailService.createManager(manager);
    		this.addErrorMessage("hello", "员工创建成功！");

		}


		 ManagerInfo ms = new ManagerInfo();
		 ms.setIsdeleted("n");
		 ms.setRoles("employee");
		 managerInfoList = mailService.queryForManagerInfoNew( ms);
		
		importDate=DateUtil.formatYYYYMMDD(new Date());
		return "showemployeepage";
	}
	
	
	public String deleteEmployee() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		 
		String str=null;
		try {
			str = new String(request.getParameter("managerid").getBytes(
			"ISO8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		manager = new ManagerInfo();
	 	manager.setManagerid(str);
	 	manager.setRoles("employee");
		mailService.deleteManager(manager);
		
		 ManagerInfo ms = new ManagerInfo();
		 ms.setIsdeleted("n");
		 ms.setRoles("employee");
		 managerInfoList = mailService.queryForManagerInfoNew( ms);
		
		importDate=DateUtil.formatYYYYMMDD(new Date());
	 
		this.addErrorMessage("hello", "员工删除成功");
		return "showemployeepage";
	}
	
	
	public String updateEmployee() {

		if(manager==null){
	 		this.addErrorMessage("hello", "员工不存在！");
			return "showupdateemployeepage";
		}
	    String managerid = manager.getManagerid();
		 
		if(managerid==null||managerid.equals("")){
			this.addErrorMessage("hello", "员工不存在！");
			return "showupdateemployeepage";
		}
		manager.setIsdeleted("n");
		manager.setManagerid(managerid);
		manager.setManagername(managerid);
		manager.setRoles("employee");
		mailService.updateManager(manager);
		
		 ManagerInfo ms = new ManagerInfo();
		 ms.setIsdeleted("n");
		 ms.setRoles("employee");
		 managerInfoList = mailService.queryForManagerInfoNew( ms);
		
		importDate=DateUtil.formatYYYYMMDD(new Date());
	 
		this.addErrorMessage("hello", "员工更新成功");
		
		return "showupdateemployeepage";
	}


	public String showCreateReceiver() {

		return CREATE_RECEIVER;
	}

	public String createManager() throws SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		ManagerInfo m = new ManagerInfo();
  
		String managerstr =manager.getManagerid();
		
		m.setManagerid(managerstr);
	 
		if (mailService.queryForManagerInfoNew(m).size() > 0) {
				this.addErrorMessage("hello", "揽件人编号已经存在！");
 
		} else {
			manager.setRoles("statuser");
			mailService.createManager(manager);
 	 
		 		
			this.addErrorMessage("hello", "揽件人创建成功！");

		}
		
		
		TransformCategory c=new TransformCategory();
	    transformCategoryList=mailService.getTransformCategory(c);
	    
		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		weightPriceList = mailService.queryForWeightPriceRuleList(p);

	    
		 m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		managerInfoList = mailService.queryForManagerInfoNew(m);
		
		
		m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		pmanagerInfoList = mailService.queryForManagerInfoNew(m);
	

		return RECEIVER_MANAGEMENT;
	}

	public String showManagerDetail() throws SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String str=null;
		try {
			str = new String(request.getParameter("managerid").getBytes(
			"ISO8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		   
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setManagerid(str);
		managerInfoList = mailService.queryForManagerInfoNew(m);
		
 		if(managerInfoList!=null&&managerInfoList.size()>0){
 			manager=managerInfoList.get(0);
 		}
	 
		TransformCategory c=new TransformCategory();
	    transformCategoryList=mailService.getTransformCategory(c);
		 
		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		weightPriceList = mailService.queryForWeightPriceRuleList(p);
		
		
		m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		pmanagerInfoList = mailService.queryForManagerInfoNew(m);
	
		return MANAGER_DETAIL;
	}

	public String updateManager() {
 
		if(manager==null){
	 		this.addErrorMessage("hello", "揽件人不存在！");
			return MANAGER_DETAIL;
		}
	    String managerid = manager.getManagerid();
		 
		if(managerid==null||managerid.equals("")){
			this.addErrorMessage("hello", "揽件人不存在！");
			return MANAGER_DETAIL;
		}
		manager.setIsdeleted("n");
		manager.setManagerid(managerid);
		mailService.updateManager(manager);
		
		
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setManagerid(managerid);
		managerInfoList = mailService.queryForManagerInfoNew(m);
		
 		if(managerInfoList!=null&&managerInfoList.size()>0){
 			manager=managerInfoList.get(0);
 		}
 		
		TransformCategory c=new TransformCategory();
	    transformCategoryList=mailService.getTransformCategory(c);
	 

		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		try {
			weightPriceList = mailService.queryForWeightPriceRuleList(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		pmanagerInfoList = mailService.queryForManagerInfoNew(m);
	
		this.addErrorMessage("hello", "揽件人信息保存成功");
		return MANAGER_DETAIL;
	}

	public String deleteManager() throws SQLException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		 
		String str=null;
		try {
			str = new String(request.getParameter("managerid").getBytes(
			"ISO8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		manager = new ManagerInfo();
	 	manager.setManagerid(str);
		mailService.deleteManager(manager);
		
		TransformCategory c=new TransformCategory();
	    transformCategoryList=mailService.getTransformCategory(c);
	    
		WeightPriceRule p = new WeightPriceRule();
		p.setIsdeleted("n");
		weightPriceList = mailService.queryForWeightPriceRuleList(p);

	    
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		managerInfoList = mailService.queryForManagerInfoNew(m);
		
		
		m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		pmanagerInfoList = mailService.queryForManagerInfoNew(m);
	
		this.addErrorMessage("hello", "揽件人信息删除成功");
		return RECEIVER_MANAGEMENT;
	}

	public String showSearchMail() {

		return SEARCH_MAIL;
	}

	public String searchMail() throws UnsupportedEncodingException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		// 在mailid=kkkkkk&flag=one or mailid=kkkkkk@kkkkk&flag=more
		mailidList = new String(request.getParameter("mailid").getBytes(
				"ISO8859-1"), "utf-8");

		String flag = new String(request.getParameter("flgg").getBytes(
				"ISO8859-1"), "utf-8");

		if (flag.equals("one")) {

			List<StatReport> ss=new ArrayList();
			for(int i=1;i<=12;i++){
			 
				ss = mailService.queryForMailByMailId(i,mailidList,null);
				
				if(ss.size()>0)
					break;
			
			}
			
			statReportList.addAll(ss);
			if (statReportList.size() == 0) {
				this.addErrorMessage("hello", "无法找到该邮件！");
			}

			return SEARCH_MAIL;

		}
		if (flag.equals("more")) {
			String[] para = mailidList.split("@");
			for (int i = 0; i < para.length; i++) {
				List<StatReport> ss=new ArrayList();
				for(int j=1;j<=12;j++){
					 
					ss = mailService.queryForMailByMailId(j,para[i],null);
					
					if(ss.size()>0)
						break;
				
				}
				statReportListMore.addAll(ss);
			}
			if (statReportListMore.size() < para.length) {
				int l = para.length - statReportListMore.size();
				this.addErrorMessage("hello2", l + "封快件无法找到！");

			}

		}
		return SEARCH_MAIL;

	}


	/*
	 * 
	 * 该函数实现的功能是：当输入批量的单号时，展示的是单号、蓝剑人、面单分配者。
	 * 
	 * */
	
	public String searchOnlineManagerInfo() throws ParserException, IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		// 在mailid=kkkkkk&flag=one or mailid=kkkkkk@kkkkk&flag=more
		
		String[] para = mailidList.split("@");
		
		String htmlstr="";
		int len=0;
		if(para.length>20)
			len=20;
		else len=para.length;
		for(int i=0;i<len;i++){
			
			htmlstr+=createOnlineManagerInfo(para[i].trim());
			
		}
		
		htmlstr=" <div class='imp_mod_inner'><div class='cc_para imp_intro_para'><div class='cc_link_sign_box2'> <table id='mytable'><tr><th width='30%'>快件单号</th>  <th width='15%'>揽件人</th>  <th width='55%'>面单分配</th></tr>"+htmlstr+"</table></div></div></div>";
		
	    this.createManagerInfoListFile(htmlstr);
	    
//在结束后，将其余的下下来。
//		if(para.length>20){
//		
//		List<String> mailL=new ArrayList();
//		for(int k=20;k<para.length;k++)
//			mailL.add(para[k]);
//		
//		DownloadManagerInfoThread t=new DownloadManagerInfoThread(mailL,UserHolder.getUser().getLoginid(),this.getEnv("tmpPath"));
//	 
//		 new Thread(t).start();
//			
//		}

	    String filename="maillist_"+UserHolder.getUser().getLoginid()+".vm";
	    if(para.length>20){
	    	try {
			BufferedWriter	output = new BufferedWriter(new FileWriter(this.getEnv("tmpPath")+filename));
				try {
					
					for(int ks=20;ks<para.length;ks++){
					  output.write(para[ks]);
					    output.newLine();
					}
					output.flush();
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	mailNum=para.length-20;
	    }
	    	    
		return SEARCH_ONLINE_MANAGERINFO;

	}



	/*
	 * 该函数实现的是：在输入批量单号后，显示批量的快件信息。
	 * 其中包括三部分：1. 快件单号分配
	 *                2. 快件收发信息
	 *                3. 快件详细跟踪记录
	 * 
	 ** */
	public String searchOnlineMailInfo() throws ParserException, IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		// 在mailid=kkkkkk&flag=one or mailid=kkkkkk@kkkkk&flag=more
		
		String[] para = mailidList.split("@");
		
		String htmlstr="";
		int len=0;
		if(para.length>20)
			len=20;
		else len=para.length;
		for(int i=0;i<len;i++){
			
			htmlstr+=createOnlineMailInfo(para[i].trim());
			
		}
		
	    this.createMailInfoListFile(htmlstr);
	    
//在结束后，将其余的下下来。
//		if(para.length>20){
//		
//		List<String> mailL=new ArrayList();
//		for(int k=20;k<para.length;k++)
//			mailL.add(para[k]);
//		
//		 DownloadMailInfoThread t=new DownloadMailInfoThread(mailL,UserHolder.getUser().getLoginid(),this.getEnv("tmpPath"));
//	 
//		 new Thread(t).start();
//			
//		}

	    String filename="maillist_"+UserHolder.getUser().getLoginid()+".vm";
	    if(para.length>20){
	    	try {
			BufferedWriter	output = new BufferedWriter(new FileWriter(this.getEnv("tmpPath")+filename));
				try {
					
					for(int ks=20;ks<para.length;ks++){
					  output.write(para[ks]);
					    output.newLine();
					}
					output.flush();
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	mailNum=para.length-20;
	    }
	    	    
		return SEARCH_ONLINE_MAILINFO;

	}


	
	public String showImportPricePage() {
		
		
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setEndtime(null);
		p.setStarttime(null);
		int recordNum = mailService.getPriceDetailSmyCount(p);
		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setBsndate(null);

		priceDetailSmyList = mailService.getPriceDetailSmy(p);
		
		return "showimportpricepage";
	}
	

	public String deletePriceDetail() throws UnsupportedEncodingException, ParseException {
		
		
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String bsndatestr=request.getParameter("bsndate");
		String type=new String(request.getParameter("pricetype").getBytes("ISO8859-1"),"utf-8");
		
		Date bsndate=DateUtil.parse(bsndatestr+" 00:00:00", DateUtil.DATE_TIME_FORMAT);
		int year =Integer.parseInt( bsndatestr.substring(0, 4));
		
		
		PageParameter pp = new PageParameter();
		pp.setStart(0);
		pp.setSize(Integer.MAX_VALUE);
		pp.setBsndate(bsndate);
		pp.setPricetype(type);

		List<PriceDetailSmy> managerSmyList = mailService
				.getPriceDetailManagerSmy(pp);
		
		DailyBalance db = new DailyBalance();
		db.setBsndate(bsndate);
		List<DailyBalance> dailybalanceList = reportService
				.getDailyBalance(db);

		if (dailybalanceList != null && dailybalanceList.size() > 0) {

			int len =0;
			if(managerSmyList!=null&&managerSmyList.size()>0)
				len=managerSmyList.size();
			
			for (int i = 0; i < len; i++) {

				PriceDetailSmy smy = managerSmyList.get(i);

				DailyBalance d = new DailyBalance();
				d.setBsndate(bsndate);
				d.setManager(smy.getManager());
				List<DailyBalance> dbList = reportService
						.getDailyBalance(d);

				if (dbList != null && dbList.size() > 0) {

					DailyBalance db2 = dbList.get(0);

					if (type.equals("大笔罚款")) {
						db2.setFakuanprice(0);
					}

					if (type.equals("有偿派费")) {
						db2.setPaisongprice(0);
					}
					if (type.equals("转件费")) {
						db2.setZhuanjianprice(0);
					}
					if (type.equals("丢件费")) {
						db2.setDiujianprice(0);
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
				}

			}

		}
		
		
		
		DailyBalance qu = new DailyBalance();
		qu.setBsndate(bsndate);
		DailyBalance db3=reportService.getDailyBalanceSmy(qu);
		if(db3==null)
			db3=new DailyBalance();
		
		 //更新 YearSmy
	    YearSmy smy=new YearSmy();
		smy.setReportdate(bsndate);
		List<YearSmy> ysList=reportService.getYearSmy(smy);
	
		if(ysList!=null&&ysList.size()>0){
			
			YearSmy ys=ysList.get(0);

		    ys.setZprice(db3.getWholeprice());
		    
			double tinprice=ys.getZprice()+ys.getWprice()+ys.getYprice();
			ys.setTinprice(tinprice);
			double tpprice=ys.getTinprice()-ys.getToutprice();
		
			ys.setTpprice(tpprice);
			reportService.updateYearSmy(ys);
			
			
		}
		
		
		PriceDetailSmy priceDetailSmy = new PriceDetailSmy();
		priceDetailSmy.setBsndate(bsndate);
		priceDetailSmy.setMon(year);
		priceDetailSmy.setPricetype(type);
		mailService.deletePriceDetail(priceDetailSmy);		
		mailService.deletePriceDetailSmy(priceDetailSmy);
		mailService.deletePriceDetailManagerSmy(priceDetailSmy);
	
		this.addErrorMessage("hell", "删除成功！");
		
		String pageNoStr = request.getParameter("pageNo");		

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setEndtime(null);
		p.setStarttime(null);
		int recordNum = mailService.getPriceDetailSmyCount(p);
		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setBsndate(null);

		priceDetailSmyList = mailService.getPriceDetailSmy(p);
		
		return "showimportpricepage";
 
	}
	
	
	public String exportPriceDetail() throws ParseException, IOException {
		
		

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String bsndatestr = request.getParameter("bsndate");		
 	 
		String pricetype = new String(request.getParameter("pricetype").getBytes("ISO8859-1"),"utf-8");
	 	int year = Integer.parseInt(bsndatestr.substring(0, 4));
	 

	 	Date bsndate=DateUtil.parse(bsndatestr+" 00:00:00",DateUtil.DATE_TIME_FORMAT);
		String outfile = this.getEnv("downpath") + "/" + bsndatestr+"-"+pricetype+"_明细.xlsx";
		try {
			
			
			PageParameter	p2 = new PageParameter();		 
			p2.setStart(0);
			p2.setSize(Integer.MAX_VALUE);
			p2.setBsndate(bsndate);
		 	p2.setPricetype(pricetype);
	        p2.setMon(year);
			priceDetailList = mailService.getPriceDetailList(p2);
	 	 

		 	
		 	int recordnum=0;
		 	if(priceDetailList!=null&&priceDetailList.size()>0)
		 		recordnum=priceDetailList.size();
		 	


		 	Excel2007WriterPriceDetail writer = new Excel2007WriterPriceDetail();
			 writer.createSheet(pricetype);
             writer.process(outfile, priceDetailList,0,recordnum);
		 	writer.createFile(outfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String path = outfile;

		// 保存文件
		response.setContentType("application/vnd.ms-excel;charset=GBK");

		try {
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(( bsndatestr+"-"+pricetype+"_明细.xlsx")
							.getBytes("gb2312"), "iso8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(path));

			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[1024];
			int bytesRead;

			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
				bos.write(buff, 0, bytesRead);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();

		}

		 
		return "showimportpricepage";
	
	}
	
	public String showReportManagement() {
		
		
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String pageNoStr = request.getParameter("pageNo");
	 
		int start=0;
		int pageN=1;
		
		if(pageNoStr!=null&&!pageNoStr.equals(""))
			pageN=Integer.parseInt(pageNoStr);
	
		PageParameter	p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		reportFlagList=mailService.queryReportFlagByPage(p);
		

		pageBean = new PageBean(30, pageN, reportFlagList.size());
 
		p = new PageParameter();
		start = (pageN - 1) *30;
		p.setStart(start);
		p.setSize(30);
	  
		reportFlagList = mailService.queryReportFlagByPage(p);

		return REPORT_MANAGEMENT;
	}
	public String showSearchOnlineMailInfo() {
		
		return SHOW_SEARCH_ONLINE_MAIL_INFO;
	}

   public String showSearchOnlineManagerInfo() {
	
		return SHOW_SEARCH_ONLINE_MANAGER_INFO;
	}

	
	
	public String showOnlineMailInfo() throws ParserException, IOException{

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
        
		String strmailid = request.getParameter("mailid");
		weightrulename=new String(request.getParameter("managerdd").getBytes(
		"ISO8859-1"), "utf-8");
		importDate = request.getParameter("importDate");
		
		String htmlstr="";
		
		htmlstr+=createOnlineMailInfo(strmailid);
	 
         this.createMailInfoListFile(htmlstr);
		
		return ONLINE_MAILINFO;
	}
	
	
	
	/**
	 *  该函数实现的功能是：
	 *  1.对面单进行排序--先不做
	 *  2.对获取面单分配记录
	 *  3.获取快件的详细跟踪记录
	 * 
	 * */
	
	private String createOnlineManagerInfo(String mailid) throws ParserException, IOException{
		
		String htmlstr="";
		String fenpeimanager="";
		//获取面单的分配记录
		 DefaultHttpClient httpclient = new DefaultHttpClient();
		String url0=ParseLocalHtml.tmfpurl;

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("lb", "tmfp"));
        formparams.add(new BasicNameValuePair("txm", mailid));
        UrlEncodedFormEntity entity=null;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HttpPost httppost = new HttpPost(url0);
        httppost.setEntity(entity);
     

        HttpResponse response1 = httpclient.execute(httppost);
        try {
            
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed           
            InputStream input=   entity1.getContent();           
           
             if(input.available()==0){
            	
            }else{
            byte [] bt=new byte[2048];        
            input.read(bt);
            input.close();           
          
           JSONObject object = JSONObject.fromObject(new String(bt));
           
           fenpeimanager=object.getString("fb");
          
            }
           
           
       //  htmlstr=" <div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>		  <tr>		   <th width='20%'>快件单号</th>  <th width='30%'>分配网点</th>  <th width='50%'>分配业务员</th></tr> <tr><td> "+mailid+"</td>"+"<td>"+object.getString("wd")+"</td><td>"+object.getString("fb")+"</td></tr></table></div></div></div>";
//         System.out.println(   object.getString("fb"));
//           
//         System.out.println( object.getString("wd"));
//           
             
            EntityUtils.consume(entity1);
        } finally {
            httppost.releaseConnection();
        }
        
		
		//获取快件的收发详情
		
		
		//获取快件跟踪记录
		//1.获取mailcode
		String url1 = ParseLocalHtml.baseurl+ mailid;
		String mailidencode = "";
		Parser parser = new Parser(url1);

		ObjectFindingVisitor visitor = new ObjectFindingVisitor(
				org.htmlparser.tags.FormTag.class);
		parser.visitAllNodesWith(visitor);
		Node[] links = visitor.getTags();
		for (int i = 0; i < links.length; i++) {
			FormTag linkTag = (FormTag) links[i];
			String link = linkTag.getAttribute("action");

			mailidencode = link.substring(link.indexOf("wen=") + 4, link
					.indexOf("&jmm"));
		}

		//System.out.println(mailidencode);
		
		String url2 = ParseLocalHtml.baseurl2+ mailidencode;
		Parser parser2 = new Parser(url2);
		
		//3.请求并且组装快件跟踪记录
		//System.out.println(url2);
	 
		ObjectFindingVisitor visitor2 = new ObjectFindingVisitor(
				org.htmlparser.tags.ScriptTag.class);
		parser2.visitAllNodesWith(visitor2);
		Node[] scriptTag = visitor2.getTags();
		for (int i = 0; i < scriptTag.length; i++) {
			ScriptTag linkTag = (ScriptTag) scriptTag[i];
			String str = linkTag.getScriptCode();

			if (str!=null&&str.indexOf("binTable") != -1) {

				String st = str.substring(str.indexOf("b4.de(") + 7, str
						.indexOf("\"));"));
				
				String html=ParseHTML.transformToNormalText(st);
				if(html==null ||html.equals("")){
					html="";
					
				}else {
					
					html=html+"r>";
				   String [] htmlarray=  html.split("</td>");
				
				if(htmlarray.length>6)
					html=htmlarray[5].substring(4);
				//System.out.println(st);
// <div class='imp_mod_inner'><div class='cc_para imp_intro_para'><div class='cc_link_sign_box2'> <table id='mytable'><tr><th width='30%'>快件单号</th>  <th width='35%'>揽件人</th>  <th width='35%'>面单分配</th></tr>"+html
		//		+ "r>	</table>      </div></div></div>
				
				
				}
				
				if(html.equals("")||fenpeimanager.indexOf(html)==-1){
					  htmlstr="<tr> <td style='background-color:red;color:white'>"+mailid+"</td><td style='background-color:red;color:white'>"+html+"</td><td style='background-color:red;color:white'>"+fenpeimanager+"</td><tr>";
					
					}else{
						htmlstr="<tr> <td>"+mailid+"</td><td>"+html+"</td><td>"+fenpeimanager+"</td><tr>";
							
						
					}
				break;

			}
		}	
	 
		
		return htmlstr;
	}
	
	
	
	/***
	 * 
	 * 该函数的功能是将 传入的html字符串组合后写入到特定的vm文件中。主要用于获取单号的揽件人和面单分配的者的记录
	 * 
	 * */
	private void createManagerInfoListFile(String htmlstr){
		
          OutputStream output;
		
		try {
			output = new FileOutputStream(this.getEnv("mailInfoPath")+"managerinfolist.vm");
			
			try {
				output.write(htmlstr.getBytes("utf-8"));
				output.flush();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	

	/**
	 *  该函数实现的功能是：
	 *  1.获取面单分配的数据
	 *  2.获取快件收发的详细信息
	 *  3.获取快件的详细跟踪记录
	 * 
	 * */
	
	private String createOnlineMailInfo(String mailid) throws ParserException, IOException{
		
		String htmlstr="";
		
		//获取面单的分配记录
		 DefaultHttpClient httpclient = new DefaultHttpClient();
		String url0=ParseLocalHtml.tmfpurl;

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("lb", "tmfp"));
        formparams.add(new BasicNameValuePair("txm", mailid));
        UrlEncodedFormEntity entity=null;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HttpPost httppost = new HttpPost(url0);
        httppost.setEntity(entity);
     

        HttpResponse response1 = httpclient.execute(httppost);
        String wd="";
        String fb="";
        try {
            
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed           
            InputStream input=   entity1.getContent();  

            if(input.available()==0){
            	
            }else{
            byte [] bt=new byte[2048];        
            input.read(bt);
            input.close();           
          
           JSONObject object = JSONObject.fromObject(new String(bt));
           wd=object.getString("wd");
           fb=object.getString("fb");
          
            }
            htmlstr=" <div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>		  <tr>		   <th width='20%'>快件单号</th>  <th width='30%'>分配网点</th>  <th width='50%'>分配业务员</th></tr> <tr><td> "+mailid+"</td>"+"<td>"+wd+"</td><td>"+fb+"</td></tr></table></div></div></div>";
            
//         System.out.println(   object.getString("fb"));
//           
//         System.out.println( object.getString("wd"));
//           
             
            EntityUtils.consume(entity1);
        } finally {
            httppost.releaseConnection();
        }
        
		
		//获取快件的收发详情
		
		
		//获取快件跟踪记录
		//1.获取mailcode
		String url1 = ParseLocalHtml.baseurl+ mailid;
		String mailidencode = "";
		Parser parser = new Parser(url1);

		ObjectFindingVisitor visitor = new ObjectFindingVisitor(
				org.htmlparser.tags.FormTag.class);
		parser.visitAllNodesWith(visitor);
		Node[] links = visitor.getTags();
		for (int i = 0; i < links.length; i++) {
			FormTag linkTag = (FormTag) links[i];
			String link = linkTag.getAttribute("action");

			mailidencode = link.substring(link.indexOf("wen=") + 4, link
					.indexOf("&jmm"));
		}

		//System.out.println(mailidencode);
		
		String url2 = ParseLocalHtml.baseurl2+ mailidencode;
		Parser parser2 = new Parser(url2);
		
		//3.请求并且组装快件跟踪记录
		//System.out.println(url2);
		String maildetail="";
		ObjectFindingVisitor visitor2 = new ObjectFindingVisitor(
				org.htmlparser.tags.ScriptTag.class);
		parser2.visitAllNodesWith(visitor2);
		Node[] scriptTag = visitor2.getTags();
		for (int i = 0; i < scriptTag.length; i++) {
			ScriptTag linkTag = (ScriptTag) scriptTag[i];
			String str = linkTag.getScriptCode();

			if (str!=null&&str.indexOf("binTable") != -1) {

				String st = str.substring(str.indexOf("b4.de(") + 7, str
						.indexOf("\"));"));

				//System.out.println(st);

				maildetail="  <div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>		  <tr>		   <th width='15%'>扫描时间</th>  <th width='15%'>入库时间</th>  <th width='10%'>扫描类型</th> <th width='30%'>跟踪记录</th> <th width='10%'>扫描单号</th> <th width='10%'>业务员</th> <th width='10%'>重量(千克)</th>   </tr>"+ParseHTML.transformToNormalText(st)
						+ "r>	</table>      </div></div></div>";

				break;

			}
		}
		
		
		//2.查找快件详情的url
		//System.out.println(url2);
		String querymailidurl="";
		Parser parser4 = new Parser(url2);
		
		ObjectFindingVisitor visitor1 = new ObjectFindingVisitor(
				org.htmlparser.tags.Div.class);
		parser4.visitAllNodesWith(visitor1);
		Node[] divTag = visitor1.getTags();
		for (int i = 0; i < divTag.length; i++) {
			Div linkTag = (Div) divTag[i];
			String str = linkTag.getAttribute("id");

			if (str!=null&&str.equals("div_ld")) {

				String html=linkTag.toHtml();
				
				querymailidurl=html.substring(html.indexOf("src")+5, html.indexOf("'></iframe>"));
			
				break;

			}
		}
		
		
		String url3 = querymailidurl;
		Parser parser3 = new Parser(url3);
		
		ObjectFindingVisitor visitor3 = new ObjectFindingVisitor(
				org.htmlparser.tags.TableTag.class);
		parser3.visitAllNodesWith(visitor3);
		Node[] tableTags = visitor3.getTags();
		for (int i = 0; i < tableTags.length; i++) {
			TableTag linkTag = (TableTag) tableTags[i];
			if(linkTag.getAttribute("border").equals("1"))
			  htmlstr=htmlstr+"<div class='imp_mod_inner'>		<div class='cc_para imp_intro_para'>		  <div class='cc_link_sign_box2'>		  <table id='mytable'>	" +linkTag.getChildrenHTML()+"</table> </div></div></div>";
              
		}
	
		htmlstr=htmlstr+maildetail;
		
		return htmlstr;
	}
	
	
	
	/***
	 * 
	 * 该函数的功能是将 传入的html字符串组合后写入到特定的vm文件中。
	 * 
	 * */
	private void createMailInfoListFile(String htmlstr){
		
          OutputStream output;
		
		try {
			output = new FileOutputStream(this.getEnv("mailInfoPath")+"mailinfolist.vm");
			
			try {
				output.write(htmlstr.getBytes("utf-8"));
				output.flush();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	/**
	 * 
	 
	 **/

	public String showErrorPage() throws IOException {

		return ERROR;

	}

	public String showUpdateWord() {

		return UPDATE_PASSWORD;
	}

	public String updatePassword() {

		ActionContext ctx = ActionContext.getContext();

		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String pass = request.getParameter("pass");
		String newpass = request.getParameter("newpass");

		String passMD5 = CreateMD5.createMD5(pass);

		user.setPassword(passMD5);

		try {
			if (!userServiceImpl.doLoginIn(user, ctx)) {
				this.addErrorMessage("hello", "密码不正确！");
			} else {
				passMD5 = CreateMD5.createMD5(newpass);
				List<User> userList=userServiceImpl.getUserByLoginid(user.getLoginid());
				if(userList!=null&&userList.size()>0){
					
					User us=userList.get(0);
					us.setPassword(passMD5);
				 	userServiceImpl.updateUser(us);
					this.addErrorMessage("hello", "密码更新成功！");
					
				}
			
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("User is error");
		}

		return UPDATE_PASSWORD;
	}

	public String userLogout() {

		ActionContext ctx = ActionContext.getContext();
		userServiceImpl.doLogout(user.getUserid(), ctx);
		return "error";
	}

	@Override
	public Map getErrorMessage() {
		return errorMessage;
	}

	@Override
	protected void addErrorMessage(String anErrorInputId, String anErrorMessage) {
		if (errorMessage == null)
			errorMessage = new HashMap();
		errorMessage.put(anErrorInputId, anErrorMessage);
	}

	@Override
	protected boolean hasErrorMessage() {
		return (errorMessage != null) ? true : false;
	}

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}


 
}
