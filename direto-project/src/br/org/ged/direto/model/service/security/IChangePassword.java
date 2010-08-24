package br.org.ged.direto.model.service.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IChangePassword extends UserDetailsService {

	//@PreAuthorize("hasRole('ROLE_USER')")
	void changePassword(String usuLogin, String usuSenha);

}
