package com.amane.entity;

import lombok.Data;

@Data 
public class Question {

	private String id;
	
	private String name;
	
	private String kind;
	
	private String field;
	
	private String subject;
	
	private String point;
	
	private String type;
	
	private int diff;
	
	private String ppsr;
	
	private String ppstime;
	
	private String content;
	
	private String optionA;
	
	private String optionB;
	
	private String optionC;
	
	private String optionD;
	
	private String key;
	
	private String solution;
	
	private String picpath;
	
}
