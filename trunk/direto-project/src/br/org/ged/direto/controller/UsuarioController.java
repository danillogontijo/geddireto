package br.org.ged.direto.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.UsuarioService;

@Controller
@RequestMapping("/usuarioTeste.html")
@SessionAttributes("usuario")
public class UsuarioController {

	private UsuarioService usuarioService;
	//private PstGradService pstgradService;
	
	@Autowired
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	/*@Autowired
	public void setPstgradService(PstGradService pstgradService) {
		this.pstgradService = pstgradService;
	}

	@ModelAttribute("pstgradAll")
	public Collection<PstGrad> populatePstGrad() {
		//PERSISTIR PSTGRAD
		//List<PstGrad> listPstGrad = new ArrayList<PstGrad>();
		//listPstGrad.add("Books");
		return pstgradService.getAll(new PstGrad(new Integer(1), "", ""));
	}*/

	@RequestMapping(method = RequestMethod.GET)
	public String showUserForm(@RequestParam("id") Integer id, ModelMap model) {
		Usuario usuario; 
		if(id == 0)
			usuario = new Usuario();
			else
//				usuario = this.usuarioService.selectById(id);
			usuario = this.usuarioService.selectByLogin("sgt.danillo2");
		model.addAttribute(usuario);
		
		//System.out.println(this.usuarioService.selectById(1));
		
		//modelMap.addAttribute("listPstGrad", populatePstGrad());
		//model.addAttribute(usuario);
		return "usuarioForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("usuario") Usuario usuario) throws Exception {
		usuarioService.save(usuario);
		return "redirect:usuarioSuccesso.html";
	}

}
