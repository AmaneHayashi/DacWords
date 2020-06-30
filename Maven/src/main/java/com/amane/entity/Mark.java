package com.amane.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
	
	private String uid;
	
	private String tid;
	
	private int marks;
}
