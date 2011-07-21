package br.org.ged.direto.model.service.impl;

import java.util.Date;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Comentario;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.ComentarioRepository;
import br.org.ged.direto.model.service.ComentarioService;

@Service("comentarioService")
@RemoteProxy(name = "comentarioJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Override
	@RemoteMethod
	@Transactional(readOnly = false)
	public void save(String comentario) {
		Comentario comentarioParaSalvar = new Comentario();
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = (Usuario) auth.getPrincipal();
			comentarioParaSalvar.setComentario(comentario);
			comentarioParaSalvar.setDataHoraComentario(new Date());
			comentarioParaSalvar.setUsuario(usuario);
			comentarioRepository.save(comentarioParaSalvar);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
