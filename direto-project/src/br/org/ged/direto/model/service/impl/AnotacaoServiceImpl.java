package br.org.ged.direto.model.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.entity.Notificacao;
import br.org.ged.direto.model.repository.AnotacaoRepository;
import br.org.ged.direto.model.service.AnotacaoService;

@Service("anotacaoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class AnotacaoServiceImpl implements AnotacaoService {

	
	@Autowired
	private AnotacaoRepository anotacaoRepository;

	@Override
	public List<Anotacao> getAnotacaoByDocumento(Integer idDocumentoDetalhes) {
		return anotacaoRepository.getAnotacaoByDocumento(idDocumentoDetalhes);
		
		
	}

	@Override
	public List<Anotacao> getAnotacaoByUsuario(Integer idUsuario) {
		return anotacaoRepository.getAnotacaoByUsuario(idUsuario);
	}

	@Override
	public void save(Anotacao anotacao) {
		anotacaoRepository.save(anotacao);
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
