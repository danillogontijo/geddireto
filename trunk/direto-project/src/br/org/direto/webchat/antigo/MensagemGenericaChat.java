package br.org.direto.webchat.antigo;


public class MensagemGenericaChat implements MensagemChat {

	private UsuarioChat from = null;
	private UsuarioChat to = null;
	private String text = null;
	
	/* Apenas caso queira mandar mensagem para todos os usuarios*/
	public MensagemGenericaChat(UsuarioChat from,String texto) {
		this.from = from;
		this.text = texto;
	}
	
	
	public MensagemGenericaChat(UsuarioChat from, UsuarioChat to, String texto) {
		this(from,texto);
		/*this.from = from;
		this.text = texto;*/
		this.to = to;
	}
	
	@Override
	public UsuarioChat getFrom() {
		return (from);
	}

	@Override
	public String getHTMLCode() {
		return (" - " + getFrom().getName() + (getTo()!=null? " fala para " + getTo().getName() : "") + ": " + text + "<br>");
	}

	@Override
	public UsuarioChat getTo() {
		return (to);
	}

}
