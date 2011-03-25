package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Carteira;


public interface CarteiraRepository {
	
	public void save(Carteira carteira);
	public List<Carteira> getAll();
	public Carteira selectById(Integer primaryKey);
}
