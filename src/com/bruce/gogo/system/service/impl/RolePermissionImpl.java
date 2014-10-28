package com.bruce.gogo.system.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bruce.gogo.Constants;
import com.bruce.gogo.common.service.ICommonDao;
import com.bruce.gogo.system.model.Modleinfo;
import com.bruce.gogo.system.model.Stmkinfo;
import com.bruce.gogo.system.service.IRolePermission;
import com.bruce.gogo.utils.SessionInfo;


/**
 * 	角色权限管理
 * @version v1.0  
 * 
 */

public class RolePermissionImpl implements IRolePermission{
	private ICommonDao daoA;
	
	protected final Log log = LogFactory.getLog(getClass());
    
    private static final String LOADS_IN_IDS = "from Modleinfo where id in (:ids)";
     /**	
	 * 查旬列表
	 * @return
	 */
    public List getRolesList(HttpServletRequest request){   
		
    	String modelname=request.getParameter("modelname");          	
    	StringBuffer sql = new StringBuffer();
		List list = new ArrayList();
		sql.append("from Modleinfo roleinfo where 1=1");
		if(!isEmpty(modelname))
		{
			sql.append(" and roleinfo.modelname like '%").append(modelname).append("%'");
		}		
		sql.append(" order by roleinfo.id desc");
		daoA.checkListSQL(request, sql.toString());
		list = daoA.paginator(request, sql.toString());
    	request.setAttribute("list", list);
		request.setAttribute("modelname", modelname);
		return list;
	}
    
