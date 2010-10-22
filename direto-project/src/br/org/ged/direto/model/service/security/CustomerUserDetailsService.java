package br.org.ged.direto.model.service.security;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.UsuarioService;

public class CustomerUserDetailsService extends JdbcDaoImpl implements
		IChangePassword {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String usuLogin)
			throws UsernameNotFoundException, DataAccessException {

		/*System.out.println(usuLogin + ": Logando...");

		Usuario usuario = this.usuarioService.selectByLogin(usuLogin);

		if (usuario == null) {
			throw new UsernameNotFoundException(usuLogin + "not found");
		}

		return usuario;*/
		
		Usuario user = this.usuarioService.selectByLogin(usuLogin);
		
		if (user == null) {
			throw new UsernameNotFoundException(usuLogin + "not found");
		}
		
		//UserDetails user = users.get(0); // contains no GrantedAuthority[]

        /*Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        if (super.getEnableAuthorities()) {
            dbAuthsSet.addAll(loadUserAuthorities(user.getAuthorities()));
        }
*/  
        /*List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);*/
		
		List<GrantedAuthority> dbAuths = (List<GrantedAuthority>) user.getAuthorities();

        addCustomAuthorities(user.getUsername(), dbAuths);

        if (dbAuths.size() == 0) {
            throw new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.noAuthority",
                            new Object[] {usuLogin}, "User {0} has no GrantedAuthority"), usuLogin);
        }

        //return user;
        //super.
        
        return createUserDetails(usuLogin, user, dbAuths);

        //return usuario;
	}
	
	@Override
	protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
            List<GrantedAuthority> combinedAuthorities) {
        
		//String returnUsername = userFromUserQuery.getUsername();
		
		Usuario u = this.usuarioService.selectByLogin(username);
		
		System.out.println(u.toString());

        return u;
    }

	public void changePassword(String usuLogin, String usuSenha) {

		String encodedPassword = passwordEncoder.encodePassword(usuSenha, null);
		getJdbcTemplate().update(
				"UPDATE usuario SET UsuSenha = ? WHERE UsuLogin = ?",
				encodedPassword, usuLogin);
	}

}
