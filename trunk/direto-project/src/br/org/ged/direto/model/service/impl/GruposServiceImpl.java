package br.org.ged.direto.model.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Grupos;
import br.org.ged.direto.model.entity.NomeGrupos;
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


	@Override
	@RemoteMethod
	public List<DataUtils> allGroups() {
		return gruposRepository.allGroups();
	}


	@Override
	@RemoteMethod
	public List<DataUtils> carteirasByGroup(int idNomeGrupo) {
		
		NomeGrupos nomeGrupo = gruposRepository.getNomeGrupo(idNomeGrupo);
		
		List<DataUtils> dados = new LinkedList<DataUtils>();
		
		for(Grupos grupo : nomeGrupo.getGrupos()){
			DataUtils data = new DataUtils();
			
			data.setId(String.valueOf(grupo.getIdGrupo()));
			data.setTexto(grupo.getCarteira().getIdCarteira()+"-"+grupo.getCarteira().getCartAbr()+"["+grupo.getCarteira().getOm().getOmAbr()+"]");
			
			dados.add(data);
		}
		
		return dados;
		
	}


	@Override
	@RemoteMethod
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void addCarteiraInGroup(int idNomeGrupo, int idCarteira) {
		gruposRepository.addCarteiraInGroup(idNomeGrupo, idCarteira);
	}


	@Override
	@RemoteMethod
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteCarteiraFromGroup(int idGrupo) {
		gruposRepository.deleteCarteiraFromGroup(idGrupo);
	}

}
