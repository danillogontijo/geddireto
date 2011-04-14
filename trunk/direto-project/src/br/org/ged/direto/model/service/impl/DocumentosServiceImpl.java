package br.org.ged.direto.model.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import br.org.direto.util.Protocolo;
import br.org.ged.direto.controller.forms.DocumentoForm;
import br.org.ged.direto.controller.forms.PesquisaForm;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.repository.DocumentosRepository;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.UsuarioService;

@Service("documentosService")
@RemoteProxy(name = "documentosJS")
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS,rollbackFor=Exception.class)
public class DocumentosServiceImpl implements DocumentosService {

	@Autowired
	private DocumentosRepository documentosRepository;
	
	@Autowired
	private CarteiraService carteiraService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Protocolo protocolo;
	
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
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario obj = (Usuario)auth.getPrincipal();
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		usuario.setIdCarteira(obj.getIdCarteira());
		//Documento documento = getDocumento(id); 
		
		//Documento documento = documentosRepository.selectByIdCarteira(idCarteira);//documentosRepository.selectById(idDocumentoDetalhes);
		Documento documento = documentosRepository.getByIdPKey(id);
		
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
			throw new DocumentNotFoundException("Você não tem permissão para acessar este documento.");
		
		return documento;
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
			throw new DocumentNotFoundException("Você não tem permissão para acessar este documento.");
		
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
	public void setDataNotificacao(Date data, int primaryKey) {
		documentosRepository.getByIdPKey(primaryKey).setDataHoraNotificacao(data);
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
	public Collection<DocumentoCompleto> returnSearch(PesquisaForm form) {
		return documentosRepository.returnSearch(form);
	}

	@Override
	public int returnTotalSearch(PesquisaForm form) {
		return documentosRepository.returnTotalSearch(form);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void saveNewDocumento(DocumentoDetalhes documentoDetalhes) {
		System.out.println("\nDOCUMENTO GRAVADO: "+documentoDetalhes.toString()+"");
		
		Documento doc = documentosRepository.getByIdPKey(new Integer(1));
		
		documentoDetalhes.setDataDocumento(doc.getDocumentoDetalhes().getDataDocumento());
		documentoDetalhes.setDataEntSistema(doc.getDocumentoDetalhes().getDataEntSistema());
		documentoDetalhes.setUsuarioElaborador(doc.getDocumentoDetalhes().getUsuarioElaborador());
		
		//documentoDetalhes = doc.getDocumentoDetalhes();
		
		documentosRepository.saveNewDocumento(documentoDetalhes);
		System.out.println("DOCUMENTO MODIFICADO: "+documentoDetalhes.toString()+"\n\n");
		
	}
	
	public synchronized String createProtocolNumber(int idDocumentoDetalhes){
		String y = "yyyy";
		String m = "MM";
		String year, month;

		java.util.Date now = new java.util.Date();
		
		SimpleDateFormat format = new SimpleDateFormat(y);
		year = format.format(now);
		
		format = new SimpleDateFormat(m);
		month = format.format(now);
		
		return year+month+idDocumentoDetalhes;

	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized void sendDocument(DocumentoDetalhes documentoDetalhes, int idCarteira, char status){
		
		Carteira to = carteiraService.selectById(idCarteira);
		int idDocumentoDetalhes = documentoDetalhes.getIdDocumentoDetalhes();
		Documento documentoToSaveOrUpdate;
		
		try {
			documentoToSaveOrUpdate = documentosRepository.selectById(idDocumentoDetalhes, idCarteira);
			documentoToSaveOrUpdate.setStatus(status);
			
			documentosRepository.saveOrUpdateDocumento(documentoToSaveOrUpdate);
		
		}catch(DocumentNotFoundException ex){
		
			System.out.println("Documento inexistente, gravando novo documento...");
			
			documentoToSaveOrUpdate = new Documento();
			documentoToSaveOrUpdate.setCarteira(to);
			documentoToSaveOrUpdate.setDataHora(new Date());
			documentoToSaveOrUpdate.setDataHoraNotificacao(new Date());
			documentoToSaveOrUpdate.setDocumentoDetalhes(documentoDetalhes);
			documentoToSaveOrUpdate.setNotificar(new Integer(0));
			documentoToSaveOrUpdate.setStatus(status);
			
			documentosRepository.saveOrUpdateDocumento(documentoToSaveOrUpdate);
			
		}
		
		System.out.println("Enviado documento: "+documentoToSaveOrUpdate.getDocumentoDetalhes().getNrProtocolo()+" para "+documentoToSaveOrUpdate.getCarteira().getCartAbr());
	}
	
	@RemoteMethod
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized Documento sendAndSaveFormToNewDocumento(DocumentoForm form){
		
		Documento sendedDocument = null;
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario user = (Usuario) auth.getPrincipal();
			
			int idDocumentoDetalhes = getLastId()+1;
			String sDestinatarios[] = form.getDestinatarios().split("\\,");
			String protocolNumber = createProtocolNumber(idDocumentoDetalhes);
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			Date dateDocument = (Date)formatter.parse(form.getDataDocumento()); 
			
			DocumentoDetalhes documento = new DocumentoDetalhes();
			
			//form.setNrProtocol(protocolNumber);
			if (form.getAssinatura() == 1)
				documento.setAssinadoPor(user.getUsuLogin());
			documento.setAssinatura(form.getAssinatura());
			
			documento.setIdDocumentoDetalhes(idDocumentoDetalhes);
			documento.setTipoDocumento(form.getTipoDocumento());
			documento.setPrioridade(form.getPrioridade());
			documento.setDataDocumento(dateDocument);
			documento.setNrDocumento(form.getNrDocumento());
			documento.setAssunto(form.getAssunto());
			documento.setRemetente(form.getRemetente());
			documento.setDestinatario(form.getDestinatario());
			documento.setReferencia(form.getReferencia());
			documento.setTipo(form.getOrigem());
			
			documento.setNrProtocolo(protocolNumber);
			
			System.out.println("Salvando documento "+documento.getNrProtocolo()+" no BD");
			saveNewDocumento(documento);
			
			for (int i = 0; i < sDestinatarios.length; i++){
				int idCarteira = Integer.parseInt(sDestinatarios[i]);
				sendDocument(documento,idCarteira,'0');
			}
			
			System.out.println("Finalizado\n\n");
			
			sendDocument(documento,form.getIdCarteiraRemetente(),'3'); //Envia o documento para caixa de saída do remetente
			
			sendedDocument = documentosRepository.selectById(idDocumentoDetalhes, form.getIdCarteiraRemetente());
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		 return sendedDocument;
	}

}