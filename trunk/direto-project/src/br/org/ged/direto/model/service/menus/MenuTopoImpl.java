package br.org.ged.direto.model.service.menus;

import java.util.ArrayList;
import java.util.Collection;

import br.org.ged.direto.model.entity.menus.MenuTopo;

public class MenuTopoImpl implements IMenuTopo{
	
	private Collection<MenuTopo> menuTopo = new ArrayList<MenuTopo>();
	
	public MenuTopoImpl(){
		this.menuTopo.add(new MenuTopo("Admin", "/admin/index.html", true, false));
		this.menuTopo.add(new MenuTopo("Relatorios", "admin.jsp", false, true));
		this.menuTopo.add(new MenuTopo("Passar Conta", "passarConta.html", false, false));
		this.menuTopo.add(new MenuTopo("Dados Cadastrais", "usuario.html", false, false));
		this.menuTopo.add(new MenuTopo("Sugestões", "#wcomentar", false, false));
		//this.menuTopo.add(new MenuTopo("Sair", "j_spring_security_logout", false, false));
		this.menuTopo.add(new MenuTopo("Sair", "j_security_rememberMe_logout", false, false));
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
