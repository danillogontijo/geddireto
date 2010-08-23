package br.org.ged.direto.model.service.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IChangePassword extends UserDetailsService {

	void changePassword(String usuLogin, String usuSenha);

}
