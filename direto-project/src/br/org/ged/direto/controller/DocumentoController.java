package br.org.ged.direto.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import java.util.Iterator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Despacho;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.service.AnotacaoService;
import br.org.ged.direto.model.service.DespachoService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.HistoricoService;
import br.org.ged.direto.model.service.SegurancaService;

@Controller
//@SessionAttributes("documentoError")
public class DocumentoController extends BaseController {
	
	@Autowired
	private DocumentosService documentosService;
	
	/*@Autowired
	private MessageSource messageSource;*/
	
	@Autowired
	DocumentoError documentoError;
	
	@Autowired
	private SegurancaService segurancaService;
	
	@Autowired
	private AnotacaoService anotacaoService;
	
	@Autowired
	private DespachoService despachoService;
	
	@Autowired
	private HistoricoService historicoService;
	
	private DocumentoDetalhes documentoDetalhes;
	
	
	@RequestMapping(value="/documento.html",method = RequestMethod.GET)
	public String showDocument(@RequestParam("pk")Integer pk, ModelMap model){
		
		Documento doc_conta = this.documentosService.selectById(pk);
		//DocumentoDetalhes documento = doc_conta.getDocumentoDetalhes();
		
		documentoDetalhes = doc_conta.getDocumentoDetalhes();
		
		if (doc_conta.getStatus() == '0')
			documentosService.setDocumentoStatus(doc_conta.getIdDocumento(), '1');
		
		model.addAttribute("idDocumento",documentoDetalhes.getIdDocumentoDetalhes());
		model.addAttribute("documento",documentoDetalhes);
		//model.addAttribute("doc_conta",doc_conta);
		model.addAttribute("usuarioElaborador",documentoDetalhes.getUsuarioElaborador());
		
		Set<Anexo> listAnexos = documentoDetalhes.getAnexos();
		
		Anexo principal = null;
		
		for(Anexo anexo : listAnexos){
			String[] nome = anexo.getAnexoCaminho().split("_");
			String nomeCompleto = anexo.getAnexoNome();
			if (nomeCompleto.length() > 60){
				nomeCompleto = nomeCompleto.substring(0, 50)+"..."+nomeCompleto.substring(nomeCompleto.length()-10, nomeCompleto.length());
				anexo.setAnexoNome(nomeCompleto);
			}
			
			if (nome[0].equals("1"))
				principal = anexo;
			
			
			
			String sha1 = "Ocorreu erro";
			try {
				File file = new File("/home/danillo/users/sgt.danillo/"+anexo.getAnexoCaminho());
				sha1 = segurancaService.sh1withRSA(file);
				anexo.setHash(sha1);
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		
		int proximoAnexo = 1;
		
		if (listAnexos != null)
			proximoAnexo = listAnexos.size()+1;
		
		
		if (principal == null){
			model.addAttribute("documento_principal", "Sem documento");
			//model.addAttribute("sha1", "Sem hash");
			model.addAttribute("proximoAnexo",1);
		}else{
			model.addAttribute("documento_principal", principal);
			listAnexos.remove(principal);
			model.addAttribute("sha1", principal.getHash());
			model.addAttribute("anexos",listAnexos);
			model.addAttribute("proximoAnexo",proximoAnexo);
		}
		
		
		return "documento";
	}
	
	@RequestMapping(value="/view.html",method = RequestMethod.GET)
	public String showByIdDocumentoDetalhes(@RequestParam("id")Integer id, ModelMap model){
	
		documentoDetalhes = documentosService.getDocumentoDetalhes(id);
		
		model.addAttribute("idDocumento",documentoDetalhes.getIdDocumentoDetalhes());
		model.addAttribute("documento",documentoDetalhes);
		model.addAttribute("usuarioElaborador",documentoDetalhes.getUsuarioElaborador());
		
		Set<Anexo> listAnexos = documentoDetalhes.getAnexos();
		
		Anexo principal = null;
		
		for(Anexo anexo : listAnexos){
			String[] nome = anexo.getAnexoCaminho().split("_");
			if (nome[0].equals("1"))
				principal = anexo;
		}
		
		model.addAttribute("proximoAnexo",listAnexos.size()+1);
		
		if (principal == null){
			model.addAttribute("documento_principal", "Sem documento");
		}else{
			model.addAttribute("documento_principal", principal);
			listAnexos.remove(principal);
		}
		
		model.addAttribute("anexos",listAnexos);
		
		
		String sha1 = "Ocorreu erro";
		
		try {
			File file = new File("/home/danillo/users/sgt.danillo/"+principal.getAnexoCaminho());
			
			sha1 = segurancaService.sh1withRSA(file);
			
		}catch(Exception e){
			
		}
		
		model.addAttribute("sha1", sha1);
		
		return "documento";

	
	}
	
		
	@ExceptionHandler(DocumentNotFoundException.class)
	public ModelAndView handlerDocumentNotFoundException(DocumentNotFoundException ex){
		 System.out.println("["+DocumentNotFoundException.class.getName()+"] "+ ex.getMessage());
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("error", ex.getMessage());
		return mav;
	}
	
	@ModelAttribute("allDocuments")
	public ArrayList<Documento> allDocumentsById(@RequestParam("id")Integer id){
		ArrayList<Documento> list = (ArrayList<Documento>) documentosService.getAllById(id);
		
		System.out.println("TAMANHO DA LISTA=="+list.size());
		return list;
	}
	
	@ModelAttribute("anotacoes")
	public Collection<Anotacao> anotacoes(@RequestParam("id")Integer id) {
    	
		List<Anotacao> results = this.anotacaoService.getAnotacaoByDocumento(id); 
		
        return results;
        
    }
	
	@ModelAttribute("despachos")
	public Collection<Despacho> despachos(@RequestParam("id")Integer id) {
    	
        return (List<Despacho>)this.despachoService.getDespachoByDocumento(id);
        
    }
	
	@ModelAttribute("historico")
	public Collection<Historico> historico(@RequestParam("id")Integer id) {
    	
        return (List<Historico>)this.historicoService.getHistoricoByDocumento(id);
        
    }
	
	

}
