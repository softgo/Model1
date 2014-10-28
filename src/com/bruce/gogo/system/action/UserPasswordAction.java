package com.bruce.gogo.system.action;

import com.bruce.gogo.action.BaseAction;
import com.bruce.gogo.system.service.IUserPassword;
/**
 * 
 * @author zhongsou
 *
 */
public class UserPasswordAction  extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4612065760094955107L;
	private IUserPassword userPasswordService;
	private String error;
	/**
	 * 进入密码修改
	 * @return
	 */
	public String enter(){
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		request.setAttribute("id", request.getParameter("id"));
		return "UserPassword";
	}
	
	/**
	 * 检查原密码
	 * @return
	 */
	public String check(){
		boolean result = userPasswordService.check(request);
		
		if(result)
		{
			request.setAttribute("msg", "0==修改成功！");
		} else {
			request.setAttribute("msg", "1==原密码不正确！");
		}
		return "Ajaxjsp";
		
		
	}
	/**
	 * 保存
	 * @return
	 */
    public String save(){
    	String type = request.getParameter("type");
    	boolean re=userPasswordService.save(request);
		if(re){
		   request.setAttribute("msg", "添加成功");
	      }
	    else
	       {
		  request.setAttribute("msg","添加失败");
	     }	
	   
		 if(type.equals("usertype")){
			   return "UserSave";
		   }else{
			return "Save";
		   }
    }
	public IUserPassword getUserPasswordService() {
		return userPasswordService;
	}

	public void setUserPasswordService(IUserPassword userPasswordService) {
		this.userPasswordService = userPasswordService;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


}
