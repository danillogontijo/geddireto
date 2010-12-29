package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Anexo;
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
	@RemoteMethod
	public List<DataUtils> listDocumentsFromAccount(Integer idCarteira, int ordenacao, int inicio, String box, String filtro) {
		return this.documentosRepository.listDocumentsFromAccount(idCarteira, ordenacao, inicio, box, filtro);
	}

	@Override
	public void sendDocument(Carteira[] carteira, Documento documento) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Documento> listByLimited(Integer idCarteira) {
		return documentosRepository.listByLimited(idCarteira);
	}

	@Override
	public Documento selectByIdCarteira(Integer idCarteira) {
		return documentosRepository.selectByIdCarteira(idCarteira);
	}

	@Override
	@RemoteMethod
	public Long counterDocumentsByBox(String box, int idCarteira, String filtro) {
		return documentosRepository.counterDocumentsByBox(box, idCarteira, filtro);
	}

	@Override
	public Documento selectById(Integer id,Integer idCarteira) {
		return documentosRepository.selectById(id, idCarteira);
	}

	@Override
	public List<Documento> getAllById(Integer id) {
		return documentosRepository.getAllById(id);
	}

	@Override
	public List<Anexo> getAllAnexos(Integer idDocumentoDetalhes) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
