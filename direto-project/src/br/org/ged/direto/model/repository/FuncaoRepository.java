package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Funcao;

public interface FuncaoRepository {
	
	public List<Funcao> getAll(Funcao funcao);
	public Funcao getPstGradById(Integer id);


}
