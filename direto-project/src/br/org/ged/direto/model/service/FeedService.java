package br.org.ged.direto.model.service;

import java.util.List;
import java.util.Set;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Feed;
import br.org.ged.direto.model.entity.Usuario;

public interface FeedService {
	public Set<Usuario> usuariosMencionados(String acao);
	public Set<Conta> contasMencionadas(String acao);
	public String formatarMencionados(String acao);
	public List<Feed> selectFeeds(int filter);
	public Integer save(Feed feed);
}
