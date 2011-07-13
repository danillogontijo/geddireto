package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Despacho;
import br.org.ged.direto.model.repository.DespachoRepository;

@Repository("despachoRepository")
public class DespachoRepositoryImpl extends BaseRepositoryImpl implements DespachoRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<Despacho> getDespachoByDocumento(Integer idDocumentoDetalhes) {
		String sql = "from Despacho as d where d.documentoDetalhes.idDocumentoDetalhes = ? order by dataHoraDespacho asc";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		
		query.setInteger(0, idDocumentoDetalhes);
		
		return (List<Despacho>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Despacho> getDespachoByUsuario(Integer idUsuario) {
		String sql = "from Despacho as despacho where despacho.usuario.idUsuario = ? order by dataHoraDespacho asc";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		
		query.setInteger(0, idUsuario);
		
		return query.list();
	}

	@Override
	public void save(Despacho despacho) {
		hibernateTemplate.save(despacho);
	}

	@Override
	public Despacho selectDespacho(int idDespacho) {
		return hibernateTemplate.get(Despacho.class, idDespacho);
	}

}