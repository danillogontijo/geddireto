package br.org.ged.direto.model.service;

public interface NotificacaoService {

	public void save(int idDocumentoDetalhes, String texto);
	public boolean deleteAllFromDocument(int idDocumento);
}
