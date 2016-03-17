package com.yundastat.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.yundastat.dao.IMailDao;
import com.yundastat.dao.MailDao;
import com.yundastat.dao.ReportDao;
import com.yundastat.dao.UserDao;
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
import com.yundastat.model.User;
import com.yundastat.model.UserInfo;
import com.yundastat.model.WeightPriceRule;
import com.yundastat.model.WeightRule;
import com.yundastat.model.YearSmy;
import com.yundastat.model.ZhichuOutcome;
import com.yundastat.model.ZhipaoOutcome;



public class ReportService {
	private ReportDao reportDao;

	 



	public ReportDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public List<YearSmy> getYearSmy(YearSmy state){
		
		return  reportDao.getYearSmy(state);
	}
	
 
    
    public void createYearSmy(YearSmy state){
		
		 reportDao.createYearSmy(state);
	}
    
    
    public void updateYearSmy(YearSmy state){
		
		 reportDao.updateYearSmy(state);
	}
    
    
	
    public Date getMaxDaySmy(){
		
		return  reportDao.getMaxDaySmy();
	}
	
    public YearSmy getTYearSmy(YearSmy smy){
		
		return  reportDao.getTYearSmy(smy);
	}
    
    
  public List<YearSmy> getTMonthSmy(YearSmy smy){
		
		return  reportDao.getTMonthSmy(smy);
	}
	
    public List<DailyBalance> getDailyBalance(DailyBalance state){
		
		return  reportDao.getDailyBalance(state);
	}
   public List<DailyBalance> getDailyBalanceForStatuser(DailyBalance state){
		
		return  reportDao.getDailyBalanceForStatuser(state);
	}
   
   public List<DailyBalance> getDailyBalanceForStatuserByMonth(DailyBalance state){
		
		return  reportDao.getDailyBalanceForStatuserByMonth(state);
	}
   
   public List<DailyBalance> getDailyBalanceForStatuserByYear(DailyBalance state){
		
		return  reportDao.getDailyBalanceForStatuserByYear(state);
	}
   
   
   
    public void updateDailyBalance(DailyBalance state){
		
		 reportDao.updateDailyBalance(state);
	}
    
    
    
    public void createDailyBalance(DailyBalance state){
		
		 reportDao.createDailyBalance(state);
	}
    
    public DailyBalance getDailyBalanceSmy(DailyBalance state){
    	
    	return reportDao.getDailyBalanceSmy(state);
    }
    
    
    public List<DailyBalance> getDailyBalanceSmyByManager(DailyBalance state){
    	
    	return reportDao.getDailyBalanceSmyByManager(state);
    }
    
    
    public List<DailyBalance> getDailyBalanceSmyByDay(DailyBalance state){
    	
    	return reportDao.getDailyBalanceSmyByDay(state);
    }
    
    
    public List<DailyBalance> getDailyBalanceMonthSmyByManager(DailyBalance state){
    	
    	return reportDao.getDailyBalanceMonthSmyByManager(state);
    }
    
    
    
    
    
  public List<DailyBalance> getDailyBalanceSmyByMonth(DailyBalance state){
    	
    	return reportDao.getDailyBalanceSmyByMonth(state);
    }
  
  public void createMailFenpei(MailFenpei state){
		
		 reportDao.createMailFenpei(state);
	}
    
  
  public Integer getMailFenpeiListCount(PageParameter state){
  	
  	return reportDao.getMailFenpeiListCount(state);
  }
  
  public  List<MailFenpei> getMailFenpeiList(PageParameter state){
	  	
	  	return reportDao.getMailFenpeiList(state);
	  }
  public  List<MailFenpei> getMailFenpeiSmyList(PageParameter state){
	  	
	  	return reportDao.getMailFenpeiSmyList(state);
	  }

  public  List<MailFenpei> getMailFenpeiSmyByDayList(PageParameter state){
	  	
	  	return reportDao.getMailFenpeiSmyByDayList(state);
	  }
  public  List<MailFenpei> getMailFenpeiSmyByMonthList(PageParameter state){
	  	
	  	return reportDao.getMailFenpeiSmyByMonthList(state);
	  }

  public  List<MailFenpei> getMailFenpeiSmyByMonthManagerList(PageParameter state){
	  	
	  	return reportDao.getMailFenpeiSmyByMonthManagerList(state);
	  }
  
  
  public MailFenpei getMailFenpeiSmy(MailFenpei state){
	  	
	  	return reportDao.getMailFenpeiSmy(state);
	  }

  public void deleteMailFenpei(MailFenpei state){
		
		 reportDao.deleteMailFenpei(state);
	}
  
  public void updateMailFenpei(MailFenpei state){
		
		 reportDao.updateMailFenpei(state);
	}
  
  public void updateIncome(Income state){
		
		 reportDao.updateIncome(state);
	}


  
  
