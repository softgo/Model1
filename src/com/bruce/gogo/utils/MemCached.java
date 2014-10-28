package com.bruce.gogo.utils;

import java.util.Date;

import com.bruce.gogo.Constants;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
* 使用memcached的缓存实用类.
* 
* @author 周立峰
* @version 1.0
*
*/
public class MemCached {
	
    // 创建全局的唯一实例
    protected static MemCachedClient mcc = new MemCachedClient();
    
    protected static MemCached memCached = new MemCached();
    
    // 设置与缓存服务器的连接池
    static {
        // 服务器列表和其权重
        String[] servers = {Constants.cache_server1};
        Integer[] weights = {3};

        // 获取socke连接池的实例对象
        SockIOPool pool = SockIOPool.getInstance();

        // 设置服务器信息
        pool.setServers( servers );
        pool.setWeights( weights );

        // 设置初始连接数、最小和最大连接数以及最大处理时间
        pool.setInitConn( 5 );
        pool.setMinConn( 5 );
        pool.setMaxConn( 250 );
        pool.setMaxIdle( 1000 * 60 * 60 * 6 );

        // 设置主线程的睡眠时间
        pool.setMaintSleep( 30 );

        // 设置TCP的参数，连接超时等
        pool.setNagle( false );
        pool.setSocketTO( 3000 );
        pool.setSocketConnectTO( 0 );

        // 初始化连接池
        pool.initialize();

        // 压缩设置，超过指定大小（单位为K）的数据都会被压缩
        mcc.setCompressEnable( true );
        mcc.setCompressThreshold( 64 * 1024 );
    }
    
    /**
     * 保护型构造方法，不允许实例化！
     *
     */
    protected MemCached()
    {
        
    }
    
    /**
     * 获取唯一实例.
     * @return
     */
    public static MemCached getInstance()
    {
        return memCached;
    }
    
    /**
     * 设置缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value)
    {
    	return mcc.set(key, value);
    }
    /**
     * 设置缓存带过期时间
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    public boolean set(String key,Object value,Date expiry)
    {
    	return mcc.set(key, value, expiry);
    }
    /**
     * 设置缓存
     * @param key
     * @param value
     * @param number
     * @return
     */
    public boolean set(String key,Object value,Integer number)
    {
    	return mcc.set(key, value, number);
    }
        
    /**
     * 添加一个指定的值到缓存中.
     * @param key
     * @param value
     * @return
     */
    public boolean add(String key, Object value)
    {
        return mcc.add(key, value);
    }
    /**
     * 添加一个指定的值到缓存中带过期时间
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    public boolean add(String key, Object value, Date expiry)
    {
        return mcc.add(key, value, expiry);
    }    
    /**
     * 替换缓存
     * @param key
     * @param value
     * @return
     */
    public boolean replace(String key, Object value)
    {
        return mcc.replace(key, value);
    }
    /**
     * 替换缓存带过期时间
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    public boolean replace(String key, Object value, Date expiry)
    {
        return mcc.replace(key, value, expiry);
    }
    

    /**
     * 删除缓存对象
     * @param key
     * @return boolean true:成功 false:失败
     */
    public boolean delete(String key)
    {
        return mcc.delete(key);
    }
    
    /**
     * 获取缓存对象
     * @param key
     * @return Object
     */
    public Object get(String key)
    {
        return mcc.get(key);
    }
    
    /**
     * 判断指定的key是否存在
     * @param key
     * @return boolean true:存在 false:不存在
     */
    public boolean containsKey(String key)
    {
    	return mcc.keyExists(key);
    }
    
    public static void main(String[] args)
    {
        MemCached cache = MemCached.getInstance();
        cache.add("hello", "java2");
        System.out.print("get value : " + cache.get("hello"));
    }

}
