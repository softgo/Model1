package com.bruce.gogo.system.model;

/**
 * Userroleinfo generated by MyEclipse Persistence Tools
 */

public class Userroleinfo implements java.io.Serializable {

	// Fields

	private Integer id;

	private Integer userid;

	private String roleid;

	// Constructors

	/** default constructor */
	public Userroleinfo() {
	}

	/** full constructor */
	public Userroleinfo(Integer id, Integer userid, String roleid) {
		this.id = id;
		this.userid = userid;
		this.roleid = roleid;
	}

	// Property accessors

	

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}