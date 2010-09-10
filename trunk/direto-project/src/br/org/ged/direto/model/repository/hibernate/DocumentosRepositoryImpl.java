package br.org.ged.direto.model.repository.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.DocumentosRepository;

@Repository("documentoRepository")
public class DocumentosRepositoryImpl implements DocumentosRepository, MessageSourceAware {

	private HibernateTemplate hibernateTemplate;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Documento> listByLimited (){
		
		messages.getMessage("limitByPage");
		
		/*final Integer idCarteira = new Integer(2);
		
		List<Documento> list = new ArrayList<Documento>();
		//
		
		Documento d = (Documento) hibernateTemplate.execute(new HibernateCallback<Documento>() {
			@Override
			public Documento doInHibernate(org.hibernate.Session session)
					throws HibernateException, SQLException {
				return (Documento) session.createQuery(
				"from Documento where IdCarteira = :idCarteira")
				.setParameter("idCarteira", idCarteira).uniqueResult();
			}
		});
		
		
		list.add(d);
		
		System.out.println(list.size());
		
		return list;*/
		
		return (List<Documento>) hibernateTemplate.find("from "
				+ Documento.class.getName());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DataUtils> listDocumentsFromAccount(Integer idCarteira) {
		
		List<DataUtils> list = new ArrayList();
		
		return null;
	}

	@Override
	public void sendDocument(Carteira[] carteira, Documento documento) {
		// TODO Auto-generated method stub

	}

	@Override
	public Documento selectByIdCarteira(final Integer idCarteira) {
		
		/*Integer idDocumento = new Integer(2);
		
		Documento d = new Documento(idDocumento, null, '1', null);
		
		System.out.println(d.getStatus());
		
		return d;*/
		
		return (Documento) hibernateTemplate.execute(new HibernateCallback<Documento>() {
			@Override
			public Documento doInHibernate(org.hibernate.Session session)
					throws HibernateException, SQLException {
				return (Documento) session.createQuery(
				"from Documento where idDocumento = :idDocumento")
				.setParameter("idDocumento", idCarteira).uniqueResult();
			}
		});
	}


}
