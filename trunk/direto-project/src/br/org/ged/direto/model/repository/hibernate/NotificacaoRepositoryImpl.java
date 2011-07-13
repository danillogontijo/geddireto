package br.org.ged.direto.model.repository.hibernate;

import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Notificacao;
import br.org.ged.direto.model.repository.NotificacaoRepository;

@Repository("notificacaoRepository")
public class NotificacaoRepositoryImpl extends BaseRepositoryImpl implements NotificacaoRepository {

	@Override
	public void save(Notificacao notificacao) {
		hibernateTemplate.save(notificacao);
	}

}
