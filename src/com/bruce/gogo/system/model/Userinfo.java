package com.bruce.gogo.system.model;

public class Userinfo implements java.io.Serializable{

	
	private Integer id;
	private String username;
	private String password;
	private String loginname;
	private String notes;
	
	/** default constructor */
	public Userinfo() {
	}

	/** minimal constructor */
	public Userinfo( String loginname) {
		this.loginname = loginname;
	}

	/** full constructor */
	public Userinfo( String username, String loginname, String notes) {
		this.username = username;
		this.loginname = loginname;
		this.notes = notes;
		
		
	}

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
}
