package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.PstGrad;


public interface PstGradRepository {
	
	public List<PstGrad> getAll();
	public PstGrad getPstGradById(Integer id);

}
