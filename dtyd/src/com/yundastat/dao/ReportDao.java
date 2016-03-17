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
import com.yundastat.model.CompanyIncome;
import com.yundastat.model.CompareReport;
import com.yundastat.model.DailyBalance;
import com.yundastat.model.FankuanOutcome;
import com.yundastat.model.GongziOutcome;
import com.yundastat.model.Income;
import com.yundastat.model.MailFenpei;
import com.yundastat.model.MailInfo;
import com.yundastat.model.MailParameter;
import com.yundastat.model.ManagerInfo;
import com.yundastat.model.PageBean;
import com.yundastat.model.PageParameter;
import com.yundastat.model.PeikuanOutcome;
import com.yundastat.model.Report;
import com.yundastat.model.ReportFlag;
import com.yundastat.model.StatReport;
import com.yundastat.model.StatSMY;
import com.yundastat.model.TransformCategory;
import com.yundastat.model.TransformMail;
import com.yundastat.model.TransformPriceRule;
import com.yundastat.model.WeightPriceRule;
import com.yundastat.model.WeightRule;
import com.yundastat.model.YearSmy;
import com.yundastat.model.ZhichuOutcome;
import com.yundastat.model.ZhipaoOutcome;

/**
 * @author hp
 * 
 */
public class ReportDao {
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

	 
	
