package br.org.ged.direto.model.service;

import br.org.ged.direto.model.entity.Anexo;

public interface AnexoService {
	
	public void saveAnexo(Anexo anexo);
	public void saveAnexo(String anexoNome, String anexoCaminho, int idDocumentoDetalhes, boolean isAssign, boolean saveInHistorico);

}
