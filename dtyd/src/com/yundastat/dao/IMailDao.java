package com.yundastat.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.yundastat.model.MailInfo;
import com.yundastat.model.MailParameter;
import com.yundastat.model.ManagerInfo;
import com.yundastat.model.PageParameter;
import com.yundastat.model.Report;
import com.yundastat.model.StatReport;
import com.yundastat.model.TransformPriceRule;
import com.yundastat.model.WeightPriceRule;
import com.yundastat.model.WeightRule;

public interface IMailDao {

	public abstract SqlMapClient getSqlMapClient();

	public abstract void setSqlMapClient(SqlMapClient sqlMapClient);

	/***
	 *
	 * */
	public abstract MailInfo getMailByMailid(String mailid);
	public abstract List<MailInfo> getMailInfoList(MailParameter parameter) throws SQLException;
	public abstract void insertSourceMail(MailInfo mail) throws SQLException;
	public abstract void insertTransfromMailInfo(MailInfo mail)throws SQLException;
	public abstract int insertSourceMailList(List<MailInfo> mail)
			throws SQLException;

	public abstract void insertWeightRuleAndUpdateRule(WeightRule rule);

	public abstract WeightRule getWeightRule();

	public abstract WeightPriceRule getWeightPriceRule();

	public abstract void insertWeightPriceRuleAndUpdateRule(WeightPriceRule rule);


	public abstract void insertTransformPriceRuleAndUpdateRule(
			TransformPriceRule rule);

	public abstract List<TransformPriceRule> getTransformPriceRule(String code);

	public abstract void insertStatReport(StatReport report);
	public abstract void  insertReport(List<Report> report) throws SQLException;
	public abstract List<StatReport> getStatReportPage(
			PageParameter pageParameter);

	public abstract void deleteExitedMailAndStatReport(PageParameter pageParameter);

	public abstract List<ManagerInfo> getManagerInfo(String managername,String managerid);
	public List<ManagerInfo> getManagerInfoNew(ManagerInfo m) ;
	  public List<TransformPriceRule> getTransformPriceRuleList(TransformPriceRule rule);
}