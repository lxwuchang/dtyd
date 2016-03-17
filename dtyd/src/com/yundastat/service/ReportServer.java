/**
 * 
 */
package com.yundastat.service;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.yundastat.model.FankuanOutcome;
import com.yundastat.model.GongziOutcome;
import com.yundastat.model.Income;
import com.yundastat.model.MailFenpei;
import com.yundastat.model.PageParameter;
import com.yundastat.model.YearSmy;
import com.yundastat.util.DateUtil;


/**
 * @author Simon
 *
 */
public class ReportServer {
	
	
 public static final String baseurl ="";
 
 private ReportService reportService;

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
 /***
  * 
  * 该函数的功能返回剩余付款数量和金额
 * @throws FileNotFoundException 
* @throws IOException 
  * */

	public Income getIncome(String date,String managerid) throws FileNotFoundException{
	
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));

		Income in=new Income();
		PageParameter p1 = new PageParameter();

		p1.setStart(0);
		p1.setSize(Integer.MAX_VALUE);
		p1.setYear(year);
		p1.setMonth(month);
		p1.setManagerid(managerid);
		List<MailFenpei>	mailFenpeiList = reportService.getMailFenpeiSmyList(p1);

		Income p = new Income();

		p.setStart(0);
		p.setSize(Integer.MAX_VALUE);
		p.setYear(year);
		p.setMonth(month);
		p.setManagerid(managerid);
		List<Income>	incomeList = reportService.getIncomeSmyList(p);
		Income mm = null;
		if (incomeList != null && incomeList.size() == 1) {

			mm = incomeList.get(0);

		} else
			mm = new Income();

		if (mailFenpeiList != null && mailFenpeiList.size() == 1) {

			MailFenpei mf = mailFenpeiList.get(0);

			int num=mf.getNum() - mm.getNum();
			double price =mf.getPrice() - mm.getPrice();
			
			in.setNum(num);
			in.setPrice(price);
				
		
		}
		
		 
	 return in;
	}

	
	public String updateGongziOutcome(GongziOutcome gongzi) throws FileNotFoundException, ParseException{
		
	 
		GongziOutcome gos=new GongziOutcome();
	    gos.setOutcomeid(gongzi.getOutcomeid());
		List<GongziOutcome> gongziOutcomeList=reportService.getGongziOutcomeList(gos);
		
		if(gongziOutcomeList!=null&&gongziOutcomeList.size()==1){
			
			GongziOutcome gz=	gongziOutcomeList.get(0);
			
			gz.setJiaban(gongzi.getJiaban());
			gz.setShibu(gongzi.getShibu());
			gz.setQuanqin(gongzi.getQuanqin());
			gz.setFakuan(gongzi.getFakuan());
			gz.setQingjia(gongzi.getQingjia());
			gz.setShebao(gongzi.getShebao());
			gz.setZhiqu(gongzi.getZhiqu());
			gz.setSalary(gongzi.getSalary());
			
			reportService.updateGongziOutcome(gz);
			
			
			gos = new GongziOutcome();
			gos.setYear(gz.getYear());
			gos.setMonth(gz.getMonth());
			gongziOutcomeList = reportService.getGongziOutcomeList(gos);
			double totalprice=0;
			if(gongziOutcomeList!=null&&gongziOutcomeList.size()>0){
				
				int len = gongziOutcomeList.size();

				for (int i = 0; i < len; i++) {
					GongziOutcome manager = gongziOutcomeList.get(i);
					 
					totalprice=totalprice+manager.getSalary();

				}
				
				
				Date bsndate=DateUtil.parse(gz.getYear()+"-"+gz.getMonth()+"-01  00:00:00",DateUtil.DATE_TIME_FORMAT);
				
				
				YearSmy smy = new YearSmy();
				smy.setReportdate(bsndate);
				List<YearSmy> ysList = reportService.getYearSmy(smy);
				if (ysList != null && ysList.size() > 0) {
					YearSmy	yearSmy = ysList.get(0);

					yearSmy.setGzprice(totalprice);
					double toutprice = yearSmy.getYfkprice() + yearSmy.getZpprice()+yearSmy.getGsprice()+yearSmy.getGzprice()+yearSmy.getFkprice()
							+ yearSmy.getPkprice()+yearSmy.getQtprice();
					yearSmy.setToutprice(toutprice);
					double tpprice = yearSmy.getTinprice() - yearSmy.getToutprice();
					yearSmy.setToutprice(toutprice);
					yearSmy.setTpprice(tpprice);

					reportService.updateYearSmy(yearSmy);

				} 
				
			}
			
			 return "1";
			
		}else return "0";
		 

	}
	
	

	public String updateFankuanOutcome(FankuanOutcome fankuan) throws FileNotFoundException, ParseException{
		
	 
		FankuanOutcome gos=new FankuanOutcome();
	    gos.setOutcomeid(fankuan.getOutcomeid());
		List<FankuanOutcome> fankuanOutcomeList=reportService.getFankuanOutcomeList(gos);
		
		if(fankuanOutcomeList!=null&&fankuanOutcomeList.size()==1){
			
			FankuanOutcome gz=	fankuanOutcomeList.get(0);
			
			gz.setFankuan(fankuan.getFankuan());
			 
			reportService.updateFankuanOutcome(gz);
			
			
			gos = new FankuanOutcome();
			gos.setYear(gz.getYear());
			gos.setMonth(gz.getMonth());
			fankuanOutcomeList = reportService.getFankuanOutcomeList(gos);
			double totalprice=0;
			if(fankuanOutcomeList!=null&&fankuanOutcomeList.size()>0){
				
				int len = fankuanOutcomeList.size();

				for (int i = 0; i < len; i++) {
					FankuanOutcome manager = fankuanOutcomeList.get(i);
					 
					totalprice=totalprice+manager.getFankuan();

				}
				
				
				Date bsndate=DateUtil.parse(gz.getYear()+"-"+gz.getMonth()+"-01  00:00:00",DateUtil.DATE_TIME_FORMAT);
				
				
				YearSmy smy = new YearSmy();
				smy.setReportdate(bsndate);
				List<YearSmy> ysList = reportService.getYearSmy(smy);
				if (ysList != null && ysList.size() > 0) {
					YearSmy	yearSmy = ysList.get(0);

					yearSmy.setFkprice(totalprice);
					double toutprice = yearSmy.getYfkprice() + yearSmy.getZpprice()+yearSmy.getGsprice()+yearSmy.getGzprice()+yearSmy.getFkprice()
							+ yearSmy.getPkprice()+yearSmy.getQtprice();
					yearSmy.setToutprice(toutprice);
					double tpprice = yearSmy.getTinprice() - yearSmy.getToutprice();
					yearSmy.setToutprice(toutprice);
					yearSmy.setTpprice(tpprice);

					reportService.updateYearSmy(yearSmy);

				} 
				
			}
			 return "1";
			
		}else return "0";
		 

	}
	
  

}
