package br.org.ged.direto.model.repository.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataTimeUtil;
import br.org.direto.util.DataUtils;
import br.org.direto.util.DocumentosUtil;
import br.org.direto.util.UsuarioUtil;
import br.org.ged.direto.controller.forms.PesquisaForm;
import br.org.ged.direto.controller.utils.DocumentoCompleto;
import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Notificacao;
import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.repository.DocumentosRepository;
import br.org.ged.direto.model.service.UsuarioService;

@Repository("documentoRepository")
public class DocumentosRepositoryImpl implements DocumentosRepository, MessageSourceAware {

	private HibernateTemplate hibernateTemplate;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    //private Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
	
    @Autowired
	private UsuarioService usuarioService;
    
    /*@Autowired	
    private JdbcTemplate jdbcTemplate;*/
    
    @Autowired
    private SessionFactory sessionFactory;
    
    //private Session session = sessionFactory.getCurrentSession();
    
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> listByLimited(Integer idCarteira){
		
		messages.getMessage("limitByPage");
		
		/*final Integer idCarteira = new Integer(2);
		
		List<Documento> list = new ArrayList<Documento>();
		//
		
		Documento d = (Documento) hibernateTemplate.execute(new HibernateCallback<Documento>() {
			@Override
			public Documento doInHibernate(org.hibernate.Session session)
					throws HibernateException, SQLException {
				return (Documento) session.createQuery(
				"from Documento where IdCarteira = :idCarteira")
				.setParameter("idCarteira", idCarteira).uniqueResult();
			}
		});
		
		
		list.add(d);
		
		System.out.println(list.size());
		
		return list;*/
		
		//Query query = session.createQuery("from User u order by u.name asc");

		
		System.out.println("Doc Repository: "+idCarteira);
		
		return (List<Documento>) hibernateTemplate.find("from "
				+ Documento.class.getName() + " where idCarteira = ? order by idDocumento desc",idCarteira);
	}
	
	public String queryFiltro(String filtro){
		
		if(filtro == null || filtro.equals("todas") || filtro.equals("naolidas")){
			filtro = "";
		}else{
			filtro = " AND details.prioridade = '2' ";			
		}
		
		return filtro;
		
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
		
		/*Long count =  (Long) hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("SELECT count(distinct doc.documentoDetalhes.idDocumentoDetalhes) FROM Documento as doc " +
				"WHERE doc.carteira.idCarteira = ? " +
				"").setInteger(0, idCarteira).uniqueResult();
		*/
		
		//Long count = counterDocumentsByBox("0", idCarteira);
		
		//Long count =  (Long)hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("select count(*) from Usuario").uniqueResult();
		
		
		//System.out.println("count: "+count);
		
		/*for(int i=0;i<count.size();i++){
			
			Object o = (Object)count.iterator().next();
			System.out.println(o.toString());
			
		}*/
		
		System.out.println(filtro);
		
		if(filtro == "" || filtro == null || filtro.equals("todas")){
			box = ((box.equals("1") || box.equals("0")) ? "0,1" : box);
			filtro = "";
		}else{
			if(filtro.equals("urgentes")){
				filtro = " AND details.prioridade = '2' ";
				box = ((box.equals("1") || box.equals("0")) ? "0,1" : box);
			}else{
				box = "0";
				filtro = "";
			}
		}
		
		
		System.out.println(box);
		
		int limitePorPagina = DocumentosUtil.LIMITE_POR_PAGINA;
		
		String sql = "from Documento as doc inner join doc.documentoDetalhes details " +
		"WHERE doc.carteira.idCarteira = ? AND doc.status in ("+box+")"+filtro+
		"GROUP BY details.idDocumentoDetalhes ORDER BY "+textoOrdenacao;
		
		//Query query;
		/*String sql = "SELECT {dc.*}, {d.*} FROM idmensausu dc, mensagens d " +
				"WHERE dc.IdMensagem = d.Id AND dc.IdCarteira = ? " +
				"GROUP BY d.Id ORDER BY "+textoOrdenacao;//+" LIMIT 1,2";
		*///String sql = "from Documento doc_cart inner join doc_cart.documentoDetalhes as doc"; 
		/*Query query = sessionFactory.openSession().createSQLQuery(sql);
		query.setInteger(0, idCarteira);
		*/
		//query.setString(1, "0");
		//query.setEntity(DocumentoDetalhes.class);
		//query.setEntity("doc_cart",Documento.class);
		
		/*Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql)
		.addEntity("dc", Documento.class)
		.addEntity("d", DocumentoDetalhes.class);*/
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sql);
		
