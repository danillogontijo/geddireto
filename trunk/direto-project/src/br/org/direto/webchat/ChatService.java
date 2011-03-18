package br.org.direto.webchat;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.org.direto.webchat.antigo.MensagemChat;
import br.org.direto.webchat.antigo.UsuarioChat;

public interface ChatService {
	
	/*public Hashtable<String,UsuarioChat> getAllUsers();
	public void showMessagePrivateUser(UsuarioChat user, MensagemChat msg);
	
	public HttpServletRequest getHttpServletRequest();
	public void setHttpServletRequest(HttpServletRequest request);
	
	public HttpServletResponse getHttpServletResponse();
	public void setHttpServletResponse(HttpServletResponse response);*/
	
	public UserChat checkNewMessageFromUser(UserChat user);
	public void sendNewMessage(Message msg);
	public Message getMessage(UserChat from);
	public UserChat getUserChat(String name);

}
