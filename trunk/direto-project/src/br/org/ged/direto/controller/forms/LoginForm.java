package br.org.ged.direto.controller.forms;

import org.springframework.stereotype.Component;

@Component
public class LoginForm {
	
	private String j_username;
	private String j_password;
	
	public String getJ_username() {
		return j_username;
	}
	public void setJ_username(String jUsername) {
		j_username = jUsername;
	}
	public String getJ_password() {
		return j_password;
	}
	public void setJ_password(String jPassword) {
		j_password = jPassword;
	}
}
