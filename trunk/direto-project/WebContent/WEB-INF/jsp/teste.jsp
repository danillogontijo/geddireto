<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_head.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/protocoloJS.js"></script>

<script type="text/javascript">

var txt = "";


$j(function(){

	//setTimeout("addTxt();",100);

});

var formulario = null;

protocoloJS.getF(ret);

function ret(d){
	formulario = d;
	alert(d.nrDocumento);
	protocoloJS.setF(formulario,setF);
}

function setF(d){
	alert(d.nrDocumento+d.assinadoPor);	
}


function addTxt(){
	//txt = txt + "<br>teste<br>";
	protocoloJS.getsRetorno(retorno);
}


function retorno(dados){
	txt = dados;
	var t = $j("#retorno").text();
	//$("retorno").innerHTML = txt;
	$j("#retorno").html(txt);

	if (t.indexOf("Finalizado") == -1){
		setTimeout("addTxt();",300);
	}else{
		alert("finalizado.");
		return false;
	}
}

//alert("<c:out value="${pstgradList}"/>");

</script>

<!--

 <c:forEach var="p" items="${pstgradList}">
 	<c:set var="pList" value="${p.pstgradDesc}" />
      <c:out value="${pList}"/>
		<c:if test="${p.idPstGrad==usuario.pstGrad.idPstGrad}">selected</c:if>
      <br>
 </c:forEach>

-->

<span id="retorno" style="min-height: 200px;"></span>

<p>

<c:forEach var="a" items="${listaProtocolo}">
 	
    ${a.idDocumentoDetalhes} - ${a.assunto}  
      <br>
 </c:forEach>

</p>
	

</body>
</html>