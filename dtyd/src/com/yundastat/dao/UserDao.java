/**
 * 
 */
package com.yundastat.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


import com.ibatis.sqlmap.client.SqlMapClient;
import com.yundastat.model.User;
import com.yundastat.model.UserInfo;


/**
 * @author hp
 *
 */
public class UserDao  {
	private SqlMapClient sqlMapClient;
	/* (non-Javadoc)
	 * @see com.wuchang.dao.UserDao#getUserByUsername(java.lang.String)
	 */
	
	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}
	
	/***
	 *
	 * */
	public List<User> getUserIdByLoginid(String loginid) {
		// TODO Auto-generated method stub
	
		 List<User>  user=null;
		 HashMap  map =new HashMap();
		 map.put("loginid", loginid);
		try {
			  user =  sqlMapClient.queryForList("user.getUserByLoginid",map);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return user;
	
		
	}
	public User getUserIdByDate(int loginid) {
		// TODO Auto-generated method stub
	
		User user=null;
		try {
			  user = (User)sqlMapClient.queryForObject("user.getUserByDate",loginid);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return user;
	
		
	}
	

	public User getUserByUserid(int userid) {
		// TODO Auto-generated method stub
		User user;
		try {
			user=(User) sqlMapClient.queryForObject("user.getUserByUserid",userid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	
	
	public int updateUser(User user){
		int returnValue=0;
		try {
			returnValue=sqlMapClient.update("user.updateUser",user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return returnValue;
		
	}
	
	  //注册用户 LRH
	public int registerUser(User user){
	int returnValue=0;
	try {
				
		  sqlMapClient.insert("user.registerUser",user);
 
		  
	} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
				return -1;
	}
		return returnValue;
			
  }
	public int deleteUser(User user){
		int returnValue=0;
		try {
					
			  sqlMapClient.delete("user.deleteUser",user);
	 
			  
		} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
					return -1;
		}
			return returnValue;
				
	  }
		
		
	
	

}
