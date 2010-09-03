package br.org.ged.direto.model.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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

	public UserDetails loadUserByUsername(String usuLogin)
			throws UsernameNotFoundException, DataAccessException {

		System.out.println(usuLogin + ": Logando...");

		Usuario usuario = this.usuarioService.selectByLogin(usuLogin);

		if (usuario == null) {
			throw new UsernameNotFoundException(usuLogin + "not found");
		}

		return usuario;
	}

	public void changePassword(String usuLogin, String usuSenha) {

		String encodedPassword = passwordEncoder.encodePassword(usuSenha, null);
		getJdbcTemplate().update(
				"UPDATE usuario SET UsuSenha = ? WHERE UsuLogin = ?",
				encodedPassword, usuLogin);
	}

}
