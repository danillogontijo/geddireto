package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Secao;
import br.org.ged.direto.model.repository.SecaoRepository;
import br.org.ged.direto.model.service.SecaoService;

@Service("secaoService")
@RemoteProxy(name = "secaoJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class SecaoServiceImpl implements SecaoService {

	@Autowired
	private SecaoRepository secaoRepository; 
	
	@Override
	@RemoteMethod
	public List<Secao> getAll() {
		return secaoRepository.getAll();
	}

	@Override
	public Secao getSecaoByPkId(Integer pkId) {
		return secaoRepository.getSecaoByPkId(pkId);
	}

}
