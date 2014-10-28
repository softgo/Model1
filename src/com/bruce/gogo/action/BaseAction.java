package com.bruce.gogo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.bruce.gogo.utils.PageBean;
import com.bruce.gogo.utils.PropertyBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * action的基础类.
 *  
 * @author admin
 *
 */
public class BaseAction extends ActionSupport implements 
                     ServletRequestAware,SessionAware,ServletResponseAware {
	
	private static final long serialVersionUID = 6333846759086947895L;

	/**
     * 日志记录，以防止session同步问题
     */
   private static Logger logger = Logger.getLogger(BaseAction.class.getName());
    
	private PageBean paginator;
	private PropertyBean propertyBean;
	public HttpServletRequest request;
	public HttpServletResponse response;
	public Map sessionmap;
	public final String result_AjaxJson = "ajaxjson";

    /**
     * 页面from.
     */
    protected String from;

    /**
     * 设置为"delete"时， "delete"请求参数
     */
    protected String delete;

    /**
     * 设置为"save"时， "save"请求参数
     */
    protected String save;

    /**
     * 附加messages信息到session
     * @param msg the message to put in the session
     */
    @SuppressWarnings("unchecked")
    protected void saveMessage(String msg) {
        List messages = (List) getRequest().getSession().getAttribute("messages");
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(msg);
        getRequest().getSession().setAttribute("messages", messages);
    }

    /**
     * 得到request
     * @return current request
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 得到response
     * @return current response
     */
    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    protected void setSession(String key,Object obj)
    {
	    ActionContext actionContext = ActionContext.getContext(); 
	    actionContext.getSession().put(key, obj);
    }
    
    protected Object getSession(String key)
    {
    	ActionContext actionContext=ActionContext.getContext();
    	return actionContext.getSession().get(key);
    }

    /**
     * 方便方法设置一个"from"参数说明上一个页面。
     * @param from 指标原始（from）页
     */
    public void setFrom(String from) {
        this.from = from;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public void setSave(String save) {
        this.save = save;
    }

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map session) {
		this.sessionmap = session;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public PageBean getPaginator() {
		return paginator;
	}

	public void setPaginator(PageBean paginator) {
		this.paginator = paginator;
	}
    
	public PropertyBean getPropertyBean() {
		return propertyBean;
	}

	public void setPropertyBean(PropertyBean propertyBean) {
		this.propertyBean = propertyBean;
	}
    
	public void getMsg(String msg)
	{
		try{
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write(msg);
		}catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	} 
}
