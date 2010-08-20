package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.repository.PstGradRepository;

//This will make easier to autowired
@Repository("pstgradRepository")
// Default is read only
@Transactional
public class PstGradRepositoryImpl implements PstGradRepository {

	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public PstGrad getPstGradById(Integer id) {
		return hibernateTemplate.get(PstGrad.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PstGrad> getAll() {
		return (List<PstGrad>) hibernateTemplate.find("from "
				+ PstGrad.class.getName());
	}

}
