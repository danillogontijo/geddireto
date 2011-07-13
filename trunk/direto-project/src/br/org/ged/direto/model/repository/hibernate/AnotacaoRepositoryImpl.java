package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.repository.AnotacaoRepository;

@Repository("anotacaoRepository")
public class AnotacaoRepositoryImpl extends BaseRepositoryImpl implements AnotacaoRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<Anotacao> getAnotacaoByDocumento(Integer idDocumentoDetalhes) {

		String sql = "from Anotacao as a where a.documentoDetalhes.idDocumentoDetalhes = ? order by dataHoraAnotacao asc";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		
		query.setInteger(0, idDocumentoDetalhes);
		
		return (List<Anotacao>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Anotacao> getAnotacaoByUsuario(Integer idUsuario) {
		
		String sql = "from Anotacao as anotacao where anotacao.usuario.idUsuario = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		
		query.setInteger(0, idUsuario);
		
		return query.list();
	}

	@Override
	public void save(Anotacao anotacao) {
		hibernateTemplate.save(anotacao);
	}

}
