package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.repository.HistoricoRepository;

@Repository("historicoRepository")
public class HistoricoRepositoryImpl extends BaseRepositoryImpl implements HistoricoRepository {

	@Override
	public List<Historico> getHistoricoByCarteira(Integer idCarteira) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Historico> getHistoricoByDocumento(Integer idDocumentoDetalhes) {

		String sql = "from Historico as h where h.documentoDetalhes.idDocumentoDetalhes = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		
		query.setInteger(0, idDocumentoDetalhes);
		
		return (List<Historico>) query.list();
	}

	@Override
	public List<Historico> getHistoricoByUsuario(Integer idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Historico historico) {
		hibernateTemplate.save(historico);
	}

}
