package br.org.ged.direto.model.service;

import br.org.ged.direto.model.entity.Anexo;

public interface AnexoService {
	
	public void saveAnexo(Anexo anexo);
	public void saveAnexo(String anexoNome, String anexoCaminho, int idDocumentoDetalhes, boolean isAssign, boolean saveInHistorico);
	public Anexo selectById(int idAnexo);
	public void signAnexo(int idAnexo,String hash);
	public void signAnexo(Anexo anexo);
	public String getAssinaturaHash(int idAnexo);
	public boolean deleteAnexoFromTemp(int idAnexo);
	public boolean copy(int idAnexo);
	public void setAssinaturaHash(String hash, int idAnexo);
}
