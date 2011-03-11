<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="../include_taglibs.jsp" %>

{ "aaData": [

<c:set var="i" value="0"></c:set>
<c:set var="tamanho" value="${fn:length(docs)}"></c:set>

<c:forEach var="d" items="${docs}">

["${d.documento.idDocumento}","${d.documentoDetalhes.tipoDocumento}","${d.documentoDetalhes.nrProtocolo}",
"${d.documentoDetalhes.nrDocumento}",
"<c:if test="${d.documentoDetalhes.remetente != '-'}">DO: ${d.documentoDetalhes.remetente} - </c:if><c:if test="${d.documentoDetalhes.destinatario != '-'}">PARA: ${d.documentoDetalhes.destinatario} - </c:if>${d.documentoDetalhes.assunto}",
"<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${d.documentoDetalhes.dataEntSistema}" />"]
<c:set var="i" value="${i+1}"></c:set>

<c:if test="${i != tamanho}">,</c:if>

</c:forEach>


] }
