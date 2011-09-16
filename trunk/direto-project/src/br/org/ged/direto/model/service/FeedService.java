package br.org.ged.direto.model.service;

import java.util.Set;

import br.org.ged.direto.model.entity.Usuario;

public interface FeedService {
	public Set<Usuario> usuariosMencionados(String acao);
	public String formatarMencionados(String acao);
}
