
package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.SessionAttributes;

import br.org.direto.util.DocumentosUtil;
import br.org.direto.util.Protocolo;
import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.entity.TipoDocumento;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.AnotacaoService;
import br.org.ged.direto.model.service.PstGradService;
import br.org.ged.direto.model.service.UsuarioService;
;


//@RequestMapping("/teste2.html")
//@SessionAttributes("usuario")
@Controller
public class TesteController extends BaseController {
	
	@Autowired
	Protocolo p1;
	
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
	
	@RequestMapping(method=RequestMethod.GET,value="/teste.html")
	public String show(){
			
			Usuario user = super.getUsuarioLogado();
			
			//Protocolo p1 = new Protocolo();
			
			Thread t1 = new Thread(p1);
			t1.setName("threadTo"+user.getUsuLogin());
			t1.start(); 
			
			
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