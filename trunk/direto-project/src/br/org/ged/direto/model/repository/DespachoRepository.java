package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Despacho;

public interface DespachoRepository {

	public void save(Despacho despacho);
	public List<Despacho> getDespachoByDocumento(Integer idDocumentoDetalhes);
	public List<Despacho> getDespachoByUsuario(Integer idUsuario);
	public Despacho selectDespacho(int idDespacho);
}
