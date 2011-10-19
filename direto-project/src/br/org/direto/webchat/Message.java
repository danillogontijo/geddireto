package br.org.direto.webchat;

import java.io.Serializable;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8161219654535242929L;
	
	private UserChat from;
	private UserChat to;
	private String HTMLCode = "";
	
	public UserChat getFrom() {
		return from;
	}
	public void setFrom(UserChat from) {
		this.from = from;
	}
	public UserChat getTo() {
		return to;
	}
	public void setTo(UserChat to) {
		this.to = to;
	}
	public String getHTMLCode() {
		return HTMLCode;
	}
	public void setHTMLCode(String hTMLCode) {
		HTMLCode = hTMLCode;
	}
	
	
}
