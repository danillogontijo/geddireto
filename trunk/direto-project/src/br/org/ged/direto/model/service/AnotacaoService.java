package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Anotacao;

public interface AnotacaoService {
	
	public void save(Anotacao anotacao);
	public List<Anotacao> getAnotacaoByDocumento(Integer idDocumentoDetalhes);
	public List<Anotacao> getAnotacaoByUsuario(Integer idUsuario);

}
