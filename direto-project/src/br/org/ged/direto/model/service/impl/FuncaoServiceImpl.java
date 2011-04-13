package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Funcao;
import br.org.ged.direto.model.repository.FuncaoRepository;
import br.org.ged.direto.model.service.FuncaoService;

@Service("funcaoService")
@RemoteProxy(name = "funcaoJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class FuncaoServiceImpl implements FuncaoService {
	
	private FuncaoRepository funcaoRepository;
	
	@Autowired
	public void setPstgradRepository(FuncaoRepository funcaoRepository) {
		this.funcaoRepository = funcaoRepository;
	}

	@Override
	@RemoteMethod
	public List<Funcao> getAll() {
		return funcaoRepository.getAll();
	}

	@Override
	public Funcao getFuncaoByPkId(Integer pkId) {
		return funcaoRepository.getFuncaoByPkId(pkId);
	}

}
