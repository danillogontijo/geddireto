package br.org.ged.direto.model.service;

import java.text.ParseException;
import java.util.List;

import br.org.ged.direto.model.entity.Anotacao;

public interface AnotacaoService {
	
	public void save(int idDocumentoDetalhes, String anotacao);
	public List<Anotacao> getAnotacaoByDocumento(Integer idDocumentoDetalhes);
	public List<Anotacao> getAnotacaoAfterDate(Integer idDocumentoDetalhes, String date_pt_br) throws ParseException;
	public List<Anotacao> getAnotacaoByUsuario(Integer idUsuario);

}
