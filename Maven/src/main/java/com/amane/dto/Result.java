package com.amane.dto;

import lombok.Data;

@Data
public class Result<T> {

	private boolean success;// 是否成功标志

	private T data;// 成功时返回的数据

	private String msg;// 错误信息

	public Result() {
	}

	// 成功时的构造器
	public Result(boolean success, T data) {
		this.success = success;
		this.data = data;
	}

	// 错误时的构造器
	public Result(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

}