package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.PstGrad;


public interface PstGradService {
	
	public List<PstGrad> getAll();
	public PstGrad getPstGradById(Integer id);

}
