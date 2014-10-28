
package com.bruce.gogo.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.bruce.gogo.Constants;
import com.bruce.gogo.common.service.ICommonDao;
import com.bruce.gogo.crypt.CryptFactory;
import com.bruce.gogo.crypt.ICrypt;
import com.bruce.gogo.system.model.Modleinfo;
import com.bruce.gogo.system.model.Stmkinfo;
import com.bruce.gogo.system.model.Userinfo;
import com.bruce.gogo.system.model.Userroleinfo;
import com.bruce.gogo.system.service.ILogin;
import com.bruce.gogo.utils.SessionInfo;


/**
 * 
 *
 */
public class LoginImpl implements ILogin {
    private ICommonDao daoA;
    static  Logger logger = Logger.getLogger(LoginImpl.class.getName());
    
	public boolean login(HttpServletRequest request, HttpServletResponse response) {
		String username = StringEscapeUtils.escapeSql(request.getParameter("username"));
		String password = StringEscapeUtils.escapeSql(request.getParameter("password"));
		request.setAttribute("username", username);
		//验证码
//		if(!CaptchaService.isOk(request, response)){
//			request.setAttribute("error", "1");//error = 1,验证码错误
//			return false;
//		}
		//验证数据是否为null
		if(username == null || password == null){
			return false;
		}
		ICrypt crypt = null;
		try {
			crypt = CryptFactory.getCryptor("UnixCrypt");
		} catch (Exception e) {
			logger.info("加密出问题了."+e.getMessage());
		}		
		//取用户信息
		StringBuffer hql = new StringBuffer();
        hql.append("from Userinfo user where user.loginname =:username");
		Map map = new LinkedHashMap();
		map.put("username",username);
		Userinfo user = (Userinfo)daoA.getHqlObject(hql.toString(), map);
		//验证用户名
		if(user == null){
			request.setAttribute("error", "2");//error = 2,用户名错误
			return false;
		}
		if(!crypt.matches(user.getPassword(), password)){
			request.setAttribute("error", "3");//error = 3,密码错误
			return false;
		}
		HttpSession session = request.getSession();
		if(session != null){
			session.setAttribute("username", user.getUsername());
		}
		
		//用户信息放入sessionInfo
		SessionInfo sessionInfo=new SessionInfo();
		sessionInfo.setUserid(user.getId());
		sessionInfo.setUserName(user.getUsername());
		sessionInfo.setLoginName(username);
        //取当前用户拥有的权限
		hql = new StringBuffer();
		hql.append("from Userroleinfo Userroleinfo ");
		hql.append("where Userroleinfo.userid = ").append(user.getId());
		List<Userroleinfo> userroleinfos = daoA.getHqlList(hql.toString());
		StringBuffer menuids = new StringBuffer();
		if(userroleinfos != null){
			Map<Integer,Stmkinfo> permissionidsMap = new HashMap<Integer,Stmkinfo>();
			List roleList = new ArrayList();
			List menuList = new ArrayList();
			for(int i = 0;i < userroleinfos.size();i++){
				Modleinfo roleinfo = (Modleinfo)daoA.getObject(Modleinfo.class, Integer.valueOf(((Userroleinfo)userroleinfos.get(i)).getRoleid()));
				
				if(roleinfo != null){
					if(menuids.toString().equals(""))
					{
						menuids.append(roleinfo.getPermissionid());
					}else{
						menuids.append("," + roleinfo.getPermissionid());
					}
					String[] permissionids = roleinfo.getPermissionid().split(",");
					for(int j = 0;j < permissionids.length;j++){
						Stmkinfo permissioninfo = (Stmkinfo)daoA.getObject(Stmkinfo.class, new Integer(permissionids[j]));
						//将权限放入Map中
						permissionidsMap.put(Integer.valueOf(permissioninfo.getId()), permissioninfo);
					}
					
					//将角色放入sessionInfo中。
					roleList.add(roleinfo.getId());					
					
				}	
			}
			if(!menuids.toString().equals("")){
				menuList = daoA.getHqlList("from Stmkinfo permissioninfo where permissioninfo.id in ("+ menuids +") order by permissioninfo.ncom");				
			}
			if(menuList == null){
				menuList = new ArrayList();
			}
			//将Map放入sessionInfo
			sessionInfo.setPermission(permissionidsMap);
			sessionInfo.setRole(roleList);			
			sessionInfo.setMenu(menuList);
		}
        //将sessionInfo放入request,在Action层取出放入session
        request.setAttribute(Constants.sessioninfo,sessionInfo);
		return true;
	}
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if(session != null){
			session.invalidate();
		}
	}
	public ICommonDao getDaoA() {
		return daoA;
	}
	public void setDaoA(ICommonDao daoA) {
		this.daoA = daoA;
	}
}
