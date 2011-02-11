package br.org.ged.direto.controller.json;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.ged.direto.model.entity.Despacho;
import br.org.ged.direto.model.service.DespachoService;

@Controller
@RequestMapping("/despachos.html")
public class DespachoController {
	
	@Autowired
	private DespachoService despachoService;
	
	@ModelAttribute("despachos")
	public Collection<Despacho> despachos(@RequestParam("id")Integer id) {
    	return (List<Despacho>)this.despachoService.getDespachoByDocumento(id); 
    }
	
	/*@ModelAttribute("despachosAfterDate")
	public Collection<despacho> despachosAfterDate(@RequestParam("id")Integer id, @RequestParam("date")String date_pt_br) throws ParseException {
    	return (List<despacho>)this.despachoService.getdespachoAfterDate(id, date_pt_br); 
    }*/
	
	@RequestMapping(method = RequestMethod.GET)
	public String show() {
		return "despachos";
	}

}
