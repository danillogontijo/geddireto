package br.org.ged.direto.controller.forms;

import java.util.Date;

import org.springframework.stereotype.Component;

import br.org.ged.direto.model.entity.Carteira;

@Component
public class PesquisaForm {

	private String tipoDocumento;
	private String nrDocumento;
	private Date dataDocumentoDe;
	private Date dataDocumentoAte;
	private String assunto;
	private String remetente;
	private String destinatario;
	private String nrProtocol;
	
	private Carteira carteira;
	private String papel;
	
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
