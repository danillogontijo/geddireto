package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Comentario;
import br.org.ged.direto.model.repository.ComentarioRepository;

@Repository("comentarioRepository")
public class ComentarioRepositoryImpl extends BaseRepositoryImpl implements ComentarioRepository {

	@Override
	public void save(Comentario comentario) {
		hibernateTemplate.save(comentario);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comentario> getAllComments() {
		return (List<Comentario>) hibernateTemplate.find("from "
				+ Comentario.class.getName());
	}

}
