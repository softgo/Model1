package com.bruce.gogo.system.service;

import javax.servlet.http.HttpServletRequest;

public interface IUserPassword {
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	public boolean check(HttpServletRequest request);
	
	/**
	 * 保存密码
	 */
	public boolean save(HttpServletRequest request);
}
