/**
 * 
 */
package com.yundastat.excelutil;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

public class MailReaderNew implements IRowReader {

	private WeightPriceRule weightPriceRule;
	private List<TransformPriceRule> ransformricerulelist;
	private MailService mailService;
	private String date;
	private String defaultPrice;
	private HashMap<String, String> managerMap;
	private HashMap<String, String> managerCategoryMap;
	private HashMap<String, WeightPriceRule> userWeightMap;
	private Map<String, List<TransformPriceRule>> transformPriceMap;
	private String flag="0";
	private String managerlist;
	private HashSet<String> sets;
	private HashSet<String> unsets;
	private HashSet<String> managerSet;
	private HashSet<String> managerDestSet;
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public MailReaderNew() {
		 

	}
	public MailReaderNew(String dt,MailService m,HashMap<String, String> mm, String dp,WeightPriceRule wpr,HashMap<String, String> mcm, HashMap<String, WeightPriceRule> uwp,Map<String, List<TransformPriceRule>> tpm,HashSet<String> set) {
	   date=dt;
		mailService=m;
		managerMap=mm;
		defaultPrice=dp;
		weightPriceRule=wpr;
		managerCategoryMap=mcm;
		userWeightMap=uwp;
		transformPriceMap=tpm;
		sets=set;
		unsets=new HashSet<String>();
		managerlist="";
		managerSet=new HashSet<String>();
		managerDestSet=new HashSet<String>();
	} 