    public String getIdsByRole(HttpServletRequest request){
    	SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constants.sessioninfo);    	
    	List li=sessionInfo.getMenu();
    	String ids = "";
    	for(int k=0;k<li.size();k++){
    		Stmkinfo per=(Stmkinfo)li.get(k);
    		if(ids.equals(""))
			{	
    			ids = per.getId().toString();
			}
    		else
			{
				ids = ids+","+per.getId().toString();
			}
    		if(ids.equals(""))
    		{
    			ids = "0";
    		}
    	}    	
    	return ids;
    }
    /**	
	 * 新增、选择所属系统显示树型目录结构
	 * @return
	 */
    public String addTreeGeneration(HttpServletRequest request){   
    	SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constants.sessioninfo);      
    	List rolist=sessionInfo.getRole();
    	Integer adminroleid=new Integer(Constants.adminrole);
    	Integer roleflag=new Integer(Constants.roleflag);
    	StringBuffer buff = new StringBuffer();
    	buff.append("");
    	List parentlist=this.getPermissionBySysid(request);    	
    	if(parentlist.size()==0){
    		buff.append("<div class=\"menutitle\" style='display:none;' id=\"me\">");		
	    	//buff.append("<span class=\"rgtcheck\"><input type=\"checkbox\" name=\"chose_\" id=\"chose_\" onclick=\"javascript:choice('')\"/> 全选</span>");
	    	buff.append("<p><a href=\"javascript:showMenu('')\">");
	    	buff.append("<input type=\"checkbox\" name=\"chose\" value='' id=\"chose_\" onclick=\"javascript:choice('')\"");	    	
	    	buff.append("/>");
	    	buff.append("</a></p>");
	    	buff.append("</div>");	
    	}
    	else
    	{	
    	 for(int i = 0;i < parentlist.size();i++){
			 Stmkinfo perinfo = (Stmkinfo)parentlist.get(i);
		    	if(perinfo.getParentid().toString().equals("-1")){
		    	buff.append("<div class=\"menutitle\" id=\"me\">");		
		    	//buff.append("<span class=\"rgtcheck\"><input type=\"checkbox\" name=\"chose_"+perinfo.getId()+"\" id=\"chose_"+perinfo.getId()+"\" onclick=\"javascript:choice('"+perinfo.getId()+"')\"/> 全选</span>");
		    	buff.append("<p><a href=\"javascript:showMenu("+perinfo.getId()+")\">");
		    	buff.append("<input type=\"checkbox\" name=\"chose\" value='"+perinfo.getId() +"' id=\"chose_"+perinfo.getId() +"\"");		    	
		    	buff.append("onclick=\"javascript:choice('"+perinfo.getId()+"')\"/>");
		    	buff.append(perinfo.getPename());
		    	buff.append("</a></p>");
		    	buff.append("</div>");		    	
		    	}
		    	buff.append("<s:hidden id=\"id\" name=\"id\"></s:hidden>");
		    	buff.append("<table class=\"menucont\" id=\"menucont"+perinfo.getId()+"\">");
		    	List childrenlist=this.getAllPermissionBypid(perinfo.getId().toString(),request);
		    	for(int j=0;j<childrenlist.size();j++){
		    		Stmkinfo chilperinfo =(Stmkinfo)childrenlist.get(j);
		    		if(perinfo.getId().equals(chilperinfo.getParentid())){
		    			for(int u=0;u<rolist.size();u++){		
		    				if(rolist.get(u).equals(adminroleid)){
		    					buff.append("<tr>");
		    		    		buff.append("<td><input type=\"checkbox\" name=\"chose\" value='"+chilperinfo.getId() +"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"\"  onclick=\"javascript:dis('"+perinfo.getId()+"_"+chilperinfo.getId()+"')\"");		    		
		    		    		//buff.append("<td><input type=\"checkbox\" name=\"chose\" value='"+chilperinfo.getId() +"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"\"  onclick=\"javascript:dis('"+perinfo.getId()+"_"+chilperinfo.getId()+"')\"");		    		
		    		    		buff.append("/>");
		    		    		buff.append(chilperinfo.getPename()+"</td>");
		    		    		buff.append("</tr>");
		    				}else{
		    					 if(!chilperinfo.getId().equals(roleflag)){	
		    				    		buff.append("<tr>");
		    				    		buff.append("<td><input type=\"checkbox\" name=\"chose\" value='"+chilperinfo.getId() +"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"\"  onclick=\"javascript:dis('"+perinfo.getId()+"_"+chilperinfo.getId()+"')\"");		    		
		    				    		buff.append("/>");
		    				    		buff.append(chilperinfo.getPename()+"</td>");
		    				    		buff.append("</tr>");		    		
		    				    			}
		    				}
		    			}
		    		
		    		}
		    		List operator=this.getAllPermissionBypid(chilperinfo.getId().toString(),request);
		    		for(int k=0;k<operator.size();k++){
		    			Stmkinfo operatorinfo =(Stmkinfo)operator.get(k);
		    			if(chilperinfo.getId().equals(operatorinfo.getParentid())){
		    			     buff.append("<tr>");
		    			     buff.append("<td class=\"menufst\"><input type=\"checkbox\" name=\"chose\" value='"+operatorinfo.getId()+"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"_"+operatorinfo.getId()+"\"");
		    			    
		    			     buff.append("/>");
		    			     buff.append(operatorinfo.getPename()+"</td>");
		    			     buff.append("</tr>");		    				
		    			}
		    			List rolelist=this.getAllPermissionBypid(operatorinfo.getId().toString(),request);
		    			for(int r=0;r<rolelist.size();r++){
		    				Stmkinfo roleinfo =(Stmkinfo)rolelist.get(r);
		    				if(operatorinfo.getId().equals(roleinfo.getParentid())){
		    					buff.append("<tr>");
		    					buff.append("<td class=\"menufstse\"><input type=\"checkbox\" name=\"chose\" value='"+roleinfo.getId()+"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"_"+operatorinfo.getId()+"_"+roleinfo.getId()+"\"");
		    					
		    					buff.append("/>");
		    					buff.append(roleinfo.getPename()+"</td>");
		    					buff.append("</tr>");
		    					
		    				}
		    				
		    			}
		    		}
		    		
		    	}
		    	buff.append("</table>");
		    	
    	 }
    	}
    	return buff.toString();
    }
    
    /**	
	 * 编辑显示树型目录结构
	 * @return
	 */
    public String treeGeneration(HttpServletRequest request){  
    	SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constants.sessioninfo);      
    	List rolist=sessionInfo.getRole();
    	Integer adminroleid=new Integer(Constants.adminrole);
    	Integer roleflag=new Integer(Constants.roleflag);
    	Modleinfo roinfo=null;    	
    	Integer id=this.formatIntegerData(request, "id");       
		if(id != null){
			roinfo = (Modleinfo)daoA.getObject(Modleinfo.class, id);			
		}		 	
		String[] rc = roinfo.getPermissionid().toString().split(",");
		request.setAttribute("roleinfo", roinfo);
    	StringBuffer buff = new StringBuffer();
    	buff.append("");
    	List parentlist=this.getPermissionBySysid(request);    	
    	 for(int i = 0;i < parentlist.size();i++){
			 Stmkinfo perinfo = (Stmkinfo)parentlist.get(i);
		    	if(perinfo.getParentid().toString().equals("-1")){		    		
		    	buff.append("<div class=\"menutitle\" id=\"me\">");		
		    	//buff.append("<span class=\"rgtcheck\"><input type=\"checkbox\" name=\"chose_"+perinfo.getId()+"\" id=\"chose_"+perinfo.getId()+"\" onclick=\"javascript:choice('"+perinfo.getId()+"')\"/> 全选</span>");
		    	buff.append("<p><a href=\"javascript:showMenu("+perinfo.getId()+")\">");
		    	buff.append("<input type=\"checkbox\" name=\"chose\" value='"+perinfo.getId() +"' id=\"chose_"+perinfo.getId() +"\"");
		    	buff.append("onclick=\"javascript:choice('"+perinfo.getId()+"')\"");
		    	for(int p=0;p<rc.length;p++){
		    		 if (perinfo.getId().toString().equals(rc[p])) {
		    			buff.append("checked"); 
		    		 }
				}
		    	
		    	buff.append("/>");
		    	buff.append(perinfo.getPename());
		    	buff.append("</a></p>");
		    	buff.append("</div>");		    	
		    	}
		    	buff.append("<s:hidden id=\"id\" name=\"id\"></s:hidden>");
		    	buff.append("<table class=\"menucont\" id=\"menucont"+perinfo.getId()+"\">");
		    	List childrenlist=this.getAllPermissionBypid(perinfo.getId().toString(),request);
		    	for(int j=0;j<childrenlist.size();j++){
		    		Stmkinfo chilperinfo =(Stmkinfo)childrenlist.get(j);
		    		if(perinfo.getId().equals(chilperinfo.getParentid())){
		    			for(int u=0;u<rolist.size();u++){		
		    				if(rolist.get(u).equals(adminroleid)){
		    		    		buff.append("<tr>");
		    		    		buff.append("<td><input type=\"checkbox\" name=\"chose\" value='"+chilperinfo.getId() +"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"\"  onclick=\"javascript:dis('"+perinfo.getId()+"_"+chilperinfo.getId()+"')\"");
		    		    		for(int v=0;v<rc.length;v++){
		    		    			if (chilperinfo.getId().toString().equals(rc[v])) {
		    		    				buff.append("checked"); 
		    		    			}
		    		    		}
		    		    		buff.append("/>");
		    		    		buff.append(chilperinfo.getPename()+"</td>");
		    		    		buff.append("</tr>");
		    		    		}else{
		    			 if(!chilperinfo.getId().equals(roleflag)){ 	    			 
		    				buff.append("<tr>");
		 		    		buff.append("<td><input type=\"checkbox\" name=\"chose\" value='"+chilperinfo.getId() +"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"\"  onclick=\"javascript:dis('"+perinfo.getId()+"_"+chilperinfo.getId()+"')\"");
		 		    		for(int v=0;v<rc.length;v++){
		 		    			if (chilperinfo.getId().toString().equals(rc[v])) {
		 		    				buff.append("checked"); 
		 		    			}
		 		    		}
		 		    		buff.append("/>");
		 		    		buff.append(chilperinfo.getPename()+"</td>");
		 		    		buff.append("</tr>");
		    			 }
		    		    		}
		    		 }
		    		}
		    		List operator=this.getAllPermissionBypid(chilperinfo.getId().toString(),request);
		    		for(int k=0;k<operator.size();k++){
		    			Stmkinfo operatorinfo =(Stmkinfo)operator.get(k);
		    			if(chilperinfo.getId().equals(operatorinfo.getParentid())){
		    			     buff.append("<tr>");
		    			     buff.append("<td class=\"menufst\"><input type=\"checkbox\" name=\"chose\" value='"+operatorinfo.getId()+"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"_"+operatorinfo.getId()+"\"");
		    			     for(int m=0;m<rc.length;m++){
		    			    	 if (operatorinfo.getId().toString().equals(rc[m])) {	
		    			    		 buff.append("checked");  
		    			    	 }
		    			     }
		    			     buff.append("/>");
		    			     buff.append(operatorinfo.getPename()+"</td>");
		    			     buff.append("</tr>");		    				
		    			}
		    			List rolelist=this.getAllPermissionBypid(operatorinfo.getId().toString(),request);
		    			for(int r=0;r<rolelist.size();r++){
		    				Stmkinfo roleinfo =(Stmkinfo)rolelist.get(r);
		    				if(operatorinfo.getId().equals(roleinfo.getParentid())){
		    					buff.append("<tr>");
		    					buff.append("<td class=\"menufstse\"><input type=\"checkbox\" name=\"chose\" value='"+roleinfo.getId()+"' id=\"chose_"+perinfo.getId()+"_"+chilperinfo.getId()+"_"+operatorinfo.getId()+"_"+roleinfo.getId()+"\"");
		    					for(int n=0;n<rc.length;n++){
		    						 if (roleinfo.getId().toString().equals(rc[n])) {
		    							 buff.append("checked"); 
		    						 }
		    					}
		    					buff.append("/>");
		    					buff.append(roleinfo.getPename()+"</td>");
		    					buff.append("</tr>");		    					
		    				}		    				
		    			}
		    		}		    		
		    	}
		    	buff.append("</table>");	    	
    	 }    	 
    	return buff.toString();
    }
    /**
	 * 根据系统得权限
	 * @param pid
	 * @return
	 */
    public List getPermissionBySysid(HttpServletRequest request){    
    	String permissionids = this.getIdsByRole(request);
    	SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constants.sessioninfo);  
    	Integer adminroleid=new Integer(Constants.adminrole);
    	String sql ="";
    	List rolelist=sessionInfo.getRole();
		for(int i=0;i<rolelist.size();i++){			
			
    		   if(rolelist.get(i).equals(adminroleid)){
        		   sql =" from Stmkinfo perinfo where  perinfo.parentid =-1 ";
        	    }else {
        		   sql ="from Stmkinfo perinfo where  perinfo.parentid =-1 and  perinfo.id in("+permissionids+")"; 
        	    }
		  }
    	List list=daoA.getHqlList(sql);
    	return list;
    }   
	/**
	 * 查询指定ID下的所有目录
	 * @param pid
	 * @return
	 */
	public List getAllPermissionBypid(String pid,HttpServletRequest request)
	{
		SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constants.sessioninfo);  	
		Integer adminroleid=new Integer(Constants.adminrole);
		String permissionids = this.getIdsByRole(request);
    	String sql ="";
    	List rolelist=sessionInfo.getRole();
