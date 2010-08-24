package br.org.ged.direto.model.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.UsuarioRepository;
import br.org.ged.direto.model.service.UsuarioService;

//@Service("userDetailsService") 
//@Component("userDetailsService")
public class CustomerUserDetailsService extends JdbcDaoImpl implements IChangePassword {
	
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	/*public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}*/




	public UserDetails loadUserByUsername(String usuLogin)
                         throws UsernameNotFoundException, DataAccessException {
		
		System.out.println("Logando...");
		
        Usuario usuario = this.usuarioService.selectByLogin(usuLogin);      
       		
              if(usuario == null) {
                     throw new UsernameNotFoundException(usuLogin + "not found");
               }
               return usuario;
       }
	
	

	//@PreAuthorize("hasRole('ROLE_USER')")
	//@PreAuthorize("#usuario.usuLogin == principal.name)")
	//@PreAuthorize("ROLE_USER")
	public void changePassword(String usuLogin, String usuSenha) {
		
		//UserDetails user = loadUserByUsername(usuLogin);
		String encodedPassword = passwordEncoder.encodePassword(usuSenha, null);
		getJdbcTemplate().update("UPDATE usuario SET UsuSenha = ? WHERE UsuLogin = ?",
			encodedPassword, usuLogin);
	}

}
