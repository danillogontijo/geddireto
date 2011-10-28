package br.org.ged.direto.model.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.org.ged.direto.model.entity.Usuario;

@Component
public class BaseRepositoryImpl implements MessageSourceAware {
	
	protected HibernateTemplate hibernateTemplate;
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	@Autowired
    protected SessionFactory sessionFactory;
    
	@Autowired
	protected void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Este método utiliza o load do hibernate template, 
	 * usado somente quando tem-se certeza que que o id existe no BD
	 * @param clazz
	 * @param id
	 * @return void
	 */
	protected Object find(Class clazz, int id){
		return hibernateTemplate.load(clazz, id);
	}
	
	/**
	 * Este método retorna o objeto do usuario atualmente logado 
	 * @return Usuario
	 */
	protected Usuario getAutenticatedUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (Usuario)auth.getPrincipal();
	}
}
