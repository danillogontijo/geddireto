package br.org.ged.direto.model.repository.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Grupos;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.GruposRepository;

@Repository("gruposRepository")
public class GruposRepositoryImpl implements GruposRepository {

	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public List<DataUtils> listGroups(Integer idCarteira) {
		
		String sql = "FROM Grupos g inner join g.carteira ng WHERE ng.idCarteira = ?";
		
		//inner join g.nomeGrupos ng
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		query.setInteger(0, idCarteira);
		
		List results = query.list();
		
		List<DataUtils> gruposDwr = new LinkedList<DataUtils>();
		
		//DataUtils data = new DataUtils();
		
		for(int i=0; i<results.size(); i++){
			DataUtils data = new DataUtils();
			
			//Grupos g = (Grupos)results.get(i);
			
			Object[] objects = (Object[]) results.get(i);
			Grupos g = (Grupos)objects[0];
			
			data.setId(String.valueOf(g.getNomeGrupo().getIdNomeGrupo()));
			data.setTexto(g.getNomeGrupo().getGrupoAbr());
			
			gruposDwr.add(data);
			
			//Object[] objects = (Object[]) results.get(i);
			//DocumentoDetalhes doc = (DocumentoDetalhes) objects[1];
			//Documento doc_cart = (Documento) objects[0];
		}
		
		return gruposDwr;
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataUtils> usersByGroup(Integer idNomeGrupo) {
		//String sql = "FROM Grupos g inner join g.nomeGrupo ng WHERE ng.idNomeGrupo = ?";
		
		String sql = "SELECT usuomsec.* " +
				"FROM pstgrad,grupo,usuomsec,usuario " +
				"WHERE grupo.IdNomeGrupo = ? " +
				"AND grupo.IdCarteira=usuomsec.IdCarteira " +
				"AND usuomsec.IdUsuario=usuario.IdUsuario " +
				"AND pstgrad.IdPstGrad=usuario.IdPstGrad "+//GROUP BY usuario.IdUsuario " +
				"ORDER BY pstgrad.IdPstGrad";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(Conta.class);
		query.setInteger(0, idNomeGrupo);
		//query.
		
		List results = query.list();
		System.out.println("RESULTS"+results.size());
		
		List<DataUtils> usersDwr = new LinkedList<DataUtils>();
		/*DataUtils d = new DataUtils();
		d.setId("1");
		d.setTexto("teste");
		usersDwr.add(d);*/
		
		
		
		for(int i=0; i<results.size(); i++){
			DataUtils data = new DataUtils();
			
			//Grupos g = (Grupos)results.get(i);
			
			/*Object[] objects = (Object[]) results.get(i);
			Grupos g = (Grupos)objects[0];
			
			Set<Conta> contas = g.getCarteira().getContas();
			Iterator<Conta> iteContas = contas.iterator();
			DataUtils data = new DataUtils();
			
			while(iteContas.hasNext()){
				
				Conta conta = iteContas.next();
				Usuario user = conta.getUsuario();
				
				data.setId(String.valueOf(user.getIdUsuario()));
				data.setTexto(user.getPstGrad().getPstgradNome()+" "+user.getUsuNGuerra());
				usersDwr.add(data);
				//System.out.println(data.getTexto());
				
			}*/
			
			//Object[] o = (Object[]) results.get(i);
			//Usuario user = (Usuario)results.get(i);
			Conta conta = (Conta)results.get(i);
			
			data.setId(String.valueOf(conta.getCarteira().getIdCarteira()));
			data.setTexto(conta.getUsuario().getPstGrad().getPstgradNome()+" "+conta.getUsuario().getUsuNGuerra()+"["+conta.getCarteira().getCartAbr()+"]");
			usersDwr.add(data);
		
			
			
			
			
			
		}
		
		System.out.println(usersDwr.size());
		
		
		
		return usersDwr;
	}

}
