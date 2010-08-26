<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>teste</title>
</head>

<body onload="document.f.j_username.focus();">





	<c:if test="${not empty param.login_error}">
      <font color="red">
        Your login attempt was not successful, try again.<br/><br/>
        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
      </font>
    </c:if>





<form action="j_spring_security_check" method="post" name="f">
	<label for="j_username">Login</label>:
	<input id="j_username" name="j_username" size="20" maxlength="50" type="text"/>
	<br />

	<input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" value="true"/>
	<label for="_spring_security_remember_me">Remember Me?</label>
	<br />
	<label for="j_password">Password</label>:
	<input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
	<br />

	<input type="submit" value="Login"/>
</form>
	





  </body>
</html>
