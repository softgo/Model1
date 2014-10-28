
package com.bruce.gogo.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.Assert;

import com.bruce.gogo.Constants;
import com.bruce.gogo.utils.SessionInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 *判断是否登录
 *
 */
public class MenuInterceptor extends MethodFilterInterceptor{
	public String doIntercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext actionContext = actionInvocation.getInvocationContext();
//	     取得当前的session
	    Map session=(Map)actionContext.get(ServletActionContext.SESSION);
	    //取得sessionInfo
	    SessionInfo sessionInfo = (SessionInfo)session.get(Constants.sessioninfo);
		//取得request
	    HttpServletRequest request = ServletActionContext.getRequest();
	    Integer parentId = null;
	    Integer childId = null;
		try{
			Assert.hasText(request.getParameter("parentId"));
			parentId = new Integer(request.getParameter("parentId"));
			sessionInfo.setParentid(parentId);
			Assert.hasText(request.getParameter("childId"));
			childId = new Integer(request.getParameter("childId"));
			sessionInfo.setChildid(childId);
		}
		catch(Exception e){

		}
		return actionInvocation.invoke();
	}

}
