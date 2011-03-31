package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;

public interface ContaService {
	
	public void saveOrUpdate(Conta conta);
	public void add(int idUsuario, int ativado, int idCarteira, int contaPrincipal);
	public void tieUsers(List<Usuario> usuarios);
	public void deleteAccount(int idConta);
	public void deleteAccount(int idUsuario, int idCarteira);
	public void updateAccount(int idConta, int idUsuario, int ativado, int idCarteira, int contaPrincipal);
	public void updateAccount(int idUsuario, int idCarteira);
	public void updateAccount(int idConta, int idCarteira, boolean isPrincipal);
	public Conta getAccount(int idConta);
	public Conta getAccount(int idUsuario, int idCarteira);
	//public List<Conta> getAll(Usuario usuario);

}
