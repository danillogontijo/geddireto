package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Anotacao;

public interface AnotacaoRepository {

	public void save(Anotacao anotacao);
	public List<Anotacao> getAnotacaoByDocumento(Integer idDocumentoDetalhes);
	public List<Anotacao> getAnotacaoByUsuario(Integer idUsuario);
}
