package br.org.ged.direto.model.service;

import java.util.List;

import br.org.ged.direto.model.entity.OM;

public interface OMService {
	
	public List<OM> getAll();
	public OM getOMByPkId(Integer pkId);

}
