package br.org.ged.direto.controller.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import br.org.ged.direto.model.entity.Carteira;

@Component
public class PesquisaForm {

	private String tipoDocumento = "";
	private String nrDocumento = "";
	private Date dataDocumentoDe;
	private Date dataDocumentoAte;
	private String assunto = "";
	private String remetente = "";
	private String destinatario = "";
	private String nrProtocol = "";
	private String dataEntSistema = "";
	
	private Carteira carteira;
	private String papel = "";
	
	private int start;
	private int amount;
	private int total;
	private String dir = "asc";
	private int echo;
	private int col;
	private String colName = "";
	private String searchTerm = "";
	private String individualSearch = "";
	private int totalAfterFilter;
	private boolean serverSide;
	
	List<String> sArray;
	
	
	
	
	public boolean isServerSide() {
		return serverSide;
	}

	public void setServerSide(boolean serverSide) {
		this.serverSide = serverSide;
	}

	public List<String> getsArray() {
		return sArray;
	}

	public void setsArray(List<String> sArray) {
		this.sArray = sArray;
	}

	public String getIndividualSearch() {
		return individualSearch;
	}

	public void setIndividualSearch(String individualSearch) {
		this.individualSearch = individualSearch;
	}

	public int getTotalAfterFilter() {
		return totalAfterFilter;
	}

	public void setTotalAfterFilter(int totalAfterFilter) {
		this.totalAfterFilter = totalAfterFilter;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public int getEcho() {
		return echo;
	}

	public void setEcho(int echo) {
		this.echo = echo;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getDataEntSistema() {
		return dataEntSistema;
	}

	public void setDataEntSistema(String dataEntSistema) {
		this.dataEntSistema = dataEntSistema;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isUserInRole(String role){
		return (role.equals(papel) ? true : false);
	}
	
	public Carteira getCarteira() {
		return carteira;
	}
	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}
	public String getPapel() {
		return papel;
	}
	public void setPapel(String papel) {
		this.papel = papel;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNrDocumento() {
		return nrDocumento;
	}
	public void setNrDocumento(String nrDocumento) {
		this.nrDocumento = nrDocumento;
	}
	public Date getDataDocumentoDe() {
		return dataDocumentoDe;
	}
	public void setDataDocumentoDe(Date dataDocumentoDe) {
		this.dataDocumentoDe = dataDocumentoDe;
	}
	public Date getDataDocumentoAte() {
		return dataDocumentoAte;
	}
	public void setDataDocumentoAte(Date dataDocumentoAte) {
		this.dataDocumentoAte = dataDocumentoAte;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getRemetente() {
		return remetente;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getNrProtocol() {
		return nrProtocol;
	}
	public void setNrProtocol(String nrProtocol) {
		this.nrProtocol = nrProtocol;
	}
	
	
	
}
