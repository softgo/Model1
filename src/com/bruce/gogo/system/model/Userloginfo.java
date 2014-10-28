package com.bruce.gogo.system.model;

import java.util.Date;

public class Userloginfo  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer userid;
     private String username;
     private Date operationtime;
     private String content;    
     private Integer moduleid;
     private String modulename;
     private Integer parentid;


    // Constructors

    /** default constructor */
    public Userloginfo() {
    }

    
    /** full constructor */
    public Userloginfo(Integer userid, String username, Date operationtime, String content, Integer moduleid, String modulename, Integer parentid) {
        this.userid = userid;
        this.username = username;
        this.operationtime = operationtime;
        this.content = content;
        this.moduleid = moduleid;
        this.modulename = modulename;
        this.parentid = parentid;
    }

   
    // Property accessors

  
    

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public Date getOperationtime() {
        return this.operationtime;
    }
    
    public void setOperationtime(Date operationtime) {
        this.operationtime = operationtime;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
  

    public String getModulename() {
        return this.modulename;
    }
    
    public void setModulename(String modulename) {
        this.modulename = modulename;
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getModuleid() {
		return moduleid;
	}


	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}


	public Integer getParentid() {
		return parentid;
	}


	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}


	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}

   
   








}