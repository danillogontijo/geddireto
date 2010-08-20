package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Funcao;
import br.org.ged.direto.model.repository.FuncaoRepository;
import br.org.ged.direto.model.service.FuncaoService;

@Service("funcaoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class FuncaoServiceImpl implements FuncaoService {
	
	private FuncaoRepository funcaoRepository;
	
	@Autowired
	public void setPstgradRepository(FuncaoRepository pstgradRepository) {
		this.funcaoRepository = funcaoRepository;
	}

	@Override
	public List<Funcao> getAll(Funcao funcao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Funcao getPstGradById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
