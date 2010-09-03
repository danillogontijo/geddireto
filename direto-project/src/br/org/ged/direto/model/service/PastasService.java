package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Pastas;

public interface PastasService {
	
	public List<Pastas> getAll();
	public Pastas getPastaById(Integer id);

}
