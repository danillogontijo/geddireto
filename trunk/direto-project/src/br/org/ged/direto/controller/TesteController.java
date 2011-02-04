package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.SessionAttributes;

import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.service.AnotacaoService;
import br.org.ged.direto.model.service.PstGradService;
import br.org.ged.direto.model.service.UsuarioService;
;

@Controller
@RequestMapping("/teste.html")
@SessionAttributes("usuario")
public class TesteController {
	
		private PstGradService pstgradService;
		private UsuarioService usuarioService;
		private AnotacaoService anotacaoService;
		
		@Autowired
		public void setPstgradService(PstGradService pstgradService) {
			this.pstgradService = pstgradService;
		}

		@Autowired
		public void setUsuarioService(UsuarioService usuarioService) {
			this.usuarioService = usuarioService;
		}
		
		@Autowired
		public void setAnotacaoService(AnotacaoService anotacaoService) {
			this.anotacaoService = anotacaoService;
		}

		@ModelAttribute("pstgradList")
		public Collection<PstGrad> populatePstGradTypes() {
	       
	        return this.pstgradService.getAll();
	    }
		
		@ModelAttribute("anotacoes")
		public Collection<Anotacao> anotacoes() {
	       
			
			
			 //this.anotacaoService.getAnotacaoByDocumento(new Integer(13)); 
			
			//System.out.println("ANOTACOES: "+r.size());
			
			List<Anotacao> results = new ArrayList<Anotacao>();
			
	        return results;
	        
	        
	        
	    }

		@RequestMapping(method = RequestMethod.GET)
		public String setupForm(ModelMap model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		     
			//model.addAttribute("usuario", (Usuario) auth.getPrincipal());
			model.addAttribute("usuario", this.usuarioService.selectByLogin(auth.getName()));
			//model.addAttribute("anotacoes", attributeValue)
			
			 this.anotacaoService.getAnotacaoByDocumento(new Integer(13));
			
			return "teste";
		}

		@RequestMapping(method=RequestMethod.POST)
		public String submitChangePasswordPage(@RequestParam("password") String newPassword) {
						
			return "redirect:principal.html";
		}



}
