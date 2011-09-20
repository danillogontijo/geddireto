package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Feed;
import br.org.ged.direto.model.service.FeedService;

@Controller
public class FeedController extends BaseController {
	
	@Autowired
	private FeedService feedService;
	
	@ModelAttribute("feeds")
	public Map<DocumentoDetalhes,List<Feed>> feedCollection(HttpServletRequest request, ModelMap model) {
		
		int filter = 1;
		
		if(request.getParameter("filter") != null)
			filter = Integer.parseInt(request.getParameter("filter"));
		
		model.addAttribute("filter",filter);
		
		Map<DocumentoDetalhes,List<Feed>> map = new HashMap<DocumentoDetalhes,List<Feed>>();
	    List<Feed> feeds = feedService.selectFeeds(filter);
		Set<DocumentoDetalhes> documentos = new HashSet<DocumentoDetalhes>();
		
		for(Feed f : feeds)
			documentos.add(f.getDocumentoDetalhes());
		
		for(DocumentoDetalhes d : documentos){
			List<Feed> feedsPorDocumento = new ArrayList<Feed>();
			for(Feed f : feeds){
				if(d.equals(f.getDocumentoDetalhes())){
					feedsPorDocumento.add(f);
				}
			}
			map.put(d, feedsPorDocumento);
		}
		
		return map;
	}
	
	
	@RequestMapping(value="/feed.html", method = RequestMethod.GET)
	public String showFeedPrincipal(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("box",7);
		return "feed";
	}

}
