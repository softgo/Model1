package com.bruce.gogo.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bruce.gogo.action.BaseAction;
import com.bruce.gogo.system.model.Modleinfo;
import com.bruce.gogo.system.model.Userinfo;
import com.bruce.gogo.system.model.Userroleinfo;
import com.bruce.gogo.system.service.IRolePermission;
import com.bruce.gogo.system.service.IUsers;
import com.bruce.gogo.utils.OptionsLong;
/**
 * 用户管理
 * @version v1.0 
 * 
 */
public class UsersAction extends BaseAction{
    private IUsers usersService;
    private IRolePermission rolePermissionService;
    private Userinfo userinfo;
    private List<OptionsLong> roleValues = new ArrayList<OptionsLong>();
    
    
    public List<OptionsLong> getRoleValues() {
		return roleValues;
	}

	public void setRoleValues(List<OptionsLong> roleValues) {
		this.roleValues = roleValues;
	}

	public Userinfo getUserinfo() {
		return userinfo;
	}

	public IRolePermission getRolePermissionService() {
		return rolePermissionService;
	}

	public void setRolePermissionService(IRolePermission rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}
	private void setRoleValuesInit() {
		List roleList = this.getRolePermissionService().getRolesList(request);
		for (int i = 0; i < roleList.size(); i++) {
			Modleinfo role = (Modleinfo) roleList.get(i);
			roleValues.add(new OptionsLong(role.getId(), role.getModelname()));
		}
	}
	/**
	 * 查询用户列表	
	 */
	@SuppressWarnings("unchecked")
	public String list(){		
		List list=usersService.getUsersList(request);	
		
		Map rolemap = new HashMap();
		for(int i=0;i<list.size();i++)
		{
			Userinfo userinfo = (Userinfo)list.get(i);
			Userroleinfo roleinfo = this.getUsersService().getRoleNameByUserId(Integer.valueOf(userinfo.getId()));
			if(roleinfo!=null){
				rolemap.put(i,this.getRolePermissionService().getById(Integer.parseInt(roleinfo.getRoleid())).getModelname());
			}
		}
		request.setAttribute("rolemap", rolemap);				
	    return "List";
	}
	/**
	 * 普通用户列表页
	 */
	public String userlist(){
	
	List list=usersService.getUList(request);	
		
		Map rolemap = new HashMap();
		for(int i=0;i<list.size();i++)
		{
			Userinfo userinfo = (Userinfo)list.get(i);
			Userroleinfo roleinfo = this.getUsersService().getRoleNameByUserId(Integer.valueOf(userinfo.getId()));
			if(roleinfo!=null){
			rolemap.put(i,this.getRolePermissionService().getById(Integer.parseInt(roleinfo.getRoleid())).getModelname());
			}
		}
		request.setAttribute("rolemap", rolemap);				
	    return "UserList";
	}
	
	
	 /**
	 * 增加用户,	
	 */
	public String add(){	

		return "Add";		
	}	
	/**
	 * 修改用户,	
	 */
	public String edit(){
		String type = request.getParameter("type");
		request.setAttribute("type", type);
        this.setUserinfo(usersService.edit(request));
		
		return "Edit";	
	}
	/**
	 * 分配角色
	 */
	public String roleset(){
		 this.setRoleValuesInit();	
		 this.setUserinfo(usersService.edit(request));
		 Integer id=this.formatIntegerData(request, "id");   
		 Userroleinfo uro=(Userroleinfo)this.getUsersService().getRoleNameByUserId(Integer.valueOf(id));	
		 if(uro!=null){
			 String[] item = uro.getRoleid().split(",");
				List list = new ArrayList();
				for(int i=0; i<item.length; i++)
				{
					list.add(item[i]);
				}
				request.setAttribute("list", list);
		 }
		return "RoleSet";
	}
	/**
	 * 保存权限
	 */
	public String saverole(){
		String usid = request.getParameter("id");
		
		Userroleinfo userrole=this.getUsersService().getRoleNameByUserId(Integer.valueOf(usid));
		if(userrole==null){
			userrole=new Userroleinfo();
		}
		String roleid="";
		String[] rc=request.getParameterValues("roleid");		
		if(rc.length!=0){
		for(int x=0;x<rc.length;x++)
		{
			if(x==rc.length-1){
				roleid=roleid+rc[x];
			}else
			{
				roleid=roleid+rc[x]+',';
			}
		}
		}	
		userrole.setRoleid(roleid);
		userrole.setUserid(Integer.parseInt(usid));
		this.getUsersService().saveOrUpdateUserRoleInfo(userrole);
		
		return "Save";
	}
	/**
	 * 保存权限	
	 */
	public String save(){
		boolean re=usersService.saveOrUpdate(request);
		String type = request.getParameter("type");
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
	
	/**
	 * 删除权限
	 * @return
	 */
	public String delete(){						
		boolean b=false;		
		b=usersService.remove(request);
			if(b){
				request.setAttribute("msg", "删除成功");
			}
			else
			{
				request.setAttribute("msg", "删除失败");	
			}
			
			
			return "Ajaxjsp";
	}
	
    //检查是否有重复
	public String checkname()
	{
		List l=usersService.getByName(request);		
		if(l.size()>0)
		{
			request.setAttribute("msg", "1==此用户名已经存在！");
		} else {
			request.setAttribute("msg", "0==此用户名不存在！");
		}
		return "Ajaxjsp";
	}
	
	public Long formatLongData(HttpServletRequest request,String name){
		return request.getParameter(name) == null || request.getParameter(name).equals("")?null:new Long(request.getParameter(name));
	}
	public Integer formatIntegerData(HttpServletRequest request,String name){
		return request.getParameter(name) == null || request.getParameter(name).equals("")?null:new Integer(request.getParameter(name));
	}
	public IUsers getUsersService() {
		return usersService;
	}

	public void setUsersService(IUsers usersService) {
		this.usersService = usersService;
	}
}