  public void createCompanyIncome(CompanyIncome state){
		
		 reportDao.createCompanyIncome(state);
	}
 

public Integer getCompanyIncomeListCount(PageParameter state){
	
	return reportDao.getCompanyIncomeListCount(state);
}

public  List<CompanyIncome> getCompanyIncomeList(PageParameter state){
	  	
	  	return reportDao.getCompanyIncomeList(state);
	  }

public void deleteCompanyIncome(CompanyIncome state){
	
	 reportDao.deleteCompanyIncome(state);
}

public  List<CompanyIncome> getCompanyIncomeSmyList(PageParameter state){
	  
	 
		return reportDao.getCompanyIncomeSmyList(state);
	  	 
	  }

public  List<CompanyIncome> getCompanyIncomeSmyByDayList(PageParameter state){
	  
	 
	return reportDao.getCompanyIncomeSmyByDayList(state);
  	 
  }

public  List<CompanyIncome> getCompanyIncomeSmyByMonthList(PageParameter state){
	  
	 
	return reportDao.getCompanyIncomeSmyByMonthList(state);
  	 
  }

public  List<CompanyIncome> getCompanyIncomeMonthDetailList(PageParameter state){
	  
	 
	return reportDao.getCompanyIncomeMonthDetailList(state);
  	 
  }

public  List<CompanyIncome> getCompanyIncomeMonthSmyByManagerList(PageParameter state){
	  
	 
	return reportDao.getCompanyIncomeMonthSmyByManagerList(state);
  	 
  }




public void createZhichuOutcome(ZhichuOutcome state){
	
	 reportDao.createZhichuOutcome(state);
}


public void deleteZhichuOutcome(ZhichuOutcome state){
	
	 reportDao.deleteZhichuOutcome(state);
}

public void updateZhichuOutcome(ZhichuOutcome state){
	
	 reportDao.updateZhichuOutcome(state);
}



public  List<ZhichuOutcome> getZhichuOutcomeList(ZhichuOutcome state){
  	
  	return reportDao.getZhichuOutcomeList(state);

}


public void createGongziOutcome(GongziOutcome state){
	
	 reportDao.createGongziOutcome(state);
}


public void deleteGongziOutcome(GongziOutcome state){
	
	 reportDao.deleteGongziOutcome(state);
}

public void updateGongziOutcome(GongziOutcome state){
	
	 reportDao.updateGongziOutcome(state);
}



public  List<GongziOutcome> getGongziOutcomeList(GongziOutcome state){
  	
  	return reportDao.getGongziOutcomeList(state);

}


public void createFankuanOutcome(FankuanOutcome state){
	
	 reportDao.createFankuanOutcome(state);
}


public void deleteFankuanOutcome(FankuanOutcome state){
	
	 reportDao.deleteFankuanOutcome(state);
}

public void updateFankuanOutcome(FankuanOutcome state){
	
	 reportDao.updateFankuanOutcome(state);
}



public  List<FankuanOutcome> getFankuanOutcomeList(FankuanOutcome state){
  	
  	return reportDao.getFankuanOutcomeList(state);

}




public void createZhipaoOutcome(ZhipaoOutcome state){
	
	 reportDao.createZhipaoOutcome(state);
}


public void deleteZhipaoOutcome(ZhipaoOutcome state){
	
	 reportDao.deleteZhipaoOutcome(state);
}

public void updateZhipaoOutcome(ZhipaoOutcome state){
	
	 reportDao.updateZhipaoOutcome(state);
}



public  List<ZhipaoOutcome> getZhipaoOutcomeList(ZhipaoOutcome state){
  	
  	return reportDao.getZhipaoOutcomeList(state);

}




public void createPeikuanOutcome(PeikuanOutcome state){
	
	 reportDao.createPeikuanOutcome(state);
}


public void deletePeikuanOutcome(PeikuanOutcome state){
	
	 reportDao.deletePeikuanOutcome(state);
}

public void updatePeikuanOutcome(PeikuanOutcome state){
	
	 reportDao.updatePeikuanOutcome(state);
}



public  List<PeikuanOutcome> getPeikuanOutcomeList(PeikuanOutcome state){
  	
  	return reportDao.getPeikuanOutcomeList(state);

}

public  List<PeikuanOutcome> getPeikuanOutcomeSmyList(PeikuanOutcome state){
  	
  	return reportDao.getPeikuanOutcomeSmyList(state);

}



public void createIncome(Income state){
	
	 reportDao.createIncome(state);
}
  public Integer getIncomeListCount(Income state){
  	
  	return reportDao.getIncomeListCount(state);
  }
  
  public  List<Income> getIncomeList(Income state){
	  	
	  	return reportDao.getIncomeList(state);

}
  public  List<Income> getIncomeSmyList(Income state){
	  	
	  	return reportDao.getIncomeSmyList(state);

}
  
  public  List<Income> getIncomeYearSmyList(Income state){
	  	
	  	return reportDao.getIncomeYearSmyList(state);

}
  
  public  List<Income> getIncomeSmyByDayList(Income state){
	  	
	  	return reportDao.getIncomeSmyByDayList(state);

}

  
  public List<Income>  getIncomeSmyByMonthList(Income state){
	  
		return reportDao.getIncomeSmyByMonthList(state);
  }

  public void deleteIncome(Income state){
  	
  	 reportDao.deleteIncome(state);
  }
  

  public List<ZhipaoOutcome>  getZhipaoOutcomeSmyByMonthList(ZhipaoOutcome state){
	  
		return reportDao.getZhipaoOutcomeSmyByMonthList(state);
  }
  
  public List<ZhichuOutcome>  getZhichuOutcomeSmyByMonthList(ZhichuOutcome state){
	  
		return reportDao.getZhichuOutcomeSmyByMonthList(state);
}
  
  public List<GongziOutcome>  getGongziOutcomeSmyByMonthList(GongziOutcome state){
	  
		return reportDao.getGongziOutcomeSmyByMonthList(state);
}

  public List<FankuanOutcome>  getFankuanOutcomeSmyByMonthList(FankuanOutcome state){
	  
		return reportDao.getFankuanOutcomeSmyByMonthList(state);
}
  
  public List<PeikuanOutcome>  getPeikuanOutcomeSmyByMonthList(PeikuanOutcome state){
	  
		return reportDao.getPeikuanOutcomeSmyByMonthList(state);
}
  
  
}
