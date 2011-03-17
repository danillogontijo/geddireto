package br.org.direto.webchat;

public interface Message {
	
	public UserChat getFrom();
	public UserChat getTo();
	public String getHTMLCode();

}
