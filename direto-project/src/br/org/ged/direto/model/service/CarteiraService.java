package br.org.ged.direto.model.service;

import java.util.List;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.dwr.CarteiraDWR;

public interface CarteiraService {
	
	public void save(Carteira carteira);
	public void save(int id,String descricao, String abreviatura, int idFuncao, int idSecao, int idOM);
	public List<Carteira> getAll();
	public List<DataUtils> getAllDwr();
	public Carteira selectById(Integer primaryKey);
	public CarteiraDWR select(int id);
	public List<DataUtils> getAllUsers(int idCarteira);

}
