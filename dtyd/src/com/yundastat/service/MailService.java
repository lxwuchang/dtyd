package com.yundastat.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.yundastat.dao.IMailDao;
import com.yundastat.dao.MailDao;
import com.yundastat.dao.UserDao;
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
import com.yundastat.model.User;
import com.yundastat.model.UserInfo;
import com.yundastat.model.WeightPriceRule;
import com.yundastat.model.WeightRule;



public class MailService {
	private MailDao mailDao;

	public MailDao getMailDao() {
		return mailDao;
	}



	public void setMailDao(MailDao mailDao) {
		this.mailDao = mailDao;
	}



	public MailInfo queryMailByMailid(String mailid) {
		// TODO Auto-generated method stub

		MailInfo mail = mailDao.getMailByMailid(mailid);
		return mail;
	}
	
	public int updateMail(MailInfo mail){
		
		return mailDao.updateMail(mail);
	}

	 
	public StatReport queryStatReportByMailid(String mailid) {
		// TODO Auto-generated method stub

		StatReport mail = mailDao.getStatReportById(mailid);
		return mail;
	}
	


	public List<MailInfo> queryMailInfoByMap(MailInfo mail) {
		// TODO Auto-generated method stub
		
		return	new ArrayList();
	
	}
	
	public void insertMailInfo(MailInfo mail) throws SQLException {
		// TODO Auto-generated method stub
		mailDao.insertSourceMail(mail);
		
		return;
	
	}
	
	public void insertTransformMail(List<TransformMail> mail) throws SQLException {
		// TODO Auto-generated method stub
		mailDao.insertTransformMail(mail);
		
		return;
	
	}
	
	public void insertCompareReportList(List<CompareReport> report) throws SQLException {
		// TODO Auto-generated method stub
		mailDao.insertCompareReportList(report);
		
		return;
	
	}
	
	
	public void deleteExitedMailAndStatReportByMailid(PageParameter pageParameter) {
		// TODO Auto-generated method stub
		mailDao.deleteExitedMailAndStatReport(pageParameter);
		
		return;
	
	}
	
	
	public void updateWeightRule(WeightRule w){
		
		mailDao.updateWightRule(w);
		return;
	}
	
	
	public void createNewWeightRule(WeightRule rule) throws ParseException {
		// TODO Auto-generated method stub
		mailDao.insertWeightRuleAndUpdateRule(rule);
		
		return;
	
	}

	public int createTransformCategory(TransformCategory  t )  {
		
		return mailDao.insertTransformCategory( t);
	
	
		
}
	
	
	public int updateTransformCategory(TransformCategory  t )  {
		
		return 	mailDao.updateTransformCategory( t);
		
		
	}
		
	public List<TransformCategory> getTransformCategory(TransformCategory t) {
		// TODO Auto-generated method stub
		 List<TransformCategory>  rule= mailDao.queryForTransformCategory(t);
		return rule;
	
	}
	public  void deleteTransformCategory(TransformCategory t) {
		// TODO Auto-generated method stub
		  mailDao.deleteTransformCategory(t);
		return  ;
	
	}
	

	public  WeightRule  queryForWeightRule() {
		// TODO Auto-generated method stub
		WeightRule  rule= mailDao.getWeightRule();
		
		return rule;
	
	}
	public List<WeightRule> queryForWeightRuleList(WeightRule weight){
		
		List<WeightRule> rule= mailDao.getWeightRuleList(weight);
	
	return rule;

}
	
	public WeightPriceRule queryForWeightPriceRule() throws SQLException {
		// TODO Auto-generated method stub
		WeightPriceRule rule= mailDao.getWeightPriceRule();
		
		return rule;
	
	}
	
	public List<WeightPriceRule> queryForWeightPriceRuleList(WeightPriceRule weight) throws SQLException {
		// TODO Auto-generated method stub
		 List<WeightPriceRule> rule= mailDao.getWeightPriceRuleList(weight);
		
		return rule;
	
	}
	public void createNewWeightPriceRule(WeightPriceRule rule) {
		// TODO Auto-generated method stub
		mailDao.insertWeightPriceRuleAndUpdateRule(rule);
		
		return;
	
	}
 
	 
	
