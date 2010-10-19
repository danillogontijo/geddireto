package br.org.ged.direto.model.repository;


import java.util.Collection;

import br.org.ged.direto.model.entity.Pastas;

public interface PastasRepository {

	public Collection<Pastas> getAll();
	public Pastas getPastaById(Integer id);
	
}