	/*
	 * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List)
	 */
	public void getRows(int sheetIndex, int curRow, List<String> rowlist)
			throws SQLException {
		// TODO Auto-generated method stub

		int mon = Integer.parseInt(date.substring(5, 7));

		if (curRow == 0)
			return;
		if (rowlist == null) {
			System.out.println("null");
			return;
		}
		
		String mailid = rowlist.get(0);		
		String manager = rowlist.get(1);
		
		if(manager.indexOf("】")!=-1){
			
			manager=manager.substring(manager.indexOf("】")+1);
		}
	  
        String datetime = rowlist.get(2).trim();
        Date sendtime =null;
   	 
		if(datetime.indexOf("/")!=-1){
		 String years[]=datetime.split("/");

		 try {
			sendtime = DateUtil.parse(years[2]+"-"+years[1]+"-"+years[0] + " "+years[3]+":"+years[4]+":00",DateUtil.DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
		}
		
		String subcompany = rowlist.get(3).trim();
		
        if(subcompany.indexOf("】")!=-1){
			
        	subcompany=subcompany.substring(subcompany.indexOf("】")+1);
		}
		double weight = 0;

		try {
			weight = Double.parseDouble(rowlist.get(4));

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		if(!sets.contains(manager)){	
			   if(!unsets.contains(manager)){
				   unsets.add(manager);
				managerlist=managerlist+manager+"、";
			   }
	 	}

		managerSet.add(manager);
		if(mailid.startsWith("1")){
			flag="0";
		}else{
			 flag="1";
//			if(subcompany.indexOf("内蒙")!=-1||subcompany.indexOf("西藏")!=-1||subcompany.indexOf("新疆")!=-1)
//				flag="0";
	 	} 
		
		MailInfo mail = new MailInfo();
		mail.setWeight(weight);
		mail.setSubcompany(subcompany);
		mail.setManager(manager);
		StatReport s = calPrice(mail);

		StatReport mails = new StatReport();
		mails.setMailid(mailid);
		mails.setOperateprice(0);// 操作费暂时设置为1.1
		mails.setTransformprice(s.getTransformprice());
		mails.setWeightprice(s.getWeightprice());
		mails.setManager(manager);
		mails.setBuzufei(s.getBuzufei());
		mails.setScanfei(s.getScanfei());//出件费即为扫描费
		mails.setChujianfei(0);
		try {
			mails.setCreatetime(DateUtil.parse(date + " 12:00:00",
					DateUtil.DATE_TIME_FORMAT));
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
		double price = s.getScanfei() +  s.getWeightprice()
				+ s.getBuzufei() + s.getTransformprice();
		BigDecimal c = new BigDecimal(price);
		// 保留2位小数
		price = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		mails.setWholeprice(price);
		mailService.createStatReport(mails);
		

	}

	public StatReport calPrice(MailInfo mail) {

		// 获取目的地，就可以去transformpricerule找;
		String managerid = null;
		String categoryid = null;
		WeightPriceRule weightpricerule = null;
		double weightPrice = 0;
		double transformPrice = 0;
		double buzufei = 0;
		double scanfei = 0;

		managerid = managerMap.get(mail.getManager());
		if (managerid == null) {

			categoryid = defaultPrice;
			weightpricerule = weightPriceRule;
		} else {

			categoryid = managerCategoryMap.get(managerid);
			weightpricerule = userWeightMap.get(managerid);

		}

		if (weightpricerule != null) {
			if (mail.getWeight() <= weightpricerule.getFirstweight()) {
				weightPrice = weightpricerule.getPrice()
						* weightpricerule.getFirstweight();

			} else {

				weightPrice = mail.getWeight() * weightpricerule.getPrice();
			}

			BigDecimal bb = new BigDecimal(weightPrice);
			// 保留2位小数
			weightPrice = bb.setScale(3, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

		}

		List<TransformPriceRule> transformPriceRuleList = transformPriceMap
				.get(categoryid);
		int transformLen = 0;
		if (transformPriceRuleList != null)
			transformLen = transformPriceRuleList.size();

		String pricelist = "";
		for (int k = 0; k < transformLen; k++) {

			TransformPriceRule rule = transformPriceRuleList.get(k);

			if (rule.getDestinationcity().trim().equals(
					mail.getSubcompany().trim())&& rule.getMtype().equals(flag)) {

				pricelist = rule.getPricelist();
				break;
			}

		}

		if (pricelist.equals("")) {// 表示该目的地不存在；

            managerDestSet.add(mail.getSubcompany());
			 
			BigDecimal cc = new BigDecimal(0);
			// 保留2位小数
			transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

		} else {
			// 3:2

			// price=中转费方案:首重:单价:补助费
			// price=中转费方案：首重：首重价格,续重单价：补助费
			// // price=中转费方案：最小结算重量:加成：首重：首重价格,续重单价：补助费
			String strprice[] = pricelist.split(":");

			if (strprice[0].equals("1")) {// 单价式计算
				buzufei = Double.parseDouble(strprice[3]);
				scanfei = weightpricerule.getScanfei();

				double shouzhong = Double.parseDouble(strprice[1]);

				if (mail.getWeight() <= shouzhong) {
					transformPrice = Double.parseDouble(strprice[1])
							* Double.parseDouble(strprice[2]);
				} else {

					transformPrice = mail.getWeight()
							* Double.parseDouble(strprice[2]);
				}

				BigDecimal cc = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("2")) {// 首重式计算
				scanfei = weightpricerule.getScanfei();
				buzufei = Double.parseDouble(strprice[3]);

				double shouzhong = Double.parseDouble(strprice[1]);
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
				transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("3")) {
				scanfei = weightpricerule.getScanfei();
				buzufei = Double.parseDouble(strprice[5]);
				double minweight = Double.parseDouble(strprice[1]);
				double jiacheng = Double.parseDouble(strprice[2]);
				double shouzhong = Double.parseDouble(strprice[3]);
				String sss[] = strprice[4].split(",");

				double weight = 0;
				if (mail.getWeight() <= minweight) {
					weight = minweight;
				} else
					weight = mail.getWeight();

				if (weight <= shouzhong)
					transformPrice = Double.parseDouble(sss[0]) * weight
							+ jiacheng;
				else {

					transformPrice = Double.parseDouble(sss[0]) * shouzhong
							+ jiacheng + (weight - shouzhong)
							* Double.parseDouble(sss[1]);
				}

				BigDecimal cd = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cd.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("4")) {
				// 三段式
				// price=中转费方案：首重:首重价格：续重,续重单价:超重,超重费：补助费
				buzufei = 0;
				scanfei = 0;
				weightPrice = 0;
				double shouzhong = Double.parseDouble(strprice[1]);

				String sss[] = strprice[3].split(",");
				double xuzhong = Double.parseDouble(sss[0]);
				if (mail.getWeight() <= shouzhong) {
					transformPrice = Double.parseDouble(strprice[2]);
				} else if (mail.getWeight() <= xuzhong) {
					transformPrice = Double.parseDouble(sss[1]);

				} else {

					double ww = 0;
					double pp = 0;
					if (strprice[4].indexOf(",") != -1) {
						ww = Double.parseDouble(strprice[4].split(",")[0]);
						pp = Double.parseDouble(strprice[4].split(",")[1]);
					} else {
						ww = 1;
						pp = Double.parseDouble(strprice[4]);
					}

					double chaoweight = mail.getWeight() - xuzhong;
					int f = (int) (chaoweight / ww);
					if (f * ww < chaoweight) {
						f = f + 1;
					}

					transformPrice = Double.parseDouble(sss[1]) + f * pp;

				}

				BigDecimal cc = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("5")) {
				// 二段取整式
				buzufei = 0;
				scanfei = 0;
				weightPrice = 0;
				double shouzhong = Double.parseDouble(strprice[1]);
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
				transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("6")){
				// 小件与超重
				// price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格：中转费方案：首重,首重价格,续重单价：补助费

				double maxweight = 0;
				if (strprice.length >= 5) {
					String str1[] = strprice[1].split(",");
					if (mail.getWeight() > Double.parseDouble(str1[0])
							&& mail.getWeight() <= Double.parseDouble(str1[1])) {
						buzufei = 0;
						scanfei = 0;
						weightPrice = 0;
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str1[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();

					}
					maxweight = Double.parseDouble(str1[1]);
				}

				if (strprice.length >= 6) {
					String str2[] = strprice[2].split(",");
					if (mail.getWeight() > Double.parseDouble(str2[0])
							&& mail.getWeight() <= Double.parseDouble(str2[1])) {
						buzufei = 0;
						scanfei = 0;
						weightPrice = 0;
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str2[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					maxweight = Double.parseDouble(str2[1]);
				}

				if (strprice.length >= 7) {
					String str3[] = strprice[3].split(",");
					if (mail.getWeight() > Double.parseDouble(str3[0])
							&& mail.getWeight() <= Double.parseDouble(str3[1])) {
						buzufei = 0;
						scanfei = 0;
						weightPrice = 0;
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str3[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					maxweight = Double.parseDouble(str3[1]);
				}

				
				 if (strprice.length >= 8) {
			          String[] str3 = strprice[4].split(",");
			          if ((mail.getWeight() > Double.parseDouble(str3[0])) && 
			            (mail.getWeight() <= Double.parseDouble(str3[1]))) {
			            buzufei = 0;
			            scanfei = 0;
			            weightPrice = 0;
			            BigDecimal cc = new BigDecimal(
			              Double.parseDouble(str3[2]));

			            transformPrice = cc.setScale(2, 
			              4).doubleValue();
			          }
			          maxweight = Double.parseDouble(str3[1]);
			        }

				if (mail.getWeight() > maxweight) {

					String str5[] = strprice[strprice.length - 2].split(",");
					scanfei = weightpricerule.getScanfei();
					if (strprice[strprice.length - 3].equals("7")) {
						double shouzhong = Double.parseDouble(str5[0]);

						if (mail.getWeight() <= shouzhong) {
							transformPrice = shouzhong
									* Double.parseDouble(str5[1]);
						} else {

							transformPrice = mail.getWeight()
									* Double.parseDouble(str5[1]);
						}

						BigDecimal cc = new BigDecimal(transformPrice);
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();

						buzufei = Double
								.parseDouble(strprice[strprice.length - 1]);
					} else {

						double shouzhong = Double.parseDouble(str5[0]);

						if (mail.getWeight() <= shouzhong) {
							transformPrice = Double.parseDouble(str5[1]);
						} else {

							transformPrice = Double.parseDouble(str5[1])
									+ (mail.getWeight() - shouzhong)
									* Double.parseDouble(str5[2]);
						}
						BigDecimal cc = new BigDecimal(transformPrice);
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						buzufei = Double
								.parseDouble(strprice[strprice.length - 1]);

					}

				}
			}else{
				
				//分段单价式
				
				double maxweight = 0;
				if (strprice.length >= 5) {
					String str1[] = strprice[1].split(",");
					if (mail.getWeight() > Double.parseDouble(str1[0])
							&& mail.getWeight() <= Double.parseDouble(str1[1])) {
					 
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str1[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						
						  buzufei = 0;
				          scanfei = 0;
				          weightPrice = 0;

					}
					maxweight = Double.parseDouble(str1[1]);
					
					
				}

				if (strprice.length >= 6) {
					String str2[] = strprice[2].split(",");
					if (mail.getWeight() > Double.parseDouble(str2[0])
							&& mail.getWeight() <= Double.parseDouble(str2[1])) {
					 
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str2[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						
						  buzufei = 0;
				            scanfei = 0;
				            weightPrice = 0;
					}
					maxweight = Double.parseDouble(str2[1]);
				}

				if (strprice.length >= 7) {
					String str3[] = strprice[3].split(",");
					if (mail.getWeight() > Double.parseDouble(str3[0])
							&& mail.getWeight() <= Double.parseDouble(str3[1])) {
					 
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str3[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						
						  buzufei = 0;
				            scanfei = 0;
				            weightPrice = 0;
					}
					maxweight = Double.parseDouble(str3[1]);
				}

				
				 if (strprice.length >= 8) {
			          String[] str3 = strprice[4].split(",");
			          if ((mail.getWeight() > Double.parseDouble(str3[0])) && 
			            (mail.getWeight() <= Double.parseDouble(str3[1]))) {
			          
			            BigDecimal cc = new BigDecimal(
			              Double.parseDouble(str3[2]));

			            transformPrice = cc.setScale(2, 
			              4).doubleValue();
			            
			            buzufei = 0;
			            scanfei = 0;
			            weightPrice = 0;
			          }
			          maxweight = Double.parseDouble(str3[1]);
			        }

				if (mail.getWeight() > maxweight) {
					
					double diyifei = Double.parseDouble(strprice[ strprice.length-3]);
					double dierfei = Double.parseDouble(strprice[ strprice.length-2]);

					if (mail.getWeight() <= 2) {
						transformPrice =  mail.getWeight()*diyifei;
					} else {

						transformPrice = mail.getWeight()*dierfei;
					}

					BigDecimal cc = new BigDecimal(transformPrice);
					// 保留2位小数
					transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					
 		
					buzufei = Double.parseDouble(strprice[ strprice.length-1]);
					scanfei = weightpricerule.getScanfei();
					 
				 }
				

				
			}

		}
		StatReport statReport = new StatReport();

		statReport.setBuzufei(buzufei);
		statReport.setWeightprice(weightPrice);
		statReport.setScanfei(scanfei);
		statReport.setTransformprice(transformPrice);

		return statReport;
	}
	
	public static StatReport calPriceNew(MailInfo mail,HashMap<String, String> managerMap, String defaultPrice,WeightPriceRule weightPriceRule,HashMap<String, String> managerCategoryMap, HashMap<String, WeightPriceRule> userWeightMap,Map<String, List<TransformPriceRule>> transformPriceMap) {

		// 获取目的地，就可以去transformpricerule找;
		String managerid = null;
		String categoryid = null;
		WeightPriceRule weightpricerule = null;
		double weightPrice = 0;
		double transformPrice = 0;
		double buzufei = 0;
		double scanfei = 0;

		managerid = managerMap.get(mail.getManager());
		if (managerid == null) {

			categoryid = defaultPrice;
			weightpricerule = weightPriceRule;
		} else {

			categoryid = managerCategoryMap.get(managerid);
			weightpricerule = userWeightMap.get(managerid);

		}

		if (weightpricerule != null) {
			if (mail.getWeight() <= weightpricerule.getFirstweight()) {
				weightPrice = weightpricerule.getPrice()
						* weightpricerule.getFirstweight();

			} else {

				weightPrice = mail.getWeight() * weightpricerule.getPrice();
			}

			BigDecimal bb = new BigDecimal(weightPrice);
			// 保留2位小数
			weightPrice = bb.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

		}
		
		String flag="0";

		if(mail.getMailid().startsWith("1")){
			flag="0";
		}else{
			 flag="1";
//			if(mail.getSubcompany().indexOf("内蒙")!=-1||mail.getSubcompany().indexOf("西藏")!=-1||mail.getSubcompany().indexOf("新疆")!=-1)
//				flag="0";
	 	} 

		List<TransformPriceRule> transformPriceRuleList = transformPriceMap
				.get(categoryid);
		int transformLen = 0;
		if (transformPriceRuleList != null)
			transformLen = transformPriceRuleList.size();

		String pricelist = "";
		for (int k = 0; k < transformLen; k++) {

			TransformPriceRule rule = transformPriceRuleList.get(k);

			if (rule.getDestinationcity().trim().equals(
					mail.getSubcompany().trim())&&rule.getMtype().equals(flag)) {

				pricelist = rule.getPricelist();
				break;
			}

		}

		if (pricelist.equals("")) {// 表示该目的地不存在；

			BigDecimal cc = new BigDecimal(0);
			// 保留2位小数
			transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

		} else {
			// 3:2

			// price=中转费方案:首重:单价:补助费
			// price=中转费方案：首重：首重价格,续重单价：补助费
			// // price=中转费方案：最小结算重量:加成：首重：首重价格,续重单价：补助费
			String strprice[] = pricelist.split(":");

			if (strprice[0].equals("1")) {// 单价式计算
				buzufei = Double.parseDouble(strprice[3]);
				scanfei = weightpricerule.getScanfei();

				double shouzhong = Double.parseDouble(strprice[1]);

				if (mail.getWeight() <= shouzhong) {
					transformPrice = Double.parseDouble(strprice[1])
							* Double.parseDouble(strprice[2]);
				} else {

					transformPrice = mail.getWeight()
							* Double.parseDouble(strprice[2]);
				}

				BigDecimal cc = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("2")) {// 首重式计算
				scanfei = weightpricerule.getScanfei();
				buzufei = Double.parseDouble(strprice[3]);

				double shouzhong = Double.parseDouble(strprice[1]);
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
				transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("3")) {
				scanfei = weightpricerule.getScanfei();
				buzufei = Double.parseDouble(strprice[5]);
				double minweight = Double.parseDouble(strprice[1]);
				double jiacheng = Double.parseDouble(strprice[2]);
				double shouzhong = Double.parseDouble(strprice[3]);
				String sss[] = strprice[4].split(",");

				double weight = 0;
				if (mail.getWeight() <= minweight) {
					weight = minweight;
				} else
					weight = mail.getWeight();

				if (weight <= shouzhong)
					transformPrice = Double.parseDouble(sss[0]) * weight
							+ jiacheng;
				else {

					transformPrice = Double.parseDouble(sss[0]) * shouzhong
							+ jiacheng + (weight - shouzhong)
							* Double.parseDouble(sss[1]);
				}

				BigDecimal cd = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cd.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("4")) {
				// 三段式
				// price=中转费方案：首重:首重价格：续重,续重单价:超重,超重费：补助费
				buzufei = 0;
				scanfei = 0;
				weightPrice = 0;
				double shouzhong = Double.parseDouble(strprice[1]);

				String sss[] = strprice[3].split(",");
				double xuzhong = Double.parseDouble(sss[0]);
				if (mail.getWeight() <= shouzhong) {
					transformPrice = Double.parseDouble(strprice[2]);
				} else if (mail.getWeight() <= xuzhong) {
					transformPrice = Double.parseDouble(sss[1]);

				} else {

					double ww = 0;
					double pp = 0;
					if (strprice[4].indexOf(",") != -1) {
						ww = Double.parseDouble(strprice[4].split(",")[0]);
						pp = Double.parseDouble(strprice[4].split(",")[1]);
					} else {
						ww = 1;
						pp = Double.parseDouble(strprice[4]);
					}

					double chaoweight = mail.getWeight() - xuzhong;
					int f = (int) (chaoweight / ww);
					if (f * ww < chaoweight) {
						f = f + 1;
					}

					transformPrice = Double.parseDouble(sss[1]) + f * pp;

				}

				BigDecimal cc = new BigDecimal(transformPrice);
				// 保留2位小数
				transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("5")) {
				// 二段取整式
				buzufei = 0;
				scanfei = 0;
				weightPrice = 0;
				double shouzhong = Double.parseDouble(strprice[1]);
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
				transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();

			} else if (strprice[0].equals("6")){
				// 小件与超重
				// price=中转费方案：首重,首重二,价格:首重,首重二,价格:首重,首重二,价格：中转费方案：首重,首重价格,续重单价：补助费

				double maxweight = 0;
				if (strprice.length >= 5) {
					String str1[] = strprice[1].split(",");
					if (mail.getWeight() > Double.parseDouble(str1[0])
							&& mail.getWeight() <= Double.parseDouble(str1[1])) {
						buzufei = 0;
						scanfei = 0;
						weightPrice = 0;
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str1[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();

					}
					maxweight = Double.parseDouble(str1[1]);
				}

				if (strprice.length >= 6) {
					String str2[] = strprice[2].split(",");
					if (mail.getWeight() > Double.parseDouble(str2[0])
							&& mail.getWeight() <= Double.parseDouble(str2[1])) {
						buzufei = 0;
						scanfei = 0;
						weightPrice = 0;
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str2[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					maxweight = Double.parseDouble(str2[1]);
				}

				if (strprice.length >= 7) {
					String str3[] = strprice[3].split(",");
					if (mail.getWeight() > Double.parseDouble(str3[0])
							&& mail.getWeight() <= Double.parseDouble(str3[1])) {
						buzufei = 0;
						scanfei = 0;
						weightPrice = 0;
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str3[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					maxweight = Double.parseDouble(str3[1]);
				}

				  if (strprice.length >= 8) {
			          String[] str3 = strprice[4].split(",");
			          if ((mail.getWeight() > Double.parseDouble(str3[0])) && 
			            (mail.getWeight() <= Double.parseDouble(str3[1]))) {
			            buzufei = 0;
			            scanfei = 0;
			            weightPrice = 0;
			            BigDecimal cc = new BigDecimal(
			              Double.parseDouble(str3[2]));

			            transformPrice = cc.setScale(2, 
			              4).doubleValue();
			          }
			          maxweight = Double.parseDouble(str3[1]);
			        }
				  
				if (mail.getWeight() > maxweight) {

					String str5[] = strprice[strprice.length - 2].split(",");
					scanfei = weightpricerule.getScanfei();
					if (strprice[strprice.length - 3].equals("7")) {
						double shouzhong = Double.parseDouble(str5[0]);

						if (mail.getWeight() <= shouzhong) {
							transformPrice = shouzhong
									* Double.parseDouble(str5[1]);
						} else {

							transformPrice = mail.getWeight()
									* Double.parseDouble(str5[1]);
						}

						BigDecimal cc = new BigDecimal(transformPrice);
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();

						buzufei = Double
								.parseDouble(strprice[strprice.length - 1]);
					} else {

						double shouzhong = Double.parseDouble(str5[0]);

						if (mail.getWeight() <= shouzhong) {
							transformPrice = Double.parseDouble(str5[1]);
						} else {

							transformPrice = Double.parseDouble(str5[1])
									+ (mail.getWeight() - shouzhong)
									* Double.parseDouble(str5[2]);
						}
						BigDecimal cc = new BigDecimal(transformPrice);
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						buzufei = Double
								.parseDouble(strprice[strprice.length - 1]);

					}

				}
			}else{
				
	//分段单价式
				
				double maxweight = 0;
				if (strprice.length >= 5) {
					String str1[] = strprice[1].split(",");
					if (mail.getWeight() > Double.parseDouble(str1[0])
							&& mail.getWeight() <= Double.parseDouble(str1[1])) {
					 
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str1[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						
			            buzufei = 0;
			            scanfei = 0;
			            weightPrice = 0;

					}
					maxweight = Double.parseDouble(str1[1]);
				}

				if (strprice.length >= 6) {
					String str2[] = strprice[2].split(",");
					if (mail.getWeight() > Double.parseDouble(str2[0])
							&& mail.getWeight() <= Double.parseDouble(str2[1])) {
					 
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str2[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						
			            buzufei = 0;
			            scanfei = 0;
			            weightPrice = 0;
					}
					maxweight = Double.parseDouble(str2[1]);
				}

				if (strprice.length >= 7) {
					String str3[] = strprice[3].split(",");
					if (mail.getWeight() > Double.parseDouble(str3[0])
							&& mail.getWeight() <= Double.parseDouble(str3[1])) {
					 
						BigDecimal cc = new BigDecimal(Double
								.parseDouble(str3[2]));
						// 保留2位小数
						transformPrice = cc.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue();
						
			            buzufei = 0;
			            scanfei = 0;
			            weightPrice = 0;
					}
					maxweight = Double.parseDouble(str3[1]);
				}

				
				 if (strprice.length >= 8) {
			          String[] str3 = strprice[4].split(",");
			          if ((mail.getWeight() > Double.parseDouble(str3[0])) && 
			            (mail.getWeight() <= Double.parseDouble(str3[1]))) {
			          
			            BigDecimal cc = new BigDecimal(
			              Double.parseDouble(str3[2]));

			            transformPrice = cc.setScale(2, 
			              4).doubleValue();
			            
			            buzufei = 0;
			            scanfei = 0;
			            weightPrice = 0;
			          }
			          maxweight = Double.parseDouble(str3[1]);
			        }

				if (mail.getWeight() > maxweight) {
					
					double diyifei = Double.parseDouble(strprice[ strprice.length-3]);
					double dierfei = Double.parseDouble(strprice[ strprice.length-2]);

					if (mail.getWeight() <= 2) {
						transformPrice =  mail.getWeight()*diyifei;
					} else {

						transformPrice = mail.getWeight()*dierfei;
					}

					BigDecimal cc = new BigDecimal(transformPrice);
					// 保留2位小数
					transformPrice = cc.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					

					
					buzufei = Double.parseDouble(strprice[ strprice.length-1]);
					scanfei = weightpricerule.getScanfei();
					 
				 }
				
				
				
			}

		}
		StatReport statReport = new StatReport();

		statReport.setBuzufei(buzufei);
		statReport.setWeightprice(weightPrice);
		statReport.setScanfei(scanfei);
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
	
		return managerlist;
	}

	public HashSet<String> getManagerSet(){
		return managerSet;
	}
	
	public HashSet<String> getManagerDestSet(){
		return managerDestSet;
	}
	 

}
