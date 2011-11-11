package br.org.ged.direto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestParam;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import br.org.direto.util.DataTimeUtil;
import br.org.ged.direto.controller.forms.PesquisaForm;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.TipoDocumento;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.TipoDocumentoService;

@Controller
public class PesquisaController extends BaseController {
	
	@Autowired
	private DocumentosService documentoService;
	
	@Autowired
	private CarteiraService carteiraService;
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	@ModelAttribute("tiposDocumentos")
	public List<TipoDocumento> tiposDocumentos() {
		return tipoDocumentoService.getAllList();
	}	
	
	@RequestMapping(method=RequestMethod.GET,value="/pesquisar.html")
	public String pesquisar(HttpServletRequest request,ModelMap model) {		
		
		PesquisaForm form = new PesquisaForm();
		Usuario obj = super.getUsuarioLogado();
		
		Carteira carteira = carteiraService.selectById(obj.getIdCarteira());
		
		form.setCarteira(carteira);
		form.setPapel(obj.getUsuPapel());
		
		this.session = request.getSession(true);
		
		int total = documentoService.returnTotalSearch(form);
		
		if(this.session.getAttribute("totalPesquisa") == null || this.session.getAttribute("totalPesquisa") == "" ){
			total = documentoService.returnTotalSearch(form);
			this.session.setAttribute("totalPesquisa", String.valueOf(total));
		} else {
			total = Integer.parseInt((String) this.session.getAttribute("totalPesquisa"));
		}
		
		
		
		boolean bServerSide = (total > 4000 ? true : false);
		model.addAttribute("bServerSide",bServerSide);
		model.addAttribute("total",total);
		
		return "pesquisar";
	}
	
	/*@RequestMapping(method=RequestMethod.POST,value="/resultado.html")
	public String resultado(@ModelAttribute(NAME_OBJ_COMMAND) PesquisaForm form,ModelMap model) {		
		
		Usuario obj = getUsuarioLogado();
		
		Carteira carteira = carteiraService.selectById(obj.getIdCarteira());
		
		form.setCarteira(carteira);
		form.setPapel(obj.getUsuPapel());
		
		List<DocumentoCompleto> docs = (List<DocumentoCompleto>) documentoService.returnSearch(form);
		
		model.addAttribute("docs", docs);
		
		return "json/pesquisaResultado";
	}*/
	
