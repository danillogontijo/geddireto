package br.org.ged.direto.model.service.menus;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import br.org.ged.direto.model.entity.menus.MenuTopo;

@Service("MenuTopoService")
public class MenuTopoImpl implements IMenuTopo{
	
	private Collection<MenuTopo> menuTopo = new ArrayList<MenuTopo>();
	
	
	public MenuTopoImpl(){
		this.menuTopo.add(new MenuTopo("Admin", "admin.jsp", true, false));
		this.menuTopo.add(new MenuTopo("Relatorios", "admin.jsp", false, true));
		this.menuTopo.add(new MenuTopo("Passar Conta", "passar_conta.jsp", false, false));
		this.menuTopo.add(new MenuTopo("Dados Cadastrais", "dados_cadastro.jsp?modo=ver", false, false));
		this.menuTopo.add(new MenuTopo("Configurações", "configuracao.jsp", false, false));
		this.menuTopo.add(new MenuTopo("Sair", "j_spring_security_logout", false, false));
	}
	
	@Override
	public Collection<MenuTopo> getMenuTopo() {
		return (menuTopo);
	}
	
	
	@Override
	public Collection<MenuTopo> filterMenuTopo(Collection<MenuTopo> menuTopo) {
		return menuTopo;
	}

}
