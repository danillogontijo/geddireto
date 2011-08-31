<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.text.*"%>
<%@ page language="java" import="java.util.*"%>

<html>
	<body>
<%

String documento = request.getParameter("documento");

try {
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/direto", "root", "g3dd143t0");

	String query = "";
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	ResultSet resultado = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	
	if(documento == null){	
	
		query = "select id,tipoNome from tiposdocumentos";
		
		pstmt = conn.prepareStatement(query);
		
		resultado = pstmt.executeQuery();
		
		
		while(resultado.next()){
			int idTipoDocumento = resultado.getInt(1);
			String tipoDoc = resultado.getString(2);
			
			query = "update mensagens set idTipoDocumento = "+idTipoDocumento+" where tipoDoc='"+tipoDoc+"'";
			pstmt2 = conn.prepareStatement(query);
			pstmt2.execute();
			pstmt2.close();
			out.println(query+"<br>");
		}
		resultado.close();
		out.print("<br><br>");
		
		query = "update idmensausu set notificar = 0";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update usuomsec set DataContaRec = '2011-08-19 00:00:00',"+
			"DataContaTransf = '2011-08-19 00:00:00',IdCarteira=1,ContaPrincipal=1,IdUsuarioProprietario=1";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update usuario set usuUltimoLogin = '2011-08-19 00:00:00' where usuUltimoLogin IS NULL";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update usuario set usuIdt = 0";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update anexos set idAssinadoPor = 0";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update anotacoes set dataHora = '2011-08-19 00:00:00',idCarteira=1 where dataHora IS NULL";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update anotacoes set idCarteira=1";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update despachos set dataHora = '2011-08-19 00:00:00' where dataHora IS NULL";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update despachos set idCarteira=1";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update despachos set idUsuarioDestinatario = 0";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update historico set dataHora = '2011-08-19 00:00:00' where dataHora IS NULL";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "update historico set idCarteira=1";
		pstmt = conn.prepareStatement(query);
		pstmt.execute();
		
		query = "select assinadoPor,id from anexos where assinadoPor IS NOT NULL and assinadoPor <> 'no' group by assinadoPor";
		pstmt = conn.prepareStatement(query);
		resultado = pstmt.executeQuery();
		while(resultado.next()){
			String usuLogin = resultado.getString(1);
			//int idAnexo = resultado.getInt(2);
			
			query = "select idUsuario from usuario where usuLogin='"+usuLogin+"'";
			pstmt = conn.prepareStatement(query);
			rs2 = pstmt.executeQuery();
			while(rs2.next()){
				int idUsuario = rs2.getInt(1);
				
				query = "update anexos set idAssinadoPor = "+idUsuario+" where assinadoPor='"+usuLogin+"'";
				pstmt =  conn.prepareStatement(query);
				pstmt.execute();
				out.println(query+"<br>");
				break;
			}
			rs2.close();
		}
		resultado.close();
		
		
		query = "select idUsuario,idUsuOmSec from usuomsec";
		pstmt = conn.prepareStatement(query);
		resultado = pstmt.executeQuery();
		while(resultado.next()){
			int idUsuario = resultado.getInt(1);
			int idUsuOmSec = resultado.getInt(2);
			
			query = "update usuomsec set IdUsuarioProprietario = "+idUsuario+" where idUsuOmSec="+idUsuOmSec;
			pstmt =  conn.prepareStatement(query);
			pstmt.execute();
			out.println(query+"<br>");
			
		}
		resultado.close();
		
}else{

	/**
	*DESVINCULANDO O DOC DO USUARIO E PASSANDO PARA A CARTEIRA
	*ESSE SCRIPT SÓ PODERÁ SER EXECUTADO DEPOIS DE TODAS AS CARTEIRAS CADASTRADAS
	**/
	/*query = "select carteira.idCarteira,usuario.idUsuario from usuario,usuomsec,carteira where usuomsec.idUsuario=usuario.idUsuario and carteira.idCarteira=usuomsec.idCarteira and usuomsec.idCarteira<>1";
	pstmt = conn.prepareStatement(query);
	resultado = pstmt.executeQuery();
	while(resultado.next()){
		int idCarteira = resultado.getInt(1);
		int idUsuario = resultado.getInt(2);
		query = "update idmensausu set idCarteira = "+idCarteira+" where idUsuario="+idUsuario;
		pstmt2 =  conn.prepareStatement(query);
		pstmt2.execute();
		out.println(query+"<br>");
	}
	resultado.close();*/
	
	query = "select idmensausu.id,idmensausu.idOM,idmensausu.idSecao,idmensausu.idUsuario from idmensausu";
	pstmt = conn.prepareStatement(query);
	resultado = pstmt.executeQuery();
	int idSecaoAnt = 0;
	int idOMAnt = 0;
	
	while(resultado.next()){
		int id = resultado.getInt(1);
		int idSecao = resultado.getInt(3);
		int idOM = resultado.getInt(2);
		int idUsuario = resultado.getInt(4);
		
		boolean tem = false;
		
		query = "select carteira.idCarteira from usuomsec,carteira where usuomsec.idUsuario="+idUsuario+" and carteira.idCarteira=usuomsec.idCarteira and usuomsec.idCarteira<>1 and carteira.idom="+idOM+" and carteira.idsecao="+idSecao;
		pstmt = conn.prepareStatement(query);
		rs2 = pstmt.executeQuery();
		while(rs2.next()){
			int idCarteira = rs2.getInt(1);
			query = "update idmensausu set idCarteira = "+idCarteira+" where id="+id;
			pstmt2 =  conn.prepareStatement(query);
			pstmt2.execute();
			pstmt2.close();
			tem = true;
				
			break;
		}
		rs2.close();
		rs2 = null;
		pstmt.close();
		pstmt = null;
		
		
		if (tem)
			continue;
	
		query = "select carteira.idCarteira from carteira where carteira.idSecao="+idSecao+" and carteira.idom="+idOM+" and idCarteira <> 1";
		pstmt = conn.prepareStatement(query);
		rs2 = pstmt.executeQuery();
		while(rs2.next()){
			int idCarteira = rs2.getInt(1);
			query = "update idmensausu set idCarteira = "+idCarteira+" where id="+id;
			pstmt2 = conn.prepareStatement(query);
			pstmt2.execute();
			pstmt2.close();
			out.println(query+"<br>");
			break;
		}
		rs2.close();
		rs2 = null;
		pstmt.close();
		pstmt = null;
		
	}
	resultado.close();
	resultado = null;
		
	
	/**
	*CADASTRANDO DOC ENVIADOS
	**/
	query = "select idUsuRem,id,data,idSecRem,idOMRem from mensagens";
	pstmt = conn.prepareStatement(query);
	resultado = pstmt.executeQuery();
	while(resultado.next()){
		int idUsuario = resultado.getInt(1);
		int idMensagem = resultado.getInt(2);
		String data = resultado.getString(3);
		int idSecao = resultado.getInt(4);
		int idOM = resultado.getInt(5);
		
		boolean tem = false;
		
		query = "select carteira.idCarteira from usuomsec,carteira where usuomsec.idUsuario="+idUsuario+" and carteira.idCarteira=usuomsec.idCarteira and usuomsec.idCarteira<>1 and carteira.idom="+idOM+" and carteira.idsecao="+idSecao;
		pstmt = conn.prepareStatement(query);
		rs2 = pstmt.executeQuery();
		while(rs2.next()){
			int idCarteira = rs2.getInt(1);
			query = "INSERT INTO idmensausu(idMensagem,status,dataHora,idCarteira,notificar)"+
		    " VALUES ("+idMensagem+", 3, '"+data+"', "+idCarteira+",0)";
			pstmt2 =  conn.prepareStatement(query);
			pstmt2.execute();
			pstmt2.close();
			pstmt2 = null;
			tem = true;
				
			break;
		}
		rs2.close();
		rs2 = null;
		pstmt.close();
		pstmt = null;
		
		
		if (tem)
			continue;
	
		query = "select carteira.idCarteira from carteira where carteira.idSecao="+idSecao+" and carteira.idom="+idOM+" and idCarteira <> 1";
		pstmt = conn.prepareStatement(query);
		rs2 = pstmt.executeQuery();
		while(rs2.next()){
			int idCarteira = rs2.getInt(1);
			query = "INSERT INTO idmensausu(idMensagem,status,dataHora,idCarteira,notificar)"+
		    " VALUES ("+idMensagem+", 3, '"+data+"', "+idCarteira+",0)";
			
			pstmt2 = conn.prepareStatement(query);
			pstmt2.execute();
			pstmt2.close();
			pstmt2 = null;
			out.println(query+"<br>");
			break;
		}
		rs2.close();
		rs2 = null;
		pstmt.close();
		pstmt = null;
	}

}

resultado.close();
resultado = null;
//pstmt.close();
pstmt2.close();
pstmt2 = null;
conn.close();

}catch(Exception e){
	e.printStackTrace();
}
%>

	</body>
</html>