	@SuppressWarnings("unused")
	@RequestMapping(method=RequestMethod.GET,value="/resultado.html")
	public void resultadoJSON(@RequestParam("total") int total,HttpServletRequest request,
			HttpServletResponse response,ModelMap model) throws UnsupportedEncodingException, JSONException {		
		
		PesquisaForm form = new PesquisaForm();
		Usuario obj = super.getUsuarioLogado();
		
		Carteira carteira = carteiraService.selectById(obj.getIdCarteira());
		
		form.setCarteira(carteira);
		form.setPapel(obj.getUsuPapel());
		form.setTotal(total);
		
		PrintWriter writer = null;
		
		try {
			writer = response.getWriter();
		} catch (IOException ex) {
			System.err.println(PesquisaController.class.getName() + "ocorreu uma exceção: "	+ ex.getMessage());
		}
		
		String[] cols = { "details.idDocumentoDetalhes", "details.tipoDocumento.tipoDocumentoNome", "details.nrProtocolo", "details.nrDocumento", "details.assunto", "details.dataEntSistema" };
		
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		int amount = 5;
		int start = 0;
		int echo = 0;
		int col = 0;
		
		String id = "";
		String tipo = "";
		String protocolo = "";
		String nrDoc = "";
		String assunto = "";
		String data = "";

		String dir = "asc";
		String sStart = request.getParameter("iDisplayStart");
		String sAmount = request.getParameter("iDisplayLength");
		String sEcho = request.getParameter("sEcho");
		String sCol = request.getParameter("iSortCol_0");
		String sdir = request.getParameter("sSortDir_0");
		boolean bServerSide = (((String)request.getParameter("bServerSide")).equals("true")?true:false);
		form.setServerSide(bServerSide);
		
		//id = request.getParameter("sSearch_0");
		
		tipo = request.getParameter("sSearch_0");
		if(tipo != null)
			form.setTipoDocumento(tipo);
		
		protocolo = request.getParameter("sSearch_1");
		if(protocolo != null)
			form.setNrProtocol(protocolo);
		
		nrDoc = request.getParameter("sSearch_2");
		if(nrDoc != null)
			form.setNrDocumento(nrDoc);
		
		assunto = request.getParameter("sSearch_3");
		if(assunto != null)
			form.setAssunto(assunto);
		
		data = request.getParameter("sSearch_4");
		if(data != null)			
			form.setDataEntSistema(DataTimeUtil.convertKeyUpFormatBrazilToUS(data));
			
		if (sStart != null) {
			start = Integer.parseInt(sStart);
			if (start < 0)
				start = 0;
		}
		form.setStart(start);
		
		if (sAmount != null) {
			amount = Integer.parseInt(sAmount);
			if (amount < 5 || amount > 100)
				amount = 10;
		}
		form.setAmount(amount);
		
		if (sEcho != null) {
			echo = Integer.parseInt(sEcho);
		}
		form.setEcho(echo);
		
		if (sCol != null) {
			col = Integer.parseInt(sCol);
			if (col < 0 || col > 6)
				col = 0;
		}
		form.setCol(col);
		
		if (sdir != null) {
			if (!sdir.equals("asc"))
				dir = "desc";
		}
		form.setDir(dir);
		
		String colName = cols[col];
		form.setColName(colName);
		
		String searchTerm = request.getParameter("sSearch");
		if (searchTerm != null)
			form.setSearchTerm(DataTimeUtil.convertKeyUpFormatBrazilToUS(searchTerm));

		
		int totalAfterFilter = total;
		
		List<DocumentoCompleto> docs = (List<DocumentoCompleto>) documentoService.returnSearch(form);
		
		if (searchTerm != "" || form.getIndividualSearch()!="")
			totalAfterFilter = docs.size() > 0 ? docs.get(0).getTotal() : 0;
		
		/*for( int i=0; i<docs.size(); i++ ){
			DocumentoCompleto dc = docs.remove(i);
			
			JSONArray ja = new JSONArray();
			ja.put(String.valueOf(dc.getDocumentoDetalhes().getIdDocumentoDetalhes()));
			ja.put(dc.getDocumentoDetalhes().getTipoDocumento().getTipoDocumentoNome());
			ja.put(dc.getDocumentoDetalhes().getNrProtocolo());
			ja.put(dc.getDocumentoDetalhes().getNrDocumento());
			ja.put(dc.getDocumentoDetalhes().getAssunto());
			ja.put(DataTimeUtil.getBrazilFormatDataHora(dc.getDocumentoDetalhes().getDataEntSistema()));
			array.put(ja);
		}*/
		
		Iterator<DocumentoCompleto> ite = docs.iterator();
		
		while (ite.hasNext()) {
			DocumentoCompleto dc = ite.next();
			
			JSONArray ja = new JSONArray();
			ja.put(String.valueOf(dc.getDocumentoDetalhes().getIdDocumentoDetalhes()));
			ja.put(dc.getDocumentoDetalhes().getTipoDocumento().getTipoDocumentoNome());
			ja.put(dc.getDocumentoDetalhes().getNrProtocolo());
			ja.put(dc.getDocumentoDetalhes().getNrDocumento());
			ja.put(dc.getDocumentoDetalhes().getAssunto());
			ja.put(DataTimeUtil.getBrazilFormatDataHora(dc.getDocumentoDetalhes().getDataEntSistema()));
			array.put(ja);
		}
		
		docs = null;
		
		response.setHeader("Content-Type", "application/json; charset=ISO-8859-1");
			
		result.put("iTotalRecords", total);
		result.put("iTotalDisplayRecords", totalAfterFilter);
		result.put("aaData", array);
		
		writer.print(result);
		
		writer.flush();
		writer.close();
		
	}

}


