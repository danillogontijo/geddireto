package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.OM;
import br.org.ged.direto.model.repository.OMRepository;
import br.org.ged.direto.model.service.OMService;

@Service("omService")
@RemoteProxy(name = "omJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class OMServiceImpl implements OMService {

	@Autowired
	private OMRepository omRepository;
	
	@Override
	@RemoteMethod
	public List<OM> getAll() {		
		return omRepository.getAll();
	}

	@Override
	public OM getOMByPkId(Integer pkId) {
		return omRepository.getOMByPkId(pkId);
	}

}
