package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.repository.HistoricoRepository;
import br.org.ged.direto.model.service.HistoricoService;

@Service("historicoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class HistoricoServiceImpl implements HistoricoService {

	@Autowired
	private HistoricoRepository historicoRepository;
	
	@Override
	public List<Historico> getHistoricoByCarteira(Integer idCarteira) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Historico> getHistoricoByDocumento(Integer idDocumentoDetalhes) {
		return historicoRepository.getHistoricoByDocumento(idDocumentoDetalhes);
	}

	@Override
	public List<Historico> getHistoricoByUsuario(Integer idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Historico historico) {
		// TODO Auto-generated method stub

	}

}
