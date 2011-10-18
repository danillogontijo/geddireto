package br.org.ged.direto.model.service;

import java.util.List;

import br.org.direto.util.DataUtils;

public interface ComentarioService {
	
	public void save(String comentario);
	public List<DataUtils> showAllComments();

}
