package com.bruce.gogo.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.bruce.gogo.Constants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 *判断是否登录
 *
 */
public class LoginInterceptor extends MethodFilterInterceptor{
	public String doIntercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext actionContext = actionInvocation.getInvocationContext();
		//取得当前的session
	    Map session=(Map)actionContext.get(ServletActionContext.SESSION);
		if(session == null || session.get(Constants.sessioninfo) == null){
			return "Logout";
		}
		return actionInvocation.invoke();
	}

}
