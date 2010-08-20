package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.repository.CarteiraRepository;

//This will make easier to autowired
@Repository("carteiraRepository")
// Default is read only
@Transactional
public class CarteiraRepositoryImpl implements CarteiraRepository {

	
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Carteira> getAll(Carteira carteira) {
		return (List<Carteira>) hibernateTemplate.find("from "
				+ PstGrad.class.getName());
	}

	@Override
	public void save(Carteira carteira) {
		hibernateTemplate.saveOrUpdate(carteira);

	}

}
