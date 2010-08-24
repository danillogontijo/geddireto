package br.org.ged.direto.model.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Usuario;

public interface UsuarioService {
	
	public List<DataUtils> listActivedContas (String usuLogin);
	
	//@PreAuthorize("ROLE_USER")
	public void save(Usuario usuario) throws Exception;
	public List<Usuario> getAll(Usuario usuario);
	public Usuario selectById(Integer idUsuario);
	public Usuario selectByLogin(String usuLogin);
	
	//@PreAuthorize("ROLE_ADMIN")
	public boolean validateUser(String usuLogin);
	
	//@PreAuthorize("hasRole('ROLE_USER')")
	@PreAuthorize("#usuLogin == principal.usuLogin")
	public void changePassword(String usuLogin, String usuSenha);

}
