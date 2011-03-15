<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<script type="text/javascript">

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


<c:forEach var="a" items="${listaProtocolo}">
 	
    ${a.idDocumentoDetalhes} - ${a.assunto}  
      <br>
 </c:forEach>


	

</body>
</html>