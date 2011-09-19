package br.org.ged.direto.model.repository.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import br.org.ged.direto.model.entity.Feed;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.FeedRepository;

@Repository("feedRepository")
public class FeedRepositoryImpl extends BaseRepositoryImpl implements FeedRepository {
	
	@Override
	public List<Feed> selectFeeds(int filter){
		List<Feed> feeds = null;
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = (Usuario)auth.getPrincipal();
			
			String filtro = "";
			if(filter==1)
				filtro = "f.usuario.idUsuario="+usuario.getIdUsuario();
			else if(filter==2)
				filtro = "f.carteira.idCarteira="+usuario.getIdCarteira();
			else
				filtro = "f.carteira.idCarteira="+usuario.getIdCarteira()+" and f.usuario.idUsuario="+usuario.getIdUsuario();
			
			String sQuery = "from Feed as f where "+filtro+" order by f.dataHora desc";
			
			Query query = getSession().createQuery(sQuery);
			
			feeds = query.list();
	
			for(Feed f : feeds){
				System.out.println(f.getIdFeed()+"-"+f.getCarteira().getCartAbr());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
			
		return feeds;
	}

	@Override
	public Integer save(Feed feed) {
		return (Integer) hibernateTemplate.save(feed);
	}
}
