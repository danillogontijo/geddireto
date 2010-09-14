package br.org.ged.direto.model.entity.menus;

import java.io.Serializable;

public class MenuTopo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	private boolean onlyAdmin;
	private boolean onlyProtocolista;
	
	public MenuTopo(String name, String value, boolean onlyAdmin, boolean onlyProtocolista) {
		this.name = name;
		this.value = value;
		this.onlyAdmin = onlyAdmin;
		this.onlyProtocolista = onlyProtocolista;
	}

	public String getName() {
		return this.name;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public boolean isOnlyAdmin() {
		return this.onlyAdmin;
	}

	public boolean isOnlyProtocolista() {
		return this.onlyProtocolista;
	}

}
