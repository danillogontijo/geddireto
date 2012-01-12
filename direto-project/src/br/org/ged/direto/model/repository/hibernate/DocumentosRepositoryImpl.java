package br.org.ged.direto.model.repository.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import br.org.direto.util.DataTimeUtil;
import br.org.direto.util.DataUtils;
import br.org.ged.direto.controller.forms.PesquisaForm;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Notificacao;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.repository.DocumentosRepository;

@Repository("documentoRepository")
public class DocumentosRepositoryImpl extends BaseRepositoryImpl implements DocumentosRepository {

    @SuppressWarnings("unchecked")
	@Override
    public List<Documento> listByLimited(Integer idCarteira){
		messages.getMessage("limitByPage");
		return (List<Documento>) hibernateTemplate.find("from "
				+ Documento.class.getName() + " where idCarteira = ? order by idDocumento desc",idCarteira);
	}
	
	@Override
	public List<DataUtils> listDocumentsFromAccount(Integer idCarteira, int ordenacao, int inicio, String box, String filtro) {
		
		String textoOrdenacao = "";
		
		if (ordenacao == 0){
			textoOrdenacao = " prioridade DESC,dataHora ASC ";
		}else{
			if (ordenacao == 1){
				textoOrdenacao = " dataHora DESC, prioridade DESC ";
			}else{
				textoOrdenacao = " dataHora ASC, prioridade DESC ";
			}
		}
		
		if(filtro == "" || filtro == null || filtro.equals("todas")){
			box = ((box.equals("1") || box.equals("0")) ? "0,1" : box);
			filtro = "";
		}else{
			if(filtro.equals("urgentes")){
				filtro = " AND details.prioridade in ('1','2') ";
				box = ((box.equals("1") || box.equals("0")) ? "0,1" : box);
			}else{
				box = "0";
				filtro = "";
			}
		}
		
		int limitePorPagina = Integer.parseInt(messages.getMessage("limitByPage")); //DocumentosUtil.LIMITE_POR_PAGINA;
		
		String sql = "from Documento as doc inner join doc.documentoDetalhes details " +
		"WHERE doc.carteira.idCarteira = ? AND doc.status in ("+box+")"+filtro+
		"GROUP BY details.idDocumentoDetalhes ORDER BY "+textoOrdenacao;
		
		if(box.equals("6")){
			sql = "from Documento as doc inner join doc.documentoDetalhes details " +
			"WHERE doc.carteira.idCarteira = ? AND notificar=1"+
			"GROUP BY details.idDocumentoDetalhes ORDER BY "+textoOrdenacao;
		}
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		query.setInteger(0, idCarteira);
		query.setFirstResult(inicio);
		query.setMaxResults(limitePorPagina);
		
		List results = query.list();
					
		List<DataUtils> documentos = new LinkedList<DataUtils>();
		
		for(int i=0; i<results.size(); i++){
			DataUtils data = new DataUtils();
			
			Object[] objects = (Object[]) results.get(i);
			DocumentoDetalhes doc = (DocumentoDetalhes) objects[1];
			Documento doc_cart = (Documento) objects[0];
			data.setId(Integer.toString(doc.getIdDocumentoDetalhes()));
			String pri = "";
			if (doc.getPrioridade() == '0') {
				pri = " N";
			} else {
				if (doc.getPrioridade() == '1') {
					pri = " U";
					//bgColor = "#FFFF00";
				} else {
					if (doc.getPrioridade() == '2') {
						pri = "UU";
						//bgColor = "#FF0000";
					}
				}
			}
			
			String assunto = doc.getAssunto();
			
			if (assunto.length() > 60){
				assunto = assunto.substring(0, 49)+"...";
			}
			
			int idDocumentoDetalhes = doc.getIdDocumentoDetalhes();
			
			String url = "documento.html?id="+idDocumentoDetalhes+"&pk="+doc_cart.getIdDocumento()+"&carteira="+idCarteira;
			
			String notificar = "";
			if (doc_cart.getNotificar() == 1){
				Date ultimaVerificacaoNotificacao = doc_cart.getDataHoraNotificacao();
				Set<Notificacao> notficacaoes = doc_cart.getNotificacoes();
				Iterator<Notificacao> ite = notficacaoes.iterator();
				
				int c = 0;
				while(ite.hasNext()){
					Notificacao not = ite.next();
					if (not.getDataHoraNotificacao().after(ultimaVerificacaoNotificacao)){
						c++;
					}
				}
				
				notificar = "<a href='"+idDocumentoDetalhes+"' class='notificacao' name='notificacoes' id='"+doc_cart.getIdDocumento()+"'>("+c+") </a>";
				
			}
			
			String texto = "<div class='div_docs'"+(doc_cart.getStatus() == '0' ? " style='font-weight: bold;'" : "")+" id='div_doc"+(i)+"'>";
			texto = texto + "<input type='checkbox' class='chkbox' pk='"+doc_cart.getIdDocumento()+"' value='"+idDocumentoDetalhes+"' id='chk"+i+"' "+
						"onClick='js.direto.sel_chkbox_doc("+(i)+");' />";
			texto = texto + (doc_cart.getStatus() == '0' ? "<img src='imagens/outras/cartaFec.gif' class='img_docs' id='doc_status' />" : "<img src='imagens/outras/cartaAbr.gif' class='img_docs' id='doc_status' />");
			texto = texto + (doc.getTipo() == 'I' ? "<img src='imagens/outras/computer.gif' title='Documento interno' class='img_docs' id='doc_tipo'/> " : ( doc.getTipo() == 'F' ? "<img src='imagens/outras/computer2.gif' title='Trâmite externo' class='img_docs' id='doc_tipo'/> " : "<img src='imagens/outras/scanner.gif' title='Documento externo' class='img_docs' id='doc_tipo'/>" ) );
			texto = texto + (pri.equals(" N") ? "<span class='prio_n_docs'>"+pri+"</span>" : (pri.equals(" U") ? "<span class='prio_u_docs'>"+pri+"</span>" : "<span class='prio_uu_docs'>"+pri+"</span>"));
			texto = texto + (notificar+"<a href='"+url+"' title='Rem: "+doc.getRemetente().replace('-',' ')+"<br>Nr. Doc: "+doc.getNrDocumento()+"' id='rem_docs' class='ahref_docs'>["+doc.getTipoDocumento().getTipoDocumentoNome()+"][Nr: "+doc.getNrDocumento()+"]</a>");
			texto = texto + (" - <a href='"+url+"' title='Protocolo: "+doc.getNrProtocolo()+"<br>Assunto: "+doc.getAssunto()+"' id='ass_docs' class='ahref_docs'>"+assunto+"</a>");
			texto = texto + ("<font class='data_docs'>"+DataTimeUtil.getBrazilFormatDataHora(doc_cart.getDataHora())+"</font>");
			texto = texto + "</div>"; 
			
			data.setTexto(texto);
			
			documentos.add(data);
		}
		
		return documentos;
		
	}
	
