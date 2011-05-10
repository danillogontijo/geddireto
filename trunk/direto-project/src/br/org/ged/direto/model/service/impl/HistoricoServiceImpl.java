package br.org.ged.direto.model.service.impl;

import java.util.Date;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.HistoricoRepository;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.HistoricoService;

@Service("historicoService")
@RemoteProxy(name = "historicoJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class HistoricoServiceImpl implements HistoricoService {

	@Autowired
	private HistoricoRepository historicoRepository;
	
	@Autowired
	private DocumentosService documentoService;
	
	@Autowired
	private CarteiraService carteiraService;
	
	@Override
	public List<Historico> getHistoricoByCarteira(Integer idCarteira) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Historico> getHistoricoByDocumento(Integer idDocumentoDetalhes) {
		return historicoRepository.getHistoricoByDocumento(idDocumentoDetalhes);
	}

	@Override
	public List<Historico> getHistoricoByUsuario(Integer idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RemoteMethod
	@Transactional(readOnly=false)
	public void save(int idDocumentoDetalhes, String txtHistorico) {
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = (Usuario) auth.getPrincipal();
			
			Carteira carteira = carteiraService.selectById(usuario.getIdCarteira()); 
			
			DocumentoDetalhes documento = documentoService.getDocumentoDetalhes(idDocumentoDetalhes);
			
			Historico historico = new Historico();
			historico.setCarteira(carteira);
			historico.setDataHoraHistorico(new Date());
			historico.setHistorico(txtHistorico);
			historico.setDocumentoDetalhes(documento);
			historico.setUsuario(usuario);
			
			historicoRepository.save(historico);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.SUPPORTS)
	public void save(Historico historico) {
		historicoRepository.save(historico);
	}

}
