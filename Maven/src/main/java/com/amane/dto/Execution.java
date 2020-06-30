package com.amane.dto;

import com.amane.enums.NormalStateEnum;
import lombok.Data;

@Data
public class Execution<T> {
	
	// 注册结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 预约成功对象
	private T data;

	public Execution() {
	}

	/** 预约失败的构造器 */
	public Execution(NormalStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	/** 预约成功的构造器 */
	public Execution(NormalStateEnum stateEnum, T data) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.data = data;
	}
}