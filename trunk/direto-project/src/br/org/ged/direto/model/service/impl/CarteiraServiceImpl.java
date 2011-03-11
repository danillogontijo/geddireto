package br.org.ged.direto.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.repository.CarteiraRepository;
import br.org.ged.direto.model.service.CarteiraService;

@Service("carteiraService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class CarteiraServiceImpl implements CarteiraService {

	private CarteiraRepository carteiraRepository;
	
	@Autowired
	public void setCarteiraRepository(CarteiraRepository carteiraRepository) {
		this.carteiraRepository = carteiraRepository;
	}

	@Override
	public List<Carteira> getAll(Carteira carteira) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Carteira carteira) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Carteira selectById(Integer primaryKey) {
		return carteiraRepository.selectById(primaryKey);
	}

}
