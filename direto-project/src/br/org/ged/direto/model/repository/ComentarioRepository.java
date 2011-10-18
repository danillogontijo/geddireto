package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Comentario;

public interface ComentarioRepository {
	
	public void save(Comentario comentario);
	public List<Comentario> getAllComments();

}
