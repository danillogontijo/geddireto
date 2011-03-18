
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
	
	@RequestMapping(method=RequestMethod.GET,value="/teste.html")
	public String show(){
		 
		return "teste";
	}

}