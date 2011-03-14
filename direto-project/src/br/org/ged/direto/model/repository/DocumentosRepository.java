package br.org.ged.direto.model.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.controller.forms.PesquisaForm;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Documento;

public interface DocumentosRepository {
	
	public List<DataUtils> listDocumentsFromAccount (Integer idCarteira, int ordenacao, int inicio, String box, String filtro);
	public void sendDocument(Carteira[] carteira, Documento documento);
	public List<Documento> listByLimited(Integer idCarteira);
	public Documento selectByIdCarteira(Integer idCarteira);
	public Long counterDocumentsByBox(String box, int idCarteira, String filtro);
	public Documento selectById(Integer id, Integer idCarteira);
	public Documento selectById(Integer idDocumentoDetalhes);
	public List<Documento> getAllById(Integer id);
	public List<Anexo> getAllAnexos(Integer idDocumentoDetalhes);
	public Integer getLastId();
	public Documento getByIdPKey(Integer id);
	public Collection<DocumentoCompleto> returnSearch(PesquisaForm form);
	public int returnTotalSearch(PesquisaForm form);
}
