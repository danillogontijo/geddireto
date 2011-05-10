package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.org.direto.util.DocumentosUtil;
import br.org.direto.webchat.ChatService;
import br.org.direto.webchat.Message;
import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.entity.menus.MenuTopo;
import br.org.ged.direto.model.service.PastasService;
import br.org.ged.direto.model.service.UsuarioService;
import br.org.ged.direto.model.service.menus.IMenuTopo;


@Controller
public abstract class BaseController {

	final int LIMITE_POR_PAGINA = DocumentosUtil.LIMITE_POR_PAGINA;
	
	protected HttpSession session;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private PastasService pastasService;
	
	@Autowired
	private IMenuTopo menuTopo;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@ModelAttribute("numUsers")
	public int getNumberOfUsers() {
		return sessionRegistry.getAllPrincipals().size();
	}
	
	@ModelAttribute("usuario")
	public Usuario getUserLogon(HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		this.session = request.getSession(true);
		System.out.println("\n\nSESSAO CONTROLER"+this.session.toString());
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		return usuario;
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

	@ModelAttribute("menuTopo")
	public Collection<MenuTopo> menuTopo() {
		Collection<MenuTopo> menu = new ArrayList<MenuTopo>();
		
		try {
			menu = menuTopo.filterMenuTopo(menuTopo.getMenuTopo());
			
			//menu = menuTopo.getMenuTopo();
			
		}catch(Exception e){
			//System.out.println();
			e.printStackTrace();
		}
		
		//System.out.println(menu.toString());
		
		return menu;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handlerDocumentNotFoundException(Exception ex){
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("error", ex.getMessage());
		return mav;
	}
		
}
