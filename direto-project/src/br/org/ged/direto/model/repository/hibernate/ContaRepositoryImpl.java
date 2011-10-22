package br.org.ged.direto.model.repository.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.ContaRepository;

@Repository("contaRepository")
public class ContaRepositoryImpl extends BaseRepositoryImpl implements ContaRepository {
	
	@Override
	public void saveOrUpdate(Conta conta) {
		hibernateTemplate.saveOrUpdate(conta);
	}

	public List<Usuario> listarUsuariosPorConta(Integer idCarteira){
		String sql = "FROM Conta c inner join c.carteira carteira WHERE carteira.idCarteira = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		query.setInteger(0, idCarteira);
		
		return null;
	}

	@Override
	public void deleteAccount(int idConta) {
		Conta conta = hibernateTemplate.load(Conta.class, idConta);
		hibernateTemplate.delete(conta);
	}
	
	@Override
	public void deleteAllPrincipalAccounts(int idCarteira) {
		getSession().createSQLQuery("DELETE FROM usuomsec WHERE idCarteira="+idCarteira+" and contaPrincipal=1").executeUpdate();  
	}

	@Override
	public void deleteAccount(int idUsuario, int idCarteira) {
		hibernateTemplate.delete(getAccount(idUsuario,idCarteira));
	}

	@Override
	public Conta getAccount(int idConta) {
		return hibernateTemplate.get(Conta.class, idConta);
	}

	@Override
	public Conta getAccount(int idUsuario, int idCarteira) {
		String sql = "FROM Conta c WHERE c.usuario.idUsuario = ? AND c.carteira.idCarteira = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		query.setInteger(0, idUsuario);
		query.setInteger(1, idCarteira);
		
		return (Conta) query.uniqueResult();
	}

	@Override
	public void updateAccount(Conta conta) {
		Conta account = getAccount(conta.getIdConta());
		account.setAtivado(conta.getAtivado());
		account.setCarteira(conta.getCarteira());
		account.setContaPrincipal(conta.getContaPrincipal());
		account.setUsuario(conta.getUsuario());
	}

	@Override
	public void updateAccount(int idUsuario, int idCarteira) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Conta> getContasPrincipais(int idUsuarioProprietario) {
		String sql = "FROM Conta c WHERE c.idUsuarioProprietario = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		query.setInteger(0, idUsuarioProprietario);
		
		List<Conta> contas = query.list();
		List<Conta> contasResp = new ArrayList<Conta>();
		Conta conta;
		if(contas.size() > 0){
			conta = contas.get(0);
			
			sql = "FROM Conta c WHERE c.carteira.idCarteira = ?";
			
			query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
			query.setInteger(0, conta.getCarteira().getIdCarteira());
			contas = query.list();
			for(Conta c : contas)
				if(!c.isPrincipal())
					contasResp.add(c);
			
		}
		
		return contasResp;
	}

	
}
