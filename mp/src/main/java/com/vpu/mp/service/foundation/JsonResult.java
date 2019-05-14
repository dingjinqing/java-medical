package com.vpu.mp.service.foundation;

public class JsonResult {

	/**
	 *  错误码：非0为错误
	 */
	private int error = 0;
	
	/**
	 * 返回内容
	 */
	private Object content;
	
	/**
	 * 错误消息
	 */
	private Object message;
	
	
	public JsonResult() {
		
	}
	
	public static JsonResult instance() {
		return new JsonResult();
	}
	
	public JsonResult(int error,Object message,Object content) {
		this.setError(error);
		this.setMessage(message);
		this.setContent(content);
	}
	
	static public JsonResult success(Object content) {
		return new JsonResult(0,null,content);
	}
	
	static  public JsonResult fail(Object message) {
		return new JsonResult(1,message,null);
	}
	
	static public JsonResult fail(Object message,int error) {
		return new JsonResult(error,message,null);
	}
		

	@Override
	public String toString() {
		return Util.toJSON(this);
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
