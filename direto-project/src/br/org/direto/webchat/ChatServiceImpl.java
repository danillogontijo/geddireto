package br.org.direto.webchat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.context.request.RequestContextHolder;

import org.springframework.web.context.request.ServletRequestAttributes;

import br.org.direto.util.DataTimeUtil;
import br.org.ged.direto.model.entity.Usuario;

@RemoteProxy(name = "chatJS")
public class ChatServiceImpl implements ChatService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4460851119832529260L;

	@Autowired
	private SessionRegistry sessionRegistry;
	
	private Map<Integer,List<Message>> messages;
	
	private Map<Integer,UserChat> users;
	
	private UserChat user;
	
	private List<Message> messagesSession;
	
	ChatUtils chatUtils;
	
	public void setChatUtils(ChatUtils chatUtils) {
		this.chatUtils = chatUtils;
		messages = chatUtils.getMessages();
		users = chatUtils.getUsers();
	}

	private static HttpSession session() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true); // true == permite criar
	}
	
	@RemoteMethod
	public List<Message> messagesSession(){
		return messagesSession;
	}
	
	private int initialStatus(int idUser){
		if (users.containsKey(idUser))
			return users.get(idUser).getStatusUser();
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	@RemoteMethod
	public int start(int idUser,String nameUser){
		
		if(session().getAttribute("messagesSession") == null)
			session().setAttribute("messagesSession", new ArrayList<Message>());

		messagesSession = (List<Message>)(session().getAttribute("messagesSession"));

		int initialStatus = initialStatus(idUser);
		
		addUser(idUser,nameUser,(initialStatus != -1 ? true : false));
		return initialStatus;
	}
	
	private void addUser(int idUser,String nameUser,boolean isStarted){
		if(!isStarted){
			synchronized (users) {
				user = new UserChat();
				user.setIdUser(idUser);
				user.setNameUser(nameUser);
				users.put(idUser, user);
				users.notifyAll();
			}
			return;
		}
		user = users.get(idUser);
	}
	
	private void removeUser(int idUser){
		synchronized (users) {
			if (users.containsKey(idUser))
				users.remove(idUser);
			
			users.notifyAll();
		}	
	}
	
	@RemoteMethod
	public List<Message> checkNewMessage(int idUser){
		
		if (!messages.containsKey(idUser)){
			try {
				synchronized (this.user) {
					
					this.user.wait(10000);
				}
			} catch (InterruptedException e) {
				System.out.println("Deu pau!");
				return null;
			}
		}
		
		List<Message> msgs = null;
		
		if(user.getStatusUser() != 0){
			msgs = (List<Message>) messages.remove(idUser);
			addMessageInSession(msgs);
		}
		return msgs;
	}
	
	private void addMessageInSession(Message msg){
		messagesSession.add(msg);
	}
	
	private void addMessageInSession(List<Message> list){
		if(list == null)
			return;
		for (Message msg : list)
			messagesSession.add(msg);
	}
	
	@RemoteMethod
	public List<UserChat> checkUsers(){
			try {
				synchronized (users) {
					users.wait(10000);
				}
			} catch (InterruptedException e) {
				System.out.println("Check Users deu pau!");
			}
			
			return getAllUsersOn();
	}
	
	private boolean isSessionActive(UserChat user){
		List<Object> principals = sessionRegistry.getAllPrincipals();
		for(Object obj : principals){
			Usuario u = (Usuario)obj;
			if (u.getIdUsuario() == user.getIdUser())
				return true;
		}
		
		return false;
	}
	/**
	 * DATA E HORA DA MENSAGEM QUE FOI ENVIADA
	 * @return String no formato dd-mm-aaaa HH:mm
	 */
	private String getNowDataTime(){
		return DataTimeUtil.getBrazilFormatDataHora(new Date());
	}
	
	@RemoteMethod
	public void send(int idToUser, int idFromUser, String msgHTML){
		UserChat to = null;
		List<Message> listMsgUser = null;
		Message msg = new Message();
		
		msgHTML = "[<i>"+getNowDataTime()+"</i>] " + msgHTML; 
		
		to = users.get(idToUser);
		synchronized (to) {
			msg.setTo(to);
			msg.setFrom(users.get(idFromUser));
			msg.setHTMLCode(msgHTML);
			
				if (!messages.containsKey(idToUser)){
					listMsgUser = new ArrayList<Message>();
					listMsgUser.add(msg);
					messages.put(idToUser, listMsgUser);
				}else{
					listMsgUser = messages.get(idToUser);
					listMsgUser.add(msg);
				}
			//if(to.getStatusUser()!=0)
				to.notifyAll();
		}
		
		addMessageInSession(msg);

	}
	
	@RemoteMethod
	public int checkToUser(int idUser) {
		UserChat userTo = users.get(idUser);
		synchronized (users) {
			if (!isSessionActive(userTo)){
				userTo.setStatusUser(0);
			}
			users.notifyAll();
		}	
		return userTo.getStatusUser();
	}
	
	@RemoteMethod
	public int getIdUserChat(String name) {
		if (!users.containsValue(name))
			return 0;
		return 1;
	}
	
	@RemoteMethod
	public List<UserChat> getAllUsersOn(){
		return new ArrayList<UserChat>(users.values());
	}
	
	@RemoteMethod
	public void changeUserStatus(int statusUser){
		synchronized (users) {
			this.user.setStatusUser(statusUser);
			users.notifyAll();
		}
		//Notifica que o user ficou off e não precisa mais checar se tem msg
		if (statusUser == 0)
			notifyUser();
	}
	
	private void notifyUser(){
		synchronized (user) {
			user.notifyAll();
		}
	}

}
