package com.yundastat.service;

import java.util.List;

import com.yundastat.dao.UserDao;
import com.yundastat.model.User;
import com.yundastat.model.UserInfo;



public class UserService {
	private UserDao userDao;

	public  List<User> queryUserByLoginid(String loginid) {
		// TODO Auto-generated method stub

		 List<User> user = userDao.getUserIdByLoginid(loginid);
		return user;
	}
	public User queryUserByDate(int loginid) {
		// TODO Auto-generated method stub

		User user = userDao.getUserIdByDate(loginid);
		return user;
	}
	
	
	public int updateUser(User user){
		return userDao.updateUser(user);
		
	}
 
	 public int registerUser(User user)
	 {
		return userDao.registerUser(user);
		 
	 }
	 
	 
	 

	 public int deleteUser(User user)
	 {
		return userDao.deleteUser(user);
		 
	 }
	 
	public User queryUserByUserId(int userid) {
		// TODO Auto-generated method stub
		
		return	userDao.getUserByUserid(userid);
	
	}
	
	
	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	

}
