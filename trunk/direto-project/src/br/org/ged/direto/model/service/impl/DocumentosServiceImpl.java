package br.org.ged.direto.model.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
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
import br.org.ged.direto.model.service.TipoDocumentoService;
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
	private TipoDocumentoService tipoDocumentoService;
	
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
	public Documento selectById(int primaryKey) {
		
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
		
		Integer id = documentosRepository.getLastId();
		
		if (id==null)
			return new Integer(1);
		
		return id;
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
	
	public int multiplica(String s, int c){
		int n = Character.getNumericValue(s.charAt(s.length()-1));
		if (s.length() == 1) return (n*c);
		return (n*c) + multiplica(s.substring(0, s.length()-1),++c);
	}
	
	public synchronized String createProtocolNumber(String year){
				
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getPrincipal();
		int omCode = carteiraService.selectById(usuario.getIdCarteira()).getOm().getOmCode();
		
		StringBuffer sb = new StringBuffer(String.valueOf(documentosRepository.getAmountDocumentoByYear(year)+1));
		
		System.out.println(sb.toString()+" QUANTIDADE ANO: "+year);
		
		while(sb.length() != 6)
			sb.insert(0, 0);
		
		sb.insert(0, omCode);
		sb.append(year);
		
		while(sb.length() != 15)
			sb.insert(0, 0);
		
		int dv = multiplica(sb.toString(),2);
		int resto = dv%11;
		int nDv = (11-resto) >= 10 ? (11-resto)/10 : (11-resto);
		
		sb.append(nDv); //primeiro dv
		
		dv = multiplica(sb.toString(),2);
		resto = dv%11;
		nDv = (11-resto) >= 10 ? (11-resto)/10 : (11-resto);
		
		sb.append(nDv); //segundo dv
		
		return sb.toString();

	}
	
	@RemoteMethod
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String encaminharDocumento(String destinatarios, int idDocumentoDetalhes){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = (Usuario) auth.getPrincipal();
		
		DocumentoDetalhes documentoDetalhes = getDocumentoDetalhes(idDocumentoDetalhes);
		String arDestinatarios[] = destinatarios.split("\\,");
		
		for (int i = 0; i < arDestinatarios.length; i++){
			int idCarteira = Integer.parseInt(arDestinatarios[i]);
			sendDocument(documentoDetalhes,idCarteira,'0');
		}
		
		try{
			Documento ownDocument = documentosRepository.selectById(idDocumentoDetalhes, user.getIdCarteira());
			if(ownDocument.getStatus() != '3')
				ownDocument.setStatus('2'); //Envia documento para caixa de arquivado caso tenha o dco na cart
			
		}catch (DocumentNotFoundException e) {
			System.out.println("Documento pertencente a seção/om, " +
					"não existe este documento nesta carteira. "+documentoDetalhes.getNrProtocolo());	
		}
		
		return "";
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized void sendDocument(DocumentoDetalhes documentoDetalhes, int idCarteira, char status){
		
		Carteira to = carteiraService.selectById(idCarteira);
		int idDocumentoDetalhes = documentoDetalhes.getIdDocumentoDetalhes();
		Documento documentoToSaveOrUpdate;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getPrincipal();
		Carteira carteiraUsuarioLogado = carteiraService.selectById(usuario.getIdCarteira());
		
		//String txtHistorico = "";
		Date data = new Date();

		try {
			documentoToSaveOrUpdate = documentosRepository.selectById(idDocumentoDetalhes, idCarteira);
			documentoToSaveOrUpdate.setStatus(status);
			documentoToSaveOrUpdate.setEncaminhadoPor(usuario.getIdUsuario());
			documentoToSaveOrUpdate.setObservacao(carteiraUsuarioLogado.getCartAbr());
			documentoToSaveOrUpdate.setDataHora(data);
			
			documentosRepository.saveOrUpdateDocumento(documentoToSaveOrUpdate);
			
			/*txtHistorico = "(Encaminhado) - Do: ";
			txtHistorico += usuario.getPstGrad().getPstgradNome()+" "+usuario.getUsuNGuerra();
			txtHistorico += " - Para: "+to.getCartAbr();*/
		
		}catch(DocumentNotFoundException ex){
		
			System.out.println("Documento inexistente, gravando novo documento...");
			
			documentoToSaveOrUpdate = new Documento();
			documentoToSaveOrUpdate.setCarteira(to);
			documentoToSaveOrUpdate.setDataHora(data);
			documentoToSaveOrUpdate.setDataHoraNotificacao(data);
			documentoToSaveOrUpdate.setDocumentoDetalhes(documentoDetalhes);
			documentoToSaveOrUpdate.setNotificar(new Integer(0));
			documentoToSaveOrUpdate.setStatus(status);
			documentoToSaveOrUpdate.setEncaminhadoPor(usuario.getIdUsuario());
			documentoToSaveOrUpdate.setObservacao(carteiraUsuarioLogado.getCartAbr());
			
			documentosRepository.saveOrUpdateDocumento(documentoToSaveOrUpdate);
			
			/*txtHistorico = "(Enviado) - Do: ";
			txtHistorico += usuario.getPstGrad().getPstgradNome()+" "+usuario.getUsuNGuerra();
			txtHistorico += " - Para: "+to.getCartAbr();*/
			
		}
		
		/*Historico historico = new Historico();
		historico.setCarteira(carteiraUsuarioLogado);
		historico.setDataHoraHistorico(data);
		historico.setDocumentoDetalhes(documentoDetalhes);
		historico.setUsuario(usuario);
		historico.setHistorico(txtHistorico);
		
		historicoService.save(historico);*/
		
		System.out.println("Enviado documento: "+documentoToSaveOrUpdate.getDocumentoDetalhes().getNrProtocolo()+" para "+documentoToSaveOrUpdate.getCarteira().getCartAbr());
	}
	
	@RemoteMethod
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized DataUtils sendAndSaveFormToNewDocumento(DocumentoForm form){
		
		DataUtils retorno = null;
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario user = (Usuario) auth.getPrincipal();
			
			String y = "yyyy";
			String year;
			java.util.Date now = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat(y);
			year = format.format(now);
			
			int idDocumentoDetalhes = getLastId()+1;
			String sDestinatarios[] = form.getDestinatarios().split("\\,");
			String protocolNumber = createProtocolNumber(year);
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			Date dateDocument = (Date)formatter.parse(form.getDataDocumento()); 
			
			DocumentoDetalhes documento = new DocumentoDetalhes();
			
			//form.setNrProtocol(protocolNumber);
			if (form.getAssinatura() == 1)
				documento.setAssinadoPor(user.getUsuLogin());
			documento.setAssinatura(form.getAssinatura());
			
			documento.setIdDocumentoDetalhes(idDocumentoDetalhes);
			documento.setTipoDocumento(tipoDocumentoService.getTipoDocumento(form.getTipoDocumento()));
			documento.setPrioridade(form.getPrioridade());
			documento.setDataDocumento(dateDocument);
			documento.setNrDocumento(form.getNrDocumento());
			documento.setAssunto(form.getAssunto());
			documento.setRemetente(form.getRemetente());
			documento.setDestinatario(form.getDestinatario());
			documento.setReferencia(form.getReferencia());
			documento.setTipo(form.getOrigem());
			documento.setDataEntSistema(now);
			documento.setUsuarioElaborador(user);
			
			documento.setNrProtocolo(protocolNumber);
			
			System.out.println("Salvando documento "+documento.getNrProtocolo()+" no BD- "+documento.getDataEntSistema());
			documentosRepository.saveNewDocumento(documento);
			
			for (int i = 0; i < sDestinatarios.length; i++){
				int idCarteira = Integer.parseInt(sDestinatarios[i]);
				sendDocument(documento,idCarteira,'0');
			}
			
			System.out.println("Finalizado\n\n");
			
			sendDocument(documento,form.getIdCarteiraRemetente(),'3'); //Envia o documento para caixa de saída do remetente
			
			//sendedDocument = documentosRepository.selectById(idDocumentoDetalhes, form.getIdCarteiraRemetente());
			retorno = new DataUtils();
			retorno.setId(String.valueOf(idDocumentoDetalhes));
			retorno.setTexto(protocolNumber);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		 return retorno;
	}

	@Override
	public DocumentoDetalhes getDocumentoDetalhes(int primaryKey) {
		return documentosRepository.getDocumentoDetalhes(primaryKey);
	}

	@Override
	@RemoteMethod
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String changeStatus(int idDocumento, char status) {
		String retorno = "";
		try{
			Documento documento = selectById(idDocumento);
			documento.setStatus(status);
			String protocolo = documento.getDocumentoDetalhes().getNrProtocolo();
			
			if(status=='2')
				retorno = protocolo + " --> arquivado";
			else if (status=='4')
				retorno = protocolo + " --> caixa de pendentes";
			else if (status=='0')
				retorno = protocolo + " --> não lido na caixa de entrada";
			
		}catch (Exception e) {
			e.printStackTrace();
			return "Documento não encontrado ou status inválido";
		}
		return retorno;
	}

}