	public List<YearSmy> getYearSmy(YearSmy state){
		
		List<YearSmy> ss=null;
	 
		try {
			ss=sqlMapClient.queryForList("report.getYearSmy", state);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}
	
	 public void createYearSmy(YearSmy state){
		  
		 
			try {
				sqlMapClient.insert("report.insertYearSmy", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
	 

	 public void updateYearSmy(YearSmy state){
		  
		 
			try {
				sqlMapClient.insert("report.updateYearSmy", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
	 

	public Date getMaxDaySmy(){
		
		Date ss=null;
	 
		try {
			ss=(Date) sqlMapClient.queryForObject("report.getMaxDaySmy");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}
	
	
   public YearSmy getTYearSmy(YearSmy smy){
		
	   YearSmy ss=null;
	 
		try {
			ss=(YearSmy) sqlMapClient.queryForObject("report.getTYearSmy",smy);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}
	
	
   public List<YearSmy> getTMonthSmy(YearSmy smy){
		
	   List<YearSmy> ss=null;
	 
		try {
			ss= sqlMapClient.queryForList("report.getTMonthSmy",smy);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}
   
   
	 public List<DailyBalance> getDailyBalance(DailyBalance state){
		 
			List<DailyBalance> ss=null;
			 
			try {
				ss=sqlMapClient.queryForList("report.getDailyBalance", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		}
	 
	 public List<DailyBalance> getDailyBalanceForStatuser(DailyBalance state){
		 
			List<DailyBalance> ss=null;
			 
			try {
				ss=sqlMapClient.queryForList("report.getDailyBalanceForStatuser", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		}
	 
	 public List<DailyBalance> getDailyBalanceForStatuserByMonth(DailyBalance state){
		 
			List<DailyBalance> ss=null;
			 
			try {
				ss=sqlMapClient.queryForList("report.getDailyBalanceForStatuserByMonth", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		}
	 
	 public List<DailyBalance> getDailyBalanceForStatuserByYear(DailyBalance state){
		 
			List<DailyBalance> ss=null;
			 
			try {
				ss=sqlMapClient.queryForList("report.getDailyBalanceForStatuserByYear", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		} 
	 public void updateDailyBalance(DailyBalance state){
		  
			 
			try {
				sqlMapClient.update("report.updateDailyBalance", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
	 public void createDailyBalance(DailyBalance state){
		  
		 
			try {
				sqlMapClient.insert("report.insertDailyBalance", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}

	 
	 
	 public DailyBalance getDailyBalanceSmy(DailyBalance state){
		 
		 DailyBalance ss=null;
			 
			try {
				ss=(DailyBalance) sqlMapClient.queryForObject("report.getDailyBalanceSmy", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		}
	 
     public List<DailyBalance> getDailyBalanceSmyByManager(DailyBalance state){
		 
    	 List<DailyBalance>  ss=null;
			 
			try {
				ss= sqlMapClient.queryForList("report.getDailyBalanceSmyByManager", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		}
	 
    public List<DailyBalance> getDailyBalanceSmyByDay(DailyBalance state){
		 
    	 List<DailyBalance>  ss=null;
			 
			try {
				ss= sqlMapClient.queryForList("report.getDailyBalanceSmyByDay", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		}
    
    public List<DailyBalance> getDailyBalanceMonthSmyByManager(DailyBalance state){
		 
   	 List<DailyBalance>  ss=null;
			 
			try {
				ss= sqlMapClient.queryForList("report.getDailyBalanceMonthSmyByManager", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		}
    
    public List<DailyBalance> getDailyBalanceSmyByMonth(DailyBalance state){
		 
    	 List<DailyBalance>  ss=null;
			 
			try {
				ss= sqlMapClient.queryForList("report.getDailyBalanceSmyByMonth", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		}
    
	 public void createMailFenpei(MailFenpei state){
		  
		 
			try {
				sqlMapClient.insert("report.insertMailFenpei", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}

	  public Integer getMailFenpeiListCount(PageParameter state){
			 
		  Integer  ss=0;
				 
				try {
					ss= (Integer) sqlMapClient.queryForObject("report.getMailFenpeiListCount", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			}
	 
    
	  
	  public  List<MailFenpei> getMailFenpeiList(PageParameter state){
		  
		  List<MailFenpei> ss=null;
			try {
				ss=   sqlMapClient.queryForList("report.getMailFenpeiList", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		  	 
		  }
	 
	  
	  public  List<MailFenpei> getMailFenpeiSmyList(PageParameter state){
		  
		  List<MailFenpei> ss=null;
			try {
				ss=   sqlMapClient.queryForList("report.getMailFenpeiSmyList", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		  	 
		  }
	  
	  
	  public  List<MailFenpei> getMailFenpeiSmyByDayList(PageParameter state){
		  
		  List<MailFenpei> ss=null;
			try {
				ss=   sqlMapClient.queryForList("report.getMailFenpeiSmyByDayList", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		  	 
		  }
	  
  public  List<MailFenpei> getMailFenpeiSmyByMonthList(PageParameter state){
		  
		  List<MailFenpei> ss=null;
			try {
				ss=   sqlMapClient.queryForList("report.getMailFenpeiSmyByMonthList", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ss;
		  	 
		  }
  
  public  List<MailFenpei> getMailFenpeiSmyByMonthManagerList(PageParameter state){
	  
	  List<MailFenpei> ss=null;
		try {
			ss=   sqlMapClient.queryForList("report.getMailFenpeiSmyByMonthManagerList", state);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	  	 
	  }
	  
  

	  public MailFenpei getMailFenpeiSmy(MailFenpei state){
			 
		  MailFenpei  ss=null;
				 
				try {
					ss=  (MailFenpei) sqlMapClient.queryForObject("report.getMailFenpeiSmy", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			}
	  
	  public void deleteMailFenpei(MailFenpei state){
		  
			 
			try {
				sqlMapClient.delete("report.deleteMailFenpei", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
	  
	  public void updateMailFenpei(MailFenpei state){
		  
			 
			try {
				sqlMapClient.update("report.updateMailFenpei", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
	  
	  
	  public void updateIncome(Income state){
		  
			 
			try {
				sqlMapClient.update("report.updateIncome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
	  
	  
		 public void createCompanyIncome(CompanyIncome state){
			  
			 
				try {
					sqlMapClient.insert("report.insertCompanyIncome", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
	   
		  public Integer getCompanyIncomeListCount(PageParameter state){
				 
			  Integer  ss=0;
					 
					try {
						ss= (Integer) sqlMapClient.queryForObject("report.getCompanyIncomeListCount", state);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return ss;
				}
		 
	    
		  
		  public  List<CompanyIncome> getCompanyIncomeList(PageParameter state){
			  
			  List<CompanyIncome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getCompanyIncomeList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }
		  
		  public void deleteCompanyIncome(CompanyIncome state){
			  
				 
				try {
					sqlMapClient.delete("report.deleteCompanyIncome", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
		  
		  
		  public  List<CompanyIncome> getCompanyIncomeSmyList(PageParameter state){
			  
			  List<CompanyIncome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getCompanyIncomeSmyList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }
       public  List<CompanyIncome> getCompanyIncomeSmyByDayList(PageParameter state){
			  
			  List<CompanyIncome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getCompanyIncomeSmyByDayList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }
		 
       
       public  List<CompanyIncome> getCompanyIncomeSmyByMonthList(PageParameter state){
			  
			  List<CompanyIncome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getCompanyIncomeSmyByMonthList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }
       
       
       public  List<CompanyIncome> getCompanyIncomeMonthDetailList(PageParameter state){
			  
			  List<CompanyIncome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getCompanyIncomeMonthDetailList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }
       
		  
       public  List<CompanyIncome> getCompanyIncomeMonthSmyByManagerList(PageParameter state){
			  
			  List<CompanyIncome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getCompanyIncomeMonthSmyByManagerList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }


       public  List<FankuanOutcome> getFankuanOutcomeList(FankuanOutcome state){
  		  
  		  List<FankuanOutcome> ss=null;
  			try {
  				ss=   sqlMapClient.queryForList("report.getFankuanOutcomeList", state);
  			} catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  			return ss;
  		  	 
  		  }
       
    

       public void createFankuanOutcome(FankuanOutcome state){
			  
			 
			try {
				sqlMapClient.insert("report.insertFankuanOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
    

       public void deleteFankuanOutcome(FankuanOutcome state){
			  
			 
			try {
				sqlMapClient.insert("report.deleteFankuanOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
    
       public void updateFankuanOutcome(FankuanOutcome state){
			  
			 
			try {
				sqlMapClient.update("report.updateFankuanOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
   
       
       
       public  List<ZhipaoOutcome> getZhipaoOutcomeList(ZhipaoOutcome state){
   		  
   		  List<ZhipaoOutcome> ss=null;
   			try {
   				ss=   sqlMapClient.queryForList("report.getZhipaoOutcomeList", state);
   			} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			return ss;
   		  	 
   		  }
        
     

        public void createZhipaoOutcome(ZhipaoOutcome state){
 			  
 			 
 			try {
 				sqlMapClient.insert("report.insertZhipaoOutcome", state);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			 
 		}
     

        public void deleteZhipaoOutcome(ZhipaoOutcome state){
 			  
 			 
 			try {
 				sqlMapClient.insert("report.deleteZhipaoOutcome", state);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			 
 		}
     
        public void updateZhipaoOutcome(ZhipaoOutcome state){
 			  
 			 
 			try {
 				sqlMapClient.update("report.updateZhipaoOutcome", state);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			 
 		}
    
        
        public  List<PeikuanOutcome> getPeikuanOutcomeList(PeikuanOutcome state){
     		  
     		  List<PeikuanOutcome> ss=null;
     			try {
     				ss=   sqlMapClient.queryForList("report.getPeikuanOutcomeList", state);
     			} catch (SQLException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
     			return ss;
     		  	 
     		  }
          
        
        
        public  List<PeikuanOutcome> getPeikuanOutcomeSmyList(PeikuanOutcome state){
     		  
     		  List<PeikuanOutcome> ss=null;
     			try {
     				ss=   sqlMapClient.queryForList("report.getPeikuanOutcomeSmyList", state);
     			} catch (SQLException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
     			return ss;
     		  	 
     		  }
        

          public void createPeikuanOutcome(PeikuanOutcome state){
   			  
   			 
   			try {
   				sqlMapClient.insert("report.insertPeikuanOutcome", state);
   			} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			 
   		}
       

          public void deletePeikuanOutcome(PeikuanOutcome state){
   			  
   			 
   			try {
   				sqlMapClient.insert("report.deletePeikuanOutcome", state);
   			} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			 
   		}
       
          public void updatePeikuanOutcome(PeikuanOutcome state){
   			  
   			 
   			try {
   				sqlMapClient.update("report.updatePeikuanOutcome", state);
   			} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			 
   		}
      
        
        
        
       public  List<ZhichuOutcome> getZhichuOutcomeList(ZhichuOutcome state){
  		  
  		  List<ZhichuOutcome> ss=null;
  			try {
  				ss=   sqlMapClient.queryForList("report.getZhichuOutcomeList", state);
  			} catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  			return ss;
  		  	 
  		  }
       
    

       public void createZhichuOutcome(ZhichuOutcome state){
			  
			 
			try {
				sqlMapClient.insert("report.insertZhichuOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
    

       public void deleteZhichuOutcome(ZhichuOutcome state){
			  
			 
			try {
				sqlMapClient.insert("report.deleteZhichuOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
    
       public void updateZhichuOutcome(ZhichuOutcome state){
			  
			 
			try {
				sqlMapClient.update("report.updateZhichuOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
   
       
       
       
       
       
       public  List<GongziOutcome> getGongziOutcomeList(GongziOutcome state){
  		  
  		  List<GongziOutcome> ss=null;
  			try {
  				ss=   sqlMapClient.queryForList("report.getGongziOutcomeList", state);
  			} catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  			return ss;
  		  	 
  		  }
       
    

       public void createGongziOutcome(GongziOutcome state){
			  
			 
			try {
				sqlMapClient.insert("report.insertGongziOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
    

       public void deleteGongziOutcome(GongziOutcome state){
			  
			 
			try {
				sqlMapClient.insert("report.deleteGongziOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
    
       public void updateGongziOutcome(GongziOutcome state){
			  
			 
			try {
				sqlMapClient.update("report.updateGongziOutcome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
   
       
       
       public void createIncome(Income state){
			  
			 
			try {
				sqlMapClient.insert("report.insertIncome", state);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
 
       public Integer getIncomeListCount(Income state){
			 
 		  Integer  ss=0;
 				 
 				try {
 					ss= (Integer) sqlMapClient.queryForObject("report.getIncomeListCount", state);
 				} catch (SQLException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 				return ss;
 			}
 	 
     
 	  
 	  public  List<Income> getIncomeList(Income state){
 		  
 		  List<Income> ss=null;
 			try {
 				ss=   sqlMapClient.queryForList("report.getIncomeList", state);
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			return ss;
 		  	 
 		  }
 	  
 	    public  List<Income> getIncomeSmyList(Income state){
			  
			  List<Income> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getIncomeSmyList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
		
 	    }
 	    public  List<Income> getIncomeYearSmyList(Income state){
			  
			  List<Income> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getIncomeYearSmyList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }
	 
 	    
 	   
 	    
	    public  List<Income> getIncomeSmyByDayList(Income state){
			  
			  List<Income> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getIncomeSmyByDayList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }
	 
	    public void deleteIncome(Income state){
			  
			 
				try {
					  sqlMapClient.delete("report.deleteIncome", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			  	 
			  }
	    
	    
	    public  List<Income> getIncomeSmyByMonthList(Income state){
			  
			  List<Income> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getIncomeSmyByMonthList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }  
	    
	    public  List<ZhipaoOutcome> getZhipaoOutcomeSmyByMonthList(ZhipaoOutcome state){
			  
			  List<ZhipaoOutcome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getZhipaoOutcomeSmyByMonthList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }  
	    
	    
	    public  List<ZhichuOutcome> getZhichuOutcomeSmyByMonthList(ZhichuOutcome state){
			  
			  List<ZhichuOutcome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getZhichuOutcomeSmyByMonthList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }  
	    
	    
	    public  List<GongziOutcome> getGongziOutcomeSmyByMonthList(GongziOutcome state){
			  
			  List<GongziOutcome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getGongziOutcomeSmyByMonthList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }  
	    
	    
	    public  List<FankuanOutcome> getFankuanOutcomeSmyByMonthList(FankuanOutcome state){
			  
			  List<FankuanOutcome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getFankuanOutcomeSmyByMonthList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }  
	    
	    public  List<PeikuanOutcome> getPeikuanOutcomeSmyByMonthList(PeikuanOutcome state){
			  
			  List<PeikuanOutcome> ss=null;
				try {
					ss=   sqlMapClient.queryForList("report.getPeikuanOutcomeSmyByMonthList", state);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ss;
			  	 
			  }  
	    
	    
	    
}
