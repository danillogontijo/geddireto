package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;


public interface ContaRepository {
	
	public void add(Conta conta);
	public void tieUsers(List<Usuario> usuarios);
	//public List<Conta> getAll(Usuario usuario);

}
