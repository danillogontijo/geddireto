package br.org.ged.direto.model.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.repository.hibernate.DocumentosRepositoryImpl;

//@Repository("documentoRepositoryJdbc")
public class DocumentosRepositoryJdbcImpl extends DocumentosRepositoryImpl {

/*@Autowired	
private JdbcTemplate jdbcTemplate;
	
public List<DataUtils> listDocumentsFromAccount(Integer idCarteira) {
		
	String sql = "SELECT * FROM idmensausu,mensagens WHERE IdCarteira = ?";

	final List<DataUtils> myResults = new ArrayList<DataUtils>();
	
	jdbcTemplate.query(sql, new Object[] {idCarteira}, 
		    new RowCallbackHandler() {
		      public void processRow(ResultSet rs) throws SQLException {
		        // do something with the rowdata - like create a new
		        // object and add it to the List in the enclosing code
		    	DataUtils data = new DataUtils();
		    	data.setId(rs.getString("IdMensagem"));
		    	data.setTexto(rs.getString("name"));
		        myResults.add(data);
		      }
		    }
		  );
	
		
	System.out.println(myResults.toString());
	
	return myResults;
	}*/
}
