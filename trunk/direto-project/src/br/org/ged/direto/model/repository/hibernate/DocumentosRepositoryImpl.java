package br.org.ged.direto.model.repository.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.direto.util.DataTimeUtil;
import br.org.direto.util.DataUtils;
import br.org.direto.util.DocumentosUtil;
import br.org.direto.util.UsuarioUtil;
import br.org.ged.direto.model.entity.Carteira;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Pastas;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.DocumentosRepository;
import br.org.ged.direto.model.service.UsuarioService;

@Repository("documentoRepository")
public class DocumentosRepositoryImpl implements DocumentosRepository, MessageSourceAware {

	private HibernateTemplate hibernateTemplate;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    
    @Autowired
	private UsuarioService usuarioService;
    
    /*@Autowired	
    private JdbcTemplate jdbcTemplate;*/
    
    @Autowired
    private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
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
		
		int limitePorPagina = 2;
		
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
			
			DocumentosUtil.documentos.put(doc.getIdDocumentoDetalhes(), doc);
			
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
				assunto = assunto.substring(0, 59)+"...";
			}
			
			String texto = "<div class='div_docs'"+(doc_cart.getStatus() == '0' ? " style='font-weight: bold;'" : "")+" id='div_doc"+(i)+"'>";
			texto = texto + "<input type='checkbox' class='chkbox' value='"+doc.getIdDocumentoDetalhes()+"' id='chk"+i+"' "+
						"onClick='js.direto.sel_chkbox_doc("+(i)+");' />";
			texto = texto + (doc_cart.getStatus() == '0' ? "<img src='imagens/outras/cartaFec.gif' class='img_docs' id='doc_status' />" : "<img src='imagens/outras/cartaAbr.gif' class='img_docs' id='doc_status' />");
			texto = texto + (doc.getTipo() == 'I' ? "<img src='imagens/outras/computer.gif' title='Documento interno' class='img_docs' id='doc_tipo'/> " : "<img src='imagens/outras/scanner.gif' title='Documento externo' class='img_docs' id='doc_tipo'/>");
			texto = texto + (pri.equals(" N") ? "<font class='prio_n_docs'>"+pri+"</font>" : (pri.equals(" U") ? "<font class='prio_u_docs'>"+pri+"</font>" : "<font class='prio_uu_docs'>"+pri+"</font>"));
			texto = texto + ("<a href='' title='"+doc.getUsuarioElaborador().getUsuLogin()+"' id='rem_docs' class='ahref_docs'>"+doc.getRemetente()+"</a>");
			texto = texto + ("<a href='' title='"+doc.getAssunto()+"' id='ass_docs' class='ahref_docs'>"+assunto+"</a>");
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


}
