package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Funcao;

public interface FuncaoService {
	
	public List<Funcao> getAll(Funcao funcao);
	public Funcao getPstGradById(Integer id);

}
