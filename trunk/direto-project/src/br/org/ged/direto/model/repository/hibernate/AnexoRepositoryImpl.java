package br.org.ged.direto.model.repository.hibernate;

import org.springframework.stereotype.Repository;
import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.repository.AnexoRepository;

@Repository("anexoRepository")
public class AnexoRepositoryImpl extends BaseRepositoryImpl implements AnexoRepository {

	@Override
	public void saveAnexo(Anexo anexo) {
		hibernateTemplate.save(anexo);
	}

	@Override
	public Anexo selectById(int idAnexo) {
		return (Anexo)hibernateTemplate.get(Anexo.class, idAnexo);
	}

	@Override
	public void updateAnexo(Anexo anexo) {
		hibernateTemplate.update(anexo);
	}

}
