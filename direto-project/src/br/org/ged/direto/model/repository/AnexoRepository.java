package br.org.ged.direto.model.repository;

import br.org.ged.direto.model.entity.Anexo;

public interface AnexoRepository {
	public void saveAnexo(Anexo anexo);
	public Anexo selectById(int idAnexo);
	public void updateAnexo(Anexo anexo);
}
