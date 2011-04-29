package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "mensagens")
public class DocumentoDetalhes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8737487864886359495L;

	@Id 
	@GeneratedValue(generator="lastId")
	@GenericGenerator(name="lastId", strategy = "select", 
			parameters = { @Parameter(name = "key", value = "nrProtocolo") })
	@Column(name = "Id")
	private Integer idDocumentoDetalhes;
	
	@Column(name = "Tipo")
	private char tipo;
	
	@Column(name = "Prioridade")
	private char prioridade;
	
	@Column(name = "Assunto")
	private String assunto;
	
	@Column(name = "Remetente")
	private String remetente;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DtDocumento", nullable = false)
	private Date dataDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Data", nullable = false)
	private Date dataEntSistema;
	
	@Column(name = "TipoDoc")
	private String tipoDocumento;
	
	@Column(name = "Destinatario")
	private String destinatario;
	
	@Column(name = "NrDoc")
	private String nrDocumento;
	
	@Column(name = "NrProtocolo")
	private String nrProtocolo;
	
	@Column(name = "Referencia")
	private String referencia;
	
	@Column(name = "Assinatura",length=1,nullable=false)
	private int assinatura;
	
	@Column(name = "AssinadoPor")
	private String assinadoPor;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUsuRem", nullable = false)
	private Usuario usuarioElaborador;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdMensagem", nullable = true)
	private Set<Documento> documentosByCarteira = new HashSet<Documento>(0);
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdMensagem", nullable = true)
	@OrderBy("idAnexo asc") 
	private Set<Anexo> anexos = new HashSet<Anexo>(0);
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdMensagem", nullable = true)
	@OrderBy("dataHoraAnotacao asc") 
	private Set<Anotacao> anotacoes = new HashSet<Anotacao>(0);
	
		 
	
	public Set<Anotacao> getAnotacoes() {
		return anotacoes;
	}

	public void setAnotacoes(Set<Anotacao> anotacoes) {
		this.anotacoes = anotacoes;
	}

	public Integer getIdDocumentoDetalhes() {
		return idDocumentoDetalhes;
	}

	public Set<Documento> getDocumentosByCarteira() {
		return documentosByCarteira;
	}

	public void setDocumentosByCarteira(Set<Documento> documentosByCarteira) {
		this.documentosByCarteira = documentosByCarteira;
	}

	public Usuario getUsuarioElaborador() {
		return usuarioElaborador;
	}

	public void setUsuarioElaborador(Usuario usuarioElaborador) {
		this.usuarioElaborador = usuarioElaborador;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public Date getDataEntSistema() {
		return dataEntSistema;
	}

	public void setDataEntSistema(Date dataEntSistema) {
		this.dataEntSistema = dataEntSistema;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getNrDocumento() {
		return nrDocumento;
	}

	public void setNrDocumento(String nrDocumento) {
		this.nrDocumento = nrDocumento;
	}

	public String getNrProtocolo() {
		return nrProtocolo;
	}

	public void setNrProtocolo(String nrProtocolo) {
		this.nrProtocolo = nrProtocolo;
	}

	public int getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(int assinatura) {
		this.assinatura = assinatura;
	}

	public String getAssinadoPor() {
		return assinadoPor;
	}

	public void setAssinadoPor(String assinadoPor) {
		this.assinadoPor = assinadoPor;
	}

	public void setIdDocumentoDetalhes(Integer idDocumentoDetalhes) {
		this.idDocumentoDetalhes = idDocumentoDetalhes;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public char getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(char prioridade) {
		this.prioridade = prioridade;
	}

	public String getAssunto() {
		return this.assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	public boolean isAssign(){
		return this.assinatura == 1 ? true : false;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Set<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(Set<Anexo> anexos) {
		this.anexos = anexos;
	}
	
	@Override
	public String toString(){
		return "Documento: [id: "+this.idDocumentoDetalhes+", assunto: "+this.assunto+",nrDoc: "+this.nrDocumento+"]";
	}
	

}
