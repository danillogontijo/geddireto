package br.org.ged.direto.model.service;


import java.util.Collection;
import java.util.LinkedList;


import br.org.ged.direto.model.entity.Pastas;

public interface PastasService {
	
	public Collection<Pastas> getAll();
	public Pastas getPastaById(Integer id);

}
