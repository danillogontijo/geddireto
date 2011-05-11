package br.org.ged.direto.model.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.IOUtils;
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
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Historico;
import br.org.ged.direto.model.entity.OM;
import br.org.ged.direto.model.entity.Secao;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.AnexoRepository;
import br.org.ged.direto.model.service.AnexoService;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.HistoricoService;
import br.org.ged.direto.model.service.UsuarioService;

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
			
			Anexo anexoToSave = new Anexo();
			anexoToSave.setAnexoNome(anexoNome);
			anexoToSave.setAnexoCaminho(anexoCaminho);
			anexoToSave.setDocumentoDetalhes(documento);
			anexoToSave.setAssinado(assinado);
			anexoToSave.setAssinadoPor(auth.getName());
			
			anexoRepository.saveAnexo(anexoToSave);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

	@Override
	@Transactional(readOnly = false)
	public void saveAnexo(Anexo anexo) {
		anexoRepository.saveAnexo(anexo);
	}

	@Override
	public Anexo selectById(int idAnexo) {
		return anexoRepository.selectById(idAnexo);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void signAnexo(int idAnexo, String hash){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario)auth.getPrincipal();
		Anexo anexo = selectById(idAnexo);
		anexo.setAssinado(1);
		anexo.setAssinadoPor(usuario.getUsuLogin());
		anexo.setIdAssinadoPor(usuario.getIdUsuario());
		anexo.setAssinaturaHash(hash);
	}

	@Override
	public void signAnexo(Anexo anexo) {
		anexoRepository.updateAnexo(anexo);
	}

	@Override
	@RemoteMethod
	public String getAssinaturaHash(int idAnexo) {
		Anexo anexo = selectById(idAnexo);
		
		if(anexo.getAssinaturaHash() == null)
			return "Documento não assinado";
		
		return anexo.getAssinaturaHash();
	}

	@Override
	@RemoteMethod
	public boolean deleteAnexoFromTemp(int idAnexo) {
		try{
			File file = new File("/home/danillo/users/sgt.danillo/temp/"+selectById(idAnexo).getAnexoCaminho());
			file.delete();
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean deleteAnexoFromTemp(Anexo anexo) {
		try{
			File file = new File("/home/danillo/users/sgt.danillo/temp/"+anexo.getAnexoCaminho());
			file.delete();
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	@RemoteMethod
	public boolean copy(int idAnexo) {
		
		Anexo anexo = selectById(idAnexo);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getPrincipal();
		
		if(anexo.getAssinado() == 1 && anexo.getIdAssinadoPor() != usuario.getIdUsuario()){
			deleteAnexoFromTemp(anexo);
			return false;
		}
		
		Carteira carteira = carteiraService.selectById(usuario.getIdCarteira());
		Secao secao = carteira.getSecao();
		OM om = carteira.getOm();
		
		DocumentoDetalhes documentoDetalhes = anexo.getDocumentoDetalhes();
		Set<Documento> documentos = documentoDetalhes.getDocumentosByCarteira();
		
		boolean havePermission = false;
		for(Documento documento : documentos){
			Carteira carteiraDocumento = documento.getCarteira(); 
			if ( carteiraDocumento.getOm() == om && carteiraDocumento.getSecao() == secao){
				havePermission = true;
				break;
			}
		}
		
		if(!havePermission){
			System.out.println("Não tem permissao.");
			return false;
		}
		
		try {
			InputStream is = new FileInputStream(new File("/home/danillo/users/sgt.danillo/temp/"+anexo.getAnexoCaminho()));
			FileOutputStream fos = new FileOutputStream(new File("/home/danillo/users/sgt.danillo/"+anexo.getAnexoCaminho()));
			IOUtils.copy(is, fos);
			
			String txtHistorico = "(Edição) -"+anexo.getAnexoNome()+"-";
			txtHistorico += usuario.getUsuLogin();
			
			Historico historico = new Historico();
			historico.setCarteira(carteira);
			historico.setDataHoraHistorico(new Date());
			historico.setHistorico(txtHistorico);
			historico.setDocumentoDetalhes(documentoDetalhes);
			historico.setUsuario(usuario);
			
			historicoService.save(historico);
			
			return deleteAnexoFromTemp(anexo);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	@Transactional(readOnly = false)
	public void setAssinaturaHash(String hash, int idAnexo) {
		Anexo anexo = anexoRepository.selectById(idAnexo);
		anexo.setAssinaturaHash(hash);
	}
}