//    	System.out.println("rolelist======================"+rolelist.size());
		for(int i=0;i<rolelist.size();i++){			
		
		       if(rolelist.get(i).equals(adminroleid)){
	    		   sql = "from Stmkinfo perinfo where perinfo.parentid="+pid;
	    	    }else{
			       sql = "from Stmkinfo perinfo where perinfo.parentid="+pid+" and perinfo.id in("+permissionids+")";
	    	    }
		 }
		 List list=daoA.getHqlList(sql);
		return list;
	}
	/**
	 * 保存
	 * @param pid
	 * @return
	 */	
	public boolean saveOrUpdate(HttpServletRequest request) throws Exception {
		SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constants.sessioninfo);
		Long adminroleid=new Long(Constants.adminrole);
		boolean result = false;
		Integer id=this.formatIntegerData(request, "id");	     
		Modleinfo roleinfo=null;
		if(id == null){
			roleinfo=new Modleinfo();
			String Keyname = new Long(System.currentTimeMillis()).toString();
			roleinfo.setKname(Keyname);			
			roleinfo.setModelname(request.getParameter("name"));
			roleinfo.setContent("");
			
			String permissionid="";
			String[] rc=request.getParameterValues("chose");		
			if(rc.length!=0){
			for(int x=0;x<rc.length;x++)
			{
				if(x==rc.length-1){
					permissionid=permissionid+rc[x];
				}else
				{
				permissionid=permissionid+rc[x]+',';
				}
			}
			}
			roleinfo.setPermissionid(permissionid);	
			List rolelist=sessionInfo.getRole();
			for(int i=0;i<rolelist.size();i++){				
					roleinfo.setRtype(Integer.parseInt("1"));		
			}				

		}
		else 
		{
			roleinfo=(Modleinfo)daoA.getObject(Modleinfo.class, id);	
			roleinfo.setModelname(request.getParameter("name"));
			roleinfo.setContent("");
		
			String permissionid="";
			String[] rc=request.getParameterValues("chose");		
			if(rc.length!=0){
			for(int x=0;x<rc.length;x++)
			{
				if(x==rc.length-1){
					permissionid=permissionid+rc[x];
				}else
				{
				permissionid=permissionid+rc[x]+',';
				}
			}
			}
			roleinfo.setPermissionid(permissionid);			
			
			List rolelist=sessionInfo.getRole();
			for(int i=0;i<rolelist.size();i++){			
				if(rolelist.get(i).equals(adminroleid)){
					roleinfo.setRtype(0);
				}
				else{
					roleinfo.setRtype(1);
				}
			}		
		
		}
			
	
		daoA.saveOrUpdate(roleinfo);		
		result=true;		
		return result;
		
	}
	/**
	 * 删除
	 * @param pid
	 * @return
	 */
	public boolean remove(HttpServletRequest request) {	
		boolean rc;	
		String id = request.getParameter("id");		
        if(id != null)
            {           
	        	daoA.removeObject(daoA.getObject(Modleinfo.class,new Integer(id)));	      	
	        	
        	rc=true;
    		return rc;
    		
        }
		return false;
	}
    public List findRolesInIDs(final List ids) {		
		return daoA.getMyHibernateTemplate().executeFind(new HibernateCallback() {
		      public Object doInHibernate(Session s) throws HibernateException, SQLException {
		        Query query = s.createQuery(LOADS_IN_IDS);
		        query.setParameterList("ids", ids);
		        List list = query.list();
		        return list;
		      }
		    });
	}


	public List getByName(HttpServletRequest request) {		  
		String name=request.getParameter("name");
		Integer id=Integer.parseInt(request.getParameter("id"));		
		String hql="from Modleinfo as p where p.rtype=1 and p.modelname='"+name+"'  and p.id<>"+id;				
		return	this.daoA.getHqlList(hql);		
		
	}
	public Modleinfo getById(int id) {		
		return (Modleinfo)daoA.getObject(Modleinfo.class, new Integer(id));
	}	
	public void remove(Modleinfo role) throws Exception {
		try{
			daoA.removeObject(role);
			}
			catch (Exception ex) {
			      log.error(ex);
			      throw new Exception(ex);
			    }
		
	}

	
	private boolean isEmpty(Object s){
		if(s == null || s.toString().equals("")){
			boolean result = false;
			return true;
		}
		return false;
	}
	/**
	 * 格式化Long型参数数据,如果为"",转换为null
	 * @param request
	 * @param name
	 * @return
	 */
	public Long formatLongData(HttpServletRequest request,String name){
		return request.getParameter(name) == null || request.getParameter(name).equals("")?null:new Long(request.getParameter(name));
	}

	public Integer formatIntegerData(HttpServletRequest request,String name){
		return request.getParameter(name) == null || request.getParameter(name).equals("")?null:new Integer(request.getParameter(name));
	}

	public ICommonDao getDaoA() {
		return daoA;
	}

	public void setDaoA(ICommonDao daoA) {
		this.daoA = daoA;
	}

}
