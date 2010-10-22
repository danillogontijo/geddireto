package br.org.direto.util;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.ged.direto.model.service.UsuarioService;
import br.org.ged.direto.model.service.impl.UsuarioServiceImpl;

public class UsuarioUtil {
	
	private static UsuarioService usuarioService = new UsuarioServiceImpl();
	
	public static String whoUser(int userid){
		return usuarioService.whoUser(userid);
	}

}
