package br.org.ged.direto.model.service.impl;

import java.util.Date;
import java.util.Set;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataTimeUtil;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Notificacao;
import br.org.ged.direto.model.repository.NotificacaoRepository;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.NotificacaoService;

@Service("notificacaoService")
@RemoteProxy(name = "notificacaoJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class NotificacaoServiceImpl implements NotificacaoService {

	@Autowired
	private DocumentosService documentosService;
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	@Override
	@RemoteMethod
	@Transactional(readOnly=false)
	public void save(int idDocumentoDetalhes, String texto) {
	
		DocumentoDetalhes documentoDetalhes = documentosService.getDocumentoDetalhes(idDocumentoDetalhes);
		
		Set<Documento> documentos = documentoDetalhes.getDocumentosByCarteira();
	
		Date dataHoraNotificacao = new Date();
		for(Documento documento : documentos){
			if (documento.getNotificar() == 1){
				Notificacao notificacao = new Notificacao();
				notificacao.setDataHoraNotificacao(dataHoraNotificacao);
				notificacao.setDocumento(documento);
				notificacao.setNotificacao(texto);
				notificacaoRepository.save(notificacao);
			}
		}
		
	}

}
