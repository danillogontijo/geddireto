package br.org.ged.direto.model.service;

import java.util.List;
import java.util.Map;

import br.org.ged.direto.model.entity.TipoDocumento;

public interface TipoDocumentoService {

	public Map<Integer,String> getAll();
	public List<TipoDocumento> getAllList();
	public TipoDocumento getTipoDocumento(int idTipoDocumento);
	
}
