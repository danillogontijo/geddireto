package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.repository.PastasRepository;

@Repository("pastasRepository")
public class PastasRepositoryImpl implements PastasRepository {

	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pastas> getAll() {
		return (List<Pastas>) hibernateTemplate.find("from "
				+ Pastas.class.getName() + " order by idPasta asc");
	}

	@Override
	public Pastas getPastaById(Integer id) {
		return hibernateTemplate.get(Pastas.class, id);
	}

}