	/*	Query query = sessionFactory.openSession().createSQLQuery(sql)
		.addEntity("dc", Documento.class)
		.addEntity("d", DocumentoDetalhes.class);
	*/	
		query.setInteger(0, idCarteira);
		
		query.setFirstResult(inicio);
		query.setMaxResults(limitePorPagina);
		
		
		List results = query.list();
					
		List<DataUtils> documentos = new LinkedList<DataUtils>();
		
		//DocumentoDetalhes doc = (DocumentoDetalhes) results.get(0); 
		
		//System.out.println("ListDoc: "+results.size());
		
		//DataUtils total = new DataUtils();
		//total.setId("0");
		//total.setTexto("Total de registros: "+ count);
		
		//r.add(total);
		
		for(int i=0; i<results.size(); i++){
			DataUtils data = new DataUtils();
			
			Object[] objects = (Object[]) results.get(i);
			DocumentoDetalhes doc = (DocumentoDetalhes) objects[1];
			Documento doc_cart = (Documento) objects[0];
			System.out.println("Hash Code doc: "+doc.hashCode());
			//DocumentosUtil.documentos.put(doc.getIdDocumentoDetalhes(), doc);
			
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
			assunto = assunto + assunto + assunto + assunto + assunto;
			
			if (assunto.length() > 60){
				assunto = assunto.substring(0, 49)+"...";
			}
			
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
				
				notificar = "<a href='"+doc.getIdDocumentoDetalhes()+"' class='notificacao' name='notificacoes' id='"+doc_cart.getIdDocumento()+"'>("+c+") </a>";
				
			}
			
			String texto = "<div class='div_docs'"+(doc_cart.getStatus() == '0' ? " style='font-weight: bold;'" : "")+" id='div_doc"+(i)+"'>";
			texto = texto + "<input type='checkbox' class='chkbox' value='"+doc_cart.getIdDocumento()+"' id='chk"+i+"' "+
						"onClick='js.direto.sel_chkbox_doc("+(i)+");' />";
			texto = texto + (doc_cart.getStatus() == '0' ? "<img src='imagens/outras/cartaFec.gif' class='img_docs' id='doc_status' />" : "<img src='imagens/outras/cartaAbr.gif' class='img_docs' id='doc_status' />");
			texto = texto + (doc.getTipo() == 'I' ? "<img src='imagens/outras/computer.gif' title='Documento interno' class='img_docs' id='doc_tipo'/> " : "<img src='imagens/outras/scanner.gif' title='Documento externo' class='img_docs' id='doc_tipo'/>");
			texto = texto + (pri.equals(" N") ? "<span class='prio_n_docs'>"+pri+"</span>" : (pri.equals(" U") ? "<span class='prio_u_docs'>"+pri+"</span>" : "<span class='prio_uu_docs'>"+pri+"</span>"));
			texto = texto + (notificar+"<a href='documento.html?id="+doc.getIdDocumentoDetalhes()+"' title='"+doc.getUsuarioElaborador().getUsuLogin()+"' id='rem_docs' class='ahref_docs'>"+doc.getRemetente().replace('-',' ')+"</a>");
			texto = texto + (" - <a href='documento.html?id="+doc.getIdDocumentoDetalhes()+"' title='"+doc.getAssunto()+"' id='ass_docs' class='ahref_docs'>"+assunto+"</a>");
			texto = texto + ("<font class='data_docs'>"+DataTimeUtil.getBrazilFormatDataHora(doc_cart.getDataHora())+"</font>");
			texto = texto + "</div>"; 
			
			//doc_cart.getCarteira().getContas().
			
			System.out.println(this.usuarioService.whoUser(1));
					
			data.setTexto(texto);
			
