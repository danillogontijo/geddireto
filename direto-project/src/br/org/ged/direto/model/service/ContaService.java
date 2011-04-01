package br.org.ged.direto.model.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;

public interface ContaService {
	
	@PreAuthorize("(hasRole('ROLE_ADMIN')) and (isAuthenticated())")
	public void saveOrUpdate(Conta conta);
	
	@PreAuthorize("(hasRole('ROLE_ADMIN')) and (isAuthenticated())")
	public void add(int idUsuario, int ativado, int idCarteira, int contaPrincipal);
	
	@PreAuthorize("(hasRole('ROLE_ADMIN')) and (isAuthenticated())")
	public void tieUsers(List<Usuario> usuarios);
	
	@PreAuthorize("(hasRole('ROLE_ADMIN')) and (isAuthenticated())")
	public void deleteAccount(int idConta);
	
	@PreAuthorize("(hasRole('ROLE_ADMIN')) and (isAuthenticated())")
	public void deleteAccount(int idUsuario, int idCarteira);
	
	@PreAuthorize("(hasRole('ROLE_ADMIN')) and (isAuthenticated())")
	public void updateAccount(int idConta, int idUsuario, int ativado, int idCarteira, int contaPrincipal);
	
	@PreAuthorize("(hasRole('ROLE_ADMIN')) and (isAuthenticated())")
	public void updateAccount(int idUsuario, int idCarteira);
	
	@PreAuthorize("(hasRole('ROLE_ADMIN')) and (isAuthenticated())")
	public void updateAccount(int idConta, int idCarteira, boolean isPrincipal);
	
	public Conta getAccount(int idConta);
	
	public Conta getAccount(int idUsuario, int idCarteira);
	
	
	//public List<Conta> getAll(Usuario usuario);

}
