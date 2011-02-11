package br.org.ged.direto.controller.json;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.ged.direto.controller.BaseController;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.Notificacao;
import br.org.ged.direto.model.service.DocumentosService;

@Controller
@RequestMapping("/notificacoes.html")
public class NotificacoesController extends BaseController {

	@Autowired
	private DocumentosService documentosService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String show(@RequestParam("id") int id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		Integer idCarteira = this.getIdCarteiraFromSession(request);
		
		Documento doc_cart = documentosService.selectById(id, idCarteira);
		
		String notificacoes = "";
		
		if (doc_cart.getNotificar() == 1){
			Date ultimaVerificacaoNotificacao = doc_cart.getDataHoraNotificacao();
			Set<Notificacao> notficacaoes = doc_cart.getNotificacoes();
			Iterator<Notificacao> ite = notficacaoes.iterator();
			
			while(ite.hasNext()){
				Notificacao not = ite.next();
				if (not.getDataHoraNotificacao().after(ultimaVerificacaoNotificacao)){
					notificacoes = notificacoes+not.getNotificacao()+"<br>";
	 			}
			}
			
		}
		
		Date now = new Date();
		documentosService.setDataNotificacao(now, id, idCarteira);
		

		model.addAttribute("id", id);
		model.addAttribute("notificacoes", notificacoes);
		
		return "notificacoes";
	}
	

}
