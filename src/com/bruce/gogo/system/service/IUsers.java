package com.bruce.gogo.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bruce.gogo.system.model.Userinfo;
import com.bruce.gogo.system.model.Userroleinfo;
/**
 * 用户管理
 * @version v1.0  
 * 
 */
public interface IUsers {
	/**
	 * 查询用户列表		  
	 * @return List
	 */
	public List getUsersList(HttpServletRequest request); 
	/**
	 * 普通用户页  
	 * @return List
	 */
	public List getUList(HttpServletRequest request);
	  /**
	   * @desc 根据id得一条记录
	   * @param id	
	   * @throws Exception 
	   */
	public Userinfo getById(Integer id) ;
	
	/**
     * @desc 根据登陆名查询
     * @param loginname
     * @return List
     */
	public List getByName(HttpServletRequest request);
	
	/**
     * @desc 增加修改一条记录
     * @param Userinfo
     * @return true||false
     */
	public boolean saveOrUpdate(HttpServletRequest request);
	/**
	 * 编辑一条记录
	 * @param request
	 * @return
	 */
	public Userinfo edit(HttpServletRequest request);
	 /**
	 * 删除记录
	 * @param request
	 * @return
	 */
	
	public boolean remove(HttpServletRequest request);	
	/**
	 * 根据用户id得角色Id
	 */
	public Userroleinfo getRoleNameByUserId(Integer userid);
	 /**
     * 保存用户---角色对照
     *  userroleinfo
     */
    public boolean saveOrUpdateUserRoleInfo(Userroleinfo userroleinfo);
}
