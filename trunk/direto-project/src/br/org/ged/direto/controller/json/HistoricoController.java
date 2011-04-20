package br.org.ged.direto.controller.json;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.service.HistoricoService;

@Controller
@RequestMapping("/historico.html")
public class HistoricoController {
	
	@Autowired
	private HistoricoService historicoService;

	@ModelAttribute("historico")
	public Collection<Historico> anotacoes(@RequestParam("id")Integer id) {
    	return (List<Historico>)this.historicoService.getHistoricoByDocumento(id);
    }
	
	@RequestMapping(method = RequestMethod.GET)
	public String show() {
		return "historico";
	}
	
}
