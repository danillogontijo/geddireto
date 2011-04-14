package br.org.ged.direto.controller.forms;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DocumentoForm {
	
	//private int[] destinatarios;
	private String destinatarios;
	private String tipoDocumento;
	private String nrDocumento;
	//private Date dataDocumento;
	private String dataDocumento;
	private char prioridade;
	private String assunto;
	private String remetente;
	private String destinatario;
	private String referencia;
	private int idCarteiraRemetente;
	private String nrProtocol;
	private int assinatura = 0;
	//private String assinadoPor;
	private char origem;
	//private int enviadoPor;
	
	
	/*public int getEnviadoPor() {
		return enviadoPor;
	}
	public void setEnviadoPor(int enviadoPor) {
		this.enviadoPor = enviadoPor;
	}*/
	
	
	public String getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public char getOrigem() {
		return origem;
	}
	public void setOrigem(char origem) {
		this.origem = origem;
	}
	/*public int[] getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(int[] destinatarios) {
		this.destinatarios = destinatarios;
	}*/
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
	/*public Date getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}*/
	public char getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(char prioridade) {
		this.prioridade = prioridade;
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
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public int getIdCarteiraRemetente() {
		return idCarteiraRemetente;
	}
	public void setIdCarteiraRemetente(int idCarteiraRemetente) {
		this.idCarteiraRemetente = idCarteiraRemetente;
	}
	public String getNrProtocol() {
		return nrProtocol;
	}
	public void setNrProtocol(String nrProtocol) {
		this.nrProtocol = nrProtocol;
	}
	public int getAssinatura() {
		return assinatura;
	}
	public void setAssinatura(int assinatura) {
		this.assinatura = assinatura;
	}
	/*public String getAssinadoPor() {
		return assinadoPor;
	}
	public void setAssinadoPor(String assinadoPor) {
		this.assinadoPor = assinadoPor;
	}*/
	
	

}
