package br.org.ged.direto.model.repository.hibernate;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Grupos;
import br.org.ged.direto.model.entity.NomeGrupos;
import br.org.ged.direto.model.repository.GruposRepository;
import br.org.ged.direto.model.service.CarteiraService;

@Repository("gruposRepository")
public class GruposRepositoryImpl extends BaseRepositoryImpl implements GruposRepository {

	
	@Autowired
	public CarteiraService carteiraService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DataUtils> listGroups(Integer idCarteira) {
		
		String sql = "FROM Grupos g inner join g.carteira ng WHERE ng.idCarteira = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		query.setInteger(0, idCarteira);
		
		List results = query.list();
		
		List<DataUtils> gruposDwr = new LinkedList<DataUtils>();
		
		for(int i=0; i<results.size(); i++){
			DataUtils data = new DataUtils();
			
			Object[] objects = (Object[]) results.get(i);
			Grupos g = (Grupos)objects[0];
			
			data.setId(String.valueOf(g.getNomeGrupo().getIdNomeGrupo()));
			data.setTexto(g.getNomeGrupo().getGrupoAbr());
			
			gruposDwr.add(data);
		}
		
		return gruposDwr;
	}

	@Override
	//@Transactional(readOnly = true)
	public List<DataUtils> usersByGroup(Integer idNomeGrupo) {
		
		String sql = "SELECT usuomsec.* " +
				"FROM pstgrad,grupo,usuomsec,usuario " +
				"WHERE grupo.IdNomeGrupo = ? " +
				"AND grupo.IdCarteira=usuomsec.IdCarteira " +
				"AND usuomsec.IdUsuario=usuario.IdUsuario " +
				"AND pstgrad.IdPstGrad=usuario.IdPstGrad "+//GROUP BY usuario.IdUsuario " +
				"ORDER BY pstgrad.IdPstGrad asc, usuario.usuNGuerra asc";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(Conta.class);
		query.setInteger(0, idNomeGrupo);
		
		List results = query.list();
		System.out.println("RESULTS"+results.size());
		
		List<DataUtils> usersDwr = new LinkedList<DataUtils>();
		
		for(int i=0; i<results.size(); i++){
			DataUtils data = new DataUtils();
			
			Conta conta = (Conta)results.get(i);
			boolean respondendo = (conta.isPrincipal() ? false : true);
			
			data.setId(String.valueOf(conta.getCarteira().getIdCarteira()));
			
			if(respondendo){
				data.setTexto(conta.getUsuario().getPstGrad().getPstgradNome()+" "+conta.getUsuario().getUsuNGuerra()+"[Resp "+conta.getCarteira().getCartAbr()+"]");
			}else{
				data.setTexto(conta.getUsuario().getPstGrad().getPstgradNome()+" "+conta.getUsuario().getUsuNGuerra()+"["+conta.getCarteira().getCartAbr()+"]");
			}
			
			usersDwr.add(data);
		}
		
		return usersDwr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataUtils> allGroups() {
		List grupos = hibernateTemplate.find("from "+NomeGrupos.class.getName());
		
		List<DataUtils> dados = new LinkedList<DataUtils>();
		
		for(int i=0; i<grupos.size(); i++){
			DataUtils data = new DataUtils();
			
			NomeGrupos grupo = (NomeGrupos)grupos.get(i);
			
			data.setId(String.valueOf(grupo.getIdNomeGrupo()));
			data.setTexto(grupo.getGrupoAbr());
			
			dados.add(data);
		
		}
		
		return dados;
	}

	@Override
	public NomeGrupos getNomeGrupo(Integer idNomeGrupo) {
		return hibernateTemplate.get(NomeGrupos.class, idNomeGrupo);
	}

	@Override
	public void addCarteiraInGroup(int idNomeGrupo, int idCarteira) {
		Grupos grupo = new Grupos();
		grupo.setCarteira(carteiraService.selectById(idCarteira));
		grupo.setNomeGrupo((NomeGrupos)find(NomeGrupos.class,idNomeGrupo));
		hibernateTemplate.saveOrUpdate(grupo);
		
	}

	@Override
	public void deleteCarteiraFromGroup(int idGrupo) {
		try{
			Grupos grupoParaExcluir = (Grupos) find(Grupos.class, idGrupo);
			hibernateTemplate.delete(grupoParaExcluir);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
