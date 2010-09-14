package br.org.ged.direto.model.service;

import java.util.List;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Documento;

public interface DocumentosService {
	
	public List<DataUtils> listDocumentsFromAccount (Integer idCarteira);
	public void sendDocument(Carteira[] carteira, Documento documento);
	public List<Documento> listByLimited(Integer idCarteira);
	public Documento selectByIdCarteira(Integer idCarteira);

}