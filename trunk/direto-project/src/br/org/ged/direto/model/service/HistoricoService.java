package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Historico;

public interface HistoricoService {
	
	public void save(int idDocumentoDetalhes, String txtHistorico);
	public void save(Historico historico);
	public List<Historico> getHistoricoByDocumento(Integer idDocumentoDetalhes);
	public List<Historico> getHistoricoByUsuario(Integer idUsuario);
	public List<Historico> getHistoricoByCarteira(Integer idCarteira);

}
