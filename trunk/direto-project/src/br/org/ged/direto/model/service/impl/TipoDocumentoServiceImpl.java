package br.org.ged.direto.model.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.ged.direto.model.entity.TipoDocumento;
import br.org.ged.direto.model.repository.TipoDocumentoRepository;
import br.org.ged.direto.model.service.TipoDocumentoService;

@Service("tipoDocumentoService")
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	@Override
	public Map<Integer, String> getAll() {
		return tipoDocumentoRepository.getAll();
	}

	@Override
	public List<TipoDocumento> getAllList() {
		return tipoDocumentoRepository.getAllList();
	}

}
