package br.org.ged.direto.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springsource.json.writer.JSONArray;
import com.springsource.json.writer.JSONObject;

import br.org.direto.util.DataTimeUtil;
import br.org.ged.direto.controller.PesquisaController;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.UsuarioService;

@Controller
public class AdminUsuariosController {

	@Autowired
	private UsuarioService usuarioService;
	
	@ModelAttribute("usuarios")
	public List<Usuario> allUsers() {
		return usuarioService.getAll(new Usuario());
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/admin/usuarios.html")
	public String showAllUsers(ModelMap model){
		
		
		
		return "admin/usuarios";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/buscaUsuarios.html")
	public void usuariosJSON(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) throws IOException{
		
		PrintWriter writer = response.getWriter();;
		
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		List<Usuario> usuarios = allUsers();
		
		for(Usuario usuario : usuarios){
			JSONArray ja = new JSONArray();
			
			ja.put(usuario.getPstGrad().getPstgradNome()+" "+usuario.getUsuNGuerra());
			ja.put(usuario.getUsuLogin());
			array.put(ja);
			
		}
		
		response.setHeader("Content-Type", "application/json; charset=ISO-8859-1");
			
		/*result.put("iTotalRecords", total);
		result.put("iTotalDisplayRecords", totalAfterFilter);*/
		
		result.put("users", array);
		
		writer.print(result);
		
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/admin/cadastros/usuarios.html")
	public String addNewUser(){
		
		return "admin/cadastros/usuarios";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/admin/editar/usuarios.html")
	public String editUser(){
		
		return "";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/admin/carteiras/usuarios.html")
	public String vincularCarteira(){
		
		return "";
	}
}
