package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Secao;
import br.org.ged.direto.model.repository.SecaoRepository;

@Repository("secaoRepository")
public class SecaoRepositoryImpl extends BaseRepositoryImpl implements SecaoRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<Secao> getAll() {
		return (List<Secao>) hibernateTemplate.find("from "
				+ Secao.class.getName());
	}

	@Override
	public Secao getSecaoByPkId(Integer pkId) {
		return hibernateTemplate.get(Secao.class, pkId);
	}

}
