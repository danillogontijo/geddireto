package br.org.ged.direto.model.service;

import java.util.Collection;
import java.util.List;

import br.org.direto.util.DataUtils;

public interface GruposService {
	
	public List<DataUtils> listGroups (Integer idCarteira);
	public Collection<DataUtils> usersByGroup(Integer idNomeGrupo);
	public List<DataUtils> allGroups();

}
