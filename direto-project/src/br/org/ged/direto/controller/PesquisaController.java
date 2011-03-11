package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.org.ged.direto.controller.forms.PesquisaForm;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.TipoDocumento;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.TipoDocumentoService;

@Controller
//@RequestMapping("/pesquisar.html")
//@SessionAttributes({"pesquisaForm"})
public class PesquisaController extends BaseController {
	
	private static final String NAME_OBJ_COMMAND = "pesquisaForm";
	
	@Autowired
	private DocumentosService documentoService;
	
	@Autowired
	private CarteiraService carteiraService;
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	@ModelAttribute("tiposDocumentos")
	public List<TipoDocumento> tiposDocumentos() {
		return tipoDocumentoService.getAllList();
	}	
	
	@RequestMapping(method=RequestMethod.GET,value="/pesquisar.html")
	public String pesquisar(ModelMap model) {		
		
		PesquisaForm form = new PesquisaForm();
		List<DocumentoCompleto> docs = new ArrayList<DocumentoCompleto>();
		
		model.addAttribute(NAME_OBJ_COMMAND, form);
		model.addAttribute("docs", docs);
		
		return "pesquisar";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/resultado.html")
	public String resultado(@ModelAttribute(NAME_OBJ_COMMAND) PesquisaForm form,ModelMap model) {		
		
		Usuario obj = getUsuarioLogado();
		
		Carteira carteira = carteiraService.selectById(obj.getIdCarteira());
		
		form.setCarteira(carteira);
		form.setPapel(obj.getUsuPapel());
		
		Set<DocumentoCompleto> docs = documentoService.returnSearch(form);
		
		model.addAttribute("docs", docs);
		
		return "json/pesquisaResultado";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/resultado.html")
	public String resultadoJSON(ModelMap model) {		
		
		PesquisaForm form = new PesquisaForm();
		
		Usuario obj = getUsuarioLogado();
		
		Carteira carteira = carteiraService.selectById(obj.getIdCarteira());
		
		form.setCarteira(carteira);
		form.setPapel(obj.getUsuPapel());
		
		Set<DocumentoCompleto> docs = documentoService.returnSearch(form);
		
		model.addAttribute("docs", docs);
		
		return "json/pesquisaResultado";
	}

}
