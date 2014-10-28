package com.bruce.gogo.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bruce.gogo.system.model.Modleinfo;

/**
 * 	角色权限管理
 * @version v1.0 
 * 
 */
public interface IRolePermission {
	/**
     * @desc 增加修改一条记录
     * @return true||false
     */
	public boolean saveOrUpdate(HttpServletRequest request) throws Exception;
	
	/**
	 * 格式化Long型参数数据,如果为"",转换为null
	 * @param request
	 * @param name
	 * @return
	 */
	public Long formatLongData(HttpServletRequest request,String name);
	/**
	   * @desc 根据id得一条记录
	   * @param id
	   * @return Roleinfo
	 * @throws Exception 
	   */
	public Modleinfo getById(int id);
	/**
	   * @desc 根据name得一条记录
	   * @param name
	   * @return Roleinfo
	 * @throws Exception 
	   */
	public List getByName(HttpServletRequest request);
	/**
	 * 查询角色列表	 
	 * @return List
	 */
	
	public List getRolesList(HttpServletRequest request);
	
	
	/**
	 * 根据id列表查询所有角色列表
	 * @param ids
	 * @return
	 */
	
	public List findRolesInIDs(final List ids);
	 /**
	 * 删除记录
	 * @param request
	 * @return
	 */
	
	public void remove(Modleinfo role) throws Exception;	
	
	 /**
	 * 删除记录
	 * @param request
	 * @return
	 */
	public boolean remove(HttpServletRequest request);
	 /**
	 * 新增显示树型目录
	 * @param request
	 * @return
	 */
	public String addTreeGeneration(HttpServletRequest request);
	 /**
	 * 编辑显示树型目录
	 * @param request
	 * @return
	 */
	public String treeGeneration(HttpServletRequest request);
}
