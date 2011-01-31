package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	public List<Funcao> getAll(Funcao funcao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Funcao getPstGradById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
