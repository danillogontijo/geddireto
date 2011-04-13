package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.Secao;

public interface SecaoService {

	public List<Secao> getAll();
	public Secao getSecaoByPkId(Integer pkId);
}
