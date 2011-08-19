package br.org.ged.direto.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.exceptions.CarteiraException;

import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.GruposService;

@Controller
@RequestMapping("/principal.html")
//@SessionAttributes({"usuario", "mt","pastas","menuTopo","box"})
@SessionAttributes({"box"})
public class PrincipalController extends BaseController {
	
	@Autowired
	private DocumentosService documentosService;
	
	@Autowired
	private GruposService gruposService;
	
	
	@ModelAttribute("grupos")
	public Collection<DataUtils> gruposCarteira(HttpServletRequest request) throws CarteiraException {
		Integer idCarteira = this.getIdCarteiraFromSession(request);
		return gruposService.listGroups(idCarteira);
	}
	
	
	/*@ModelAttribute("pastas")
	public Collection<Pastas> todasPastas(HttpServletRequest request) {
		
		
		
		//Integer idCarteira = new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
		
		Map<Long,Pastas> pastaMapped = new LinkedHashMap<Long,Pastas>();
		
		Set<Pastas> pastas = new LinkedHashSet<Pastas>();
		pastas = (Set<Pastas>) pastasService.getAll(); 
		
		Iterator<Pastas> ite = pastas.iterator();
		
		while (ite.hasNext()){
			
			Pastas pasta = new Pastas();
			pasta = ite.next();
			
			pastaMapped.put(documentosService.counterDocumentsByBox(pasta.getIdPasta().toString(), (idCarteira.intValue()*1000*pasta.getIdPasta())), pasta);
			
		}
		 
		
		//return pastaMapped;
		Integer idCarteira = this.getIdCarteiraFromSession(request);
		
		int limitePorPagina = 2;
		long totalregs = this.documentosService.counterDocumentsByBox(box, idCarteira);
		int totalpgs = Math.round(totalregs / limitePorPagina);
		if ((totalregs % limitePorPagina) > 0)
			totalpgs++;
		
		int pageAtual = ((pr + limitePorPagina))/limitePorPagina;
		int nextPage = (pageAtual*limitePorPagina);
		int previousPage = (pageAtual*limitePorPagina)-(limitePorPagina);
		
		System.out.println("Next page: "+previousPage);
		
		
		model.addAttribute("totalRegs",totalregs);
		model.addAttribute("pages",totalpgs);
		model.addAttribute("page", pageAtual);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("previousPage", previousPage);
		model.addAttribute("limiteByPage", limitePorPagina);
		
		//System.out.println("IDCARTEIRA: "+idCarteira);
		
		List<Pastas> pastas = (ArrayList<Pastas>) pastasService.getAll();
		
		
		Iterator<Pastas> ite = pastas.iterator();
		
		while (ite.hasNext()){
			
			Pastas pasta = new Pastas();
			pasta = ite.next();
			long total = this.documentosService.counterDocumentsByBox(String.valueOf(pasta.getIdPasta()), idCarteira,null);
			String nomePasta = pasta.getNomePasta()+" ("+total+")";
			pasta.setNomePasta(nomePasta);
		}
		
		return pastas;
		
		
		return this.pastasService.pastasComNrDocumentos(idCarteira);
		
	}*/
	
	
	
	@ModelAttribute("DocDWR")
	public Collection<DataUtils> docDWR(@RequestParam("box") String box,@RequestParam("filtro") String filtro,@RequestParam("pr")int pr, HttpServletRequest request, ModelMap model) throws CarteiraException {
		
		String url = "?pr="+pr+"&box="+box+"&"+"filtro="+filtro+"&"+"ordenacao=";
		
		model.addAttribute("url", url);
		
		int ordenacao = 0;
		try{
			if(request.getParameter("ordenacao")!=null)
				ordenacao = Integer.parseInt(request.getParameter("ordenacao"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("ordenacao", ordenacao);
		
		Integer idCarteira = this.getIdCarteiraFromSession(request);
		
		List<DataUtils> docList = documentosService.listDocumentsFromAccount(idCarteira,ordenacao,pr,box,filtro);
		
		long totalregs = this.documentosService.counterDocumentsByBox(box, idCarteira,filtro);
		int totalpgs = Math.round(totalregs / LIMITE_POR_PAGINA);
		if ((totalregs % LIMITE_POR_PAGINA) > 0)
			totalpgs++;
		
		int pageAtual = ((pr + LIMITE_POR_PAGINA))/LIMITE_POR_PAGINA;
		int nextPage = (pageAtual*LIMITE_POR_PAGINA);
		int previousPage = (pageAtual*LIMITE_POR_PAGINA)-(LIMITE_POR_PAGINA);
		
		model.addAttribute("totalRegs",totalregs);
		model.addAttribute("pages",totalpgs);
		model.addAttribute("page", pageAtual);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("previousPage", previousPage);
		model.addAttribute("limiteByPage", LIMITE_POR_PAGINA);
		//model.addAttribute("docUtil", DocumentosUtil.documentos.size());
		
	/*	String s = "Danillo";
		
		System.out.println(s.hashCode());
		
		System.out.println("[Documentos Estaticos]: "+DocumentosUtil.documentos.toString());
		
		System.out.println("First key: "+DocumentosUtil.documentos.firstKey());
		System.out.println("Last key: "+DocumentosUtil.documentos.lastKey());*/
		
		model.addAttribute("filtro", filtro);
		
		return docList; 
	}
	
	
	
	/*public Map<String,String> menuTopo() {
		
		
		Map<String, String> map = new HashMap<String, String>();
	    map.put("Passar Conta", "passar_conta.jsp");
	    map.put("Dados Cadastrais", "dados_cadastro.jsp?modo=ver");
	    map.put("Configurações", "configuracao.jsp");
	    //Set<?> set = map.entrySet();
		
		
		
		//List<String> menuTopo = new ArrayList<String>();
		
		
		
		return map;
	}*/
	
	@ModelAttribute("documentos")
	public Collection<Documento> todosDocumentos(HttpServletRequest request) {
		Integer idCarteira = getIdCarteiraFromSession(request);
		List<Documento> docsByConta = new ArrayList<Documento>();
		docsByConta = this.documentosService.listByLimited(idCarteira);
		return docsByConta;
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String showPrincipal(@RequestParam("box") int box, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if (box == 0)
			box = 1;
		
		model.addAttribute("box",box);
						
		return "principal";
	
	}
	
	
}



