package com.bruce.gogo.system.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bruce.gogo.Constants;
import com.bruce.gogo.common.service.ICommonDao;
import com.bruce.gogo.system.model.Stmkinfo;
import com.bruce.gogo.system.service.IPermission;
import com.bruce.gogo.utils.OptionsInt;
import com.bruce.gogo.utils.SessionInfo;

/**
 * 系统权限管理
 * @version v1.0 
 * 
 */

public class PermissionImpl implements IPermission{
	private ICommonDao daoA;
	private List ptypeList ;
	protected final Log log = LogFactory.getLog(getClass());
	private static final String LOADS_IN_IDS = "from Stmkinfo where id in (:ids)";
	
	public List getPermissionList(HttpServletRequest request) {
		
		String keyword=request.getParameter("keyword");				
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("from Stmkinfo Permission where 1=1 ");		
		//输入关键字搜索
		if(!isEmpty(keyword))
		{
			hql.append(" and Permission.pename like '%").append(keyword).append("%'");
		}		
		hql.append(" order by Permission.id desc");			
		daoA.checkListSQL(request, hql.toString());	
	    list =daoA.paginator(request, hql.toString());		
		List parentlist=daoA.getHqlList("from Stmkinfo Permission where 1=1  order by Permission.id desc");
		Map parentidmap = new HashMap();
		for(int k=0;k<parentlist.size();k++){
			Stmkinfo permission=(Stmkinfo)parentlist.get(k);			
			parentidmap.put(permission.getId(), permission.getPename());			
			}
 
		request.setAttribute("parentidmap", parentidmap);		
		request.setAttribute("keyword", keyword);			
		request.setAttribute("list", list);
		return list;
	}
	
	public Stmkinfo add(HttpServletRequest request){		
		Stmkinfo pinfo=null;
		Integer id=this.formatIntegerData(request, "id");       
		if(id != null){
			pinfo = (Stmkinfo)daoA.getObject(Stmkinfo.class, id);
		}		

		return pinfo;
	}	
	public Stmkinfo edit(HttpServletRequest request) {		
		Stmkinfo pinfo=null;
		Integer id=this.formatIntegerData(request, "id");       
		if(id != null){
			pinfo = (Stmkinfo)daoA.getObject(Stmkinfo.class, id);
		}
		request.setAttribute("Permissioninfo", pinfo);		
		List permiList = this.getModuleList();					
		request.setAttribute("ptypeList", ptypeList);
		request.setAttribute("permiList", permiList);		
		return pinfo;
	}	
	/**
	 * 查询权限
	 * @return
	 */
	public List findPermissions(){
		   List list;		  			 
		   list = daoA.getHqlList(" from Stmkinfo where 1=1 order by id desc ");		
		   return list;
	}
	
	public Stmkinfo getById(Integer id){			
	     return	(Stmkinfo) this.daoA.getObject(Stmkinfo.class, id);			
	}

	
	public List getByName(HttpServletRequest request) {		
		String pename=request.getParameter("pename");
		Integer id=Integer.parseInt(request.getParameter("id"));				
		String hql="from Stmkinfo as p where p.pename='"+pename+"' and p.id<>'"+id+ "'";		
		
		return	this.daoA.getHqlList(hql);
		
		
	}
	
	public boolean remove(HttpServletRequest request) {	
		String Key = request.getParameter("id");
        if(Key != null)
        {
	        	String hql = "select info from Stmkinfo info where info.id="+Key;
	        	List list = daoA.getHqlList(hql);
	        	if(list !=null && list.size()>0){
	        		for(int i=0;i<list.size();i++){
	        			Stmkinfo delinfo = (Stmkinfo)list.get(i);
	        			if(delinfo !=null ){
	        				daoA.removeObject(delinfo);
	        			}
	        		}	        		
	        	}
	        	daoA.removeObject(daoA.getObject(Stmkinfo.class,new Integer(Key)));	
	        	return true;
        }
		return false;
	}

	public boolean saveOrUpdate(HttpServletRequest request) {		
		boolean result = false;
		Integer id=this.formatIntegerData(request, "id");
		Stmkinfo permissioninfo=null;
		if(id == null){
			permissioninfo=new Stmkinfo();
			String Keyname = new Long(System.currentTimeMillis()).toString();
			permissioninfo.setKname(Keyname);
		}
		else 
		{
		permissioninfo=(Stmkinfo)daoA.getObject(Stmkinfo.class, id);	
		}
		permissioninfo.setPerlink(request.getParameter("perlink"));
		permissioninfo.setPename(request.getParameter("pename"));		
		System.out.println(permissioninfo.getPename());
		permissioninfo.setContent(request.getParameter("content"));
		permissioninfo.setPtype(request.getParameter("ptype"));
		permissioninfo.setNcom(new Integer(request.getParameter("ncom")));
		permissioninfo.setIsfolder( request.getParameter("isfolder") == null ?"N":"Y");
		permissioninfo.setParentid(new Integer(request.getParameter("parentid")));				
		daoA.saveOrUpdate(permissioninfo);
		daoA.getMySession().flush();
		result=true;	
		
		
		
		return result;		
	}

