package com.bruce.gogo.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bruce.gogo.Constants;
import com.bruce.gogo.common.service.ICommonDao;
import com.bruce.gogo.crypt.CryptFactory;
import com.bruce.gogo.crypt.ICrypt;
import com.bruce.gogo.system.model.Userinfo;
import com.bruce.gogo.system.model.Userroleinfo;
import com.bruce.gogo.system.service.IUsers;
import com.bruce.gogo.utils.SessionInfo;

public class UsersImpl implements IUsers {
	private ICommonDao daoA;
	protected final Log log = LogFactory.getLog(getClass());
	/**
	 * 查询用户列表		  
	 * @return List
	 */
	public List getUsersList(HttpServletRequest request){
		String username=StringEscapeUtils.escapeSql(request.getParameter("username"));   
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("from Userinfo info where 1=1 ");		
		//输入关键字搜索
		if(!isEmpty(username))
		{
			hql.append(" and info.username like '%").append(username).append("%'");
		}		
					
		daoA.checkListSQL(request, hql.toString());	
	    list =daoA.paginator(request, hql.toString());	
	    request.setAttribute("list", list);
	    request.setAttribute("username", username);
		return list;
	}
	/**
	 * 普通用户页  
	 * @return List
	 */
	public List getUList(HttpServletRequest request){
		SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constants.sessioninfo);
		String userid =sessionInfo.getUserid().toString();
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("from Userinfo info where 1=1 ");		
		//输入关键字搜索
		if(!isEmpty(userid))
		{
			hql.append(" and info.id ='").append(userid).append("'");
		}		
					
		daoA.checkListSQL(request, hql.toString());	
	    list =daoA.paginator(request, hql.toString());	
	    request.setAttribute("list", list);
	   
		return list;
	}
	/**
	   * @desc 根据id得一条记录
	   * @param id	
	   * @throws Exception 
	   */
	public Userinfo getById(Integer id) {
		  return	(Userinfo) this.daoA.getObject(Userinfo.class, id);	
	}
	
	/**
     * @desc 增加修改一条记录
     * @param Userinfo
     * @return true||false
     */
	public boolean saveOrUpdate(HttpServletRequest request){
		boolean result = false;
		Integer id=this.formatIntegerData(request, "id");
		Userinfo info  = null;
		if(id == null){
			info = new Userinfo();
			String password = request.getParameter("password");
			ICrypt crypt = null;
			try {
				crypt = CryptFactory.getCryptor("UnixCrypt");
			} catch (Exception e) {
			
					e.printStackTrace();
				
			}
			
			info.setPassword(crypt.crypt(password));
			
		}else 
		{
			info=(Userinfo)daoA.getObject(Userinfo.class, id);	
		}
		info.setLoginname(request.getParameter("loginname"));
		info.setUsername(request.getParameter("username"));
		info.setNotes(request.getParameter("notes"));
	
		daoA.saveOrUpdate(info);
		result = true;
		return result;
	}
	/**
	 * 编辑一条记录
	 * @param request
	 * @return
	 */
	public Userinfo edit(HttpServletRequest request) {		
		Userinfo info=null;
		Integer id=this.formatIntegerData(request, "id");       
		if(id != null){
			info = (Userinfo)daoA.getObject(Userinfo.class, id);
		}
		request.setAttribute("Users", info);		
		
		return info;
	}
	 /**
	 * 删除记录
	 * @param request
	 * @return
	 */
	
	public boolean remove(HttpServletRequest request){
		boolean rc;	
		String id = request.getParameter("id");		
        if(id != null)
            {           
	        	daoA.removeObject(daoA.getObject(Userinfo.class,new Integer(id)));	     	
	        	
        	rc=true;
    		return rc;
    		
        }
		return false;
	}
	

	/**
     * @desc 根据登陆名查询
     * @param loginname
     * @return List
     */
	public List getByName(HttpServletRequest request){
		String loginname=request.getParameter("loginname");
		Integer id=Integer.parseInt(request.getParameter("id"));				
		String hql="from Userinfo as info where info.loginname='"+loginname+"' and info.id<>'"+id+ "'";		
		
		return	this.daoA.getHqlList(hql);
		
	}
	public Userroleinfo getRoleNameByUserId(Integer userid){
		String sql="select info from Userroleinfo info where info.userid='"+userid+"'";
		List list=daoA.getHqlList(sql);
		Userroleinfo info = null;
		if(list.size()>0){
		 info = (Userroleinfo)list.get(0);
		}
		return info;
		
	}
	
	  /**
     * 保存用户---角色对照
     *  userroleinfo
     */
    public boolean saveOrUpdateUserRoleInfo(Userroleinfo userroleinfo) {		
		daoA.saveOrUpdate(userroleinfo);
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
	/**
	 * 格式化Long型参数数据,如果为"",转换为null
	 * @param request
	 * @param name
	 * @return
	 */
	public Integer formatIntegerData(HttpServletRequest request,String name){
		return request.getParameter(name) == null || request.getParameter(name).equals("")?null:new Integer(request.getParameter(name));
	}
	private boolean isEmpty(Object s){
		if(s == null || s.toString().equals("")){
			return true;
		}
		return false;
	}
	public ICommonDao getDaoA() {
		return daoA;
	}

	public void setDaoA(ICommonDao daoA) {
		this.daoA = daoA;
	}
	
}
