package br.org.ged.direto.model.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.repository.GruposRepository;
import br.org.ged.direto.model.service.GruposService;

@Service("gruposService")
@RemoteProxy(name = "gruposJS")
public class GruposServiceImpl implements GruposService {

	@Autowired
	public GruposRepository gruposRepository;
	
	@Override
	@RemoteMethod
	public List<DataUtils> listGroups(Integer idCarteira) {
		return gruposRepository.listGroups(idCarteira);
		//return null;
	}

	
	@Override
	@RemoteMethod
	public Collection<DataUtils> usersByGroup(Integer idNomeGrupo) {
		
		
		
		//Set<DataUtils> list = (Set<DataUtils>) gruposRepository.usersByGroup(idNomeGrupo);
		
		//System.out.println("GRUPOS: "+list.size());
		
		return gruposRepository.usersByGroup(idNomeGrupo);
	}

}
