
package br.org.ged.direto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import br.org.direto.util.DocumentosUtil;
import br.org.direto.util.Protocolo;
import br.org.direto.webchat.ChatService;
import br.org.direto.webchat.antigo.MensagemChat;
import br.org.direto.webchat.antigo.MensagemGenericaChat;
import br.org.direto.webchat.antigo.UsuarioChat;
import br.org.direto.webchat.antigo.UsuarioChatImpl;

import br.org.ged.direto.controller.forms.DocumentoForm;
import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.entity.TipoDocumento;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.service.AnotacaoService;
import br.org.ged.direto.model.service.PstGradService;
import br.org.ged.direto.model.service.UsuarioService;
;


//@RequestMapping("/teste2.html")
//@SessionAttributes("usuario")
@Controller
public class TesteController extends BaseController {
	
	/*@Autowired
	Protocolo p1;*/
	
	@Autowired
	ChatService chatService;
	
	@ModelAttribute("listaProtocolo")
	public List<DocumentoDetalhes> tiposDocumentos() {
		return DocumentosUtil.listaProtocolo;
	}
	
	/*public List<> mostraLista(){
		Iterator<DocumentoDetalhes> ite = DocumentosUtil.listaProtocolo.iterator();
		
		int c = 0;
		while (ite.hasNext()){
			
			System.out.println("INDEX: "+c+" - "+ite.next().getIdDocumentoDetalhes());
			c++;
		}
	}*/
	
