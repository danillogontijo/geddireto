package br.org.ged.direto.controller.forms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import br.org.ged.direto.model.entity.Carteira;

@Component
public class PesquisaForm {

	/*private Date dataDocumentoDe;
	private Date dataDocumentoAte;
	private String remetente = "";
	private String destinatario = "";
	*/
	
	private String papel = "";
	private Carteira carteira;
	
	/* VARIÁVEIS PARA PESQUISA NAS COLUNAS */
	private String tipoDocumento = "";
	private String nrDocumento = "";
	private String assunto = "";
	private String nrProtocol = "";
	private String dataEntSistema = "";
	
	/* VARIÁVEIS PARA O DATATABLES */
	private int start;
	private int amount;
	private int total;
	private String dir = "desc";
	private int echo;
	private int col;
	private String colName = "";
	private String searchTerm = "";
	private String individualSearch = "";
	private int totalAfterFilter;
	private boolean serverSide;
	
	
	public boolean isServerSide() {
		return serverSide;
	}

	public void setServerSide(boolean serverSide) {
		this.serverSide = serverSide;
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

	public void setSearchTerm(String searchTerm) throws UnsupportedEncodingException {
		this.searchTerm = new String(searchTerm.toString().getBytes("ISO-8859-1"),"UTF-8"); 
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) throws UnsupportedEncodingException {
		this.colName = new String(colName.toString().getBytes("ISO-8859-1"),"UTF-8");
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
	
	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) throws UnsupportedEncodingException {
		this.tipoDocumento = new String(tipoDocumento.toString().getBytes("ISO-8859-1"),"UTF-8");
	}
	public String getNrDocumento() {
		return nrDocumento;
	}
	public void setNrDocumento(String nrDocumento) throws UnsupportedEncodingException {
		this.nrDocumento = new String(nrDocumento.toString().getBytes("ISO-8859-1"),"UTF-8");
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) throws UnsupportedEncodingException {
		this.assunto = new String(assunto.toString().getBytes("ISO-8859-1"),"UTF-8");
	}
	public String getNrProtocol() {
		return nrProtocol;
	}
	public void setNrProtocol(String nrProtocol) {
		this.nrProtocol = nrProtocol;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}
	
	
}
