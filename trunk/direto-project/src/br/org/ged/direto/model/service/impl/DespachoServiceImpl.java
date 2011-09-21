package br.org.ged.direto.model.service.impl;

import java.util.Date;
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

import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Despacho;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Feed;
import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.DespachoRepository;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DespachoService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.FeedService;
import br.org.ged.direto.model.service.HistoricoService;
import br.org.ged.direto.model.service.UsuarioService;

@Service("despachoService")
@RemoteProxy(name = "despachoJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DespachoServiceImpl implements DespachoService {

	
	@Autowired
	private DespachoRepository despachoRepository;
	
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
	public List<Despacho> getDespachoByDocumento(Integer idDocumentoDetalhes) {
		return despachoRepository.getDespachoByDocumento(idDocumentoDetalhes);
	}

	@Override
	public List<Despacho> getDespachoByUsuario(Integer idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RemoteMethod
	@Transactional(readOnly=false)
	public void save(int idDocumentoDetalhes, String txtDespacho, int idUsuarioDestinatario) {
		try{
			
			Set<Conta> contasMencionadas = feedService.contasMencionadas(txtDespacho);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuarioLogado = (Usuario) auth.getPrincipal();
			Usuario usuario = usuarioService.selectById(usuarioLogado.getIdUsuario());
			
			Carteira carteira = carteiraService.selectById(usuarioLogado.getIdCarteira());
			
			DocumentoDetalhes documento = documentoService.getDocumentoDetalhes(idDocumentoDetalhes);
			
			Date data = new Date();
			
			String txtDespachoTemp = feedService.formatarMencionados(txtDespacho);
			
			Despacho despacho = new Despacho();
			despacho.setCarteira(carteira);
			despacho.setDataHoraDespacho(data);
			despacho.setDespacho(txtDespachoTemp);
			despacho.setDocumentoDetalhes(documento);
			despacho.setUsuario(usuario);
			despacho.setIdUsuarioDestinatario(idUsuarioDestinatario);
			
			despacho.setIdDespacho(despachoRepository.save(despacho));
			
			for(Conta c : contasMencionadas){
				Feed feed = new Feed();
				feed.setAcao("<b>[Despacho]</b> "+txtDespachoTemp);
				feed.setCarteira(c.getCarteira());
				feed.setCarteiraRem(carteira);
				feed.setDataHora(data);
				feed.setDocumentoDetalhes(documento);
				feed.setIdAnotacao(0);
				feed.setIdDespacho(despacho.getIdDespacho());
				feed.setUsuario(c.getUsuario());
				feed.setUsuarioRem(usuario);
				
				feedService.save(feed);
			}
			
			String txtHistorico = "(Despacho) - ";
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
	public Despacho selectDespacho(int idDespacho) {
		return despachoRepository.selectDespacho(idDespacho);
	}
}
