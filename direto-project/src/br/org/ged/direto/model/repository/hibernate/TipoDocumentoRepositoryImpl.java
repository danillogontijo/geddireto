package br.org.ged.direto.model.repository.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.TipoDocumento;
import br.org.ged.direto.model.repository.TipoDocumentoRepository;

@Repository("tipoDocumentoRepository")
public class TipoDocumentoRepositoryImpl implements TipoDocumentoRepository {

	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getAll() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		List<TipoDocumento> all = new ArrayList<TipoDocumento>();
		
		all = (List<TipoDocumento>)hibernateTemplate.find("from "
				+ TipoDocumento.class.getName() + " order by tipoDocumentoAbr asc");
		
		
		Iterator<TipoDocumento> ite = all.iterator();
		while(ite.hasNext()){
			TipoDocumento obj = ite.next();
			map.put(obj.getIdTipoDocumento(), obj.getTipoDocumentoNome());
		}
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoDocumento> getAllList() {
		return (List<TipoDocumento>)hibernateTemplate.find("from "
				+ TipoDocumento.class.getName() + " order by tipoDocumentoAbr asc");
	}

	@Override
	public TipoDocumento getTipoDocumento(int idTipoDocumento) {
		return hibernateTemplate.get(TipoDocumento.class, idTipoDocumento);
	}

}
