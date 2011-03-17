package br.org.direto.webchat.antigo;


public interface MensagemChat {

	public UsuarioChat getFrom();
	public UsuarioChat getTo();
	public String getHTMLCode();
	
}
