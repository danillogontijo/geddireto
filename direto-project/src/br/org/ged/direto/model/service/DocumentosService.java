package br.org.ged.direto.model.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.controller.forms.DocumentoForm;
import br.org.ged.direto.controller.forms.PesquisaForm;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;

public interface DocumentosService {
	
	public List<DataUtils> listDocumentsFromAccount (Integer idCarteira, int ordenacao, int inicio, String box, String filtro);
	public void sendDocument(Carteira[] carteira, Documento documento);
	public List<Documento> listByLimited(Integer idCarteira);
	
	//@PostAuthorize("returnObject.idCarteira == principal.idCarteira")
	public Documento selectByIdCarteira(Integer idCarteira);
	
	//@PostAuthorize("(returnObject == null) or (returnObject.carteira.idCarteira == principal.idCarteira)")
	@PostAuthorize("(returnObject == null) or (returnObject.granted)")
	public Documento selectById(Integer idDocumentoDetalhes, Integer idCarteira) throws DocumentNotFoundException;
	
	@PostAuthorize("(returnObject == null) or (returnObject.granted)")
	public Documento getDocumento(Integer primaryKey) throws DocumentNotFoundException;
	
	public DocumentoDetalhes getDocumentoDetalhes(int primaryKey);
	
	public Long counterDocumentsByBox(String box, int idCarteira, String filtro);
	
	public List<Documento> getAllById(Integer id);
	
	public List<Anexo> getAllAnexos(Integer idDocumentoDetalhes);
	
	public void setDataNotificacao(Date data, int primaryKey);
	//public Documento selectById(Integer id) throws DocumentNotFoundException;
	public Integer getLastId();
	
	
	public void acompanhar(Integer id, boolean yesOrNo);
	
	public void setDocumentoStatus(Integer id, char status);
	
	public Collection<DocumentoCompleto> returnSearch(PesquisaForm form);
	
	public int returnTotalSearch(PesquisaForm form);
	
	public void saveNewDocumento(DocumentoDetalhes documentoDetalhes);
	
	public Documento sendAndSaveFormToNewDocumento(DocumentoForm form);
}
