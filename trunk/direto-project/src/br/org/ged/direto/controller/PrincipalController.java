package br.org.ged.direto.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;



import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.Pastas;

import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.menus.MenuTopo;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.PastasService;
import br.org.ged.direto.model.service.UsuarioService;
import br.org.ged.direto.model.service.menus.IMenuTopo;
import br.org.ged.direto.model.service.menus.MenuTopoImpl;

@Controller
@RequestMapping("/principal.html")
@SessionAttributes({"usuario", "mt"})
public class PrincipalController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DocumentosService documentosService;
	
	@Autowired
	private PastasService pastasService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private IMenuTopo menuTopo;
	
	private HttpSession session;
	
	@ModelAttribute("numUsers")
	public int getNumberOfUsers() {
		return sessionRegistry.getAllPrincipals().size();
	}
	
	@ModelAttribute("pastas")
	public Collection<Pastas> todasPastas() {
		
		List<Pastas> pastas = new ArrayList<Pastas>();
		
		pastas = pastasService.getAll(); 
		
		return pastas;
	}
	
	@ModelAttribute("DocDWR")
	public Collection<DataUtils> docDWR(HttpServletRequest request) {
		
		this.session = request.getSession(true);
		
		Integer idCarteira = new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
		
		return documentosService.listDocumentsFromAccount(idCarteira);
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
	
	/*public Map<String,String> menuTopo() {
		
		
		Map<String, String> map = new HashMap<String, String>();
	    map.put("Passar Conta", "passar_conta.jsp");
	    map.put("Dados Cadastrais", "dados_cadastro.jsp?modo=ver");
	    map.put("Configurações", "configuracao.jsp");
	    //Set<?> set = map.entrySet();
		
		
		
		//List<String> menuTopo = new ArrayList<String>();
		
		
		
		return map;
	}*/
	
	@ModelAttribute("documentos")
	public Collection<Documento> todosDocumentos(HttpServletRequest request) {
		
		this.session = request.getSession(true);
		
		Integer idCarteira = new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
		
		//String usuLogin = (String)session.getAttribute("usuLogin");
		
		//System.out.println("j_conta DOCUMENTOS: "+session.getAttribute("j_usuario_conta"));
		
		//Integer idCarteira = new Integer(2);
		
		List<Documento> docsByConta = new ArrayList<Documento>();
		
		docsByConta = this.documentosService.listByLimited(idCarteira);
		
		//System.out.println(documentosService.listDocumentsFromAccount(new Integer(2)).toString());
		
		
		return docsByConta;
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String showUserDetails(@RequestParam("box") int box, ModelMap model, HttpServletRequest request) {
		
		if (box == 0)
			box = 1;
		
		//String request.getParameter("");
		
		//System.out.println(this.documentosService.selectByIdCarteira(new Integer(2)));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		this.session = request.getSession(true);
		
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		//Usuario usuario = this.usuarioService.selectById(usuarioTemp.getIdUsuario());
		
		System.out.println("j_conta: "+session.getAttribute("j_usuario_conta"));
		
		//PstGrad pstgrad = usuario.getPstGrad();
		
		//Iterator<PstGrad> ite_pstgrad = usuario.getPstGrad().iterator();
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("box",box);
		model.addAttribute("contaAtual", session.getAttribute("j_usuario_conta"));
		//model.addAttribute(pstgrad);
		//model.addAttribute(usuarioService.listActivedContas(auth.getName()));
		
		//model.addAttribute(auth.getCredentials());
		
		//System.out.println(auth.toString());
		System.out.println(usuario.getContas().size());
		
				
		return "principal";
	
	}
	
	
}




