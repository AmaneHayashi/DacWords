package com.amane.enums;

public enum NormalStateEnum {
	
	SUCCESS(1, "传输成功"), 
	DUPLICATION(0, "字段或键值重复"), 
	EMPTY_FIELD(-1, "空输入或输出"),
	WRONG_INPUT(-2, "输入错误"),
	INNER_ERROR(-3, "内部错误");

	private int state;

	private String stateInfo;

	private NormalStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static NormalStateEnum stateOf(int index) {
		for (NormalStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
