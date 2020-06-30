package com.amane.enums;

public enum WebSocketEnum {
	
	DURATION_RECORDED(1, "时长已接收"),
	INVALID_DURATION(-1, "时长无效"),
	PING_RECEIVED(0, "心跳已接收"),
	TIMEOUT(2, "时长结束");

	private int state;

	private String stateInfo;

	private WebSocketEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
}
