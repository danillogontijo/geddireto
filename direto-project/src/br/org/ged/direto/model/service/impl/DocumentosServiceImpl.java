package br.org.ged.direto.model.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.controller.forms.PesquisaForm;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.repository.DocumentosRepository;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.UsuarioService;

@Service("documentosService")
@RemoteProxy(name = "documentosJS")
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS,rollbackFor=Exception.class)
public class DocumentosServiceImpl implements DocumentosService {

	@Autowired
	private DocumentosRepository documentosRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
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
	public Documento selectById(Integer idDocumentoDetalhes,Integer idCarteira) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario obj = (Usuario)auth.getPrincipal();
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		usuario.setIdCarteira(obj.getIdCarteira());
		//Documento documento = getDocumento(id); 
		
		Documento documento = documentosRepository.selectById(idDocumentoDetalhes);
		
		int secaoDocumento = documento.getCarteira().getSecao().getIdSecao();
		int omDocumento = documento.getCarteira().getOm().getIdOM();
		
		Iterator<Conta> ite = usuario.getContas().iterator();
		
		while(ite.hasNext()){
			
			Carteira carteiraUsuarioLogado = ite.next().getCarteira();
			
			if (usuario.getIdCarteira() == carteiraUsuarioLogado.getIdCarteira()){
			
				int secaoUsuarioLogado = carteiraUsuarioLogado.getSecao().getIdSecao();
				int omUsuarioLogado = carteiraUsuarioLogado.getOm().getIdOM();
				
				if (secaoUsuarioLogado == secaoDocumento && omUsuarioLogado == omDocumento)
					documento.setGranted(true);
			}
			
		}
		
		if (!documento.isGranted()) 
			throw new DocumentNotFoundException("Sem permissão de visualização");
		
		return documento;
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

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
	public void setDataNotificacao(Date data, Integer id, Integer idCarteira) {
		documentosRepository.selectById(id, idCarteira).setDataHoraNotificacao(data);
		
	}

	@Override
	public Integer getLastId() {
		return documentosRepository.getLastId();
	}

	@Override
	@RemoteMethod
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void acompanhar(Integer id, boolean yesOrNo) {
		Documento doc = documentosRepository.getByIdPKey(id);
		if (yesOrNo) { doc.setNotificar(1); } else { doc.setNotificar(0);}
	}

	@Override
	@RemoteMethod
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void setDocumentoStatus(Integer id, char status) {
		Documento doc = documentosRepository.getByIdPKey(id);
		doc.setStatus(status);
	}

	@Override
	public Documento getDocumento(Integer primaryKey) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario obj = (Usuario)auth.getPrincipal();
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		usuario.setIdCarteira(obj.getIdCarteira());
		Documento documento = documentosRepository.getByIdPKey(primaryKey);
		
		int secaoDocumento = documento.getCarteira().getSecao().getIdSecao();
		int omDocumento = documento.getCarteira().getOm().getIdOM();
		
		Iterator<Conta> ite = usuario.getContas().iterator();
		
		while(ite.hasNext()){
			
			Carteira carteiraUsuarioLogado = ite.next().getCarteira();
			
			if (usuario.getIdCarteira() == carteiraUsuarioLogado.getIdCarteira()){
			
				int secaoUsuarioLogado = carteiraUsuarioLogado.getSecao().getIdSecao();
				int omUsuarioLogado = carteiraUsuarioLogado.getOm().getIdOM();
				
				if (secaoUsuarioLogado == secaoDocumento && omUsuarioLogado == omDocumento)
					documento.setGranted(true);
			}
			
		}
		
		if (!documento.isGranted()) 
			throw new DocumentNotFoundException("Sem permissão de visualização");
		
		return documento;
	}

	@Override
	public Collection<DocumentoCompleto> returnSearch(PesquisaForm form) {
		return documentosRepository.returnSearch(form);
	}

	@Override
	public int returnTotalSearch(PesquisaForm form) {
		return documentosRepository.returnTotalSearch(form);
	}

	
}
