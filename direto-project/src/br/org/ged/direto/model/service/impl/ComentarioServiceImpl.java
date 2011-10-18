package br.org.ged.direto.model.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataUtils;
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

	@Override
	@RemoteMethod
	public List<DataUtils> showAllComments() {
		List<DataUtils> dados = new ArrayList<DataUtils>(); 
		try{
			List<Comentario> comentarios = comentarioRepository.getAllComments();
			for(Comentario c : comentarios){
				DataUtils data = new DataUtils();
				data.setId(""+c.getIdComentario());
				String texto = "<b>"+c.getUsuario().getUsuLogin()+" ["+c.getDataHoraComentario()+"]</b> - "+
				""+c.getComentario();
				data.setTexto(texto);
				
				dados.add(data);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return dados;
	}

}
