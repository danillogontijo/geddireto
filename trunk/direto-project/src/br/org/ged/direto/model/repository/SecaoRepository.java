package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.Secao;

public interface SecaoRepository {
	public List<Secao> getAll();
	public Secao getSecaoByPkId(Integer pkId);
}
