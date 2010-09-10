package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.repository.DocumentosRepository;
import br.org.ged.direto.model.service.DocumentosService;

@Service("documentosService")
@RemoteProxy(name = "documentosJS")
public class DocumentosServiceImpl implements DocumentosService {

	@Autowired
	private DocumentosRepository documentosRepository;
	
	@Override
	public List<DataUtils> listDocumentsFromAccount(Integer idCarteira) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDocument(Carteira[] carteira, Documento documento) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Documento> listByLimited() {
		return documentosRepository.listByLimited();
	}

	@Override
	public Documento selectByIdCarteira(Integer idCarteira) {
		return documentosRepository.selectByIdCarteira(idCarteira);
	}

}
