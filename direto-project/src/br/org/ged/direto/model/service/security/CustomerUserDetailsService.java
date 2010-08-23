package br.org.ged.direto.model.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.UsuarioRepository;

@Service("userDetailsService") 
public class CustomerUserDetailsService extends JdbcDaoImpl implements IChangePassword {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*@Autowired
	public CustomerUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}*/
	
	public UserDetails loadUserByUsername(String usuLogin)
                         throws UsernameNotFoundException, DataAccessException {
        Usuario usuario = this.usuarioRepository.selectByLogin(usuLogin);      
		
		
              if(usuario == null) {
                     throw new UsernameNotFoundException(usuLogin + "not found");
               }
               return usuario;
       }
	
	
	public void changePassword(String usuLogin, String usuSenha) {
		
		//UserDetails user = loadUserByUsername(usuLogin);
		String encodedPassword = passwordEncoder.encodePassword(usuSenha, null);
		getJdbcTemplate().update("UPDATE usuario SET UsuSenha = ? WHERE UsuLogin = ?",
			encodedPassword, usuLogin);
	}

}
