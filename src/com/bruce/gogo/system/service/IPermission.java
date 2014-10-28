package com.bruce.gogo.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bruce.gogo.system.model.Stmkinfo;

/**
 * 系统权限管理
 * @version v1.0  
 * 
 */
public interface IPermission {
	/**
	 * 查询权限列表	
	 * @param Parentid 大模块名：	 
	 * @return List
	 */
	public List getPermissionList(HttpServletRequest request); 
	/**
	   * @desc 根据id得一条记录
	   * @param id
	   * @return Permissioninfo
	 * @throws Exception 
	   */
	public Stmkinfo getById(Integer id) ;
	
	public List getPermissionBypid(String pid,List alllist,List resultlist,int gf);
	
	/**
     * @desc 根据权限名查询
     * @param name
     * @return List
     */
	public List getByName(HttpServletRequest request);
	/**
	 * 查询权限
	 * @return
	 */
	public List findPermissions();
	/**
     *跳转到添加权限页
	 * @param request
     */
	public Stmkinfo add(HttpServletRequest request) ;
	/**
     *跳转到修改权限页
	 * @param request
     */
	public Stmkinfo edit(HttpServletRequest request) ;
	/**
	 * 格式化Long型参数数据,如果为"",转换为null
	 * @param request
	 * @param name
	 * @return
	 */
	public Long formatLongData(HttpServletRequest request,String name);
	/**
	 * 递归权限下拉列表
	 * @return
	 */
	public String UserMenu(Integer Parentid, List permissionList,String jb);
	/**
	 * 得模块列表
	 * @return
	 */
	public List getModuleList();
	/**
     * @desc 增加修改一条记录
     * @param permissioninfo
     * @return true||false
     */
	public boolean saveOrUpdate(HttpServletRequest request);
	/**
	 * 根据id列表查询所有权限列表
	 * @param ids
	 * @return
	 */
	public List findPermissionnIDs(final List ids);
	 /**
	 * 删除记录
	 * @param request
	 * @return
	 */
	public boolean remove(HttpServletRequest request);
	
	/**
	 * 判断用户是否有权限
	 * @param request
	 * @param keyName 权限关键字
	 * @return
	 */
	public boolean isHasPermission(HttpServletRequest request,String keyName);
}