			documentos.add(data);
		}
		
		return documentos;

		
		//data.setId(query.)
		
		//List list = query.list();
	
		
		
		/*String sql = "SELECT * FROM mensagens doc,idmensausu doc_cart WHERE doc_cart.IdCarteira = ?";
		
		System.out.println(sql);

		final List<DataUtils> myResults = new ArrayList<DataUtils>();
		
		jdbcTemplate.query(sql, new Object[] {idCarteira}, 
			    new RowCallbackHandler() {
			      public void processRow(ResultSet rs) throws SQLException {
			        // do something with the rowdata - like create a new
			        // object and add it to the List in the enclosing code
			    	DataUtils data = new DataUtils();
			    	data.setId(rs.getString("IdMensagem"));
			    	data.setTexto(rs.getString("Status")+" - "+rs.getString("IdSecao"));
			        myResults.add(data);
			      }
			    }
			  );
*/		
		/*DataUtils d = new DataUtils();
		d.setId("id");
		d.setTexto("texto");
		myResults.add(d);*/
		
			
		/*System.out.println(myResults.toString());
		
		return myResults;*/
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
		
		return (Long) hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("SELECT count(distinct doc.documentoDetalhes.idDocumentoDetalhes) FROM Documento as doc " +
				"WHERE doc.carteira.idCarteira = ? AND doc.status in ("+box+")" + filtro +
		"").setInteger(0, idCarteira).uniqueResult();
	}

	@Override
	public void sendDocument(Carteira[] carteira, Documento documento) {
		// TODO Auto-generated method stub

	}

	@Override
	public Documento selectByIdCarteira(final Integer idCarteira) {
		
		/*Integer idDocumento = new Integer(2);
		
		Documento d = new Documento(idDocumento, null, '1', null);
		
		System.out.println(d.getStatus());
		
		return d;*/
		
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
	public Documento selectById(Integer id, Integer idCarteira) {
		
		Documento returnDoc = (Documento) hibernateTemplate.getSessionFactory().getCurrentSession().createQuery("FROM Documento as doc " +
				"WHERE doc.carteira.idCarteira = ? AND doc.documentoDetalhes.idDocumentoDetalhes = ? GROUP BY doc.carteira.idCarteira" +
		"").setInteger(0, idCarteira).setInteger(1, id).uniqueResult(); 
		
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
			String sTipo = " details.tipoDocumento like '%" + tipo + "%'";
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
									+ "details.tipoDocumento like '%"+searchTerm+"%'"
									+ " or details.nrProtocolo like '%"+searchTerm+"%'"
									+ " or details.nrDocumento like '%"+searchTerm+"%'"
									+ " or details.assunto like '%"+searchTerm+"%'"
									+ " or details.dataEntSistema like '%"+searchTerm+"%')";
		String baseSearch = " doc.carteira.secao.idSecao = "+idSecao+" and doc.carteira.om.idOM = "+idOM+"";
		
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
		
		System.out.println(sql);
		
		Query query = getSession().createQuery(sql);
		
		if(form.isServerSide()){
			query.setFirstResult(start);
			query.setMaxResults(amount);
		}
		
		List results = query.list();
		System.out.println(results.size());
		System.out.println(query.getQueryString());
		
		for(int i=0; i<results.size(); i++){
			
			Object[] objects = (Object[]) results.get(i);
			DocumentoDetalhes doc_det = (DocumentoDetalhes) objects[1];
			Documento doc_conta = (Documento) objects[0];
			System.out.println(doc_det.getIdDocumentoDetalhes());
			DocumentoCompleto doc_completo = new DocumentoCompleto(doc_conta,doc_det);
			
			documentos.add(doc_completo);
			
		}
			
		
		return documentos;
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

	@Override
	public int returnTotalSearch(PesquisaForm form) {
		
		int idSecao = form.getCarteira().getSecao().getIdSecao();
		int idOM = form.getCarteira().getOm().getIdOM();
		
		String sqlCount = "select count(distinct doc.documentoDetalhes.idDocumentoDetalhes) from Documento as doc" +
		" where doc.carteira.secao.idSecao = "+idSecao+" and doc.carteira.om.idOM = "+idOM;
		
		Integer total = ((Long)getSession().createQuery(sqlCount).uniqueResult()).intValue();
		
		return (total == null ? 0 : total.intValue()); 
	}

}
