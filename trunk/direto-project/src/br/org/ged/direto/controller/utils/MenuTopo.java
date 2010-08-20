package br.org.ged.direto.controller.utils;

import org.springframework.security.access.annotation.Secured;

//@Component
public class MenuTopo {
	
	private final String admin = "Admin";
	private final String alterarSenha = "Alterar Senha";
	private final String sugestoes = "Sugestões";
	private final String passarConta = "Passar Conta";
	private final String dadosCadastrais = "Dados Cadastrais";
	private final String configuracoes = "Configuracoes";
	
	//@Secured("ROLE_ADMIN")
	public String getAdmin() {
		return admin;
	}
	
	public String getAlterarSenha() {
		return alterarSenha;
	}
	
	public String getSugestoes() {
		return sugestoes;
	}
	
	public String getPassarConta() {
		return passarConta;
	}
	
	public String getDadosCadastrais() {
		return dadosCadastrais;
	}
	
	public String getConfiguracoes() {
		return configuracoes;
	}
	
	
	

}
