package br.org.ged.direto.model.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Feed;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.FeedRepository;
import br.org.ged.direto.model.service.ContaService;
import br.org.ged.direto.model.service.FeedService;
import br.org.ged.direto.model.service.UsuarioService;

@Service("feedService")
public class FeedServiceImpl implements FeedService {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private FeedRepository feedRepository;
	
	@Override
	public Set<Usuario> usuariosMencionados(String acao) {
		Set<Usuario> users = new HashSet<Usuario>();
		String arAcao[] = acao.split("\\s+");
		for(String w : arAcao){
			if(w.length() != 0){
				if( w.charAt(0) == '[' && w.charAt(w.length()-1) == ']' ){
					String sU[] = w.split("\\@");
					users.add(usuarioService.selectById(Integer.parseInt(sU[0].substring(1))));
				}
			}
		}
		return users;
	}

	@Override
	public String formatarMencionados(String acao) {
		StringBuffer sb = new StringBuffer();
		String arAcao[] = acao.split("\\s+");
		for(String w : arAcao){
			if(w.length() != 0){
				if( w.charAt(0) == '[' && w.charAt(w.length()-1) == ']' ){
					String sU[] = w.split("\\@");
					sb.append( (sU[1].substring(0,sU[1].length()-1)+" ").replaceAll("\\-", "\\ ") );
				}else
					sb.append(w+" ");
			}
		}
		return sb.toString();
	}

	@Override
	public List<Feed> selectFeeds(int filter) {
		return feedRepository.selectFeeds(filter);
	}

	@Override
	public Set<Conta> contasMencionadas(String acao) {
		Set<Conta> contas = new HashSet<Conta>();
		String arAcao[] = acao.split("\\s+");
		for(String w : arAcao){
			
			if(w.length() != 0){
				if( w.charAt(0) == '[' && w.charAt(w.length()-1) == ']' ){
					String sU[] = w.split("\\@");
					String ids[] = sU[0].split("\\_");
					int idUsuario = Integer.parseInt(ids[0].substring(1));
					int idCarteira = Integer.parseInt(ids[1]);
					contas.add(contaService.getAccount(idUsuario, idCarteira));
				}
			}
		}
		return contas;
	}

	@Override
	public Integer save(Feed feed) {
		return feedRepository.save(feed);
	}

}
