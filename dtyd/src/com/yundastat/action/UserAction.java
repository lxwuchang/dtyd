/**
 * 
 */
package com.yundastat.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.yundastat.model.User;
import com.yundastat.service.UserServiceImpl;
import com.yundastat.util.CreateMD5;
import com.yundastat.util.DateUtil;
import com.yundastat.util.StringUtil;

/**
 * @author hp
 *
 */
public class UserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6297819871093744770L;
	private static Logger logger = Logger.getLogger (UserAction.class.getName () );
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork.Action#execute()
	 */
	protected Map errorMessage = new HashMap();
    private User user=new User();
    private String registerCode;
    
    
    public String getRegisterCode() {
		return registerCode;
	}



	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}



	private UserServiceImpl userServiceImpl;
  
 	public User getUser() {
		return user;
	}


    
	public void setUser(User user) {
		this.user = user;
	}

  
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		//User u=userServiceImpl.getUserByDate(201208);
		logger.info("User is logining!");
		
		 String passMD5=CreateMD5.createMD5(user.getPassword());	 
		 user.setPassword(passMD5);
		 
			try {
				
			   List<User> users=userServiceImpl.getUserByLoginid(user.getLoginid());
			 
			   //获取有效的截止日期 和是否过期。
			   //如果已经过期，则跳转到注册码的页面。
			   //如果未过期，则判定今天的日子和有效截止的日期（有效-今天）：1.=0,则将状态置为y，2.>0,则表示有效，继续，3,<0,将之置为y
			   if(users==null)
				return INPUT;
			   else {
				  
				   if(users.size()>0){
				   
					User u=users.get(0);
					
				   if(u.getEffdays()<=0){
					   this.addErrorMessage("hello", "注册码已经过期，请联系管理员！");
					   return REG_HOME; 
					   
				   }else{
					
				   Date lastlogintime=u.getLastlogintime();
				   Date deadline=DateUtil.parse(u.getDeadline()+" 12:00:00", DateUtil.DATE_TIME_FORMAT);
//			     	  
				   
				   Date nowD=new Date();
				   
				   int diff=DateUtil.compareDay(nowD,lastlogintime);
				   //如果现在小于最后登录时间，则设置所有用户失效
				   if(diff<=0){
				 	   u=users.get(0);
					   u.setEffdays(-1);
					   userServiceImpl.updateUser(u);
				 	   this.addErrorMessage("hello", "注册码已经过期，请联系管理员！");
					   return  REG_HOME;
					   
					   
				   }else{//如果现在大于最后登录时间，则比较大于几天，如果大于1天以上，则减去相差的日期。
					   deadline=DateUtil.parse(u.getDeadline()+" 12:00:00", DateUtil.DATE_TIME_FORMAT);
//					     
					   int dif=DateUtil.compareDays(nowD,lastlogintime);
					   
					   int ddif =DateUtil.compareDays(deadline,nowD);
					   //dif>=1
					   
					   if(ddif>=0){
					   
					   if(dif>=1){
				 		   
						 	   u=users.get(0);
							   u.setLastlogintime(nowD);
							   u.setEffdays(u.getEffdays()-dif);
							   userServiceImpl.updateUser(u);
					 	    
					   }
					   
					   //diff<1
					   if(dif<1){
						   	   u=users.get(0);
							   u.setLastlogintime(nowD);
							   userServiceImpl.updateUser(u);
							  
					   }
					   
					   if(userServiceImpl.doLoginIn(user,ctx)){
							
							return STAT_HOME;
						}
					   }else{
						   this.addErrorMessage("hello", "注册码已经过期，请联系管理员！");
						   return  REG_HOME;
						   
						   
					   }
						
					   
				   }
				   
				 }
				   
				  }
				   
			 	   
			   }
			
				
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("User is error");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return INPUT;
	}
	
	
   public String  register() throws IOException, ParseException{
	
	   //0=c;
	   //1=t;
	   //2=l;
	   //3=p;
	   //4=e;
	   //5=b;
	   //6=k;
	   //7=r;
	   //8=s;
	   //9=z;
	   //每次产生一个16位的字符串，里面有数字和字母，
	   //其中分别取12,23,34,14
	   //20 12 12 31
	   
	   //lcxxxtlxxxtlpxxt
	   ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		String reg = request.getParameter("reg");
		reg=StringUtil.trim(reg);
		
		
		
		if(reg.length()==16){
			
			//
			//根据这个16位的字符串，返回一个日期的格式:2012-07-09
			
	//		String d=""+reg.charAt(0)+reg.charAt(1)+reg.charAt(5)+reg.charAt(6)+"-"+reg.charAt(10)+reg.charAt(11)+"-"+reg.charAt(12)+reg.charAt(15)+"";
			//如果未过期，则需要判断，这个日期是否比截止日期久，如果是则更新，如果不是则不动。
			//如果已过期，则需判断，这个日期是否比截止日期久，如果是则更新isdeadline为n,deadline更新为最新的。否则，更新失败。
			
			String d=getDateFromWord(reg);
		   List<User>	users=userServiceImpl.getUserByLoginid(null);
		   
		   if(users!=null){
			   String flag=users.get(0).getIsdeadline();
			   String deadline=users.get(0).getDeadline();
			   deadline=deadline+" 12:00:00";
			   String dtmp=d;
			   d=d+" 12:00:00";
			  Date deadlined=DateUtil.parse(deadline, DateUtil.DATE_TIME_FORMAT);
			  Date newdeadlined=DateUtil.parse(d, DateUtil.DATE_TIME_FORMAT);
			  
			  int diff=DateUtil.compareDay(newdeadlined, deadlined); 
			   
			   if(flag.equals("y")){
				   
				   
				   if(diff>=0){
					   
					   for(int i=0;i<users.size();i++){
						   User u=users.get(i);
						   u.setIsdeadline("n");
						   u.setDeadline(dtmp);
						   userServiceImpl.updateUser(u);	   
					    
					   }
					   
					   this.addErrorMessage("hello", "注册码更新成功，有效期至："+dtmp);  
					   
				   }else{
					   this.addErrorMessage("hello", "注册码更新失败");
					   
					   return REG_HOME;//更新失败
				   }
				   
				   
				   
			   }else{
				   
				   
				   if(diff>=0){
					   
					   for(int i=0;i<users.size();i++){
						   User u=users.get(i);
						   u.setDeadline(dtmp);
						   userServiceImpl.updateUser(u);	   
					    
					   }
					   this.addErrorMessage("hello", "注册码更新成功，有效期至："+dtmp);
					   
					   
				   }else{
					   this.addErrorMessage("hello", "注册码更新失败！");
					   
					   return REG_HOME;//更新失败
				   }
			   }
			   
		   }
		   
			
			
		}
		
		
		return REG_HOME;
		
		
	}
	public String showCreateregistercode() throws IOException{
		
		return CREATE_REG;
		
		
	}
	
   public String createregistercode() throws IOException{
	   
	   ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(WebWorkStatics.HTTP_REQUEST);
		String reg = request.getParameter("reg");
		reg=StringUtil.trim(reg);
		//2012-12-20
			
		registerCode=getRegisterCode(reg);
		
		//lctptlptxxxxxxxx
		
		return CREATE_REG;
		
		
	}
   
	//user login
	public String doSubmit() throws IOException{
		
		return INPUT;
		
		
	}
	
	private String getRegisterCode(String reg){
           if(reg==null||reg.equals("")){
			
			return null;
		}
		//20121220
		String d=""+reg.charAt(0)+reg.charAt(1)+reg.charAt(2)+reg.charAt(3)+reg.charAt(5)+reg.charAt(6)+reg.charAt(8)+reg.charAt(9);
	
		String dat= getCode(d);
		
		
		return dat;
		
	}
	
	private String getCode(String dateword){
		//lctptlptxxxxxxxx
		
		char str[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		String d="";
		

		for(int i=0;i<3;i++){
			
			Random r=new Random();
			int t=r.nextInt(26);
			
			d=d+str[t];
			
		}
		for(int i=0;i<dateword.length();i++){
			
			char k=dateword.charAt(i);
			
			switch (k){
			
			case '0': d=d+'c';break;
			case '1': d=d+'t';break;
			case '2': d=d+'l';break;
			case '3': d=d+'p';break;
			case '4': d=d+'e';break;
			case '5': d=d+'b';break;
			case '6': d=d+'k';break;
			case '7': d=d+'r';break;
			case '8': d=d+'s';break;
			case '9': d=d+'z';break;
			
			}
		
			
		}
		
		
		for(int i=0;i<5;i++){
			
			Random r=new Random();
			int t=r.nextInt(26);
			
			d=d+str[t];
			
		}
		
		return d;
	}
	private String getDateFromWord(String word){
		
		if(word==null||word.equals("")){
			
			return null;
		}
		
		
		String d=""+word.charAt(3)+word.charAt(4)+word.charAt(5)+word.charAt(6)+"-"+word.charAt(7)+word.charAt(8)+"-"+word.charAt(9)+word.charAt(10)+"";
		
		
		String dat= getDate(d);
		
		
		return dat;
		
		
	}
	
	
	private String getDate(String dateword){
		   //0=c;
		   //1=t;
		   //2=l;
		   //3=p;
		   //4=e;
		   //5=b;
		   //6=k;
		   //7=r;
		   //8=s;
		   //9=z;lctlcslz
		//lcxxxtlxxxtlpxxt ueqlctlcslzbppbw
		String d="";
		for(int i=0;i<dateword.length();i++){
			
			char k=dateword.charAt(i);
			
			switch (k){
			
			case 'c': d=d+'0';break;
			case 't': d=d+'1';break;
			case 'l': d=d+'2';break;
			case 'p': d=d+'3';break;
			case 'e': d=d+'4';break;
			case 'b': d=d+'5';break;
			case 'k': d=d+'6';break;
			case 'r': d=d+'7';break;
			case 's': d=d+'8';break;
			case 'z': d=d+'9';break;
			case '-': d=d+'-';break;
			
			
			}
			
			
			
		}
		
		return d;
		
		
		
		
	}
	
	/**
	 * 
	 
	 **/
	
	public String userLogout(){
	
 		ActionContext ctx = ActionContext.getContext();
 		userServiceImpl.doLogout(user.getUserid(), ctx);
		return INPUT;
	}


	@Override
	public Map getErrorMessage() {
		return errorMessage;
	}
	
	@Override
	protected void addErrorMessage(String anErrorInputId, String anErrorMessage) {
		if (errorMessage == null)
			errorMessage = new HashMap();
		errorMessage.put(anErrorInputId,anErrorMessage);
	}
	
	@Override
	protected boolean hasErrorMessage(){
		return (errorMessage != null) ? true:false;
	}





	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}



	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}



}
