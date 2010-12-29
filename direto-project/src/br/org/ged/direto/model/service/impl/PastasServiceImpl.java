package br.org.ged.direto.model.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.repository.PastasRepository;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.PastasService;

@Service("pastasService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class PastasServiceImpl implements PastasService {

	@Autowired
	private PastasRepository pastasRepository;
	
	@Autowired
	private DocumentosService documentosService;
		
	@Override
	public Collection<Pastas> getAll() {
		return (List<Pastas>) pastasRepository.getAll();
	}

	@Override
	public Pastas getPastaById(Integer id) {
		return pastasRepository.getPastaById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Pastas> pastasComNrDocumentos(int idCarteira){
		
		List<Pastas> pastas = (ArrayList<Pastas>) pastasRepository.getAll();
		Iterator<Pastas> ite = pastas.iterator();
		
		while (ite.hasNext()){
			
			Pastas pasta = new Pastas();
			pasta = ite.next();
			long total = this.documentosService.counterDocumentsByBox(String.valueOf(pasta.getIdPasta()),idCarteira,null);
			String nomePasta = pasta.getNomePasta()+" ("+total+")";
			pasta.setNomePasta(nomePasta);
			System.out.println(nomePasta);
		}
		
		return pastas;
		
	}

}
