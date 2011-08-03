package br.org.ged.direto.controller.forms;

import org.springframework.stereotype.Component;

@Component
public class UsuarioForm {
	
	private String usuNome;
	private String usuNGuerra;
	private String usuIdt;
	private Integer usu_pstGrad;
	private String usuSenha;
	
	private String repeatedPassword;
	
	public String getUsuSenha() {
		return usuSenha;
	}
	public void setUsuSenha(String usuSenha) {
		this.usuSenha = usuSenha;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	public String getUsuNome() {
		return usuNome;
	}
	public void setUsuNome(String usuNome) {
		this.usuNome = usuNome;
	}
	public String getUsuNGuerra() {
		return usuNGuerra;
	}
	public void setUsuNGuerra(String usuNGuerra) {
		this.usuNGuerra = usuNGuerra;
	}
	public String getUsuIdt() {
		return usuIdt;
	}
	public void setUsuIdt(String usuIdt) {
		this.usuIdt = usuIdt;
	}
	public Integer getUsu_pstGrad() {
		return usu_pstGrad;
	}
	public void setUsu_pstGrad(Integer usu_pstGrad) {
		this.usu_pstGrad = usu_pstGrad;
	}
	
	

}
