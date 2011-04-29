package br.org.ged.direto.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Notificacao;
import br.org.ged.direto.model.repository.NotificacaoRepository;

@Repository("notificacaoRepository")
public class NotificacaoRepositoryImpl implements NotificacaoRepository {

private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public void save(Notificacao notificacao) {
		hibernateTemplate.save(notificacao);
	}

}
