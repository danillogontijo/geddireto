package br.org.direto.webchat;

import java.util.ArrayList;
import java.util.List;

public class UserChatImpl implements UserChat {

	private String nameUser;
	private List<Message> messages = new ArrayList<Message>();
	
	@Override
	public void addMessage(Message msg) {
		this.messages.add(msg);
	}

	@Override
	public Message getNewMessage() {
		return null;
	}

	@Override
	public String getUser() {
		return nameUser;
	}

		@Override
	public void addMessages(List<Message> listMsg) {
		this.messages = listMsg;
		
	}

}
