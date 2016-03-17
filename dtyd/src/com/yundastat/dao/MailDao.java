/**
 * 
 */
package com.yundastat.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.transaction.Transaction;
import com.yundastat.model.CompareReport;
import com.yundastat.model.MailInfo;
import com.yundastat.model.MailParameter;
import com.yundastat.model.ManagerInfo;
import com.yundastat.model.PageBean;
import com.yundastat.model.PageParameter;
import com.yundastat.model.PriceDetail;
import com.yundastat.model.PriceDetailSmy;
import com.yundastat.model.Report;
import com.yundastat.model.ReportFlag;
import com.yundastat.model.StatReport;
import com.yundastat.model.StatSMY;
import com.yundastat.model.TransformCategory;
import com.yundastat.model.TransformMail;
import com.yundastat.model.TransformPriceRule;
import com.yundastat.model.WeightPriceRule;
import com.yundastat.model.WeightRule;

/**
 * @author hp
 * 
 */
public class MailDao {
	private SqlMapClient sqlMapClient;

	// /* (non-Javadoc)
	// * @see com.wuchang.dao.UserDao#getUserByUsername(java.lang.String)
	// */
	//	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yundastat.dao.IMailDao#getSqlMapClient()
	 */
	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yundastat.dao.IMailDao#setSqlMapClient(com.ibatis.sqlmap.client.
	 * SqlMapClient)
	 */
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yundastat.dao.IMailDao#getMailByMailid(java.lang.String)
	 */
	public MailInfo getMailByMailid(String mailid) {
		// TODO Auto-generated method stub

		MailInfo mail = null;
		try {
			mail = (MailInfo) sqlMapClient.queryForObject(
					"mail.getSourceMailByMailid", mailid);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mail;

	}
	
	public int updateMail(MailInfo mail) {
		// TODO Auto-generated method stub

		int i=0;
		try {
			i=sqlMapClient.update("mail.updateMail", mail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return i;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yundastat.dao.IMailDao#getMailByMailid(java.lang.String)
	 */
	public StatReport getStatReportById(String mailid) {
		// TODO Auto-generated method stub

		
		StatReport mail = null;
		try {
			mail = (StatReport) sqlMapClient.queryForObject(
					"mail.getStatReportById", mailid);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mail;

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yundastat.dao.IMailDao#insertSourceMail(com.yundastat.model.MailInfo)
	 */
	public void insertSourceMail(MailInfo mail) {

		try {
			sqlMapClient.insert("mail.insertSourceMail", mail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertTransformMail(List<TransformMail> mail) throws SQLException {

	 
		try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for (int i = 0; i < mail.size(); i++) {
		 
				sqlMapClient.insert("mail.insertTransformMail", mail.get(i));
				if (i % 200 == 0 && i > 1)
					sqlMapClient.executeBatch();

			}

			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
			

		} finally {

			try {

				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
	
	

	public void insertCompareReportList(List<CompareReport> report) throws SQLException {

	 
		try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for (int i = 0; i < report.size(); i++) {
		 
				sqlMapClient.insert("mail.insertCompareReport", report.get(i));
				if (i % 200 == 0 && i > 1)
					sqlMapClient.executeBatch();

			}

			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
			

		} finally {

			try {

				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yundastat.dao.IMailDao#insertSourceMailList(java.util.List)
	 */
	public void insertSourceMailList(final List<MailInfo> mail)
			throws SQLException {
		// this.getSqlMapClientTemplate().startTransaction();
		//		
		// for(int i=0;i<mail.size();i++){
		//	    
		// this.getSqlMapClientTemplate().insert("mail.insertSourceMail",mail.get(i));
		// System.out.println("insert the number:" +i);
		// if(i>100)throw new RuntimeException("ROllback!");
		//		
		// }
		//		
		//		
		// this.getSqlMapClientTemplate().commitTransaction();

		// this.getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
		//
		// public Object doInSqlMapClient(SqlMapExecutor arg0)
		// throws SQLException {
		// // TODO Auto-generated method stub
		//				
		// arg0.startBatch();
		// for(int i=0;i<mail.size();i++){
		//				    
		// arg0.insert("mail.insertSourceMail",mail.get(i));
		// System.out.println("insert the number:" +i);
		//					
		// if(i%20==0&&i>1)
		// arg0.executeBatch();
		//					
		//					
		// }
		//				
		// arg0.executeBatch();
		//				 
		// return null;
		// }
		//			
		//			
		//			
		// });
		//		 
		try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for (int i = 0; i < mail.size(); i++) {
			//	MailInfo m=(MailInfo) sqlMapClient.queryForObject("mail.getSourceMailByMailid",  mail.get(i));
			//	if(m!=null&&m.getMailid().equals(mail.get(i))){
			//	  sqlMapClient.update("mail.updateMail", mail.get(i));
			//	}else{
				   sqlMapClient.insert("mail.insertSourceMail", mail.get(i));
			//	}

				if (i % 1000 == 0 && i > 1)
					sqlMapClient.executeBatch();

			}

			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
			

		} finally {

			try {

				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yundastat.dao.IMailDao#insertWeightRuleAndUpdateRule(com.yundastat
	 * .model.WeightRule)
	 */
	public void insertWeightRuleAndUpdateRule(WeightRule rule) throws ParseException {
		
	

		try {
		
			sqlMapClient.insert("mail.insertWeightRule", rule);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	

	public void createStatSMY(PageParameter param) throws ParseException {
	 
		try {
		
			sqlMapClient.insert("mail.createStatSMY", param);
		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yundastat.dao.IMailDao#getWeightRule()
	 */
	public List<WeightRule> getWeightRuleList(WeightRule weight) {
		// TODO Auto-generated method stub

		List<WeightRule> rule = null;
	  
		HashMap map=new HashMap();
		if(weight.getWeightruleid()==0)
		map.put("weightruleid", null);
		else map.put("weightruleid", weight.getWeightruleid());
		map.put("weightrulename", weight.getWeightrulename());
		map.put("isdeleted", weight.getIsdeleted());
		try {
			rule =  sqlMapClient.queryForList("mail.getWeightRuleList", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rule;

	}
	
	public  WeightRule  getWeightRule() {
		// TODO Auto-generated method stub

		 WeightRule  rule = null;
	 
		try {
			rule =  (WeightRule) sqlMapClient.queryForObject("mail.getWeightRule", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rule;

	}


	  public void  deleteTransformCategory(TransformCategory t){
			  
			  HashMap map=new HashMap();
			 
				map.put("transformcategoryid", t.getTransformcategoryid());
				try {
					sqlMapClient.update("mail.deleteTransformCategory", map);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		  
		  
		  public List<TransformCategory> queryForTransformCategory(TransformCategory t){
				
				List<TransformCategory> ss=null;
				
				HashMap map=new HashMap();
				if(t.getTransformcategoryid()==0)
				map.put("transformcategoryid", null);
				else 	map.put("transformcategoryid", t.getTransformcategoryid());
				map.put("status", t.getStatus());
				map.put("transformcategoryname", t.getTransformcategoryname());
				try {
					ss=sqlMapClient.queryForList("mail.getTransformCategory", map);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			}
		  
		  public int insertTransformCategory(TransformCategory t){
				
			  int flag=-1;
				try {
					flag=(Integer) sqlMapClient.insert("mail.insertTransformCategory", t);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -1;
				}
				return flag;
			}
		  
		  
		  public int  updateTransformCategory(TransformCategory t){
			  
			   int flag=0;
				try {
					flag=sqlMapClient.update("mail.updateTransformCategory", t);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -1;
				}
				return flag;
			}
	public List<StatSMY> getReportFlagByPage(PageParameter d){
		List<StatSMY> flag = new ArrayList();
	 
		try {
			flag =  sqlMapClient.queryForList("mail.getReportFlagByPage", d);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
		
		
	}
	
	 
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yundastat.dao.IMailDao#getWeightPriceRule()
	 */
	public WeightPriceRule getWeightPriceRule() throws SQLException {
		// TODO Auto-generated method stub

		WeightPriceRule rule = null;
		rule = (WeightPriceRule) sqlMapClient.queryForObject(
				"mail.getWeightPriceRule", null);
		return rule;

	}
	
	public List<WeightPriceRule> getWeightPriceRuleList(WeightPriceRule r) throws SQLException {
		// TODO Auto-generated method stub

		List<WeightPriceRule> rule = null;
		
		HashMap map =new HashMap();
		if( r.getWeightpriceruleid()==0)
			map.put("weightpriceruleid", null);
		else map.put("weightpriceruleid", r.getWeightpriceruleid());
		map.put("weightpricename", r.getWeightpricename());
		map.put("isdeleted", r.getIsdeleted());
		map.put("isactive", r.getIsactive());
		
		rule =  sqlMapClient.queryForList(
				"mail.getWeightPriceRuleList", map);
		return rule;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yundastat.dao.IMailDao#insertWeightPriceRuleAndUpdateRule(com.yundastat
	 * .model.WeightPriceRule)
	 */
	public void insertWeightPriceRuleAndUpdateRule(WeightPriceRule rule) {

		Date dt=rule.getEnddate();
		try {
			
			sqlMapClient.insert("mail.insertWeightPriceRule", rule);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateStatReport(HashMap map) {

		try {
			sqlMapClient.update("mail.updateStatReport", map);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void updateStatReportNew(StatReport map) {

		try {
			sqlMapClient.update("mail.updateStatReportNew", map);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int deleteStatReport(StatReport map) {
		int v=0;

		try {
			v=sqlMapClient.update("mail.deleteStatReport", map);

		} catch (Exception e) {
			e.printStackTrace();
		}
          return v;
	}
	
	public void insertReportFlag(StatSMY map) {

		try {
			sqlMapClient.update("mail.insertReportFlag", map);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	 
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yundastat.dao.IMailDao#insertTransformPriceRuleAndUpdateRule(com.
	 * yundastat.model.TransformPriceRule)
	 */
	public void insertTransformPriceRule(TransformPriceRule rule) {

		try {
			sqlMapClient.insert("mail.insertTransformPriceRule", rule);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yundastat.dao.IMailDao#insertTransformPriceRuleAndUpdateRule(com.
	 * yundastat.model.TransformPriceRule)
	 */
	public void insertTransformPriceRuleAndUpdateRule(TransformPriceRule rule) {

		try {
			sqlMapClient.update("mail.updateTransformPriceRule", rule);	
			sqlMapClient.insert("mail.insertTransformPriceRule", rule);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void updateTransformPriceRule(TransformPriceRule rule) {

		try {
			sqlMapClient.update("mail.updateTransformPriceRule", rule);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void updateReportFlag(StatSMY flag) {

		try {
			sqlMapClient.update("mail.updateReportFlag", flag);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public ReportFlag getStatReportSmy(PageParameter flag) {

		ReportFlag fl=null;
		try {
			fl=(ReportFlag) sqlMapClient.queryForObject("mail.getStatReportSmy", flag);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return fl;
	}
	
	public void deleteReportFlag(StatSMY flag) {

		try {
			sqlMapClient.delete("mail.deleteReportFlag", flag);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/*
	 * (non-Javadoc)
	 *  历史遗留问题
	 * @see com.yundastat.dao.IMailDao#getTransformPriceRule(java.lang.String)
	 */
	public List<TransformPriceRule> getTransformPriceRule(String code) {
		// TODO Auto-generated method stub

		List<TransformPriceRule> rule = new ArrayList();
		;
        if(code.equals(""))
        	code=null;
		HashMap map = new HashMap();
		map.put("code", code);
		try {
			rule = sqlMapClient.queryForList("mail.getTransformPriceRuleNew", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rule;

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yundastat.dao.IMailDao#getTransformPriceRule(java.lang.String)
	 */
	public List<TransformPriceRule> getTransformPriceRule(TransformPriceRule rule) {
		// TODO Auto-generated method stub

		List<TransformPriceRule> rules = new ArrayList();
		;

		HashMap map = new HashMap();
		
		map.put("code", rule.getCode());
		map.put("destination", rule.getDestination());
		map.put("destinationcity", rule.getDestinationcity());
		
		
		try {
			rules = sqlMapClient.queryForList("mail.getTransformPriceRuleNew", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rules;

	}
	
	
	public List<TransformPriceRule> getTransformPriceRuleList(TransformPriceRule rule) {
		// TODO Auto-generated method stub

		List<TransformPriceRule> rules = new ArrayList();
		
		HashMap map = new HashMap();
		
		map.put("code", rule.getCode());
	
		map.put("isdeleted", rule.getIsdeleted());
		if(rule.getTransformcategoryid()==0)
			map.put("transformcategoryid", null);
			else map.put("transformcategoryid", rule.getTransformcategoryid());
		 	
		if(rule.getWeightruleid()==0)
		map.put("weightruleid", null);
		else map.put("weightruleid", rule.getWeightruleid());
		
		if(rule.getWeightpriceruleid()==0)
			map.put("weightpriceruleid", null);
			else map.put("weightpriceruleid", rule.getWeightpriceruleid());
			
		map.put("destination", rule.getDestination());
		map.put("destinationcity", rule.getDestinationcity());
		map.put("mtype", rule.getMtype());
		
		if(rule.getTransformpriceruleid()==0)
			map.put("transformpriceruleid", null);
			else map.put("transformpriceruleid", rule.getTransformpriceruleid());
		 
	
		try {
			rules = sqlMapClient.queryForList("mail.getTransformPriceRuleNewList", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rules;

	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yundastat.dao.IMailDao#insertStatReport(com.yundastat.model.StatReport
	 * )
	 */
	public void insertStatReport(StatReport report) {

		try {
			sqlMapClient.insert("mail.insertStatReport", report);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertReport(final List<Report> report) {

		try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for (int i = 0; i < report.size(); i++) {

				sqlMapClient.insert("mail.insertReport", report.get(i));

				if (i % 500 == 0 && i > 1)
					sqlMapClient.executeBatch();

			}

			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

			try {

				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yundastat.dao.IMailDao#getStatReportPage(com.yundastat.model.
	 * PageParameter)
	 */
	public List<StatReport> getStatReportPage(PageParameter pageParameter) {
		List<StatReport> list = new ArrayList();
		// if(pageParameter.getManager().equals("全部"))
		// pageParameter.setManager(null);
		try {
			list = sqlMapClient.queryForList("mail.getStatReportPage",
					pageParameter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public int  getStatReportCount(PageParameter pageParameter) {
	 int list=0;
		// if(pageParameter.getManager().equals("全部"))
		// pageParameter.setManager(null);
		try {
			list = (Integer) sqlMapClient.queryForObject("mail.getStatReportCount",
					pageParameter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	
	public List<CompareReport> getCompareReportPage(PageParameter pageParameter) {
		List<CompareReport> list = new ArrayList();
		// if(pageParameter.getManager().equals("全部"))
		// pageParameter.setManager(null);
		try {
			list = sqlMapClient.queryForList("mail.getCompareReportPage",
					pageParameter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public List<StatReport> getStatReportPageByPageBean(PageBean pageBean) {
		List<StatReport> list = new ArrayList();
		// if(pageParameter.getManager().equals("全部"))
		// pageParameter.setManager(null);
		try {
			list = sqlMapClient.queryForList("mail.getStatReportPageByPageBean",
					pageBean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yundastat.dao.IMailDao#deleteExitedMailAndStatReport(com.yundastat
	 * .model.PageParameter)
	 */
	public void deleteExitedMailAndStatReport(PageParameter pageParameter) {

		try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			
			sqlMapClient.delete("mail.deleteExitedStatReport", pageParameter);
			sqlMapClient.delete("mail.deleteStatSMY", pageParameter);
			
			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			try {

				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	 
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yundastat.dao.IMailDao#getManagerInfo(java.lang.String)
	 */
	public List<ManagerInfo> getManagerInfo(String managername,String managerid) {
		// TODO Auto-generated method stub

		List<ManagerInfo> rule = new ArrayList();
		HashMap map = new HashMap();
		map.put("managername", managername);
		map.put("managerid", managerid);
		try {
			rule = sqlMapClient.queryForList("mail.getManagerInfo", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rule;

	}
	
	
	public List<ManagerInfo> queryForSubManager(ManagerInfo manager) {
		// TODO Auto-generated method stub

		List<ManagerInfo> rule =null;
	 
		try {
			rule = sqlMapClient.queryForList("mail.queryForSubManager", manager);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rule;

	}
	
	
	
	public List<ManagerInfo> getManagerInfoNew(ManagerInfo m) {
		// TODO Auto-generated method stub

		List<ManagerInfo> rule = new ArrayList();
		HashMap map = new HashMap();
		map.put("managername", m.getManagername());
		map.put("managerid", m.getManagerid());
		map.put("isdeleted", m.getIsdeleted());
		map.put("roles", m.getRoles());
		map.put("state", m.getState());
		map.put("cardid", m.getCardid());//区分结算用户和非结算用户
		map.put("salesid", m.getSalesid());//区分员工和结算用户
		try {
			rule = sqlMapClient.queryForList("mail.getManagerInfoNew", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rule;

	}
	 
	
	public List<StatSMY> getStatSMY(PageParameter pageParameter) {
		List<StatSMY> list = new ArrayList();
		// if(pageParameter.getManager().equals("全部"))
		// pageParameter.setManager(null);
		try {
			list = sqlMapClient.queryForList("mail.getStatSMY",
					pageParameter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	 
	
	public void deleteTransformPrice(String code,int transformcategoryid){
		
		HashMap map = new HashMap();
		map.put("code", code);
		map.put("transformcategoryid", transformcategoryid);
		try {
		 sqlMapClient.delete("mail.deleteTransformPrice", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ;
		
	}
	
	public void deleteTransformPrice(TransformPriceRule t){
		
		HashMap map = new HashMap();
	   map.put("transformpriceruleid", t.getTransformpriceruleid());
		try {
		 sqlMapClient.delete("mail.deleteTransformPriceById", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ;
		
	}
	public void updateWightRule(WeightRule r) {
		// TODO Auto-generated method stub

		 
		try {
		 sqlMapClient.update("mail.updateWeightRule", r);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ;

	}
	
	public void updateWeightPriceRule(WeightPriceRule r) {
		// TODO Auto-generated method stub

		 
		try {
		 sqlMapClient.update("mail.updateWeightPriceRule", r);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ;

	}
	
	
	
	
	public List<MailInfo> getMailInfoList(MailParameter parameter)
			throws SQLException {
		List<MailInfo> list = new ArrayList();
		if (parameter.getQuerytype().equals("today"))
			list = sqlMapClient.queryForList("mail.getTodayMail", parameter);
		if (parameter.getQuerytype().equals("delay"))
			list = sqlMapClient.queryForList("mail.getDelayMail", parameter);
		if (parameter.getQuerytype().equals("waiting"))
			list = sqlMapClient.queryForList("mail.getWaitingMail", parameter);
		if (parameter.getQuerytype().equals("miss"))
			list = sqlMapClient.queryForList("mail.getMissMail", parameter);
		if (parameter.getQuerytype().equals("error"))
			list = sqlMapClient.queryForList("mail.getErrorMail", parameter);

		return list;
	}
	
	public void insertManager(ManagerInfo manager){
		
		try {
			sqlMapClient.insert("mail.insertManager", manager);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void  updateManager(ManagerInfo manager){
		
		try {
			sqlMapClient.update("mail.updateManager", manager);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void  deleteManager(ManagerInfo manager){
		
		try {
			sqlMapClient.delete("mail.deleteManager", manager);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public List<StatReport> queryForMailByMailId(int mon,String mailid,String state){
		
		List<StatReport> ss=null;
		
		HashMap map=new HashMap();
		map.put("mailid", mailid);
		map.put("state", state);
		map.put("mon",mon);
		try {
			ss=sqlMapClient.queryForList("mail.getStatReportByMailId", map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}
	 
     
    public void insertPriceDetail(PriceDetail manager){
		
		try {
			sqlMapClient.insert("mail.insertPriceDetail", manager);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void  deletePriceDetail(PriceDetailSmy manager){
		
		try {
			sqlMapClient.delete("mail.deletePriceDetail", manager);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
    public void deletePriceDetailSmy(PriceDetailSmy map) {
		 
		 try {
				sqlMapClient.delete("mail.deletePriceDetailSmy", map);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void createPriceDetailSmy(PriceDetailSmy map) {
		 
		 try {
				sqlMapClient.insert("mail.insertPriceDetailSmy", map);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public void deletePriceDetailManagerSmy(PriceDetailSmy map) {
		 try {
				sqlMapClient.delete("mail.deletePriceDetailManagerSmy", map);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	public void createPriceDetailManagerSmy(PriceDetailSmy map) {
	 
			 try {
					sqlMapClient.insert("mail.insertPriceDetailManagerSmy", map);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 
	}
	
	
    public List<PriceDetail> getPriceDetailList(PageParameter p){
		
		List<PriceDetail> ss=null;
	 
		try {
			ss=sqlMapClient.queryForList("mail.getPriceDetailList", p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}
	
	
      public List<PriceDetailSmy> getPriceDetailManagerSmy(PageParameter p){
		
		List<PriceDetailSmy> ss=null;
	 
		try {
			ss=sqlMapClient.queryForList("mail.getPriceDetailManagerSmy", p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}
	
      public int getPriceDetailSmyCount(PageParameter p){
  		
  		int ss=0;
  	 
  		try {
  			ss=(Integer) sqlMapClient.queryForObject("mail.getPriceDetailSmyCount", p);
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		return ss;
  	}
      
      
      public List<PriceDetailSmy> getPriceDetailSmy(PageParameter p){
  		
  		List<PriceDetailSmy> ss=null;
  		try {
  			ss=sqlMapClient.queryForList("mail.getPriceDetailSmy", p);
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		return ss;
  	}
  	
      
      
	
    
}
