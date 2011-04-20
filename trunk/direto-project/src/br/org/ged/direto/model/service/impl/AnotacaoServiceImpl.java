package br.org.ged.direto.model.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.AnotacaoRepository;
import br.org.ged.direto.model.service.AnotacaoService;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.HistoricoService;

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
	public void save(int idDocumentoDetalhes, String txtAnotacao) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getPrincipal();
		
		Carteira carteira = carteiraService.selectById(usuario.getIdCarteira()); 
		
		DocumentoDetalhes documento = documentoService.getDocumentoDetalhes(idDocumentoDetalhes);
		
		Date data = new Date();
		
		Anotacao anotacao = new Anotacao();
		anotacao.setCarteira(carteira);
		anotacao.setDataHoraAnotacao(data);
		anotacao.setAnotacao(txtAnotacao);
		anotacao.setDocumentoDetalhes(documento);
		anotacao.setUsuario(usuario);
		
		anotacaoRepository.save(anotacao);
		
		String txtHistorico = "(Anotação) - ";
				txtHistorico += usuario.getPstGrad().getPstgradNome()+" "+usuario.getUsuNGuerra();
				
		Historico historico = new Historico();
		historico.setCarteira(carteira);
		historico.setDataHoraHistorico(data);
		historico.setHistorico(txtHistorico);
		historico.setDocumentoDetalhes(documento);
		historico.setUsuario(usuario);
		
		historicoService.save(historico);
		
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
