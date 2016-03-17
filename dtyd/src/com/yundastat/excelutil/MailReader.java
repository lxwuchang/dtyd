/**
 * 
 */
package com.yundastat.excelutil;

 
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.math.BigDecimal;
import java.sql.*;
import com.yundastat.model.MailInfo;
import com.yundastat.model.StatReport;
import com.yundastat.model.TransformPriceRule;
import com.yundastat.model.WeightPriceRule;
import com.yundastat.service.MailService;
import com.yundastat.util.DateUtil;
 
/**
 * @author Administrator
 *
 */

public class MailReader implements IRowReader{
	
	private WeightPriceRule weightPriceRule;
	private List<TransformPriceRule> ransformricerulelist;
	private MailService mailService;
	private String date;

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	 public MailReader(){
	    	weightPriceRule=null;
	    	ransformricerulelist=null;
	    	date="2013-01-01";
	    	
	    }
    public MailReader(WeightPriceRule w,List<TransformPriceRule> r,String d,MailService ms){
    	weightPriceRule=w;
    	ransformricerulelist=r;
    	date=d;
    	mailService=ms;
    }
/*  
  * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List)
  */
 public void getRows(int sheetIndex, int curRow, List<String> rowlist)throws SQLException {
  // TODO Auto-generated method stub
  
	int mon = Integer.parseInt(date.substring(5, 7));
     	String linenum = rowlist.get(0);
		String mailid =rowlist.get(1);
		Date sendtime = null;
		String dateStr = "";
		if (rowlist.get(2).trim().equals(""))
			dateStr = "2000-12-31 00:00:00";
		else {
			dateStr = rowlist.get(2).trim();

		}
		sendtime = new Date();
		String sendgroup = rowlist.get(3);
		String manager = rowlist.get(4);
	 	String destinationgroup = rowlist.get(5);
		String subcompany = rowlist.get(6).trim();
	 	double weight = 0;
	 
		try {
			weight = Double.parseDouble(rowlist.get(9));

		} catch (Exception e) {
			e.printStackTrace();
 
		}
		double scanfei = weightPriceRule.getScanfei();
		double chujianfei = weightPriceRule.getChujianfei();
		
		MailInfo mail=new MailInfo();
		mail.setWeight(weight);
		mail.setSubcompany(subcompany);
		StatReport s=calPrice(mail);
		
		StatReport  mails = new StatReport();
		mails.setMailid(mailid);
		mails.setOperateprice(1.1);// 操作费暂时设置为1.1
		mails.setTransformprice(s.getTransformprice());
		mails.setWeightprice(s.getWeightprice());
		mails.setManager(manager);
		mails.setBuzufei(s.getBuzufei());
		mails.setScanfei(scanfei);
		mails.setChujianfei(chujianfei);
		try {
			mails.setCreatetime(DateUtil.parse(date+" 12:00:00", DateUtil.DATE_TIME_FORMAT));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mails.setWeight(weight);
		mails.setIsdeleted("n");
		mails.setDestinationcity(subcompany);// 目前设置目标组为中转站:条码显示实名
		mails.setSendtime(new Date());
		mails.setState("normal");
		mails.setMon(mon); 	
		double price=scanfei+chujianfei+s.getWeightprice()+s.getBuzufei()+s.getTransformprice();
		BigDecimal c = new BigDecimal(price);
		// 保留2位小数
		price = c.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		mails.setWholeprice(price);
		mailService.createStatReport(mails);
 
  
 }
 
 
 public StatReport calPrice(MailInfo mail){
	 
		double transformPrice = 0;
		double buzufei = 0;
		// 得出重量区间，从而得到一个index

		double weightPrice = 0;
		 

		// 计重 0,15,m,0;15,30,m,1;30,9999,m,2

		if(weightPriceRule!=null){
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
		}
		// 获取目的地，就可以去transformpricerule找;

		String pricelist = "";
		if(ransformricerulelist!=null){
		int transformLen = ransformricerulelist.size();
		for (int k = 0; k < transformLen; k++) {

			TransformPriceRule rule = ransformricerulelist
					.get(k);

			if (rule.getDestinationcity().equals(
					mail.getSubcompany())) {

				pricelist = rule.getPricelist();

				break;

			}

		}

		if (pricelist.equals("")) {// 表示该目的地不存在；
			 BigDecimal c = new BigDecimal(0);
			// 保留2位小数
			transformPrice = c.setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();

		} else {
			// 3:2

			// price=中转费方案:首重:单价:补助费
			// price=中转费方案：首重：首重价格,续重单价：补助费
			String strprice[] = pricelist.split(":");

			if (strprice[0].equals("1")) {// 单价式计算

				buzufei = Double.parseDouble(strprice[3]);

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

				BigDecimal c = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = c.setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();

			} else if (strprice[0].equals("2")){// 首重式计算

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

				BigDecimal c = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = c.setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();

			}else if (strprice[0].equals("3")){
				buzufei = Double.parseDouble(strprice[5]);
			    double minweight = Double.parseDouble(strprice[1]);
				double jiacheng = Double.parseDouble(strprice[2]);
				double shouzhong = Double.parseDouble(strprice[3]);
				String sss[] = strprice[4].split(",");

				double weight=0;
				if (mail.getWeight() <= minweight) {
				    weight=minweight;
				}else weight=mail.getWeight();
				
				if(weight<=shouzhong)
					transformPrice = Double.parseDouble(sss[0])*weight+jiacheng;
				else {

					transformPrice =  Double.parseDouble(sss[0])*shouzhong+jiacheng
							+ (weight - shouzhong)* Double.parseDouble(sss[1]);
				}

				BigDecimal cd = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cd.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				

			}else if (strprice[0].equals("4")){
				//三段式
			    //price=中转费方案：首重:首重价格：续重,续重单价:超重费：补助费
				buzufei = Double.parseDouble(strprice[5]);

				double shouzhong = Double
						.parseDouble(strprice[1]);
			
				String sss[] = strprice[3].split(",");
				double xuzhong = Double
				.parseDouble(sss[0]);
				if (mail.getWeight() <= shouzhong) {
					transformPrice = Double.parseDouble(strprice[2]);
				} else if (mail.getWeight() <= xuzhong){
					transformPrice = Double.parseDouble(sss[1]);
					
				}else{
					
					transformPrice = Double.parseDouble(sss[1])
					+ (Math.ceil(mail.getWeight()) - xuzhong)
					* Double.parseDouble(strprice[4]);
				}

				BigDecimal cc = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cc.setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				
				
			}else{
				//二段取整式
				
				buzufei = Double.parseDouble(strprice[3]);

				double shouzhong = Double
						.parseDouble(strprice[1]);
				String sss[] = strprice[2].split(",");

				if (mail.getWeight() <= shouzhong) {
					transformPrice = Double.parseDouble(sss[0]);
				} else {

					transformPrice = Double.parseDouble(sss[0])
							+( Math.ceil(mail.getWeight() )- shouzhong)
							* Double.parseDouble(sss[1]);
				}

				BigDecimal cc = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cc.setScale(2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				
			}


		}
		
		}
		StatReport statReport = new StatReport();
		
		statReport.setBuzufei(buzufei);
		statReport.setWeightprice(weightPrice);
		statReport.setTransformprice(transformPrice);
		
		return statReport;
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

