package com.bruce.gogo.common.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;

import javax.sql.DataSource;
import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import sun.jdbc.rowset.CachedRowSet;

/**
 * DB helper.
 * 
 * @author admin
 *
 */
public class DBHelper {

	private static Logger logger = Logger.getLogger(DBHelper.class.getName());

	private Statement statement = null;

	private PreparedStatement preparedStatement = null;

	private static DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DBHelper() {
		
	}

	/**
	 * Execute the database's update
	 * 
	 * @param sql
	 *            String
	 * @throws ZhongsouException
	 * @return int
	 */
	public int executeUpdate(String[] sql) throws SQLException {
		Connection conn = null;
		Statement stat = null;
		int count = 0;
		try {
			conn = getConnection();
			for (int i = 0; i < sql.length; i++) {
				stat = conn.createStatement();
				logger.info(sql[i]);
				try {
					count += stat.executeUpdate(sql[i]);
				} catch (SQLException e) {
					logger.error(sql[i] + "执行失败！");
				}
			}
		} catch (SQLException se) {
			logger.info("执行出错："+se.getMessage());
		} finally {
			try {
				if(stat!=null)
					stat.close();
				if(conn!=null)
				   conn.close();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		return count;
	}

	/**
	 * Execute the database's update
	 * 
	 * @param sql
	 *            String
	 * @throws ZhongsouException
	 * @return int
	 */
	public static int executeUpdate(String sql) throws SQLException {
		Connection conn = null;
		Statement stat = null;
		int count = 0;
		try {
			conn = getConnection();
			stat = conn.createStatement();
			count = stat.executeUpdate(sql);
		} catch (SQLException se) {
			logger.info("执行出错："+se.getMessage());
		} finally {
			try {
				if(stat!=null)
					stat.close();
				if(conn!=null)
				   conn.close();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		return count;
	}

	public static PreparedStatement update(String tablename, Map<String, Object> contentValues,
			String wheres,Connection conn) throws Exception {
		String sql = "update " + tablename+" set ";
		for (Map.Entry<String, Object> entry : contentValues.entrySet()) {
			sql += entry.getKey() + "=? ,";
		}

		sql = sql.substring(0, sql.length() - 1);
		sql += " where " + wheres;

		System.out.println("执行update:"+sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		int i = 0;
		for (Map.Entry<String, Object> entry : contentValues.entrySet()) {
			ps.setObject(++i, entry.getValue());
		}
		return ps;

	}
	/**
	 * Execute the database query
	 * 
	 * @param sql
	 *            String
	 * @throws ZhongsouException
	 * @return RowSet
	 */
	public static RowSet executeQuery(String sql) throws SQLException {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		RowSet rowSet = null;
		try {
			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			CachedRowSet cachedRs = new CachedRowSet();
			cachedRs.populate(rs);
			rowSet = cachedRs;
		} catch (SQLException se) {
			logger.info("执行出错："+se.getMessage());
		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(stat!=null)
					stat.close();
				if(conn!=null)
				   conn.close();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		return rowSet;
	}
	

	/**
	 * 直接使用已有连接查询
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet exeRepeatableQuery(String sql) throws SQLException {
		return getPreparedStatement().executeQuery(sql);
	}

	/**
	 * 执行PreparedStatement的查询
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet exePreparedQuery() throws SQLException {
		return getPreparedStatement().executeQuery();
	}

	/**
	 * 执行PreparedStatement的更新
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int exePreparedUpdate() throws SQLException {
		return getPreparedStatement().executeUpdate();
	}


	private Connection getConnection1() throws SQLException {
		// if (connection == null) {
		Connection connection = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://localhost:1433/NewsSourceDB", "sa",
					"sa");
		} catch (Exception e) {
			throw new SQLException("创建连接出现异常：" + e.getMessage());
		}
		// }

		return connection;
	}

	private Connection getConnection2() throws SQLException {
		Connection connection = null;
		// if (connection == null) {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://211.147.17.158:1433/NewsSourceDB2",
					"sa", "idc_kaifa2003");
		} catch (Exception e) {
			throw new SQLException("创建连接出现异常：" + e.getMessage());
		}
		// }

		return connection;
	}
	
	private Connection getConnection3() throws SQLException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://202.108.1.249:3306/video3","vider3","vi3#DehE");
		} catch (Exception e) {
			throw new SQLException("创建连接出现异常：" + e.getMessage());
		}
		return connection;
	}

	/**
	 * 取得数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		if (dataSource == null) {
			String root=DBHelper.class.getResource("/").getPath();
			logger.debug("DBExecutor.class.getResource(/).getPath()==="+DBHelper.class.getResource("/").getPath());
			String path="/" + root+"applicationContext.xml";
			try {
				path=URLDecoder.decode(path,"gbk");
			} catch (UnsupportedEncodingException e) {
			}
			String[] arg0 = { path };
			ApplicationContext ctx = new FileSystemXmlApplicationContext(arg0);
			dataSource = (DataSource) ctx.getBean("dataSourceA");
		}
		connection = dataSource.getConnection();
		return connection;
	}

	private Statement createStatement(int resultSetType,
			int resultSetConcurrency) throws SQLException {
		try {
			if (this.statement != null)
				this.closeStatement();
				statement = getConnection().createStatement(resultSetType,
					resultSetConcurrency);
		} catch (SQLException ex) {
			closeAll();
			throw ex;
		}
		return statement;
	}

	/**
	 * 创建普通的statement对象,(备注：外部调用该方法时，一定不能关闭Statement)
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Statement createStatement() throws SQLException {
		return createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
	}

	/**
	 * 创建可更新statement对象
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Statement createForwardOnlyAndUpdatableStatement()
			throws SQLException {
		return createStatement(ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE);
	}

	/**
	 * 创建PreparedStatement对象
	 * 
	 * @param sql
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @return
	 * @throws SQLException
	 */
	public boolean createPreparedStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		boolean result = false;
		try {
			if (this.preparedStatement != null)
				this.closePrepareStatement();
			this.preparedStatement = getConnection().prepareStatement(sql,
					resultSetType, resultSetConcurrency);
			result = true;
		} catch (SQLException ex) {
			closeAll();
			logger.info("出错了"+ex.getMessage());
		}
		return result;
	}

	/**
	 * 创建PreparedStatement对象
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public boolean createPreparedStatement(String sql) throws SQLException {
		return createPreparedStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
	}

	public void setInt(int position, int value) {
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.setInt(position, value);
			} catch (SQLException ex) {
				System.err.println("设置PreparedStatement参数值时访问数据库失败");
				logger.info("出错了"+ex.getMessage());
				closeAll();
			}
		}
	}

	public void setString(int position, String value) {
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.setString(position, value);
			} catch (SQLException ex) {
				System.err.println("设置PreparedStatement参数值时访问数据库失败");
				logger.info("出错了"+ex.getMessage());
				closeAll();
			}
		}
	}
	/**
	 * 新增setTimestamp
	 * @author GuoYing 2010.11.1
	 */
	public void setTimestamp(int position, Timestamp value){
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.setTimestamp(position, value);
			} catch (SQLException ex) {
				System.err.println("设置PreparedStatement参数值时访问数据库失败");
				logger.info("出错了"+ex.getMessage());
				closeAll();
			}
		}
	}
	
	/**
	 * 新增操作Text类型的数据 
	 * @author GuoYing 2010.10.8
	 * @param position
	 * @param value
	 */
    public void setText(int position, String value){
		if (this.preparedStatement != null) {
			byte[] bt = value.getBytes();
			try {
				this.preparedStatement.setBytes(position, bt);
			} catch (SQLException ex) {
				System.err.println("设置PreparedStatement参数值时访问数据库失败");
				logger.info("出错了"+ex.getMessage());
				closeAll();
			}
		}
	}
    
    /**
     * 新增操作Boolean类型的数据 
     * @param position
     * @param value
     */
    public void setBoolean(int position, Boolean value) {
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.setBoolean(position, value);
			} catch (SQLException ex) {
				System.err.println("设置PreparedStatement参数值时访问数据库失败");
				logger.info("出错了"+ex.getMessage());
				closeAll();
			}
		}
	}
    
    /**
     * 新增操作Object类型的数据 
     * @param position
     * @param value
     */
    public void setObject(int position, Object value) {
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.setObject(position, value);
			} catch (SQLException ex) {
				System.err.println("设置PreparedStatement参数值时访问数据库失败");
				logger.info("出错了"+ex.getMessage());
				closeAll();
			}
		}
	}
    
	public void setColb(int position, Clob clob) {
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.setClob(position, clob);
			} catch (SQLException ex) {
				System.err.println("设置PreparedStatement参数值时访问数据库失败");
				logger.info("出错了"+ex.getMessage());
				closeAll();
			}
		}
	}

	public void setNull(int position, int sqlType) {
		if (this.preparedStatement != null) {
			try {
				this.preparedStatement.setNull(position, sqlType);
			} catch (SQLException ex) {
				System.err.println("设置PreparedStatement参数值时访问数据库失败");
				logger.info("出错了"+ex.getMessage());
				closeAll();
			}
		}
	}

	/**
	 * 关闭所有的连接
	 * 
	 */
	public void closeAll() {
		closeConnection();
	}

	private void closeStatement() {
		try {
			if (this.statement != null){
				this.statement.getConnection().close();
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
	}

	private void closePrepareStatement() {
		try {
			if (this.preparedStatement != null){
			    this.preparedStatement.getConnection().close(); 
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
	}

	private void closeConnection() {
		logger.debug("join in closeConnection");
		try {
			if (this.preparedStatement != null){
				this.preparedStatement.getConnection().close();//之前this.preparedStatement.close()无法关闭立即数据库连接
			}
		} catch (SQLException ex) {
		 logger.error(ex.getMessage());
		}
		try {
			if (this.statement != null){
				this.statement.getConnection().close();
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 获得preparedstatement对象
	 * 
	 * @return
	 */
	public PreparedStatement getPreparedStatement() {
		return this.preparedStatement;
	}

	/**
	 * 获得preparedstatement对象
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement getPreparedStatement(String sql)
			throws SQLException {
		if (this.preparedStatement == null) {
			createPreparedStatement(sql);
		}
		return this.preparedStatement;
	}

	/**
	 * 获得statement对象
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Statement getStatement() throws SQLException {
		if (this.statement == null) {
			createStatement();
		}
		return this.statement;
	}
	
	public static void main(String[] args) {
		DBHelper exe = new DBHelper();
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
			DataSource shardedJedisPool =(DataSource)applicationContext.getBean("dataSourceA");
			System.out.println(shardedJedisPool.getConnection().getMetaData().getDatabaseProductVersion());
			exe.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
