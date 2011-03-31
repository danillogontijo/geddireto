package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;


public interface ContaRepository {
	
	public void saveOrUpdate(Conta conta);
	public void tieUsers(List<Usuario> usuarios);
	public void deleteAccount(int idConta);
	public void deleteAccount(int idUsuario, int idCarteira);
	public void updateAccount(Conta conta);
	public void updateAccount(int idUsuario, int idCarteira);
	public Conta getAccount(int idConta);
	public Conta getAccount(int idUsuario, int idCarteira);

}
