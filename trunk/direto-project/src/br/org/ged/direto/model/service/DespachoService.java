package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.entity.Despacho;

public interface DespachoService {

	public void save(Despacho despacho);
	public List<Despacho> getDespachoByDocumento(Integer idDocumentoDetalhes);
	public List<Despacho> getDespachoByUsuario(Integer idUsuario);
}