	public List findPermissionnIDs(final List ids) {		
		return daoA.getMyHibernateTemplate().executeFind(new HibernateCallback() {
		      public Object doInHibernate(Session s) throws HibernateException, SQLException {
		        Query query = s.createQuery(LOADS_IN_IDS);
		        query.setParameterList("ids", ids);
		        List list = query.list();
		        return list;
		      }
		    });
	}

	public List getModuleList(){		
	   List list;		  			 
	   list = daoA.getHqlList("select permission from Stmkinfo as permission ");		
	   return list;
	}
	public String UserMenu(Integer Parentid,List permissionList,String jb) 
	{
		String result="";
		try {
				for (int i = 0; i < permissionList.size(); i++) 
				{
					
					Stmkinfo pinfo = new Stmkinfo();
					pinfo = (Stmkinfo) permissionList.get(i);
					if (pinfo.getParentid().equals(Parentid)) 
					{
						//二级菜单（功能连接）
						String temp = jb;
						if(!Parentid.equals(new Integer(-1)))
						{
							temp += "|--";
						}
						result += "<option value='" + pinfo.getId() + "' onchange='check();'>" + temp
								+ pinfo.getPename() + "</option>";
						
						result += UserMenu(Integer.valueOf(pinfo.getId()),permissionList, temp);
					}
				}
		} catch (Exception e) {
			System.out.print(e);
			result = "";
		}
		
		return result;
	}	
	public List getPermissionBypid(String pid,List alllist,List resultlist,int gf)
	{
		List arrylist = this.getPidAll(pid,alllist);
		int f = arrylist.size();
		for(int i=0;i<f;i++)
		{
			int s = gf+1;
			Stmkinfo per = (Stmkinfo)arrylist.get(i);
			resultlist.add(new OptionsInt(per.getId().intValue(),getTagstr(s).concat(per.getPename())));
			this.getPermissionBypid(per.getId().toString(), alllist, resultlist, s);
		}
		return resultlist;
	}
	/**
	 * 返回相同pid的数据
	 * @param pid
	 * @param list
	 * @return
	 */
	public List getPidAll(String pid,List list)
	{
		List result = new ArrayList();
		for(int i=0;i<list.size();i++)
		{
			Stmkinfo per = (Stmkinfo)list.get(i);
			if(pid.equals(per.getParentid().toString()))
			{
				result.add(per);
			}
		}
		return result;
	}
	/**
	 * 返回下拉列表要显示的标记 如: |--
	 * @param num 栏目的级别
	 * @return
	 */
	private String getTagstr(int num)
	{
		String tag = "";
		if(num>1)
		{	
			tag="";
			for(int i=0;i<num-1;i++)
			{
				tag=tag+"|--";
			}
		}
		return tag;
	}
	
	private boolean isEmpty(Object s){
		if(s == null || s.toString().equals("")){
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
	public List getPtypeList() {
		return ptypeList;
	}


	public void setPtypeList(List ptypeList) {
		this.ptypeList = ptypeList;
	}


	public ICommonDao getDaoA() {
		return daoA;
	}


	public void setDaoA(ICommonDao daoA) {
		this.daoA = daoA;
	}

	

	/**
	 * 判断用户是否有权限
	 * @param request
	 * @param keyName 权限关键字
	 * @return
	 */
	public boolean isHasPermission(HttpServletRequest request,String keyName)
	{
		boolean result = false;
		Stmkinfo permission = new Stmkinfo();
		String sql = "from Stmkinfo permissioninfo where permissioninfo.kname = '" + keyName + "'";
		List list = daoA.getHqlList(sql);
		if(list.size() > 0)
		{
			permission = (Stmkinfo)list.get(0);
		}
		SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constants.sessioninfo);
		Map<Long,Stmkinfo> permissionMap = (Map<Long,Stmkinfo>)sessionInfo.getPermission();
		if(permission.getId()!=null && permissionMap.containsKey(permission.getId()))
		{
			result = true;
		}
		return result;
	}


}
