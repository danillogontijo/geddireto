package br.org.ged.direto.controller.json;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.service.AnotacaoService;

@Controller
@RequestMapping("/anotacoes.html")
public class AnotacaoController {
	
	@Autowired
	private AnotacaoService anotacaoService;
	
	@ModelAttribute("anotacoes")
	public Collection<Anotacao> anotacoes(@RequestParam("id")Integer id) {
    	return (List<Anotacao>)this.anotacaoService.getAnotacaoByDocumento(id); 
    }
	
	/*@ModelAttribute("anotacoesAfterDate")
	public Collection<Anotacao> anotacoesAfterDate(@RequestParam("id")Integer id, @RequestParam("date")String date_pt_br) throws ParseException {
    	return (List<Anotacao>)this.anotacaoService.getAnotacaoAfterDate(id, date_pt_br); 
    }*/
	
	@RequestMapping(method = RequestMethod.GET)
	public String show() {
		return "anotacoes";
	}

}
