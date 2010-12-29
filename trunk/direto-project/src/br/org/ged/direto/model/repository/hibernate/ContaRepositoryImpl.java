package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.ContaRepository;

//This will make easier to autowired
@Repository("contaRepository")
// Default is read only
@Transactional
public class ContaRepositoryImpl implements ContaRepository {
	
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void add(Conta conta) {
		hibernateTemplate.saveOrUpdate(conta);

	}

	@Override
	public void tieUsers(List<Usuario> usuarios) {
		// TODO Auto-generated method stub

	}
	
	public List<Usuario> listarUsuariosPorConta(Integer idCarteira){
		String sql = "FROM Conta c inner join c.carteira carteira WHERE carteira.idCarteira = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		query.setInteger(0, idCarteira);
		
		return null;
	}

	
}
