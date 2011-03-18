package br.org.direto.webchat;

public class Message {
	
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
