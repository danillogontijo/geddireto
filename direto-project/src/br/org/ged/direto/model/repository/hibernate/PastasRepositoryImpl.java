package br.org.ged.direto.model.repository.hibernate;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.repository.PastasRepository;

@Repository("pastasRepository")
public class PastasRepositoryImpl extends BaseRepositoryImpl implements PastasRepository {

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Pastas> getAll() {
		return (List<Pastas>) hibernateTemplate.find("from "
				+ Pastas.class.getName() + " order by idPasta asc");
	}

	@Override
	public Pastas getPastaById(Integer id) {
		return hibernateTemplate.get(Pastas.class, id);
	}

}