	@Override
	public Long counterDocumentsByBox(String box, int idCarteira, String filtro){
		
		if(filtro == "" || filtro == null || filtro.equals("todas")){
			box = ((box.equals("1") || box.equals("0")) ? "0,1" : box);
			filtro = "";
		}else{
			if(filtro.equals("urgentes")){
				filtro = " AND doc.documentoDetalhes.prioridade = '2' ";
				box = ((box.equals("1") || box.equals("0")) ? "0,1" : box);
			}else{
				box = "0";
				filtro = "";
			}
		}
		
		String sql = "SELECT count(distinct doc.documentoDetalhes.idDocumentoDetalhes) FROM Documento as doc " +
				"WHERE doc.carteira.idCarteira = ? AND doc.status in ("+box+")" + filtro;
		
		if(box.equals("6")){
			sql = "SELECT count(distinct doc.documentoDetalhes.idDocumentoDetalhes) FROM Documento as doc " +
			"WHERE doc.carteira.idCarteira = ? AND notificar=1"+filtro;
		}
		
		return (Long) hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql).setInteger(0, idCarteira).uniqueResult();
	}

	@Override
	public Documento selectByIdCarteira(final Integer idCarteira) {
		return (Documento) hibernateTemplate.execute(new HibernateCallback<Documento>() {
			@Override
			public Documento doInHibernate(org.hibernate.Session session)
					throws HibernateException, SQLException {
				return (Documento) session.createQuery(
				"from Documento where idDocumento = :idDocumento")
				.setParameter("idDocumento", idCarteira).uniqueResult();
			}
		});
	}

	@Override
	public Documento selectById(Integer idDocumentoDetalhes, Integer idCarteira) {
		Documento returnDoc = (Documento) hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("FROM Documento as doc " +
				"WHERE doc.carteira.idCarteira = ? AND doc.documentoDetalhes.idDocumentoDetalhes = ? GROUP BY doc.carteira.idCarteira" +
		"").setInteger(0, idCarteira).setInteger(1, idDocumentoDetalhes).uniqueResult(); 
		
		if (returnDoc == null) {
			throw new DocumentNotFoundException(messages.getMessage("documento.exception.notfound"));
		}
		
		return returnDoc;
	}
	
	@Override
	public Documento selectById(Integer idDocumentoDetalhes) {
		Documento returnDoc = (Documento) hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("FROM Documento as doc " +
				"WHERE doc.documentoDetalhes.idDocumentoDetalhes = ? GROUP BY doc.documentoDetalhes.idDocumentoDetalhes" +
		"").setInteger(0, idDocumentoDetalhes).uniqueResult(); 
		
		if (returnDoc == null) {
			throw new DocumentNotFoundException(messages.getMessage("documento.exception.notfound"));
		}
		
		return returnDoc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> getAllById(Integer id) {
		
		return (List<Documento>) hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("FROM Documento as doc " +
				"WHERE doc.documentoDetalhes.idDocumentoDetalhes = ? GROUP BY doc.carteira.idCarteira ORDER BY doc.idDocumento" +
		"").setInteger(0, id).list(); 
			
	}

	@Override
	public List<Anexo> getAllAnexos(Integer idDocumentoDetalhes) {
		
		hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("").setInteger(0, idDocumentoDetalhes).list(); 
		
		return null;
	}
	
	@Override
	public Integer getLastId(){
		return (Integer)hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("SELECT max(doc.idDocumentoDetalhes) FROM DocumentoDetalhes as doc").uniqueResult();
	}

	@Override
	public Documento getByIdPKey(Integer id) {
		return (Documento)hibernateTemplate.get(Documento.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<DocumentoCompleto> returnSearch(PesquisaForm form) {
		
		//Set<DocumentoCompleto> documentos = new HashSet<DocumentoCompleto>();
		List<DocumentoCompleto> documentos = new ArrayList<DocumentoCompleto>();
		
		int idSecao = form.getCarteira().getSecao().getIdSecao();
		int idOM = form.getCarteira().getOm().getIdOM();
		
		int	start = form.getStart();
		int	amount = form.getAmount();
		
		String tipo = "";
		String protocolo = "";
		String nrDoc = "";
		String assunto = "";
		String data = "";
		
		tipo = form.getTipoDocumento();
		protocolo = form.getNrProtocol();
		nrDoc = form.getNrDocumento();
		assunto = form.getAssunto();
		data = form.getDataEntSistema();
		
		List<String> sArray = new ArrayList<String>();
		
		if (!tipo.equals("")) {
			String sTipo = " details.tipoDocumento.tipoDocumentoNome like '%" + tipo + "%'";
			sArray.add(sTipo);
		}
		if (!protocolo.equals("")) {
			String sProtocolo = " details.nrProtocolo like '%" + protocolo + "%'";
			sArray.add(sProtocolo);
		}
		if (!nrDoc.equals("")) {
			String sNrDoc = " details.nrDocumento like '%" + nrDoc + "%'";
			sArray.add(sNrDoc);
		}
		if (!assunto.equals("")) {
			String sAssunto = " details.assunto like '%" + assunto + "%'";
			sArray.add(sAssunto);
		}
		if (!data.equals("")) {
			String sData = " details.dataEntSistema like '%" + data + "%'";
			sArray.add(sData);
		}
		
		String individualSearch = "";
		if(sArray.size()==1){
			individualSearch = sArray.get(0);
		}else if(sArray.size()>1){
			for(int i=0;i<sArray.size()-1;i++){
				individualSearch += sArray.get(i)+ " and ";
			}
			individualSearch += sArray.get(sArray.size()-1);
		}
		form.setIndividualSearch(individualSearch);
		
		String searchSQL = "";
		String sql = "from Documento as doc inner join doc.documentoDetalhes details";
		String searchTerm = form.getSearchTerm();
		
		String globeSearch =  " where (" 
									+ "details.tipoDocumento.tipoDocumentoNome like '%"+searchTerm+"%'"
									+ " or details.nrProtocolo like '%"+searchTerm+"%'"
									+ " or details.nrDocumento like '%"+searchTerm+"%'"
									+ " or details.assunto like '%"+searchTerm+"%'"
									+ " or details.dataEntSistema like '%"+searchTerm+"%')";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario obj = (Usuario)auth.getPrincipal();
		String baseSearch = "";
		
		if(obj.getUsuPapel().equals("ADMIN"))
			baseSearch = " doc.carteira.secao.idSecao <> 0 and doc.carteira.om.idOM <> 0";
		else if (obj.getUsuPapel().equals("PROTOCOLO"))
			baseSearch = " doc.carteira.om.idOM = "+idOM+"";
		else
			baseSearch = " doc.carteira.secao.idSecao = "+idSecao+" and doc.carteira.om.idOM = "+idOM+"";
		
		if(searchTerm!=""&&individualSearch!=""){
			searchSQL = globeSearch + " and " + individualSearch;
			baseSearch = " and" + baseSearch;
		}
		else if(individualSearch!=""){
			searchSQL = " where " + individualSearch;
			baseSearch = " and" + baseSearch;
		}else if(searchTerm!=""){
			searchSQL=globeSearch;
			baseSearch = " and" + baseSearch;
		}
		
		if (searchTerm==""&&individualSearch=="")
			baseSearch = " where" + baseSearch;
		
		sql += searchSQL;
		sql += baseSearch;
		sql += " group by details.idDocumentoDetalhes order by " + form.getColName() + " " + form.getDir();
		
		//System.out.println(sql);
		int total = returnTotalSearch(sql);
		
		Query query = getSession().createQuery(sql);
		
		if(form.isServerSide()){
			query.setFirstResult(start);
			query.setMaxResults(amount);
		}
		
		List results = query.list();
		
		for(int i=0; i<results.size(); i++){
			
			Object[] objects = (Object[]) results.get(i);
			DocumentoDetalhes doc_det = (DocumentoDetalhes) objects[1];
			Documento doc_conta = (Documento) objects[0];
			DocumentoCompleto doc_completo = new DocumentoCompleto(doc_conta,doc_det);
			doc_completo.setTotal(total);
			documentos.add(doc_completo);
			
		}
			
		
		return documentos;
	}
	
	private int returnTotalSearch(String sql){
		int total = 0; 
		try{
			Query query = getSession().createQuery("select count(*) "+sql);
			total = (query.list()).size();
		}catch (Exception e) {
			e.printStackTrace();
			total = 0;
		}
		
		return total; 
	}

	@Override
	public int returnTotalSearch(PesquisaForm form) {
		
		Usuario obj = getAutenticatedUser();
		
		int idSecao = form.getCarteira().getSecao().getIdSecao();
		int idOM = form.getCarteira().getOm().getIdOM();
		
		String sqlCount = "select count(distinct doc.documentoDetalhes.idDocumentoDetalhes) from Documento as doc";
		
		if(obj.getUsuPapel().equals("USER"))
			sqlCount += " where doc.carteira.secao.idSecao = "+idSecao+" and doc.carteira.om.idOM = "+idOM;
		else if (obj.getUsuPapel().equals("PROTOCOLO"))
			sqlCount += " where doc.carteira.om.idOM = "+idOM+"";
		else
			sqlCount += " where doc.carteira.secao.idSecao <> 0 and doc.carteira.om.idOM <> 0";
		
		Integer total = ((Long)getSession().createQuery(sqlCount).uniqueResult()).intValue();
		
		return (total == null ? 0 : total.intValue()); 
	}

	@Override
	public void saveNewDocumento(DocumentoDetalhes documentoDetalhes) {
		hibernateTemplate.save(documentoDetalhes);
	}

	@Override
	public void saveOrUpdateDocumento(Documento documento) {
		hibernateTemplate.saveOrUpdate(documento);
	}

	@Override
	public DocumentoDetalhes getDocumentoDetalhes(int primaryKey) {
		return (DocumentoDetalhes)hibernateTemplate.get(DocumentoDetalhes.class, primaryKey);
	}

	@Override
	public int getAmountDocumentoByYear(String year) {
		
		String sqlCount = "select count(doc.idDocumentoDetalhes) from DocumentoDetalhes as doc" +
			" where doc.dataEntSistema like '"+year+"%'";
		
		int total = ((Long)getSession().createQuery(sqlCount).uniqueResult()).intValue();
		
		return total;
	}

	@Override
	public void tranferirDocumentos(int idUsuario, int idCarteira) {
		getSession().createSQLQuery("UPDATE idmensausu set idCarteira="+idCarteira+" WHERE idUsuario="+idUsuario).executeUpdate();  
	}

	@Override
	public List<DataUtils> fastSearch(int box, String protocolo,
			String assunto, String dataDe, String dataAte) {
		
		Usuario obj = getAutenticatedUser();
		
		String sql = "SELECT m.id,m.nrProtocolo,m.assunto,m.nrDoc,t.TipoAbr FROM mensagens as m, idmensausu as c, tiposdocumentos as t "+
			"WHERE t.id=m.IdTipoDocumento AND c.idMensagem=m.id AND c.idcarteira="+obj.getIdCarteira();
		
		if (protocolo.length()>0)
			sql += " AND (m.nrProtocolo='"+protocolo+"' OR m.id="+protocolo+")";
		else{
		
			if(box != 0)
				sql += " AND c.status in("+(box == 1 ? "0,1" : box)+")";
			
			if(assunto.length()>0)
				sql += " AND m.assunto like '%"+assunto+"%'";
			
			if(dataDe.length()>0 || dataAte.length()>0){
				sql += " AND m.data";
				
				if (dataDe.length()>0 && dataAte.length()==0)
					sql += " > '" + dataDe
					+ " 00:00:00'";
				if (dataDe.length()==0 && dataAte.length()>0)
					sql += " < '" + dataAte
					+ " 23:59:59'";
				if (dataDe.length()>0 && dataAte.length()>0)
					sql += " BETWEEN '" + dataDe
					+ " 00:00:00' AND '"+dataAte+" 23:59:59'";
			}
		}	
		
		sql += " GROUP BY m.id ORDER BY m.data DESC LIMIT 0,20";
		
		Query query = getSession().createSQLQuery(sql);
		
		List results = query.list();
		
		List<DataUtils> dados = new ArrayList<DataUtils>();
		
		for(int i=0; i<results.size(); i++){
			DataUtils du = new DataUtils();
			Object[] objs = (Object[]) results.get(i);
			du.setId(objs[0].toString());
			String texto = "<a href='view.html?id="+objs[0].toString()+"' title='"+objs[1].toString()+"'>["+objs[4].toString()+"]["+objs[3].toString()+"]"+objs[2].toString()+"</a>";
			du.setTexto(texto);
			dados.add(du);
		}
		
		return dados;
	}

	@Override
	public int returnTotalNUDExterno(int year) {
		String sqlCount = "select count(distinct doc.idDocumentoDetalhes) from DocumentoDetalhes as doc";
			sqlCount += " where doc.tipo = 'F'";
		
		if(year != 0)
			sqlCount += " and doc.dataEntSistema like '"+year+"%'";
			
		Integer total = ((Long)getSession().createQuery(sqlCount).uniqueResult()).intValue();
		
		return (total == null ? 0 : total.intValue()); 
	}


}
