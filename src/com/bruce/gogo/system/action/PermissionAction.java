package com.bruce.gogo.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bruce.gogo.Constants;
import com.bruce.gogo.action.BaseAction;
import com.bruce.gogo.system.model.Stmkinfo;
import com.bruce.gogo.system.service.IPermission;
import com.bruce.gogo.utils.OptionsInt;


/**
 * 系统权限管理
 * @version v1.0 
 * 
 */
public class PermissionAction extends BaseAction{
	private IPermission permissionService;	
	private Stmkinfo Permissioninfo;
	private List<OptionsInt> perValues = new ArrayList<OptionsInt>();	
	private List permissionList;
    private String parentid;
    private String ptype;    
    private Constants con;
	
    
    public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	/**
	 * 初始化权限下拉列表
	 *
	 */
	private void setPerValuesInit()
	{
		List alllist = this.getPermissionService().getModuleList();	
		List list = this.getPermissionService().getPermissionBypid("-1", alllist, new ArrayList(), 0);
		int f = list.size();
		for(int i=0;i<f;i++)
		{
			perValues.add((OptionsInt)list.get(i));
		}
	}
	
	
	 /**
	 * 查询权限列表	
	 */
	public String list(){					
		permissionService.getPermissionList(request);		
		
	    return "List";
	}
	 /**
	 * 增加权限,	
	 */
	public String add(){
			
		this.setPerValuesInit();
		permissionService.add(request);	
	
		return "Add";		
	}	
	/**
	 * 修改权限,	
	 */
	public String edit(){			
		permissionService.edit(request);
		String id=request.getParameter("id");	
		Stmkinfo pinfo=this.getPermissionService().getById(Integer.valueOf(id));
		this.setParentid(pinfo.getParentid().toString());		
		this.setPerValuesInit();
		
		return "Edit";		
	}	

      //检查是否有重复
	public String checkname()
	{
		List l=permissionService.getByName(request);		
		if(l.size()>0)
		{
			request.setAttribute("msg", "1==权限名称已经存在！");
		} else {
			request.setAttribute("msg", "0==权限名称不存在！");
		}
		return "Ajaxjsp";
	}
    //模块下拉列表
	public void importselect()
	{

		List list=permissionService.getModuleList();
		StringBuffer strOption = new StringBuffer();		
		strOption.append("");
		if(list.size()>0)
		{
			strOption.append("<select id='parentid' name='parentid' class='input03'>");
			strOption.append("<option value='-1'>");
			strOption.append("－－请选择所属父ID－－");
			strOption.append("</option>");
			strOption.append(this.getPermissionService().UserMenu(new Integer(-1),list, "").toString());
		    strOption.append("</select>");
		}
		try {
			response.getWriter().write(strOption.toString());
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}
	
	/**
	 * 保存权限	
	 */
	public String save(){
		boolean re=permissionService.saveOrUpdate(request);
		if(re){
		request.setAttribute("msg", "添加成功");
	}
	else
	{
		request.setAttribute("msg","添加失败");
	}	
	return "Save";
	}
	   
	/**
	 * 删除权限
	 * @return
	 */
	public String delete(){						
		boolean b=false;		
		b=permissionService.remove(getRequest());
			if(b){
				request.setAttribute("msg", "删除成功");
			}
			else
			{
				request.setAttribute("msg", "删除失败");	
			}
			
			
			return "Ajaxjsp";
	}
	public IPermission getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(IPermission permissionService) {
		this.permissionService = permissionService;
	}
	public Stmkinfo getPermissioninfo() {
		return Permissioninfo;
	}
	public void setPermissioninfo(Stmkinfo permissioninfo) {
		Permissioninfo = permissioninfo;
	}
	public List getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List permissionList) {
		this.permissionList = permissionList;
	}

	public Constants getCon() {
		return con;
	}
	public void setCon(Constants con) {
		this.con = con;
	}
	public List<OptionsInt> getPerValues() {
		return perValues;
	}
	public void setPerValues(List<OptionsInt> perValues) {
		this.perValues = perValues;
	}
	
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	
	
	

	

}
