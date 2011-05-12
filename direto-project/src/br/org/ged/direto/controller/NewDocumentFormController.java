package br.org.ged.direto.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.org.direto.util.DataUtils;
import br.org.direto.util.DocumentosUtil;
import br.org.ged.direto.controller.forms.DocumentoForm;
import br.org.ged.direto.model.entity.TipoDocumento;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.TipoDocumentoService;

@Controller
@RequestMapping("/criarDocumento.html")
public class NewDocumentFormController extends BaseController {

	@Autowired
	private DocumentoForm documentoForm;
	
	@Autowired
	private DocumentosService documentoService;
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	@ModelAttribute("tiposDocumentos")
	public List<TipoDocumento> tiposDocumentos() {
		return tipoDocumentoService.getAllList();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showLoginForm(ModelMap model) {
		
		//Integer protocolo = DocumentosUtil.geraProtocolo(documentoService.getLastId());
		
		model.addAttribute("documentoForm",documentoForm);
		//model.addAttribute("protocolo",protocolo);
		
		return "criarDocumento";
	}
	
}
