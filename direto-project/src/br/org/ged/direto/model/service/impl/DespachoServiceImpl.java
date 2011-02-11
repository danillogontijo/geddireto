package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Despacho;
import br.org.ged.direto.model.repository.DespachoRepository;
import br.org.ged.direto.model.service.DespachoService;

@Service("despachoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DespachoServiceImpl implements DespachoService {

	
	@Autowired
	private DespachoRepository despachoRepository;

	@Override
	public List<Despacho> getDespachoByDocumento(Integer idDocumentoDetalhes) {
		return despachoRepository.getDespachoByDocumento(idDocumentoDetalhes);
	}

	@Override
	public List<Despacho> getDespachoByUsuario(Integer idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Despacho despacho) {
		// TODO Auto-generated method stub
		
	}
}
