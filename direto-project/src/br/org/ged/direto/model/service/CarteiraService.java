package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Carteira;

public interface CarteiraService {
	
	public void save(Carteira carteira);
	public void save(String descricao, String abreviatura, int idFuncao, int idSecao, int idOM);
	public List<Carteira> getAll();
	public Carteira selectById(Integer primaryKey);

}
