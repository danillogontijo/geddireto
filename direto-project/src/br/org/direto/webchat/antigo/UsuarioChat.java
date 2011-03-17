package br.org.direto.webchat.antigo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface UsuarioChat {
	
	public String getName();
	
	boolean isConnected();
	public void disconnect();
	public void checkSession();
	
	public HttpServletResponse getResponse();
	public HttpServletRequest getRequest();
	public void setResponse(HttpServletResponse res);
	public void setRequest(HttpServletRequest req);
	
	public void addMessage(MensagemChat msg);
	public MensagemChat getNewMessage();
	public void showMessage(MensagemChat msg) throws IOException;


}
