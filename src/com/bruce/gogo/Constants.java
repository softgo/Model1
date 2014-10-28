package com.bruce.gogo;

/**
 * admin
 * 
 * 系统级静态常量
 * 
 * <p>可通过powerapps.properties初始化,同时保持常量static & final的特征.</P>
 * 
 * @version v1.0
 * @see ConfigConstants
 * 
 */
public class Constants extends ConfigConstants {

    //静态初始化读入apps.properties中的设置
    static {
        init("apps.properties");
    }

   /**
    * 从apps.properties中读取constant.message_bundle_key的值，如果配置文件不存在或配置文件中不存在该值时，默认取值"messages"
    */
    //本地
    public final static String ActionExt = getProperty("ActionExt", "action");//Action 后缀名
    public final static int DEFAULT_PAGE_SIZE = Integer.parseInt(getProperty("default_page_size", String.valueOf(50)));
    public final static int SET_PAGE_SIZE_100 = Integer.parseInt(getProperty("set_page_size", String.valueOf(100)));
    public final static int SET_PAGE_SIZE_300 = Integer.parseInt(getProperty("set_page_size", String.valueOf(300)));
    public final static int SET_PAGE_SIZE_500 = Integer.parseInt(getProperty("set_page_size", String.valueOf(500)));
    public final static int SET_PAGE_SIZE_800 = Integer.parseInt(getProperty("set_page_size", String.valueOf(800)));
    public final static int SET_PAGE_SIZE_1000 = Integer.parseInt(getProperty("set_page_size", String.valueOf(1000)));
    public final static int SET_PAGE_SIZE_2000 = Integer.parseInt(getProperty("set_page_size", String.valueOf(2000)));
    public static final String sessioninfo = getProperty("sessioninfo","sessioninfo");//sessioninfo名称
    public static final String adminrole = getProperty("adminrole","1");//系统管理员角色
    public static final String roleflag = getProperty("roleflag","1");//系统管理员----角色分配
    public final static String cache_server1 = getProperty("cache_server", "192.168.3.251:21211");//缓存服务器
    
    //正式用
    public final static String imgserver = "http://pic9.zhongsou.com/zfsimage/";//图片服务器地址，图片预览
    //测试用
    public final static String imgservertest = "http://pic9test.zhongsou.com/zfsimage/";//图片服务器地址，图片预览
    
    public static final String common_batchsave = "";//保存所做修改
    
}

