package com.bruce.gogo.common.service.impl;

import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bruce.gogo.common.service.ICommonDao;
import com.bruce.gogo.utils.PageBean;

/**
 * 接口实现类.
 * 
 * @author admin
 * 
 */
public class CommonDaoImpl extends HibernateDaoSupport implements ICommonDao {

	/**
	 * 获得hibernate对象.
	 * 
	 * @return
	 */
	public HibernateTemplate getMyHibernateTemplate() {
		return getHibernateTemplate();
	}

	/**
	 * 获得session.
	 * 
	 * @return
	 */
	public Session getMySession() {
		return getSession();
	}

	/**
	 * 得到对象根据类和标识符的普通方法 如果什么都没有被找到, 返回 null 如果出现错误, 返回 null
	 * 
	 * @param clazz
	 *            model 类
	 * @param id标识符(主关键字)
	 * @return 一个存在的对象
	 * @see org.springframework.orm.ObjectRetrievalFailureException a
	 */
	public Object getObject(Class clazz, Serializable sign) {
		Object object = null;
		try {
			object = getHibernateTemplate().get(clazz, sign);
		} catch (Exception e) {
			logger.info("获取对象出错,id = "+sign+e.getMessage());
			return object;
		}
		return object;
	}

	/**
	 * 保存对象.
	 * 
	 * @param obj
	 */
	public void save(Object obj){
		try {
			super.getHibernateTemplate().flush();
			super.getHibernateTemplate().clear();
			super.getHibernateTemplate().save(obj);
		} catch (HibernateException e) {
			logger.info("保存出错"+e.getMessage());
		}
	}

	/**
	 * 保存或者更改对象.
	 * 
	 * @param obj
	 */
	public void saveOrUpdate(Object obj) {
		try {
			super.getHibernateTemplate().flush();
			super.getHibernateTemplate().clear();
			super.getHibernateTemplate().saveOrUpdate(obj);
		} catch (HibernateException e) {
			logger.info("保存出错"+e.getMessage());
		}
	}

	/**
	 * 根据对象删除
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return
	 */
	public void removeObject(Object obj){
		try {
			getHibernateTemplate().delete(obj);
		} catch (Exception e) {
			logger.info("删除失败!"+e.getMessage());
		}
	}

	/**
	 * 根据id与类删除记录
	 * 
	 * @param clazz
	 * @param id
	 */
	public void removeObject(Class clazz, Serializable id){
		try {
			Object object = getObject(clazz, id);
			getHibernateTemplate().delete(object);
		} catch (Exception e) {
			logger.info("删除失败!id= "+id+e.getMessage());
		}
	}

	/**
	 * 多项删除
	 * 
	 * @param c
	 *            要删除的对象集合
	 * @return
	 */
	public void removeAll(ArrayList arry){
		try {
			Collection coll = (Collection)arry;
			getHibernateTemplate().deleteAll(coll);
		} catch (Exception e) {
			logger.info("删除所有失败!"+e.getMessage());
		}
	}
	
	 public int deleteAllById(Class clazz, String idArray[])
	    {
	        int deletedNum = 0;
	        for(int i = 0; i < idArray.length; i++)
	        {
	            try
	            {
	                Long id = new Long(idArray[i]);
	                removeObject(clazz, id);
	            }
	            catch(Exception e)
	            {
	            	logger.info("删除失败"+e.getMessage());
	            	return deletedNum;
	            }
	            deletedNum++;
	        }

	        return deletedNum;
	    }
	
