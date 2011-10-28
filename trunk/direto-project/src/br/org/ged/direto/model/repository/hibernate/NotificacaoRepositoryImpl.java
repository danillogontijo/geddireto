package br.org.ged.direto.model.repository.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Notificacao;
import br.org.ged.direto.model.repository.NotificacaoRepository;

@Repository("notificacaoRepository")
public class NotificacaoRepositoryImpl extends BaseRepositoryImpl implements NotificacaoRepository {

	@Override
	public void save(Notificacao notificacao) {
		hibernateTemplate.save(notificacao);
	}

	@Override
	public void deleteAllFromDocument(int idDocumento) {
		String sQuery = "DELETE from Notificacao as n WHERE n.documento.idDocumento="+idDocumento;

		Query query = getSession().createQuery(sQuery);
		query.executeUpdate();
	}

}
