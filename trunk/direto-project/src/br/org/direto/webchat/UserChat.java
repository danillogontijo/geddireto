package br.org.direto.webchat;

import java.util.List;

public class UserChat {

	private String nameUser;
	private List<Message> messages;
	
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
	
	

}
