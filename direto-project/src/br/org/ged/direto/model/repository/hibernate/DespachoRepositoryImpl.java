package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.entity.Despacho;
import br.org.ged.direto.model.repository.DespachoRepository;

@Repository("despachoRepository")
public class DespachoRepositoryImpl implements DespachoRepository {

	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Despacho> getDespachoByDocumento(Integer idDocumentoDetalhes) {
		String sql = "from Despacho as d where d.documentoDetalhes.idDocumentoDetalhes = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		
		query.setInteger(0, idDocumentoDetalhes);
		
		return (List<Despacho>) query.list();
	}

	@Override
	public List<Despacho> getDespachoByUsuario(Integer idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Despacho despacho) {
		// TODO Auto-generated method stub
		
	}

}