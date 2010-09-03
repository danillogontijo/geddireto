package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Pastas;

public interface PastasRepository {

	public List<Pastas> getAll();
	public Pastas getPastaById(Integer id);
	
}
