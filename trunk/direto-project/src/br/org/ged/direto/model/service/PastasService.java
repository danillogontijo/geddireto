package br.org.ged.direto.model.service;

import java.util.Collection;
import br.org.ged.direto.model.entity.Pastas;

public interface PastasService {
	
	public Collection<Pastas> getAll();
	public Pastas getPastaById(Integer id);
	public Collection<Pastas> pastasComNrDocumentos(int idCarteira);

}
