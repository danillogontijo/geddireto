package br.org.ged.direto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.org.ged.direto.controller.forms.DocumentoForm;

@Controller
@RequestMapping("/criarDocumento.html")
public class NewDocumentFormController {

	@Autowired
	private DocumentoForm documentoForm;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showLoginForm(ModelMap model) {
		
		model.addAttribute(documentoForm);
		
		return "criarDocumento";
	}
	
}