	public List<TransformPriceRule> queryForTransformPriceRule(String code) {
		// TODO Auto-generated method stub
		 List<TransformPriceRule>  rule= mailDao.getTransformPriceRule(code);
		
		return rule;
	
	}
	

	public List<TransformPriceRule> queryForTransformPriceRule(TransformPriceRule rule) {
		// TODO Auto-generated method stub
		 List<TransformPriceRule>  rules= mailDao.getTransformPriceRule(rule);
		
		return rules;
	
	}
	
	public List<TransformPriceRule> queryForTransformPriceRuleList(TransformPriceRule rule) {
		// TODO Auto-generated method stub
		 List<TransformPriceRule>  rules= mailDao.getTransformPriceRuleList(rule);
		
		return rules;
	
	}
	
	
	
	public void createNewTransformPriceRule(TransformPriceRule rule) {
		// TODO Auto-generated method stub
		mailDao.insertTransformPriceRule(rule);
		
		return;
	
	}
	public void updateTransformPriceRule(TransformPriceRule rule) {
		// TODO Auto-generated method stub
		mailDao.insertTransformPriceRuleAndUpdateRule(rule);
		
		return;
	
	}
	
	public void updateTransformPriceRuleNew(TransformPriceRule rule) {
		// TODO Auto-generated method stub
		mailDao.updateTransformPriceRule(rule);
		
		return;
	
	}
	
	
	
	
	public void createStatReport(StatReport rule) {
		// TODO Auto-generated method stub
		mailDao.insertStatReport(rule);
		
		return;
	
	}
	
	public void  createReport(List<Report> report){
		mailDao.insertReport(report);
		return;
		
	}
	public List<StatReport> queryForStatReportPage(PageParameter pageParameter) {
		// TODO Auto-generated method stub
		 List<StatReport>  rule= mailDao.getStatReportPage(pageParameter);
		
		return rule;
	
	}
	
	public int queryForStatReportCount(PageParameter pageParameter) {
		// TODO Auto-generated method stub
		 int rule= mailDao.getStatReportCount(pageParameter);
		return rule;
	
	}
	
	
	public List<CompareReport> queryForCompareReportPage(PageParameter pageParameter) {
		// TODO Auto-generated method stub
		 List<CompareReport>  rule= mailDao.getCompareReportPage(pageParameter);
		
		return rule;
	
	}
	
	
	
	public List<StatReport> queryForStatReportPageByPageBean(PageBean pageBean) {
		// TODO Auto-generated method stub
		 List<StatReport>  rule= mailDao.getStatReportPageByPageBean(pageBean);
		
		return rule;
	
	}
	
	public void  deleteExitedMailAndStatReport(PageParameter pageParameter) {
		// TODO Auto-generated method stub
		mailDao.deleteExitedMailAndStatReport(pageParameter);
		
		return ;
	
	}
	
	
	
	public List<ManagerInfo> queryForSubManager(ManagerInfo manager) {
		// TODO Auto-generated method stub
		 List<ManagerInfo>  rule= mailDao.queryForSubManager(manager);
		return rule;
	
	}
	
	
	public List<ManagerInfo> queryForManagerInfo(String managername,String managerid) {
		// TODO Auto-generated method stub
		 List<ManagerInfo>  rule= mailDao.getManagerInfo(managername,managerid);
		return rule;
	
	}
	
	public List<ManagerInfo> queryForManagerInfoNew(ManagerInfo m) {
		// TODO Auto-generated method stub
		 List<ManagerInfo>  rule= mailDao.getManagerInfoNew(m);
		return rule;
	
	}
	
	 
	public List<StatSMY> queryForStatSMY(PageParameter smy) {
		// TODO Auto-generated method stub
		 List<StatSMY>  rule= mailDao.getStatSMY(smy);
		return rule;
	
	}
	
	
	public void deleteTransformPrice(String code,int transformcategoryid){
		
		mailDao.deleteTransformPrice(code,transformcategoryid);
		return;
	}
	

