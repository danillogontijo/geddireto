package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.OM;
import br.org.ged.direto.model.repository.OMRepository;

@Repository("omRepository")
public class OMRepositoryImpl implements OMRepository {

	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OM> getAll() {
		return (List<OM>) hibernateTemplate.find("from "
				+ OM.class.getName());
	}

	@Override
	public OM getOMByPkId(Integer pkId) {
		return hibernateTemplate.get(OM.class, pkId);
	}

}
