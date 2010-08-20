package br.org.ged.direto.model.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.org.ged.direto.model.entity.User;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.UsuarioRepository;

@Service("userDetailsService") 
public class HibernateUserDetailsService implements UserDetailsService {
    
	private UsuarioRepository usuarioRepository;

	@Autowired
	public HibernateUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String usuLogin)
                         throws UsernameNotFoundException, DataAccessException {
        Usuario usuario = this.usuarioRepository.selectByLogin(usuLogin);      
		
		
              if(usuario == null) {
                     throw new UsernameNotFoundException(usuLogin + "not found");
               }
               return usuario;
       }
}
