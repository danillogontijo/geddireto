package br.org.ged.direto.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;



import br.org.direto.util.DataUtils;
import br.org.direto.util.DocumentosUtil;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.Pastas;

import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.menus.MenuTopo;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.GruposService;
import br.org.ged.direto.model.service.PastasService;
import br.org.ged.direto.model.service.UsuarioService;
import br.org.ged.direto.model.service.menus.IMenuTopo;
import br.org.ged.direto.model.service.menus.MenuTopoImpl;

@Controller
@RequestMapping("/principal.html")
//@SessionAttributes({"usuario", "mt","pastas","menuTopo","box"})
@SessionAttributes({"box"})
public class PrincipalController extends BaseController {
	
	@Autowired
	private DocumentosService documentosService;
	
	/*@Autowired
	private PastasService pastasService;*/
	
	@Autowired
	private GruposService gruposService;
	
	
	@ModelAttribute("grupos")
	public Collection<DataUtils> gruposCarteira(HttpServletRequest request) {
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
	public Collection<DataUtils> docDWR(@RequestParam("box") String box,@RequestParam("filtro") String filtro,@RequestParam("pr")int pr, HttpServletRequest request, ModelMap model) {
		Integer idCarteira = this.getIdCarteiraFromSession(request);
		
		List<DataUtils> docList = documentosService.listDocumentsFromAccount(idCarteira,0,pr,box,filtro);
		
		
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
		
		
		this.session = request.getSession(true);
		
		Integer idCarteira = new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
		
		//String usuLogin = (String)session.getAttribute("usuLogin");
		
		//System.out.println("j_conta DOCUMENTOS: "+session.getAttribute("j_usuario_conta"));
		
		//Integer idCarteira = new Integer(2);
		
		List<Documento> docsByConta = new ArrayList<Documento>();
		
		docsByConta = this.documentosService.listByLimited(idCarteira);
		
		//System.out.println(documentosService.listDocumentsFromAccount(new Integer(2)).toString());
		
		
		return docsByConta;
	}
	
	
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

	
	@RequestMapping(method = RequestMethod.GET)
	public String showPrincipal(@RequestParam("box") int box, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if (box == 0)
			box = 1;
		
		model.addAttribute("box",box);
						
		return "principal";
	
	}
	
	
}




