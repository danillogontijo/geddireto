package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Anotacao;

public interface AnotacaoRepository {

	public Integer save(Anotacao anotacao);
	public List<Anotacao> getAnotacaoByDocumento(Integer idDocumentoDetalhes);
	public List<Anotacao> getAnotacaoByUsuario(Integer idUsuario);
	//public List<Anotacao> getAnotacaoAfterDate(Integer idDocumentoDetalhes, String date_pt_br);
	public Anotacao get(int idAnotacao);
}
