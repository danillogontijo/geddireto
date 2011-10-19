package br.org.direto.webchat;

import java.io.Serializable;
import java.util.List;

public class UserChat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1741853602802046523L;
	private int idUser;
	private String nameUser;
	private List<Message> messages;
	private int statusUser = 1; //1=online, 0=offline, 2=inativo
	
	public int getStatusUser() {
		return statusUser;
	}
	public void setStatusUser(int statusUser) {
		this.statusUser = statusUser;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
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
