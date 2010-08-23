package br.org.ged.direto.model.repository.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
import br.org.direto.util.HibernateUtil;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.UsuarioRepository;

//This will make easier to autowired
@Repository("usuarioRepository")
// Default is read only
@Transactional
public class UsuarioRepositoryImpl implements UsuarioRepository {

	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Transactional(readOnly = false)
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
		// System.out.println(hibernateTemplate.getMaxResults());
		// hibernateTemplate.get("br.ged.direto.domain.Usuario", idUsuario);
		return hibernateTemplate.get(Usuario.class, idUsuario);
	}

	@Override
	public Usuario selectByLogin(final String usuLogin) {
		/*Object obj = new Object();
		  Session session = HibernateUtil.getSessionFactory().openSession();  
		  
		  if(obj instanceof Usuario){
		    obj = (Usuario)(session.createQuery(
			"from Usuario where usuLogin = :usuLogin")
			.setParameter("usuLogin", usuLogin).uniqueResult());
		  }
		  
		  return (Usuario) obj;*/
		  
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

	@SuppressWarnings("unchecked")
	@Override
	public List<DataUtils> listActivedContas(String usuLogin) {
		
		List<DataUtils> list = new ArrayList(); //= new ArrayList<Usuario>();
		Usuario u = this.selectByLogin(usuLogin);
		
		//lu = hibernateTemplate.find("from Usuario u, Conta c where u.usuLogin = ? and c.ativado = 1", usuLogin);
		
		//lu = hibernateTemplate.find("from Usuario u where u.usuLogin = ?", usuLogin);
		
		
		
				
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
			
			
			//System.out.println(u.getPstGrad().getPstgradDesc());
			
		
		//System.out.println(list);
		
		
		
		return list;//(List<Conta>)hibernateTemplate.find("from Usuario u, Conta c where u.usuLogin = ? and c.ativado = 1", usuLogin);
	}

	@Override
	public void changePassword(String usuLogin, String usuSenha) {
				
	}

}
