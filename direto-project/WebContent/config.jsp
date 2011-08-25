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
	query = "select carteira.idCarteira,usuario.idUsuario from usuario,usuomsec,carteira where usuomsec.idUsuario=usuario.idUsuario and carteira.idCarteira=usuomsec.idCarteira and usuomsec.idCarteira<>1";
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
	resultado.close();

	/**
	*CADASTRANDO DOC ENVIADOS
	**/
	query = "select idUsuRem,id,data from mensagens";
	pstmt = conn.prepareStatement(query);
	resultado = pstmt.executeQuery();
	while(resultado.next()){
		int idUsuario = resultado.getInt(1);
		int idMensagem = resultado.getInt(2);
		String data = resultado.getString(3);
		
		query = "select idCarteira from usuomsec where idCarteira<>1 and idUsuario="+idUsuario;
		pstmt = conn.prepareStatement(query);
		rs2 = pstmt.executeQuery();
		while(rs2.next()){
			int idCarteira = rs2.getInt(1);
			
			query = "INSERT INTO idmensausu(idMensagem,status,dataHora,idCarteira,notificar)"+
			    " VALUES ("+idMensagem+", 3, '"+data+"', "+idCarteira+",0)";
			pstmt =  conn.prepareStatement(query);
			pstmt.execute();
			out.println(query+"<br>");
			break;
		}
	}

}

resultado.close();
pstmt.close();
pstmt2.close();
conn.close();

}catch(Exception e){
	e.printStackTrace();
}
%>

	</body>
</html>