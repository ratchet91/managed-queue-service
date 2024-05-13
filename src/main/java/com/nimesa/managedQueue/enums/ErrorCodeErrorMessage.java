package com.nimesa.managedQueue.enums;

public enum ErrorCodeErrorMessage {

	INTERNAL_SERVER_ERROR("ERR_100", "Internal Server Error"), APPLICATION_ERROR("ERR_101", "Application Error"),
	INVALID_TOPIC("ERROR_104", "Topic Not Found"), DUPLICATE_TOPIC("ERROR_103", "Duplicate Topic Found"),
	DUPLICATE_MESSAGE("ERROR_105", "Duplicate Message Found");

	private String errCode;
	private String errMsg;

	ErrorCodeErrorMessage(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
