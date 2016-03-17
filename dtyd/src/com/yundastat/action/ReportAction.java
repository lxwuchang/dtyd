/**
 * 
 */
package com.yundastat.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yundastat.util.*;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.yundastat.excelutil.Excel2007WriterCompanyIncomeDaySmy;
import com.yundastat.excelutil.Excel2007WriterCompanyIncomeMonthDetail;
import com.yundastat.excelutil.Excel2007WriterCompanyIncomeMonthSmy;
import com.yundastat.excelutil.Excel2007WriterCompanyIncomeMonthSmyByDay;
import com.yundastat.excelutil.Excel2007WriterCompanyIncomeMonthSmyByManager;
import com.yundastat.excelutil.Excel2007WriterCompanyIncomeYearSmy;
import com.yundastat.excelutil.Excel2007WriterCompanyIncomeYearSmyByMonth;
import com.yundastat.excelutil.Excel2007WriterDailyBalance;
import com.yundastat.excelutil.Excel2007WriterDailyBalanceMonthByDay;
import com.yundastat.excelutil.Excel2007WriterDailyBalanceMonthByManager;
import com.yundastat.excelutil.Excel2007WriterDailyBalanceMonthByManagerDetail;
import com.yundastat.excelutil.Excel2007WriterDailyBalanceYearByManager;
import com.yundastat.excelutil.Excel2007WriterDailyBalanceYearByMonth;
import com.yundastat.excelutil.Excel2007WriterFankuanOutcome;
import com.yundastat.excelutil.Excel2007WriterFankuanOutcomeYearByMonth;
import com.yundastat.excelutil.Excel2007WriterGongziOutcome;
import com.yundastat.excelutil.Excel2007WriterGongziOutcomeYearByMonth;
import com.yundastat.excelutil.Excel2007WriterIncomeDetail;
import com.yundastat.excelutil.Excel2007WriterIncomeMonthByDay;
import com.yundastat.excelutil.Excel2007WriterIncomeMonthByManager;
import com.yundastat.excelutil.Excel2007WriterIncomeYearByManager;
import com.yundastat.excelutil.Excel2007WriterIncomeYearByMonth;
import com.yundastat.excelutil.Excel2007WriterIncomeYearSmyByManager;
import com.yundastat.excelutil.Excel2007WriterMailPriceDaySmy;
import com.yundastat.excelutil.Excel2007WriterMailPriceDetail;
import com.yundastat.excelutil.Excel2007WriterMailPriceMonthByDay;
import com.yundastat.excelutil.Excel2007WriterMailPriceMonthByManager;
import com.yundastat.excelutil.Excel2007WriterMailPriceYearByMonth;
import com.yundastat.excelutil.Excel2007WriterMailPriceYearByMonthManager;
import com.yundastat.excelutil.Excel2007WriterPeikuanOutcome;
import com.yundastat.excelutil.Excel2007WriterPeikuanOutcomeYearByMonth;
import com.yundastat.excelutil.Excel2007WriterZZF;
import com.yundastat.excelutil.Excel2007WriterZhichuOutcome;
import com.yundastat.excelutil.Excel2007WriterZhichuOutcomeYearByMonth;
import com.yundastat.excelutil.Excel2007WriterZhipaoOutcome;
import com.yundastat.excelutil.Excel2007WriterZhipaoOutcomeYearByMonth;
import com.yundastat.model.CompanyIncome;
import com.yundastat.model.DailyBalance;
import com.yundastat.model.FankuanOutcome;
import com.yundastat.model.GongziOutcome;
import com.yundastat.model.Income;
import com.yundastat.model.MailFenpei;
import com.yundastat.model.ManagerInfo;
import com.yundastat.model.PageBean;
import com.yundastat.model.PageParameter;
import com.yundastat.model.PeikuanOutcome;
import com.yundastat.model.PriceDetail;
import com.yundastat.model.StatReport;
import com.yundastat.model.YearSmy;
import com.yundastat.model.ZhichuOutcome;
import com.yundastat.model.ZhipaoOutcome;
import com.yundastat.service.MailService;
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
public class ReportAction extends BaseAction {

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
	private File upexcel;
	private String upexcelContentType;
	private String upexcelFilename;
	protected Map errorMessage = new HashMap();
	private UserServiceImpl userServiceImpl;
	private MailService mailService;
	private ReportService reportService;
	private List<DailyBalance> dailybalanceList = new ArrayList();
	private String importDate;
	private List<ManagerInfo> managerInfoList = new ArrayList();
	private MailFenpei mailFenpei;
	private List<MailFenpei> mailFenpeiList = new ArrayList();
	private PageBean pageBean;
	private CompanyIncome companyIncome;
	private List<CompanyIncome> companyIncomeList = new ArrayList();
	private List<StatReport> statReportList = new ArrayList();

	private List<PriceDetail> priceDetailList = new ArrayList();
	private String bsndatestr;
	private Income income;
	private List<Income> incomeList = new ArrayList();
	private List<GongziOutcome> gongziOutcomeList = new ArrayList();
	private List<FankuanOutcome> fankuanOutcomeList = new ArrayList();
	private List<ZhichuOutcome> zhichuOutcomeList = new ArrayList();
	private ZhichuOutcome zhichuOutcome;

	private List<ZhipaoOutcome> zhipaoOutcomeList = new ArrayList();
	private ZhipaoOutcome zhipaoOutcome;

	private List<PeikuanOutcome> peikuanOutcomeList = new ArrayList();
	private PeikuanOutcome peikuanOutcome;

	private List<PeikuanOutcome> peikuanOutcomeSmyList = new ArrayList();
	public final static int pageSize = 10;
	private List<YearSmy> yearSmyList = new ArrayList();
	private GongziOutcome gongziOutcome;
	private FankuanOutcome fankuanOutcome;

	public FankuanOutcome getFankuanOutcome() {
		return fankuanOutcome;
	}

	public void setFankuanOutcome(FankuanOutcome fankuanOutcome) {
		this.fankuanOutcome = fankuanOutcome;
	}

	public GongziOutcome getGongziOutcome() {
		return gongziOutcome;
	}

	public void setGongziOutcome(GongziOutcome gongziOutcome) {
		this.gongziOutcome = gongziOutcome;
	}

	public List<YearSmy> getYearSmyList() {
		return yearSmyList;
	}

	public void setYearSmyList(List<YearSmy> yearSmyList) {
		this.yearSmyList = yearSmyList;
	}

	public List<PeikuanOutcome> getPeikuanOutcomeSmyList() {
		return peikuanOutcomeSmyList;
	}

	public void setPeikuanOutcomeSmyList(
			List<PeikuanOutcome> peikuanOutcomeSmyList) {
		this.peikuanOutcomeSmyList = peikuanOutcomeSmyList;
	}

	public List<PeikuanOutcome> getPeikuanOutcomeList() {
		return peikuanOutcomeList;
	}

	public void setPeikuanOutcomeList(List<PeikuanOutcome> peikuanOutcomeList) {
		this.peikuanOutcomeList = peikuanOutcomeList;
	}

	public PeikuanOutcome getPeikuanOutcome() {
		return peikuanOutcome;
	}

	public void setPeikuanOutcome(PeikuanOutcome peikuanOutcome) {
		this.peikuanOutcome = peikuanOutcome;
	}

	public List<ZhipaoOutcome> getZhipaoOutcomeList() {
		return zhipaoOutcomeList;
	}

	public void setZhipaoOutcomeList(List<ZhipaoOutcome> zhipaoOutcomeList) {
		this.zhipaoOutcomeList = zhipaoOutcomeList;
	}

	public ZhipaoOutcome getZhipaoOutcome() {
		return zhipaoOutcome;
	}

	public void setZhipaoOutcome(ZhipaoOutcome zhipaoOutcome) {
		this.zhipaoOutcome = zhipaoOutcome;
	}

	public ZhichuOutcome getZhichuOutcome() {
		return zhichuOutcome;
	}

	public void setZhichuOutcome(ZhichuOutcome zhichuOutcome) {
		this.zhichuOutcome = zhichuOutcome;
	}

	public List<ZhichuOutcome> getZhichuOutcomeList() {
		return zhichuOutcomeList;
	}

	public void setZhichuOutcomeList(List<ZhichuOutcome> zhichuOutcomeList) {
		this.zhichuOutcomeList = zhichuOutcomeList;
	}

	public List<FankuanOutcome> getFankuanOutcomeList() {
		return fankuanOutcomeList;
	}

	public void setFankuanOutcomeList(List<FankuanOutcome> fankuanOutcomeList) {
		this.fankuanOutcomeList = fankuanOutcomeList;
	}

	public List<GongziOutcome> getGongziOutcomeList() {
		return gongziOutcomeList;
	}

	public void setGongziOutcomeList(List<GongziOutcome> gongziOutcomeList) {
		this.gongziOutcomeList = gongziOutcomeList;
	}

	public List<Income> getIncomeList() {
		return incomeList;
	}

	public void setIncomeList(List<Income> incomeList) {
		this.incomeList = incomeList;
	}

	public Income getIncome() {
		return income;
	}

	public void setIncome(Income income) {
		this.income = income;
	}

	public String getBsndatestr() {
		return bsndatestr;
	}

	public void setBsndatestr(String bsndatestr) {
		this.bsndatestr = bsndatestr;
	}

	public File getUpexcel() {
		return upexcel;
	}

	public void setUpexcel(File upexcel) {
		this.upexcel = upexcel;
	}

	public String getUpexcelContentType() {
		return upexcelContentType;
	}

	public void setUpexcelContentType(String upexcelContentType) {
		this.upexcelContentType = upexcelContentType;
	}

	public String getUpexcelFilename() {
		return upexcelFilename;
	}

	public void setUpexcelFilename(String upexcelFilename) {
		this.upexcelFilename = upexcelFilename;
	}

	public List<PriceDetail> getPriceDetailList() {
		return priceDetailList;
	}

	public void setPriceDetailList(List<PriceDetail> priceDetailList) {
		this.priceDetailList = priceDetailList;
	}

	public List<StatReport> getStatReportList() {
		return statReportList;
	}

	public void setStatReportList(List<StatReport> statReportList) {
		this.statReportList = statReportList;
	}

	public List<CompanyIncome> getCompanyIncomeList() {
		return companyIncomeList;
	}

	public void setCompanyIncomeList(List<CompanyIncome> companyIncomeList) {
		this.companyIncomeList = companyIncomeList;
	}

	public CompanyIncome getCompanyIncome() {
		return companyIncome;
	}

	public void setCompanyIncome(CompanyIncome companyIncome) {
		this.companyIncome = companyIncome;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List<MailFenpei> getMailFenpeiList() {
		return mailFenpeiList;
	}

	public void setMailFenpeiList(List<MailFenpei> mailFenpeiList) {
		this.mailFenpeiList = mailFenpeiList;
	}

	public MailFenpei getMailFenpei() {
		return mailFenpei;
	}

	public void setMailFenpei(MailFenpei mailFenpei) {
		this.mailFenpei = mailFenpei;
	}

	public List<ManagerInfo> getManagerInfoList() {
		return managerInfoList;
	}

	public void setManagerInfoList(List<ManagerInfo> managerInfoList) {
		this.managerInfoList = managerInfoList;
	}

	private static Logger logger = Logger.getLogger(ReportAction.class
			.getName());

	private YearSmy yearSmy;

	public List<DailyBalance> getDailybalanceList() {
		return dailybalanceList;
	}

	public void setDailybalanceList(List<DailyBalance> dailybalanceList) {
		this.dailybalanceList = dailybalanceList;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public YearSmy getYearSmy() {
		return yearSmy;
	}

	public void setYearSmy(YearSmy yearSmy) {
		this.yearSmy = yearSmy;
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

	public String showCompanyIncomeYearSmyByMonthPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);
			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
			}
		} else {
			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		PageParameter p = new PageParameter();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);

		companyIncomeList = reportService.getCompanyIncomeSmyByMonthList(p);

