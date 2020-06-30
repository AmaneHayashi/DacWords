package com.amane.entity;

import java.util.Date;

import lombok.Data;

@Data
public class User {

	private String uid;
	
	private String name;
	
	private String pswd;

	private Date registime;
	
	private Date lastlogin;
	
	private Date thislogin;
	
	private int ppsn;
	
	private int tn;
	
	private int coln;
	
	private int msgn;
}