	@ExceptionHandler(DocumentNotFoundException.class)
	public ModelAndView handlerDocumentNotFoundException(DocumentNotFoundException ex){
		 System.out.println("["+DocumentNotFoundException.class.getName()+"] "+ ex.getMessage());
		
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("error", ex.getMessage());
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/chat.html")
	public String chat(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException{
	
		String nomeUsuario = super.getUserLogon(request).getUsuLogin();
		
		try {
			UsuarioChat user = new UsuarioChatImpl(nomeUsuario);
			request.getSession().setAttribute("userChat", user);
			
		} catch (IllegalArgumentException iaex) {
			String erro = "Usuario ja logado no chat";
			model.addAttribute("error", erro);
		}
		
		final UsuarioChat user = (UsuarioChat) request.getSession(false).getAttribute("userChat");
		//chatService.setHttpServletRequest(request);
		//chatService.setHttpServletResponse(response);
		
		
		return "teste";
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/chatMensagens.html")
	public void chatMensagens(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException{
	
		final UsuarioChat user = (UsuarioChat) request.getSession(false).getAttribute("user");
		user.setResponse(response);
		user.setRequest(request);
		
		// envia a mensagem de entrada
		UsuarioChatImpl.sendMessage(
			new MensagemGenericaChat(
				user,
				"entrou na sala: "
					+ new SimpleDateFormat("hh:mm").format(new Date())));
		
		
		// dorme eternamente, ate uma excecao ser jogada pois o usuario saiu da sala
		while (user.isConnected()) {
			try {
				
				// espera pela proxima mensagem
				MensagemChat msg = user.getNewMessage();

				// checa a sessao
				user.checkSession();

				// se for uma mensagem, mostra ela, se nao, verifica a conexao
				if (msg != null) {
					user.showMessage(msg);
				} else {
					user.showMessage(null);
				}
				
			} catch (java.net.SocketException e) {
				//user.addLog("socketexception:\\n" + e.getMessage());
				user.disconnect();
			} catch (Exception e) {
				UsuarioChatImpl.sendMessage(
					new MensagemGenericaChat(user, e.getMessage()));
					//user.addLog("deu exception, desconectar: " + e.toString());
					user.disconnect();
			}

		}
		
		
		// tira o usuario da lista e tira sua sessao
		try {
			UsuarioChatImpl.removeUser(user);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// invalida a sessao
		try {
			session.invalidate();
		} catch (IllegalStateException e) {
			// a sessao ja estava invalidada
		}

		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/chatFormulario.html")
	public void chatFormularioPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		final UsuarioChat UsuarioChat = (UsuarioChat) request.getSession(false).getAttribute("user");
		
		if (request.getParameter("message") != null) {
			// se mensagem nao for vazia
			if (!request.getParameter("message").trim().equals("")) {
				// envia a mensagem (insira o codigo aqui)
			}
		}
		
		chatFormularioGet(request,response);
		
		MensagemChat msg = new MensagemGenericaChat(
				UsuarioChat,
				UsuarioChatImpl.getByName(request.getParameter("para")),
				request.getParameter("message")
				);
			// envia a mensagem
		try {
			UsuarioChatImpl.sendMessage(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/chatFormulario.html")
	public void chatFormularioGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		response.setHeader("Content-Type", "text/html; charset=ISO-8859-1");
		
		// pega o codigo de listagem de usuarios
		String codigoPara = "<option selected value=''>Todos</option>";
		Iterator i = new HashMap(UsuarioChatImpl.getUsers()).keySet().iterator();
		while (i.hasNext()) {
			String value = (String) i.next();
			codigoPara += "<option value='"
				+ value
				+ "'>"
				+ value
				+ "</option>";
		}

		// mostra o formulario
		try {
			response.getWriter().print(
				"<html><form name=chat action='"
					+ "chatFormulario.html"
					+ "' method=post>"
					+ "<input type=hidden name=show value=form>"
					+ "<input name=message> para <select name=para>"
					+ codigoPara
					+ "</select>"
					+ "<input type=submit value='Enviar'>"
					+ "</form>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.getWriter().flush();
		response.getWriter().close();

	}
	
	@RequestMapping(method=RequestMethod.GET,value="/teste.html")
	public void showChat(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		String nomeUsuario = super.getUserLogon(request).getUsuLogin();
		
		PrintWriter writer = null;
		
		try {
			writer = response.getWriter();
		} catch (IOException ex) {
			System.err.println(PesquisaController.class.getName() + "ocorreu uma exceção: "	+ ex.getMessage());
		}
		
		response.setHeader("Content-Type", "text/html; charset=ISO-8859-1");
		
		// cria um usuario
		try {
			UsuarioChat user = new UsuarioChatImpl(nomeUsuario);
			// seta o usuario na session
			request.getSession().setAttribute("user", user);
			// seta o tempo limite da session (10 minutos)
			request.getSession().setMaxInactiveInterval(10 * 60);
		} catch (IllegalArgumentException iaex) {
			// se ja existe um usuario, mostra a pagina de login denovo
			//response.getWriter().println("<html>Ja existe um usuario com esse login:<br><form action=LoginServlet><input name=login><input type=submit value=Login></form></html>");
			
			String erro = "Usuario ja logado no chat";
			model.addAttribute("error", erro);
			writer.print(erro);
			//return "error";
		}
		
		String result = ("<html><frameset rows=*,100><frame name='text' src='chatMensagens.html'><frame name='form' src='chatFormulario.html'></frameset></html>");
		
		writer.print(result);
		
		writer.flush();
		writer.close();
		
		//return "teste";
	}
	
	
	
	
	public String show(@ModelAttribute("documentoForm") DocumentoForm f){
		
			//DocumentoForm f = new DocumentoForm();
			//f.setNrDocumento("0000");
			//f.setAssinadoPor("assindoPor Teste");
			
			System.out.println(f.getDestinatarios());
			System.out.println(f.getDataDocumento());
			System.out.println(f.getNrDocumento());
			
			//p1.setFormulario(f);
			
			Usuario user = super.getUsuarioLogado();
			
			//Protocolo p1 = new Protocolo();
			
			/*Thread t1 = new Thread(p1);
			t1.setName("threadTo"+user.getUsuLogin());
			t1.start();*/ 
			
			
			/*Thread[] t = new Thread[5];
			
			for (int i=0;i<t.length;i++){
				t[i] = new Thread(p1);
				t[i].setName("t"+(i+1));
				t[i].start();
			}*/
			
			/*List<Integer> random = new ArrayList<Integer>();
		    for (int i = 0; i < t.length; ++i) {
		        random.add(i);
		    }
		    Collections.shuffle(random);
		    
		    // Imprimindo as primeiras 4 cartas que foram embaralhadas...
		    for (int i = 0; i < t.length; ++i) {
		        //System.out.println (random.get (i));
		    	int k = random.get(i);
		        
		        t[k] = new Thread(p1);
				t[k].setName("t"+(k+1));
				t[k].start();
		        
		    }*/
			
			return "teste";
		}



}