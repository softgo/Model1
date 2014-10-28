package com.bruce.gogo.common.service;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * dao的接口类.
 * 
 * @author admin
 * 
 */
public interface ICommonDao {

	/**
	 * 获得hibernate对象.
	 * @return
	 */
	public HibernateTemplate getMyHibernateTemplate();

	/**
	 * 获得session.
	 * @return
	 */
	public Session getMySession();

	/**
	 * 得到对象根据类和标识符的普通方法 如果什么都没有被找到, 返回 null 如果出现错误, 返回 null
	 * 
	 * @param clazz
	 *            model 类
	 * @param id标识符(主关键字)
	 * @return 一个存在的对象
	 * @see org.springframework.orm.ObjectRetrievalFailureException a
	 */
	public Object getObject(Class clazz, Serializable sign);
	
	/**
	 * 保存对象.
	 * 
	 * @param obj
	 */
	public void save(Object obj);

	/**
	 * 保存或者更改对象.
	 * 
	 * @param obj
	 */
	public void saveOrUpdate(Object obj);

	/**
	 * 根据对象删除
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return
	 */
	public void removeObject(Object obj);

	/**
	 * 根据id与类删除记录
	 * 
	 * @param clazz
	 * @param id
	 */
	public void removeObject(Class clazz, Serializable id);
	
	/**
	 * 多项删除
	 * 
	 * @param c
	 *            要删除的对象集合
	 * @return
	 */
	public void removeAll(ArrayList arry);

	/**
	 * 用一条hql语句完成更新操作
	 * 
	 * @param hql
	 * @return
	 */
	public int executeUpdate(String hql);


	public void clear();

	/**
	 * 普通使用的方法得到一个特殊类型的所有对象 这是象查寻相同表里所有列
	 * 
	 * @param clazz
	 *            对象
	 * @return 在对象里的数据列表
	 */
	public List getAllObjects(Class entity);


	/**
	 * 获得多条数据的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 *            带参数的查询语句
	 * @param map
	 *            参数
	 * @return List 查询结果列表
	 */
	public List getHqlList(String queryString, Map map);

	/**
	 * 获得单一记录的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 *            带参数的查询语句
	 * @param map
	 *            参数
	 * @return Object 查询结果对象
	 */
	public Object getHqlObject(String queryString, Map map);

	/**
	 * 返回Query接口
	 * 
	 * @param sql
	 * @return
	 */
	public Query getQuery(String sql);

	/**
	 * 返回SQLQuery接口
	 * 
	 * @param sql
	 * @return
	 */
	public SQLQuery createSqlQuery(String sql);

	/**
	 * 返回Criteria接口
	 * 
	 * @param classz
	 * @return
	 */
	public Criteria createCriteria(Class classz);

	/**
	 * 获得多条数据的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 * @return
	 */
	public List getHqlList(String queryString);

	/**
	 * 命名查询
	 * 
	 * @param namedQuerStr
	 * @return
	 */
	public Query getNamedQuery(String namedQuerStr);

	/**
	 * 获得多条数据的普通方法 (根据Hql) 如出现异常时 查询结果为 null
	 * 
	 * @param queryString
	 * @param max
	 *            返回记录数
	 * @return
	 */
	public List getHqlList(String queryString, int max);

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
	public List getHqlList(String queryString, int first, int max);

	/**
	 * 打开ajax的输出流
	 * 
	 * @param request
	 * @param response
	 * @return PrintWriter
	 */
	public PrintWriter startResponseWriter(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 将输出流关闭
	 * 
	 * @param out
	 */
	public void endResponseWriter(PrintWriter out);

	public List findHibernateTemplate(String arg0, Object arg1);

	public List findHibernateTemplate(String arg0, Object[] arg1);

	/** ******************Manager*********************************************************************** */

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param sql
	 *            带参数的查询语句
	 * @return List 分页后的结果集
	 */
	public List paginator(HttpServletRequest request, String sql);

	public void checkListSQL(HttpServletRequest request, String sql);

	public void checkListSQL(HttpServletRequest request, String sql,
			String countSql);

	/**
	 * 多项删除
	 * 
	 * @param clazz
	 * @param idArray
	 *            要删除的数据的主键集合
	 * @return int 删除的记录数目
	 */
	public int deleteAllById(Class clazz, String idArray[]);

	public int getCountByCriteria(final DetachedCriteria detachedCriteria);

}
