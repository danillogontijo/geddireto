<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<form:form method="POST" commandName="usuario">
	<table>
		<tr>
			<td>Usuario Login :</td>
			<td><form:input path="usuLogin" /></td>
		</tr>
		<tr>
			<td>Password :</td>
			<td><form:password path="usuSenha" /></td>
		</tr>

		<tr>
			<td colspan="2"><input type="submit"></td>
		</tr>
	</table>
</form:form>




</body>
</html>