package com.bruce.gogo.utils;

import java.util.List;
import java.util.Map;

/**
 * 存储用户Session信息，可根据需求添加其他信息
 * 
 * @author 周立峰
 *
 */
public class SessionInfo implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3017789229910036799L;

	//用户ID
	private Integer userid;
	//登陆名
	private String loginName;
	//权限
	private Map permission;
	//角色
	private List role;
	//菜单
	private List menu;
	//用户类型
	private Map usertype;
	//当前所在的一级菜单id
	private Integer parentid;
	//当前所在的子菜单id
	private Integer childid;	
	//用户姓名
	private String userName;
	

	/** default constructor */
	public SessionInfo() {
	}

	/** full constructor */
	public SessionInfo(
			Integer userid,
			String userName,			
			String loginName,
			Integer childid,
			Integer parentid,
			Map permission,
			List role,
			List menu,
			Map usertype
			
			)
	{
		this.userid = userid;	
		this.userName = userName;
		this.loginName = loginName;
		this.parentid = parentid;
		this.childid = childid;
		this.permission = permission;
		this.role = role;
		this.menu = menu;
		this.usertype = usertype;
		
	}

	
	
	/**
	 * @return the permission
	 */
	public Map getPermission() {
		return permission;
	}

	public List getMenu() {
		return menu;
	}

	public void setMenu(List menu) {
		this.menu = menu;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(Map permission) {
		this.permission = permission;
	}

	/**
	 * @return the usertype
	 */
	public Map getUsertype() {
		return usertype;
	}

	/**
	 * @param usertype the usertype to set
	 */
	public void setUsertype(Map usertype) {
		this.usertype = usertype;
	}

	
	/**
	 * @return the userid
	 */
	


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List getRole() {
		return role;
	}

	public void setRole(List role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getChildid() {
		return childid;
	}

	public void setChildid(Integer childid) {
		this.childid = childid;
	}

	
}
