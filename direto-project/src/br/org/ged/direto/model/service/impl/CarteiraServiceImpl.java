package br.org.ged.direto.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Funcao;
import br.org.ged.direto.model.entity.OM;
import br.org.ged.direto.model.entity.Secao;
import br.org.ged.direto.model.repository.CarteiraRepository;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.FuncaoService;
import br.org.ged.direto.model.service.OMService;
import br.org.ged.direto.model.service.SecaoService;

@Service("carteiraService")
@RemoteProxy(name = "carteiraJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class CarteiraServiceImpl implements CarteiraService {

	@Autowired
	private CarteiraRepository carteiraRepository;
	
	@Autowired
	private FuncaoService funcaoService;
	
	@Autowired
	private SecaoService secaoService;
	
	@Autowired
	private OMService omService;
	
	@Override
	public List<Carteira> getAll() {
		return carteiraRepository.getAll();
	}

	@Override
	public void save(Carteira carteira) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Carteira selectById(Integer primaryKey) {
		return carteiraRepository.selectById(primaryKey);
	}

	@Override
	@RemoteMethod
	public void save(String descricao, String abreviatura, int idFuncao,
			int idSecao, int idOM) {
		Carteira carteira = new Carteira();
		Funcao funcao = funcaoService.getFuncaoByPkId(idFuncao);
		Secao secao = secaoService.getSecaoByPkId(idSecao);
		OM om = omService.getOMByPkId(idOM);
		carteira.setFuncao(funcao);
		carteira.setSecao(secao);
		carteira.setOm(om);
		carteira.setCartDesc(descricao);
		carteira.setCartAbr(abreviatura);	
		try{
			carteiraRepository.save(carteira);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	@RemoteMethod
	public List<DataUtils> getAllDwr() {
		List<DataUtils> carteirasDWR = new ArrayList<DataUtils>();
		
		List<Carteira> carteiras = getAll();
		
		for(Carteira carteira : carteiras){
			DataUtils dado = new DataUtils();
			dado.setId(String.valueOf(carteira.getIdCarteira()));
			dado.setTexto(carteira.getCartAbr());
			carteirasDWR.add(dado);
		}

		return carteirasDWR;
	}

}
