package com.bruce.gogo.system.action;

import java.io.IOException;
import java.util.List;

import com.bruce.gogo.Constants;
import com.bruce.gogo.action.BaseAction;
import com.bruce.gogo.system.service.IPermission;
import com.bruce.gogo.system.service.IRolePermission;


/**
 * 	角色权限管理
 * @version v1.0 
 * 
 */
public class RolePermissionAction extends BaseAction{
    private IRolePermission rolePermissionService;
    private IPermission permissionService;
    private Constants con;
  
    /**
	 * 查询列表	
	 */
    
    public String list(){    	
    	
    	rolePermissionService.getRolesList(request); 
    
    	return "List";
    }
    /**	
	 * 新增、选择所属系统显示树型目录结构
	 * @return
	 */
    public void importselect()
	{
 
    	String buff =this.getRolePermissionService().addTreeGeneration(request);
    	try {
			response.getWriter().write(buff);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}  
    /**	
	 * 编辑显示树型目录结构
	 * @return
	 */
    public void importeditselect()
	{
    	
    	String buff =this.getRolePermissionService().treeGeneration(request);
    	try {
			response.getWriter().write(buff);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}    
    /**
	 * 增加记录	
	 */
    
    public String add(){
   
    	return "Add";
    }
    /**
	 * 修改记录	
     */
    
    public String edit(){     	
    	String id=request.getParameter("id");
    	String buff=this.getRolePermissionService().treeGeneration(request);
    	request.setAttribute("id", id);     	
	
    	return "Edit";
    }
    /**
	 * 保存
	 * @return
     * @throws Exception 
	 */
	public String save() throws Exception{
	       
			boolean re=rolePermissionService.saveOrUpdate(request);
			if(re){
			request.setAttribute("msg", "添加成功");
		         }
		       else
		        {
			request.setAttribute("msg","添加失败");
		        }
		return "Save";
	}
//	检查编号
	public String checkname()
	{
		List l=rolePermissionService.getByName(request);		
		if(l.size()>0)
		{
			request.setAttribute("msg", "1==角色名称已经存在！");
		} else {
			request.setAttribute("msg", "0==角色名称不存在！");
		}
		return "Ajaxjsp";
	}
	/**
	 * 删除权限
	 * @return
	 */
	public String delete(){						
		boolean b=false;		
		b=rolePermissionService.remove(getRequest());
			if(b){
				request.setAttribute("msg", "删除成功");
			}
			else
			{
				request.setAttribute("msg", "删除失败");	
			}
			
			
			return "Ajaxjsp";
	}
	public IRolePermission getRolePermissionService() {
		return rolePermissionService;
	}
	public void setRolePermissionService(IRolePermission rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
	}
	public Constants getCon() {
		return con;
	}
	public void setCon(Constants con) {
		this.con = con;
	}
	public IPermission getPermissionService() {
		return permissionService;
	}
	public void setPermissionService(IPermission permissionService) {
		this.permissionService = permissionService;
	}

     
}
