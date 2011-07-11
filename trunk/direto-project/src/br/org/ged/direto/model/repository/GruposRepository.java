package br.org.ged.direto.model.repository;

import java.util.List;
import java.util.Set;

import br.org.direto.util.DataUtils;

public interface GruposRepository {
	
	public List<DataUtils> listGroups(Integer idCarteira);
	public List<DataUtils> usersByGroup(Integer idNomeGrupo);
	public List<DataUtils> allGroups();

}
