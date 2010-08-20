package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;

public interface ContaService {
	
	public void add(Conta conta);
	public void tieUsers(List<Usuario> usuarios);
	//public List<Conta> getAll(Usuario usuario);

}
