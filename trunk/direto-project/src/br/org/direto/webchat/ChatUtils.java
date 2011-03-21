package br.org.direto.webchat;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ChatUtils {
	
	private Map<Integer,List<Message>> messages = new Hashtable<Integer,List<Message>>();
	
	private Map<Integer,UserChat> users = new Hashtable<Integer,UserChat>();

	public Map<Integer, List<Message>> getMessages() {
		return messages;
	}

	public Map<Integer, UserChat> getUsers() {
		return users;
	}
	
	

}
