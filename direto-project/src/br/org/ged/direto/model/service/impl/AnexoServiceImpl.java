package br.org.ged.direto.model.service.impl;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.AnexoRepository;
import br.org.ged.direto.model.service.AnexoService;
import br.org.ged.direto.model.service.DocumentosService;

@Service("anexoService")
@RemoteProxy(name = "anexoJS")
public class AnexoServiceImpl implements AnexoService {

	@Autowired
	private AnexoRepository anexoRepository;
	
	@Autowired
	private DocumentosService documentosService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
	public void saveAnexo(Anexo anexo) {
		anexoRepository.saveAnexo(anexo);
	}

	@Override
	@RemoteMethod
	@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
	public void saveAnexo(String anexoNome, String anexoCaminho,
			int idDocumentoDetalhes, boolean isAssign) {
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			int assinado = (isAssign == true ? 1 : 0);
			DocumentoDetalhes documento = documentosService.getDocumentoDetalhes(idDocumentoDetalhes);
			System.out.println(idDocumentoDetalhes);
			System.out.println(documento);
			Anexo anexoToSave = new Anexo();
			anexoToSave.setAnexoNome(anexoNome);
			anexoToSave.setAnexoCaminho(anexoCaminho);
			anexoToSave.setDocumentoDetalhes(documento);
			anexoToSave.setAssinado(assinado);
			anexoToSave.setAssinadoPor(auth.getName());
			
			saveAnexo(anexoToSave);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
