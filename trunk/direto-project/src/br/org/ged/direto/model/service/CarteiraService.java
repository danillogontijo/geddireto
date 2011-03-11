package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Carteira;

public interface CarteiraService {
	
	public void save(Carteira carteira);
	public List<Carteira> getAll(Carteira carteira);
	public Carteira selectById(Integer primaryKey);

}
