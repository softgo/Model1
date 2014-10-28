package com.bruce.gogo.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bruce.gogo.Constants;
import com.bruce.gogo.common.service.ICommonDao;
import com.bruce.gogo.system.model.Stmkinfo;
import com.bruce.gogo.system.service.IPermission;
import com.bruce.gogo.utils.SessionInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 *根据操作判断是否有操作权限
 *
 */
public class PermissionInterceptor extends MethodFilterInterceptor{
	
	private static Logger logger = Logger.getLogger(PermissionInterceptor.class.getName());
	private static final String PERMISSIONERROR_PAGE = "PermissionErrorPage";
	private static final String ERROR_PAGE = "Error";
	private IPermission permissionService;

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {

	ActionContext actionContext = actionInvocation.getInvocationContext();
	// 指定查询Permissioninfo表
	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Stmkinfo.class);
	// 取得servletContext
	ServletContext servletContext=(ServletContext)actionContext.get(ServletActionContext.SERVLET_CONTEXT);
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	ICommonDao daoA = (	ICommonDao)wac.getBean("daoA");
	HttpServletRequest request = ServletActionContext.getRequest();
	StringBuffer url = request.getRequestURL();
	String uri = request.getRequestURI().substring(1); 

	
	// 权限控制部分--开始
	// 系统权限和用户权限
	Map sysurlallpermission = new HashMap();
	Map permissionsmap = new HashMap();

	
	 //logger.debug("uri="+uri.toString());
    // 以下为获取当前用户权限
	// 取得当前的session
	Map session=(Map)actionContext.get(ServletActionContext.SESSION);
	SessionInfo sessionInfo = (SessionInfo)session.get(Constants.sessioninfo);
	if(sessionInfo != null)
	{
	  //取得当前用户所有权限
	  permissionsmap = sessionInfo.getPermission();
	}

	// memcached
   // MemCached cache = MemCached.getInstance();

   // if(cache.containsKey("permissionAllMap"))
    //{
      // sysurlallpermission =(Map)cache.get("permissionAllMap");
  //  }
   // else
   // {
   //	logger.debug("-------  从数据库取系统权限  ----------");
    	// 从数据库取系统权限--得到系统所有权限(URLKEY)
    	//cache服务器,没有数据,则去取数据库数据!
		List listAMPermission = permissionService.findPermissions();
		Map AMPermissionAllMap = new HashMap();
	
		for(int i=0;i<listAMPermission.size();i++)
		{
			Stmkinfo info = (Stmkinfo)listAMPermission.get(i);
			AMPermissionAllMap.put(info.getPerlink(), info.getId());
			//logger.debug("cache服务器---id="+i+"="+info.getPename()+"url="+info.getPerlink());
		}
		//保存cache
		//cache.set("permissionAllMap", AMPermissionAllMap);		
		sysurlallpermission = AMPermissionAllMap;

   // }
		
    // 判断uri是否属于权限范围
    if(sysurlallpermission.containsKey(uri))
    {
    	//logger.debug("--------------------是属于权限范围="+uri);
	    // 属于权限范围
	    // 判断当前用户是否有此操作权限
    	Long pid = (Long)sysurlallpermission.get(uri);

    	//logger.debug("--------------------是属于权限范围pid="+pid);
    	//没权限--跳转到没权限错误页面
	     if(!permissionsmap.containsKey(pid))
	     {
			 return PERMISSIONERROR_PAGE;
		 }
			 
			 
	 }

	//权限控制部分--结束
	return actionInvocation.invoke();

}
	
	public IPermission getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(IPermission permissionService) {
		this.permissionService = permissionService;
	}

}

