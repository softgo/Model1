
package com.bruce.gogo.system.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ILogin {
	/**
	 * 登录
	 * @param request
	 */
	public boolean login(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 注销
	 * @param request
	 */
	public void logout(HttpServletRequest request);
}
