package br.org.ged.direto.model.repository.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.UsuarioException;
import br.org.ged.direto.model.repository.UsuarioRepository;

@Repository("usuarioRepository")
public class UsuarioRepositoryImpl extends BaseRepositoryImpl implements UsuarioRepository {

	@Override
	public void save(Usuario usuario) {
		hibernateTemplate.saveOrUpdate(usuario);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getAll(Usuario usuario) {
		return (List<Usuario>) hibernateTemplate.find("from "
				+ Usuario.class.getName());
	}

	@Override
	public Usuario selectById(Integer idUsuario) {
		return hibernateTemplate.get(Usuario.class, idUsuario);
	}

	@Override
	public Usuario selectByLogin(final String usuLogin) {
		return (Usuario) hibernateTemplate.execute(new HibernateCallback<Usuario>() {
			@Override
			public Usuario doInHibernate(org.hibernate.Session session)
					throws HibernateException, SQLException {
				return (Usuario) session.createQuery(
				"from Usuario where usuLogin = :usuLogin")
				.setParameter("usuLogin", usuLogin).uniqueResult();
			}
		});
	}

	@Override
	public List<DataUtils> listActivedContas(String usuLogin) {
		
		List<DataUtils> list = new ArrayList<DataUtils>();
		Usuario u = this.selectByLogin(usuLogin);
		
		Iterator<Conta> ite_conta = u.getContas().iterator();
			
		for(int i = 0; i < u.getContas().size(); i++){
			Conta c = ite_conta.next();
			DataUtils dados = new DataUtils();
			dados.setId(String.valueOf(c.getCarteira().getIdCarteira()));
			dados.setTexto(c.getCarteira().getCartAbr()+"["+c.getCarteira().getOm().getOmAbr()+"]");
				
			list.add(dados);
				
			System.out.println(dados.getId());
			System.out.println(dados.getTexto());
			//System.out.println(c.getCarteira().getCartAbr());
		}
			
		return list;//(List<Conta>)hibernateTemplate.find("from Usuario u, Conta c where u.usuLogin = ? and c.ativado = 1", usuLogin);
	}

	@Override
	public void changePassword(String usuLogin, String usuSenha) {
				
	}

	@Override
	public String whoUser(int userid) {
		
		String sql = "SELECT usuLogin FROM Usuario u WHERE u.idUsuario = ?";
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		query.setInteger(0, new Integer(userid));
		
		return query.uniqueResult().toString();
	}

	public boolean checkIfUserIsDuplicate(String usuLogin, int idUsuario){
		Usuario user = null;
		try {
			user = selectByLogin(usuLogin);
			
			if (user.getIdUsuario() != idUsuario)
				throw new UsuarioException("Este login de usuário já existe!");
			
		}catch (UsuarioException e){
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			//e.printStackTrace();
			return (user == null ? true : false);
		}
		
		return true;
	}

}
