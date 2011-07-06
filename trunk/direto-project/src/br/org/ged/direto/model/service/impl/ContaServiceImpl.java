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
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.ContaRepository;
import br.org.ged.direto.model.service.CarteiraService;
import br.org.ged.direto.model.service.ContaService;
import br.org.ged.direto.model.service.UsuarioService;

@Service("contaService")
@RemoteProxy(name = "contasJS")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ContaServiceImpl implements ContaService {
	
	private ContaRepository contaRepository;
	private CarteiraService carteiraService;
	private UsuarioService usuarioService;
	
	@Autowired
	public void setCarteiraService(CarteiraService carteiraService) {
		this.carteiraService = carteiraService;
	}
	
	@Autowired
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Autowired
	public void setContaRepository(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	@Override
	public void saveOrUpdate(Conta conta) {
		contaRepository.saveOrUpdate(conta);
	}

	@Override
	public void tieUsers(List<Usuario> usuarios) {
		// TODO Auto-generated method stub

	}

	@Override
	@RemoteMethod
	public void add(int idUsuario, int ativado, int idCarteira,
			int contaPrincipal) {
		
		try{
		
			Conta conta = new Conta();
			Usuario usuario = usuarioService.selectById(idUsuario);
			Carteira carteira = carteiraService.selectById(idCarteira);
			conta.setAtivado(ativado);
			conta.setCarteira(carteira);
			conta.setContaPrincipal(contaPrincipal);
			conta.setDataContaRec(new Date());
			conta.setDataContaTransf(new Date());
			conta.setUsuario(usuario);
			
			boolean alreadyExists = checkIfExistsAccount(idUsuario,idCarteira);
			
			if (!alreadyExists)
				contaRepository.saveOrUpdate(conta);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@RemoteMethod
	@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
	public void deleteAccount(int idConta) {
		contaRepository.deleteAccount(idConta);
	}

	@Override
	@RemoteMethod
	@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
	public void deleteAccount(int idUsuario, int idCarteira) {
		contaRepository.deleteAccount(idUsuario, idCarteira);
	}

	@Override
	public Conta getAccount(int idConta) {
		return contaRepository.getAccount(idConta);
	}

	@Override
	public Conta getAccount(int idUsuario, int idCarteira) {
		return contaRepository.getAccount(idUsuario, idCarteira);
	}

	@Override
	@RemoteMethod
	@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
	public void updateAccount(int idConta, int idUsuario, int ativado, int idCarteira, int contaPrincipal) {
		try{
			Conta conta = getAccount(idConta);
			Carteira carteira = carteiraService.selectById(idCarteira);
			Usuario usuario = usuarioService.selectById(idUsuario);
			conta.setAtivado(ativado);
			conta.setCarteira(carteira);
			conta.setContaPrincipal(contaPrincipal);
			conta.setUsuario(usuario);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAccount(int idUsuario, int idCarteira) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	@RemoteMethod
	@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
	public void updateAccount(int idConta, int idCarteira, boolean isPrincipal){
		try{
			Conta conta = getAccount(idConta);
			int idUsuario = conta.getUsuario().getIdUsuario();
			
			boolean alreadyExists = checkIfExistsAccount(idUsuario, idCarteira);
			
			if (alreadyExists){
				System.out.println("Apaga a conta\n");
				deleteAccount(idUsuario, idCarteira);
			}
			Carteira carteira = carteiraService.selectById(idCarteira);
			conta.setCarteira(carteira);
			
			if (isPrincipal)
				conta.setContaPrincipal(1);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean checkIfExistsAccount(int idUsuario, int idCarteira){
		return ( getAccount(idUsuario, idCarteira) != null ? true : false );
	}

	@Override
	@RemoteMethod
	@Transactional(propagation=Propagation.REQUIRED,readOnly = false)
	public String transferAccount(int idConta, String usuLoginDestinatario) {
		Conta conta = getAccount(idConta);
		try{
			Usuario usuarioDestinatario = usuarioService.selectByLogin(usuLoginDestinatario);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuarioLogado = (Usuario) auth.getPrincipal();
			
			//checa se o usuario que está logado tem permissão para estar transferindo esta conta
			if(conta.getUsuario().getIdUsuario().equals(usuarioLogado.getIdUsuario())){
				System.out.println("Usuario tem permissao");
				//Esta conta já existe para o usuário para o qual esta tentando transferir?
				Conta conta2 = getAccount(usuarioDestinatario.getIdUsuario(), conta.getCarteira().getIdCarteira());
				//System.out.println(conta2.getCarteira().getCartDesc());
				if(conta2 != null){
					System.out.println("Conta já existe");
					if(!conta2.isAtivada()){
						conta2.setAtivado(1);
						return conta.getCarteira().getCartAbr()+" (reativada),true";
					}
					
					return conta.getCarteira().getCartAbr()+" (já resp por esta conta),false";
				}else{
					System.out.println("usuario nao tem esta conta");
					
					if(usuarioDestinatario.getIdUsuario().equals(conta.getIdUsuarioProprietario())){
						System.out.println("Conta retornada");
						conta.setUsuario(usuarioDestinatario);
						conta.setContaPrincipal(1);
						return conta.getCarteira().getCartAbr()+" (retornada para "+usuarioDestinatario.getUsuLogin()+"),true";
					}else{
						System.out.println("Conta transferida");
						if(conta.isPrincipal()){
							conta.setContaPrincipal(0);
							conta.setIdUsuarioProprietario(conta.getUsuario().getIdUsuario());
						}
						conta.setUsuario(usuarioDestinatario);
					}
					
				}
				
				return conta.getCarteira().getCartAbr()+",true";
			}
		}catch (Exception e) {
			e.printStackTrace();
			return conta.getCarteira().getCartAbr()+"(erro tentando gravar no BD),false";
		}
		
		return conta.getCarteira().getCartAbr()+"(negado),false";
		
	}

	

}
