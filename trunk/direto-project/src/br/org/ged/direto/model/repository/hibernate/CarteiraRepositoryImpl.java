package br.org.ged.direto.model.repository.hibernate;

import java.util.List;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.repository.CarteiraRepository;

@Repository("carteiraRepository")
public class CarteiraRepositoryImpl extends BaseRepositoryImpl implements CarteiraRepository {

	@SuppressWarnings("unchecked")
	@Override
	public List<Carteira> getAll() {
		return (List<Carteira>) hibernateTemplate.find("from "
				//+ Carteira.class.getName()+" order by cartAbr asc");
				+ Carteira.class.getName()+" order by idOM asc, cartAbr asc");
	}

	@Override
	public void save(Carteira carteira) {
		hibernateTemplate.saveOrUpdate(carteira);
	}
	
	@Override
	public Carteira selectById(Integer primaryKey){
		return (Carteira) hibernateTemplate.get(Carteira.class, primaryKey);
	}

}
