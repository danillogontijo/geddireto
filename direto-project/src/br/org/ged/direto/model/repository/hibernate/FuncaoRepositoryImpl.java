package br.org.ged.direto.model.repository.hibernate;

import java.util.List;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Funcao;
import br.org.ged.direto.model.repository.FuncaoRepository;

@Repository("funcaoRepository")
public class FuncaoRepositoryImpl extends BaseRepositoryImpl implements FuncaoRepository {

	@Override
	@SuppressWarnings("unchecked")
	public List<Funcao> getAll() {
		return (List<Funcao>) hibernateTemplate.find("from "
				+ Funcao.class.getName());
	}

	@Override
	public Funcao getFuncaoByPkId(Integer pkId) {
		return hibernateTemplate.get(Funcao.class, pkId);
	}

}
