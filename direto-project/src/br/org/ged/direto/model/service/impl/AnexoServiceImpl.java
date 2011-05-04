package br.org.ged.direto.model.service.impl;

import java.util.Date;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.AnexoRepository;
import br.org.ged.direto.model.service.AnexoService;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.HistoricoService;

@Service("anexoService")
@RemoteProxy(name = "anexoJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class AnexoServiceImpl implements AnexoService {

	
	@Autowired
	private AnexoRepository anexoRepository;
	
	@Autowired
	private DocumentosService documentosService;
	
	@Autowired
	private CarteiraService carteiraService;
	
	@Autowired
	private HistoricoService historicoService;
	
	@Override
	@RemoteMethod
	@Transactional(readOnly = false)
	public void saveAnexo(String anexoNome, String anexoCaminho,
			int idDocumentoDetalhes, boolean isAssign, boolean saveInHistorico) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					
			int assinado = (isAssign == true ? 1 : 0);
			DocumentoDetalhes documento = documentosService.getDocumentoDetalhes(idDocumentoDetalhes);
			Anexo anexoToSave = new Anexo();
			anexoToSave.setAnexoNome(anexoNome);
			anexoToSave.setAnexoCaminho(anexoCaminho);
			anexoToSave.setDocumentoDetalhes(documento);
			anexoToSave.setAssinado(assinado);
			anexoToSave.setAssinadoPor(auth.getName());
			
			saveAnexo(anexoToSave);
			
			if(saveInHistorico){
				
				Usuario usuario = (Usuario) auth.getPrincipal();
				Carteira carteira = carteiraService.selectById(usuario.getIdCarteira()); 
				
				Date data = new Date();
				
				String txtHistorico = "(Anexo) - "+anexoNome+" - ";
				txtHistorico += usuario.getUsuLogin();
				
				Historico historico = new Historico();
				historico.setCarteira(carteira);
				historico.setDataHoraHistorico(data);
				historico.setHistorico(txtHistorico);
				historico.setDocumentoDetalhes(documento);
				historico.setUsuario(usuario);
				
				historicoService.save(historico);
				
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

	@Override
	@Transactional(readOnly = false)
	public void saveAnexo(Anexo anexo) {
		anexoRepository.saveAnexo(anexo);
	}
}
