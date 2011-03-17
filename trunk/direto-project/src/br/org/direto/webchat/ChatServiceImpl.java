package br.org.direto.webchat;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.org.direto.webchat.antigo.UsuarioChat;

public class ChatServiceImpl implements ChatService{

	public static Map<String,List<Message>> messages = new Hashtable<String,List<Message>>();
	
	//public static List<Message> messages = new ArrayList<Message>();
	
	@Override
	public synchronized UserChat checkNewMessageFromUser(UserChat user) {
		synchronized (user) {
			
			/*for(Message message : messages){
			
				if ((message.getTo().getUser()).equals(user.getUser())){
					user.addMessage(message);
				}
			
			}*/
			
			List<Message> listMsg = messages.remove(user.getUser());
			user.addMessages(listMsg);
			
		}
		
		
		return user;
	}

	@Override
	public synchronized void sendNewMessage(UserChat user, Message msg) {
		
	//	messages.put(user.getUser(), user.g)
		
	}
	
	
	
	

}
