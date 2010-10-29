package br.org.ged.direto.model.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;

public interface DocumentosService {
	
	public List<DataUtils> listDocumentsFromAccount (Integer idCarteira, int ordenacao, int inicio, String box, String filtro);
	public void sendDocument(Carteira[] carteira, Documento documento);
	public List<Documento> listByLimited(Integer idCarteira);
	
	//@PostAuthorize("returnObject.idCarteira == principal.idCarteira")
	public Documento selectByIdCarteira(Integer idCarteira);
	
	@PostAuthorize("(returnObject == null) or (returnObject.carteira.idCarteira == principal.idCarteira)")
	public Documento selectById(Integer id, Integer idCarteira) throws DocumentNotFoundException;
	
	public Long counterDocumentsByBox(String box, int idCarteira, String filtro);
	
	public List<Documento> getAllById(Integer id);
}
