package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.ContaRepository;
import br.org.ged.direto.model.service.ContaService;

@Service("contaService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ContaServiceImpl implements ContaService {
	
	private ContaRepository contaRepository;
	
	@Autowired
	public void setContaRepository(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	@Override
	public void add(Conta conta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tieUsers(List<Usuario> usuarios) {
		// TODO Auto-generated method stub

	}

	

}
