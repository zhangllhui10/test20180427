package com.hui10.app.common.exception;

import com.hui10.app.common.lottery.HttpResult;

/**
 * @ClassName: ResultException.java
 * @Description:
 * @author yangcb
 * @date 2017年1月11日 下午3:34:18
 */
@SuppressWarnings("rawtypes")
public class ResultException extends RuntimeException {

	private static final long serialVersionUID = 2258449149167291953L;

	private HttpResult httpResult;

	public HttpResult getHttpResult() {
		return httpResult;
	}

	public void setHttpResult(HttpResult httpResult) {
		this.httpResult = httpResult;
	}

	public ResultException() {

	}

	public ResultException(HttpResult httpResult) {
		this.httpResult = httpResult;
	}

}
