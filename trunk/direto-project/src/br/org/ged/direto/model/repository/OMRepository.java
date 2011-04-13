package br.org.ged.direto.model.repository;

import java.util.List;

import br.org.ged.direto.model.entity.OM;

public interface OMRepository {
	
	public List<OM> getAll();
	public OM getOMByPkId(Integer pkId);

}
