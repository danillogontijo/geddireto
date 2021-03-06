package br.org.ged.direto.model.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Feed;
import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.AnotacaoRepository;
import br.org.ged.direto.model.service.AnotacaoService;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.FeedService;
import br.org.ged.direto.model.service.HistoricoService;
import br.org.ged.direto.model.service.UsuarioService;

@Service("anotacaoService")
@RemoteProxy(name = "anotacaoJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class AnotacaoServiceImpl implements AnotacaoService {

	
	@Autowired
	private AnotacaoRepository anotacaoRepository;
	
	@Autowired
	private DocumentosService documentoService;
	
	@Autowired
	private CarteiraService carteiraService;
	
	@Autowired
	private HistoricoService historicoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private FeedService feedService;

	@Override
	public List<Anotacao> getAnotacaoByDocumento(Integer idDocumentoDetalhes) {
		return anotacaoRepository.getAnotacaoByDocumento(idDocumentoDetalhes);
		
		
	}

	@Override
	public List<Anotacao> getAnotacaoByUsuario(Integer idUsuario) {
		return anotacaoRepository.getAnotacaoByUsuario(idUsuario);
	}

	@Override
	@RemoteMethod
	@Transactional(readOnly=false)
	public void save(int idDocumentoDetalhes, String txtAnotacao) {
		try{
			
			Set<Conta> contasMencionadas = feedService.contasMencionadas(txtAnotacao);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuarioLogado = (Usuario) auth.getPrincipal();
			Usuario usuario = usuarioService.selectById(usuarioLogado.getIdUsuario());
			
			Carteira carteira = carteiraService.selectById(usuarioLogado.getIdCarteira()); 
			
			DocumentoDetalhes documento = documentoService.getDocumentoDetalhes(idDocumentoDetalhes);
			
			Date data = new Date();
			
			String txtAnotacaoTemp = feedService.formatarMencionados(txtAnotacao);
			
			Anotacao anotacao = new Anotacao();
			anotacao.setCarteira(carteira);
			anotacao.setDataHoraAnotacao(data);
			anotacao.setAnotacao(txtAnotacaoTemp);
			anotacao.setDocumentoDetalhes(documento);
			anotacao.setUsuario(usuario);
			
			anotacao.setIdAnotacao(anotacaoRepository.save(anotacao));
			
			if(contasMencionadas.size()>0){
			
				for(Conta c : contasMencionadas){
					Feed feed = new Feed();
					//feed.setAcao("1_"+anotacao.getIdAnotacao());
					feed.setAcao("<b>[Anotação]</b> "+txtAnotacaoTemp);
					feed.setCarteira(c.getCarteira());
					feed.setCarteiraRem(carteira);
					feed.setDataHora(data);
					feed.setDocumentoDetalhes(documento);
					feed.setIdAnotacao(anotacao.getIdAnotacao());
					feed.setIdDespacho(0);
					feed.setUsuario(c.getUsuario());
					feed.setUsuarioRem(usuario);
					
					feedService.save(feed);
				}
				
				//Feed do usuário que esta enviando
				Feed feed = new Feed();
				feed.setAcao("<b>[Anotação]</b> "+txtAnotacaoTemp);
				feed.setCarteira(carteira);
				feed.setCarteiraRem(carteira);
				feed.setDataHora(data);
				feed.setDocumentoDetalhes(documento);
				feed.setIdAnotacao(anotacao.getIdAnotacao());
				feed.setIdDespacho(0);
				feed.setUsuario(usuario);
				feed.setUsuarioRem(usuario);
				
				feedService.save(feed);
			
			}
			
			String txtHistorico = "(Anotação) - ";
					txtHistorico += usuario.getPstGrad().getPstgradNome()+" "+usuario.getUsuNGuerra();
					
			Historico historico = new Historico();
			historico.setCarteira(carteira);
			historico.setDataHoraHistorico(data);
			historico.setHistorico(txtHistorico);
			historico.setDocumentoDetalhes(documento);
			historico.setUsuario(usuario);
			
			historicoService.save(historico);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Anotacao> getAnotacaoAfterDate(Integer idDocumentoDetalhes,
			String date_pt_br) throws ParseException {
		
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");  
		Date date = (Date)formatter.parse(date_pt_br);  
		
		List<Anotacao> list = anotacaoRepository.getAnotacaoByDocumento(idDocumentoDetalhes);
		
		Iterator<Anotacao> ite = list.iterator();
		
		while(ite.hasNext()){
			Anotacao anot = ite.next();
			if (!anot.getDataHoraAnotacao().after(date)){
				ite.remove();
				//list.remove(index);
 			}
		}
		
		return list;
	}
}