	public void deleteTransformPrice(TransformPriceRule t){
		
		mailDao.deleteTransformPrice(t);
		return;
	}
	
	
	
	
	public void insertSourceMailList(List<MailInfo> mailList) throws SQLException {
		
			mailDao.insertSourceMailList(mailList);
		
		
	}
	
	
	public void createStatSMY(PageParameter param) throws ParseException {
		
		mailDao.createStatSMY(param);
	
	
}

	



	public List<MailInfo> queryForMail(MailParameter para) throws SQLException {
		// TODO Auto-generated method stub
		return mailDao.getMailInfoList(para);
	}



	public List<MailInfo> queryForTransformMail(PageParameter para) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateStatReport(HashMap map){
		mailDao.updateStatReport(map);
		return;
		
	}
	
	public void updateStatReportNew(StatReport map){
		mailDao.updateStatReportNew(map);
		return;
		
	}
	public void createReportFlag(StatSMY map){
		mailDao.insertReportFlag(map);
		return;
		
	}
	public void updateManager(ManagerInfo manager){
		mailDao.updateManager(manager);
		return;
		
	}
	
	public void deleteManager(ManagerInfo manager){
		mailDao.deleteManager(manager);
		return;
		
	}
	
 
	public List<StatSMY> queryReportFlagByPage(PageParameter date){
		
		return mailDao.getReportFlagByPage(date);
	}
	
	public void createManager(ManagerInfo manager){
		mailDao.insertManager(manager);
		return;
	}
	public List<StatReport> queryForMailByMailId(int mon,String mailid,String state){
		return mailDao.queryForMailByMailId(mon,mailid,state);
		
	}
	public int deleteStatReport(StatReport map) {
		return mailDao.deleteStatReport(map);
	}
	public void updateReportFlag(StatSMY map) {
		 mailDao.updateReportFlag(map);
	}
	
	public void deleteReportFlag(StatSMY map) {
		 mailDao.deleteReportFlag(map);
	}
	public void updateWeightPriceRule(WeightPriceRule m) {
		 mailDao.updateWeightPriceRule(m);
	}
	
	public ReportFlag getStatReportSmy(PageParameter m) {
		ReportFlag flag= mailDao.getStatReportSmy(m);
		
		return flag;
	}
	 
	public void createPriceDetail(PriceDetail detail) {

		mailDao.insertPriceDetail(detail);
		return;
	}
	public void deletePriceDetail(PriceDetailSmy map) {
		 mailDao.deletePriceDetail(map);
	}
	
	public void deletePriceDetailSmy(PriceDetailSmy map) {
		 mailDao.deletePriceDetailSmy(map);
		 return;
	}
	
	public void createPriceDetailSmy(PriceDetailSmy map) {
		 mailDao.createPriceDetailSmy(map);
		 return;
	}
	
	public int getPriceDetailSmyCount(PageParameter map) {
		return mailDao.getPriceDetailSmyCount(map);
		  
	}
	
	public List<PriceDetailSmy> getPriceDetailSmy(PageParameter map) {
		return mailDao.getPriceDetailSmy(map);
		 
	}
	
	  public List<PriceDetail> getPriceDetailList(PageParameter p){
		  
		  return mailDao.getPriceDetailList(p);
	  }
	
	
	public void deletePriceDetailManagerSmy(PriceDetailSmy map) {
		 mailDao.deletePriceDetailManagerSmy(map);
		 return;
	}
	
	
	
	public void createPriceDetailManagerSmy(PriceDetailSmy map) {
		 mailDao.createPriceDetailManagerSmy(map);
		 return;
	}
	
	
	public List<PriceDetailSmy> getPriceDetailManagerSmy(PageParameter p){
		
		return mailDao.getPriceDetailManagerSmy(p);
	}
	


}
