package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import br.org.direto.util.DataUtils;
import br.org.direto.util.DocumentosUtil;
import br.org.direto.util.Utils;
import br.org.ged.direto.controller.forms.LoginForm;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.service.DocumentosService;

@Controller
@RequestMapping("/documento.html")
//@SessionAttributes("documentoError")
public class DocumentoController extends BaseController {
	
	@Autowired
	private DocumentosService documentosService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	DocumentoError documentoError;
	
	@ExceptionHandler(DocumentNotFoundException.class)
	public ModelAndView handlerDocumentNotFoundException(DocumentNotFoundException ex){
		 System.out.println("["+DocumentNotFoundException.class.getName()+"] "+ ex.getMessage());
		
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("error", ex.getMessage());
		return mav;
	}
	
	@ModelAttribute("allDocuments")
	public Map<Usuario,String> allDocumentsUsers(@RequestParam("id")Integer id){
		
		//Set<DataUtils> set = new HashSet<DataUtils>();
		Map<Usuario,String> map = new HashMap<Usuario,String>();
		ArrayList<Documento> list = (ArrayList<Documento>) documentosService.getAllById(id);
		
		Iterator<Documento> ite = list.iterator();
		while(ite.hasNext()){
			Documento doc = ite.next();
			String status = String.valueOf(doc.getStatus())+","+doc.getCarteira().getCartAbr();
			Set<Conta> contas = doc.getCarteira().getContas();
			
			Iterator<Conta> iteContas = contas.iterator();
			while(iteContas.hasNext()){
				Conta conta = iteContas.next();
				Usuario user = conta.getUsuario();
				map.put(user, status);
			}
			
		}
		
		
		return map;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showDocument(@RequestParam("id")Integer id, HttpServletRequest request, ModelMap model){
		
		
		Integer idCarteira = Utils.getIdCarteiraFromSession(request);
		Documento doc_conta = this.documentosService.selectById(id, idCarteira);
		DocumentoDetalhes documento = DocumentosUtil.returnDocument(id, idCarteira,doc_conta);
		//this.documentoError.setDocumento(DocumentosUtil.returnDocument(id, idCarteira,doc_conta));
		model.addAttribute("documento",documento);
		/*Set<Documento> todos = documento.getDocumentosByCarteira();
		model.addAllAttributes(todos);*/
		
		
		/*DocumentoError o = new DocumentoError();
		o.setDocumento(this.documento.getDocumento());
		Errors error = new BindException(o, "documento");
		
		if (o.getDocumento() == null){
			error.reject("documento","Documento nao encontrado.");
		}*/
		
		//errors.addAllErrors(errors);
		
		
		/*try{
			Integer idCarteira = Utils.getIdCarteiraFromSession(request);
			Documento doc_conta = this.documentosService.selectById(id, idCarteira);
			this.documentoError.setDocumento(DocumentosUtil.returnDocument(id, idCarteira,doc_conta));
			model.addAttribute(documentoError);
		}catch(Exception e){
			Errors error = new BindException("documentoError", "documentoError");
			error.reject("documentoError","documento.exception.notfound");
			br.addAllErrors(error);
			
			//return "error";
		}*/
		
		
		
		
		return "documento";
	}

}
