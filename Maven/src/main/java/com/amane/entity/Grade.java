package com.amane.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Grade {
	
	private String uid;
	
	private String qid;
	
	private String answer;
	
	private int grade;
	
	private Date answerTime;
	
	private Date gradeTime;
}
