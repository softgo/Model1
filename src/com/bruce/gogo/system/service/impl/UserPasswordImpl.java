package com.bruce.gogo.system.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.bruce.gogo.common.service.ICommonDao;
import com.bruce.gogo.crypt.CryptFactory;
import com.bruce.gogo.crypt.ICrypt;
import com.bruce.gogo.system.model.Userinfo;
import com.bruce.gogo.system.service.IUserPassword;

public class UserPasswordImpl implements IUserPassword {
	private ICommonDao daoA;
	public boolean check(HttpServletRequest request) {
		String oldpassword = request.getParameter("oldpassword");
		Integer id=this.formatIntegerData(request, "id");   		
		Userinfo user = (Userinfo)daoA.getObject(Userinfo.class, id);
		ICrypt crypt = null;
		try {
			crypt = CryptFactory.getCryptor("UnixCrypt");
		} catch (Exception e) {
		
				e.printStackTrace();
			
		}	
		
		if(!crypt.matches(user.getPassword(), oldpassword)){
			return false;
		}else{
			return true;
		}
		
		
		
	}
	public boolean save(HttpServletRequest request){
		String newpassword = request.getParameter("newpassword");
		Integer id=this.formatIntegerData(request, "id");   
		Userinfo user = (Userinfo)daoA.getObject(Userinfo.class, id);
		ICrypt crypt = null;
		try {
			crypt = CryptFactory.getCryptor("UnixCrypt");
		} catch (Exception e) {
		
				e.printStackTrace();
			
		}	
		user.setPassword(crypt.crypt(newpassword));
		daoA.saveOrUpdate(user);
		return true;
	}
	/**
	 * 格式化Long型参数数据,如果为"",转换为null
	 * @param request
	 * @param name
	 * @return
	 */
	public Long formatLongData(HttpServletRequest request,String name){
		return request.getParameter(name) == null || request.getParameter(name).equals("")?null:new Long(request.getParameter(name));
	}
	public Integer formatIntegerData(HttpServletRequest request,String name){
		return request.getParameter(name) == null || request.getParameter(name).equals("")?null:new Integer(request.getParameter(name));
	}
	public ICommonDao getDaoA() {
		return daoA;
	}
	public void setDaoA(ICommonDao daoA) {
		this.daoA = daoA;
	}

}
