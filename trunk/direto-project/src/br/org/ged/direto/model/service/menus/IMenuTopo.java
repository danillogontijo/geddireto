package br.org.ged.direto.model.service.menus;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;

import br.org.ged.direto.model.entity.menus.MenuTopo;

public interface IMenuTopo {

	@PreFilter("(!filterObject.onlyAdmin and !filterObject.onlyProtocolista)" +
			" or (filterObject.onlyAdmin and hasRole('ROLE_ADMIN'))" +
			" or (filterObject.onlyProtocolista and hasRole('ROLE_PROTOCOL'))")
	public Collection<MenuTopo> filterMenuTopo(Collection<MenuTopo> menuTopo);
	
	public Collection<MenuTopo> getMenuTopo();
	
}
