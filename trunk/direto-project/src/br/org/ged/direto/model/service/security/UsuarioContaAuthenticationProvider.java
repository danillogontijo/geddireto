package br.org.ged.direto.model.service.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import br.org.ged.direto.model.entity.Usuario;

public class UsuarioContaAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Override
	public boolean supports(Class<? extends Object> authentication) {
        return (UsuarioContaAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		Usuario usuarioDetails = (Usuario)userDetails;
		
		UsuarioContaAuthenticationToken contaUsuarioToken = 
			(UsuarioContaAuthenticationToken) authentication;
		
		usuarioDetails.setContaAtual(contaUsuarioToken.getRequestUsuarioConta());
		
		super.additionalAuthenticationChecks(usuarioDetails, contaUsuarioToken);
		
		System.out.println("UsuarioContaAuthenticationProvider:" + contaUsuarioToken.getRequestUsuarioConta());
		
		if(contaUsuarioToken.getRequestUsuarioConta() == null) {
			throw new BadCredentialsException(messages.getMessage(
                    "UsuarioContaAuthenticationProvider.missingUsuarioConta", "Missing request ContaUsuario"),
                    isIncludeDetailsObject() ? userDetails : null);
		}
		
		// verifique se a conta esta ativa
		/*if(!contaUsuarioToken.getRequestUsuarioConta().equals(calculateExpectedContaUsuario(contaUsuarioToken))) {
			throw new BadCredentialsException(messages.getMessage(
                    "UsuarioContaAuthenticationProvider.badUsuarioConta", "Conta Usuario Invalida."),
                    isIncludeDetailsObject() ? userDetails : null);			
		}*/
	}

	/*private String calculateExpectedContaUsuario(UsuarioContaAuthenticationToken contaUsuarioToken) {
		//return contaUsuarioToken.getPrincipal() + "|+|" + contaUsuarioToken.getCredentials();
		return contaUsuarioToken.getRequestUsuarioConta();
	}*/
	
	
}
