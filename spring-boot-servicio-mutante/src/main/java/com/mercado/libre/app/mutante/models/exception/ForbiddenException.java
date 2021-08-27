/**
 * 
 */
package com.mercado.libre.app.mutante.models.exception;

/**
 * @author dfcertuche
 *
 */
public class ForbiddenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5152955855297494513L;

	private String msg;

	public ForbiddenException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