		if (companyIncomeList != null && companyIncomeList.size() > 0) {

			companyIncome = new CompanyIncome();

			int len = companyIncomeList.size();
			int num = 0;
			double price = 0;
			double transprice = 0;
			double mailprice = 0;
			double profit = 0;
			for (int i = 0; i < len; i++) {
				transprice += companyIncomeList.get(i).getTransformprice();
				num += companyIncomeList.get(i).getNum();
				price += companyIncomeList.get(i).getPrice();
				mailprice += companyIncomeList.get(i).getMailprice();
				profit += companyIncomeList.get(i).getProfit();

			}
			companyIncome.setNum(num);
			companyIncome.setPrice(price);
			companyIncome.setTransformprice(transprice);
			companyIncome.setMailprice(mailprice);
			companyIncome.setProfit(profit);

		}
		return "showcompanyincomeyearsmybymonthpage";

	}

	public String showCompanyIncomeYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
			}

		} else {
			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
			}

		}

		PageParameter p = new PageParameter();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);

		companyIncomeList = reportService.getCompanyIncomeSmyList(p);

		if (companyIncomeList != null && companyIncomeList.size() > 0) {

			companyIncome = new CompanyIncome();

			int len = companyIncomeList.size();
			int num = 0;
			double price = 0;
			double transprice = 0;
			double mailprice = 0;
			double profit = 0;
			for (int i = 0; i < len; i++) {
				transprice += companyIncomeList.get(i).getTransformprice();
				num += companyIncomeList.get(i).getNum();
				price += companyIncomeList.get(i).getPrice();
				mailprice += companyIncomeList.get(i).getMailprice();
				profit += companyIncomeList.get(i).getProfit();

			}
			companyIncome.setNum(num);
			companyIncome.setPrice(price);
			companyIncome.setTransformprice(transprice);
			companyIncome.setMailprice(mailprice);
			companyIncome.setProfit(profit);

		}

		return "showcompanyincomeyearsmypage";

	}

	public String showCompanyIncomeMonthSmyByDayPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| ((yearSmy.getYear() == null && yearSmy.getMonth() == null))) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		// 列举出当月每个人汇总情况，这里是按人
		PageParameter p = new PageParameter();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);

		companyIncomeList = reportService.getCompanyIncomeSmyByDayList(p);

		if (companyIncomeList != null && companyIncomeList.size() > 0) {

			companyIncome = new CompanyIncome();

			int len = companyIncomeList.size();
			int num = 0;
			double price = 0;
			double transprice = 0;
			double mailprice = 0;
			double profit = 0;
			for (int i = 0; i < len; i++) {
				transprice += companyIncomeList.get(i).getTransformprice();
				num += companyIncomeList.get(i).getNum();
				price += companyIncomeList.get(i).getPrice();
				mailprice += companyIncomeList.get(i).getMailprice();
				profit += companyIncomeList.get(i).getProfit();

			}
			companyIncome.setNum(num);
			companyIncome.setPrice(price);
			companyIncome.setTransformprice(transprice);
			companyIncome.setMailprice(mailprice);
			companyIncome.setProfit(profit);

		}

		return "showcompanyincomemonthsmybydaypage";

	}

	public String showCompanyIncomeMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		// 列举出当月每个人汇总情况，这里是按人
		PageParameter p = new PageParameter();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);

		companyIncomeList = reportService.getCompanyIncomeSmyList(p);

		if (companyIncomeList != null && companyIncomeList.size() > 0) {

			companyIncome = new CompanyIncome();

			int len = companyIncomeList.size();
			int num = 0;
			double price = 0;
			double transprice = 0;
			double mailprice = 0;
			double profit = 0;
			for (int i = 0; i < len; i++) {
				transprice += companyIncomeList.get(i).getTransformprice();
				num += companyIncomeList.get(i).getNum();
				price += companyIncomeList.get(i).getPrice();
				mailprice += companyIncomeList.get(i).getMailprice();
				profit += companyIncomeList.get(i).getProfit();

			}
			companyIncome.setNum(num);
			companyIncome.setPrice(price);
			companyIncome.setTransformprice(transprice);
			companyIncome.setMailprice(mailprice);
			companyIncome.setProfit(profit);

		}

		return "showcompanyincomemonthsmypage";

	}

	public String showCompanyIncomeDaySmyPage() throws IOException,
			ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		Date maxday = null;

		if (yearSmy == null || yearSmy.getBsndatestr().equals("")) {

			maxday = reportService.getMaxDaySmy();

			YearSmy smy = new YearSmy();
			smy.setReportdate(maxday);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0)
				yearSmy = ysList.get(0);
			else {
				yearSmy = new YearSmy();
				yearSmy.setReportdate(maxday);
			}
		} else {

			maxday = DateUtil.parse(yearSmy.getBsndatestr() + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
			YearSmy smy = new YearSmy();
			smy.setReportdate(maxday);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				yearSmy = ysList.get(0);
				yearSmy.setReportdate(maxday);
			} else {
				yearSmy = new YearSmy();
				yearSmy.setReportdate(maxday);
			}

		}

		PageParameter p = new PageParameter();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setBsndate(maxday);

		companyIncomeList = reportService.getCompanyIncomeSmyList(p);

		if (companyIncomeList != null && companyIncomeList.size() > 0) {

			companyIncome = new CompanyIncome();

			int len = companyIncomeList.size();
			int num = 0;
			double price = 0;
			double transprice = 0;
			double mailprice = 0;
			double profit = 0;
			for (int i = 0; i < len; i++) {
				transprice += companyIncomeList.get(i).getTransformprice();
				num += companyIncomeList.get(i).getNum();
				price += companyIncomeList.get(i).getPrice();
				mailprice += companyIncomeList.get(i).getMailprice();
				profit += companyIncomeList.get(i).getProfit();

			}
			companyIncome.setNum(num);
			companyIncome.setPrice(price);
			companyIncome.setTransformprice(transprice);
			companyIncome.setMailprice(mailprice);
			companyIncome.setProfit(profit);

		}

		return "showcompanyincomedaysmypage";

	}

	public String deletecompanyIncome() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String dmid = request.getParameter("dmid");
		PageParameter pp = new PageParameter();
		pp.setId(Integer.parseInt(dmid));
		pp.setStart(0);
		pp.setSize(10);
		List<CompanyIncome> ciList = reportService.getCompanyIncomeList(pp);
		if (ciList != null && ciList.size() > 0) {
			companyIncome = ciList.get(0);
		}

		reportService.deleteCompanyIncome(companyIncome);
		this.addErrorMessage("hello", "删除成功");

		// 计算profit;
		double profit = 0;
		if (companyIncomeList != null && companyIncomeList.size() > 0) {

			int len = companyIncomeList.size();

			for (int i = 0; i < len; i++) {

				profit = profit + companyIncomeList.get(i).getProfit();

			}

		}

		// 更新yearSmy

		YearSmy smy = new YearSmy();
		smy.setReportdate(companyIncome.getBsndate());
		List<YearSmy> ysList = reportService.getYearSmy(smy);
		if (ysList != null && ysList.size() > 0) {
			yearSmy = ysList.get(0);

			yearSmy.setYprice(profit);
			double tinprice = yearSmy.getZprice() + yearSmy.getWprice()
					+ yearSmy.getYprice();
			yearSmy.setTinprice(tinprice);
			double tpprice = yearSmy.getTinprice() - yearSmy.getToutprice();

			yearSmy.setTinprice(tinprice);
			yearSmy.setTpprice(tpprice);

			reportService.updateYearSmy(yearSmy);

		} else {

			YearSmy ys = new YearSmy();
			ys.setReportdate(companyIncome.getBsndate());
			ys.setYear(companyIncome.getYear());
			ys.setMonth(companyIncome.getMonth());
			ys.setWnum(0);
			ys.setZnum(0);
			ys.setYnum(0);
			ys.setWprice(0);
			ys.setZprice(0);
			ys.setYprice(profit);

			ys.setYfkprice(0);
			ys.setZpprice(0);
			ys.setGsprice(0);
			ys.setGzprice(0);
			ys.setQtprice(0);

			ys.setTinprice(profit);
			ys.setToutprice(0);
			ys.setTpprice(profit);

			reportService.createYearSmy(ys);
		}

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		Integer recordNum = reportService.getCompanyIncomeListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);

		companyIncomeList = reportService.getCompanyIncomeList(p);

		return "showimportcompanyincomepage";

	}

	public String createCompanyIncome() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		Date bsndate = null;
		try {
			bsndate = DateUtil.parse(companyIncome.getBsndatestr()
					+ " 00:00:00", DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		companyIncome.setBsndate(bsndate);

		if (companyIncome.getType().equals("月结")) {

			companyIncome.setManager(companyIncome.getManager1());
			companyIncome.setPricetype(companyIncome.getPricetype1());
			companyIncome.setTransformprice(0);
			companyIncome.setMailprice(0);

		} else {

			if (companyIncome.getPricetype().equals("中转费")) {

				companyIncome.setNum(0);
				companyIncome.setPrice(0);
			} else if (companyIncome.getPricetype().equals("面单费")) {

				companyIncome.setNum(0);
				companyIncome.setPrice(0);

			} else {
				companyIncome.setTransformprice(0);
				companyIncome.setMailprice(0);

			}

		}

		if (companyIncome.getNum() == null)
			companyIncome.setNum(0);

		companyIncome.setCreatetime(new Date());
		companyIncome.setYear(Integer.parseInt(companyIncome.getBsndatestr()
				.substring(0, 4)));
		companyIncome.setMonth(Integer.parseInt(companyIncome.getBsndatestr()
				.substring(5, 7)));

		reportService.createCompanyIncome(companyIncome);

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setBsndate(companyIncome.getBsndate());
		companyIncomeList = reportService.getCompanyIncomeSmyList(p);

		// 计算profit;
		double profit = 0;
		if (companyIncomeList != null && companyIncomeList.size() > 0) {

			int len = companyIncomeList.size();

			for (int i = 0; i < len; i++) {

				profit = profit + companyIncomeList.get(i).getProfit();

			}

		}

		// 更新yearSmy

		YearSmy smy = new YearSmy();
		smy.setReportdate(companyIncome.getBsndate());
		List<YearSmy> ysList = reportService.getYearSmy(smy);
		if (ysList != null && ysList.size() > 0) {
			yearSmy = ysList.get(0);

			yearSmy.setYprice(profit);
			double tinprice = yearSmy.getZprice() + yearSmy.getWprice()
					+ yearSmy.getYprice();
			yearSmy.setTinprice(tinprice);
			double tpprice = yearSmy.getTinprice() - yearSmy.getToutprice();

			yearSmy.setTinprice(tinprice);
			yearSmy.setTpprice(tpprice);

			reportService.updateYearSmy(yearSmy);

		} else {

			YearSmy ys = new YearSmy();
			ys.setReportdate(companyIncome.getBsndate());
			ys.setYear(companyIncome.getYear());
			ys.setMonth(companyIncome.getMonth());
			ys.setWnum(0);
			ys.setZnum(0);
			ys.setYnum(0);
			ys.setWprice(0);
			ys.setZprice(0);
			ys.setYprice(profit);

			ys.setYfkprice(0);
			ys.setZpprice(0);
			ys.setGsprice(0);
			ys.setGzprice(0);
			ys.setQtprice(0);

			ys.setTinprice(profit);
			ys.setToutprice(0);
			ys.setTpprice(profit);

			reportService.createYearSmy(ys);
		}

		this.addErrorMessage("hello", "录入成功");

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		Integer recordNum = reportService.getCompanyIncomeListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);

		companyIncomeList = reportService.getCompanyIncomeList(p);

		return "showimportcompanyincomepage";

	}

	public String showImportCompanyIncomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		if (companyIncome == null)
			companyIncome = new CompanyIncome();

		companyIncome.setBsndate(new Date());

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		Integer recordNum = reportService.getCompanyIncomeListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);

		companyIncomeList = reportService.getCompanyIncomeList(p);

		return "showimportcompanyincomepage";

	}

	public String showImportMailInfoPage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);
		if (mailFenpei == null)
			mailFenpei = new MailFenpei();

		mailFenpei.setBsndate(new Date());

		String pageNoStr = request.getParameter("pageNo");
		bsndatestr = DateUtil.formatYYYYMMDD(new Date());

		int year = Integer.parseInt(bsndatestr.substring(0, 4));
		int month = Integer.parseInt(bsndatestr.substring(5, 7));

		if (yearSmy == null)
			yearSmy = new YearSmy();
		yearSmy.setYear(year);
		yearSmy.setMonth(month);

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		Integer recordNum = reportService.getMailFenpeiListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		mailFenpeiList = reportService.getMailFenpeiList(p);

		return "showimportmailinfopage";

	}

	public String showUpdateMailInfoPage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		String mailinfoid = request.getParameter("mailinfoid");
		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setId(Integer.parseInt(mailinfoid));
		mailFenpeiList = reportService.getMailFenpeiList(p);

		if (mailFenpeiList != null && mailFenpeiList.size() == 1)
			mailFenpei = mailFenpeiList.get(0);

		String pageNo = request.getParameter("pageNo");

		if (pageNo == null)
			yearSmy.setPageNo(1);
		else
			yearSmy.setPageNo(Integer.parseInt(pageNo));

		return "showupdatemailinfopage";

	}

	// /如何更新
	public String updateMailFenpei() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String mailinfoid = request.getParameter("mailinfoid");

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setId(Integer.parseInt(mailinfoid));
		mailFenpeiList = reportService.getMailFenpeiList(p);

		if (mailFenpeiList != null && mailFenpeiList.size() == 1) {

			MailFenpei oldmailfenpei = mailFenpeiList.get(0);

			String oldbsndate = DateUtil.formatYYYYMMDD(oldmailfenpei
					.getBsndate());
			int oldnum = oldmailfenpei.getNum();
			double oldprice = oldmailfenpei.getPrice();
			if (oldbsndate.equals(mailFenpei.getBsndatestr())) {

				oldmailfenpei.setManagerid(mailFenpei.getManagerid());
				oldmailfenpei.setType(mailFenpei.getType());
				oldmailfenpei.setNum(mailFenpei.getNum());
				oldmailfenpei.setPerprice(mailFenpei.getPerprice());
				oldmailfenpei.setPrice(mailFenpei.getPrice());
				oldmailfenpei.setMemo(mailFenpei.getMemo());

				reportService.updateMailFenpei(oldmailfenpei);

				YearSmy smy = new YearSmy();
				smy.setReportdate(DateUtil.parse(mailFenpei.getBsndatestr()
						+ " 00:00:00", DateUtil.DATE_TIME_FORMAT));
				List<YearSmy> ysList = reportService.getYearSmy(smy);
				if (ysList != null && ysList.size() > 0) {
					YearSmy yearSmy2 = ysList.get(0);

					yearSmy2.setWnum(yearSmy2.getWnum() - oldnum
							+ mailFenpei.getNum());
					yearSmy2.setWprice(yearSmy2.getWprice() - oldprice
							+ mailFenpei.getPrice());
					double tinprice = yearSmy2.getZprice()
							+ yearSmy2.getWprice() + yearSmy2.getYprice();
					yearSmy2.setTinprice(tinprice);
					double tpprice = yearSmy2.getTinprice()
							- yearSmy2.getToutprice();

					yearSmy2.setTinprice(tinprice);
					yearSmy2.setTpprice(tpprice);

					reportService.updateYearSmy(yearSmy2);
				}

			} else {
				// 修改了面单日期

				YearSmy smy = new YearSmy();
				smy.setReportdate(DateUtil.parse(oldbsndate + " 00:00:00",
						DateUtil.DATE_TIME_FORMAT));
				List<YearSmy> ysList = reportService.getYearSmy(smy);
				if (ysList != null && ysList.size() > 0) {
					YearSmy yearSmy2 = ysList.get(0);

					yearSmy2.setWnum(yearSmy2.getWnum() - oldnum);
					yearSmy2.setWprice(yearSmy2.getWprice() - oldprice);
					double tinprice = yearSmy2.getZprice()
							+ yearSmy2.getWprice() + yearSmy2.getYprice();
					yearSmy2.setTinprice(tinprice);
					double tpprice = yearSmy2.getTinprice()
							- yearSmy2.getToutprice();

					yearSmy2.setTinprice(tinprice);
					yearSmy2.setTpprice(tpprice);

					reportService.updateYearSmy(yearSmy2);
					// 先去掉面单的数据

					oldmailfenpei.setManagerid(mailFenpei.getManagerid());
					oldmailfenpei.setType(mailFenpei.getType());
					oldmailfenpei.setNum(mailFenpei.getNum());
					oldmailfenpei.setPerprice(mailFenpei.getPerprice());
					oldmailfenpei.setPrice(mailFenpei.getPrice());
					oldmailfenpei.setMemo(mailFenpei.getMemo());
					oldmailfenpei.setBsndate(DateUtil.parse(mailFenpei
							.getBsndatestr()
							+ " 00:00:00", DateUtil.DATE_TIME_FORMAT));
					reportService.updateMailFenpei(oldmailfenpei);

				}

				YearSmy smy3 = new YearSmy();
				smy3.setReportdate(DateUtil.parse(mailFenpei.getBsndatestr()
						+ " 00:00:00", DateUtil.DATE_TIME_FORMAT));
				List<YearSmy> ysList2 = reportService.getYearSmy(smy3);

				if (ysList2 != null && ysList2.size() == 1) {

					YearSmy yearSmy2 = ysList.get(0);

					yearSmy2.setWnum(yearSmy2.getWnum() + mailFenpei.getNum());
					yearSmy2.setWprice(yearSmy2.getWprice()
							+ mailFenpei.getPrice());
					double tinprice = yearSmy2.getZprice()
							+ yearSmy2.getWprice() + yearSmy2.getYprice();
					yearSmy2.setTinprice(tinprice);
					double tpprice = yearSmy2.getTinprice()
							- yearSmy2.getToutprice();

					yearSmy2.setTinprice(tinprice);
					yearSmy2.setTpprice(tpprice);

					reportService.updateYearSmy(yearSmy2);

				} else {

					YearSmy ys = new YearSmy();
					ys.setReportdate(mailFenpei.getBsndate());
					ys.setYear(mailFenpei.getYear());
					ys.setMonth(mailFenpei.getMonth());
					ys.setWnum(mailFenpei.getNum());
					ys.setZnum(0);
					ys.setYnum(0);
					ys.setWprice(mailFenpei.getPrice());
					ys.setZprice(0);
					ys.setYprice(0);

					ys.setYfkprice(0);
					ys.setZpprice(0);
					ys.setGsprice(0);
					ys.setGzprice(0);
					ys.setQtprice(0);

					ys.setTinprice(mailFenpei.getPrice());
					ys.setToutprice(0);
					ys.setTpprice(mailFenpei.getPrice());
					ys.setSkprice(0);

					reportService.createYearSmy(ys);

				}

			}

		}

		this.addErrorMessage("hello", "更新成功");

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		mailinfoid = request.getParameter("mailinfoid");
		p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setId(Integer.parseInt(mailinfoid));
		mailFenpeiList = reportService.getMailFenpeiList(p);

		if (mailFenpeiList != null && mailFenpeiList.size() == 1)
			mailFenpei = mailFenpeiList.get(0);

		String pageNo = request.getParameter("pageNo");

		if (pageNo == null)
			yearSmy.setPageNo(1);
		else
			yearSmy.setPageNo(Integer.parseInt(pageNo));

		return "showupdatemailinfopage";
	}

	public String searchMailInfoPage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);
		if (mailFenpei == null)
			mailFenpei = new MailFenpei();

		mailFenpei.setBsndate(new Date());

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManager(yearSmy.getManager());
		p.setType(yearSmy.getType());
		Integer recordNum = reportService.getMailFenpeiListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManager(yearSmy.getManager());
		p.setType(yearSmy.getType());
		mailFenpeiList = reportService.getMailFenpeiList(p);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());

		return "showimportmailinfopage";

	}

	public String exportMailInfo() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		Integer year = yearSmy.getYear();
		Integer month = yearSmy.getMonth();

		String outfile = this.getEnv("downpath") + "/" + year == null ? "全部"
				: year + "-" + month == null ? "全部" : month + "_客服赔款.xlsx";
		try {

			PageParameter p = new PageParameter();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(yearSmy.getYear());
			p.setMonth(yearSmy.getMonth());
			p.setManager(yearSmy.getManager());
			p.setType(yearSmy.getType());
			p.setState("normal");
			mailFenpeiList = reportService.getMailFenpeiList(p);

			int recordnum = 0;
			if (mailFenpeiList != null && mailFenpeiList.size() > 0)
				recordnum = mailFenpeiList.size();

			Excel2007WriterMailPriceDetail writer = new Excel2007WriterMailPriceDetail();
			writer.createSheet("面单收入");
			writer.process(outfile, mailFenpeiList, 0, recordnum);

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
					+ new String((year + "-" + month + "_面单收入.xlsx")
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

		return IMPORT_PAGE;

	}

	public String showImportPeikuanOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());

		if (yearSmy == null)
			yearSmy = new YearSmy();

		yearSmy.setYear(Integer.parseInt(bsndatestr.substring(0, 4)));
		yearSmy.setMonth(Integer.parseInt(bsndatestr.substring(5, 7)));
		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PeikuanOutcome p = new PeikuanOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		int recordNum = 0;
		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0)
			recordNum = peikuanOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PeikuanOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		return "showimportpeikuanoutcomepage";

	}

	public String showUpdatePeikuanOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		String outcomeid = request.getParameter("outcomeid");
		String pageNo = request.getParameter("pageNo");

		if (pageNo == null)
			yearSmy.setPageNo(1);
		else
			yearSmy.setPageNo(Integer.parseInt(pageNo));

		PeikuanOutcome ps = new PeikuanOutcome();
		ps.setOutcomeid(Integer.parseInt(outcomeid));
		ps.setStart(0);
		ps.setSize(Integer.MAX_VALUE);
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(ps);

		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0)
			peikuanOutcome = peikuanOutcomeList.get(0);

		return "showupdatepeikuanoutcomepage";

	}

	public String deletePeikuanOutcomePage() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		String outcomeid = request.getParameter("outcomeid");

		PeikuanOutcome ps = new PeikuanOutcome();
		ps.setOutcomeid(Integer.parseInt(outcomeid));
		ps.setStart(0);
		ps.setSize(Integer.MAX_VALUE);
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(ps);
		PeikuanOutcome oldpeikuan = peikuanOutcomeList.get(0);

		reportService.deletePeikuanOutcome(ps);

		this.addErrorMessage("hello", "删除成功！");
		PeikuanOutcome p = new PeikuanOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(oldpeikuan.getYear());
		p.setMonth(oldpeikuan.getMonth());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		int recordNum = 0;

		double totalprice = 0;

		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0) {
			recordNum = peikuanOutcomeList.size();

			for (int i = 0; i < recordNum; i++) {

				PeikuanOutcome pout = peikuanOutcomeList.get(i);
				totalprice = totalprice + pout.getYingpeiprice();
			}

		}

		Date bsn = DateUtil.parse(oldpeikuan.getYear() + "-"
				+ oldpeikuan.getMonth() + "-01  00:00:00",
				DateUtil.DATE_TIME_FORMAT);

		YearSmy smy = new YearSmy();
		smy.setReportdate(bsn);
		List<YearSmy> ysList = reportService.getYearSmy(smy);
		if (ysList != null && ysList.size() > 0) {
			YearSmy yearSmy2 = ysList.get(0);

			yearSmy2.setPkprice(totalprice);
			double toutprice = yearSmy2.getYfkprice() + yearSmy2.getZpprice()
					+ yearSmy2.getGsprice() + yearSmy2.getGzprice()
					+ yearSmy2.getFkprice() + yearSmy2.getPkprice()
					+ yearSmy2.getQtprice();
			yearSmy2.setToutprice(toutprice);
			double tpprice = yearSmy2.getTinprice() - yearSmy2.getToutprice();
			yearSmy2.setToutprice(toutprice);
			yearSmy2.setTpprice(tpprice);

			reportService.updateYearSmy(yearSmy2);

		} else {

			YearSmy ys = new YearSmy();
			ys.setReportdate(bsn);
			ys.setYear(oldpeikuan.getYear());
			ys.setMonth(oldpeikuan.getMonth());
			ys.setWnum(0);
			ys.setZnum(0);
			ys.setYnum(0);
			ys.setWprice(0);
			ys.setZprice(0);
			ys.setYprice(0);

			ys.setYfkprice(0);
			ys.setZpprice(0);
			ys.setGsprice(0);
			ys.setGzprice(0);
			ys.setQtprice(0);
			ys.setFkprice(0);
			ys.setPkprice(totalprice);

			ys.setTinprice(0);
			ys.setToutprice(totalprice);
			ys.setTpprice(0 - totalprice);

			reportService.createYearSmy(ys);
		}

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new PeikuanOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManagerid(yearSmy.getManager());
		p.setMailid(yearSmy.getMailid());
		p.setDealstate(yearSmy.getState());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		recordNum = 0;
		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0)
			recordNum = peikuanOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PeikuanOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManagerid(yearSmy.getManager());
		p.setMailid(yearSmy.getMailid());
		p.setDealstate(yearSmy.getState());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		return "showimportpeikuanoutcomepage";

	}

	public String searchPeikuanOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PeikuanOutcome p = new PeikuanOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManagerid(yearSmy.getManager());
		p.setMailid(yearSmy.getMailid());
		p.setDealstate(yearSmy.getState());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		int recordNum = 0;
		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0)
			recordNum = peikuanOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PeikuanOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManagerid(yearSmy.getManager());
		p.setMailid(yearSmy.getMailid());
		p.setDealstate(yearSmy.getState());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		return "showimportpeikuanoutcomepage";

	}

	public String exportPeikuanOutcome() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		Integer year = yearSmy.getYear();
		Integer month = yearSmy.getMonth();
		String manager = "";
		if (yearSmy.getManager() == null || yearSmy.getManager().equals("")) {
			manager = null;
		} else {
			manager = new String(yearSmy.getManager().getBytes("ISO8859-1"),
					"utf-8");

		}
		String outfile = this.getEnv("downpath") + "/" + year == null ? "全部"
				: year + "-" + month == null ? "全部" : month + "_客服赔款.xlsx";
		try {

			PeikuanOutcome p = new PeikuanOutcome();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(yearSmy.getYear());
			p.setMonth(yearSmy.getMonth());
			p.setManager(manager);
			peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

			int recordnum = 0;
			if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0)
				recordnum = peikuanOutcomeList.size();

			peikuanOutcomeSmyList = reportService.getPeikuanOutcomeSmyList(p);

			Excel2007WriterPeikuanOutcome writer = new Excel2007WriterPeikuanOutcome();
			writer.createSheet();
			writer.process(outfile, peikuanOutcomeList, peikuanOutcomeSmyList,
					0, recordnum);
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
					+ new String((year + "-" + month + "_客户赔款.xlsx")
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

		return IMPORT_PAGE;

	}

	public String updatePeikuanOutcome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		PeikuanOutcome p = new PeikuanOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setOutcomeid(peikuanOutcome.getOutcomeid());
		List<PeikuanOutcome> oldpeikuanOutcomeList = reportService
				.getPeikuanOutcomeList(p);

		PeikuanOutcome oldpeikuan = oldpeikuanOutcomeList.get(0);
		Integer oldyear = oldpeikuan.getYear();
		Integer oldmonth = oldpeikuan.getMonth();

		PeikuanOutcome go = new PeikuanOutcome();

		Date bsndate = DateUtil.parse(peikuanOutcome.getBsndatestr()
				+ " 00:00:00", DateUtil.DATE_TIME_FORMAT);

		int year = Integer.parseInt(peikuanOutcome.getBsndatestr().substring(0,
				4));
		int month = Integer.parseInt(peikuanOutcome.getBsndatestr().substring(
				5, 7));

		String managerid[] = peikuanOutcome.getManagerid().split("@");

		go.setManagerid(managerid[0]);
		go.setManagername(managerid[1]);
		go.setYear(year);
		go.setMonth(month);
		go.setBsndate(bsndate);
		go.setYingpeiprice(peikuanOutcome.getYingpeiprice());
		go.setMailid(peikuanOutcome.getMailid());
		go.setDealstate(peikuanOutcome.getDealstate());
		go.setDest(peikuanOutcome.getDest());
		go.setReason(peikuanOutcome.getReason());
		go.setMemo(peikuanOutcome.getMemo());
		go.setRealpeiprice(peikuanOutcome.getRealpeiprice());
		go.setOutcomeid(peikuanOutcome.getOutcomeid());
		reportService.updatePeikuanOutcome(go);

		this.addErrorMessage("hello", "修改成功！");

		if (oldyear.intValue() == year && oldmonth == month) {

			p = new PeikuanOutcome();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);
			peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

			int recordNum = 0;

			double totalprice = 0;

			if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0) {
				recordNum = peikuanOutcomeList.size();

				for (int i = 0; i < recordNum; i++) {

					PeikuanOutcome pout = peikuanOutcomeList.get(i);
					totalprice = totalprice + pout.getYingpeiprice();
				}

			}

			Date bsn = DateUtil.parse(year + "-" + month + "-01  00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			YearSmy smy = new YearSmy();
			smy.setReportdate(bsn);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				YearSmy yearSmy2 = ysList.get(0);

				yearSmy2.setPkprice(totalprice);
				double toutprice = yearSmy2.getYfkprice()
						+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
						+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
						+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
				yearSmy2.setToutprice(toutprice);
				double tpprice = yearSmy2.getTinprice()
						- yearSmy2.getToutprice();
				yearSmy2.setToutprice(toutprice);
				yearSmy2.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy2);

			}

		} else {

			p = new PeikuanOutcome();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(oldyear);
			p.setMonth(oldmonth);
			peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

			int recordNum = 0;

			double totalprice = 0;

			if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0) {
				recordNum = peikuanOutcomeList.size();

				for (int i = 0; i < recordNum; i++) {

					PeikuanOutcome pout = peikuanOutcomeList.get(i);
					totalprice = totalprice + pout.getYingpeiprice();
				}

			}

			Date bsn = DateUtil.parse(oldyear.intValue() + "-"
					+ oldmonth.intValue() + "-01  00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			YearSmy smy = new YearSmy();
			smy.setReportdate(bsn);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				YearSmy yearSmy2 = ysList.get(0);

				yearSmy2.setPkprice(totalprice);
				double toutprice = yearSmy2.getYfkprice()
						+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
						+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
						+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
				yearSmy2.setToutprice(toutprice);
				double tpprice = yearSmy2.getTinprice()
						- yearSmy2.getToutprice();
				yearSmy2.setToutprice(toutprice);
				yearSmy2.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy2);

			}

			// 新的日期更新
			p = new PeikuanOutcome();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);
			peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

			recordNum = 0;
			totalprice = 0;

			if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0) {
				recordNum = peikuanOutcomeList.size();

				for (int i = 0; i < recordNum; i++) {

					PeikuanOutcome pout = peikuanOutcomeList.get(i);
					totalprice = totalprice + pout.getYingpeiprice();
				}

			}

			Date bsn2 = DateUtil.parse(year + "-" + month + "-01  00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			YearSmy smy2 = new YearSmy();
			smy2.setReportdate(bsn2);
			List<YearSmy> ysList2 = reportService.getYearSmy(smy2);
			if (ysList2 != null && ysList2.size() > 0) {
				YearSmy yearSmy2 = ysList2.get(0);

				yearSmy2.setPkprice(totalprice);
				double toutprice = yearSmy2.getYfkprice()
						+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
						+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
						+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
				yearSmy2.setToutprice(toutprice);
				double tpprice = yearSmy2.getTinprice()
						- yearSmy2.getToutprice();
				yearSmy2.setToutprice(toutprice);
				yearSmy2.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy2);

			} else {

				YearSmy ys = new YearSmy();
				ys.setReportdate(bsn2);
				ys.setYear(year);
				ys.setMonth(month);
				ys.setWnum(0);
				ys.setZnum(0);
				ys.setYnum(0);
				ys.setWprice(0);
				ys.setZprice(0);
				ys.setYprice(0);

				ys.setYfkprice(0);
				ys.setZpprice(0);
				ys.setGsprice(0);
				ys.setGzprice(0);
				ys.setQtprice(0);
				ys.setFkprice(0);
				ys.setPkprice(totalprice);

				ys.setTinprice(0);
				ys.setToutprice(totalprice);
				ys.setTpprice(0 - totalprice);

				reportService.createYearSmy(ys);
			}

		}

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		String pageNo = request.getParameter("pageNo");

		if (pageNo == null)
			yearSmy.setPageNo(1);
		else
			yearSmy.setPageNo(Integer.parseInt(pageNo));

		PeikuanOutcome ps = new PeikuanOutcome();
		ps.setOutcomeid(peikuanOutcome.getOutcomeid());
		ps.setStart(0);
		ps.setSize(Integer.MAX_VALUE);
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(ps);

		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0)
			peikuanOutcome = peikuanOutcomeList.get(0);

		return "showupdatepeikuanoutcomepage";

	}

	public String createPeikuanOutcome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		Integer year = null;
		Integer month = null;

		PeikuanOutcome p = new PeikuanOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setMailid(peikuanOutcome.getMailid());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0) {

			this.addErrorMessage("hello", peikuanOutcome.getMailid()
					+ "该单号已经录入！");

		} else {

			PeikuanOutcome go = new PeikuanOutcome();

			Date bsndate = DateUtil.parse(peikuanOutcome.getBsndatestr()
					+ " 00:00:00", DateUtil.DATE_TIME_FORMAT);

			year = Integer.parseInt(peikuanOutcome.getBsndatestr().substring(0,
					4));
			month = Integer.parseInt(peikuanOutcome.getBsndatestr().substring(
					5, 7));

			String managerid[] = peikuanOutcome.getManagerid().split("@");

			go.setManagerid(managerid[0]);
			go.setManagername(managerid[1]);
			go.setYear(year);
			go.setMonth(month);
			go.setBsndate(bsndate);
			go.setYingpeiprice(peikuanOutcome.getYingpeiprice());
			go.setMailid(peikuanOutcome.getMailid());
			go.setDealstate(peikuanOutcome.getDealstate());
			go.setDest(peikuanOutcome.getDest());
			go.setReason(peikuanOutcome.getReason());
			go.setMemo(peikuanOutcome.getMemo());
			go.setRealpeiprice(peikuanOutcome.getRealpeiprice());
			reportService.createPeikuanOutcome(go);

			this.addErrorMessage("hello", "创建成功！");

			p = new PeikuanOutcome();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);
			peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

			int recordNum = 0;

			double totalprice = 0;

			if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0) {
				recordNum = peikuanOutcomeList.size();

				for (int i = 0; i < recordNum; i++) {

					PeikuanOutcome pout = peikuanOutcomeList.get(i);
					totalprice = totalprice + pout.getYingpeiprice();
				}

			}

			Date bsn = DateUtil.parse(year + "-" + month + "-01  00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			YearSmy smy = new YearSmy();
			smy.setReportdate(bsn);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				YearSmy yearSmy2 = ysList.get(0);

				yearSmy2.setPkprice(totalprice);
				double toutprice = yearSmy2.getYfkprice()
						+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
						+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
						+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
				yearSmy2.setToutprice(toutprice);
				double tpprice = yearSmy2.getTinprice()
						- yearSmy2.getToutprice();
				yearSmy2.setToutprice(toutprice);
				yearSmy2.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy2);

			} else {

				YearSmy ys = new YearSmy();
				ys.setReportdate(bsndate);
				ys.setYear(year);
				ys.setMonth(month);
				ys.setWnum(0);
				ys.setZnum(0);
				ys.setYnum(0);
				ys.setWprice(0);
				ys.setZprice(0);
				ys.setYprice(0);

				ys.setYfkprice(0);
				ys.setZpprice(0);
				ys.setGsprice(0);
				ys.setGzprice(0);
				ys.setQtprice(0);
				ys.setFkprice(0);
				ys.setPkprice(totalprice);

				ys.setTinprice(0);
				ys.setToutprice(totalprice);
				ys.setTpprice(0 - totalprice);

				reportService.createYearSmy(ys);
			}

		}
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		yearSmy = new YearSmy();
		yearSmy.setYear(Integer.parseInt(bsndatestr.substring(0, 4)));
		yearSmy.setMonth(Integer.parseInt(bsndatestr.substring(5, 7)));

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new PeikuanOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		int recordNum = 0;
		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0)
			recordNum = peikuanOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PeikuanOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		peikuanOutcomeList = reportService.getPeikuanOutcomeList(p);

		return "showimportpeikuanoutcomepage";

	}

	public String showImportZhipaoOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		yearSmy = new YearSmy();
		yearSmy.setYear(Integer.parseInt(bsndatestr.substring(0, 4)));
		yearSmy.setMonth(Integer.parseInt(bsndatestr.substring(5, 7)));
		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		ZhipaoOutcome p = new ZhipaoOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		int recordNum = 0;
		if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0)
			recordNum = zhipaoOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new ZhipaoOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		return "showimportzhipaooutcomepage";

	}

	public String createZhipaoOutcome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ZhipaoOutcome go = new ZhipaoOutcome();

		Date bsndate = DateUtil.parse(zhipaoOutcome.getBsndatestr()
				+ " 00:00:00", DateUtil.DATE_TIME_FORMAT);

		// 直跑可能是一天一条

		int year = Integer.parseInt(zhipaoOutcome.getBsndatestr().substring(0,
				4));
		int month = Integer.parseInt(zhipaoOutcome.getBsndatestr().substring(5,
				7));

		String managerid[] = zhipaoOutcome.getManagerid().split("@");

		go.setManagerid(managerid[0]);
		go.setManagername(managerid[1]);
		go.setYear(year);
		go.setMonth(month);
		go.setBsndate(bsndate);
		go.setPrice(zhipaoOutcome.getPrice());
		go.setMemo(zhipaoOutcome.getMemo());
		go.setWeight(zhipaoOutcome.getWeight());
		go.setJipaoweight(zhipaoOutcome.getJipaoweight());
		go.setTotalweight(zhipaoOutcome.getTotalweight());
		go.setShouldincome(zhipaoOutcome.getShouldincome());
		go.setTuikuan(zhipaoOutcome.getTuikuan());
		go.setRealincome(zhipaoOutcome.getRealincome());
		reportService.createZhipaoOutcome(go);

		double totalprice = 0;
		ZhipaoOutcome p = new ZhipaoOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0) {
			int len = zhipaoOutcomeList.size();

			for (int i = 0; i < len; i++) {

				totalprice = totalprice
						+ zhipaoOutcomeList.get(i).getShouldincome();
			}

			Date bsn = DateUtil.parse(year + "-" + month + "-01  00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			YearSmy smy = new YearSmy();
			smy.setReportdate(bsn);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				YearSmy yearSmy2 = ysList.get(0);

				yearSmy2.setZpprice(totalprice);
				double toutprice = yearSmy2.getYfkprice()
						+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
						+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
						+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
				yearSmy2.setToutprice(toutprice);
				double tpprice = yearSmy2.getTinprice()
						- yearSmy2.getToutprice();
				yearSmy2.setToutprice(toutprice);
				yearSmy2.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy2);

			} else {

				YearSmy ys = new YearSmy();
				ys.setReportdate(bsn);
				ys.setYear(year);
				ys.setMonth(month);
				ys.setWnum(0);
				ys.setZnum(0);
				ys.setYnum(0);
				ys.setWprice(0);
				ys.setZprice(0);
				ys.setYprice(0);

				ys.setYfkprice(0);
				ys.setZpprice(totalprice);
				ys.setGsprice(0);
				ys.setGzprice(0);
				ys.setQtprice(0);

				ys.setTinprice(0);
				ys.setToutprice(totalprice);
				ys.setTpprice(0 - totalprice);

				reportService.createYearSmy(ys);
			}
		}

		this.addErrorMessage("hello", "创建成功！");
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new ZhipaoOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		int recordNum = 0;
		if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0)
			recordNum = zhipaoOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new ZhipaoOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		return "showimportzhipaooutcomepage";

	}

	public String deleteZhipaoOutcome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String outcomeid = request.getParameter("outcomeid");

		ZhipaoOutcome p = new ZhipaoOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setOutcomeid(Integer.parseInt(outcomeid));
		List<ZhipaoOutcome> zhipaoOutcomeList2 = reportService
				.getZhipaoOutcomeList(p);

		ZhipaoOutcome old = zhipaoOutcomeList2.get(0);

		ZhipaoOutcome go = new ZhipaoOutcome();
		go.setOutcomeid(Integer.parseInt(outcomeid));
		reportService.deleteZhipaoOutcome(go);

		double totalprice = 0;
		p = new ZhipaoOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(old.getYear());
		p.setMonth(old.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0) {
			int len = zhipaoOutcomeList.size();

			for (int i = 0; i < len; i++) {

				totalprice = totalprice
						+ zhipaoOutcomeList.get(i).getShouldincome();
			}

			Date bsn = DateUtil.parse(old.getYear() + "-" + old.getMonth()
					+ "-01  00:00:00", DateUtil.DATE_TIME_FORMAT);

			YearSmy smy = new YearSmy();
			smy.setReportdate(bsn);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				YearSmy yearSmy2 = ysList.get(0);

				yearSmy2.setZpprice(totalprice);
				double toutprice = yearSmy2.getYfkprice()
						+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
						+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
						+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
				yearSmy2.setToutprice(toutprice);
				double tpprice = yearSmy2.getTinprice()
						- yearSmy2.getToutprice();
				yearSmy2.setToutprice(toutprice);
				yearSmy2.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy2);

			}
		}

		this.addErrorMessage("hello", "删除成功！");

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageno");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new ZhipaoOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		int recordNum = 0;
		if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0)
			recordNum = zhipaoOutcomeList.size();

		if ((pageN - 1) * pageSize == recordNum)
			pageN = pageN - 1;

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new ZhipaoOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		return "showimportzhipaooutcomepage";

	}

	public String searchZhipaoOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageNo");
		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		ZhipaoOutcome p = new ZhipaoOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		int recordNum = 0;
		if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0)
			recordNum = zhipaoOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new ZhipaoOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);
		return "showimportzhipaooutcomepage";

	}

	public String exportGongziYearByMonth() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		int year = yearSmy.getYear();

		String outfile = this.getEnv("downpath") + "/" + year + "_公司年工资明细.xlsx";
		try {

			GongziOutcome zp = new GongziOutcome();
			zp.setYear(year);
			List<GongziOutcome> gongziOutcomeList2 = reportService
					.getGongziOutcomeSmyByMonthList(zp);

			int recordnum = 0;
			if (gongziOutcomeList2 != null && gongziOutcomeList2.size() > 0)
				recordnum = gongziOutcomeList2.size();

			Excel2007WriterGongziOutcomeYearByMonth writer = new Excel2007WriterGongziOutcomeYearByMonth();
			writer.createSheet("汇总");
			writer.process2(outfile, gongziOutcomeList2, 0, recordnum);

			for (int i = 0; i < recordnum; i++) {
				GongziOutcome z = gongziOutcomeList2.get(i);
				GongziOutcome gos = new GongziOutcome();
				gos.setYear(z.getYear());
				gos.setMonth(z.getMonth());

				gongziOutcomeList = reportService.getGongziOutcomeList(gos);
				writer.createSheet(z.getYear() + "-" + z.getMonth());
				int len = 0;
				if (gongziOutcomeList != null && gongziOutcomeList.size() > 0)
					len = gongziOutcomeList.size();
				writer.process(outfile, gongziOutcomeList, 0, len);
			}
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
					+ new String((year + "_公司年工资明细.xlsx").getBytes("gb2312"),
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

	public String exportZhichuYearByMonth() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		int year = yearSmy.getYear();

		String outfile = this.getEnv("downpath") + "/" + year + "_公司年支出明细.xlsx";
		try {

			ZhichuOutcome gos = new ZhichuOutcome();
			gos.setYear(yearSmy.getYear());

			List<ZhichuOutcome> zhichuOutcomeList2 = reportService
					.getZhichuOutcomeSmyByMonthList(gos);
			int recordnum = 0;
			if (zhichuOutcomeList2 != null && zhichuOutcomeList2.size() > 0)
				recordnum = zhichuOutcomeList2.size();
			Excel2007WriterZhichuOutcomeYearByMonth writer = new Excel2007WriterZhichuOutcomeYearByMonth();
			writer.createSheet("汇总");
			writer.process(outfile, zhichuOutcomeList2, 0, recordnum);

			for (int i = 0; i < recordnum; i++) {
				ZhichuOutcome z = zhichuOutcomeList2.get(i);
				gos = new ZhichuOutcome();
				gos.setYear(z.getYear());
				gos.setMonth(z.getMonth());
				gos.setStart(0);
				gos.setSize(Integer.MAX_VALUE);
				zhichuOutcomeList = reportService.getZhichuOutcomeList(gos);
				writer.createSheet(z.getYear() + "-" + z.getMonth());
				int len = 0;
				if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0)
					len = zhichuOutcomeList.size();
				writer.process2(outfile, zhichuOutcomeList, 0, len);
			}
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
					+ new String((year + "_公司年支出明细.xlsx").getBytes("gb2312"),
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

	public String exportFankuanYearByMonth() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		int year = yearSmy.getYear();

		String outfile = this.getEnv("downpath") + "/" + year + "_公司年返款明细.xlsx";
		try {

			FankuanOutcome gos = new FankuanOutcome();
			gos.setYear(yearSmy.getYear());

			List<FankuanOutcome> fankuanOutcomeList2 = reportService
					.getFankuanOutcomeSmyByMonthList(gos);
			int recordnum = 0;
			if (fankuanOutcomeList2 != null && fankuanOutcomeList2.size() > 0)
				recordnum = fankuanOutcomeList2.size();
			Excel2007WriterFankuanOutcomeYearByMonth writer = new Excel2007WriterFankuanOutcomeYearByMonth();
			writer.createSheet("汇总");
			writer.process(outfile, fankuanOutcomeList2, 0, recordnum);

			for (int i = 0; i < recordnum; i++) {
				FankuanOutcome z = fankuanOutcomeList2.get(i);
				gos = new FankuanOutcome();
				gos.setYear(z.getYear());
				gos.setMonth(z.getMonth());
				gos.setStart(0);
				gos.setSize(Integer.MAX_VALUE);
				fankuanOutcomeList = reportService.getFankuanOutcomeList(gos);
				writer.createSheet(z.getYear() + "-" + z.getMonth());
				int len = 0;
				if (fankuanOutcomeList != null && fankuanOutcomeList.size() > 0)
					len = fankuanOutcomeList.size();
				writer.process2(outfile, fankuanOutcomeList, 0, len);
			}
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
					+ new String((year + "_公司年返款明细.xlsx").getBytes("gb2312"),
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

	public String exportPeikuanYearByMonth() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		int year = yearSmy.getYear();
		String outfile = this.getEnv("downpath") + "/" + year + "_公司年赔款明细.xlsx";
		try {

			PeikuanOutcome gos = new PeikuanOutcome();
			gos.setYear(yearSmy.getYear());

			List<PeikuanOutcome> peikuanOutcomeList2 = reportService
					.getPeikuanOutcomeSmyByMonthList(gos);
			int recordnum = 0;
			if (peikuanOutcomeList2 != null && peikuanOutcomeList2.size() > 0)
				recordnum = peikuanOutcomeList2.size();

			Excel2007WriterPeikuanOutcomeYearByMonth writer = new Excel2007WriterPeikuanOutcomeYearByMonth();
			writer.createSheet("汇总");
			writer.process(outfile, peikuanOutcomeList2, null, 0, recordnum);

			for (int i = 0; i < recordnum; i++) {
				PeikuanOutcome z = peikuanOutcomeList2.get(i);
				gos = new PeikuanOutcome();
				gos.setYear(z.getYear());
				gos.setMonth(z.getMonth());
				gos.setStart(0);
				gos.setSize(Integer.MAX_VALUE);
				peikuanOutcomeList = reportService.getPeikuanOutcomeList(gos);

				peikuanOutcomeSmyList = reportService
						.getPeikuanOutcomeSmyList(gos);
				writer.createSheet(z.getYear() + "-" + z.getMonth());
				int len = 0;
				if (peikuanOutcomeSmyList != null
						&& peikuanOutcomeSmyList.size() > 0)
					len = peikuanOutcomeSmyList.size();
				writer.process2(outfile, peikuanOutcomeList,
						peikuanOutcomeSmyList, 0, len);
			}
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
					+ new String((year + "_公司年赔款明细.xlsx").getBytes("gb2312"),
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

	public String exportZhipaoYearByMonth() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		int year = yearSmy.getYear();

		String outfile = this.getEnv("downpath") + "/" + year + "_公司年直跑明细.xlsx";
		try {

			ZhipaoOutcome gos = new ZhipaoOutcome();
			gos.setYear(yearSmy.getYear());

			List<ZhipaoOutcome> zhipaoOutcomeList2 = reportService
					.getZhipaoOutcomeSmyByMonthList(gos);
			int recordnum = 0;
			if (zhipaoOutcomeList2 != null && zhipaoOutcomeList2.size() > 0)
				recordnum = zhipaoOutcomeList2.size();
			Excel2007WriterZhipaoOutcomeYearByMonth writer = new Excel2007WriterZhipaoOutcomeYearByMonth();
			writer.createSheet("汇总");
			writer.process(outfile, zhipaoOutcomeList2, 0, recordnum);

			for (int i = 0; i < recordnum; i++) {
				ZhipaoOutcome z = zhipaoOutcomeList2.get(i);
				gos = new ZhipaoOutcome();
				gos.setYear(z.getYear());
				gos.setMonth(z.getMonth());
				gos.setStart(0);
				gos.setSize(Integer.MAX_VALUE);
				zhipaoOutcomeList = reportService.getZhipaoOutcomeList(gos);
				writer.createSheet(z.getYear() + "-" + z.getMonth());
				int len = 0;
				if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0)
					len = zhipaoOutcomeList.size();
				writer.process2(outfile, zhipaoOutcomeList, 0, len);
			}
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
					+ new String((year + "_公司年直跑明细.xlsx").getBytes("gb2312"),
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

	public String exportZhipaoOutcome() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		int year = yearSmy.getYear();
		int month = yearSmy.getMonth();

		String outfile = this.getEnv("downpath") + "/" + year + "-" + month
				+ "_公司直跑明细.xlsx";
		try {

			ZhipaoOutcome gos = new ZhipaoOutcome();
			gos.setYear(yearSmy.getYear());
			gos.setMonth(yearSmy.getMonth());
			gos.setStart(0);
			gos.setSize(Integer.MAX_VALUE);
			zhipaoOutcomeList = reportService.getZhipaoOutcomeList(gos);
			int recordnum = 0;
			if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0)
				recordnum = zhipaoOutcomeList.size();

			Excel2007WriterZhipaoOutcome writer = new Excel2007WriterZhipaoOutcome();
			writer.createSheet();
			writer.process(outfile, zhipaoOutcomeList, 0, recordnum);
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
					+ new String((year + "-" + month + "_公司直跑明细.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportGongziOutcome() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		int year = yearSmy.getYear();
		int month = yearSmy.getMonth();

		String outfile = this.getEnv("downpath") + "/" + year + "-" + month
				+ "_月工资明细.xlsx";
		try {

			GongziOutcome gos = new GongziOutcome();
			gos.setYear(yearSmy.getYear());
			gos.setMonth(yearSmy.getMonth());

			gongziOutcomeList = reportService.getGongziOutcomeList(gos);
			int recordnum = 0;
			if (gongziOutcomeList != null && gongziOutcomeList.size() > 0)
				recordnum = gongziOutcomeList.size();

			Excel2007WriterGongziOutcome writer = new Excel2007WriterGongziOutcome();
			writer.createSheet();
			writer.process(outfile, gongziOutcomeList, 0, recordnum);
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
					+ new String((year + "-" + month + "_月工资明细.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportFankuanOutcome() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		int year = yearSmy.getYear();
		int month = yearSmy.getMonth();

		String outfile = this.getEnv("downpath") + "/" + year + "-" + month
				+ "_月返款明细.xlsx";
		try {

			FankuanOutcome gos = new FankuanOutcome();
			gos.setYear(yearSmy.getYear());
			gos.setMonth(yearSmy.getMonth());

			fankuanOutcomeList = reportService.getFankuanOutcomeList(gos);
			int recordnum = 0;
			if (fankuanOutcomeList != null && fankuanOutcomeList.size() > 0)
				recordnum = fankuanOutcomeList.size();

			Excel2007WriterFankuanOutcome writer = new Excel2007WriterFankuanOutcome();
			writer.createSheet();
			writer.process(outfile, fankuanOutcomeList, 0, recordnum);
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
					+ new String((year + "-" + month + "_月返款明细.xlsx")
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

		return IMPORT_PAGE;
	}

	public String showImportFankuanOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		if (yearSmy == null)
			yearSmy = new YearSmy();

		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);

		Date lastmonth = c.getTime();

		yearSmy.setYear(lastmonth.getYear() + 1900);
		yearSmy.setMonth(lastmonth.getMonth() + 1);

		FankuanOutcome gos = new FankuanOutcome();
		gos.setYear(yearSmy.getYear());
		gos.setMonth(yearSmy.getMonth());
		fankuanOutcomeList = reportService.getFankuanOutcomeList(gos);

		return "showimportfankuanoutcomepage";

	}

	public String createFankuanOutcome() throws ParseException {

		FankuanOutcome gos = new FankuanOutcome();
		gos.setYear(yearSmy.getYear());
		gos.setMonth(yearSmy.getMonth());
		fankuanOutcomeList = reportService.getFankuanOutcomeList(gos);

		if (fankuanOutcomeList != null && fankuanOutcomeList.size() > 0) {

			reportService.deleteFankuanOutcome(gos);

		}

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		double totalprice = 0;
		if (managerInfoList != null && managerInfoList.size() > 0) {

			int len = managerInfoList.size();

			for (int i = 0; i < len; i++) {
				ManagerInfo manager = managerInfoList.get(i);
				FankuanOutcome go = new FankuanOutcome();
				go.setManagerid(manager.getManagerid());
				go.setManagername(manager.getManagername());
				go.setYear(yearSmy.getYear());
				go.setMonth(yearSmy.getMonth());
				go.setBsndate(new Date());
				reportService.createFankuanOutcome(go);

			}

			Date bsndate = DateUtil.parse(yearSmy.getYear() + "-"
					+ yearSmy.getMonth() + "-01  00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			YearSmy smy = new YearSmy();
			smy.setReportdate(bsndate);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				yearSmy = ysList.get(0);

				yearSmy.setFkprice(totalprice);
				double toutprice = yearSmy.getYfkprice() + yearSmy.getZpprice()
						+ yearSmy.getGsprice() + yearSmy.getGzprice()
						+ yearSmy.getFkprice() + yearSmy.getPkprice()
						+ yearSmy.getQtprice();
				yearSmy.setToutprice(toutprice);
				double tpprice = yearSmy.getTinprice() - yearSmy.getToutprice();
				yearSmy.setToutprice(toutprice);
				yearSmy.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy);

			} else {

				YearSmy ys = new YearSmy();
				ys.setReportdate(bsndate);
				ys.setYear(yearSmy.getYear());
				ys.setMonth(yearSmy.getMonth());
				ys.setWnum(0);
				ys.setZnum(0);
				ys.setYnum(0);
				ys.setWprice(0);
				ys.setZprice(0);
				ys.setYprice(0);

				ys.setYfkprice(0);
				ys.setZpprice(0);
				ys.setGsprice(0);
				ys.setGzprice(0);
				ys.setQtprice(0);
				ys.setFkprice(totalprice);
				ys.setPkprice(0);

				ys.setTinprice(0);
				ys.setToutprice(totalprice);
				ys.setTpprice(0 - totalprice);

				reportService.createYearSmy(ys);
			}

			this.addErrorMessage("hello", "创建成功！");
			gos = new FankuanOutcome();
			gos.setYear(yearSmy.getYear());
			gos.setMonth(yearSmy.getMonth());
			fankuanOutcomeList = reportService.getFankuanOutcomeList(gos);

		}

		return "showimportfankuanoutcomepage";

	}

	public String searchFankuanOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		FankuanOutcome gos = new FankuanOutcome();
		gos.setYear(yearSmy.getYear());
		gos.setMonth(yearSmy.getMonth());
		fankuanOutcomeList = reportService.getFankuanOutcomeList(gos);

		return "showimportfankuanoutcomepage";

	}

	public String showImportGongziOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		if (yearSmy == null)
			yearSmy = new YearSmy();

		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);

		Date lastmonth = c.getTime();

		yearSmy.setYear(lastmonth.getYear() + 1900);
		yearSmy.setMonth(lastmonth.getMonth() + 1);

		GongziOutcome gos = new GongziOutcome();
		gos.setYear(yearSmy.getYear());
		gos.setMonth(yearSmy.getMonth());
		gongziOutcomeList = reportService.getGongziOutcomeList(gos);

		return "showimportgongzioutcomepage";

	}

	public String searchGongziOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		GongziOutcome gos = new GongziOutcome();
		gos.setYear(yearSmy.getYear());
		gos.setMonth(yearSmy.getMonth());
		gongziOutcomeList = reportService.getGongziOutcomeList(gos);

		return "showimportgongzioutcomepage";

	}

	public String createGongziOutcome() throws ParseException {

		GongziOutcome gos = new GongziOutcome();
		gos.setYear(yearSmy.getYear());
		gos.setMonth(yearSmy.getMonth());
		gongziOutcomeList = reportService.getGongziOutcomeList(gos);

		if (gongziOutcomeList != null && gongziOutcomeList.size() > 0) {

			reportService.deleteGongziOutcome(gos);

		}

		double totalprice = 0;
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("employee");
		m.setState("在职");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		if (managerInfoList != null && managerInfoList.size() > 0) {

			int len = managerInfoList.size();

			for (int i = 0; i < len; i++) {
				ManagerInfo manager = managerInfoList.get(i);
				GongziOutcome go = new GongziOutcome();
				go.setManagerid(manager.getManagerid());
				go.setBasicsalary(manager.getBasicsalary());
				go.setCometime(manager.getCometime());
				go.setOuttime(manager.getOuttime());
				go.setSalary(manager.getBasicsalary());
				go.setYear(yearSmy.getYear());
				go.setMonth(yearSmy.getMonth());
				go.setBsndate(new Date());
				reportService.createGongziOutcome(go);
				totalprice = totalprice + manager.getBasicsalary();

			}

			gos = new GongziOutcome();
			gos.setYear(yearSmy.getYear());
			gos.setMonth(yearSmy.getMonth());
			gongziOutcomeList = reportService.getGongziOutcomeList(gos);

		}

		Date bsndate = DateUtil.parse(yearSmy.getYear() + "-"
				+ yearSmy.getMonth() + "-01  00:00:00",
				DateUtil.DATE_TIME_FORMAT);

		YearSmy smy = new YearSmy();
		smy.setReportdate(bsndate);
		List<YearSmy> ysList = reportService.getYearSmy(smy);
		if (ysList != null && ysList.size() > 0) {
			yearSmy = ysList.get(0);

			yearSmy.setGzprice(totalprice);
			double toutprice = yearSmy.getYfkprice() + yearSmy.getZpprice()
					+ yearSmy.getGsprice() + yearSmy.getGzprice()
					+ yearSmy.getFkprice() + yearSmy.getPkprice()
					+ yearSmy.getQtprice();
			yearSmy.setToutprice(toutprice);
			double tpprice = yearSmy.getTinprice() - yearSmy.getToutprice();
			yearSmy.setToutprice(toutprice);
			yearSmy.setTpprice(tpprice);

			reportService.updateYearSmy(yearSmy);

		} else {

			YearSmy ys = new YearSmy();
			ys.setReportdate(bsndate);
			ys.setYear(yearSmy.getYear());
			ys.setMonth(yearSmy.getMonth());
			ys.setWnum(0);
			ys.setZnum(0);
			ys.setYnum(0);
			ys.setWprice(0);
			ys.setZprice(0);
			ys.setYprice(0);

			ys.setYfkprice(0);
			ys.setZpprice(0);
			ys.setGsprice(0);
			ys.setGzprice(totalprice);
			ys.setQtprice(0);

			ys.setTinprice(0);
			ys.setToutprice(totalprice);
			ys.setTpprice(0 - totalprice);

			reportService.createYearSmy(ys);
		}

		this.addErrorMessage("hello", "创建成功！");
		return "showimportgongzioutcomepage";

	}

	public String deleteZhichuOutcome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String outcomeid = request.getParameter("outcomeid");

		ZhichuOutcome p = new ZhichuOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setOutcomeid(Integer.parseInt(outcomeid));
		List<ZhichuOutcome> zhichuOutcomeList2 = reportService
				.getZhichuOutcomeList(p);

		ZhichuOutcome old = zhichuOutcomeList2.get(0);

		ZhichuOutcome go = new ZhichuOutcome();
		go.setOutcomeid(Integer.parseInt(outcomeid));
		reportService.deleteZhichuOutcome(go);

		double totalprice = 0;
		p = new ZhichuOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(old.getYear());
		p.setMonth(old.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0) {
			int len = zhichuOutcomeList.size();

			for (int i = 0; i < len; i++) {

				totalprice = totalprice + zhichuOutcomeList.get(i).getPrice();
			}

			Date bsn = DateUtil.parse(old.getYear() + "-" + old.getMonth()
					+ "-01  00:00:00", DateUtil.DATE_TIME_FORMAT);

			YearSmy smy = new YearSmy();
			smy.setReportdate(bsn);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				YearSmy yearSmy2 = ysList.get(0);

				yearSmy2.setGsprice(totalprice);
				double toutprice = yearSmy2.getYfkprice()
						+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
						+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
						+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
				yearSmy2.setToutprice(toutprice);
				double tpprice = yearSmy2.getTinprice()
						- yearSmy2.getToutprice();
				yearSmy2.setToutprice(toutprice);
				yearSmy2.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy2);

			}
		}
		this.addErrorMessage("hello", "删除成功！");

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageno");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new ZhichuOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		int recordNum = 0;
		if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0)
			recordNum = zhichuOutcomeList.size();

		if ((pageN - 1) * pageSize == recordNum)
			pageN = pageN - 1;

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new ZhichuOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		return "showimportzhichuoutcomepage";

	}

	// 支出界面
	public String searchZhichuOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageNo");
		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		ZhichuOutcome p = new ZhichuOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		int recordNum = 0;
		if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0)
			recordNum = zhichuOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new ZhichuOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		return "showimportzhichuoutcomepage";

	}

	public String createZhichuOutcome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ZhichuOutcome go = new ZhichuOutcome();

		Date bsndate = DateUtil.parse(zhichuOutcome.getBsndatestr()
				+ " 00:00:00", DateUtil.DATE_TIME_FORMAT);

		int year = Integer.parseInt(zhichuOutcome.getBsndatestr().substring(0,
				4));
		int month = Integer.parseInt(zhichuOutcome.getBsndatestr().substring(5,
				7));

		String managerid[] = zhichuOutcome.getManagerid().split("@");

		go.setManagerid(managerid[0]);
		go.setManagername(managerid[1]);
		go.setYear(year);
		go.setMonth(month);
		go.setBsndate(bsndate);
		go.setPrice(zhichuOutcome.getPrice());
		go.setPricetype(zhichuOutcome.getPricetype());
		go.setMemo(zhichuOutcome.getMemo());
		reportService.createZhichuOutcome(go);

		double totalprice = 0;
		ZhichuOutcome p = new ZhichuOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0) {
			int len = zhichuOutcomeList.size();

			for (int i = 0; i < len; i++) {

				totalprice = totalprice + zhichuOutcomeList.get(i).getPrice();
			}

			Date bsn = DateUtil.parse(year + "-" + month + "-01  00:00:00",
					DateUtil.DATE_TIME_FORMAT);

			YearSmy smy = new YearSmy();
			smy.setReportdate(bsn);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				YearSmy yearSmy2 = ysList.get(0);

				yearSmy2.setGsprice(totalprice);
				double toutprice = yearSmy2.getYfkprice()
						+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
						+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
						+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
				yearSmy2.setToutprice(toutprice);
				double tpprice = yearSmy2.getTinprice()
						- yearSmy2.getToutprice();
				yearSmy2.setToutprice(toutprice);
				yearSmy2.setTpprice(tpprice);

				reportService.updateYearSmy(yearSmy2);

			} else {

				YearSmy ys = new YearSmy();
				ys.setReportdate(bsn);
				ys.setYear(year);
				ys.setMonth(month);
				ys.setWnum(0);
				ys.setZnum(0);
				ys.setYnum(0);
				ys.setWprice(0);
				ys.setZprice(0);
				ys.setYprice(0);

				ys.setYfkprice(0);
				ys.setZpprice(0);
				ys.setGsprice(totalprice);
				ys.setGzprice(0);
				ys.setQtprice(0);

				ys.setTinprice(0);
				ys.setToutprice(totalprice);
				ys.setTpprice(0 - totalprice);

				reportService.createYearSmy(ys);
			}
		}

		this.addErrorMessage("hello", "创建成功！");

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageNo");

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());

		if (yearSmy == null)
			yearSmy = new YearSmy();

		yearSmy.setYear(Integer.parseInt(bsndatestr.substring(0, 4)));
		yearSmy.setMonth(Integer.parseInt(bsndatestr.substring(5, 7)));

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new ZhichuOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		int recordNum = 0;
		if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0)
			recordNum = zhichuOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new ZhichuOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		return "showimportzhichuoutcomepage";

	}

	// 支出界面
	public String showImportZhichuOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		if (yearSmy == null)
			yearSmy = new YearSmy();
		yearSmy.setYear(Integer.parseInt(bsndatestr.substring(0, 4)));
		yearSmy.setMonth(Integer.parseInt(bsndatestr.substring(5, 7)));

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		ZhichuOutcome p = new ZhichuOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		int recordNum = 0;
		if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0)
			recordNum = zhichuOutcomeList.size();

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new ZhichuOutcome();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		return "showimportzhichuoutcomepage";

	}

	
	// 支出界面
	public String showUpdateZhichuOutcomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		
		String pageNo = request.getParameter("pageNo");
	 

		if (pageNo == null)
			yearSmy.setPageNo(1);
		else
			yearSmy.setPageNo(Integer.parseInt(pageNo));
		
		String outcomeid = request.getParameter("outcomeid");
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);
   
    	ZhichuOutcome p = new ZhichuOutcome();
  
		p.setStart(0);
		p.setSize(pageSize);
		p.setOutcomeid(Integer.parseInt(outcomeid));
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);
		
		if(zhichuOutcomeList!=null&&zhichuOutcomeList.size()==1){
			
			zhichuOutcome=zhichuOutcomeList.get(0);
			
		}else this.addErrorMessage("hello", "公司支出数据错误！");

		return "showupdatezhichuoutcomepage";

	}
	
	public String updateZhichuOutcome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		//String outcomeid = request.getParameter("outcomeid");
		
		String pageNo = request.getParameter("pageNo");
	 

		if (pageNo == null)
			yearSmy.setPageNo(1);
		else
			yearSmy.setPageNo(Integer.parseInt(pageNo));
		
		Integer outcomeid=zhichuOutcome.getOutcomeid();
		ZhichuOutcome oldzhichuOutcome=null;
		ZhichuOutcome   p = new ZhichuOutcome();
		
		p.setStart(0);
		p.setSize(pageSize);
		p.setOutcomeid(outcomeid);
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);
			
			if(zhichuOutcomeList!=null&&zhichuOutcomeList.size()==1){
				
				oldzhichuOutcome=zhichuOutcomeList.get(0);
			}
		Date bsndate = DateUtil.parse(zhichuOutcome.getBsndatestr()
				+ " 00:00:00", DateUtil.DATE_TIME_FORMAT);

		int year = Integer.parseInt(zhichuOutcome.getBsndatestr().substring(0,
				4));
		int month = Integer.parseInt(zhichuOutcome.getBsndatestr().substring(5,
				7));
		

		int oldyear = Integer.parseInt(DateUtil.formatYYYYMMDD(oldzhichuOutcome.getBsndate()).substring(0,
				4));
		int oldmonth = Integer.parseInt(DateUtil.formatYYYYMMDD(oldzhichuOutcome.getBsndate()).substring(5,
				7));

		String managerid[] = zhichuOutcome.getManagerid().split("@");
		
		
		
		if(DateUtil.formatYYYYMMDD(oldzhichuOutcome.getBsndate()).equals(zhichuOutcome.getBsndatestr())){
			
 
			oldzhichuOutcome.setManagerid(managerid[0]);
			oldzhichuOutcome.setManagername(managerid[1]);
			oldzhichuOutcome.setYear(year);
			oldzhichuOutcome.setMonth(month);
			oldzhichuOutcome.setBsndate(bsndate);
			oldzhichuOutcome.setPrice(zhichuOutcome.getPrice());
			oldzhichuOutcome.setPricetype(zhichuOutcome.getPricetype());
			oldzhichuOutcome.setMemo(zhichuOutcome.getMemo());
			reportService.updateZhichuOutcome(oldzhichuOutcome);
			
			
		    p = new ZhichuOutcome();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(oldyear);
			p.setMonth(oldmonth);
			zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

			double totalprice=0;
			if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0) {
				int len = zhichuOutcomeList.size();

				for (int i = 0; i < len; i++) {

					totalprice = totalprice + zhichuOutcomeList.get(i).getPrice();
				}

				Date bsn = DateUtil.parse(oldyear + "-" + oldmonth + "-01  00:00:00",
						DateUtil.DATE_TIME_FORMAT);

				YearSmy smy = new YearSmy();
				smy.setReportdate(bsn);
				List<YearSmy> ysList = reportService.getYearSmy(smy);
				if (ysList != null && ysList.size() > 0) {
					YearSmy yearSmy2 = ysList.get(0);

					yearSmy2.setGsprice(totalprice);
					double toutprice = yearSmy2.getYfkprice()
							+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
							+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
							+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
					yearSmy2.setToutprice(toutprice);
					double tpprice = yearSmy2.getTinprice()
							- yearSmy2.getToutprice();
					yearSmy2.setToutprice(toutprice);
					yearSmy2.setTpprice(tpprice);

					reportService.updateYearSmy(yearSmy2);

				} 
				
			}
			
		}else{
			
			//

			oldzhichuOutcome.setManagerid(managerid[0]);
			oldzhichuOutcome.setManagername(managerid[1]);
			oldzhichuOutcome.setYear(year);
			oldzhichuOutcome.setMonth(month);
			oldzhichuOutcome.setBsndate(bsndate);
			oldzhichuOutcome.setPrice(zhichuOutcome.getPrice());
			oldzhichuOutcome.setPricetype(zhichuOutcome.getPricetype());
			oldzhichuOutcome.setMemo(zhichuOutcome.getMemo());
			reportService.updateZhichuOutcome(oldzhichuOutcome);
			
			
		    p = new ZhichuOutcome();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(oldyear);
			p.setMonth(oldmonth);
			zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

			double totalprice=0;
			if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0) {
				int len = zhichuOutcomeList.size();

				for (int i = 0; i < len; i++) {

					totalprice = totalprice + zhichuOutcomeList.get(i).getPrice();
				}

				Date bsn = DateUtil.parse(oldyear + "-" + oldmonth + "-01  00:00:00",
						DateUtil.DATE_TIME_FORMAT);

				YearSmy smy = new YearSmy();
				smy.setReportdate(bsn);
				List<YearSmy> ysList = reportService.getYearSmy(smy);
				if (ysList != null && ysList.size() > 0) {
					YearSmy yearSmy2 = ysList.get(0);

					yearSmy2.setGsprice(totalprice);
					double toutprice = yearSmy2.getYfkprice()
							+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
							+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
							+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
					yearSmy2.setToutprice(toutprice);
					double tpprice = yearSmy2.getTinprice()
							- yearSmy2.getToutprice();
					yearSmy2.setToutprice(toutprice);
					yearSmy2.setTpprice(tpprice);

					reportService.updateYearSmy(yearSmy2);

				} 
				
			}
			
			//更新 新日期下的支出综合
			
			if(year!=oldyear||month!=oldmonth){
		    p = new ZhichuOutcome();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);
			zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

			totalprice=0;
			if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0) {
				int len = zhichuOutcomeList.size();

				for (int i = 0; i < len; i++) {

					totalprice = totalprice + zhichuOutcomeList.get(i).getPrice();
				}

				Date bsn = DateUtil.parse(year + "-" + month + "-01  00:00:00",
						DateUtil.DATE_TIME_FORMAT);

				YearSmy smy = new YearSmy();
				smy.setReportdate(bsn);
				List<YearSmy> ysList = reportService.getYearSmy(smy);
				if (ysList != null && ysList.size() > 0) {
					YearSmy yearSmy2 = ysList.get(0);

					yearSmy2.setGsprice(totalprice);
					double toutprice = yearSmy2.getYfkprice()
							+ yearSmy2.getZpprice() + yearSmy2.getGsprice()
							+ yearSmy2.getGzprice() + yearSmy2.getFkprice()
							+ yearSmy2.getPkprice() + yearSmy2.getQtprice();
					yearSmy2.setToutprice(toutprice);
					double tpprice = yearSmy2.getTinprice()
							- yearSmy2.getToutprice();
					yearSmy2.setToutprice(toutprice);
					yearSmy2.setTpprice(tpprice);

					reportService.updateYearSmy(yearSmy2);

				}else{
					
					YearSmy ys = new YearSmy();
					ys.setReportdate(bsn);
					ys.setYear(year);
					ys.setMonth(month);
					ys.setWnum(0);
					ys.setZnum(0);
					ys.setYnum(0);
					ys.setWprice(0);
					ys.setZprice(0);
					ys.setYprice(0);

					ys.setYfkprice(0);
					ys.setZpprice(0);
					ys.setGsprice(totalprice);
					ys.setGzprice(0);
					ys.setQtprice(0);

					ys.setTinprice(0);
					ys.setToutprice(totalprice);
					ys.setTpprice(0 - totalprice);

					reportService.createYearSmy(ys);
					
					
					
				}
				
			}
	 
		
		}
		}
		 
	 
		this.addErrorMessage("hello", "更新成功！");
 
		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);
   
        p = new ZhichuOutcome();
  
		p.setStart(0);
		p.setSize(pageSize);
		p.setOutcomeid(outcomeid);
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);
		
		if(zhichuOutcomeList!=null&&zhichuOutcomeList.size()==1){
			
			zhichuOutcome=zhichuOutcomeList.get(0);
			
		}else this.addErrorMessage("hello", "公司支出数据错误！");
		return "showupdatezhichuoutcomepage";

	}
	
	public String exportZhichuOutcome() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		Integer year = yearSmy.getYear();
		Integer month = yearSmy.getMonth();

		String outfile = this.getEnv("downpath") + "/" + year == null ? "全部"
				: year + "-" + month == null ? "全部" : month + "_公司支出明细.xlsx";
		try {

			ZhichuOutcome gos = new ZhichuOutcome();
			gos.setYear(yearSmy.getYear());
			gos.setMonth(yearSmy.getMonth());
			gos.setStart(0);
			gos.setSize(Integer.MAX_VALUE);
			zhichuOutcomeList = reportService.getZhichuOutcomeList(gos);
			int recordnum = 0;
			if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0)
				recordnum = zhichuOutcomeList.size();

			Excel2007WriterZhichuOutcome writer = new Excel2007WriterZhichuOutcome();
			writer.createSheet();
			writer.process(outfile, zhichuOutcomeList, 0, recordnum);
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
					+ new String((year + "-" + month + "_公司支出明细.xlsx")
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

		return IMPORT_PAGE;
	}

	public String showImportIncomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		String pageNoStr = request.getParameter("pageNo");

		if (yearSmy == null)
			yearSmy = new YearSmy();

		yearSmy.setYear(Integer.parseInt(bsndatestr.substring(0, 4)));
		yearSmy.setMonth(Integer.parseInt(bsndatestr.substring(5, 7)));
		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		Income p = new Income();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		Integer recordNum = reportService.getIncomeListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new Income();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		incomeList = reportService.getIncomeList(p);

		return "showimportincomepage";

	}

	public String showUpdateIncomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String pageNo = request.getParameter("pageNo");
		String incomeid = request.getParameter("incomeid");

		if (pageNo == null)
			yearSmy.setPageNo(1);
		else
			yearSmy.setPageNo(Integer.parseInt(pageNo));

		Income p = new Income();
		p.setStart(0);
		p.setSize(pageSize);
		p.setIncomeid(Integer.parseInt(incomeid));
		incomeList = reportService.getIncomeList(p);

		if (incomeList != null && incomeList.size() == 1)
			income = incomeList.get(0);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		return "showupdateincomepage";

	}

	public String searchIncomePage() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		Income p = new Income();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManagerid(yearSmy.getManager());
		Integer recordNum = reportService.getIncomeListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new Income();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManagerid(yearSmy.getManager());
		incomeList = reportService.getIncomeList(p);

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());
		return "showimportincomepage";

	}

	public String exportIncome() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);

		Integer year = yearSmy.getYear();
		Integer month = yearSmy.getMonth();

		String outfile = this.getEnv("downpath") + "/" + year == null ? "全部"
				: year + "-" + month == null ? "全部" : month + "_收款明细.xlsx";
		try {

			Income p = new Income();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(yearSmy.getYear());
			p.setMonth(yearSmy.getMonth());
			p.setManagerid(yearSmy.getManager());
			incomeList = reportService.getIncomeList(p);

			int recordnum = 0;
			if (incomeList != null && incomeList.size() > 0)
				recordnum = incomeList.size();

			Excel2007WriterIncomeDetail writer = new Excel2007WriterIncomeDetail();
			writer.createSheet("收款");
			writer.process(outfile, incomeList, 0, recordnum);

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
					+ new String((year == null ? "全部"
							: year + "-" + month == null ? "全部" : month
									+ "_收款明细.xlsx").getBytes("gb2312"),
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

	public String createIncome() {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		Date t = new Date();
		String imgid = null;
		if (upexcel == null) {
			imgid = "nopingzheng";
		} else {

			imgid = t.getTime() + "";
			FileUploadUtil.uploadFile(upexcel, "jpg", imgid, this
					.getEnv("path"), "/");

		}

		Date bsndate = null;
		try {
			bsndate = DateUtil.parse(income.getBsndatestr() + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// /先查找是否有该用户的面单销售，同时不能大于总数量

		int year = Integer.parseInt(income.getBsndatestr().substring(0, 4));
		int month = Integer.parseInt(income.getBsndatestr().substring(5, 7));

		PageParameter p1 = new PageParameter();

		p1.setStart(0);
		p1.setSize(Integer.MAX_VALUE);
		p1.setYear(year);
		p1.setMonth(month);
		p1.setManagerid(income.getManagerid());
		mailFenpeiList = reportService.getMailFenpeiSmyList(p1);

		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		p.setManagerid(income.getManagerid());
		incomeList = reportService.getIncomeSmyList(p);
		Income mm = null;
		if (incomeList != null && incomeList.size() == 1) {

			mm = incomeList.get(0);

		} else
			mm = new Income();

		if (mailFenpeiList != null && mailFenpeiList.size() == 1) {

			MailFenpei mf = mailFenpeiList.get(0);

			if ((mf.getNum() - mm.getNum()) >= income.getNum()
					&& (mf.getPrice() - mm.getPrice()) >= income.getPrice()) {

				income.setBsndate(bsndate);
				income.setImgid(imgid);
				income.setCreatetime(new Date());
				income.setYear(year);
				income.setMonth(month);
				reportService.createIncome(income);

				p = new Income();

				p.setStart(0);
				p.setSize(Integer.MAX_VALUE);
				p.setBsndate(bsndate);
				incomeList = reportService.getIncomeList(p);
				if (incomeList != null && incomeList.size() > 0) {
					int len = 0;
					len = incomeList.size();
					double price = 0;

					for (int i = 0; i < len; i++) {
						Income m = incomeList.get(i);

						price = price + m.getPrice();

					}

					income.setPrice(price);

				}

				YearSmy smy = new YearSmy();
				smy.setReportdate(bsndate);
				List<YearSmy> ysList = reportService.getYearSmy(smy);
				if (ysList != null && ysList.size() > 0) {
					yearSmy = ysList.get(0);

					double skprice = income.getPrice();

					yearSmy.setSkprice(skprice);

					reportService.updateYearSmy(yearSmy);

				} else {

					YearSmy ys = new YearSmy();
					ys.setReportdate(income.getBsndate());
					ys.setYear(income.getYear());
					ys.setMonth(income.getMonth());
					ys.setWnum(0);
					ys.setZnum(0);
					ys.setYnum(0);
					ys.setWprice(0);
					ys.setZprice(0);
					ys.setYprice(0);

					ys.setYfkprice(0);
					ys.setZpprice(0);
					ys.setGsprice(0);
					ys.setGzprice(0);
					ys.setQtprice(0);

					ys.setTinprice(0);
					ys.setToutprice(0);
					ys.setTpprice(0);
					ys.setSkprice(income.getPrice());

					reportService.createYearSmy(ys);
				}

				this.addErrorMessage("hello", "创建成功！");

			} else
				this.addErrorMessage("hello", "收款前先进行销售或者收款的数量和金额不对！");

		} else {

			this.addErrorMessage("hello", "收款前先进行销售或者收款的数量和金额不对！");

		}

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());

		if (yearSmy == null)
			yearSmy = new YearSmy();

		yearSmy.setYear(Integer.parseInt(bsndatestr.substring(0, 4)));
		yearSmy.setMonth(Integer.parseInt(bsndatestr.substring(5, 7)));

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		p = new Income();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		Integer recordNum = reportService.getIncomeListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new Income();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		incomeList = reportService.getIncomeList(p);

		return "showimportincomepage";

	}

	public String updateIncome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String incomeid = request.getParameter("incomeid");
		Date t = new Date();
		String imgid = null;
		if (upexcel == null) {
			imgid = "nopingzheng";
		} else {

			imgid = t.getTime() + "";
			FileUploadUtil.uploadFile(upexcel, "jpg", imgid, this
					.getEnv("path"), "/");

		}
		Date bsndate = null;
		try {
			bsndate = DateUtil.parse(income.getBsndatestr() + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// /先查找是否有该用户的面单销售，同时不能大于总数量

		int year = Integer.parseInt(income.getBsndatestr().substring(0, 4));
		int month = Integer.parseInt(income.getBsndatestr().substring(5, 7));

		PageParameter p1 = new PageParameter();

		p1.setStart(0);
		p1.setSize(Integer.MAX_VALUE);
		p1.setYear(year);
		p1.setMonth(month);
		p1.setManagerid(income.getManagerid());
		mailFenpeiList = reportService.getMailFenpeiSmyList(p1);

		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		p.setManagerid(income.getManagerid());
		incomeList = reportService.getIncomeSmyList(p);
		Income mm = null;
		if (incomeList != null && incomeList.size() == 1) {

			mm = incomeList.get(0);

		} else
			mm = new Income();

		if (mailFenpeiList != null && mailFenpeiList.size() == 1) {

			MailFenpei mf = mailFenpeiList.get(0);

			if ((mf.getNum() - mm.getNum()) >= income.getNum()
					&& (mf.getPrice() - mm.getPrice()) >= income.getPrice()) {

				// 如果日期相同

				p = new Income();
				p.setStart(0);
				p.setSize(pageSize);
				p.setIncomeid(income.getIncomeid());
				incomeList = reportService.getIncomeList(p);

				Income mms = null;
				if (incomeList != null && incomeList.size() == 1)
					mms = incomeList.get(0);

				Date bsn = mms.getBsndate();
				String oldbsndate = DateUtil.formatYYYYMMDD(mms.getBsndate());

				double oldprice = mms.getPrice();
				if (oldbsndate.equals(income.getBsndatestr())) {

					mms.setManagerid(income.getManagerid());
					mms.setFukuantype(income.getFukuantype());
					mms.setFukuanman(income.getFukuanman());
					mms.setPricetype(income.getPricetype());
					mms.setNum(income.getNum());
					mms.setPrice(income.getPrice());
					mms.setMemo(income.getMemo());
					mms.setCreatetime(new Date());
					mms.setImgid(imgid);

					reportService.updateIncome(mms);

					YearSmy smy = new YearSmy();
					smy.setReportdate(bsn);
					List<YearSmy> ysList = reportService.getYearSmy(smy);
					if (ysList != null && ysList.size() > 0) {
						YearSmy yearSmy2 = ysList.get(0);

						double skprice = yearSmy2.getSkprice() - oldprice
								+ mms.getPrice();

						yearSmy2.setSkprice(skprice);

						reportService.updateYearSmy(yearSmy2);

					}

				} else {

					// 如果不同
					// 先恢复原先的状态
					YearSmy smy = new YearSmy();
					smy.setReportdate(bsn);// bsn 为老的日期
					List<YearSmy> ysList = reportService.getYearSmy(smy);
					if (ysList != null && ysList.size() > 0) {
						YearSmy yearSmy2 = ysList.get(0);

						double skprice = yearSmy2.getSkprice() - oldprice;

						yearSmy2.setSkprice(skprice);

						reportService.updateYearSmy(yearSmy2);

						year = Integer.parseInt(income.getBsndatestr()
								.substring(0, 4));
						month = Integer.parseInt(income.getBsndatestr()
								.substring(5, 7));

						mms.setManagerid(income.getManagerid());
						mms.setFukuantype(income.getFukuantype());
						mms.setFukuanman(income.getFukuanman());
						mms.setPricetype(income.getPricetype());
						mms.setNum(income.getNum());
						mms.setPrice(income.getPrice());
						mms.setMemo(income.getMemo());
						mms.setCreatetime(new Date());
						mms.setBsndate(bsndate);
						mms.setYear(year);
						mms.setMonth(month);
						mms.setImgid(imgid);
						reportService.updateIncome(mms);
					}

					YearSmy smy3 = new YearSmy();
					smy3.setReportdate(DateUtil.parse(income.getBsndatestr()
							+ " 00:00:00", DateUtil.DATE_TIME_FORMAT));
					List<YearSmy> ysList2 = reportService.getYearSmy(smy3);

					if (ysList2 != null && ysList2.size() == 1) {

						YearSmy yearSmy2 = ysList.get(0);

						double skprice = yearSmy2.getSkprice()
								+ income.getPrice();

						yearSmy2.setSkprice(skprice);

						reportService.updateYearSmy(yearSmy2);

					} else {

						YearSmy ys = new YearSmy();
						ys.setReportdate(bsndate);
						ys.setYear(income.getYear());
						ys.setMonth(income.getMonth());
						ys.setWnum(0);
						ys.setZnum(0);
						ys.setYnum(0);
						ys.setWprice(0);
						ys.setZprice(0);
						ys.setYprice(0);

						ys.setYfkprice(0);
						ys.setZpprice(0);
						ys.setGsprice(0);
						ys.setGzprice(0);
						ys.setQtprice(0);

						ys.setTinprice(0);
						ys.setToutprice(0);
						ys.setTpprice(0);
						ys.setSkprice(income.getPrice());

						reportService.createYearSmy(ys);

					}

				}

				this.addErrorMessage("hello", "收款更新成功!");
			} else {

				this.addErrorMessage("hello", "收款前先进行销售或者收款的数量和金额不对！");

			}

		} else {

			this.addErrorMessage("hello", "收款前先进行销售或者收款的数量和金额不对！");

		}

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		p = new Income();
		p.setStart(0);
		p.setSize(pageSize);
		p.setIncomeid(income.getIncomeid());
		incomeList = reportService.getIncomeList(p);

		if (incomeList != null && incomeList.size() == 1)
			income = incomeList.get(0);

		return "showupdateincomepage";

	}

	public String showIncomeYearSmyByMonthPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		// 列举出当月每个人汇总情况，这里是按人

		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		incomeList = reportService.getIncomeSmyByMonthList(p);
		if (incomeList != null && incomeList.size() > 0) {
			int len = 0;
			len = incomeList.size();
			income = new Income();

			double price = 0;
			double mprice = 0;
			double zprice = 0;
			double qprice = 0;
			int num = 0;
			for (int i = 0; i < len; i++) {
				Income m = incomeList.get(i);
				mprice = mprice + m.getMprice();
				zprice = zprice + m.getZprice();
				qprice = qprice + m.getQprice();
				price = price + m.getPrice();
				num = num + m.getNum();

			}
			income.setMprice(mprice);
			income.setZprice(zprice);
			income.setQprice(qprice);
			income.setPrice(price);
			income.setNum(num);
		}

		return "showincomeyearsmybymonthpage";

	}

	public String showIncomeYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
			}

		} else {
			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		// 列举出当月每个人汇总情况，这里是按人
		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		incomeList = reportService.getIncomeSmyList(p);
		if (incomeList != null && incomeList.size() > 0) {
			int len = 0;
			len = incomeList.size();
			income = new Income();

			double price = 0;
			double mprice = 0;
			double zprice = 0;
			double qprice = 0;
			int num = 0;
			for (int i = 0; i < len; i++) {
				Income m = incomeList.get(i);
				mprice = mprice + m.getMprice();
				zprice = zprice + m.getZprice();
				qprice = qprice + m.getQprice();
				price = price + m.getPrice();
				num = num + m.getNum();

			}
			income.setMprice(mprice);
			income.setZprice(zprice);
			income.setQprice(qprice);
			income.setPrice(price);
			income.setNum(num);

		}

		return "showincomeyearsmypage";

	}

	public String showIncomeMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);
		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		// 列举出当月每个人汇总情况，这里是按人

		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		incomeList = reportService.getIncomeSmyList(p);
		if (incomeList != null && incomeList.size() > 0) {
			int len = 0;
			len = incomeList.size();
			income = new Income();

			double price = 0;
			double mprice = 0;
			double zprice = 0;
			double qprice = 0;
			int num = 0;
			for (int i = 0; i < len; i++) {
				Income m = incomeList.get(i);
				mprice = mprice + m.getMprice();
				zprice = zprice + m.getZprice();
				qprice = qprice + m.getQprice();
				price = price + m.getPrice();
				num = num + m.getNum();

			}
			income.setMprice(mprice);
			income.setZprice(zprice);
			income.setQprice(qprice);
			income.setPrice(price);
			income.setNum(num);

		}

		return "showincomemonthsmypage";

	}

	public String showIncomeMonthSmyByDayPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);
		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		// 列举出当月每个人汇总情况，这里是按人

		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		incomeList = reportService.getIncomeSmyByDayList(p);
		if (incomeList != null && incomeList.size() > 0) {
			int len = 0;
			len = incomeList.size();
			income = new Income();

			double price = 0;
			double mprice = 0;
			double zprice = 0;
			double qprice = 0;
			int num = 0;
			for (int i = 0; i < len; i++) {
				Income m = incomeList.get(i);
				mprice = mprice + m.getMprice();
				zprice = zprice + m.getZprice();
				qprice = qprice + m.getQprice();
				price = price + m.getPrice();
				num = num + m.getNum();

			}
			income.setMprice(mprice);
			income.setZprice(zprice);
			income.setQprice(qprice);
			income.setPrice(price);
			income.setNum(num);

		}

		return "showincomemonthsmybydaypage";

	}

	public String showIncomeDaySmyPage() throws IOException, ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		Date maxday = null;

		if (yearSmy == null || yearSmy.getBsndatestr().equals("")) {

			maxday = reportService.getMaxDaySmy();

			YearSmy smy = new YearSmy();
			smy.setReportdate(maxday);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0)
				yearSmy = ysList.get(0);
			else {
				yearSmy = new YearSmy();
				yearSmy.setReportdate(maxday);
			}
		} else {

			maxday = DateUtil.parse(yearSmy.getBsndatestr() + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
			YearSmy smy = new YearSmy();
			smy.setReportdate(maxday);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				yearSmy = ysList.get(0);
				yearSmy.setReportdate(maxday);
			} else {
				yearSmy = new YearSmy();
				yearSmy.setReportdate(maxday);
			}

		}

		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setBsndate(maxday);

		incomeList = reportService.getIncomeList(p);

		if (incomeList != null && incomeList.size() > 0) {

			income = new Income();

			int len = incomeList.size();

			double price = 0;
			int num = 0;
			for (int i = 0; i < len; i++) {

				price += incomeList.get(i).getPrice();
				num += incomeList.get(i).getNum();

			}

			income.setPrice(price);
			income.setNum(num);
		}

		return "showincomedaysmypage";

	}

	public String deleteIncome() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String incomeid = request.getParameter("incomeid");
		String bsndatestr = request.getParameter("bsndate");
		String pricestr = request.getParameter("skprice");
		if (incomeid == null || incomeid.equals("nopingzheng")) {
			ManagerInfo m = new ManagerInfo();
			m.setIsdeleted("n");
			managerInfoList = mailService.queryForManagerInfoNew(m);

			String pageNoStr = request.getParameter("pageNo");

			int start = 0;
			int pageN = 1;

			if (pageNoStr != null && !pageNoStr.equals(""))
				pageN = Integer.parseInt(pageNoStr);

			Income p = new Income();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(yearSmy.getYear());
			p.setMonth(yearSmy.getMonth());
			Integer recordNum = reportService.getIncomeListCount(p);

			pageBean = new PageBean(pageSize, pageN, recordNum);
			p = new Income();
			start = (pageN - 1) * pageSize;
			p.setStart(start);
			p.setSize(pageSize);
			p.setYear(yearSmy.getYear());
			p.setMonth(yearSmy.getMonth());
			incomeList = reportService.getIncomeList(p);

			bsndatestr = DateUtil.formatYYYYMMDD(new Date());
			this.addErrorMessage("hello", "刪除失败");
			return "showimportincomepage";

		}

		if (income == null)
			income = new Income();

		income.setIncomeid(Integer.parseInt(incomeid));
		reportService.deleteIncome(income);

		YearSmy smy = new YearSmy();
		smy.setReportdate(DateUtil.parse(bsndatestr + " 00:00:00",
				DateUtil.DATE_TIME_FORMAT));
		List<YearSmy> ysList = reportService.getYearSmy(smy);
		if (ysList != null && ysList.size() > 0) {
			yearSmy = ysList.get(0);
			double skprice = yearSmy.getSkprice()
					- Double.parseDouble(pricestr);
			yearSmy.setSkprice(skprice);
			reportService.updateYearSmy(yearSmy);

		}
		this.addErrorMessage("hello", "刪除成功");

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		Income p = new Income();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		Integer recordNum = reportService.getIncomeListCount(p);

		if ((pageN - 1) * pageSize == recordNum.intValue())
			pageN = pageN - 1;

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new Income();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		incomeList = reportService.getIncomeList(p);

		bsndatestr = DateUtil.formatYYYYMMDD(new Date());

		return "showimportincomepage";

	}

	public String deleteMailFenpei() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String mailinfoid = request.getParameter("mailinfoid");
		String numstr = request.getParameter("num");
		String pricestr = request.getParameter("price");
		String bsnstr = request.getParameter("bsnstr");

		MailFenpei mf = new MailFenpei();
		mf.setMailinfoid(Integer.parseInt(mailinfoid));
		reportService.deleteMailFenpei(mf);

		YearSmy smy = new YearSmy();
		smy.setReportdate(DateUtil.parse(bsnstr + " 00:00:00",
				DateUtil.DATE_TIME_FORMAT));
		List<YearSmy> ysList = reportService.getYearSmy(smy);
		if (ysList != null && ysList.size() > 0) {
			YearSmy yearSmy2 = ysList.get(0);

			yearSmy2.setWnum(yearSmy2.getWnum() - Integer.parseInt(numstr));
			yearSmy2.setWprice(yearSmy2.getWprice()
					- Double.parseDouble(pricestr));
			double tinprice = yearSmy2.getZprice() + yearSmy2.getWprice()
					+ yearSmy2.getYprice();
			yearSmy2.setTinprice(tinprice);
			double tpprice = yearSmy2.getTinprice() - yearSmy2.getToutprice();

			yearSmy2.setTinprice(tinprice);
			yearSmy2.setTpprice(tpprice);

			reportService.updateYearSmy(yearSmy2);
		}

		this.addErrorMessage("hello", "删除成功");

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		if (mailFenpei == null)
			mailFenpei = new MailFenpei();

		mailFenpei.setBsndate(new Date());

		String pageNoStr = request.getParameter("pageNo");

		int start = 0;
		int pageN = 1;

		if (pageNoStr != null && !pageNoStr.equals(""))
			pageN = Integer.parseInt(pageNoStr);

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManager(yearSmy.getManager());
		p.setType(yearSmy.getType());
		Integer recordNum = reportService.getMailFenpeiListCount(p);

		if ((pageN - 1) * pageSize == recordNum.intValue())
			pageN = pageN - 1;

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		p.setManager(yearSmy.getManager());
		p.setType(yearSmy.getType());
		mailFenpeiList = reportService.getMailFenpeiList(p);
		return "showimportmailinfopage";

	}

	public String createMailFenpei() throws ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String bsn = mailFenpei.getBsndatestr();
		mailFenpei.setBsndate(DateUtil.parse(bsn + " 00:00:00",
				DateUtil.DATE_TIME_FORMAT));
		mailFenpei.setYear(Integer.parseInt(bsn.substring(0, 4)));
		mailFenpei.setMonth(Integer.parseInt(bsn.substring(5, 7)));
		mailFenpei.setCreatetime(new Date());
		reportService.createMailFenpei(mailFenpei);

		// 汇总当日所有面单收入

		MailFenpei mf = new MailFenpei();
		mf.setBsndate(mailFenpei.getBsndate());
		MailFenpei mfSmy = reportService.getMailFenpeiSmy(mf);

		YearSmy smy = new YearSmy();
		smy.setReportdate(mailFenpei.getBsndate());
		List<YearSmy> ysList = reportService.getYearSmy(smy);
		if (ysList != null && ysList.size() > 0) {
			yearSmy = ysList.get(0);

			yearSmy.setWnum(mfSmy.getNum());
			yearSmy.setWprice(mfSmy.getPrice());
			double tinprice = yearSmy.getZprice() + yearSmy.getWprice()
					+ yearSmy.getYprice();
			yearSmy.setTinprice(tinprice);
			double tpprice = yearSmy.getTinprice() - yearSmy.getToutprice();

			yearSmy.setTinprice(tinprice);
			yearSmy.setTpprice(tpprice);

			reportService.updateYearSmy(yearSmy);

		} else {

			YearSmy ys = new YearSmy();
			ys.setReportdate(mailFenpei.getBsndate());
			ys.setYear(mailFenpei.getYear());
			ys.setMonth(mailFenpei.getMonth());
			ys.setWnum(mfSmy.getNum());
			ys.setZnum(0);
			ys.setYnum(0);
			ys.setWprice(mfSmy.getPrice());
			ys.setZprice(0);
			ys.setYprice(0);

			ys.setYfkprice(0);
			ys.setZpprice(0);
			ys.setGsprice(0);
			ys.setGzprice(0);
			ys.setQtprice(0);

			ys.setTinprice(mfSmy.getPrice());
			ys.setToutprice(0);
			ys.setTpprice(mfSmy.getPrice());

			reportService.createYearSmy(ys);
		}

		this.addErrorMessage("hello", "录入成功");

		ManagerInfo m = new ManagerInfo();
		m.setIsdeleted("n");
		m.setRoles("statuser");
		m.setCardid("stat");
		managerInfoList = mailService.queryForManagerInfoNew(m);

		if (mailFenpei == null)
			mailFenpei = new MailFenpei();

		mailFenpei.setBsndate(new Date());

		int start = 0;
		int pageN = 1;

		PageParameter p = new PageParameter();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		Integer recordNum = reportService.getMailFenpeiListCount(p);

		pageBean = new PageBean(pageSize, pageN, recordNum);
		p = new PageParameter();
		start = (pageN - 1) * pageSize;
		p.setStart(start);
		p.setSize(pageSize);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		mailFenpeiList = reportService.getMailFenpeiList(p);
		return "showimportmailinfopage";

	}

	public String showYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
			}

		} else {
			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		// 列举出当月每个人汇总情况，这里是按人

		// DailyBalance db = new DailyBalance();
		// db.setYear(year);
		// dailybalanceList = reportService.getDailyBalanceSmyByManager(db);

		DailyBalance db = new DailyBalance();
		db.setYear(year);
		dailybalanceList = reportService.getDailyBalanceForStatuserByYear(db);

		return "showyearsmypage";

	}

	public String showYearSmyByMonthPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		// 列举出当月每个人汇总情况，这里是按人

		DailyBalance db = new DailyBalance();
		db.setYear(year);
		dailybalanceList = reportService.getDailyBalanceSmyByMonth(db);

		return "showyearsmybymonthpage";

	}

	public String showMailPriceYearSmyByMonthPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {
			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
			}

		}

		// 列举出当月每个人汇总情况，这里是按人
		PageParameter p = new PageParameter();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		mailFenpeiList = reportService.getMailFenpeiSmyByMonthList(p);
		if (mailFenpeiList != null && mailFenpeiList.size() > 0) {
			int len = 0;
			len = mailFenpeiList.size();
			mailFenpei = new MailFenpei();
			int cnum = 0;
			double cprice = 0;
			int pnum = 0;
			double pprice = 0;
			int dnum = 0;
			double dprice = 0;
			int num = 0;
			double price = 0;
			int rnum = 0;
			double rprice = 0;
			int fnum = 0;
			double fprice = 0;

			for (int i = 0; i < len; i++) {
				MailFenpei m = mailFenpeiList.get(i);
				cnum = cnum + m.getCnum();
				cprice = cprice + m.getCprice();
				pnum = pnum + m.getPnum();
				pprice = pprice + m.getPprice();
				dnum = dnum + m.getDnum();
				dprice = dprice + m.getDprice();
				rnum = rnum + m.getRnum();
				rprice = rprice + m.getRprice();
				fnum = fnum + m.getFnum();
				fprice = fprice + m.getFprice();

				num = num + m.getNum();
				price = price + m.getPrice();

			}

			mailFenpei.setCnum(cnum);
			mailFenpei.setCprice(cprice);
			mailFenpei.setDnum(dnum);
			mailFenpei.setDprice(dprice);
			mailFenpei.setPnum(pnum);
			mailFenpei.setPprice(pprice);
			mailFenpei.setRnum(rnum);
			mailFenpei.setRprice(rprice);
			mailFenpei.setFnum(fnum);
			mailFenpei.setFprice(fprice);
			mailFenpei.setNum(num);
			mailFenpei.setPrice(price);

		}

		return "showmailpriceyearsmybymonthpage";

	}

	public String showMailPriceYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {
			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
			}

		}

		Map<String, Income> incomeSmyMap = new HashMap<String, Income>();
		Income p1 = new Income();

		p1.setStart(0);
		p1.setSize(Integer.MAX_VALUE);
		p1.setYear(year);

		incomeList = reportService.getIncomeSmyList(p1);
		if (incomeList != null && incomeList.size() > 0) {
			int len = 0;
			len = incomeList.size();

			for (int i = 0; i < len; i++) {
				Income m = incomeList.get(i);
				incomeSmyMap.put(m.getManagerid(), m);

			}

		}
		// 列举出当月每个人汇总情况，这里是按人
		PageParameter p = new PageParameter();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		mailFenpeiList = reportService.getMailFenpeiSmyList(p);
		if (mailFenpeiList != null && mailFenpeiList.size() > 0) {
			int len = 0;
			len = mailFenpeiList.size();
			mailFenpei = new MailFenpei();
			int cnum = 0;
			double cprice = 0;
			int pnum = 0;
			double pprice = 0;
			int dnum = 0;
			double dprice = 0;
			int num = 0;
			double price = 0;
			int rnum = 0;
			double rprice = 0;
			int fnum = 0;
			double fprice = 0;
			int yfnum = 0;
			double yfprice = 0;
			int wfnum = 0;
			double wfprice = 0;

			for (int i = 0; i < len; i++) {
				MailFenpei m = mailFenpeiList.get(i);

				if (incomeSmyMap.get(m.getManagerid()) != null) {
					Income in = incomeSmyMap.get(m.getManagerid());
					m.setYfnum(in.getNum());
					m.setYfprice(in.getPrice());

					m.setWfnum(m.getNum() - m.getYfnum());
					m.setWfprice(m.getPrice() - m.getYfprice());

				} else {

					m.setWfnum(m.getNum() - m.getYfnum());
					m.setWfprice(m.getPrice() - m.getYfprice());

				}

				cnum = cnum + m.getCnum();
				cprice = cprice + m.getCprice();
				pnum = pnum + m.getPnum();
				pprice = pprice + m.getPprice();
				dnum = dnum + m.getDnum();
				dprice = dprice + m.getDprice();
				rnum = rnum + m.getRnum();
				rprice = rprice + m.getRprice();
				fnum = fnum + m.getFnum();
				fprice = fprice + m.getFprice();
				yfnum = yfnum + m.getYfnum();
				yfprice = yfprice + m.getYfprice();
				wfnum = wfnum + m.getWfnum();
				wfprice = wfprice + m.getWfprice();
				num = num + m.getNum();
				price = price + m.getPrice();

			}

			mailFenpei.setCnum(cnum);
			mailFenpei.setCprice(cprice);
			mailFenpei.setDnum(dnum);
			mailFenpei.setDprice(dprice);
			mailFenpei.setPnum(pnum);
			mailFenpei.setPprice(pprice);
			mailFenpei.setRnum(rnum);
			mailFenpei.setRprice(rprice);
			mailFenpei.setFnum(fnum);
			mailFenpei.setFprice(fprice);
			mailFenpei.setYfnum(yfnum);
			mailFenpei.setPrice(yfprice);
			mailFenpei.setWfnum(wfnum);
			mailFenpei.setWfprice(wfprice);
			mailFenpei.setNum(num);
			mailFenpei.setPrice(price);

		}

		return "showmailpriceyearsmypage";

	}

	public String showMailPriceMonthSmyByDayPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);
		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		Map<Date, Income> incomeSmyMap = new HashMap<Date, Income>();
		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		incomeList = reportService.getIncomeSmyByDayList(p);
		if (incomeList != null && incomeList.size() > 0) {
			int len = 0;
			len = incomeList.size();

			for (int i = 0; i < len; i++) {
				Income m = incomeList.get(i);
				incomeSmyMap.put(m.getBsndate(), m);

			}

		}

		// 列举出当月每个人汇总情况，这里是按人

		PageParameter p1 = new PageParameter();

		p1.setStart(0);
		p1.setSize(Integer.MAX_VALUE);
		p1.setYear(year);
		p1.setMonth(month);
		mailFenpeiList = reportService.getMailFenpeiSmyByDayList(p1);

		if (mailFenpeiList != null && mailFenpeiList.size() > 0) {
			int len = 0;
			len = mailFenpeiList.size();
			mailFenpei = new MailFenpei();
			int cnum = 0;
			double cprice = 0;
			int pnum = 0;
			double pprice = 0;
			int dnum = 0;
			double dprice = 0;
			int num = 0;
			double price = 0;
			int rnum = 0;
			double rprice = 0;
			int fnum = 0;
			double fprice = 0;
			int yfnum = 0;
			double yfprice = 0;
			int wfnum = 0;
			double wfprice = 0;

			for (int i = 0; i < len; i++) {
				MailFenpei m = mailFenpeiList.get(i);

				if (incomeSmyMap.get(m.getBsndate()) != null) {
					Income in = incomeSmyMap.get(m.getBsndate());
					m.setYfnum(in.getNum());
					m.setYfprice(in.getPrice());

					m.setWfnum(m.getNum() - m.getYfnum());
					m.setWfprice(m.getPrice() - m.getYfprice());

				} else {

					m.setWfnum(m.getNum() - m.getYfnum());
					m.setWfprice(m.getPrice() - m.getYfprice());

				}

				cnum = cnum + m.getCnum();
				cprice = cprice + m.getCprice();
				pnum = pnum + m.getPnum();
				pprice = pprice + m.getPprice();
				dnum = dnum + m.getDnum();
				dprice = dprice + m.getDprice();
				rnum = rnum + m.getRnum();
				rprice = rprice + m.getRprice();
				fnum = fnum + m.getFnum();
				fprice = fprice + m.getFprice();
				yfnum = yfnum + m.getYfnum();
				yfprice = yfprice + m.getYfprice();
				wfnum = wfnum + m.getWfnum();
				wfprice = wfprice + m.getWfprice();
				num = num + m.getNum();
				price = price + m.getPrice();

			}

			mailFenpei.setCnum(cnum);
			mailFenpei.setCprice(cprice);
			mailFenpei.setDnum(dnum);
			mailFenpei.setDprice(dprice);
			mailFenpei.setPnum(pnum);
			mailFenpei.setPprice(pprice);
			mailFenpei.setRnum(rnum);
			mailFenpei.setRprice(rprice);
			mailFenpei.setFnum(fnum);
			mailFenpei.setFprice(fprice);
			mailFenpei.setNum(num);
			mailFenpei.setPrice(price);
			mailFenpei.setYfnum(yfnum);
			mailFenpei.setYfprice(yfprice);
			mailFenpei.setWfnum(wfnum);
			mailFenpei.setWfprice(wfprice);
		}

		return "showmailpricemonthsmybydaypage";

	}

	public String showMailPriceMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);
		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		// 查找
		Map<String, Income> incomeSmyMap = new HashMap<String, Income>();
		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		incomeList = reportService.getIncomeSmyList(p);
		if (incomeList != null && incomeList.size() > 0) {
			int len = 0;
			len = incomeList.size();

			for (int i = 0; i < len; i++) {
				Income m = incomeList.get(i);
				incomeSmyMap.put(m.getManagerid(), m);

			}

		}

		// 列举出当月每个人汇总情况，这里是按人

		PageParameter p1 = new PageParameter();

		p1.setStart(0);
		p1.setSize(Integer.MAX_VALUE);
		p1.setYear(year);
		p1.setMonth(month);
		mailFenpeiList = reportService.getMailFenpeiSmyList(p1);
		if (mailFenpeiList != null && mailFenpeiList.size() > 0) {
			int len = 0;
			len = mailFenpeiList.size();
			mailFenpei = new MailFenpei();
			int cnum = 0;
			double cprice = 0;
			int pnum = 0;
			double pprice = 0;
			int dnum = 0;
			double dprice = 0;
			int num = 0;
			double price = 0;
			int rnum = 0;
			double rprice = 0;
			int fnum = 0;
			double fprice = 0;
			int yfnum = 0;
			double yfprice = 0;
			int wfnum = 0;
			double wfprice = 0;

			for (int i = 0; i < len; i++) {
				MailFenpei m = mailFenpeiList.get(i);

				if (incomeSmyMap.get(m.getManagerid()) != null) {
					Income in = incomeSmyMap.get(m.getManagerid());
					m.setYfnum(in.getNum());
					m.setYfprice(in.getPrice());

					m.setWfnum(m.getNum() - m.getYfnum());
					m.setWfprice(m.getPrice() - m.getYfprice());

				} else {

					m.setWfnum(m.getNum() - m.getYfnum());
					m.setWfprice(m.getPrice() - m.getYfprice());

				}

				cnum = cnum + m.getCnum();
				cprice = cprice + m.getCprice();
				pnum = pnum + m.getPnum();
				pprice = pprice + m.getPprice();
				dnum = dnum + m.getDnum();
				dprice = dprice + m.getDprice();
				rnum = rnum + m.getRnum();
				rprice = rprice + m.getRprice();
				fnum = fnum + m.getFnum();
				fprice = fprice + m.getFprice();
				yfnum = yfnum + m.getYfnum();
				yfprice = yfprice + m.getYfprice();
				wfnum = wfnum + m.getWfnum();
				wfprice = wfprice + m.getWfprice();
				num = num + m.getNum();
				price = price + m.getPrice();

			}

			mailFenpei.setCnum(cnum);
			mailFenpei.setCprice(cprice);
			mailFenpei.setDnum(dnum);
			mailFenpei.setDprice(dprice);
			mailFenpei.setPnum(pnum);
			mailFenpei.setPprice(pprice);
			mailFenpei.setRnum(rnum);
			mailFenpei.setRprice(rprice);
			mailFenpei.setFnum(fnum);
			mailFenpei.setFprice(fprice);
			mailFenpei.setYfnum(yfnum);
			mailFenpei.setYfprice(yfprice);
			mailFenpei.setWfnum(wfnum);
			mailFenpei.setWfprice(wfprice);
			mailFenpei.setNum(num);
			mailFenpei.setPrice(price);

		}

		return "showmailpricemonthsmypage";

	}

	public String showMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			} else {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
				yearSmy.setMonth(month);
			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		// 列举出当月每个人汇总情况，这里是按人
		//			
		// DailyBalance db = new DailyBalance();
		// db.setYear(year);
		// db.setMonth(month);
		// dailybalanceList = reportService.getDailyBalanceSmyByManager(db);

		DailyBalance db = new DailyBalance();
		db.setYear(year);
		db.setMonth(month);
		dailybalanceList = reportService.getDailyBalanceForStatuserByMonth(db);

		//		 	
		// if(dailybalanceList!=null&&dailybalanceList.size()>0){
		//		 		
		// int len=dailybalanceList.size();
		//		 		
		// for(int i=0;i<len;i++){
		// DailyBalance db2=dailybalanceList.get(i);
		// num+=db2.getNum();
		//		 			
		//		 			
		//		 			
		// }
		//		 	 	
		//		 		
		// }
		//		 	
		//		 	
		//		 	

		return "showmonthsmypage";

	}

	public String showMonthSmyByDayPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		// 列举出当月每个人汇总情况，这里是按人

		DailyBalance db = new DailyBalance();
		db.setYear(year);
		db.setMonth(month);
		dailybalanceList = reportService.getDailyBalanceSmyByDay(db);

		return "showmonthsmybydaypage";

	}

	public String showMonthDailyBalanceByManagerPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String manager = request.getParameter("manager");

		if (manager == null || manager.equals("")) {
			manager = null;
		} else {
			manager = new String(request.getParameter("manager").getBytes(
					"ISO8859-1"), "utf-8");

		}

		String yearstr = new String(request.getParameter("year").getBytes(
				"ISO8859-1"), "utf-8");

		String monthstr = new String(request.getParameter("month").getBytes(
				"ISO8859-1"), "utf-8");

		int year = Integer.parseInt(yearstr);
		int month = Integer.parseInt(monthstr);

		// 列举出当月每个人汇总情况，这里是按人

		DailyBalance db = new DailyBalance();
		db.setYear(year);
		db.setMonth(month);
		db.setManager(manager);
		dailybalanceList = reportService.getDailyBalanceForStatuser(db);


		yearSmy = new YearSmy();
		yearSmy.setYear(year);
		yearSmy.setMonth(month);
		yearSmy.setManager(manager);
		return "showmonthdailybalancebymanagerpage";

	}

	public String showYearDailyBalanceByManagerPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String manager = request.getParameter("manager");

		if (manager == null || manager.equals("")) {
			manager = null;
		} else {
			manager = new String(request.getParameter("manager").getBytes(
					"ISO8859-1"), "utf-8");

		}

		String yearstr = new String(request.getParameter("year").getBytes(
				"ISO8859-1"), "utf-8");

		int year = Integer.parseInt(yearstr);

		// 列举出当月每个人汇总情况，这里是按人

		DailyBalance db = new DailyBalance();
		db.setYear(year);
		db.setManager(manager);
		dailybalanceList = reportService.getDailyBalanceForStatuserByMonth(db);

		yearSmy = new YearSmy();
		yearSmy.setYear(year);
		yearSmy.setManager(manager);

		return "showyeardailybalancebymanagerpage";

	}

	public String showDailyBalancePage() throws IOException, ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		String bsndatestr = new String(request.getParameter("bsndate")
				.getBytes("ISO8859-1"), "utf-8");
		bsndatestr = bsndatestr + " 00:00:00";

