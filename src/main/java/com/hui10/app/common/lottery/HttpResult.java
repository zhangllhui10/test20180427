package com.hui10.app.common.lottery;
/**
 * @ClassName: HttpResult.java
 * @Description:
 * @author yangcb
 * @date 2017年1月11日 上午11:48:14
 */
public  class  HttpResult<T> {

	private String result;
	private String resultDesc;
	private String signature;
	private T transData;
	
	
	public HttpResult(){
		this.signature="null";
		this.result="0";
		this.resultDesc="成功";
	}
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public T getTransData() {
		return transData;
	}
	public void setTransData(T transData) {
		this.transData = transData;
	} 
	
	
	
	
	
}
