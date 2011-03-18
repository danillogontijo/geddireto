<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="include_taglibs.jsp" %>

<json:object>

  <json:array name="dados" var="d" items="${anotacoes}">
    <json:object>
      <json:property name="carteira" value="${d.carteira.cartAbr}"/>
      <json:property name="usuNGuerra" value="${d.usuario.usuNGuerra}"/>
      <json:property name="acao" value="${d.anotacao}"/>
      <json:property name="dataHora">
        <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${d.dataHoraAnotacao}" />
      </json:property>
    </json:object>
  </json:array>

</json:object>