	/**
	 * 用一条hql语句完成更新操作
	 * 
	 * @param hql
	 * @return
	 */
	public int executeUpdate(String hql){
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			int num = query.executeUpdate();
			return num;
		} catch (Exception e) {
			logger.info("更新操作失败!"+e.getMessage());
			return -1;
		}
	}

	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				int allCounts = ((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
				criteria.setProjection(null);//还原
				return allCounts;
			}
		}, true);
		return count.intValue();
	}


	public void clear(){
		try {
			getHibernateTemplate().clear();
		} catch (Exception e) {
			logger.info("清理失败!"+e.getMessage());
		}
	}

	/**
	 * 普通使用的方法得到一个特殊类型的所有对象 这是象查寻相同表里所有列
	 * 
	 * @param clazz
	 *            对象
	 * @return 在对象里的数据列表
	 */
	public List getAllObjects(Class entity){
		List list = new ArrayList();
	    try {
			list = this.getHibernateTemplate().loadAll(entity);
		} catch (Exception e) {
			logger.info("获取集合对象出错"+e.getMessage());
			return list;
		}
	   return list;
	}

	/**
	 * 获得多条数据的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 *            带参数的查询语句
	 * @param map
	 *            参数
	 * @return List 查询结果列表
	 */
	public List getHqlList(String queryString, Map map){
    	List list = null;
        try{
        	Session session = getSession();
            Query query = session.createQuery(queryString);
            String[] property = query.getNamedParameters();
    	    for(int i = 0; i < property.length; i++)
    	    {
    	    	String pro = property[i];
    	    	query.setParameter(pro, map.get(pro));
    	    }        
            list = query.list();
        }
        catch(Exception e)
        {
        	logger.info("获取list集合出错了..."+e.getMessage());
        	return null;
        }
        return list;
    
	}

	/**
	 * 获得单一记录的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 *            带参数的查询语句
	 * @param map
	 *            参数
	 * @return Object 查询结果对象
	 */
	public Object getHqlObject(String queryString, Map map){
    	Object obj = null;
    	try
    	{
            Session session = getSession();
            Query query = session.createQuery(queryString);
            String[] property = query.getNamedParameters();
    	    for(int i = 0; i < property.length; i++)
    	    {
    	    	String pro = property[i];
    	    	query.setParameter(pro, map.get(pro));
    	    }        
    	    obj = query.uniqueResult();
    	}
        catch(Exception e)
        {
        	logger.info("获得对象出错了："+e.getMessage());
        	return null;
        }
        return obj;
	}

	/**
	 * 返回Query接口
	 * 
	 * @param sql
	 * @return
	 */
	public Query getQuery(String sql){
		try {
			Session session = this.getSession();
			return session.createQuery(sql);
		} catch (Exception e) {
			logger.info("获得对象失败"+e.getMessage());
			return null;
		}
	}

	/**
	 * 返回SQLQuery接口
	 * 
	 * @param sql
	 * @return
	 */
	public SQLQuery createSqlQuery(String sql){
		try {
			Session session = this.getSession();
			return  session.createSQLQuery(sql);
		} catch (Exception e) {
			logger.info("创建对象失败"+e.getMessage());
			return null;
		}
	}

	/**
	 * 返回Criteria接口
	 * 
	 * @param classz
	 * @return
	 */
	public Criteria createCriteria(Class classz){
		try {
			Session session = this.getSession();
			return session.createCriteria(classz);
		} catch (Exception e) {
			logger.info("错误信息："+e.getMessage());
			return null;
		}
	}

	/**
	 * 获得多条数据的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 * @return
	 */
	public List getHqlList(String queryString){
		List list = null;
        try
        {
        	Session session = getSession();
            Query query = session.createQuery(queryString);  
            list = query.list();
        }
        catch(Exception e)
        {
        	logger.info("获得集合出错"+e.getMessage());
        	return null;
        }
       return list;
	}

	/**
	 * 命名查询
	 * 
	 * @param namedQuerStr
	 * @return
	 */
	public Query getNamedQuery(String namedQuerStr){
		try {
			Session session = this.getSession();		
			return session.getNamedQuery(namedQuerStr);
		} catch (Exception e) {
			logger.info("获得对象失败"+e.getMessage());
			return null;
		}
	}

	/**
	 * 获得多条数据的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 * @param max
	 *            返回记录数
	 * @return
	 */
	public List getHqlList(String queryString, int max){
		List list = null;
        try
        {
        	Session session = getSession();
            Query query = session.createQuery(queryString);  
            query.setMaxResults(max);
            list = query.list();
        }
        catch(Exception e)
        {
        	logger.info("获得对象失败"+e.getMessage());
        	return null;
        }
        return list;
	}

	/**
	 * 获得多条数据的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 * @param first
	 *            第一条记录
	 * @param max
	 *            返回记录数
	 * @return
	 */
	public List getHqlList(String queryString, int first, int max){
    	List list = null;
        try
        {
        	Session session = getSession();
            Query query = session.createQuery(queryString);  
            query.setFirstResult(first);
            query.setMaxResults(max);
            list = query.list();
        }
        catch(Exception e)
        {
        	logger.info("获取出错"+e.getMessage());
        	return null;
        }
        return list;
	}

	/**
     * 打开ajax的输出流
     * 
     * @param request
     * @param response
     * @return PrintWriter
     */
    public PrintWriter startResponseWriter(
    		HttpServletRequest request,
    		HttpServletResponse response)
    {
    	PrintWriter out = null;
		try
		{
	    	request.setCharacterEncoding("UTF-8");
	        response.setContentType("text/xml; charset=UTF-8");
	        response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.println("<response>");
		}
		catch(Exception e)
		{
			logger.info("打印出错了"+e.getMessage());
		}
		
		return out;
    }
	/**
     * 将输出流关闭
     * 
     * @param out
     */
    public void endResponseWriter(PrintWriter out) 
    {
		out.println("</response>");
		out.close();
    }

    public List findHibernateTemplate(String arg0,Object arg1)
	{
		return getHibernateTemplate().find(arg0, arg1);
	}
	
	public List findHibernateTemplate(String arg0,Object[] arg1)
	{
		return getHibernateTemplate().find(arg0, arg1);
	}

	/** ******************Manager*********************************************************************** */
	/**
	 * {@inheritDoc}
	 */
	public PageBean isFirstPaginator(PageBean pageBean, HttpServletRequest request)
    {
		String actionType = request.getParameter("actionType");
		String dynsize = request.getParameter("dynsize");
        if(actionType == null || "".equals(actionType))
            actionType = "none";
        
        if(actionType.equals("none"))
        {
        	int count = getPaginateCount(pageBean);
            
        	if(dynsize != null && !"".equals(dynsize))
        	{
        		pageBean.setPageSize(new Integer(dynsize));
        	}
            pageBean.setCurrentPage(1);
            pageBean.setCount(count);
            pageBean.setActionType(count);
        } 
        else
        if(actionType.equals("count"))
        {
        	int count = getPaginateCount(pageBean);
            pageBean.setCount(count);
            pageBean.setActionType(count);;
            if(pageBean.getCurrentPage()>pageBean.getPageCount() && pageBean.getPageCount()>0)//段洋波修改:当删除最末页的最后一条记录时,返回到上一页.
        	{
        		pageBean.setCurrentPage(pageBean.getPageCount());
        	}
        } 
        else
        {
            pageBean.setCount((new Integer(actionType)).intValue());
            pageBean.setActionType((new Integer(actionType)).intValue());
        }
        return pageBean;
    }
	
	public void checkListSQL(HttpServletRequest request, String sql,String countSql){
		 
		 PageBean pageBean = (PageBean)request.getAttribute("paginator");
	        String listSQL = request.getParameter("listSQL");
	    	String actType = request.getParameter("actType");
	        String pageNo = request.getParameter("pageNo");
	        String pageSize = request.getParameter("pageSize");
	        String navigationItemCount = request.getParameter("navigationItemCount");

	        //如果是第一次进入分页, 新建分页对象
	        //struts1由form自动初始化, struts2要手工设置
	        if(pageBean == null)
	        	pageBean = new PageBean();   
	        
	        if(listSQL == null || "".equals(listSQL))
	            listSQL = "none";
	        
	        if("none".equals(listSQL)){
	        	pageBean.setListSQL(sql);
	        	pageBean.setPagecountSQL(countSql);
	        }
	        else{
	            pageBean.setListSQL(listSQL);
	            pageBean.setPagecountSQL(countSql);
	        }
	        
	        if(pageSize != null && !pageSize.equals("")){
	        	pageBean.setPageSize(Integer.parseInt(pageSize));
	        }
	        pageBean = isFirstPaginator(pageBean, request);
	        
	    	
	        if("goto_page".equals(actType)){
	        	pageBean.setCurrentPage(new Integer(pageNo).intValue());
	        }
	        if("first_page".equals(actType)){
	        	pageBean.setCurrentPage(1);
	        }
	        if("yest_page".equals(actType)){
	        	pageBean.setCurrentPage(pageBean.getCurrentPage() - 1);
	        }
	        if("next_page".equals(actType)){
	        	pageBean.setCurrentPage(pageBean.getCurrentPage() + 1);
	        }
	        if("last_page".equals(actType)){
	        	pageBean.setCurrentPage(pageBean.getPageCount());
	        }
	        if(pageBean.count % pageBean.pageSize == 0) {
	        	pageBean.pageCount = pageBean.count / pageBean.pageSize;
	        }
	        else{ 
	        	pageBean.pageCount = pageBean.count / pageBean.pageSize + 1;
	        }
	        if(navigationItemCount != null && !navigationItemCount.equals("")){
	        	pageBean.setNavigationItemCount(Integer.parseInt(navigationItemCount));
	        }
	        pageBean.pageCote = (int)(Math.ceil(new Double(pageBean.currentPage) / new Double(pageBean.navigationItemCount - 1)) - 1);    //当前页处于第几栏分页
	        pageBean.pageCoteCount = (int)(Math.ceil(new Double(pageBean.getPageCount()) / new Double(pageBean.navigationItemCount - 1)));     //总分页栏
	        pageBean.pageStart = pageBean.pageCote * (pageBean.navigationItemCount -1) + 1;                    //分页栏中起始页
	        pageBean.pageEnd = pageBean.pageStart + pageBean.navigationItemCount - 1;                          //分页栏中终止页
	        if(pageBean.pageCount < pageBean.pageEnd)
	        {
	            pageBean.pageEnd = pageBean.pageCount;
	        }
	        request.setAttribute("paginator", pageBean);
	 }
	
    /**
     * {@inheritDoc}
     */
    public void checkListSQL(HttpServletRequest request, String sql)
    {
        PageBean pageBean = (PageBean)request.getAttribute("paginator");
        String listSQL = request.getParameter("listSQL");
    	String actType = request.getParameter("actType");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String navigationItemCount = request.getParameter("navigationItemCount");

        //如果是第一次进入分页, 新建分页对象
        //struts1由form自动初始化, struts2要手工设置
        if(pageBean == null)
        	pageBean = new PageBean();   
        
        if(listSQL == null || "".equals(listSQL))
            listSQL = "none";
        
        if("none".equals(listSQL))
        	pageBean.setListSQL(sql);
        else
            pageBean.setListSQL(listSQL);
        
        if(pageSize != null && !pageSize.equals("")){
        	pageBean.setPageSize(Integer.parseInt(pageSize));
        }
        pageBean = isFirstPaginator(pageBean, request);
        
    	
        if("goto_page".equals(actType)){
        	pageBean.setCurrentPage(new Integer(pageNo).intValue());
        }
        if("first_page".equals(actType)){
        	pageBean.setCurrentPage(1);
        }
        if("yest_page".equals(actType)){
        	pageBean.setCurrentPage(pageBean.getCurrentPage() - 1);
        }
        if("next_page".equals(actType)){
        	pageBean.setCurrentPage(pageBean.getCurrentPage() + 1);
        }
        if("last_page".equals(actType)){
        	pageBean.setCurrentPage(pageBean.getPageCount());
        }
        if(pageBean.count % pageBean.pageSize == 0) {
        	pageBean.pageCount = pageBean.count / pageBean.pageSize;
        }
        else{ 
        	pageBean.pageCount = pageBean.count / pageBean.pageSize + 1;
        }
        if(navigationItemCount != null && !navigationItemCount.equals("")){
        	pageBean.setNavigationItemCount(Integer.parseInt(navigationItemCount));
        }
        pageBean.pageCote = (int)(Math.ceil(new Double(pageBean.currentPage) / new Double(pageBean.navigationItemCount - 1)) - 1);    //当前页处于第几栏分页
        pageBean.pageCoteCount = (int)(Math.ceil(new Double(pageBean.getPageCount()) / new Double(pageBean.navigationItemCount - 1)));     //总分页栏
        pageBean.pageStart = pageBean.pageCote * (pageBean.navigationItemCount -1) + 1;                    //分页栏中起始页
        pageBean.pageEnd = pageBean.pageStart + pageBean.navigationItemCount - 1;                          //分页栏中终止页
        if(pageBean.pageCount < pageBean.pageEnd)
        {
            pageBean.pageEnd = pageBean.pageCount;
        }
        request.setAttribute("paginator", pageBean);
    }
    
    public int getPaginateCount(PageBean aPageBean)
    {
        try {
        	Session session = getSession();
            Query query = session.createQuery(aPageBean.getListSQL());
            ScrollableResults scrollRs = query.scroll();
            scrollRs.last();
            int count = scrollRs.getRowNumber() + 1;
            return count;
		} catch (Exception e) {
			logger.info("出错了"+e.getMessage());
			return -1;
		}
    }

    public List getPaginateList(PageBean aPageBean)
    {	
        try {
        	Session session = getSession();
            Query query = session.createQuery(aPageBean.getListSQL());
            query.setFirstResult((aPageBean.getCurrentPage() - 1) * aPageBean.getPageSize());
            query.setMaxResults(aPageBean.getPageSize());
            List list = query.list();
            return list;
		} catch (Exception e) {
			logger.info("出错了"+e.getMessage());
			return null;
		}
    }

    
    /**
     * {@inheritDoc}
     */
    public List paginator(HttpServletRequest request, String sql)
    {    	
    	PageBean pageBean = (PageBean)request.getAttribute("paginator");
        List list = getPaginateList(pageBean);
        request.setAttribute("paginator", pageBean);
        return list;
    }

	
	/*******************************基于sql的分页*******************************/
	 /**
     * 基于sql的分页
     * @param aPageBean
     * @return
     */
    public List getPaginateListBySql(PageBean aPageBean)
    {	
        Session session = getSession();
        Query query = session.createSQLQuery(aPageBean.getListSQL());
        query.setFirstResult((aPageBean.getCurrentPage() - 1) * aPageBean.getPageSize());
        query.setMaxResults(aPageBean.getPageSize());
        List list = query.list();
        return list;
    }

    /**
     * 基于sql的分页
     * @param request
     * @param sql
     * @return
     */
    public List paginatorBySql(HttpServletRequest request, String sql)
    {    	
    	PageBean pageBean = (PageBean)request.getAttribute("paginator");
        List list = getPaginateListBySql(pageBean);
        request.setAttribute("paginator", pageBean);
        return list;
    }

    /**
     * 基于sql的分页
     * @param request
     * @param sql
     */
    public void checkListBySql(HttpServletRequest request, String sql)
    {
        PageBean pageBean = (PageBean)request.getAttribute("paginator");
        String listSQL = request.getParameter("listSQL");
    	String actType = request.getParameter("actType");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String navigationItemCount = request.getParameter("navigationItemCount");

        //如果是第一次进入分页, 新建分页对象
        //struts1由form自动初始化, struts2要手工设置
        if(pageBean == null)
        	pageBean = new PageBean();   
        
        if(listSQL == null || "".equals(listSQL))
            listSQL = "none";
        
        if("none".equals(listSQL))
        	pageBean.setListSQL(sql);
        else
            pageBean.setListSQL(listSQL);
        
        if(pageSize != null && !pageSize.equals("")){
        	pageBean.setPageSize(Integer.parseInt(pageSize));
        }
        pageBean = isFirstPaginatorBySql(pageBean, request);
    	
        if("goto_page".equals(actType)){
        	pageBean.setCurrentPage(new Integer(pageNo).intValue());
        }
        if("first_page".equals(actType)){
        	pageBean.setCurrentPage(1);
        }
        if("yest_page".equals(actType)){
        	pageBean.setCurrentPage(pageBean.getCurrentPage() - 1);
        }
        if("next_page".equals(actType)){
        	pageBean.setCurrentPage(pageBean.getCurrentPage() + 1);
        }
        if("last_page".equals(actType)){
        	pageBean.setCurrentPage(pageBean.getPageCount());
        }
        if(pageBean.count % pageBean.pageSize == 0) {
        	pageBean.pageCount = pageBean.count / pageBean.pageSize;
        }
        else{ 
        	pageBean.pageCount = pageBean.count / pageBean.pageSize + 1;
        }
        if(navigationItemCount != null && !navigationItemCount.equals("")){
        	pageBean.setNavigationItemCount(Integer.parseInt(navigationItemCount));
        }
        pageBean.pageCote = (int)(Math.ceil(new Double(pageBean.currentPage) / new Double(pageBean.navigationItemCount - 1)) - 1);    //当前页处于第几栏分页
        pageBean.pageCoteCount = (int)(Math.ceil(new Double(pageBean.getPageCount()) / new Double(pageBean.navigationItemCount - 1)));     //总分页栏
        pageBean.pageStart = pageBean.pageCote * (pageBean.navigationItemCount -1) + 1;                    //分页栏中起始页
        pageBean.pageEnd = pageBean.pageStart + pageBean.navigationItemCount - 1;                          //分页栏中终止页
        if(pageBean.pageCount < pageBean.pageEnd)
        {
            pageBean.pageEnd = pageBean.pageCount;
        }
        request.setAttribute("paginator", pageBean);
    }
    /**
     * 基于sql的分页
     * @param pageBean
     * @param request
     * @return
     */
    public PageBean isFirstPaginatorBySql(PageBean pageBean, HttpServletRequest request)
    {
		String actionType = request.getParameter("actionType");
		String dynsize = request.getParameter("dynsize");
        if(actionType == null || "".equals(actionType))
            actionType = "none";
        if(actionType.equals("none"))
        {
        	int count = getPaginateCountBySql(pageBean,request);
        	if(dynsize != null && !"".equals(dynsize))
        	{
        		pageBean.setPageSize(new Integer(dynsize));
        	}
            pageBean.setCurrentPage(1);
            pageBean.setCount(count);
            pageBean.setActionType(count);
        } 
        else if(actionType.equals("count"))
        {
        	int count = getPaginateCountBySql(pageBean,request);
            pageBean.setCount(count);
            pageBean.setActionType(count);;
            if(pageBean.getCurrentPage()>pageBean.getPageCount() && pageBean.getPageCount()>0)//段洋波修改:当删除最末页的最后一条记录时,返回到上一页.
        	{
        		pageBean.setCurrentPage(pageBean.getPageCount());
        	}
        } 
        else
        {
            pageBean.setCount((new Integer(actionType)).intValue());
            pageBean.setActionType((new Integer(actionType)).intValue());
        }
        return pageBean;
    }
    /**
	 * 基于sql的分页
	 * 
	 * @param aPageBean
	 * @return
	 */
	public int getPaginateCountBySql(PageBean pageBean,
			HttpServletRequest request) {
		// 使用sql语句和hql语句分页的判断 modify wanghc
		int count = 0;
		Session session = this.getSession();
		List lcount = session.createSQLQuery("select count(*) as count1 from (" + pageBean.getListSQL()+ ") as a").list();
		if (lcount != null && lcount.size() > 0) {
			BigInteger o = (BigInteger) lcount.get(0);
			if (o != null && o.intValue() > 0) {
				count = o.intValue();
			}
		}
		return count;
	}

    /** *****************************基于sql的分页****************************** */
}