//		DailyBalance db = new DailyBalance();
//		db.setBsndate(DateUtil.parse(bsndatestr, DateUtil.DATE_TIME_FORMAT));
//		dailybalanceList = reportService.getDailyBalance(db);

		DailyBalance  db = new DailyBalance();
		 db.setBsndate(DateUtil.parse(bsndatestr, DateUtil.DATE_TIME_FORMAT));
		 dailybalanceList = reportService.getDailyBalanceForStatuser(db);
		yearSmy = new YearSmy();
		yearSmy.setBsndatestr(DateUtil.formatYYYYMMDD(db.getBsndate()));

		return "showdailybalancepage";

	}

	public String showMailPriceDaySmyPage() throws IOException, ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		Date maxday = null;

		if (yearSmy == null || yearSmy.getBsndatestr().equals("")) {

			maxday = reportService.getMaxDaySmy();
			if (maxday == null)
				maxday = DateUtil.parse(DateUtil.formatYYYYMMDD(new Date())
						+ " 00:00:00", DateUtil.DATE_TIME_FORMAT);
			YearSmy smy = new YearSmy();
			smy.setReportdate(maxday);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0)
				yearSmy = ysList.get(0);
			else {
				yearSmy = new YearSmy();
				yearSmy.setReportdate(maxday);
			}
		} else {

			maxday = DateUtil.parse(yearSmy.getBsndatestr() + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
			YearSmy smy = new YearSmy();
			smy.setReportdate(maxday);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				yearSmy = ysList.get(0);
				yearSmy.setReportdate(maxday);
			} else {
				yearSmy = new YearSmy();
				yearSmy.setReportdate(maxday);
			}

		}

		PageParameter p = new PageParameter();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setBsndate(maxday);
		mailFenpeiList = reportService.getMailFenpeiList(p);

		return "showmailpricedaysmypage";

	}

	public String showDaySmyPage() throws IOException, ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		Date maxday = null;

		if (yearSmy == null || yearSmy.getBsndatestr().equals("")) {

			maxday = reportService.getMaxDaySmy();

			if (maxday == null)
				maxday = DateUtil.parse(DateUtil.formatYYYYMMDD(new Date())
						+ " 00:00:00", DateUtil.DATE_TIME_FORMAT);
			YearSmy smy = new YearSmy();
			smy.setReportdate(maxday);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0)
				yearSmy = ysList.get(0);
			else {
				yearSmy = new YearSmy();
				yearSmy.setReportdate(maxday);
			}
		} else {

			maxday = DateUtil.parse(yearSmy.getBsndatestr() + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
			YearSmy smy = new YearSmy();
			smy.setReportdate(maxday);
			List<YearSmy> ysList = reportService.getYearSmy(smy);
			if (ysList != null && ysList.size() > 0) {
				yearSmy = ysList.get(0);
				yearSmy.setReportdate(maxday);
			} else {
				yearSmy = new YearSmy();
				yearSmy.setReportdate(maxday);
			}

		}
		// old method to query the dailybalance for all users
		// DailyBalance db = new DailyBalance();
		// db.setBsndate(maxday);
		// dailybalanceList = reportService.getDailyBalance(db);

		// new method to query the balance for only the stat user .so you have
		// to summary the unstat usr's data to stat user
		DailyBalance db = new DailyBalance();
		db.setBsndate(maxday);
		dailybalanceList = reportService.getDailyBalanceForStatuser(db);

		return "showdaysmypage";

	}

	// 导出公司收入总明细
	public String exportCompanyIncomeMonthDetail() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		int year = Integer.parseInt(yearstr);
		int month = Integer.parseInt(monthstr);
		String manager = request.getParameter("manager");

		if (manager == null || manager.equals("")) {
			manager = null;
		} else {
			manager = new String(request.getParameter("manager").getBytes(
					"ISO8859-1"), "utf-8");

		}
		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_" + manager + "_月公司收入总明细.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);
			p.setManager(manager);
			companyIncomeList = reportService
					.getCompanyIncomeMonthDetailList(p);

			int recordnum = 0;
			if (companyIncomeList != null && companyIncomeList.size() > 0)
				recordnum = companyIncomeList.size();

			Excel2007WriterCompanyIncomeMonthDetail writer = new Excel2007WriterCompanyIncomeMonthDetail();
			writer.createSheet();
			writer.process(outfile, companyIncomeList, 0, recordnum);
			writer.createFile(outfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String path = outfile;

		// 保存文件
		response.setContentType("application/vnd.ms-excel;charset=GBK");

		try {
			response.setHeader("Content-disposition",
					"attachment; filename="
							+ new String((yearstr + "-" + monthstr + "_"
									+ manager + "_月公司收入总明细.xlsx")
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

		return IMPORT_PAGE;
	}

	// 导出公司收入按人按月汇总
	public String exportCompanyIncomeMonthSmyByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		int year = Integer.parseInt(yearstr);
		String manager = request.getParameter("manager");

		if (manager == null || manager.equals("")) {
			manager = null;
		} else {
			manager = new String(request.getParameter("manager").getBytes(
					"ISO8859-1"), "utf-8");

		}
		String outfile = this.getEnv("downpath") + "/" + yearstr + "_"
				+ manager + "_月公司收入总明细.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setManager(manager);
			companyIncomeList = reportService
					.getCompanyIncomeMonthSmyByManagerList(p);

			int recordnum = 0;
			if (companyIncomeList != null && companyIncomeList.size() > 0)
				recordnum = companyIncomeList.size();

			Excel2007WriterCompanyIncomeMonthSmyByManager writer = new Excel2007WriterCompanyIncomeMonthSmyByManager();
			writer.createSheet();
			writer.process(outfile, companyIncomeList, 0, recordnum);
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
					+ new String((yearstr + "_" + manager + "_月公司收入总明细.xlsx")
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

		return IMPORT_PAGE;
	}

	// 按人按月汇总
	public String exportMailPriceMonthSmyByManager() throws IOException,
			ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String manager = request.getParameter("manager");

		if (manager == null || manager.equals("")) {
			manager = null;
		} else {
			manager = new String(request.getParameter("manager").getBytes(
					"ISO8859-1"), "utf-8");

		}
		String outfile = this.getEnv("downpath") + "/" + yearstr + "_"
				+ manager + "_面单费用月汇总.xlsx";
		try {

			// 中转费明细
			PageParameter p = new PageParameter();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setState("normal");
			p.setManager(manager);
			p.setYear(Integer.parseInt(yearstr));
			mailFenpeiList = reportService
					.getMailFenpeiSmyByMonthManagerList(p);

			int recordnum = 0;

			if (mailFenpeiList != null && mailFenpeiList.size() > 0)
				recordnum = mailFenpeiList.size();

			Excel2007WriterMailPriceYearByMonthManager writer = new Excel2007WriterMailPriceYearByMonthManager();
			writer.createSheet();
			writer.process(outfile, mailFenpeiList, 0, recordnum);

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
					+ new String((yearstr + "_" + manager + "_面单费用月汇总.xlsx")
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

		return IMPORT_PAGE;
	}

	// 导出揽件人按月收款月汇总

	public String exportIncomeYearSmyByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");

		String manager = new String(request.getParameter("manager").getBytes(
				"ISO8859-1"), "utf-8");
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + year + "_" + manager
				+ "_收款月汇总.xlsx";
		try {

			Income p = new Income();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(Integer.parseInt(yearstr));
			p.setManager(manager);
			incomeList = reportService.getIncomeYearSmyList(p);
			int recordnum = 0;

			if (incomeList != null && incomeList.size() > 0)
				recordnum = incomeList.size();

			Excel2007WriterIncomeYearSmyByManager writer = new Excel2007WriterIncomeYearSmyByManager();
			writer.createSheet();
			writer.process(outfile, incomeList, 0, recordnum);

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
					+ new String((year + "_" + manager + "_收款月汇总.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportIncomeYearByManager() throws IOException,
			ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");

		String outfile = this.getEnv("downpath") + "/" + yearstr
				+ "_收款年汇总.xlsx";
		try {

			// 中转费明细

			Income p = new Income();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(Integer.parseInt(yearstr));
			incomeList = reportService.getIncomeSmyList(p);
			int recordnum = 0;

			if (incomeList != null && incomeList.size() > 0)
				recordnum = incomeList.size();

			Excel2007WriterIncomeYearByManager writer = new Excel2007WriterIncomeYearByManager();
			writer.createSheet();
			writer.process(outfile, incomeList, 0, recordnum);

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
					+ new String((yearstr + "_收款年汇总.xlsx").getBytes("gb2312"),
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

	// 导出月收款汇总

	public String exportIncomeMonthByManager() throws IOException,
			ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");

		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_收款月汇总.xlsx";
		try {

			// 中转费明细

			String starttime = yearstr + "-" + monthstr + "-01" + " 00:00:00";

			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtil.parse(starttime, DateUtil.DATE_TIME_FORMAT));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int maxday = c.getActualMaximum(Calendar.DATE);
			String endtime = yearstr + "-" + monthstr + "-" + maxday
					+ " 00:00:00";

			Income p = new Income();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(Integer.parseInt(yearstr));
			p.setMonth(Integer.parseInt(monthstr));

			incomeList = reportService.getIncomeSmyList(p);

			int recordnum = 0;

			if (incomeList != null && incomeList.size() > 0)
				recordnum = incomeList.size();

			Excel2007WriterIncomeMonthByManager writer = new Excel2007WriterIncomeMonthByManager();
			writer.createSheet();
			writer.process(outfile, incomeList, 0, recordnum);

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
					+ new String((yearstr + "-" + monthstr + "_收款月汇总.xlsx")
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

		return IMPORT_PAGE;
	}

	// 导出月收款总明细

	public String exportIncomeMonthDetailByDay() throws IOException,
			ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String bsndatestr = request.getParameter("bsndate");

		Date bsndate = DateUtil.parse(bsndatestr + " 00:00:00",
				DateUtil.DATE_TIME_FORMAT);

		String outfile = this.getEnv("downpath") + "/" + bsndatestr
				+ "_收款明细.xlsx";
		try {

			// 中转费明细

			Income p = new Income();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setBsndate(bsndate);
			incomeList = reportService.getIncomeList(p);

			int recordnum = 0;

			if (incomeList != null && incomeList.size() > 0)
				recordnum = incomeList.size();

			Excel2007WriterIncomeDetail writer = new Excel2007WriterIncomeDetail();
			writer.createSheet("收款明细");
			writer.process(outfile, incomeList, 0, recordnum);

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
					+ new String(
							(bsndatestr + "_收款明细.xlsx").getBytes("gb2312"),
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

	public String exportIncomeMonthDetailByManager() throws IOException,
			ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		String manager = request.getParameter("manager");

		if (manager == null || manager.equals("")) {
			manager = null;
		} else {
			manager = new String(request.getParameter("manager").getBytes(
					"ISO8859-1"), "utf-8");

		}
		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_" + manager + "_收款明细.xlsx";
		try {

			// 中转费明细

			String starttime = yearstr + "-" + monthstr + "-01" + " 00:00:00";

			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtil.parse(starttime, DateUtil.DATE_TIME_FORMAT));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int maxday = c.getActualMaximum(Calendar.DATE);
			String endtime = yearstr + "-" + monthstr + "-" + maxday
					+ " 00:00:00";
			int mon = Integer.parseInt(monthstr);
			int year = Integer.parseInt(yearstr);
			Income p = new Income();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			// p.setEndtime(endtime);
			// p.setStarttime(starttime);
			p.setYear(year);
			p.setMonth(mon);
			p.setManager(manager);
			incomeList = reportService.getIncomeList(p);

			int recordnum = 0;

			if (incomeList != null && incomeList.size() > 0)
				recordnum = incomeList.size();

			Excel2007WriterIncomeDetail writer = new Excel2007WriterIncomeDetail();
			writer.createSheet("收款");
			writer.process(outfile, incomeList, 0, recordnum);

			writer.createFile(outfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String path = outfile;

		// 保存文件
		response.setContentType("application/vnd.ms-excel;charset=GBK");

		try {
			response
					.setHeader("Content-disposition",
							"attachment; filename="
									+ new String((yearstr + "-" + monthstr
											+ "_" + manager + "收款明细.xlsx")
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

		return IMPORT_PAGE;
	}

	// 导出按日月收款汇总

	public String exportIncomeYearByMonth() throws IOException, ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String outfile = this.getEnv("downpath") + "/" + yearstr
				+ "_收款按月年汇总.xlsx";
		try {

			// 中转费明细

			Income p = new Income();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(Integer.parseInt(yearstr));

			incomeList = reportService.getIncomeSmyByMonthList(p);

			int recordnum = 0;

			if (incomeList != null && incomeList.size() > 0)
				recordnum = incomeList.size();

			Excel2007WriterIncomeYearByMonth writer = new Excel2007WriterIncomeYearByMonth();
			writer.createSheet();
			writer.process(outfile, incomeList, 0, recordnum);

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
					+ new String(
							(yearstr + "_收款按月年汇总.xlsx").getBytes("gb2312"),
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

	public String exportIncomeMonthByDay() throws IOException, ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");

		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_收款按日月汇总.xlsx";
		try {

			// 中转费明细

			String starttime = yearstr + "-" + monthstr + "-01" + " 00:00:00";

			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtil.parse(starttime, DateUtil.DATE_TIME_FORMAT));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int maxday = c.getActualMaximum(Calendar.DATE);
			String endtime = yearstr + "-" + monthstr + "-" + maxday
					+ " 00:00:00";

			Income p = new Income();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(Integer.parseInt(yearstr));
			p.setMonth(Integer.parseInt(monthstr));

			incomeList = reportService.getIncomeSmyByDayList(p);

			int recordnum = 0;

			if (incomeList != null && incomeList.size() > 0)
				recordnum = incomeList.size();

			Excel2007WriterIncomeMonthByDay writer = new Excel2007WriterIncomeMonthByDay();
			writer.createSheet();
			writer.process(outfile, incomeList, 0, recordnum);

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
					+ new String((yearstr + "-" + monthstr + "_收款月汇总.xlsx")
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

		return IMPORT_PAGE;
	}

	// 按人汇总
	public String exportMailPriceMonthDetailByManager() throws IOException,
			ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		String manager = request.getParameter("manager");

		if (manager == null || manager.equals("")) {
			manager = null;
		} else {
			manager = new String(request.getParameter("manager").getBytes(
					"ISO8859-1"), "utf-8");

		}
		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_" + manager + "_面单费用明细.xlsx";
		try {

			// 中转费明细

			String starttime = yearstr + "-" + monthstr + "-01" + " 00:00:00";

			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtil.parse(starttime, DateUtil.DATE_TIME_FORMAT));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int maxday = c.getActualMaximum(Calendar.DATE);
			String endtime = yearstr + "-" + monthstr + "-" + maxday
					+ " 00:00:00";
			int mon = Integer.parseInt(monthstr);
			int year = Integer.parseInt(yearstr);
			PageParameter p = new PageParameter();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(mon);
			p.setState("normal");
			p.setManager(manager);
			mailFenpeiList = reportService.getMailFenpeiList(p);

			int recordnum = 0;

			if (mailFenpeiList != null && mailFenpeiList.size() > 0)
				recordnum = mailFenpeiList.size();

			// 收款明细：

			Income p1 = new Income();
			p1.setStart(0);
			p1.setSize(Integer.MAX_VALUE);
			p1.setYear(year);
			p1.setMonth(mon);
			p1.setManager(manager);
			incomeList = reportService.getIncomeList(p1);

			int recordnum1 = 0;

			if (incomeList != null && incomeList.size() > 0)
				recordnum1 = incomeList.size();

			// Excel2007WriterIncomeDetail writer = new
			// Excel2007WriterIncomeDetail();

			Excel2007WriterMailPriceDetail writer = new Excel2007WriterMailPriceDetail();
			writer.createSheet("面单收入");
			writer.process(outfile, mailFenpeiList, 0, recordnum);
			writer.createSheet("收款明细");
			writer.process2(outfile, incomeList, 0, recordnum1);
			writer.createFile(outfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String path = outfile;

		// 保存文件
		response.setContentType("application/vnd.ms-excel;charset=GBK");

		try {
			response.setHeader("Content-disposition",
					"attachment; filename="
							+ new String((yearstr + "-" + monthstr + "_"
									+ manager + "_面单费用明细.xlsx")
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

		return IMPORT_PAGE;
	}

	// 导出个人每日的中转费及其他费用的数据
	public String exportZZFFileByManager() throws IOException, ParseException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String start = request.getParameter("start");
		String manager = new String(request.getParameter("manager").getBytes(
				"ISO8859-1"), "utf-8");
		Date bsndate = DateUtil.parse(start + " 00:00:00",
				DateUtil.DATE_TIME_FORMAT);
		String outfile = this.getEnv("downpath") + "/" + start + "_" + manager
				+ "_日揽件及其他明细.xlsx";
		try {

			// 中转费明细
			String starttime = start + " " + "12:00:00";
			String endtime = start + " " + "12:00:00";

			String starttime2 = start + " " + "00:00:00";
			String endtime2 = start + " " + "00:00:00";
			int mon = Integer.parseInt(start.substring(5, 7));
			int year = Integer.parseInt(start.substring(0, 4));
			Date dates = null;
			try {
				dates = DateUtil.parse(starttime, DateUtil.DATE_TIME_FORMAT);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PageParameter p = new PageParameter();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setEndtime(endtime);
			p.setStarttime(starttime);
			p.setState("normal");
			p.setManager(manager);
			p.setMon(mon);
			statReportList = mailService.queryForStatReportPage(p);

			if (statReportList == null)
				statReportList = new ArrayList<StatReport>();

			ManagerInfo mm = new ManagerInfo();
			mm.setManagername(manager);
			List<ManagerInfo> subManagerList = mailService
					.queryForSubManager(mm);

			if (subManagerList != null && subManagerList.size() > 0) {

				int len = subManagerList.size();
				for (int i = 0; i < len; i++) {

					p = new PageParameter();
					p.setStart(0);
					p.setSize(Integer.MAX_VALUE);
					p.setEndtime(endtime);
					p.setStarttime(starttime);
					p.setState("normal");
					p.setManager(subManagerList.get(i).getManagername());
					p.setMon(mon);
					List<StatReport> statReportListTmp = mailService
							.queryForStatReportPage(p);

					if (statReportListTmp != null
							&& statReportListTmp.size() > 0) {

						statReportList.addAll(statReportListTmp);

					}

				}

			}

			int recordnum1 = 0;

			if (statReportList != null && statReportList.size() > 0)
				recordnum1 = statReportList.size();

			// 其他费用

			PageParameter p2 = new PageParameter();
			p2.setStart(0);
			p2.setSize(Integer.MAX_VALUE);
			p2.setEndtime(endtime2);
			p2.setStarttime(starttime2);
			p2.setManager(manager);
			p2.setMon(year);
			priceDetailList = mailService.getPriceDetailList(p2);

			int recordnum = 0;

			if (priceDetailList != null && priceDetailList.size() > 0)
				recordnum = priceDetailList.size();

			Excel2007WriterZZF writer = new Excel2007WriterZZF();
			writer.createSheet("中转费");
			writer.process(outfile, statReportList, 0, recordnum1);
			writer.createSheet("其他费用");
			writer.process2(outfile, priceDetailList, 0, recordnum);
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
					+ new String((start + "_" + manager + "_日揽件及其他明细.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportCompanyIncomeYearByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + yearstr
				+ "_年公司收入汇总表按揽件人.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);

			companyIncomeList = reportService.getCompanyIncomeSmyList(p);

			int recordnum = 0;
			if (companyIncomeList != null && companyIncomeList.size() > 0)
				recordnum = companyIncomeList.size();

			Excel2007WriterCompanyIncomeYearSmy writer = new Excel2007WriterCompanyIncomeYearSmy();
			writer.createSheet();
			writer.process(outfile, companyIncomeList, 0, recordnum);
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
					+ new String((yearstr + "_年公司收入汇总表按揽件人.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportCompanyIncomeYearByMonth() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + yearstr
				+ "_年公司收入汇总表按日期.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);

			companyIncomeList = reportService.getCompanyIncomeSmyByMonthList(p);

			int recordnum = 0;
			if (companyIncomeList != null && companyIncomeList.size() > 0)
				recordnum = companyIncomeList.size();

			Excel2007WriterCompanyIncomeYearSmyByMonth writer = new Excel2007WriterCompanyIncomeYearSmyByMonth();
			writer.createSheet();
			writer.process(outfile, companyIncomeList, 0, recordnum);
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
					+ new String((yearstr + "_年公司收入汇总表按日期.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportMailPriceYearByMonth() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + yearstr
				+ "_年面单收入汇总表按日期.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			mailFenpeiList = reportService.getMailFenpeiSmyByMonthList(p);

			int recordnum = 0;
			if (mailFenpeiList != null && mailFenpeiList.size() > 0)
				recordnum = mailFenpeiList.size();

			Excel2007WriterMailPriceYearByMonth writer = new Excel2007WriterMailPriceYearByMonth();
			writer.createSheet();
			writer.process(outfile, mailFenpeiList, 0, recordnum);
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
					+ new String((yearstr + "_年面单收入汇总表按日期.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportMailPriceYearByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + yearstr
				+ "_年面单收入汇总表按揽件人.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			mailFenpeiList = reportService.getMailFenpeiSmyList(p);

			int recordnum = 0;
			if (mailFenpeiList != null && mailFenpeiList.size() > 0)
				recordnum = mailFenpeiList.size();

			Excel2007WriterMailPriceMonthByManager writer = new Excel2007WriterMailPriceMonthByManager();
			writer.createSheet();
			writer.process(outfile, mailFenpeiList, 0, recordnum);
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
					+ new String((yearstr + "_年面单收入汇总表按揽件人.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportMailPriceMonthByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		int year = Integer.parseInt(yearstr);
		int month = Integer.parseInt(monthstr);

		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_月面单收入汇总表按揽件人.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);
			mailFenpeiList = reportService.getMailFenpeiSmyList(p);

			int recordnum = 0;
			if (mailFenpeiList != null && mailFenpeiList.size() > 0)
				recordnum = mailFenpeiList.size();

			Map<String, Income> incomeSmyMap = new HashMap<String, Income>();
			Income p1 = new Income();

			p1.setStart(0);
			p1.setSize(Integer.MAX_VALUE);
			p1.setYear(year);
			p1.setMonth(month);
			incomeList = reportService.getIncomeSmyList(p1);
			if (incomeList != null && incomeList.size() > 0) {
				int len = 0;
				len = incomeList.size();

				for (int i = 0; i < len; i++) {
					Income m = incomeList.get(i);
					incomeSmyMap.put(m.getManagerid(), m);

				}

			}

			if (mailFenpeiList != null && mailFenpeiList.size() > 0) {
				int len = 0;
				len = mailFenpeiList.size();

				for (int i = 0; i < len; i++) {
					MailFenpei m = mailFenpeiList.get(i);

					if (incomeSmyMap.get(m.getManagerid()) != null) {
						Income in = incomeSmyMap.get(m.getManagerid());
						m.setYfnum(in.getNum());
						m.setYfprice(in.getPrice());

						m.setWfnum(m.getNum() - m.getYfnum());
						m.setWfprice(m.getPrice() - m.getYfprice());

					} else {

						m.setWfnum(m.getNum() - m.getYfnum());
						m.setWfprice(m.getPrice() - m.getYfprice());

					}

				}

			}

			// 未付款
			Excel2007WriterMailPriceMonthByManager writer = new Excel2007WriterMailPriceMonthByManager();
			writer.createSheet();
			writer.process(outfile, mailFenpeiList, 0, recordnum);
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
					+ new String(
							(yearstr + "-" + monthstr + "_月面单收入汇总表按揽件人.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportMailPriceMonthByDay() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		int year = Integer.parseInt(yearstr);
		int month = Integer.parseInt(monthstr);

		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_月面单收入汇总表按日期.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);
			mailFenpeiList = reportService.getMailFenpeiSmyByDayList(p);
			int recordnum = 0;
			if (mailFenpeiList != null && mailFenpeiList.size() > 0)
				recordnum = mailFenpeiList.size();

			Excel2007WriterMailPriceMonthByDay writer = new Excel2007WriterMailPriceMonthByDay();
			writer.createSheet();
			writer.process(outfile, mailFenpeiList, 0, recordnum);
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
					+ new String(
							(yearstr + "-" + monthstr + "_月面单收入汇总表按揽件人.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportCompanyIncomeMonthByDay() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		int year = Integer.parseInt(yearstr);
		int month = Integer.parseInt(monthstr);

		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_月公司收入汇总表按日期.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);

			companyIncomeList = reportService.getCompanyIncomeSmyByDayList(p);

			int recordnum = 0;
			if (companyIncomeList != null && companyIncomeList.size() > 0)
				recordnum = companyIncomeList.size();

			Excel2007WriterCompanyIncomeMonthSmyByDay writer = new Excel2007WriterCompanyIncomeMonthSmyByDay();
			writer.createSheet();
			writer.process(outfile, companyIncomeList, 0, recordnum);
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
					+ new String(
							(yearstr + "-" + monthstr + "_月公司收入汇总表按日期.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportCompanyIncomeMonthByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		int year = Integer.parseInt(yearstr);
		int month = Integer.parseInt(monthstr);

		String outfile = this.getEnv("downpath") + "/" + yearstr + "-"
				+ monthstr + "_月公司收入汇总表按揽件人.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setYear(year);
			p.setMonth(month);

			companyIncomeList = reportService.getCompanyIncomeSmyList(p);
			int recordnum = 0;
			if (companyIncomeList != null && companyIncomeList.size() > 0)
				recordnum = companyIncomeList.size();

			Excel2007WriterCompanyIncomeMonthSmy writer = new Excel2007WriterCompanyIncomeMonthSmy();
			writer.createSheet();
			writer.process(outfile, companyIncomeList, 0, recordnum);
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
					+ new String(
							(yearstr + "-" + monthstr + "_月公司收入汇总表按揽件人.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportCompanyIncomeDaySmy() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String startstr = request.getParameter("start");

		Date date = null;
		try {
			date = DateUtil.parse(startstr + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String outfile = this.getEnv("downpath") + "/" + startstr
				+ "_companyincomedaysmy.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setBsndate(date);

			companyIncomeList = reportService.getCompanyIncomeSmyList(p);

			int recordnum = 0;
			if (companyIncomeList != null && companyIncomeList.size() > 0)
				recordnum = companyIncomeList.size();

			Excel2007WriterCompanyIncomeDaySmy writer = new Excel2007WriterCompanyIncomeDaySmy();
			writer.createSheet();
			writer.process(outfile, companyIncomeList, 0, recordnum);
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
					+ new String((startstr + "_companyincomedaysmy.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportMailPriceDaySmy() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String startstr = request.getParameter("start");

		Date date = null;
		try {
			date = DateUtil.parse(startstr + " 00:00:00",
					DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String outfile = this.getEnv("downpath") + "/" + startstr
				+ "_mailpricedaysmy.xlsx";
		try {

			PageParameter p = new PageParameter();

			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setBsndate(date);
			mailFenpeiList = reportService.getMailFenpeiList(p);

			int recordnum = 0;
			if (mailFenpeiList != null && mailFenpeiList.size() > 0)
				recordnum = mailFenpeiList.size();

			Excel2007WriterMailPriceDaySmy writer = new Excel2007WriterMailPriceDaySmy();
			writer.createSheet();
			writer.process(outfile, mailFenpeiList, 0, recordnum);
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
					+ new String((startstr + "_mailpricedaysmy.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportDailyBalanceYearByMonth() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + year
				+ "_yearbalance.xlsx";
		try {

			DailyBalance db = new DailyBalance();
			db.setYear(year);
			dailybalanceList = reportService.getDailyBalanceSmyByMonth(db);

			int recordnum = 0;
			if (dailybalanceList != null && dailybalanceList.size() > 0)
				recordnum = dailybalanceList.size();

			Excel2007WriterDailyBalanceYearByMonth writer = new Excel2007WriterDailyBalanceYearByMonth();
			writer.createSheet();
			writer.process(outfile, dailybalanceList, 0, recordnum);
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
					+ new String((year + "_dailybalance.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportDailyBalanceYearByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + year
				+ "_yearbalance.xlsx";
		try {

			DailyBalance db = new DailyBalance();
			db.setYear(year);
			List<DailyBalance> dailybalanceList2 = reportService.getDailyBalanceSmyByManager(db);
			int recordnum2 = 0;
			if (dailybalanceList2 != null && dailybalanceList2.size() > 0)
				recordnum2 = dailybalanceList2.size();
			
 			 db = new DailyBalance();
 			db.setYear(year);
 			dailybalanceList = reportService.getDailyBalanceForStatuserByYear(db);

			int recordnum = 0;
			if (dailybalanceList != null && dailybalanceList.size() > 0)
				recordnum = dailybalanceList.size();

			Excel2007WriterDailyBalanceYearByManager writer = new Excel2007WriterDailyBalanceYearByManager();
			writer.createSheet("结算用户汇总");
			writer.process(outfile, dailybalanceList, 0, recordnum);
			writer.createSheet("所有用户汇总");
			writer.process(outfile, dailybalanceList2, 0, recordnum2);
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
					+ new String((year + "_dailybalance.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportDailyBalanceMonthByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");

		String monthstr = request.getParameter("month");

		int mon = Integer.parseInt(monthstr);
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + year + "-" + mon
				+ "_monthbalance.xlsx";
		try {

 			DailyBalance db = new DailyBalance();
 			db.setYear(year);
 			db.setMonth(mon);
 			List<DailyBalance> dailybalanceList2	 = reportService.getDailyBalanceSmyByManager(db);
 			int recordnum2 = 0;
			if (dailybalanceList2 != null && dailybalanceList2.size() > 0)
				recordnum2 = dailybalanceList2.size();
			
		    db = new DailyBalance();
 			db.setYear(year);
 			db.setMonth(mon);
 			dailybalanceList = reportService.getDailyBalanceForStatuserByMonth(db);
			int recordnum = 0;
			if (dailybalanceList != null && dailybalanceList.size() > 0)
				recordnum = dailybalanceList.size();

			Excel2007WriterDailyBalanceMonthByManager writer = new Excel2007WriterDailyBalanceMonthByManager();
			writer.createSheet("结算用户汇总");
			writer.process(outfile, dailybalanceList, 0, recordnum);
			
			writer.createSheet("所有用户汇总");
			writer.process(outfile, dailybalanceList2, 0, recordnum2);
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
					+ new String((year + "-" + mon + "_dailybalance.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportZZFDaySmyFileByManagerDetail() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");
		String monthstr = request.getParameter("month");
		String manager = new String(request.getParameter("manager").getBytes(
				"ISO8859-1"), "utf-8");
		int mon = Integer.parseInt(monthstr);
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + year + "-" + mon + "_"
				+ manager + "_月明细.xlsx";
		try {

			// 中转费明细
			String starttime = year + "-" + mon + "-01" + " 12:00:00";
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtil.parse(starttime, DateUtil.DATE_TIME_FORMAT));

			int maxday = c.getActualMaximum(Calendar.DATE);
			String endtime = year + "-" + mon + "-" + maxday + " 12:00:00";

			String starttime2 = year + "-" + mon + "-01" + " 00:00:00";
			String endtime2 = year + "-" + mon + "-" + maxday + " 00:00:00";

			Date dates = null;
			try {
				dates = DateUtil.parse(starttime, DateUtil.DATE_TIME_FORMAT);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 

			PageParameter p = new PageParameter();
			p.setStart(0);
			p.setSize(Integer.MAX_VALUE);
			p.setEndtime(endtime);
			p.setStarttime(starttime);
			p.setState("normal");
			p.setManager(manager);
			p.setMon(mon);
			statReportList = mailService.queryForStatReportPage(p);

			if (statReportList == null)
				statReportList = new ArrayList<StatReport>();

			ManagerInfo mm = new ManagerInfo();
			mm.setManagername(manager);
			List<ManagerInfo> subManagerList = mailService
					.queryForSubManager(mm);

			if (subManagerList != null && subManagerList.size() > 0) {

				int len = subManagerList.size();
				for (int i = 0; i < len; i++) {

					p = new PageParameter();
					p.setStart(0);
					p.setSize(Integer.MAX_VALUE);
					p.setEndtime(endtime);
					p.setStarttime(starttime);
					p.setState("normal");
					p.setManager(subManagerList.get(i).getManagername());
					p.setMon(mon);
					List<StatReport> statReportListTmp = mailService
							.queryForStatReportPage(p);

					if (statReportListTmp != null
							&& statReportListTmp.size() > 0) {

						statReportList.addAll(statReportListTmp);

					}

				}

			}
			int recordnum1 = 0;

			if (statReportList != null && statReportList.size() > 0)
				recordnum1 = statReportList.size();

			// 其他费用

			PageParameter p2 = new PageParameter();
			p2.setStart(0);
			p2.setSize(Integer.MAX_VALUE);
			p2.setEndtime(endtime2);
			p2.setStarttime(starttime2);
			p2.setManager(manager);
			p2.setMon(year);
			priceDetailList = mailService.getPriceDetailList(p2);

			int recordnum = 0;

			if (priceDetailList != null && priceDetailList.size() > 0)
				recordnum = priceDetailList.size();

			Excel2007WriterZZF writer = new Excel2007WriterZZF();
			writer.createSheet("中转费");
			writer.process(outfile, statReportList, 0, recordnum1);
			writer.createSheet("其他费用");
			writer.process2(outfile, priceDetailList, 0, recordnum);
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
					+ new String(
							(year + "-" + mon + "_" + manager + "_月明细.xlsx")
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

		return IMPORT_PAGE;
	}

	// 导出中转费月汇总

	public String exportZZFMonthSmyFileByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");

		String manager = new String(request.getParameter("manager").getBytes(
				"ISO8859-1"), "utf-8");
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + year + "_" + manager
				+ "_中转费月汇总.xlsx";
		try {

			DailyBalance db = new DailyBalance();
			db.setYear(year);
			db.setManager(manager);
			List<DailyBalance> dailybalanceList2 = reportService
					.getDailyBalanceMonthSmyByManager(db);
			
			
			ManagerInfo mm = new ManagerInfo();
			mm.setManagername(manager);
			List<ManagerInfo> subManagerList = mailService
					.queryForSubManager(mm);

			if (subManagerList != null && subManagerList.size() > 0) {

				int len = subManagerList.size();
				for (int i = 0; i < len; i++) {

				 
					db.setManager(subManagerList.get(i).getManagername());
					 
					List<DailyBalance>  dailybalanceList3 = reportService.getDailyBalanceMonthSmyByManager(db);

					if (dailybalanceList3 != null
							&& dailybalanceList3.size() > 0) {

						dailybalanceList2.addAll(dailybalanceList3);

					}

				}

			}
			int recordnum2 = 0;
			if (dailybalanceList2 != null && dailybalanceList2.size() > 0)
				recordnum2 = dailybalanceList2.size();
			
			
			db = new DailyBalance();
			db.setYear(year);
			db.setManager(manager);
			dailybalanceList = reportService
					.getDailyBalanceForStatuserByMonth(db);

			int recordnum = 0;
			if (dailybalanceList != null && dailybalanceList.size() > 0)
				recordnum = dailybalanceList.size();

			Excel2007WriterDailyBalanceMonthByManager writer = new Excel2007WriterDailyBalanceMonthByManager();
			writer.createSheet("结算用户汇总");
			writer.process(outfile, dailybalanceList, 0, recordnum);
			writer.createSheet("所有用户汇总");
			writer.process(outfile, dailybalanceList2, 0, recordnum2);
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
					+ new String((year + "_" + manager + "_月汇总.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportZZFDaySmyFileByManager() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");

		String monthstr = request.getParameter("month");

		String manager = new String(request.getParameter("manager").getBytes(
				"ISO8859-1"), "utf-8");
		int mon = Integer.parseInt(monthstr);
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + year + "-" + mon + "_"
				+ manager + "_月汇总.xlsx";
		try {

			DailyBalance db = new DailyBalance();
			db.setYear(year);
			db.setMonth(mon);
			db.setManager(manager);
			List<DailyBalance> dailybalanceList2 = reportService.getDailyBalance(db);
			
			ManagerInfo mm = new ManagerInfo();
			mm.setManagername(manager);
			List<ManagerInfo> subManagerList = mailService
					.queryForSubManager(mm);

			if (subManagerList != null && subManagerList.size() > 0) {

				int len = subManagerList.size();
				for (int i = 0; i < len; i++) {

				 
					if(!subManagerList.get(i).getManagername().equals(manager)){
					db.setManager(subManagerList.get(i).getManagername());
					 
					List<DailyBalance>  dailybalanceList3 = reportService.getDailyBalance(db);

					if (dailybalanceList3 != null
							&& dailybalanceList3.size() > 0) {

						dailybalanceList2.addAll(dailybalanceList3);

					}
					}

				}

			}
			
			int recordnum2 = 0;
			if (dailybalanceList2 != null && dailybalanceList2.size() > 0)
				recordnum2 = dailybalanceList2.size();
			
			db = new DailyBalance();
			db.setYear(year);
			db.setMonth(mon);
			db.setManager(manager);
			dailybalanceList = reportService.getDailyBalanceForStatuser(db);

			int recordnum = 0;
			if (dailybalanceList != null && dailybalanceList.size() > 0)
				recordnum = dailybalanceList.size();

			Excel2007WriterDailyBalanceMonthByManagerDetail writer = new Excel2007WriterDailyBalanceMonthByManagerDetail();
			writer.createSheet("结算用户汇总");
			writer.process(outfile, dailybalanceList, 0, recordnum);
			writer.createSheet("所有用户汇总");
			writer.process(outfile, dailybalanceList2, 0, recordnum2);
			
			
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
					+ new String(
							(year + "-" + mon + "_" + manager + "_月汇总.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportDailyBalanceMonthByDay() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String yearstr = request.getParameter("year");

		String monthstr = request.getParameter("month");

		int mon = Integer.parseInt(monthstr);
		int year = Integer.parseInt(yearstr);

		String outfile = this.getEnv("downpath") + "/" + year + "-" + mon
				+ "_monthbalance.xlsx";
		try {

			DailyBalance db = new DailyBalance();
			db.setYear(year);
			db.setMonth(mon);
			dailybalanceList = reportService.getDailyBalanceSmyByDay(db);

			int recordnum = 0;
			if (dailybalanceList != null && dailybalanceList.size() > 0)
				recordnum = dailybalanceList.size();

			Excel2007WriterDailyBalanceMonthByDay writer = new Excel2007WriterDailyBalanceMonthByDay();
			writer.createSheet();
			writer.process(outfile, dailybalanceList, 0, recordnum);
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
					+ new String((year + "-" + mon + "_dailybalance.xlsx")
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

		return IMPORT_PAGE;
	}

	public String exportDailyBalance() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(WebWorkStatics.HTTP_RESPONSE);
		String date = request.getParameter("start");

		String bsndates = date + " " + "00:00:00";
		String starttime = date + " " + "00:00:00";
		String endtime = date + " " + "00:00:00";
		int mon = Integer.parseInt(date.substring(5, 7));
		int year = Integer.parseInt(date.substring(0, 4));
		Date dates = null;
		try {
			dates = DateUtil.parse(bsndates, DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String outfile = this.getEnv("downpath") + "/" + date
				+ "_dailybalance.xlsx";
		try {

			 DailyBalance db = new DailyBalance();
			 db.setBsndate(dates);
			 List<DailyBalance> dailybalanceList2 = reportService.getDailyBalance(db);

				int recordnum2 = 0;
				if (dailybalanceList2 != null && dailybalanceList2.size() > 0)
					recordnum2 = dailybalanceList2.size();
		    db = new DailyBalance();
			db.setBsndate(dates);
			dailybalanceList = reportService.getDailyBalanceForStatuser(db);

			int recordnum = 0;
			if (dailybalanceList != null && dailybalanceList.size() > 0)
				recordnum = dailybalanceList.size();

			Excel2007WriterDailyBalance writer = new Excel2007WriterDailyBalance();
			writer.createSheet("结算用户汇总");
			writer.process(outfile, dailybalanceList, 0, recordnum);
			writer.createSheet("所有用户汇总");
			writer.process(outfile, dailybalanceList2, 0, recordnum2);
			
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
					+ new String((date + "_dailybalance.xlsx")
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

		return IMPORT_PAGE;
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

	public String showGongziMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			} else {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
				yearSmy.setMonth(month);
			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		GongziOutcome gos = new GongziOutcome();
		gos.setYear(yearSmy.getYear());
		gos.setMonth(yearSmy.getMonth());
		gongziOutcomeList = reportService.getGongziOutcomeList(gos);

		return "showgongzimonthsmypage";

	}

	public String showFankuanMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			} else {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
				yearSmy.setMonth(month);
			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		FankuanOutcome gos = new FankuanOutcome();
		gos.setYear(yearSmy.getYear());
		gos.setMonth(yearSmy.getMonth());
		fankuanOutcomeList = reportService.getFankuanOutcomeList(gos);

		return "showfankuanmonthsmypage";

	}

	public String showPeikuanMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			} else {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
				yearSmy.setMonth(month);
			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		PeikuanOutcome peikuan = new PeikuanOutcome();
		peikuan.setYear(yearSmy.getYear());
		peikuan.setMonth(yearSmy.getMonth());
		peikuan.setStart(0);
		peikuan.setSize(Integer.MAX_VALUE);
		peikuanOutcomeList = reportService.getPeikuanOutcomeSmyList(peikuan);

		peikuanOutcome = new PeikuanOutcome();
		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0) {

			int len = peikuanOutcomeList.size();
			double yingpei = 0;
			double realpei = 0;
			int num = 0;
			for (int i = 0; i < len; i++) {
				PeikuanOutcome p = peikuanOutcomeList.get(i);
				yingpei += p.getYingpeiprice();
				realpei += p.getRealpeiprice();
				num += p.getNum();

			}
			peikuanOutcome.setNum(num);
			peikuanOutcome.setYingpeiprice(yingpei);
			peikuanOutcome.setRealpeiprice(realpei);

		}

		return "showpeikuanmonthsmypage";

	}

	public String showZhichuMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			} else {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
				yearSmy.setMonth(month);
			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		ZhichuOutcome p = new ZhichuOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhichuOutcomeList = reportService.getZhichuOutcomeList(p);

		if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0) {

			int len = zhichuOutcomeList.size();
			zhichuOutcome = new ZhichuOutcome();

			double totalprice = 0;

			for (int i = 0; i < len; i++) {

				totalprice = totalprice + zhichuOutcomeList.get(i).getPrice();
			}
			BigDecimal b = new BigDecimal(totalprice);
			// 保留2位小数
			totalprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			zhichuOutcome.setPrice(totalprice);

		}

		return "showzhichumonthsmypage";

	}

	public String showZhipaoMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			} else {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
				yearSmy.setMonth(month);
			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		ZhipaoOutcome p = new ZhipaoOutcome();
		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(yearSmy.getYear());
		p.setMonth(yearSmy.getMonth());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeList(p);

		if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0) {

			int len = zhipaoOutcomeList.size();
			zhipaoOutcome = new ZhipaoOutcome();

			double totalprice = 0;

			for (int i = 0; i < len; i++) {

				totalprice = totalprice
						+ zhipaoOutcomeList.get(i).getShouldincome();
			}
			BigDecimal b = new BigDecimal(totalprice);
			// 保留2位小数
			totalprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			zhipaoOutcome.setShouldincome(totalprice);

		}

		return "showzhipaomonthsmypage";

	}

	public String showYufukuanMonthSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);

		int year = 0;
		int month = 0;
		if (yearSmy == null
				|| (yearSmy.getYear() == null && yearSmy.getMonth() == null)) {

			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			month = Integer.parseInt(todaystr.substring(5, 7));
			YearSmy smy = new YearSmy();
			smy.setMonth(month);
			smy.setYear(year);
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0) {
				yearSmy = yearSmyList.get(0);

			} else {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);
				yearSmy.setMonth(month);
			}

		} else {

			year = yearSmy.getYear();
			month = yearSmy.getMonth();
			YearSmy smy = new YearSmy();
			smy.setMonth(yearSmy.getMonth());
			smy.setYear(yearSmy.getYear());
			List<YearSmy> yearSmyList = reportService.getTMonthSmy(smy);

			if (yearSmyList != null && yearSmyList.size() > 0)
				yearSmy = yearSmyList.get(0);

		}

		return "showyufukuanmonthsmypage";

	}

	public String showMainReportPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		// 列举出当月每个人汇总情况，这里是按人

		Date day = new Date();
		Integer years = Integer.parseInt(DateUtil.formatYYYYMMDD(day)
				.substring(0, 4));
		YearSmy smy = new YearSmy();
		smy.setYear(yearSmy.getYear());
		List<YearSmy> yearSmyListTmp = reportService.getTMonthSmy(smy);
		HashMap<Integer, YearSmy> yearSmyMap = new HashMap<Integer, YearSmy>();
		if (yearSmyListTmp != null && yearSmyListTmp.size() > 0) {
			int len = yearSmyListTmp.size();

			for (int i = 0; i < len; i++) {
				YearSmy tmp = yearSmyListTmp.get(i);
				yearSmyMap.put(tmp.getMonth(), tmp);

			}

		}

		yearSmyList = new ArrayList();
		for (int i = 0; i < 12; i++) {

			YearSmy yearsmy = new YearSmy();
			yearsmy.setYear(yearSmy.getYear());
			yearsmy.setMonth(Integer.valueOf((i + 1)));
			if (yearSmyMap.get(Integer.valueOf((i + 1))) == null) {
				yearSmyList.add(i, yearsmy);

			} else {
				yearSmyList.add(i, yearSmyMap.get(Integer.valueOf((i + 1))));
			}

		}

		return "showmainreportpage";

	}

	public String showZhipaoYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		ZhipaoOutcome zp = new ZhipaoOutcome();

		zp.setYear(yearSmy.getYear());
		zhipaoOutcomeList = reportService.getZhipaoOutcomeSmyByMonthList(zp);

		if (zhipaoOutcomeList != null && zhipaoOutcomeList.size() > 0) {

			int len = zhipaoOutcomeList.size();
			zhipaoOutcome = new ZhipaoOutcome();

			double totalprice = 0;

			for (int i = 0; i < len; i++) {

				totalprice = totalprice
						+ zhipaoOutcomeList.get(i).getShouldincome();
			}
			BigDecimal b = new BigDecimal(totalprice);
			// 保留2位小数
			totalprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			zhipaoOutcome.setShouldincome(totalprice);

		}

		return "showzhipaoyearsmypage";

	}

	public String showZhichuYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		ZhichuOutcome zp = new ZhichuOutcome();

		zp.setYear(yearSmy.getYear());
		zhichuOutcomeList = reportService.getZhichuOutcomeSmyByMonthList(zp);

		if (zhichuOutcomeList != null && zhichuOutcomeList.size() > 0) {

			int len = zhichuOutcomeList.size();
			zhichuOutcome = new ZhichuOutcome();

			double totalprice = 0;

			for (int i = 0; i < len; i++) {

				totalprice = totalprice + zhichuOutcomeList.get(i).getPrice();
			}
			BigDecimal b = new BigDecimal(totalprice);
			// 保留2位小数
			totalprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			zhichuOutcome.setPrice(totalprice);

		}

		return "showzhichuyearsmypage";

	}

	public String showGongziYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		GongziOutcome zp = new GongziOutcome();

		zp.setYear(yearSmy.getYear());
		gongziOutcomeList = reportService.getGongziOutcomeSmyByMonthList(zp);

		if (gongziOutcomeList != null && gongziOutcomeList.size() > 0) {

			int len = gongziOutcomeList.size();
			gongziOutcome = new GongziOutcome();

			double totalprice = 0;

			for (int i = 0; i < len; i++) {

				totalprice = totalprice + gongziOutcomeList.get(i).getSalary();
			}
			BigDecimal b = new BigDecimal(totalprice);
			// 保留2位小数
			totalprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			gongziOutcome.setSalary(totalprice);

		}

		return "showgongziyearsmypage";

	}

	public String showFankuanYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		FankuanOutcome zp = new FankuanOutcome();

		zp.setYear(yearSmy.getYear());
		fankuanOutcomeList = reportService.getFankuanOutcomeSmyByMonthList(zp);

		if (fankuanOutcomeList != null && fankuanOutcomeList.size() > 0) {

			int len = fankuanOutcomeList.size();
			fankuanOutcome = new FankuanOutcome();

			double totalprice = 0;

			for (int i = 0; i < len; i++) {

				totalprice = totalprice
						+ fankuanOutcomeList.get(i).getFankuan();
			}
			BigDecimal b = new BigDecimal(totalprice);
			// 保留2位小数
			totalprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			fankuanOutcome.setFankuan(totalprice);

		}

		return "showfankuanyearsmypage";

	}

	public String showPeikuanYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		PeikuanOutcome zp = new PeikuanOutcome();

		zp.setYear(yearSmy.getYear());
		peikuanOutcomeList = reportService.getPeikuanOutcomeSmyByMonthList(zp);

		if (peikuanOutcomeList != null && peikuanOutcomeList.size() > 0) {

			int len = peikuanOutcomeList.size();
			peikuanOutcome = new PeikuanOutcome();

			double totalprice = 0;
			double totalprice2 = 0;
			int num = 0;
			for (int i = 0; i < len; i++) {

				totalprice = totalprice
						+ peikuanOutcomeList.get(i).getYingpeiprice();
				totalprice2 = totalprice2
						+ peikuanOutcomeList.get(i).getRealpeiprice();
				num = num + peikuanOutcomeList.get(i).getNum();
			}
			BigDecimal b = new BigDecimal(totalprice);
			// 保留2位小数
			totalprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			peikuanOutcome.setYingpeiprice(totalprice);

			b = new BigDecimal(totalprice);
			// 保留2位小数
			totalprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			peikuanOutcome.setRealpeiprice(totalprice2);
			peikuanOutcome.setNum(num);

		}

		return "showpeikuanyearsmypage";

	}

	public String showYufukuanYearSmyPage() throws IOException {

		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		int year = 0;
		if (yearSmy == null || yearSmy.getYear() == null) {
			Date today = new Date();
			String todaystr = DateUtil.formatYYYYMMDD(today);
			year = Integer.parseInt(todaystr.substring(0, 4));
			YearSmy smy = new YearSmy();
			smy.setYear(year);
			yearSmy = reportService.getTYearSmy(smy);

		} else {

			year = yearSmy.getYear();
			YearSmy smy = new YearSmy();
			smy.setYear(yearSmy.getYear());
			yearSmy = reportService.getTYearSmy(smy);

			if (yearSmy == null) {
				yearSmy = new YearSmy();
				yearSmy.setYear(year);

			}

		}

		return "showyufukuanyearsmypage";

	}

}
