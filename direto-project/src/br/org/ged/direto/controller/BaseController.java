package br.org.ged.direto.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.direto.util.DocumentosUtil;
import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.PastasService;


@Controller
public abstract class BaseController {

	final int LIMITE_POR_PAGINA = DocumentosUtil.LIMITE_POR_PAGINA;
	
	protected HttpSession session;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private PastasService pastasService;
	
	@ModelAttribute("numUsers")
	public int getNumberOfUsers() {
		return sessionRegistry.getAllPrincipals().size();
	}
	
	public Integer getIdCarteiraFromSession(HttpServletRequest request){
		this.session = request.getSession(true);
		return new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
	}
		
	@ModelAttribute("pastas")
	public Collection<Pastas> todasPastas(HttpServletRequest request){
		Integer idCarteira = this.getIdCarteiraFromSession(request);
		return this.pastasService.pastasComNrDocumentos(idCarteira);
	}
	
	@ModelAttribute("contaAtual")
	public int getIdCarteira(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario o = (Usuario) auth.getPrincipal();
		return o.getIdCarteira();
	}
	
	public Usuario getUsuarioLogado(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (Usuario) auth.getPrincipal();
	}

	
		
}
