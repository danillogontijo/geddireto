package br.org.direto.webchat;

import java.util.List;

public interface UserChat {
	
	public String getUser();
	
	public Message getNewMessage();
	
	public void addMessage(Message msg);
	
	public void addMessages(List<Message> listMsg);

}
