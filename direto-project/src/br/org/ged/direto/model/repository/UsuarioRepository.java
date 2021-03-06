package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Usuario;

public interface UsuarioRepository {
	
	public void save(Usuario user);
	public List<Usuario> getAll(Usuario usuario);
	public Usuario selectById(Integer idUsuario);
	public Usuario selectByLogin(String login);
	public List<DataUtils> listActivedContas (String usuLogin);
	public void changePassword(String usuLogin, String usuSenha);
	public String whoUser(int userid);
	public boolean checkIfUserIsDuplicate(String usuLogin, int idUsuario);
	
	//public Usuario doLogin(String login, String senha) throws Exception;

}
