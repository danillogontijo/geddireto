package br.org.ged.direto.model.service;

import java.util.List;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Usuario;

public interface UsuarioService {
	
	public List<DataUtils> listActivedContas (String usuLogin);
	public void save(Usuario usuario) throws Exception;
	public List<Usuario> getAll(Usuario usuario);
	public Usuario selectById(Integer idUsuario);
	public Usuario selectByLogin(String usuLogin);
	public boolean validateUser(String usuLogin);

}
