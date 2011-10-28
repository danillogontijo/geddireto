package br.org.ged.direto.model.repository;

import br.org.ged.direto.model.entity.Notificacao;

public interface NotificacaoRepository {

	public void save(Notificacao notificacao);
	public void deleteAllFromDocument(int idDocumento);
}
