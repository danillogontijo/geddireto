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
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.Pastas;

import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.menus.MenuTopo;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.PastasService;
import br.org.ged.direto.model.service.UsuarioService;
import br.org.ged.direto.model.service.menus.IMenuTopo;
import br.org.ged.direto.model.service.menus.MenuTopoImpl;

@Controller
@RequestMapping("/principal.html")
@SessionAttributes({"usuario", "mt"})
public class PrincipalController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DocumentosService documentosService;
	
	@Autowired
	private PastasService pastasService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private IMenuTopo menuTopo;
	
	private HttpSession session;
	
	@ModelAttribute("numUsers")
	public int getNumberOfUsers() {
		return sessionRegistry.getAllPrincipals().size();
	}
	
	
	@ModelAttribute("pastas")
	public Collection<Pastas> todasPastas(@RequestParam("pr")int pr,@RequestParam("box") String box,HttpServletRequest request,ModelMap model) {
		
		
		
		//Integer idCarteira = new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
		
		/*Map<Long,Pastas> pastaMapped = new LinkedHashMap<Long,Pastas>();
		
		Set<Pastas> pastas = new LinkedHashSet<Pastas>();
		pastas = (Set<Pastas>) pastasService.getAll(); 
		
		Iterator<Pastas> ite = pastas.iterator();
		
		while (ite.hasNext()){
			
			Pastas pasta = new Pastas();
			pasta = ite.next();
			
			pastaMapped.put(documentosService.counterDocumentsByBox(pasta.getIdPasta().toString(), (idCarteira.intValue()*1000*pasta.getIdPasta())), pasta);
			
		}*/
		 
		
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
			long total = this.documentosService.counterDocumentsByBox(String.valueOf(pasta.getIdPasta()), idCarteira);
			String nomePasta = pasta.getNomePasta()+" ("+total+")";
			pasta.setNomePasta(nomePasta);
		}
		
		return pastas;
	}
	
	public Integer getIdCarteiraFromSession(HttpServletRequest request){
		this.session = request.getSession(true);
		return new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
	}
	
	@ModelAttribute("DocDWR")
	public Collection<DataUtils> docDWR(@RequestParam("box") String box,@RequestParam("pr")int pr, HttpServletRequest request) {
		Integer idCarteira = this.getIdCarteiraFromSession(request);
		return documentosService.listDocumentsFromAccount(idCarteira,0,pr,box);
	}
	
	@ModelAttribute("menuTopo")
	public Collection<MenuTopo> menuTopo() {
		Collection<MenuTopo> menu = new ArrayList<MenuTopo>();
		
		try {
			menu = menuTopo.filterMenuTopo(menuTopo.getMenuTopo());
			
			//menu = menuTopo.getMenuTopo();
			
		}catch(Exception e){
			//System.out.println();
			e.printStackTrace();
		}
		
		//System.out.println(menu.toString());
		
		return menu;
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
	public String showUserDetails(@RequestParam("box") int box, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if (box == 0)
			box = 1;
		
		//String request.getParameter("");
		
		//System.out.println(this.documentosService.selectByIdCarteira(new Integer(2)));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		this.session = request.getSession(true);
		
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		//Usuario usuario = this.usuarioService.selectById(usuarioTemp.getIdUsuario());
		
		System.out.println("j_conta: "+session.getAttribute("j_usuario_conta"));
		
		//PstGrad pstgrad = usuario.getPstGrad();
		
		//Iterator<PstGrad> ite_pstgrad = usuario.getPstGrad().iterator();
		
		
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("box",box);
		model.addAttribute("contaAtual", session.getAttribute("j_usuario_conta"));
		//model.addAttribute(pstgrad);
		//model.addAttribute(usuarioService.listActivedContas(auth.getName()));
		
		//model.addAttribute(auth.getCredentials());
		
		//System.out.println(auth.toString());
		System.out.println(usuario.getContas().size());
		
		
		/*File file;
		FileInputStream in = null;
		//FileOutputStream out;
		//response.
		
		try {
			
			file = new File("/home/danillo/teste.odt");
			
			byte[] buffer = getBytesFromFile(file);
			
			//System.out.println(buffer.length);
			
			//response.setHeader("Content-Length", String.valueOf(buffer.length));
			
			//response.setContentType("application/vnd.oasis.opendocument.text");
			
			
			//String fullName = file.getPath();
			 		
			  
		     String pathName = file.getPath();//response.getServletContext().getRealPath ( "/" + fullName) ; 
		     String contentType = request.getSession().getServletContext().getMimeType(pathName) ; 
		  
		  
		     if  ( contentType != null )  
		         response.setContentType (contentType) ; 
		     else 
		         response.setContentType ("application/octet-stream") ; 

		     System.out.println(contentType);
			
			//in = new FileInputStream(file);
			//out = new FileOutputStream(file);
		     
		     response.setContentType("application/vnd.oasis.opendocument.text");
		     response.setHeader("Content-disposition","attachment; filename=" + file.getName() );
			ServletOutputStream sout = response.getOutputStream(); 
			
			//in.r
	        
	        int bytesRead = 0;

	        do
	        {
	                bytesRead = in.read(buffer);
	            	System.out.println(bytesRead);
	                response.getOutputStream().write(buffer);
	        }
	        while (bytesRead == buffer.length);

			
			sout.write(buffer);
			sout.flush();
			
	       //file.
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
	    {
	        if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }*/
		
				
		return "principal";
	
	}
	
	
}




