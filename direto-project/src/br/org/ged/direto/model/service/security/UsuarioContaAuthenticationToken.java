package br.org.ged.direto.model.service.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UsuarioContaAuthenticationToken extends UsernamePasswordAuthenticationToken {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3427564689306907683L;
	private String requestUsuarioConta;
	
	public UsuarioContaAuthenticationToken(String principal,
			String credentials, String usuarioConta) {
		super(principal, credentials);
		this.requestUsuarioConta = usuarioConta;
		System.out.println("Autenticando UsuarioContaAuthenticationToken:" + usuarioConta);
	}

	public String getRequestUsuarioConta() {
		return requestUsuarioConta;
	}

	public void setRequestUsuarioConta(String requestUsuarioConta) {
		this.requestUsuarioConta = requestUsuarioConta;
	}
	
	
	
}
