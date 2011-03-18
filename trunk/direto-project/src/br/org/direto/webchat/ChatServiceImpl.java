package br.org.direto.webchat;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;


@RemoteProxy(name = "chatJS")
public class ChatServiceImpl implements ChatService{

	public static Map<String,List<Message>> messages = new Hashtable<String,List<Message>>();
	//private Message message = new Messa;
	
	//public static List<Message> messages = new ArrayList<Message>();
	
	@Override
	@RemoteMethod
	public synchronized UserChat checkNewMessageFromUser(UserChat user) {
		
				
		if (messages.isEmpty()) {
			try {
				synchronized (messages) {
					messages.wait(5 * 1000);
				}
			} catch (InterruptedException e) {
				return user;
			}
			
		}else{	
			
		
			if (messages.containsKey(user.getNameUser())){
				List<Message> listMsg = messages.remove(user.getNameUser());
				user.setMessages(listMsg);
				
				System.out.println((user.getMessages().get(0)).getHTMLCode());
				
			}
			
			
			return user;
			
			
		}
		
	
		return user;
	}

	@Override
	@RemoteMethod
	public synchronized void sendNewMessage(Message msg) {
		
		String userTo = msg.getTo().getNameUser();
		
		System.out.println(userTo);
		System.out.println(msg.getHTMLCode());
		
			if (messages.get(userTo) != null){
				(messages.get(userTo)).add(msg);
				return;
			}	
			
		List<Message> msgs = new ArrayList<Message>();
		msgs.add(msg);
		
		messages.put(userTo, msgs);
		
		System.out.println(messages.size()+"====tamanho");
		
	}

	@Override
	@RemoteMethod
	public Message getMessage(UserChat from) {
		
		System.out.println(from.getNameUser());
		
		Message msg = new Message();
		msg.setFrom(from);
		return msg;
	}
	
	@RemoteMethod
	public Message getMessage() {
		return new Message();
	}

	@Override
	@RemoteMethod
	public UserChat getUserChat(String name) {
		UserChat user = new UserChat();
		user.setNameUser(name);
		
		return user;
	}
	
	
	
	

}
