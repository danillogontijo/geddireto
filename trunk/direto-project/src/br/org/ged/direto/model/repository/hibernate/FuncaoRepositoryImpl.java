package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Funcao;
import br.org.ged.direto.model.repository.FuncaoRepository;

@Repository("funcaoRepository")
public class FuncaoRepositoryImpl implements FuncaoRepository {

	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Funcao> getAll() {
		return (List<Funcao>) hibernateTemplate.find("from "
				+ Funcao.class.getName());
	}

	@Override
	public Funcao getFuncaoByPkId(Integer pkId) {
		return hibernateTemplate.get(Funcao.class, pkId);
	}

}