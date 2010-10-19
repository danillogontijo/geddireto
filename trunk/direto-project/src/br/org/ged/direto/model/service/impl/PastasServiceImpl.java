package br.org.ged.direto.model.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.repository.PastasRepository;
import br.org.ged.direto.model.service.PastasService;

@Service("pastasService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class PastasServiceImpl implements PastasService {

	@Autowired
	private PastasRepository pastasRepository;
		
	@Override
	public Collection<Pastas> getAll() {
		return (List<Pastas>) pastasRepository.getAll();
	}

	@Override
	public Pastas getPastaById(Integer id) {
		return pastasRepository.getPastaById(id);
	}

}
