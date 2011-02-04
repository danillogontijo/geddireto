package br.org.ged.direto.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Anotacao;
import br.org.ged.direto.model.repository.AnotacaoRepository;
import br.org.ged.direto.model.service.AnotacaoService;

@Service("anotacaoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class AnotacaoServiceImpl implements AnotacaoService {

	
	@Autowired
	private AnotacaoRepository anotacaoRepository;

	@Override
	public List<Anotacao> getAnotacaoByDocumento(Integer idDocumentoDetalhes) {
		//return anotacaoRepository.getAnotacaoByDocumento(idDocumentoDetalhes);
		
		System.out.println("ANOTACOES: ");
		
		List<Anotacao> r = new ArrayList<Anotacao>();
		
		Anotacao a = new Anotacao();
		a.setAnotacao("teste");
		
		r.add(a);
		
		return r; 
	}

	@Override
	public List<Anotacao> getAnotacaoByUsuario(Integer idUsuario) {
		return anotacaoRepository.getAnotacaoByUsuario(idUsuario);
	}

	@Override
	public void save(Anotacao anotacao) {
		anotacaoRepository.save(anotacao);
	}
}
