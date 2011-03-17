package br.org.direto.webchat.antigo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UsuarioChatImpl implements UsuarioChat {

	private String name = null;
	
	private HttpServletResponse res = null;
	private HttpServletRequest request = null;
	
	private boolean connected = true;  
	
	private ArrayList<MensagemChat> messages = new ArrayList<MensagemChat>();
	
	private static Hashtable<String,UsuarioChat> users = new Hashtable<String,UsuarioChat>(); 
	
	public UsuarioChatImpl(String name) {
		this.name = name;
		if (UsuarioChatImpl.getByName(getName()) != null) {
			throw new IllegalArgumentException("Usuario Invalido");
		} else {
			UsuarioChatImpl.addUser(this);
		}
	}
	
	@Override
	public void addMessage(MensagemChat msg) {
		messages.add(msg);
		synchronized (messages) {
			// sinaliza para quem está esperando
			messages.notifyAll();
		}		
	}

	@Override
	public void checkSession() {
		try {
			if (getRequest().getSession() == null
				|| getRequest().getSession().getAttribute("user") == null) {
				this.disconnect();
			}
		} catch (IllegalStateException isex) {
			this.disconnect();
		}		
	}

	@Override
	public void disconnect() {
		 connected = false; 		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public synchronized MensagemChat getNewMessage() {
		if (messages.isEmpty()) {
			try {
				synchronized (messages) {
					messages.wait(5 * 1000);
				}
			} catch (InterruptedException e) {
				// desconecta
				//this.disconnect();
				// ele desconectou
				//return (null);
			}
			// se deu timeout
			if (messages.isEmpty()) {
				return (null);
			}
		}
		return (MensagemChat) (messages.remove(0));
	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return res;
	}

	@Override
	public boolean isConnected() {
		 return connected;
	}

	@Override
	public void setRequest(HttpServletRequest req) {
		this.request = req;		
	}

	@Override
	public void setResponse(HttpServletResponse res) {
		this.res = res;
	}

	@Override
	public void showMessage(MensagemChat msg) throws IOException {
		
		res.getWriter().print(msg.getHTMLCode());
		
		System.out.println(msg.getHTMLCode());
		
	}
	
	public static void addUser(UsuarioChat user) {
		synchronized (users) {
			users.put(user.getName(), user);
		}
	}
	
	public static void removeUser(UsuarioChat user) throws IOException {
		synchronized (users) {
			// se não existir
			if (!users.containsValue(user)) {
				return;
			}
			// remove
			users.remove(user.getName());
			// desconecta
			user.disconnect();
			// notifica da saída
			synchronized (user) {
				user.notifyAll();
				// envia uma mensagem para a sala
				MensagemChat msg =
					new MensagemGenericaChat(user,
						"deu no pé<script>removeUser('"
							+ user.getName()
							+ "');</script>");
				
				UsuarioChatImpl.sendMessage(msg);
			}
		}
	}
	
	public static UsuarioChat getByName(String name) {
		return (UsuarioChat) users.get(name);
	}
	
	public static Hashtable<String, UsuarioChat> getUsers() {
		synchronized (users) {
			return (new Hashtable<String, UsuarioChat>(users));
		}
	}
	
	public static void sendMessage(MensagemChat msg) throws IOException{
		// se a mensagem tem um destinatario especifico
		if (msg != null && msg.getTo() != null) {
			msg.getTo().addMessage(msg);
			msg.getFrom().addMessage(msg);
			return;
		}
		
		// caso contrario envia para todos
		Map<String,UsuarioChat> usersList = getUsers();
		Iterator<String> i =  usersList.keySet().iterator();
		while (i.hasNext()) {
			String username = i.next();
			UsuarioChat user = ((UsuarioChat) usersList.get(username));
			try {
				user.addMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
				removeUser(user);
			}
		}
	}


}
